package com.grupo24.cerraduras_casas.Repository;

import com.grupo24.cerraduras_casas.Model.EventoAcceso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoAccesoRepository extends JpaRepository<EventoAcceso, Long> {
}
