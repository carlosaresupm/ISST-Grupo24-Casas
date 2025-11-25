package com.grupo24.cerraduras_casas.payload.request;

import java.time.LocalDateTime;

public class AperturaRequest {

    private String clave;
    private String cerradura;
    private LocalDateTime fechainicio;
    private LocalDateTime fechafin;

    // Getters
    public String getClave() {
        return clave;
    }

    public String getCerradura() {
        return cerradura;
    }

    public LocalDateTime getFechainicio() {
        return fechainicio;
    }

    public LocalDateTime getFechafin() {
        return fechafin;
    }

    // Setters
    public void setClave(String clave) {
        this.clave = clave;
    }

    public void setCerradura(String cerradura) {
        this.cerradura = cerradura;
    }

    public void setFechainicio(LocalDateTime fechainicio) {
        this.fechainicio = fechainicio;
    }

    public void setFechafin(LocalDateTime fechafin) {
        this.fechafin = fechafin;
    }
}

