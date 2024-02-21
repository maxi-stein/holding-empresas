package inspt.steindilella.HoldingManagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable

public class VendedorCaptadoId {

    @Column(name = "id_empleado")
    private Integer idVendedor;

    @Column(name = "id_empleado_captado")
    private Integer idVendedorCaptado;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VendedorCaptadoId that = (VendedorCaptadoId) o;
        return Objects.equals(idVendedor, that.idVendedor) && Objects.equals(idVendedorCaptado, that.idVendedorCaptado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idVendedor, idVendedorCaptado);
    }

    public VendedorCaptadoId() {
    }

    public VendedorCaptadoId(Integer idVendedor, Integer idVendedorCaptado) {
        this.idVendedor = idVendedor;
        this.idVendedorCaptado = idVendedorCaptado;
    }

    public Integer getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(Integer idVendedor) {
        this.idVendedor = idVendedor;
    }

    public Integer getIdVendedorCaptado() {
        return idVendedorCaptado;
    }

    public void setIdVendedorCaptado(Integer idVendedorCaptado) {
        this.idVendedorCaptado = idVendedorCaptado;
    }

    @Override
    public String toString() {
        return idVendedor + "_" + idVendedorCaptado;
    }
}
