package com.grupo24.cerraduras_casas.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.grupo24.cerraduras_casas.Model.Cerradura;
import com.grupo24.cerraduras_casas.Model.Gestor;

public interface CerraduraRepository extends CrudRepository<Cerradura, Long> {
    List<Cerradura> findByDireccion(String direccion);
    List<Cerradura> findByGestor(Gestor gestor);
    Optional<Cerradura> findByNombre(String nombre);
    Optional<Cerradura> findByDeviceId(String deviceId);
}
