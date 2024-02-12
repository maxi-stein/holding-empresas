package inspt.steindilella.HoldingManagement.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("adm")
public class Administrador extends Empleado {

    public Administrador() {
    }

    public Administrador(String nombre, String apellido) {
        super(nombre, apellido);
    }

    @Override
    public String despacharVista() {
        return "/admin";
    }
}
