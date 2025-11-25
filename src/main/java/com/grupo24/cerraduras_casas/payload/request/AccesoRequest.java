package com.grupo24.cerraduras_casas.payload.request;

import com.grupo24.cerraduras_casas.Model.Cliente;
import com.grupo24.cerraduras_casas.Model.Gestor;

public class AccesoRequest {

    private String clave;
    private String fechainicio;
    private String fechafin;
    private String direccion;
    private String cerradura;
    private Cliente cliente;
    private Gestor gestor;
    private String googleToken; // 🔥 El nuevo campo que necesitamos

    // Getters y Setters

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(String fechainicio) {
        this.fechainicio = fechainicio;
    }

    public String getFechafin() {
        return fechafin;
    }

    public void setFechafin(String fechafin) {
        this.fechafin = fechafin;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCerradura() {
        return cerradura;
    }

    public void setCerradura(String cerradura) {
        this.cerradura = cerradura;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Gestor getGestor() {
        return gestor;
    }

    public void setGestor(Gestor gestor) {
        this.gestor = gestor;
    }

    public String getGoogleToken() {
        return googleToken;
    }

    public void setGoogleToken(String googleToken) {
        this.googleToken = googleToken;
    }
}