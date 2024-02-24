package inspt.steindilella.HoldingManagement.controller;

import inspt.steindilella.HoldingManagement.entity.Administrador;
import inspt.steindilella.HoldingManagement.entity.Credencial;
import inspt.steindilella.HoldingManagement.entity.Empleado;
import inspt.steindilella.HoldingManagement.service.AreasMercadoService;
import inspt.steindilella.HoldingManagement.service.EmpleadoService;
import inspt.steindilella.HoldingManagement.service.EmpresaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/admin/panelAdmin")
public class AdminController {

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

    @GetMapping("/listar")
    public String listarAdmin(HttpSession session, Model model){
        recuperarAdmin(session,model);
        Set<Administrador> admins = empleadoService.getAdministradores();
        model.addAttribute("admins",admins);

        return "listarAdmin";
    }

    @GetMapping("/formulario")
    public String formularioAdmin(HttpSession session,Model model){
        Administrador admin = new Administrador();
        //agregamos los datos del usuario loggeado
        recuperarAdmin(session,model);

        model.addAttribute("adminFormulario", admin);

        return "formularioAdmin";
    }

    @PostMapping("/agregar")
    public String agregarAdmin(@ModelAttribute("adminFormulario") Administrador administrador){

        administrador.setEliminado(0);

        //si el id es null, es porque estoy creando el admin
        if(administrador.getId() == null){

            empleadoService.save(administrador);
        }
        else{

            empleadoService.update(administrador);
        }

        return "redirect:/admin/panelAdmin/listar";
    }

    @GetMapping("/actualizar")
    public String actualizarAdmin(@RequestParam("idTemporal") Integer id, HttpSession session, Model model){
        Administrador admin = (Administrador) empleadoService.getById(id);
        //agregamos los datos del usuario loggeado
        recuperarAdmin(session,model);

        model.addAttribute("adminFormulario",admin);

        return "formularioAdmin";
    }

    @GetMapping("/eliminar")
    public String eliminar(@RequestParam("idTemporal") Integer id){
        empleadoService.delete(id);

        return "redirect:/admin/panelAdmin/listar";
    }

    /*@GetMapping("/accesos")
    public String accesos(@RequestParam("idTemporal") Integer id){

        return "redirect:/admin/listarAccesos";
    }*/

    @GetMapping("/listarAccesos")
    public String usuarios(HttpSession session, Model model){
        recuperarAdmin(session,model);
        //Buscamos la colección de empleados registrados
        Set<Empleado> empleadosRegistrados = empleadoService.getAll();
        Set<Empleado> usuarios = empleadoService.getUsuariosgetCredencialesAll();
        model.addAttribute("empleados", empleadosRegistrados);
        model.addAttribute("usuarios", usuarios);

        return "listar-usuarios.html";
    }

    @GetMapping("/habilitarUsuario")
    public String habilitarUsuario(@RequestParam("idTemporal") Integer id,HttpSession session, Model model){
        recuperarAdmin(session,model);
        //busco al usuario
        Empleado usuario = empleadoService.getById(id);
        Credencial credencial = new Credencial();
        credencial.setUsuario(usuario);
        model.addAttribute("idTemporal", id);
        model.addAttribute("credencial", credencial);

        return "credencial.html";
    }

    @PostMapping("/habilitarUsuario")
    public String habilitarCredencial(@RequestParam("pass") String pass,@RequestParam("idTemporal") String idUsuario){
       Empleado usuario = empleadoService.getById(Integer.valueOf(idUsuario));

       Credencial credencial = new Credencial(pass, usuario, empleadoService.getRol(usuario));
       empleadoService.savePass(credencial);

        return "redirect:/admin/panelAdmin/listarAccesos";
    }

    @GetMapping("/deshabilitarUsuario")
    public String eliminarCredencial(@RequestParam("idTemporal") String idUsuario){
        Empleado usuario = empleadoService.getById(Integer.valueOf(idUsuario));
        Credencial credencial = empleadoService.getCredencial(usuario);
        empleadoService.deletePass(credencial);

        return "redirect:/admin/panelAdmin/listarAccesos";
    }



}
