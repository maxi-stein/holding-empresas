package inspt.steindilella.HoldingManagement.controller;

import inspt.steindilella.HoldingManagement.service.EmpleadoServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

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
    public String redirect(@RequestParam String id){

        var empleado = empleadoService.getById(Integer.valueOf(id));

        return empleado.despacharVista();

    }

    @GetMapping("/admin")
    public String admin(){
        return "admin";
    }

    private boolean contieneRol(ArrayList<String> roles, String rol){
       return roles.stream().anyMatch(s -> s.equals(rol));
    }

}
