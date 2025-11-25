package com.grupo24.cerraduras_casas.Model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "GESTORES")
public class Gestor {

    @Id
    private String dni;

    @NotEmpty
    private String nombre;

    @NotEmpty
    private String apellido;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaNacimiento;

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @Pattern(regexp = "^[0-9]{9,15}$")
    @Column(unique = true, nullable = false)
    private String telefono;

    @NotEmpty
    private String password;

    // Constructor vacÃ­o
    public Gestor() {
    }

    // Constructor completo
    public Gestor(String dni, String nombre, String apellido, Date fechaNacimiento, String email, String telefono, String password) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.email = email;
        this.telefono = telefono;
        this.password = password;
    }

    // Getters y Setters

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}