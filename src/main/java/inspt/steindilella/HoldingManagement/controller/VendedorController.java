package inspt.steindilella.HoldingManagement.controller;

import inspt.steindilella.HoldingManagement.entity.Administrador;
import inspt.steindilella.HoldingManagement.entity.Empresa;
import inspt.steindilella.HoldingManagement.entity.Vendedor;
import inspt.steindilella.HoldingManagement.entity.VendedorCaptado;
import inspt.steindilella.HoldingManagement.service.AreasMercadoService;
import inspt.steindilella.HoldingManagement.service.EmpleadoService;
import inspt.steindilella.HoldingManagement.service.EmpresaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("admin/vendedor")
public class VendedorController {

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private AreasMercadoService areasMercadoService;

    @Autowired
    private EmpresaService empresaService;

    private void recuperarAdmin(HttpSession session, Model model){
        Integer id = Integer.valueOf( (String) session.getAttribute("id"));

        //recupero al admin
        Administrador adm = (Administrador) empleadoService.getById(id);

        model.addAttribute("admin",adm);
    }

    private void cargarDatosFormulario(Vendedor vendedor, Model model){

        Empresa empresaVendedor = empleadoService.getEmpresaVendedor(vendedor.getId());

        //Obtengo todos los empleados de la empresa que esten disponibles para ser captados
        if(empresaVendedor != null){
            Integer idEmpresa = empresaVendedor.getId();

            Set <Vendedor> vendedoresDeLaEmpresa = empresaService.getVendedoresPorEmpresa(idEmpresa);

            //retiro al vendedor para que no figure como captable a sí mismo
            vendedoresDeLaEmpresa.remove(vendedor);

            Set<Vendedor> vendedoresDisponibles = new HashSet<>();

            for(Vendedor v : vendedoresDeLaEmpresa){
                if(empleadoService.vendedorEsCaptable(vendedor.getId(),v.getId())){
                    vendedoresDisponibles.add(v);
                }
            }
            model.addAttribute("vendedoresDisponibles", vendedoresDisponibles);
        }

        //le cargo la empresa a la cual trabaja
        vendedor.setEmpresa(empresaVendedor);

        //agrego el vendedor al formulario
        model.addAttribute("vendFormulario",vendedor);

        model.addAttribute("empresas",empresaService.getAll());

        //agrego los vendedores captados
        try{
            model.addAttribute("vendedoresCaptados", empleadoService.getVendedoresCaptados(vendedor.getId()));
        }catch (Exception e){
            System.out.println("Vendedor sin captados");
        }



    }

    @GetMapping("/listar")
    public String listarVendedores(HttpSession session, Model model){
        recuperarAdmin(session,model);
        Set<Vendedor> vendedores = empleadoService.getVendedores();

        for(Vendedor v : vendedores){
            Integer id = v.getId();
            if(v.getEmpresa() != null){
                v.setEmpresa(empresaService.getEmpresaByVendedorId(id));
            }

            if(v.getVendedoresCaptados() != null){
                v.setVendedoresCaptados(empleadoService.getVendedoresCaptados(id));
            }

        }

        model.addAttribute("vendedores",vendedores);

        return "listarVend";
    }

    @GetMapping("/formulario")
    public String formularioVendedor(HttpSession session, Model model){
        //Instancio un Vendedor vacio
        Vendedor vendedor = new Vendedor();

        recuperarAdmin(session,model);

        cargarDatosFormulario(vendedor,model);

        return "formularioVend";
    }

    @PostMapping("/agregar")
    public String agregar(@ModelAttribute("vendFormulario") Vendedor vendedor){

        vendedor.setEliminado(0);

        //si el id es null, es porque estoy creando el vendedor
        if(vendedor.getId() == null){

            empleadoService.save(vendedor);
        }
        else{

            empleadoService.update(vendedor);
        }

        return "redirect:/admin/vendedor/listar";
    }

    @GetMapping("/actualizar")
    public String actualizar(@RequestParam("idTemporal") Integer id,HttpSession session, Model model){
        recuperarAdmin(session,model);
        //Rescato al Vendedor
        Vendedor vendedor = (Vendedor) empleadoService.getById(id);

        cargarDatosFormulario(vendedor,model);

        return "formularioVend";
    }

    @GetMapping("/eliminar")
    public String eliminar(@RequestParam("idTemporal") Integer id){

        Vendedor vendedorEliminar = (Vendedor) empleadoService.getById(id);
        Empresa empresaDesvincular = empleadoService.getEmpresaVendedor(id);

        //desvinculo al vendedor de la empresa (si trabaja)
        if(empresaDesvincular != null){
            empresaService.desvincularVendedor(vendedorEliminar,empresaDesvincular.getId());
        }
        
        empleadoService.delete(id);

        return "redirect:/admin/vendedor/listar";
    }

    @PostMapping("/listarEmpresas/{id}")
    public String listarEmpresas(@PathVariable("id") Integer id, Model model, HttpSession session){
        model.addAttribute("vendFormulario", (Vendedor) empleadoService.getById(id));
        Set<Empresa> listadoEmpresas = empresaService.getAll();
        for(Empresa e : listadoEmpresas){
            if(e.getEliminado() == 1){
                listadoEmpresas.remove(e);
            }
        }
        model.addAttribute("empresas",listadoEmpresas);
        model.addAttribute("empresa", new Empresa());

        recuperarAdmin(session,model);

        return "formularioVendEmpresa";
    }

    @PostMapping("/agregarEmpresa/{idVendedor}")
    public String agregarEmpresa(@RequestParam("idEmpresa") Integer idEmpresa, @PathVariable("idVendedor") Integer idVendedor){

        Vendedor vendedor = (Vendedor) empleadoService.getById(idVendedor);

        Set<VendedorCaptado> listadoCaptados = empleadoService.getVendedoresCaptados(idVendedor);

        if(listadoCaptados != null){
            vendedor.setVendedoresCaptados(listadoCaptados);
        }
        //manejo la excepción en caso de no encontrar una empresa asignada al vendedor
        try{
            vendedor.setEmpresa(empresaService.getEmpresaByVendedorId(idVendedor));
        }catch(Exception e){
            System.out.println("Vendedor sin empresa asignada");
        }


        //si el vendedor tiene una empresa, se la elimino
        if(vendedor.getVendedoresCaptados() != null){
             empleadoService.eliminarTodosLosVendedoresCaptados(idVendedor);
        }

        //si el vendedor tiene captados, los elimino
        if(vendedor.getEmpresa() != null){
            empresaService.desvincularVendedor(vendedor,vendedor.getEmpresa().getId());
        }

        //tambien elimino al vendedor de los captados (si es que fue captado)
        Vendedor captador = empleadoService.getCaptador(idVendedor);
        if( captador != null){
            empleadoService.eliminarVendedorCaptado(idVendedor,captador.getId());
        }

        empresaService.agregarVendedor(vendedor, idEmpresa);

        return "redirect:/admin/vendedor/listar";
    }

    @PostMapping("/captarVendedor/{idVendCapt}/{idVendedor}")
    public String captarVendedor(@PathVariable("idVendCapt") Integer idVendCapt, @PathVariable("idVendedor") Integer idVendedor,
                                          @RequestParam("fechaCaptado")
                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaCaptado, Model model){

        Vendedor vendedor = (Vendedor) empleadoService.getById(idVendedor);

        //creo el VendedorCaptado
        empleadoService.agregarVendedorCaptado(idVendedor,idVendCapt,fechaCaptado);

        cargarDatosFormulario(vendedor,model);

        return "redirect:/admin/vendedor/actualizar?idTemporal="+vendedor.getId();
    }

    @PostMapping("/eliminarVendedorCaptado/{id}")
    public String desvincularVendedorCaptado(@PathVariable("id") String idVendedores,Model model){

        String idVendedor = idVendedores.substring(0,idVendedores.indexOf('_'));

        String idVendCaptado = idVendedores.substring(idVendedores.indexOf('_')+1,idVendedores.length());

        empleadoService.eliminarVendedorCaptado(Integer.valueOf(idVendedor),Integer.valueOf(idVendCaptado));

        Vendedor vendedor = (Vendedor) empleadoService.getById(Integer.valueOf(idVendedor));

        cargarDatosFormulario(vendedor,model);

        return "redirect:/admin/vendedor/actualizar?idTemporal="+idVendedor;
    }

}
