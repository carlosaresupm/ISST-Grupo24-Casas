package com.grupo24.cerraduras_casas.Usuarios;

import com.grupo24.cerraduras_casas.Model.Cerradura;
import com.grupo24.cerraduras_casas.Model.Cliente;
import com.grupo24.cerraduras_casas.Model.Gestor;
import com.grupo24.cerraduras_casas.Model.Acceso;
import com.grupo24.cerraduras_casas.Repository.AccesoRepository;
import com.grupo24.cerraduras_casas.Repository.CerraduraRepository;
import com.grupo24.cerraduras_casas.Repository.ClienteRepository;
import com.grupo24.cerraduras_casas.Repository.GestorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class SistemaGeneralTest {

    @Autowired
    private GestorRepository gestorRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CerraduraRepository cerraduraRepository;

    @Autowired
    private AccesoRepository accesoRepository;

    @Test
    public void testCrearGestor() {
        Gestor gestor = new Gestor();
        gestor.setDni("12345678G");
        gestor.setNombre("Gestor Prueba");
        gestor.setTelefono("600000001");
        gestor.setEmail("gestor@prueba.com");

        gestorRepository.save(gestor);

        Gestor resultado = gestorRepository.findByDni("12345678G").orElse(null);
        assertThat(resultado).isNotNull();
        assertThat(resultado.getNombre()).isEqualTo("Gestor Prueba");
    }

    @Test
    public void testCrearCliente() {
        Cliente cliente = new Cliente();
        cliente.setDni("00000000C");
        cliente.setNombre("Cliente Prueba");
        cliente.setTelefono("600000002");

        clienteRepository.save(cliente);

        Cliente resultado = clienteRepository.findByDni("00000000C").orElse(null);
        assertThat(resultado).isNotNull();
        assertThat(resultado.getNombre()).isEqualTo("Cliente Prueba");
    }

    @Test
    public void testAgregarCerradura() {
        Cerradura cerradura = new Cerradura();
        cerradura.setNombre("LOCK_TEST_UNIT");
        cerradura.setDireccion("Test Street");
        cerradura.setClave("clave123");
        cerradura.setAbierto(false);
        cerradura.setDeviceId("device-123");

        cerraduraRepository.save(cerradura);

        Cerradura encontrada = cerraduraRepository.findByNombre("LOCK_TEST_UNIT").orElse(null);
        assertThat(encontrada).isNotNull();
        assertThat(encontrada.getDireccion()).isEqualTo("Test Street");
    }

    @Test
    public void testCrearAccesoValido() {
        // Crear gestor
        Gestor gestor = new Gestor();
        gestor.setDni("99999999G");
        gestor.setNombre("GestorAcceso");
        gestor.setTelefono("600000009");
        gestor.setEmail("gestor@acceso.com");
        gestorRepository.save(gestor);

        // Crear cliente
        Cliente cliente = new Cliente();
        cliente.setDni("11111111C");
        cliente.setNombre("ClienteAcceso");
        cliente.setTelefono("600000011");
        clienteRepository.save(cliente);

        // Crear cerradura
        Cerradura cerradura = new Cerradura();
        cerradura.setNombre("LOCK_ACCESO");
        cerradura.setDireccion("Calle Acceso");
        cerradura.setClave("claveAcceso");
        cerradura.setAbierto(false);
        cerradura.setDeviceId("device-acceso");
        cerradura.setGestor(gestor);
        cerraduraRepository.save(cerradura);

        // Crear acceso válido
        Acceso acceso = new Acceso();
        acceso.setCerradura("LOCK_ACCESO");
        acceso.setClave("claveAcceso");
        acceso.setDireccion("Calle Acceso");
        acceso.setCliente(cliente);
        acceso.setGestor(gestor);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, -10);
        acceso.setFechainicio(cal.getTime());

        cal.add(Calendar.MINUTE, 20);
        acceso.setFechafin(cal.getTime());

        accesoRepository.save(acceso);

        // Verificar que fue guardado correctamente
        assertThat(accesoRepository.findAll()).anyMatch(a ->
            a.getCerradura().equals("LOCK_ACCESO") &&
            a.getCliente().getDni().equals("11111111C")
        );
    }
}
