package inspt.steindilella.HoldingManagement.entity;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "areas_mercado")
public class AreasMercado implements Comparable<AreasMercado> {
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
    private Set<Empresa> empresas;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable( name = "areas_asesoradas",
            joinColumns = @JoinColumn(name = "id_area_mercado_asesorada"),
            inverseJoinColumns = @JoinColumn(name = "id_empleado_area"))
    private Set<Empleado> asesores;

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

    public Set<Empresa> getEmpresas() {
        return empresas;
    }

    public Set<Empleado> getAsesores() {
        return asesores;
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

    @Override
    public int compareTo(AreasMercado o) {
        return this.nombre.compareTo(o.nombre);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AreasMercado that = (AreasMercado) o;
        return id.equals(that.id) && nombre.equals(that.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre);
    }
}
