package inspt.steindilella.HoldingManagement.controller;

import inspt.steindilella.HoldingManagement.entity.*;
import inspt.steindilella.HoldingManagement.service.AreasMercadoService;
import inspt.steindilella.HoldingManagement.service.EmpleadoService;
import inspt.steindilella.HoldingManagement.service.EmpresaService;
import inspt.steindilella.HoldingManagement.service.UbicacionesService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("admin/empresas")
public class EmpresaController {
    private EmpresaService empresaService;
    private UbicacionesService ubicacionesService;
    private EmpleadoService empleadoService;
    private AreasMercadoService areasMercadoService;
    @Autowired
    public EmpresaController(EmpresaService empresaService, UbicacionesService ubicacionesService, EmpleadoService empleadoService, AreasMercadoService areasMercadoService) {
        this.empresaService = empresaService;
        this.ubicacionesService = ubicacionesService;
        this.empleadoService= empleadoService;
        this.areasMercadoService = areasMercadoService;
    }

    private void recuperarAdmin(HttpSession session, Model model){
        Integer id = Integer.valueOf( (String) session.getAttribute("id"));

        //recupero al admin
        Administrador adm = (Administrador) empleadoService.getById(id);

        model.addAttribute("admin",adm);
    }

    @GetMapping("/listar")
    public String listarAreas(HttpSession session, Model model){
        Set<Empresa> set = empresaService.getAll();
        recuperarAdmin(session,model);

        //buscamos las areas relacionadas a las empresas.

        model.addAttribute("empresas",set);


        return "listar-empresas.html";
    }

    @GetMapping("/formulario")
    public String mostrarFormulario(HttpSession session, Model model){
        Empresa empresa = new Empresa();

        recuperarAdmin(session,model);
        model.addAttribute("empresa",empresa);

        //agrego el listado de ciudades al html
        Set<Ciudad> ciudades = ubicacionesService.getAllCiudades();
        //System.out.println(ciudades.size());
        model.addAttribute("ciudades", ciudades);

        //agrego el listado de areas de mercado al html
        Set<AreasMercado> areas = areasMercadoService.getAll();
        //System.out.println(areas.size());
        //model.addAttribute("areas", areas);

        return "formulario-empresa";
    }

    @PostMapping("/agregar")
    public String agregar(@Param("idTemporal") Integer id,
                          @ModelAttribute("empresa") Empresa empresa){
        System.out.println(empresa.toString());

        if(id != null){
            //si el id es null, es porque estoy creando el area
            if(empresaService.getById(id) == null){
                System.out.println("Se crea el registro de empresa");
                empresaService.save(empresa);
            }
            else{
                System.out.println("Se actualiza registro de empresa");
                Set<AreasMercado> areasCubiertas = empresaService.getAreasMercadoPorEmpresa(id);
                empresa.setAreasMercados(areasCubiertas);
                empresaService.update(empresa);
            }
        }else{
            //si el id es null, el registro no existe
            System.out.println("Se crea nueva empresa: "+ empresa.getNombre());
            Empresa nuevaEmpresa = empresa;
            nuevaEmpresa.setSede(ubicacionesService.getCiudadById(empresa.getSede().getId()));
            nuevaEmpresa.setEliminado(0);
            empresaService.save(nuevaEmpresa);

        }



        return "redirect:/admin/empresas/listar";
    }

    @GetMapping("/actualizar")
    public String actualizar(@RequestParam("idTemporal") Integer id, HttpSession session, Model model){
        recuperarAdmin(session,model);

        //agregamos a la session http la empresa a modificar
        session.setAttribute("idEmpresa", empresaService.getById(id));
        Empresa empresa = (Empresa) session.getAttribute("idEmpresa");
        model.addAttribute("empresa",empresa);
        System.out.println("Se actualiza "+empresa.toString());

        //agregamos el listado de areas al html
        Set<AreasMercado> areas = areasMercadoService.getAll();
        Set<AreasMercado> areasCubiertas = empresa.getAreasMercados();

        //agrego el listado de ciudades al html
        Set<Ciudad> ciudades = ubicacionesService.getAllCiudades();

        System.out.println(ciudades.size());
        model.addAttribute("ciudades", ciudades);
        model.addAttribute("areasMercado", areas);
        model.addAttribute("areasCubiertas", areasCubiertas);
        model.addAttribute("selectedSedeId", empresa.getSede().getId()); // Suponiendo que empresa tiene un objeto Sede

        return "formulario-empresa";
    }

    @GetMapping("/eliminar")
    public String eliminar(@RequestParam("idTemporal") Integer id){
        empresaService.delete(id);

        return "redirect:/admin/empresas/listar";
    }

    @GetMapping("/desbloquear")
    public String desbloquear(@RequestParam("idTemporal") Integer id){
        empresaService.desbloquear(id);

        return "redirect:/admin/empresas/listar";
    }

    @GetMapping("/vendedoresEmpresa")
    public String vendedoresEmpresa(@RequestParam("idTemporal") Integer id, HttpSession session, Model model){
        recuperarAdmin(session,model);
        Set<Vendedor> listadoVendedores = empresaService.getVendedoresPorEmpresa(id);
        Empresa empresa = empresaService.getById(id);

        for(Vendedor v : listadoVendedores){
            Set<VendedorCaptado> vCaptados = empleadoService.getVendedoresCaptados(v.getId());

            if(vCaptados != null){
                v.setVendedoresCaptados(vCaptados);
            }
        }

        model.addAttribute("vendedores", listadoVendedores);
        model.addAttribute("empresa", empresa);
        return "listarVend-empresa";
    }

    @GetMapping("/asesoresEmpresa")
    public String asesoresEmpresa(@RequestParam("idTemporal") Integer id, HttpSession session, Model model){
        recuperarAdmin(session,model);
        Set<Asesor> listadoAsesores = empresaService.getAsesoresPorEmpresa(id);
        Empresa empresa = empresaService.getById(id);
        model.addAttribute("asesores", listadoAsesores);
        model.addAttribute("empresa", empresa);
        return "listarAses-empresa";
    }

    @PostMapping("/cubrirArea")
    public String cubrirArea(@RequestParam ("idTemporal") Integer idEmpresa,
                             @RequestParam ("idArea") Integer idArea){
        System.out.println("Se agrega area a empresa: "+idArea+ " " + idEmpresa);
        if(idArea!= null && idEmpresa != null){
            //recupero el objeto Empresa
            Empresa empresa = empresaService.getById(idEmpresa);

            //recupero el objeto Area
            AreasMercado area = areasMercadoService.getById(idArea);
            try{
                empresaService.agregarAreaMercado(area,empresa.getId());

            }catch (DataAccessException e){
                System.out.println(e.toString());
            }
        }

        return "redirect:/admin/empresas/listar";
    }

    @PostMapping("/quitarArea")
    public String quitarArea(@RequestParam ("idTemporal") Integer idEmpresa,
                             @RequestParam ("idArea") Integer idArea){
        System.out.println("Se quita area a empresa: "+idArea+ " " + idEmpresa);
        if(idArea!= null && idEmpresa != null){
            //recupero el objeto Empresa
            Empresa empresa = empresaService.getById(idEmpresa);

            //recupero el objeto Area
            AreasMercado area = areasMercadoService.getById(idArea);
            try{
                empresaService.quitarAreaMercado(area,empresa.getId());

            }catch (DataAccessException e){
                System.out.println(e.toString());
            }
        }

        return "redirect:/admin/empresas/listar";
    }
}
