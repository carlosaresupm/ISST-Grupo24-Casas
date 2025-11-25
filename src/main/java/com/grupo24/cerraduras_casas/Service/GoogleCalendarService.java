package com.grupo24.cerraduras_casas.Service;

import com.grupo24.cerraduras_casas.GoogleCalendar.GoogleApiClient;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;

@Service
public class GoogleCalendarService {

    private final GoogleApiClient googleApiClient;

    public GoogleCalendarService(GoogleApiClient googleApiClient) {
        this.googleApiClient = googleApiClient;
    }

    public void createCalendarEvent(String accessToken, String summary, Date startDate, Date endDate)
            throws GeneralSecurityException, IOException {
        // ✅ Aquí delegas la lógica real
        googleApiClient.crearEvento(accessToken, summary, startDate, endDate);
    }
}