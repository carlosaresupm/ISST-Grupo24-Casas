package com.grupo24.cerraduras_casas.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.grupo24.cerraduras_casas.Model.Acceso;
import com.grupo24.cerraduras_casas.Model.Cliente;
import com.grupo24.cerraduras_casas.Model.Gestor;

public interface AccesoRepository extends CrudRepository<Acceso, Long> {
    List<Acceso> findByCliente(Cliente cliente);
    List<Acceso> findByGestor(Gestor gestor);
    List<Acceso> findByDireccion(String direccion);
    List<Acceso> findByCerradura(String cerradura);
    Optional<Acceso> findByCerraduraAndClave(String cerradura, String clave);
}
