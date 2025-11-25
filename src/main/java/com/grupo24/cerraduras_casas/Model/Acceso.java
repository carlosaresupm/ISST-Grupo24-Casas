package com.grupo24.cerraduras_casas.Model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "ACCESOS")
public class Acceso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String clave;

    @NotEmpty
    private String cerradura;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechainicio;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechafin;

    @Column(length = 255, nullable = false)
    private String direccion;

    @ManyToOne
    @JoinColumn(name = "gestor_dni", referencedColumnName = "dni")
    private Gestor gestor;

    @ManyToOne
    @JoinColumn(name = "cliente_dni", referencedColumnName = "dni")
    private Cliente cliente;

    // Constructor vacÃ­o
    public Acceso() {
    }

    // Constructor completo
    public Acceso(Long id, String clave, String cerradura, Date fechainicio, Date fechafin, String direccion, Gestor gestor, Cliente cliente) {
        this.id = id;
        this.clave = clave;
        this.cerradura = cerradura;
        this.fechainicio = fechainicio;
        this.fechafin = fechafin;
        this.direccion = direccion;
        this.gestor = gestor;
        this.cliente = cliente;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getCerradura() {
        return cerradura;
    }

    public void setCerradura(String cerradura) {
        this.cerradura = cerradura;
    }

    public Date getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(Date fechainicio) {
        this.fechainicio = fechainicio;
    }

    public Date getFechafin() {
        return fechafin;
    }

    public void setFechafin(Date fechafin) {
        this.fechafin = fechafin;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Gestor getGestor() {
        return gestor;
    }

    public void setGestor(Gestor gestor) {
        this.gestor = gestor;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}