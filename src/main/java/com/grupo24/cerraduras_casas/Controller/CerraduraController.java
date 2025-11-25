package com.grupo24.cerraduras_casas.Controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.grupo24.cerraduras_casas.Model.Cerradura;
import com.grupo24.cerraduras_casas.Model.Gestor;
import com.grupo24.cerraduras_casas.Repository.CerraduraRepository;
import com.grupo24.cerraduras_casas.Repository.GestorRepository;
import com.grupo24.cerraduras_casas.Service.TokenService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/cerraduras")
public class CerraduraController {
    private final CerraduraRepository cerraduraRepository;
    private final GestorRepository gestorRepository;
    private final TokenService tokenService;

    public static final Logger log = LoggerFactory.getLogger(CerraduraController.class);

    public CerraduraController(CerraduraRepository cerraduraRepository, GestorRepository gestorRepository, TokenService tokenService) {
        this.cerraduraRepository = cerraduraRepository;
        this.gestorRepository = gestorRepository;
        this.tokenService = tokenService;
    }

    /**
     * Obtiene el listado de todas las cerraduras (accesos), o las cerraduras filtradas por dirección.
     * @param direccion La dirección de la cerradura (opcional).
     * @return La lista de cerraduras (accesos).
     */
    @GetMapping
    public List<Cerradura> readAll(@RequestParam(name = "direccion", required = false) String direccion) {
        if (direccion != null && !direccion.isEmpty()) {
            return cerraduraRepository.findByDireccion(direccion);
        } else {
            return (List<Cerradura>) cerraduraRepository.findAll();
        }
    }

    /**
     * Crea una nueva cerradura (acceso).
     * @param newCerradura La cerradura (acceso) a crear.
     * @return La respuesta HTTP con la cerradura (acceso) creada.
     * @throws URISyntaxException Si la URI no es válida.
     */
    @PostMapping
    public ResponseEntity<Cerradura> create(@RequestBody Cerradura newCerradura) throws URISyntaxException {
        // Comprobamos si ya existe una cerradura con la misma dirección
        if (cerraduraRepository.findByDireccion(newCerradura.getDireccion()).isEmpty()) {
            
            // Generar el token a partir de la clave de la cerradura
            String tokenGenerado = tokenService.generateToken(newCerradura.getClave());
            
            // Asignar el token generado a la cerradura en lugar de la clave original
            newCerradura.setClave(tokenGenerado);
            
            // Guardamos la cerradura con el token generado
            Cerradura result = cerraduraRepository.save(newCerradura);
            
            return ResponseEntity.created(new URI("/cerraduras/" + result.getId())).body(result);
        }
        
        // Si ya existe una cerradura con la misma dirección, retornamos un conflicto
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }


    /**
     * Obtiene una cerradura (acceso) por su ID.
     * @param id El ID de la cerradura (acceso).
     * @return La cerradura (acceso) con el ID especificado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Cerradura> readOne(@PathVariable Long id) {
        Optional<Cerradura> cerradura = cerraduraRepository.findById(id);
        return cerradura.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Actualiza una cerradura (acceso) existente.
     * @param newCerradura La cerradura (acceso) con los nuevos datos.
     * @param id El ID de la cerradura (acceso) a actualizar.
     * @return La cerradura (acceso) actualizada.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Cerradura> update(@RequestBody Cerradura newCerradura, @PathVariable Long id) {
        return cerraduraRepository.findById(id).map(cerradura -> {
            cerradura.setDireccion(newCerradura.getDireccion());
            cerradura.setNombre(newCerradura.getNombre());
            cerradura.setAbierto(newCerradura.getAbierto());
            cerradura.setClave(newCerradura.getClave());
            cerradura.setDeviceId(newCerradura.getDeviceId());
            cerradura.setGestor(newCerradura.getGestor());

            // Solo codifica si la clave nueva no coincide con la ya codificada
            if (!tokenService.matches(newCerradura.getClave(), cerradura.getClave())) {
                String tokenGenerado = tokenService.generateToken(newCerradura.getClave());
                cerradura.setClave(tokenGenerado);
            }

            cerraduraRepository.save(cerradura);
            return ResponseEntity.ok().body(cerradura);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Actualiza parcialmente una cerradura (acceso) existente.
     * @param newCerradura Los datos a actualizar.
     * @param id El ID de la cerradura (acceso).
     * @return La cerradura (acceso) parcialmente actualizada.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Cerradura> partialUpdate(@RequestBody Cerradura newCerradura, @PathVariable Long id) {
        return cerraduraRepository.findById(id).map(cerradura -> {
            if (newCerradura.getDireccion() != null) cerradura.setDireccion(newCerradura.getDireccion());
            if (newCerradura.getNombre() != null) cerradura.setNombre(newCerradura.getNombre());
            if (newCerradura.getAbierto() != null) cerradura.setAbierto(newCerradura.getAbierto());
            if (newCerradura.getClave() != null) cerradura.setClave(newCerradura.getClave());
            if (newCerradura.getDeviceId() != null) cerradura.setDeviceId(newCerradura.getDeviceId());
            cerraduraRepository.save(cerradura);
            return ResponseEntity.ok().body(cerradura);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Elimina una cerradura (acceso) por su ID.
     * @param id El ID de la cerradura (acceso) a eliminar.
     * @return Respuesta de eliminación.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (cerraduraRepository.existsById(id)) {
            cerraduraRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Obtiene todas las cerraduras (accesos) de un gestor específico.
     * @param gestorDni El DNI del gestor.
     * @return Lista de cerraduras (accesos) asociadas al gestor.
     */
    @GetMapping("/gestores/{gestorDni}")
    public ResponseEntity<List<Cerradura>> getCerradurasByGestor(@PathVariable String gestorDni) {
        Optional<Gestor> gestorOpt = gestorRepository.findById(gestorDni);
        if (!gestorOpt.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Cerradura> cerraduras = cerraduraRepository.findByGestor(gestorOpt.get());
        return new ResponseEntity<>(cerraduras, HttpStatus.OK);
    }
}
