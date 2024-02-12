package inspt.steindilella.HoldingManagement.controller;

import inspt.steindilella.HoldingManagement.entity.Administrador;
import inspt.steindilella.HoldingManagement.entity.Asesor;
import inspt.steindilella.HoldingManagement.entity.Vendedor;
import inspt.steindilella.HoldingManagement.service.AreasMercadoService;
import inspt.steindilella.HoldingManagement.service.EmpleadoService;
import inspt.steindilella.HoldingManagement.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EmpleadoController {

    private EmpleadoService empleadoService;
    private EmpresaService empresaService;
    private AreasMercadoService areasService;



    @Autowired
    public EmpleadoController(EmpleadoService empleadoService, EmpresaService empresaService, AreasMercadoService areasService) {
        this.empleadoService = empleadoService;
        this.empresaService = empresaService;
        this.areasService = areasService;
    }


    @GetMapping("/admin")
    public String administrador(@RequestParam Integer id, Model model){

        //recupero al admin
        Administrador adm = (Administrador) empleadoService.getById(id);

        model.addAttribute("admin",adm);

        return "admin";
    }

    @GetMapping("/vendedor")
    public String vendedor(@RequestParam Integer id, Model model){
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
    public String asesor(@RequestParam Integer id, Model model){
        //recupero el asesor
        Asesor asesor = (Asesor) empleadoService.getById(id);

        System.out.println("Se mostrara el empleado: " + asesor.toString());
        model.addAttribute("asesor",asesor);
        return "asesor";
    }

}
