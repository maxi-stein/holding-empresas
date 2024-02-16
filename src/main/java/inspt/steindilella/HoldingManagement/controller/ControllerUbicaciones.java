package inspt.steindilella.HoldingManagement.controller;

import inspt.steindilella.HoldingManagement.entity.Administrador;
import inspt.steindilella.HoldingManagement.entity.Ciudad;
import inspt.steindilella.HoldingManagement.entity.Pais;
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
@RequestMapping("admin/empresas/ubicaciones")
public class ControllerUbicaciones {
    EmpleadoService empleadoService;
    EmpresaService empresaService;
    UbicacionesService ubicacionesService;
    @Autowired
    public ControllerUbicaciones(EmpleadoService empleadoService, EmpresaService empresaService, UbicacionesService ubicacionesService) {
        this.empleadoService = empleadoService;
        this.empresaService = empresaService;
        this.ubicacionesService = ubicacionesService;
    }

    @GetMapping("/listar")
    public String listarAreas(HttpSession session, Model model){
        //traigo todas las ciudades registradas
        Set<Ciudad> set = ubicacionesService.getAllCiudades();
        //traigo el usuario admin
        String idtemp = (String) session.getAttribute("id");
        Integer id = Integer.valueOf(idtemp);
        Administrador admin = (Administrador) empleadoService.getById(id);
        //agrego al modelo las ciudades y paises
        model.addAttribute("ciudades",set);
        model.addAttribute("admin",admin);

        return "listar-ubicaciones.html";
    }

    @GetMapping("/formularioCiudad")
    public String mostrarFormularioCiudad(HttpSession session, Model model){
        Ciudad ciudad = new Ciudad();

        String idtemp = (String) session.getAttribute("id");
        Integer id = Integer.valueOf(idtemp);

        Administrador admin = (Administrador) empleadoService.getById(id);
        model.addAttribute("admin",admin);
        model.addAttribute("ciudad",ciudad);

        //agrego el listado de ciudades al html
        Set<Pais> paises = ubicacionesService.getAllPaises();
        model.addAttribute("paises", paises);

        return "formulario-ciudad";
    }

    @GetMapping("/formularioPais")
    public String mostrarFormularioPais(HttpSession session, Model model){
        Pais pais = new Pais();

        String idtemp = (String) session.getAttribute("id");
        Integer id = Integer.valueOf(idtemp);

        Administrador admin = (Administrador) empleadoService.getById(id);
        model.addAttribute("admin",admin);
        model.addAttribute("pais",pais);
        //agrego el listado de ciudades al html
        Set<Ciudad> ciudades = ubicacionesService.getAllCiudades();
        model.addAttribute("ciudades", ciudades);
       // model.addAttribute("selectedCiudadId", pais.getCapital().getId()); // Suponiendo que empresa tiene un objeto Sede

        return "formulario-pais";
    }

    @GetMapping("/actualizar")
    public String actualizar(@RequestParam("idTemporal") Integer id, HttpSession session, Model model){
        //Recuperamos el usuario para el nav-bar
        String idtemp = (String) session.getAttribute("id");
        Integer idAdmin = Integer.valueOf(idtemp);
        Administrador admin = (Administrador) empleadoService.getById(idAdmin);
        model.addAttribute("admin",admin);

        //agregamos a la session http la empresa a modificar
        session.setAttribute("idCiudad", ubicacionesService.getCiudadById(id));
        Ciudad ciudad = (Ciudad) session.getAttribute("idCiudad");
        model.addAttribute("ciudad",ciudad);
        //LOG
        System.out.println("Se actualiza "+ciudad.toString());

        //agrego el listado de paises al html
        Set<Pais> paises = ubicacionesService.getAllPaises();
        model.addAttribute("paises", paises);
        model.addAttribute("selectedPaisId", ciudad.getPais_ciudad().getId()); // Suponiendo que empresa tiene un objeto Sede

        return "formulario-ciudad";
    }

    @GetMapping("/actualizarPais")
    public String actualizarPais(@RequestParam("idTemporal") Integer id, HttpSession session, Model model){
        //Recuperamos el usuario para el nav-bar
        String idtemp = (String) session.getAttribute("id");
        Integer idAdmin = Integer.valueOf(idtemp);
        Administrador admin = (Administrador) empleadoService.getById(idAdmin);
        model.addAttribute("admin",admin);

        //agregamos a la session http la empresa a modificar
        session.setAttribute("idPais", ubicacionesService.getPaisById(id));
        Pais pais = (Pais) session.getAttribute("idPais");
        model.addAttribute("pais",pais);
        //LOG
        System.out.println("Se actualiza "+pais.toString());

        //agrego el listado de ciudades al html
        Set<Ciudad> ciudades = ubicacionesService.getAllCiudades();
        model.addAttribute("ciudades", ciudades);
        model.addAttribute("selectedCapitalId", pais.getCapital().getId()); // Suponiendo que empresa tiene un objeto Sede

        return "formulario-pais";
    }

    @PostMapping("/agregar")
    public String agregarCiudad(@Param("idTemporal") Integer id,
                          @ModelAttribute("ciudad") Ciudad ciudad){
       // System.out.println(ciudad.toString());
        //System.out.println(ciudad.getPais_ciudad().getId());

        //si el id es null, es porque estoy creando la ciudad
        if(id != null){
            if(ubicacionesService.getCiudadById(id) == null){
                //LOG
                System.out.println("Se crea el registro de Ciudad");
                ubicacionesService.saveCiudad(ciudad);
            }else{
                //LOG
                System.out.println("Se actualiza registro de Ciudad");
                //Para actualizar la ciudad, necesito recuperarla con el service
                Ciudad ciudadActualizada = ubicacionesService.getCiudadById(id);
                //Como el html me devuelve solo el ID del pais, recupero el pais
                Pais pais = ubicacionesService.getPaisById(ciudad.getPais_ciudad().getId());
                //seteo la ciudad que recupere con el pais:
                ciudadActualizada.setPais_ciudad(pais);
                ubicacionesService.updateCiudad(ciudadActualizada);
            }
        }else{
            //si es null es porque no esta creado
            System.out.println("Se crea nueva ciudad: "+ ciudad.getNombre());
            Ciudad nuevaCiudad = ciudad;
            nuevaCiudad.setPais_ciudad(ubicacionesService.getPaisById(ciudad.getPais_ciudad().getId()));
            nuevaCiudad.setEliminado(0);
            ubicacionesService.saveCiudad(nuevaCiudad);
        }

        return "redirect:/admin/empresas/ubicaciones/listar";
    }

    @PostMapping("/agregarPais")
    public String agregarPais(@Param("idTemporal") Integer id,
                          @ModelAttribute("pais") Pais pais){
        // System.out.println(ciudad.toString());
        //System.out.println(ciudad.getPais_ciudad().getId());

        //si el id es null, es porque estoy creando la ciudad
        if(id != null){
            if(ubicacionesService.getPaisById(id) == null){
                //LOG
                System.out.println("Se crea el registro de Pais");
                ubicacionesService.savePais(pais);
            }else{
                //LOG
                System.out.println("Se actualiza registro de Pais");
                //Para actualizar la ciudad, necesito recuperarla con el service
                Pais paisActualizado = ubicacionesService.getPaisById(id);
                //Como el html me devuelve solo el ID del pais, recupero el pais
                Ciudad ciudad = ubicacionesService.getCiudadById(pais.getCapital().getId());
                //seteo la ciudad que recupere con el pais:
                paisActualizado.setCapital(ciudad);
                ubicacionesService.updatePais(paisActualizado);
            }
        }else{
            //si es null es porque no esta creado
            System.out.println("Se crea nuevo pais: "+ pais.getNombre());
            Pais nuevoPais = pais;
            nuevoPais.setCapital(ubicacionesService.getCiudadById(pais.getCapital().getId()));
            nuevoPais.setEliminado(0);
            ubicacionesService.savePais(nuevoPais);
        }

        return "redirect:/admin/empresas/ubicaciones/listar";
    }

    @GetMapping("/eliminarPais")
    public String eliminarPais(@RequestParam("idTemporal") Integer id){
        ubicacionesService.deletePais(id);
        return "redirect:/admin/empresas/listar";
    }

    @GetMapping("/eliminarCiudad")
    public String eliminarCiudad(@RequestParam("idTemporal") Integer id){
        ubicacionesService.deleteCiudad(id);

        return "redirect:/admin/empresas/listar";
    }

}
