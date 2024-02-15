package inspt.steindilella.HoldingManagement.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "asesores_empresa")
public class AsesorEmpresa implements Comparable<AsesorEmpresa> {

    @EmbeddedId
    private AsesorEmpresaId id;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @MapsId("asesorId") //el nombre del atributo en AsesorEmpresaId (el que hace referencia a id_empleado_empresa)
    @JoinColumn(name = "id_empleado_empresa")
    private Asesor asesor;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @MapsId("empresaId") //el nombre del atributo en AsesorEmpresaId (el que hace referencia a id_empleado_empresa)
    @JoinColumn(name = "id_empresa_relacion")
    private Empresa empresa;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    public AsesorEmpresa(AsesorEmpresaId id, Asesor asesor, Empresa empresa, LocalDate fechaInicio) {
        this.id = id;
        this.asesor = asesor;
        this.empresa = empresa;
        this.fechaInicio = fechaInicio;
    }

    public AsesorEmpresa() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AsesorEmpresa that = (AsesorEmpresa) o;
        return asesor.equals(that.asesor) && empresa.equals(that.empresa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(asesor, empresa);
    }

    public AsesorEmpresaId getId() {
        return id;
    }

    public void setId(AsesorEmpresaId id) {
        this.id = id;
    }

    public Asesor getAsesor() {
        return asesor;
    }

    public void setAsesor(Asesor asesor) {
        this.asesor = asesor;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    @Override
    public int compareTo(AsesorEmpresa o) {
        return this.empresa.getNombre().compareTo(o.empresa.getNombre());
    }
}
