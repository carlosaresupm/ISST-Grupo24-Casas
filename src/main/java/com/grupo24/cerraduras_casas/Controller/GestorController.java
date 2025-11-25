package com.grupo24.cerraduras_casas.Controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grupo24.cerraduras_casas.Model.Gestor;
import com.grupo24.cerraduras_casas.Repository.GestorRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/gestores")
public class GestorController {

    private final GestorRepository gestorRepository;
    public static final Logger log = LoggerFactory.getLogger(GestorController.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    public GestorController(GestorRepository gestorRepository, PasswordEncoder passwordEncoder) {
        this.gestorRepository = gestorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Obtiene el listado de todos los gestores, o los gestores filtrados por nombre.
     * @param nombre El nombre del gestor (opcional).
     * @return La lista de gestores.
     */
    @GetMapping
    public List<Gestor> readAll(@RequestParam(name = "nombre", required = false) String nombre) {
        if (nombre != null && !nombre.isEmpty()) {
            return gestorRepository.findByNombre(nombre);
        } else {
            return (List<Gestor>) gestorRepository.findAll();
        }
    }

    /**
     * Crea un nuevo gestor.
     * @param newGestor El gestor a crear.
     * @return La respuesta HTTP con el gestor creado.
     * @throws URISyntaxException Si la URI no es válida.
     */
    @PostMapping
    public ResponseEntity<Gestor> create(@RequestBody Gestor newGestor) throws URISyntaxException {
        log.info("Intentando crear gestor con DNI: {}", newGestor.getDni());

        if (newGestor.getDni() == null || newGestor.getDni().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (gestorRepository.existsById(newGestor.getDni())) {
            log.warn("Gestor con DNI {} ya existe", newGestor.getDni());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        // Cifrar la contraseña con BCrypt
        String encryptedPassword = passwordEncoder.encode(newGestor.getPassword());

        // Asignar la contraseña cifrada al gestor
        newGestor.setPassword(encryptedPassword);

        Gestor result = gestorRepository.save(newGestor);
        log.info("Gestor creado con éxito: {}", result);

        return ResponseEntity.created(new URI("/gestores/" + result.getDni())).body(result);
    }



    /**
     * Obtiene un gestor por su DNI.
     * @param dni El DNI del gestor.
     * @return El gestor con el DNI especificado.
     */
    @GetMapping("/{dni}")
    public ResponseEntity<Gestor> readOne(@PathVariable String dni) {
        Optional<Gestor> gestor = gestorRepository.findById(dni);
        return gestor.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Actualiza un gestor existente.
     * @param newGestor El gestor con los nuevos datos.
     * @param dni El DNI del gestor a actualizar.
     * @return El gestor actualizado.
     */
    @PutMapping("/{dni}")
    public ResponseEntity<Gestor> update(@RequestBody Gestor newGestor, @PathVariable String dni) {
        return gestorRepository.findById(dni).map(gestor -> {
            gestor.setNombre(newGestor.getNombre());
            gestor.setApellido(newGestor.getApellido());
            gestor.setFechaNacimiento(newGestor.getFechaNacimiento());
            gestor.setTelefono(newGestor.getTelefono());
            gestor.setEmail(newGestor.getEmail());
            gestor.setTelefono(newGestor.getTelefono());
            gestorRepository.save(gestor);
            return ResponseEntity.ok().body(gestor);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Actualiza parcialmente un gestor existente.
     * @param newGestor El gestor con los datos a actualizar.
     * @param dni El DNI del gestor.
     * @return El gestor parcialmente actualizado.
     */
    @PatchMapping("/{dni}")
    public ResponseEntity<Gestor> partialUpdate(@RequestBody Gestor newGestor, @PathVariable String dni) {
        return gestorRepository.findById(dni).map(gestor -> {
            gestor.setNombre(newGestor.getNombre());
            gestor.setApellido(newGestor.getApellido());
            gestor.setFechaNacimiento(newGestor.getFechaNacimiento());
            gestor.setTelefono(newGestor.getTelefono());
            gestor.setEmail(newGestor.getEmail());
            gestor.setTelefono(newGestor.getTelefono());
            gestorRepository.save(gestor);
            return ResponseEntity.ok().body(gestor);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Elimina un gestor por su DNI.
     * @param dni El DNI del gestor a eliminar.
     * @return Respuesta de eliminación.
     */
    @DeleteMapping("/{dni}")
    public ResponseEntity<Void> delete(@PathVariable String dni) {
        if (gestorRepository.existsById(dni)) {
            gestorRepository.deleteById(dni);
            return ResponseEntity.ok().build();
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}