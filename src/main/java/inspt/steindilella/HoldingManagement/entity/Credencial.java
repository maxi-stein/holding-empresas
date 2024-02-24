package inspt.steindilella.HoldingManagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "empleados_seguridad")
public class Credencial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //Cada vez que creo un usuario, debo crear DESPUES su contrasenia. Esto ocurre debido a que debe existir el ID del usuario.
    @OneToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(name = "usuario_id",referencedColumnName = "id")
    private Empleado usuario;

    @Column(name = "password")
    private String password;

    private String rol;

    public Credencial() {
    }

    public Credencial(String password, Empleado usuario, String rol){
        this.password = password;
        this.usuario = usuario;
        this.rol = rol;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Empleado getUsuario() {
        return usuario;
    }

    public void setUsuario(Empleado usuario) {
        this.usuario = usuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
