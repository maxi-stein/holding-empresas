package inspt.steindilella.HoldingManagement.entity;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("vend")
public class Vendedor extends Empleado{

    @Column(name = "direccion")
    private String direccion;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},
    fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empresa")
    private Empresa empresa;

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


    @Override
    public String toString() {
        return "Vendedor{" +
                "direccion='" + direccion + '\'' +
                super.toString() +
                '}';
    }
}
