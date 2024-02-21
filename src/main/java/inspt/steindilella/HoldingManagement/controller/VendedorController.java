package inspt.steindilella.HoldingManagement.controller;

import inspt.steindilella.HoldingManagement.entity.Administrador;
import inspt.steindilella.HoldingManagement.entity.Empresa;
import inspt.steindilella.HoldingManagement.entity.Vendedor;
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

        Empresa empresaVendedor = vendedor.getEmpresa();

        //Obtengo todos los empleados de la empresa que esten disponibles para ser captados
        if(empresaVendedor != null){
            Integer idEmpresa = vendedor.getEmpresa().getId();

            Set <Vendedor> vendedoresDeLaEmpresa = empresaService.getVendedoresPorEmpresa(idEmpresa);
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
        model.addAttribute("vendedoresCaptados", empleadoService.getVendedoresCaptados(vendedor.getId()));
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
    public String formularioVendedor(Model model){
        //Instancio un Vendedor vacio
        Vendedor vendedor = new Vendedor();

        cargarDatosFormulario(vendedor,model);

        return "formularioVend";
    }

    @PostMapping("/agregar")
    public String agregarAses(@ModelAttribute("vendFormulario") Vendedor vendedor){

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
    public String actualizarVendedor(@RequestParam("idTemporal") Integer id, Model model){
        //Rescato al Vendedor
        Vendedor vendedor = (Vendedor) empleadoService.getById(id);

        cargarDatosFormulario(vendedor,model);

        return "formularioVend";
    }

    @GetMapping("/eliminar")
    public String eliminarAsesor(@RequestParam("idTemporal") Integer id){
        empleadoService.delete(id);

        return "redirect:/admin/vendedor/listar";
    }

    @PostMapping("/listarEmpresas/{id}")
    public String listarEmpresas(@PathVariable("id") Integer id, Model model){
        model.addAttribute("vendFormulario", (Vendedor) empleadoService.getById(id));
        model.addAttribute("empresas",empresaService.getAll());
        model.addAttribute("empresa", new Empresa());

        return "formularioVendEmpresa";
    }

    //todo: continuar armando este endpoint
    @PostMapping("/agregarEmpresa/{idVendedor}")
    public String agregarEmpresa(@RequestParam("idEmpresa") Integer idEmpresa, @PathVariable("idVendedor") Integer idVendedor){

        Vendedor vendedor = (Vendedor) empleadoService.getById(idVendedor);

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

    @PostMapping("/eliminarVendedorCaptado/{idVendCapt}/{idVendedor}")
    public String desvincularVendedorCaptado(@PathVariable("idVendCapt") Integer idVendCapt,
                                           @PathVariable("idVendedor") Integer idVendedor,Model model){

        Vendedor vendedor = (Vendedor) empleadoService.getById(idVendedor);

        empleadoService.eliminarVendedorCaptado(idVendedor,idVendCapt);

        cargarDatosFormulario(vendedor,model);

        return "redirect:/admin/vendedor/actualizarVend?idTemporal="+vendedor.getId();
    }

}
