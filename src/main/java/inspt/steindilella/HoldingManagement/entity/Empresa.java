package inspt.steindilella.HoldingManagement.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

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
    private Set<Empleado> vendedores;

    //todo: cambiar de List<AsesorEmpresa> a Set<AsesorEmpresa> para evitar duplicados
    //Una empresa tiene muchos AsesorEmpresa donde se detalla cada asesor junto a su fecha inicio
    @OneToMany(mappedBy = "empresa",  cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH}) //el atributo de AsesorEmpresa
    private Set<AsesorEmpresa> asesores;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
                fetch = FetchType.LAZY)
    @JoinTable( name = "ciudad_empresa",
            joinColumns = @JoinColumn(name = "id_empresa_ubicada"),
            inverseJoinColumns = @JoinColumn(name = "id_ciudad_empresa"))
    private Set<Ciudad> ciudades;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @JoinTable( name = "areas_empresa",
            joinColumns = @JoinColumn(name = "id_empresa_mercados"),
            inverseJoinColumns = @JoinColumn(name = "id_area_mercado_empresa"))
    private Set<AreasMercado> areasMercados;

    public Empresa() {

    }

    public Empresa(String nombre, Ciudad sede, LocalDate inicio, BigDecimal facturacion) {
        this.nombre = nombre;
        this.sede = sede;
        this.inicio = inicio;
        this.facturacion = facturacion;
        eliminado=0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Empresa that = (Empresa) o;
        return Objects.equals(id, that.id) ||
                Objects.equals(nombre, that.getNombre());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre);
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

    public Set<Empleado> getVendedores() {
        return vendedores;
    }

    public Set<AsesorEmpresa> getAsesores() {
        return asesores;
    }

    public void setAsesores(Set<AsesorEmpresa> asesores) {
        this.asesores = asesores;
    }

    public Set<Ciudad> getCiudades() {
        return ciudades;
    }

    public Set<AreasMercado> getAreasMercados() {
        return areasMercados;
    }

    public void setVendedores(Set<Empleado> vendedores) {
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

    public void agregarAreaMercado(AreasMercado area){
        areasMercados.add(area);
    }

    public void quitarAreaMercado(AreasMercado area){
        areasMercados.remove(area);
    }

    public void vincularCiudadPais(Ciudad ciudad){
        ciudades.add(ciudad);
    }

    public void desvincularCiudadPais(Ciudad ciudad){
        ciudades.remove(ciudad);
    }
}
