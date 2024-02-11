package inspt.steindilella.HoldingManagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "empleados_seguridad")
public class Credencial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //Cada vez que creo un usuario, debo crear DESPUES su contrasenia. Esto ocurre debido a que debe existir el ID del usuario.
    @OneToOne
    @JoinColumn(name = "usuario_id",referencedColumnName = "id")
    private Empleado usuario;

    @Column(name = "password")
    private String password;

    public Credencial() {
    }

    public Credencial(String password, Empleado usuario){
        this.password = password;
        this.usuario = usuario;
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
}
