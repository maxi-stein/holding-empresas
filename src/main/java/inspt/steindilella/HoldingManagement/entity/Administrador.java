package inspt.steindilella.HoldingManagement.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("adm")
public class Administrador extends Empleado {

}
