package com.grupo24.cerraduras_casas.Cerradura;

import com.grupo24.cerraduras_casas.Model.Acceso;
import com.grupo24.cerraduras_casas.Model.Cerradura;
import com.grupo24.cerraduras_casas.Repository.AccesoRepository;
import com.grupo24.cerraduras_casas.Repository.CerraduraRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CerraduraSystemTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CerraduraRepository cerraduraRepository;

    @Autowired
    private AccesoRepository accesoRepository;

    @Test
    public void testAperturaRemotaConAccesoValido() {
        // Crear cerradura
        Cerradura cerradura = new Cerradura();
        cerradura.setNombre("LOCK_TEST");
        cerradura.setDireccion("Calle Test");
        cerradura.setClave("clave123");  // sin codificar para test directo
        cerradura.setAbierto(false);
        cerradura.setDeviceId("23f8f888-66bb-40af-b58d-319af653fc0c"); // tu deviceId real de Seam
        cerraduraRepository.save(cerradura);

        // Crear acceso válido
        Acceso acceso = new Acceso();
        acceso.setCerradura("LOCK_TEST");
        acceso.setClave("clave123");

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, -10);
        acceso.setFechainicio(cal.getTime());

        cal.add(Calendar.MINUTE, 20);
        acceso.setFechafin(cal.getTime());

        acceso.setDireccion("Calle Test");
        accesoRepository.save(acceso);

        // Simular petición de apertura
        String jsonBody = "{\"cerradura\": \"LOCK_TEST\", \"clave\": \"clave123\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
            "http://localhost:" + port + "/api/apertura/abrir", entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Cerradura updated = cerraduraRepository.findByNombre("LOCK_TEST").orElse(null);
        assertThat(updated).isNotNull();
        assertThat(updated.getAbierto()).isTrue();
    }
}
