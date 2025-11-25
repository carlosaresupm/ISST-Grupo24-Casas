
package com.grupo24.cerraduras_casas.Cerradura;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AbrirCerraduraUITest {

    private static WebDriver driver;
    private static final String CERRADURA_NOMBRE = "LOCK_TEST";
    private static final String CLAVE = "clave123";
    private static final String CLIENTE_DNI = "00000000Y";

    @BeforeAll
    public static void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> cerradura = new HashMap<>();
        cerradura.put("nombre", CERRADURA_NOMBRE);
        cerradura.put("direccion", "Calle Selenium");
        cerradura.put("abierto", false);
        cerradura.put("clave", CLAVE);
        Map<String, String> gestor = new HashMap<>();
        gestor.put("dni", "12345678A");
        cerradura.put("gestor", gestor);
        cerradura.put("deviceId", "23f8f888-66bb-40af-b58d-319af653fc0c");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> cerraduraEntity = new HttpEntity<>(cerradura, headers);
        try {
            restTemplate.postForEntity("http://localhost:8080/api/cerraduras", cerraduraEntity, String.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() != HttpStatus.CONFLICT) throw e;
        }

        Map<String, Object> acceso = new HashMap<>();
        acceso.put("cerradura", CERRADURA_NOMBRE);
        acceso.put("clave", CLAVE);
        acceso.put("direccion", "Calle Selenium");

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, -10);
        acceso.put("fechainicio", cal.getTime());
        cal.add(Calendar.MINUTE, 20);
        acceso.put("fechafin", cal.getTime());

        Map<String, String> cliente = new HashMap<>();
        cliente.put("dni", CLIENTE_DNI);
        acceso.put("cliente", cliente);
        acceso.put("gestor", gestor);

        HttpEntity<Map<String, Object>> accesoEntity = new HttpEntity<>(acceso, headers);
        try {
            restTemplate.postForEntity("http://localhost:8080/api/accesos", accesoEntity, String.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() != HttpStatus.CONFLICT) throw e;
        }
    }

    @Test
    @Order(1)
    public void testAbrirCerraduraDesdeFrontend() {
        driver.get("http://localhost:3000/Cliente/Login");
    
        driver.findElement(By.name("email")).sendKeys("prueba@gmail.com");
        driver.findElement(By.name("password")).sendKeys("clave123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
    
        new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.urlContains("/HomeUser"));
    
        driver.get("http://localhost:3000/HomeUser/AccessDetails/53");
    
        WebElement botonAbrir = new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.elementToBeClickable(By.className("open-button")));
        botonAbrir.click();
    
        // ✅ Aceptar el alert primero
        Alert alert = new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        alert.accept();
        assertThat(alertText).contains("✅");
    
        // ✅ Esperar después al mensaje visual
        WebElement mensaje = new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.visibilityOfElementLocated(By.className("resultado-operacion")));
        assertThat(mensaje.getText().toLowerCase()).contains("cerradura");
    }

}
