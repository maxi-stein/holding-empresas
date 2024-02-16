package inspt.steindilella.HoldingManagement.controller;

import inspt.steindilella.HoldingManagement.entity.*;
import inspt.steindilella.HoldingManagement.service.AreasMercadoService;
import inspt.steindilella.HoldingManagement.service.EmpleadoService;
import inspt.steindilella.HoldingManagement.service.EmpresaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

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

    private void cargarDatosFormularioAdmin(Asesor asesor, Model model){
        //Rescato las areas asesoradas
        Set<AreasMercado> areasAsesoradas = empleadoService.getAreasAsesoradasPorAsesor(asesor.getId());

        //Obtengo las Areas de Mercado sin asesorar
        Set<AreasMercado> areas = areasMercadoService.getAll();
        Set<AreasMercado> areasSinAsesorar = new TreeSet<>();

        for(AreasMercado a : areas){
            boolean existe = false;
            for(AreasMercado a_aux : areasAsesoradas){
                if(a.equals(a_aux)){
                    existe = true;
                }
            }
            if(!existe){
                areasSinAsesorar.add(a);
            }
        }

        //Obtengo todas las Empresas asesoradas y no Asesoradas
        Set<Empresa> empresas = empresaService.getAll();
        Set<AsesorEmpresa> empresasAsesoradas = null;

        try{
            empresasAsesoradas = empleadoService.getEmpresasAsesoradas(asesor.getId());
        }
        catch (EmptyResultDataAccessException e){

        }

        Set<Empresa> empresasNoAsesoradas = new HashSet<>();

        if(empresasAsesoradas!=null){
            for(Empresa e : empresas){
                boolean existe = false;
                for(AsesorEmpresa ae : empresasAsesoradas){
                    if(Objects.equals(ae.getEmpresa().getId(), e.getId())){
                        existe = true;
                    }
                }
                if(!existe){
                    empresasNoAsesoradas.add(e);
                }
            }
        }

        model.addAttribute("asesFormulario",asesor);
        model.addAttribute("areasMercadoSinAses", areasSinAsesorar);
        model.addAttribute("areasMercadoAsesoradas", areasAsesoradas);
        model.addAttribute("empresasAsesoradas",empresasAsesoradas);
        model.addAttribute("empresasNoAsesoradas",empresasNoAsesoradas);

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

        cargarDatosFormularioAdmin(asesor,model);

        return "formularioAses";
    }

    @PostMapping("/agregarAses")
    public String agregarAses(@ModelAttribute("asesFormulario") Asesor asesor){

        asesor.setEliminado(0);

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

        cargarDatosFormularioAdmin(asesor,model);

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

        cargarDatosFormularioAdmin(asesor,model);

        return "redirect:/admin/usuarios/actualizarAses?idTemporal="+asesor.getId();

    }

    @PostMapping("/agregarAreaAsesorada/{idArea}/{idAsesor}")
    public String agregarAreaAsesorada(@PathVariable("idArea") Integer idArea,
                                       @PathVariable("idAsesor") Integer idAsesor, Model model){

        Asesor asesor = (Asesor) empleadoService.getById(idAsesor);

        empleadoService.cubrirAreaMercado(areasMercadoService.getById(idArea),asesor.getId());

        cargarDatosFormularioAdmin(asesor,model);

        return "redirect:/admin/usuarios/actualizarAses?idTemporal="+asesor.getId();
    }

    @PostMapping("/agregarEmpresaAsesorada/{idEmpresa}/{idAsesor}")
    public String agregarEmpresaAsesorada(@PathVariable("idEmpresa") Integer idEmpresa, @PathVariable("idAsesor") Integer idAsesor,
                                          @RequestParam("fechaInicio")
                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio, Model model){

        Asesor asesor = (Asesor) empleadoService.getById(idAsesor);

        //creo el AsesorEmpresa
        empresaService.agregarAsesor(asesor,fechaInicio,idEmpresa);

        cargarDatosFormularioAdmin(asesor,model);

        return "redirect:/admin/usuarios/actualizarAses?idTemporal="+asesor.getId();
    }

    @PostMapping("eliminarEmpresaAsesorada/{idEmpresa}/{idAsesor}")
    public String eliminarEmpresaAsesorada(@PathVariable("idEmpresa") Integer idEmpresa,
                                           @PathVariable("idAsesor") Integer idAsesor,Model model){

        Asesor asesor = (Asesor) empleadoService.getById(idAsesor);

        empresaService.desvincularAsesor(asesor,idEmpresa);

        cargarDatosFormularioAdmin(asesor,model);

        return "redirect:/admin/usuarios/actualizarAses?idTemporal="+asesor.getId();
    }



    //Metodos para el CRUD de VENDEDOR

}
