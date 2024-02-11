package inspt.steindilella.HoldingManagement.security;

import inspt.steindilella.HoldingManagement.entity.Empleado;
import inspt.steindilella.HoldingManagement.service.EmpleadoServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private EmpleadoServiceInterface userRepository;

    public UserDetailsService(EmpleadoServiceInterface userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        //valido el parametro
        if (StringUtils.isEmpty(id)) {
            throw new UsernameNotFoundException("Nombre de usuario no válido");
        }
        // Cargar el usuario desde la base de datos (usando el UserRepository)
        Empleado user = userRepository.getById(Integer.valueOf(id));

        if (user == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con el ID: " + id);
        }
        // Devolver un UserDetails que Spring Security pueda utilizar para la autenticación
        return org.springframework.security.core.userdetails.User
                .withUsername(String.valueOf(user.getId()))
                .password(userRepository.getPass(user))
                .roles(userRepository.getRol(user))
                .build();
    }
}

