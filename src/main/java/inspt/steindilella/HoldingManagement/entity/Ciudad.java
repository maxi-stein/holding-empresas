package inspt.steindilella.HoldingManagement.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "ciudades")
public class Ciudad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "eliminado", nullable = false)
    private Integer eliminado;

    //Pais al cual la Ciudad pertenece (no-capital)
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "id_pais", referencedColumnName = "id")
    private Pais pais_ciudad;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @JoinTable(name = "ciudad_empresa",
            joinColumns = @JoinColumn(name = "id_ciudad_empresa"),
            inverseJoinColumns = @JoinColumn(name = "id_empresa_ubicada"))
    private Set<Empresa> empresas;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEliminado() {
        return eliminado;
    }

    public void setEliminado(Integer eliminado) {
        this.eliminado = eliminado;
    }



    public Pais getPais_ciudad() {
        return pais_ciudad;
    }

    public void setPais_ciudad(Pais pais_ciudad) {
        this.pais_ciudad = pais_ciudad;
    }

    public Ciudad() {
    }

    public Ciudad(String nombre, Pais pais_ciudad) {
        this.nombre = nombre;
        this.pais_ciudad = pais_ciudad;
        eliminado=0;
    }

    @Override
    public String toString() {
        return "Ciudad{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", eliminado=" + eliminado +
                ", pais =" + pais_ciudad.getNombre() +
                '}';
    }
}
