package inspt.steindilella.HoldingManagement.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@DiscriminatorValue("ases")
public class Asesor extends Empleado {

    @Column(name = "titulacion")
    private String titulacion;

    @Column(name = "direccion")
    private String direccion;

    //Un asesor tiene un listado de AsesorEmpresa donde se detalla cada empresa a la que trabaja junto a su fecha de inicio
    @OneToMany(mappedBy = "asesor", cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH}) //el atributo de AsesorEmpresa
    private List<AsesorEmpresa> empresasAsesoradas;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable( name = "areas_asesoradas",
            joinColumns = @JoinColumn(name = "id_empleado_area"),
            inverseJoinColumns = @JoinColumn(name = "id_area_mercado_asesorada"))
    private List<AreasMercado> areasAsesoradas;

    public Asesor() {
    }

    public Asesor(String nombre, String apellido, String titulacion, String direccion) {
        super(nombre, apellido);
        this.titulacion = titulacion;
        this.direccion = direccion;
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

    public List<AsesorEmpresa> getEmpresasAsesoradas() {
        return empresasAsesoradas;
    }

    public void setEmpresasAsesoradas(List<AsesorEmpresa> empresasAsesoradas) {
        this.empresasAsesoradas = empresasAsesoradas;
    }

    public List<AreasMercado> getAreasAsesoradas() {
        return areasAsesoradas;
    }

    public void setAreasAsesoradas(List<AreasMercado> areasAsesoradas) {
        this.areasAsesoradas = areasAsesoradas;
    }

    @Override
    public String toString() {
        return "Asesor{" +
                "titulacion='" + titulacion + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}
