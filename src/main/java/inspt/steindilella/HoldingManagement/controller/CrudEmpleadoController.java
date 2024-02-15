package inspt.steindilella.HoldingManagement.controller;

import inspt.steindilella.HoldingManagement.entity.Administrador;
import inspt.steindilella.HoldingManagement.entity.AreasMercado;
import inspt.steindilella.HoldingManagement.entity.Asesor;
import inspt.steindilella.HoldingManagement.entity.Empresa;
import inspt.steindilella.HoldingManagement.service.AreasMercadoService;
import inspt.steindilella.HoldingManagement.service.EmpleadoService;
import inspt.steindilella.HoldingManagement.service.EmpresaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/usuarios")
public class CrudEmpleadoController {

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


    //Metodos para el CRUD de ADMINISTRADOR

    @GetMapping("/listarAdmin")
    public String listarAdmin(HttpSession session, Model model){
        recuperarAdmin(session,model);
        Set<Administrador> admins = empleadoService.getAdministradores();
        model.addAttribute("admins",admins);

        return "listarAdmin";
    }

    @GetMapping("/formularioAdmin")
    public String formularioAdmin(Model model){
        Administrador admin = new Administrador();

        model.addAttribute("adminFormulario", admin);

        return "formularioAdmin";
    }

    @PostMapping("/agregarAdmin")
    public String agregarAdmin(@ModelAttribute("adminFormulario") Administrador administrador){

        administrador.setEliminado(0);

        //si el id es null, es porque estoy creando el admin
        if(administrador.getId() == null){

            empleadoService.save(administrador);
        }
        else{

            empleadoService.update(administrador);
        }

        return "redirect:/admin/usuarios/listarAdmin";
    }

    @GetMapping("/actualizarAdmin")
    public String actualizarAdmin(@RequestParam("idTemporal") Integer id, Model model){
        Administrador admin = (Administrador) empleadoService.getById(id);

        model.addAttribute("adminFormulario",admin);

        return "formularioAdmin";
    }

    @GetMapping("/eliminarAdmin")
    public String eliminar(@RequestParam("idTemporal") Integer id){
        empleadoService.delete(id);

        return "redirect:/admin/usuarios/listarAdmin";
    }

    //Metodos para el CRUD de ASESOR

    private void cargarDatosFormulario(Asesor asesor, Model model){
        //Rescato las areas asesoradas
        Set<AreasMercado> areasAsesoradas = empleadoService.getAreasAsesoradasPorAsesor(asesor.getId());

        //Obtengo las Areas de Mercado sin asesorar
        Set<AreasMercado> areas = areasMercadoService.getAll();
        Set<AreasMercado> areasSinAsesorar = areas.stream()
                .filter(area -> !areasAsesoradas.contains(area))
                .collect(Collectors.toSet());

        //Obtengo todas las Empresas
        Set<Empresa> empresas = empresaService.getAll();

        model.addAttribute("asesFormulario",asesor);
        model.addAttribute("areasMercadoSinAses", areasSinAsesorar);
        model.addAttribute("areasMercadoAsesoradas", areasAsesoradas);
        model.addAttribute("empresas",empresas);
    }

    @GetMapping("/listarAses")
    public String listarAses(HttpSession session, Model model){
        recuperarAdmin(session,model);
        Set<Asesor> asesores = empleadoService.getAsesores();

        for(Asesor a : asesores){
            a.setAreasAsesoradas(empleadoService.getAreasAsesoradasPorAsesor(a.getId()));
            a.setEmpresasAsesoradas(empleadoService.getEmpresasAsesoradas(a.getId()));
        }

        model.addAttribute("asesores",asesores);

        return "listarAses";
    }

    @GetMapping("/formularioAses")
    public String formularioAses(Model model){
        //Instancio un Asesor vacio
        Asesor asesor = new Asesor();

        cargarDatosFormulario(asesor,model);

        return "formularioAses";
    }

    @PostMapping("/agregarAses")
    public String agregarAses(@ModelAttribute("asesFormulario") Asesor asesor,
                              @ModelAttribute("idAreas") String[] idAreas){

        asesor.setEliminado(0);

        //Transformo el Array de IDs de AreasMercado que llega de Thymeleaf al Set de areas asesoradas
        Set<AreasMercado> areas = new TreeSet<>();

        for(String a : idAreas){
            areas.add(areasMercadoService.getById(Integer.valueOf(a)));
        }

        asesor.setAreasAsesoradas(areas);

        //si el id es null, es porque estoy creando el admin
        if(asesor.getId() == null){

            empleadoService.save(asesor);
        }
        else{

            empleadoService.update(asesor);
        }

        return "redirect:/admin/usuarios/listarAses";
    }

    @GetMapping("/actualizarAses")
    public String actualizarAses(@RequestParam("idTemporal") Integer id, Model model){
        //Rescato el Asesor
        Asesor asesor = (Asesor) empleadoService.getById(id);

        cargarDatosFormulario(asesor,model);

        return "formularioAses";
    }

    @GetMapping("/eliminarAses")
    public String eliminarAsesor(@RequestParam("idTemporal") Integer id){
        empleadoService.delete(id);

        return "redirect:/admin/usuarios/listarAses";
    }

    @PostMapping("/eliminarAreaAsesorada/{idArea}/{idAsesor}")
    public String eliminarAreaAsesorada(@PathVariable("idArea") Integer idArea,
                                        @PathVariable("idAsesor") Integer idAsesor, Model model){

        Asesor asesor = (Asesor) empleadoService.getById(idAsesor);

        empleadoService.desvincularAreaMercado(areasMercadoService.getById(idArea),asesor.getId());

        cargarDatosFormulario(asesor,model);

        return "redirect:/admin/usuarios/actualizarAses?idTemporal="+asesor.getId();

    }

    @PostMapping("/agregarAreaAsesorada/{idArea}/{idAsesor}")
    public String agregarAreaAsesorada(@PathVariable("idArea") Integer idArea,
                                       @PathVariable("idAsesor") Integer idAsesor, Model model){

        Asesor asesor = (Asesor) empleadoService.getById(idAsesor);

        empleadoService.cubrirAreaMercado(areasMercadoService.getById(idArea),asesor.getId());

        cargarDatosFormulario(asesor,model);

        return "redirect:/admin/usuarios/actualizarAses?idTemporal="+asesor.getId();
    }

    //Metodos para el CRUD de VENDEDOR

}
