package com.grupo24.cerraduras_casas.Service;

import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

@Service
public class TokenService {

    private static final String SECRET_KEY = "35t435un4cl4v353cr3t45up3rs3gur4"; // Cambiar por una más segura

    public String generateToken(String input) {
        try {
            // Algoritmo HMAC-SHA256
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "HmacSHA256");
            mac.init(secretKey);

            // Generar el hash
            byte[] hash = mac.doFinal(input.getBytes());

            // Convertir a Base64 para que sea legible
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el token", e);
        }
    }

    public boolean matches(String rawClave, String encodedClave) {
        String generated = generateToken(rawClave);
        return generated.equals(encodedClave);
    }
}
