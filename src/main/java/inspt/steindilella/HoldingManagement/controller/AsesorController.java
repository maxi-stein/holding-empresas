package inspt.steindilella.HoldingManagement.controller;

import inspt.steindilella.HoldingManagement.entity.*;
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
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

@Controller
@RequestMapping("/admin/asesor")
public class AsesorController {

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

    private void cargarDatosFormulario(Asesor asesor, Model model){
        //Rescato las areas asesoradas
        Set<AreasMercado> areasAsesoradas = empleadoService.getAreasAsesoradasPorAsesor(asesor.getId());

        //Obtengo las Areas de Mercado sin asesorar
        Set<AreasMercado> areas = areasMercadoService.getAll();
        Set<AreasMercado> areasSinAsesorar = new TreeSet<>();

        if(areasAsesoradas != null){
            for(AreasMercado a : areas){
                if(a.getEliminado()==0){ //agrego unicamente las areas de mercado no eliminadas
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
            }
        }

        //Obtengo todas las Empresas asesoradas y no Asesoradas
        Set<Empresa> empresas = empresaService.getAll();
        Set<AsesorEmpresa> empresasAsesoradas = empleadoService.getEmpresasAsesoradas(asesor.getId());
        Set<Empresa> empresasNoAsesoradas = new HashSet<>();

        boolean hayEmpresasAsesoradas = (empresasAsesoradas != null);

        for(Empresa e : empresas) {

            if(e.getEliminado() == 0){ //agrego unicamente las empresas no eliminadas
                boolean existe = false;

                if (hayEmpresasAsesoradas) {
                    for (AsesorEmpresa ae : empresasAsesoradas) {
                        if (Objects.equals(ae.getEmpresa().getId(), e.getId())) {
                            existe = true;
                        }
                    }
                }

                //antes de agregar al set de empresas no asesoradas, verifico que sea "asesorable" (deben tener Asesor y Empresa la misma Area de Mercado)
                if (!existe && asesor.getAreasAsesoradas() != null) { /*si estoy creando un asesor, no listo ninguna empresa disponible para asesorar*/

                    //itero las Areas de Mercado del Asesor para verificar que tambien dicha area esté en la Empresa
                    boolean asesorable = false;
                    for (AreasMercado ae : asesor.getAreasAsesoradas()) {
                        if (e.getAreasMercados().contains(ae)) {
                            asesorable = true;
                        }
                    }
                    if (asesorable) {
                        empresasNoAsesoradas.add(e);
                    }
                }
            }
        }

        model.addAttribute("asesFormulario",asesor);
        model.addAttribute("areasMercadoSinAses", areasSinAsesorar);
        model.addAttribute("areasMercadoAsesoradas", areasAsesoradas);
        model.addAttribute("empresasAsesoradas",empresasAsesoradas);
        model.addAttribute("empresasNoAsesoradas",empresasNoAsesoradas);

    }

    @GetMapping("/listar")
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

    @GetMapping("/formulario")
    public String formularioAses(HttpSession session, Model model){
        //Instancio un Asesor vacio
        Asesor asesor = new Asesor();

        cargarDatosFormulario(asesor,model);

        //agregamos los datos del usuario loggeado
        recuperarAdmin(session,model);

        return "formularioAses";
    }

    @PostMapping("/agregar")
    public String agregarAses(@ModelAttribute("asesFormulario") Asesor asesor){

        asesor.setEliminado(0);

        //si el id es null, es porque estoy creando el admin
        if(asesor.getId() == null){

            empleadoService.save(asesor);
        }
        else{

            empleadoService.update(asesor);
        }

        return "redirect:/admin/asesor/listar";
    }

    @GetMapping("/actualizar")
    public String actualizarAses(@RequestParam("idTemporal") Integer id, HttpSession session, Model model){
        //Rescato el Asesor
        Asesor asesor = (Asesor) empleadoService.getById(id);

        //agregamos los datos del usuario loggeado
        recuperarAdmin(session,model);

        cargarDatosFormulario(asesor,model);

        return "formularioAses";
    }

    @GetMapping("/eliminar")
    public String eliminarAsesor(@RequestParam("idTemporal") Integer id){

        //Obtengo las empresas que asesora el asesor a eliminar (para desvincularlo)
        Set<AsesorEmpresa> empresasAsesoradas = empleadoService.getEmpresasAsesoradas(id);

        //desvinculo al asesor de todas las empresas
        if(empresasAsesoradas != null){
            Asesor asesor = (Asesor) empleadoService.getById(id);
            for(AsesorEmpresa asesorEmpresa : empresasAsesoradas){
                empresaService.desvincularAsesor(asesor,asesorEmpresa.getEmpresa().getId());
            }
        }

        empleadoService.delete(id);

        return "redirect:/admin/asesor/listar";
    }

    @PostMapping("/eliminarAreaAsesorada/{idArea}/{idAsesor}")
    public String eliminarAreaAsesorada(@PathVariable("idArea") Integer idArea,
                                        @PathVariable("idAsesor") Integer idAsesor, Model model){

        Asesor asesor = (Asesor) empleadoService.getById(idAsesor);

        empleadoService.desvincularAreaMercado(areasMercadoService.getById(idArea),asesor.getId());

        cargarDatosFormulario(asesor,model);

        return "redirect:/admin/asesor/actualizar?idTemporal="+asesor.getId();

    }

    @PostMapping("/agregarAreaAsesorada/{idArea}/{idAsesor}")
    public String agregarAreaAsesorada(@PathVariable("idArea") Integer idArea,
                                       @PathVariable("idAsesor") Integer idAsesor, Model model){

        Asesor asesor = (Asesor) empleadoService.getById(idAsesor);

        empleadoService.cubrirAreaMercado(areasMercadoService.getById(idArea),asesor.getId());

        cargarDatosFormulario(asesor,model);

        return "redirect:/admin/asesor/actualizar?idTemporal="+asesor.getId();
    }

    @PostMapping("/agregarEmpresaAsesorada/{idEmpresa}/{idAsesor}")
    public String agregarEmpresaAsesorada(@PathVariable("idEmpresa") Integer idEmpresa, @PathVariable("idAsesor") Integer idAsesor,
                                          @RequestParam("fechaInicio")
                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio, Model model){

        Asesor asesor = (Asesor) empleadoService.getById(idAsesor);

        //creo el AsesorEmpresa
        empresaService.agregarAsesor(asesor,fechaInicio,idEmpresa);

        cargarDatosFormulario(asesor,model);

        return "redirect:/admin/asesor/actualizar?idTemporal="+asesor.getId();
    }

    @PostMapping("eliminarEmpresaAsesorada/{idEmpresa}/{idAsesor}")
    public String eliminarEmpresaAsesorada(@PathVariable("idEmpresa") Integer idEmpresa,
                                           @PathVariable("idAsesor") Integer idAsesor,Model model){

        Asesor asesor = (Asesor) empleadoService.getById(idAsesor);

        empresaService.desvincularAsesor(asesor,idEmpresa);

        cargarDatosFormulario(asesor,model);

        return "redirect:/admin/asesor/actualizar?idTemporal="+asesor.getId();
    }
}

