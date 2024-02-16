package inspt.steindilella.HoldingManagement.entity;

import jakarta.persistence.*;

import java.math.BigInteger;
import java.util.Set;

@Entity
@Table(name = "paises")
public class Pais {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "PBI")
    private Double pbi;

    //capital que posee el pais
    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}) //si elimino la capital, no se elimina el pais
    @JoinColumn(name = "capital_id", referencedColumnName = "id")
    private Ciudad capital;

    //Cada ciudad esta asociada a un Pais al cual pertenece.
    @OneToMany(mappedBy = "pais_ciudad",
            cascade = CascadeType.ALL)
    private Set<Ciudad> ciudad;


    @Column(name = "habitantes")
    private BigInteger habitantes;

    @Column(name = "eliminado", nullable = false)
    private Integer eliminado;

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

    public Double getPbi() {
        return pbi;
    }

    public void setPbi(Double pbi) {
        this.pbi = pbi;
    }

    public Ciudad getCapital() {
        return capital;
    }

    public void setCapital(Ciudad capital) {
        this.capital = capital;
    }

    public BigInteger getHabitantes() {
        return habitantes;
    }

    public void setHabitantes(BigInteger habitantes) {
        this.habitantes = habitantes;
    }

    public Integer getEliminado() {
        return eliminado;
    }

    public void setEliminado(Integer eliminado) {
        this.eliminado = eliminado;
    }

    public Set<Ciudad> getCiudades() {
        return ciudad;
    }

    public Pais() {
    }

    public Pais(String nombre, Double pbi, BigInteger habitantes) {
        this.nombre = nombre;
        this.pbi = pbi;
        this.habitantes = habitantes;
        eliminado=0;
    }

    @Override
    public String toString() {
        return "Pais{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", pbi=" + pbi +
                ", capital=" + capital +
                ", habitantes=" + habitantes +
                ", eliminado=" + eliminado +
                '}';
    }
}
