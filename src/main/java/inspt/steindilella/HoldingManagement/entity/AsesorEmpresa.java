package inspt.steindilella.HoldingManagement.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "asesores_empresa")
public class AsesorEmpresa {

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
}
