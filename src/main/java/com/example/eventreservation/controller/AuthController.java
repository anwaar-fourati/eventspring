package com.example.eventreservation.controller;

import com.example.eventreservation.config.JwtService;
import com.example.eventreservation.dto.AuthRequest;
import com.example.eventreservation.model.Utilisateur;
import com.example.eventreservation.service.UtilisateurService;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")

public class AuthController {

    private final UtilisateurService utilisateurService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(
            UtilisateurService utilisateurService,
            AuthenticationManager authenticationManager,
            JwtService jwtService) {
        this.utilisateurService = utilisateurService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/connexion")
    public ResponseEntity<String> authenticate(@RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getMotDePasse()
                )
        );
        
        if (authentication.isAuthenticated()) {
            UserDetails userDetails = utilisateurService.loadUserByUsername(request.getEmail());
            String token = jwtService.generateToken(userDetails);
            return ResponseEntity.ok(token);
        }
        
        return ResponseEntity.status(401).body("Authentification échouée");
    }
    
    @PostMapping("/inscription")
    public ResponseEntity<?> inscrireUtilisateur(@RequestBody Utilisateur utilisateur) {
        try {
            Utilisateur nouvelUtilisateur = utilisateurService.inscrireUtilisateur(utilisateur);
            return ResponseEntity.ok(nouvelUtilisateur);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}