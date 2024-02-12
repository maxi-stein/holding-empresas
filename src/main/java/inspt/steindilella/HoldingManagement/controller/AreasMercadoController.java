package inspt.steindilella.HoldingManagement.controller;

import inspt.steindilella.HoldingManagement.entity.Administrador;
import inspt.steindilella.HoldingManagement.entity.AreasMercado;
import inspt.steindilella.HoldingManagement.service.AreasMercadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("admin/areasMercado")
public class AreasMercadoController {

    private AreasMercadoService areasMercadoService;

    @Autowired
    public AreasMercadoController(AreasMercadoService areasMercadoService) {
        this.areasMercadoService = areasMercadoService;
    }

    @GetMapping("/listar")
    public String listarAreas(@ModelAttribute("admin") Administrador admin, Model model){
        Set<AreasMercado> set = areasMercadoService.getAll();

        model.addAttribute("areas",set);
        model.addAttribute("admin",admin);

        return "listar-areasMercado.html";
    }

    @GetMapping("/formulario")
    public String mostrarFormulario(Model model){
        AreasMercado area = new AreasMercado();

        model.addAttribute("area",area);

        return "formulario-areasmercado";
    }

    @PostMapping("/agregar")
    public String agregar(@ModelAttribute("area") AreasMercado area){

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
    public String actualizar(@RequestParam("idTemporal") Integer id,Model model){
        AreasMercado area = areasMercadoService.getById(id);

        model.addAttribute("area",area);

        return "formulario-areasmercado";
    }


    @GetMapping("/eliminar")
    public String eliminar(@RequestParam("idTemporal") Integer id){
        areasMercadoService.delete(id);

        return "redirect:/admin/areasMercado/listar";
    }
}
