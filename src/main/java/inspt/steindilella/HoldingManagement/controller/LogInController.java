package inspt.steindilella.HoldingManagement.controller;

import inspt.steindilella.HoldingManagement.entity.Empleado;
import inspt.steindilella.HoldingManagement.service.EmpleadoServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LogInController {

    EmpleadoServiceInterface empleadoService;

    @Autowired
    public LogInController(EmpleadoServiceInterface empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping("/")
    public String redirectLogin(){
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/redirect")
    public String redirect(@RequestParam String id, Model model){

        Empleado empleado = empleadoService.getById(Integer.valueOf(id));

        //despacho la vista con la interface
        return empleado.despacharVista() + "?id=" + id;

    }

}
