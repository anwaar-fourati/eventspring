package com.example.eventreservation.controller;

import com.example.eventreservation.model.Reservation;
import com.example.eventreservation.service.ReservationService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<Reservation> reserverPlaces(
            @RequestParam Long evenementId,
            @RequestParam Long utilisateurId,
            @RequestParam int nombrePlaces) {
        return ResponseEntity.ok(
                reservationService.reserverPlaces(evenementId, utilisateurId, nombrePlaces)
        );
    }

    @GetMapping("/utilisateur/{utilisateurId}")
    public ResponseEntity<List<Reservation>> obtenirReservationsUtilisateur(
            @PathVariable Long utilisateurId) {
        return ResponseEntity.ok(
                reservationService.obtenirReservationsParUtilisateur(utilisateurId)
        );
    }
}