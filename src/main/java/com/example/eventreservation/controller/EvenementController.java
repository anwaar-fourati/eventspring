package com.example.eventreservation.controller;

import com.example.eventreservation.model.Evenement;
import com.example.eventreservation.service.EvenementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evenements")
public class EvenementController {

    private final EvenementService evenementService;

    public EvenementController(EvenementService evenementService) {
        this.evenementService = evenementService;
    }

    @GetMapping
    public List<Evenement> obtenirEvenementsAVenir() {
        return evenementService.obtenirEvenementsAVenir();
    }

    @PostMapping
    public ResponseEntity<Evenement> creerEvenement(
            @RequestBody Evenement evenement,
            @RequestParam Long organisateurId) {
        return ResponseEntity.ok(evenementService.creerEvenement(evenement, organisateurId));
    }

    @GetMapping("/recherche")
    public List<Evenement> rechercherEvenements(@RequestParam String keyword) {
        return evenementService.rechercherEvenements(keyword);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerEvenement(
            @PathVariable Long id,
            @RequestParam Long organisateurId) {
        evenementService.supprimerEvenement(id, organisateurId);
        return ResponseEntity.noContent().build();
    }
}
