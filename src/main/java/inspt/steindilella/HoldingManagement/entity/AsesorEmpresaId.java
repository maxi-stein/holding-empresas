package inspt.steindilella.HoldingManagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class AsesorEmpresaId {

    @Column(name = "id_empresa_relacion")
    private Integer empresaId;

    @Column(name = "id_empleado_empresa")
    private Integer asesorId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AsesorEmpresaId that = (AsesorEmpresaId) o;
        return asesorId == that.asesorId && empresaId == that.empresaId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(asesorId, empresaId);
    }

    public Integer getAsesorId() {
        return asesorId;
    }

    public void setAsesorId(Integer asesorId) {
        this.asesorId = asesorId;
    }

    public Integer getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Integer empresaId) {
        this.empresaId = empresaId;
    }

    public AsesorEmpresaId(Integer empresaId, Integer asesorId) {
        this.empresaId = empresaId;
        this.asesorId = asesorId;
    }

    public AsesorEmpresaId() {
    }
}
