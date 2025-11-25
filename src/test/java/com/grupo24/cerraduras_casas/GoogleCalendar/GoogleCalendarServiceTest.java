package com.grupo24.cerraduras_casas.GoogleCalendar;

import com.grupo24.cerraduras_casas.Service.GoogleCalendarService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
public class GoogleCalendarServiceTest {

    @Mock
    private GoogleApiClient googleApiClient; // ✅ Aquí simulas la dependencia

    @InjectMocks
    private GoogleCalendarService googleCalendarService;

    @Test
    public void testCreateCalendarEvent_noExcepcion() throws Exception{
        // Simulamos los datos de entrada
        String fakeToken = "fake-token";
        String summary = "Test event";
        Calendar cal = Calendar.getInstance();
        Date start = cal.getTime();
        cal.add(Calendar.HOUR, 1);
        Date end = cal.getTime();

        // 🟡 Simula que no hace nada cuando se llama
        Mockito.doNothing().when(googleApiClient)
                .crearEvento(fakeToken, summary, start, end);

        // Esta prueba solo asegura que tu método no lanza excepciones con datos válidos
        assertDoesNotThrow(() -> {
            googleCalendarService.createCalendarEvent(fakeToken, summary, start, end);
        });
    }
}
