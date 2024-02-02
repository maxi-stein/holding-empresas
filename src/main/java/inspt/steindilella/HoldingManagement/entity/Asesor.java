package inspt.steindilella.HoldingManagement.entity;

import jakarta.persistence.*;

import java.util.List;

@DiscriminatorValue("ases")
public class Asesor extends Empleado {

    @Column(name = "titulacion")
    private String titulacion;

    @Column(name = "direccion")
    private String direccion;

    @ManyToMany(fetch = FetchType.LAZY,
                cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable( name = "asesores_empresa",
                joinColumns = @JoinColumn(name = "id_empleado_empresa"),
                inverseJoinColumns = @JoinColumn(name = "id_empresa_relacion"))
    private List<Empresa> empresasAsesoradas;

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

    public List<Empresa> getEmpresasAsesoradas() {
        return empresasAsesoradas;
    }

    public void setEmpresasAsesoradas(List<Empresa> empresasAsesoradas) {
        this.empresasAsesoradas = empresasAsesoradas;
    }

    @Override
    public String toString() {
        return "Asesor{" +
                "titulacion='" + titulacion + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}
