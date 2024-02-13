package inspt.steindilella.HoldingManagement.security;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig implements WebMvcConfigurer {

    @Autowired
    UserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/","/login","/procesarLogin","/static/**","/css/**").permitAll()
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
                .headers(headers -> headers.cacheControl(cacheControl -> cacheControl.disable()))
                .userDetailsService(customUserDetailsService); // Configurar el UserDetailsService personalizado
               // .passwordEncoder(passwordEncoder()); // Configurar el codificador de contraseñas
        return http.build();
    }

    //con este metodo evito que los recursos estaticos se envien con un MIME del tipo (aplication/json) y se envien como (text/css)
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/static/css/**") //son todas las URL que voy a manejar
                .addResourceLocations("classpath:/static/")// Especifico la ubicación donde se encuentran los recursos estáticos

                .resourceChain(true)//con estos metodos cambio el MIME a text/css
                .addResolver(new PathResourceResolver() {
                    protected MediaType getMediaType(Resource resource) {
                        return MediaType.parseMediaType("text/css");
                    }
                });
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        //return new BCryptPasswordEncoder();
       return NoOpPasswordEncoder.getInstance();
   }
}

