package inspt.steindilella.HoldingManagement.controller;

import inspt.steindilella.HoldingManagement.entity.Administrador;
import inspt.steindilella.HoldingManagement.service.EmpleadoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("admin/usuarios")
public class CrudEmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    private void recuperarAdmin(HttpSession session, Model model){
        Integer id = Integer.valueOf( (String) session.getAttribute("id"));

        //recupero al admin
        Administrador adm = (Administrador) empleadoService.getById(id);

        model.addAttribute("admin",adm);
    }


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
}
