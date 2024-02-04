package inspt.steindilella.HoldingManagement.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "vendedores_captados")
public class VendedorCaptado {

    @EmbeddedId
    private VendedorCaptadoId id;

    @OneToOne
    @MapsId("idVendedor")
    @JoinColumn(name = "id_empleado")
    private Vendedor vendedorPadre;

    @ManyToOne
    @MapsId("idVendedorCaptado")
    @JoinColumn(name = "id_empleado_captado")
    private Vendedor vendedorCaptados;

    @Column(name = "fecha_captado")
    private LocalDate fechaCaptado;

    public VendedorCaptado() {
    }

    public VendedorCaptado(VendedorCaptadoId id, Vendedor vendedorPadre, Vendedor vendedorCaptados, LocalDate fechaCaptado) {
        this.id = id;
        this.vendedorPadre = vendedorPadre;
        this.vendedorCaptados = vendedorCaptados;
        this.fechaCaptado = fechaCaptado;
    }

    public Vendedor getVendedorPadre() {
        return vendedorPadre;
    }

    public void setVendedorPadre(Vendedor vendedorPadre) {
        this.vendedorPadre = vendedorPadre;
    }

    public VendedorCaptadoId getId() {
        return id;
    }

    public void setId(VendedorCaptadoId id) {
        this.id = id;
    }

    public Vendedor getVendedorCaptados() {
        return vendedorCaptados;
    }

    public void setVendedorCaptados(Vendedor vendedorCaptados) {
        this.vendedorCaptados = vendedorCaptados;
    }

    public LocalDate getFechaCaptado() {
        return fechaCaptado;
    }

    public void setFechaCaptado(LocalDate fechaCaptado) {
        this.fechaCaptado = fechaCaptado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VendedorCaptado that = (VendedorCaptado) o;
        return id.equals(that.id) && vendedorPadre.equals(that.vendedorPadre) && vendedorCaptados.equals(that.vendedorCaptados);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, vendedorPadre, vendedorCaptados);
    }
}
