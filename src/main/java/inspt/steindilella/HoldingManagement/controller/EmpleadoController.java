package inspt.steindilella.HoldingManagement.controller;

import inspt.steindilella.HoldingManagement.entity.Vendedor;
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

    @Autowired
    public EmpleadoController(EmpleadoService empleadoService, EmpresaService empresaService) {
        this.empleadoService = empleadoService;
        this.empresaService = empresaService;
    }

    @Autowired


    @GetMapping("/admin")
    public String admin(){
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

        System.out.println("Se mostrara el empleado: " + vendedor.toString());

        model.addAttribute("vendedor",vendedor);

        return "vendedor";
    }

    @GetMapping("/asesor")
    public String asesor(){
        return "asesor";
    }

}
