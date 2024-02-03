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

    //Vendedores de la empresa
    @OneToMany(mappedBy = "empresa",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            targetEntity = Vendedor.class)
    private List<Empleado> vendedores;

    //todo: cambiar de List<AsesorEmpresa> a Set<AsesorEmpresa> para evitar duplicados
    //Una empresa tiene muchos AsesorEmpresa donde se detalla cada asesor junto a su fecha inicio
    @OneToMany(mappedBy = "empresa",  cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH}) //el atributo de AsesorEmpresa
    private List<AsesorEmpresa> asesores;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
                fetch = FetchType.LAZY)
    @JoinTable( name = "asesores_empresa",
            joinColumns = @JoinColumn(name = "id_empleado_empresa"),
            inverseJoinColumns = @JoinColumn(name = "id_empresa_relacion"))
    private List<Ciudad> ciudades;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @JoinTable( name = "areas_empresa",
            joinColumns = @JoinColumn(name = "id_empresa_mercados"),
            inverseJoinColumns = @JoinColumn(name = "id_area_mercado_empresa"))
    private List<AreasMercado> areasMercados;

    public Empresa() {

    }

    public Empresa(String nombre, Ciudad sede, LocalDate inicio, BigDecimal facturacion) {
        this.nombre = nombre;
        this.sede = sede;
        this.inicio = inicio;
        this.facturacion = facturacion;
        eliminado=0;
    }

    public Integer getId() {
        return id;
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

    public List<Empleado> getVendedores() {
        return vendedores;
    }

    public List<AsesorEmpresa> getAsesores() {
        return asesores;
    }

    public void setAsesores(List<AsesorEmpresa> asesores) {
        this.asesores = asesores;
    }

    public List<Ciudad> getCiudades() {
        return ciudades;
    }

    public List<AreasMercado> getAreasMercados() {
        return areasMercados;
    }

    public void setVendedores(List<Empleado> vendedores) {
        this.vendedores = vendedores;
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

    public void agregarVendedor(Vendedor vendedor){
        //le cargo la empresa al vendedor
        vendedor.setEmpresa(this);

        //agrego el vendedor al listado de vendedores
        vendedores.add(vendedor);
    }

    public void desvincularVendedor(Vendedor vendedor){
        //desvinculo al vendedor de la empresa
        vendedor.setEmpresa(null);

        //elimino al vendedor del listado de vendedores
        vendedores.remove(vendedor);
    }

    public void agregarAsesor(AsesorEmpresa asesor){
        asesores.add(asesor);
    }

    public void desvincularAsesor(AsesorEmpresa asesor){
        asesores.remove(asesor);
    }
}
