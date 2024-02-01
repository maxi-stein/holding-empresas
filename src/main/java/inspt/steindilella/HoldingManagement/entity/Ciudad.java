package inspt.steindilella.HoldingManagement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ciudades")
public class Ciudad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "eliminado")
    private Integer eliminado;

    //Ciudad capital referenciada en Pais
    @OneToOne(mappedBy = "capital", cascade = CascadeType.ALL) //si elimino el pais, debo eliminar la capital
    private Pais pais_capital;

    //Pais al cual la Ciudad pertenece (no-capital)
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "id_pais", referencedColumnName = "id")
    private Pais pais_ciudad;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEliminado() {
        return eliminado;
    }

    public void setEliminado(Integer eliminado) {
        this.eliminado = eliminado;
    }

    public Pais getPais_capital() {
        return pais_capital;
    }

    public void setPais_capital(Pais pais_capital) {
        this.pais_capital = pais_capital;
    }

    public Pais getPais_ciudad() {
        return pais_ciudad;
    }

    public void setPais_ciudad(Pais pais_ciudad) {
        this.pais_ciudad = pais_ciudad;
    }

    public Ciudad() {
    }

    public Ciudad(String nombre, Pais pais_ciudad) {
        this.nombre = nombre;
        this.pais_ciudad = pais_ciudad;
        eliminado=0;
    }

    @Override
    public String toString() {
        return "Ciudad{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", eliminado=" + eliminado +
                ", pais =" + pais_capital.getNombre() +
                '}';
    }
}
