package com.grupo24.cerraduras_casas.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "CERRADURAS")
public class Cerradura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 4, unique = true, nullable = false)
    private Long id;

    @NotEmpty
    private String direccion;

    @NotEmpty
    private String nombre;

    @NotEmpty
    private Boolean abierto;

    @NotEmpty
    private String clave;

    @Column(name = "device_id", nullable = false)
    private String deviceId;

    @ManyToOne
    @JoinColumn(name = "gestor_dni", referencedColumnName = "dni")
    private Gestor gestor;

    // Constructor vaci­o
    public Cerradura() {
    }

    // Constructor completo
    public Cerradura(Long id, String direccion, String nombre, Boolean abierto, String clave, String deviceId, Gestor gestor) {
        this.id = id;
        this.direccion = direccion;
        this.nombre = nombre;
        this.abierto = abierto;
        this.clave = clave;
        this.deviceId = deviceId;
        this.gestor = gestor;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getAbierto() {
        return abierto;
    }

    public void setAbierto(Boolean abierto) {
        this.abierto = abierto;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Gestor getGestor() {
        return gestor;
    }

    public void setGestor(Gestor gestor) {
        this.gestor = gestor;
    }
}