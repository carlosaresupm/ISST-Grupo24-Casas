package com.grupo24.cerraduras_casas.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.grupo24.cerraduras_casas.Model.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, String> {
    Optional<Cliente> findByDni(String dni);
    List<Cliente> findByNombre(String nombre);
    List<Cliente> findByApellido(String apellido);
    Optional<Cliente> findByEmail(String email);
    List<Cliente> findByTelefono(String telefono);
}

