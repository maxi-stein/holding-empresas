package inspt.steindilella.HoldingManagement.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "empresas")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @OneToOne
    @JoinColumn(name = "sede_id",referencedColumnName = "id")
    private Ciudad sede;

    @Column(name = "fecha_inicio")
    private LocalDate inicio;

    @Column(name = "facturacion")
    private BigDecimal facturacion;

    @Column(name = "eliminado")
    private Integer eliminado;

    @OneToMany(mappedBy = "empresa",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            targetEntity = Vendedor.class)
    private List<Empleado> vendedores;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
                fetch = FetchType.LAZY)
    @JoinTable( name = "ciudad_empresa",
                joinColumns = @JoinColumn(name = "id_empresa_ubicada"),
                inverseJoinColumns = @JoinColumn(name = "id_ciudad_empresa"))
    private List<Ciudad> ciudades;

    public Empresa() {

    }

    public Empresa(String nombre, Ciudad sede, LocalDate inicio, BigDecimal facturacion) {
        this.nombre = nombre;
        this.sede = sede;
        this.inicio = inicio;
        this.facturacion = facturacion;
        eliminado=0;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Ciudad getSede() {
        return sede;
    }

    public void setSede(Ciudad sede) {
        this.sede = sede;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public BigDecimal getFacturacion() {
        return facturacion;
    }

    public void setFacturacion(BigDecimal facturacion) {
        this.facturacion = facturacion;
    }

    public Integer getEliminado() {
        return eliminado;
    }

    public void setEliminado(Integer eliminado) {
        this.eliminado = eliminado;
    }

    @Override
    public String toString() {
        return "Empresa{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", sede='" + sede.getNombre() + '\'' +
                ", inicio=" + inicio +
                ", facturacion='" + facturacion + '\'' +
                ", eliminado='" + eliminado + '\'' +
                '}';
    }
}
