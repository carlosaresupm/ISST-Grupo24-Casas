package com.grupo24.cerraduras_casas.Controller;

import com.grupo24.cerraduras_casas.Model.EventoAcceso;
import com.grupo24.cerraduras_casas.Repository.EventoAccesoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/eventos")
public class EventoAccesoController {

    @Autowired
    private EventoAccesoRepository repository;

    @PostMapping("/registrar")
    public void registrarEvento(@RequestBody Map<String, Object> payload, Principal principal) {

        // Verificación explícita de campos requeridos
        Object idCerraduraObj = payload.get("idCerradura");
        Object dniObj = payload.get("dniCliente");

        if (idCerraduraObj == null || dniObj == null) {
            throw new IllegalArgumentException("Faltan campos obligatorios: dniCliente o idCerradura");
        }

        EventoAcceso evento = new EventoAcceso();
        evento.setDniCliente(dniObj.toString());
        evento.setIdCerradura(idCerraduraObj.toString());
        evento.setAccion(payload.getOrDefault("accion", "desconocido").toString());

        Object exitoObj = payload.get("exito");
        evento.setExito(exitoObj != null && Boolean.parseBoolean(exitoObj.toString()));

        evento.setFechaHora(LocalDateTime.now());

        repository.save(evento);
    }

    @GetMapping
    public List<EventoAcceso> listarEventos() {
        return repository.findAll();
    }
}


