package com.grupo24.cerraduras_casas.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.grupo24.cerraduras_casas.Model.Gestor;

public interface GestorRepository extends CrudRepository<Gestor, String> {
    List<Gestor> findByNombre(String nombre);
    List<Gestor> findByApellido(String apellido);
    Optional<Gestor> findByEmail(String email);
    Optional<Gestor> findByDni(String dni);
}

