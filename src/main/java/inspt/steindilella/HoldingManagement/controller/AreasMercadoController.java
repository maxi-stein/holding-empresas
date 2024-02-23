package inspt.steindilella.HoldingManagement.controller;

import inspt.steindilella.HoldingManagement.entity.Administrador;
import inspt.steindilella.HoldingManagement.entity.AreasMercado;
import inspt.steindilella.HoldingManagement.service.AreasMercadoService;
import inspt.steindilella.HoldingManagement.service.EmpleadoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("admin/areasMercado")
public class AreasMercadoController {

    private AreasMercadoService areasMercadoService;
    private EmpleadoService empleadoService;

    @Autowired
    public AreasMercadoController(AreasMercadoService areasMercadoService, EmpleadoService empleadoService) {
        this.areasMercadoService = areasMercadoService;
        this.empleadoService = empleadoService;
    }

    private void recuperarAdmin(HttpSession session, Model model){
        Integer id = Integer.valueOf( (String) session.getAttribute("id"));

        //recupero al admin
        Administrador adm = (Administrador) empleadoService.getById(id);

        model.addAttribute("admin",adm);
    }

    @GetMapping("/listar")
    public String listarAreas(HttpSession session, Model model){
        Set<AreasMercado> set = areasMercadoService.getAll();

        String idtemp = (String) session.getAttribute("id");
        Integer id = Integer.valueOf(idtemp);

        Administrador admin = (Administrador) empleadoService.getById(id);

        model.addAttribute("areas",set);
        model.addAttribute("admin",admin);

        return "listar-areasMercado.html";
    }

    @GetMapping("/formulario")
    public String mostrarFormulario(HttpSession session, Model model){
        AreasMercado area = new AreasMercado();
        recuperarAdmin(session,model);

        model.addAttribute("area",area);

        return "formulario-areasmercado";
    }

    @PostMapping("/agregar")
    public String agregar(@ModelAttribute("area") AreasMercado area){
        System.out.println(area.toString());

        //si el id es null, es porque estoy creando el area
        if(area.getId() == null){
            System.out.println("Se crea el area");
            areasMercadoService.save(area);
        }
        else{
            System.out.println("Se actuyaliza el area");
            areasMercadoService.update(area);
        }

        return "redirect:/admin/areasMercado/listar";
    }

    @GetMapping("/actualizar")
    public String actualizar(@RequestParam("idTemporal") Integer id,HttpSession session, Model model){
        AreasMercado area = areasMercadoService.getById(id);
        recuperarAdmin(session,model);

        model.addAttribute("area",area);

        return "formulario-areasmercado";
    }

    @GetMapping("/eliminar")
    public String eliminar(@RequestParam("idTemporal") Integer id){
        areasMercadoService.delete(id);

        return "redirect:/admin/areasMercado/listar";
    }
}
