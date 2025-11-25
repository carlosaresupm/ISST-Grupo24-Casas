package com.grupo24.cerraduras_casas.Config; 

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Config implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Habilita CORS para todas las rutas y permite solicitudes solo desde localhost:3000
        registry.addMapping("/**").allowedOrigins("http://localhost:3000"); // Cambia el puerto si es necesario
    }
}
