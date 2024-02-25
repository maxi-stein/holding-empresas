package inspt.steindilella.HoldingManagement.controller;

import inspt.steindilella.HoldingManagement.entity.*;
import inspt.steindilella.HoldingManagement.service.AreasMercadoService;
import inspt.steindilella.HoldingManagement.service.EmpleadoService;
import inspt.steindilella.HoldingManagement.service.EmpresaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EmpleadoController {

    private EmpleadoService empleadoService;
    private EmpresaService empresaService;

    @Autowired
    public EmpleadoController(EmpleadoService empleadoService, EmpresaService empresaService, AreasMercadoService areasService) {
        this.empleadoService = empleadoService;
        this.empresaService = empresaService;
    }


    @GetMapping("/admin")
    public String administrador(Model model, HttpSession session){

        Integer id = Integer.valueOf( (String) session.getAttribute("id"));

        //recupero al admin
        Administrador adm = (Administrador) empleadoService.getById(id);

        model.addAttribute("admin",adm);

        return "admin";
    }

    @GetMapping("/vendedor")
    public String vendedor(Model model, HttpSession session){

        Integer id = Integer.valueOf( (String) session.getAttribute("id"));

        //recupero el vendedor
        Vendedor vendedor = (Vendedor) empleadoService.getById(id);

        //rescato la empresa del vendedor
        vendedor.setEmpresa(empresaService.getEmpresaByVendedorId(id));

        //rescato los vendedores captados del vendedor
        vendedor.setVendedoresCaptados(empleadoService.getVendedoresCaptados(id));

        model.addAttribute("vendedor",vendedor);

        return "vendedor";
    }

    @GetMapping("/asesor")
    public String asesor(Model model, HttpSession session){

        Integer id = Integer.valueOf( (String) session.getAttribute("id"));

        //recupero el asesor
        Asesor asesor = (Asesor) empleadoService.getById(id);

        System.out.println("Se mostrara el empleado: " + asesor.toString());
        model.addAttribute("asesor",asesor);
        return "asesor";
    }

    private void recuperarAdmin(HttpSession session, Model model){
        Integer id = Integer.valueOf( (String) session.getAttribute("id"));

        //recupero al usuario
        Empleado adm = empleadoService.getById(id);

        model.addAttribute("admin",adm);
    }
    @GetMapping("/cambiarPass")
    public String cambiarPass(HttpSession session, Model model){
        recuperarAdmin(session,model);
        Credencial credencial = new Credencial();
        credencial.setUsuario(empleadoService.getById(Integer.valueOf( (String) session.getAttribute("id"))));
        model.addAttribute("credencial", credencial);

        return "credencial-actualiza.html";
    }

    @PostMapping("/cambiarPass")
    public String cambiarPass(@RequestParam("pass") String pass, HttpSession session){
        Empleado usuario = empleadoService.getById(Integer.valueOf( (String) session.getAttribute("id")));

        Credencial credencial = empleadoService.getCredencial(usuario);
        credencial.setPassword(pass);
        empleadoService.updatePass(credencial);
        if (usuario instanceof Administrador) {
            return "redirect:/admin";
        }

        if (usuario instanceof Vendedor) {
            return "redirect:/vendedor";
        }

        if (usuario instanceof Asesor) {
            return "redirect:/asesor";
        }
        return "redirect:/";
    }

    @GetMapping("/cancelaProceso")
    public String cancelaProceso(HttpSession session){
       //buscamos al usuario para reconocer la vista
        Empleado usuario = empleadoService.getById(Integer.valueOf( (String) session.getAttribute("id")));

        if (usuario instanceof Administrador) {
            return "redirect:/admin";
        }

        if (usuario instanceof Vendedor) {
            return "redirect:/vendedor";
        }

        if (usuario instanceof Asesor) {
            return "redirect:/asesor";
        }
        return "redirect:/";
    }

}
