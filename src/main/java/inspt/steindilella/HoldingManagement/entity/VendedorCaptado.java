package inspt.steindilella.HoldingManagement.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "vendedores_captados")
public class VendedorCaptado {

    @EmbeddedId
    private VendedorCaptadoId id;

    @ManyToOne
    @MapsId("idVendedorCaptado")
    @JoinColumn(name = "id_empleado_captado")
    private Vendedor vendedorCaptados;

    @Column(name = "fecha_captado")
    private LocalDate fechaCaptado;

    public VendedorCaptado() {
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
}
