package inspt.steindilella.HoldingManagement.entity;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
@DiscriminatorValue("ases")
public class Asesor extends Empleado {

    @Column(name = "titulacion")
    private String titulacion;

    @Column(name = "direccion")
    private String direccion;

    //Un asesor tiene un listado de AsesorEmpresa donde se detalla cada empresa a la que trabaja junto a su fecha de inicio
    @OneToMany(mappedBy = "asesor", cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH}) //el atributo de AsesorEmpresa
    private Set<AsesorEmpresa> empresasAsesoradas;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable( name = "areas_asesoradas",
            joinColumns = @JoinColumn(name = "id_empleado_area"),
            inverseJoinColumns = @JoinColumn(name = "id_area_mercado_asesorada"))
    private Set<AreasMercado> areasAsesoradas;

    public Asesor() {
    }

    public Asesor(String nombre, String apellido, String titulacion, String direccion) {
        super(nombre, apellido);
        this.titulacion = titulacion;
        this.direccion = direccion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Asesor asesor = (Asesor) o;
        return titulacion.equals(asesor.titulacion) &&
                direccion.equals(asesor.direccion) &&
                super.getNombre().equals(asesor.getNombre()) &&
                super.getApellido().equals(asesor.getApellido());
    }

    @Override
    public int hashCode() {
        return Objects.hash(titulacion, direccion, super.getNombre(), super.getApellido());
    }

    public String getTitulacion() {
        return titulacion;
    }

    public void setTitulacion(String titulacion) {
        this.titulacion = titulacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Set<AsesorEmpresa> getEmpresasAsesoradas() {
        return empresasAsesoradas;
    }

    public void setEmpresasAsesoradas(Set<AsesorEmpresa> empresasAsesoradas) {
        this.empresasAsesoradas = empresasAsesoradas;
    }

    public Set<AreasMercado> getAreasAsesoradas() {
        return areasAsesoradas;
    }

    public void setAreasAsesoradas(Set<AreasMercado> areasAsesoradas) {
        this.areasAsesoradas = areasAsesoradas;
    }

    public void cubrirArea(AreasMercado area){
        areasAsesoradas.add(area);
    }

    public void quitarAreaAsesor(AreasMercado area){
        areasAsesoradas.remove(area);
    }


    public boolean esAreaSeleccionada(AreasMercado area) {
        if (areasAsesoradas != null) {
            return areasAsesoradas.contains(area);
        }
        return false;
    }

    @Override
    public String toString() {
        return "Asesor{" +
                "titulacion='" + titulacion + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }

    @Override
    public String despacharVista() {
        return "redirect:/asesor";
    }
}
