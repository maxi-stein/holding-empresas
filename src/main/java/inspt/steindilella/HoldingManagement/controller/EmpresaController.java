package inspt.steindilella.HoldingManagement.controller;

import inspt.steindilella.HoldingManagement.entity.Administrador;
import inspt.steindilella.HoldingManagement.entity.Ciudad;
import inspt.steindilella.HoldingManagement.entity.Empresa;
import inspt.steindilella.HoldingManagement.service.EmpleadoService;
import inspt.steindilella.HoldingManagement.service.EmpresaService;
import inspt.steindilella.HoldingManagement.service.UbicacionesService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("admin/empresas")
public class EmpresaController {
    private EmpresaService empresaService;
    private UbicacionesService ubicacionesService;
    private EmpleadoService empleadoService;
    @Autowired
    public EmpresaController(EmpresaService empresaService, UbicacionesService ubicacionesService, EmpleadoService empleadoService) {
        this.empresaService = empresaService;
        this.ubicacionesService = ubicacionesService;
        this.empleadoService= empleadoService;
    }

    @GetMapping("/listar")
    public String listarAreas(HttpSession session, Model model){
        Set<Empresa> set = empresaService.getAll();

        String idtemp = (String) session.getAttribute("id");
        Integer id = Integer.valueOf(idtemp);

        Administrador admin = (Administrador) empleadoService.getById(id);

        model.addAttribute("empresas",set);
        model.addAttribute("admin",admin);

        return "listar-empresas.html";
    }

    @GetMapping("/formulario")
    public String mostrarFormulario(HttpSession session, Model model){
        Empresa empresa = new Empresa();

        String idtemp = (String) session.getAttribute("id");
        Integer id = Integer.valueOf(idtemp);

        Administrador admin = (Administrador) empleadoService.getById(id);
        model.addAttribute("admin",admin);
        model.addAttribute("empresa",empresa);

        //agrego el listado de ciudades al html
        Set<Ciudad> ciudades = ubicacionesService.getAllCiudades();
        System.out.println(ciudades.size());
        model.addAttribute("ciudades", ciudades);

        return "formulario-empresa";
    }

    @PostMapping("/agregar")
    public String agregar(@Param("idTemporal") Integer id,
                          @ModelAttribute("empresa") Empresa empresa){
        System.out.println(empresa.toString());

        if(id != null){
            //si el id es null, es porque estoy creando el area
            if(empresaService.getById(id) == null){
                System.out.println("Se crea el registro de empresa");
                empresaService.save(empresa);
            }
            else{
                System.out.println("Se actualiza registro de empresa");
                empresaService.update(empresa);
            }
        }else{
            //si el id es null, el registro no existe
            System.out.println("Se crea nueva empresa: "+ empresa.getNombre());
            Empresa nuevaEmpresa = empresa;
            nuevaEmpresa.setSede(ubicacionesService.getCiudadById(empresa.getSede().getId()));
            nuevaEmpresa.setEliminado(0);
            empresaService.save(nuevaEmpresa);

        }



        return "redirect:/admin/empresas/listar";
    }

    @GetMapping("/actualizar")
    public String actualizar(@RequestParam("idTemporal") Integer id, HttpSession session, Model model){
        //Recuperamos el usuario para el nav-bar
        String idtemp = (String) session.getAttribute("id");
        Integer idAdmin = Integer.valueOf(idtemp);
        Administrador admin = (Administrador) empleadoService.getById(idAdmin);
        model.addAttribute("admin",admin);

        //agregamos a la session http la empresa a modificar
        session.setAttribute("idEmpresa", empresaService.getById(id));
        Empresa empresa = (Empresa) session.getAttribute("idEmpresa");
        model.addAttribute("empresa",empresa);
        System.out.println("Se actualiza "+empresa.toString());

        //agrego el listado de ciudades al html
        Set<Ciudad> ciudades = ubicacionesService.getAllCiudades();
        System.out.println(ciudades.size());
        model.addAttribute("ciudades", ciudades);
        model.addAttribute("selectedSedeId", empresa.getSede().getId()); // Suponiendo que empresa tiene un objeto Sede

        return "formulario-empresa";
    }

    @GetMapping("/eliminar")
    public String eliminar(@RequestParam("idTemporal") Integer id){
        empresaService.delete(id);

        return "redirect:/admin/empresas/listar";
    }
}
