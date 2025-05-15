package com.example.eventreservation.controller;

import com.example.eventreservation.config.JwtService;
import com.example.eventreservation.model.Utilisateur;
import com.example.eventreservation.service.UtilisateurService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/inscription")
    public ResponseEntity<Utilisateur> inscrireUtilisateur(@RequestBody Utilisateur utilisateur) {
        Utilisateur nouvelUtilisateur = utilisateurService.inscrireUtilisateur(utilisateur);
        return ResponseEntity.ok(nouvelUtilisateur);
    }

    @PostMapping("/connexion")
    public ResponseEntity<String> connecterUtilisateur(
            @RequestParam String email,
            @RequestParam String motDePasse) {
        
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, motDePasse)
        );
        
        if (authentication.isAuthenticated()) {
            return ResponseEntity.ok(jwtService.generateToken(utilisateurService.loadUserByUsername(email)));
        }
        
        return ResponseEntity.status(401).body("Authentification échouée");
    }
}