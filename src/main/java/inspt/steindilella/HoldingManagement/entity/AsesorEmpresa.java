package inspt.steindilella.HoldingManagement.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "asesores_empresa")
public class AsesorEmpresa {

    @EmbeddedId
    private AsesorEmpresaId id;

    @ManyToOne
    @MapsId("asesorId") //el nombre del atributo en AsesorEmpresaId (el que hace referencia a id_empleado_empresa)
    @JoinColumn(name = "id_empleado_empresa")
    private Empleado asesor;

    @ManyToOne
    @MapsId("empresaId") //el nombre del atributo en AsesorEmpresaId (el que hace referencia a id_empleado_empresa)
    @JoinColumn(name = "id_empresa_relacion")
    private Empresa empresa;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    public AsesorEmpresa(Empleado asesor, Empresa empresa, LocalDate fechaInicio) {
        this.asesor = asesor;
        this.empresa = empresa;
        this.fechaInicio = fechaInicio;
    }

    public AsesorEmpresa(LocalDate fechaInicio) {
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

    public Empleado getAsesor() {
        return asesor;
    }

    public void setAsesor(Empleado asesor) {
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
