package com.grupo24.cerraduras_casas.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.grupo24.cerraduras_casas.Model.Gestor;
import com.grupo24.cerraduras_casas.Repository.GestorRepository;

@Service
public class GestorService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private GestorRepository gestorRepository;

    public Optional<Gestor> findByEmail(String email) {
        return gestorRepository.findByEmail(email);
    }

    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}

