package inspt.steindilella.HoldingManagement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig{

    @Autowired
    UserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/","procesarLogin").permitAll()
                        .requestMatchers("/admin","/admin/**").hasRole("ADM")
                        .requestMatchers("/asesor","/asesor/**").hasRole("ASES")
                        .requestMatchers("/vendedor","/vendedor/**").hasRole("VEND")
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .usernameParameter("id") // Nombre del campo en el formulario
                        .passwordParameter("password") // Nombre del campo en el formulario
                        .loginProcessingUrl("/home")
                        .successForwardUrl("/redirect")
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll())
                .userDetailsService(customUserDetailsService); // Configurar el UserDetailsService personalizado
               // .passwordEncoder(passwordEncoder()); // Configurar el codificador de contraseñas
        return http.build();
    }

   /* @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("password")
                        .roles("USER")
                        .build();
        //System.out.println("!! UserDetails !!");
       //System.out.println(user.getUsername()+" "+user.getPassword());
        return new InMemoryUserDetailsManager(user);
    }*/

   @Bean
    public PasswordEncoder passwordEncoder() {
        //return new BCryptPasswordEncoder();
       return NoOpPasswordEncoder.getInstance();
   }
}

