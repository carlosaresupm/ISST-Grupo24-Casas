package com.grupo24.cerraduras_casas.GoogleCalendar;

import com.grupo24.cerraduras_casas.Service.GoogleCalendarService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Date;

@SpringBootTest
public class GoogleCalendarIntegrationTest {

    @Autowired
    private GoogleCalendarService googleCalendarService;

    @Test
    public void testCreateRealGoogleEvent() throws Exception {
        // ⚠ Copia aquí tu token real temporal desde localStorage (solo para pruebas)
        

        String summary = "🧪 Evento real desde prueba automatizada";
        Calendar cal = Calendar.getInstance();
        Date start = cal.getTime();
        cal.add(Calendar.HOUR, 1);
        Date end = cal.getTime();

        googleCalendarService.createCalendarEvent(realToken, summary, start, end);

        System.out.println("✅ Evento creado correctamente en Google Calendar.");
    }
}