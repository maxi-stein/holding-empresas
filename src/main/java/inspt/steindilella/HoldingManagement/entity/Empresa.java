package inspt.steindilella.HoldingManagement.entity;

import jakarta.persistence.*;
@Entity
@Table(name = "empresas")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Empresa {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Column(name = "nombre")
        private String nombre;

        @Column(name = "sede_id")
        private String sede;

        @Column(name = "fecha_inicio")
        private Integer inicio;

        @Column(name = "facturacion")
        private String facturacion;

        @Column(name = "eliminado")
        private String eliminado;

         public Empresa() {
        }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public Integer getInicio() {
        return inicio;
    }

    public void setInicio(Integer inicio) {
        this.inicio = inicio;
    }

    public String getFacturacion() {
        return facturacion;
    }

    public void setFacturacion(String facturacion) {
        this.facturacion = facturacion;
    }

    public String getEliminado() {
        return eliminado;
    }

    public void setEliminado(String eliminado) {
        this.eliminado = eliminado;
    }

    @Override
    public String toString() {
        return "Empresa{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", sede='" + sede + '\'' +
                ", inicio=" + inicio +
                ", facturacion='" + facturacion + '\'' +
                ", eliminado='" + eliminado + '\'' +
                '}';
    }
}
