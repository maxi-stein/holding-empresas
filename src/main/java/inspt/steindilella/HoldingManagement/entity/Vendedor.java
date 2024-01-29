package inspt.steindilella.HoldingManagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("vendedor")
public class Vendedor extends Empleado{

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "id_empresa") //ojo aca hay que usar relacion OneToMany - ManyToOne
    private String id_empresa;

    public Vendedor() {
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(String id_empresa) {
        this.id_empresa = id_empresa;
    }

    @Override
    public String toString() {
        return "Vendedor{" +
                "direccion='" + direccion + '\'' +
                ", id_empresa='" + id_empresa + '\'' +
                '}';
    }
}
