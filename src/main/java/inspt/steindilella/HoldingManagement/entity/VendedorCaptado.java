package inspt.steindilella.HoldingManagement.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "vendedores_captados")
public class VendedorCaptado implements Comparable<VendedorCaptado> {

    @EmbeddedId
    private VendedorCaptadoId id;

    @OneToOne
    @MapsId("idVendedor")
    @JoinColumn(name = "id_empleado")
    private Vendedor vendedorPadre;

    @ManyToOne
    @MapsId("idVendedorCaptado")
    @JoinColumn(name = "id_empleado_captado")
    private Vendedor vendedorCaptado;

    @Column(name = "fecha_captado")
    private LocalDate fechaCaptado;

    public VendedorCaptado() {
    }

    public VendedorCaptado(VendedorCaptadoId id, Vendedor vendedorPadre, Vendedor vendedorCaptados, LocalDate fechaCaptado) {
        this.id = id;
        this.vendedorPadre = vendedorPadre;
        this.vendedorCaptado = vendedorCaptados;
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

    public Vendedor getVendedorCaptado() {
        return vendedorCaptado;
    }

    public void setVendedorCaptado(Vendedor vendedorCaptado) {
        this.vendedorCaptado = vendedorCaptado;
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
        return id.equals(that.id) && vendedorPadre.equals(that.vendedorPadre) && vendedorCaptado.equals(that.vendedorCaptado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, vendedorPadre, vendedorCaptado);
    }

    @Override
    public int compareTo(VendedorCaptado o) {
        int comparacionVendedorPadre = this.vendedorPadre.compareTo(o.vendedorPadre);

        if (comparacionVendedorPadre != 0) {
            // Si la comparación basada en vendedorPadre no es cero, devolver ese resultado
            return comparacionVendedorPadre;
        } else {
            // Si la comparación basada en vendedorPadre es cero, comparar por vendedorCaptado
            return this.vendedorCaptado.compareTo(o.vendedorCaptado);
        }
    }
}
