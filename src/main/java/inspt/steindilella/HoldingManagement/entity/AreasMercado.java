package inspt.steindilella.HoldingManagement.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "areas_mercado")
public class AreasMercado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "eliminado")
    private Integer eliminado;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @JoinTable( name = "areas_empresa",
            joinColumns = @JoinColumn(name = "id_area_mercado_empresa"),
            inverseJoinColumns = @JoinColumn(name = "id_empresa_mercados"))
    private List<Empresa> empresas;

    public AreasMercado() {
        eliminado=0;
    }

    public AreasMercado(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        eliminado=0;
    }

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getEliminado() {
        return eliminado;
    }

    public void setEliminado(Integer eliminado) {
        this.eliminado = eliminado;
    }

    @Override
    public String toString() {
        return "AreasMercado{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", eliminado=" + eliminado +
                '}';
    }
}
