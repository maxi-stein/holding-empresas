package inspt.steindilella.HoldingManagement.controller;

import inspt.steindilella.HoldingManagement.security.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LogInController {

    UserDetailsService userDetailsService;

    private Map<String, String> redirectUrl;

    @Autowired
    public LogInController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
        redirectUrl = new HashMap<>();
        redirectUrl.put("ROLE_ADM","/admin");
        redirectUrl.put("ROLE_ASE","/asesor");
        redirectUrl.put("ROLE_VEND","/vendedor");

    }

    @GetMapping("/")
    public String redirectLogin(){
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/home")
    public String procesarLogin(@RequestParam String id){

        UserDetails user = userDetailsService.loadUserByUsername(id);

        Collection<? extends GrantedAuthority> grantedAuthority = user.getAuthorities();

        ArrayList<String> roles = new ArrayList<>();

        for(GrantedAuthority authority : grantedAuthority){
            roles.add(authority.getAuthority());
            System.out.println("Se agrega: " + authority.getAuthority());
        }

        if(contieneRol(roles,"ROLE_ADM")){
            return "redirect:/admin";
        }

        if(contieneRol(roles,"ROLE_ASE")){
            return "redirect:/asesor";
        }

        if(contieneRol(roles,"ROLE_VEND")){
            return  "redirect:/vendedor";
        }

        else{
            return "login";
        }
    }

    @GetMapping("/admin")
    public String admin(){
        return "admin";
    }

    private boolean contieneRol(ArrayList<String> roles, String rol){
       return roles.stream().anyMatch(s -> s.equals(rol));
    }

}
