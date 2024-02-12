package inspt.steindilella.HoldingManagement.entity;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
@DiscriminatorValue("vend")
public class Vendedor extends Empleado{

    @Column(name = "direccion")
    private String direccion;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},
    fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empresa")
    private Empresa empresa;

    @OneToMany(mappedBy = "vendedorCaptado",
                cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Set<VendedorCaptado> vendedoresCaptados;

    public Vendedor() {
    }

    public Vendedor(String nombre, String apellido, String direccion) {
        super(nombre, apellido);
        this.direccion = direccion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public boolean tieneEmpresaAsignada(){
        return empresa != null;
    }

    public Set<VendedorCaptado> getVendedoresCaptados() {
        return vendedoresCaptados;
    }

    public void setVendedoresCaptados(Set<VendedorCaptado> vendedoresCaptados) {
        this.vendedoresCaptados = vendedoresCaptados;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Vendedor vendedor = (Vendedor) o;
        return direccion.equals(vendedor.direccion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), direccion);
    }

    @Override
    public String toString() {
        return "Vendedor{" +
                "direccion='" + direccion + '\'' +
                super.toString() +
                '}';
    }

    public void agregarVendedorCaptado(VendedorCaptado vc){
        vendedoresCaptados.add(vc);
    }

    @Override
    public String despacharVista() {
        return "redirect:/vendedor";
    }
}
