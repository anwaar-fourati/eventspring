package com.example.eventreservation.service;

import com.example.eventreservation.model.*;
import com.example.eventreservation.repository.*;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final EvenementRepository evenementRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final EmailService emailService;

    public ReservationService(ReservationRepository reservationRepository,
                            EvenementRepository evenementRepository,
                            UtilisateurRepository utilisateurRepository,
                            EmailService emailService) {
        this.reservationRepository = reservationRepository;
        this.evenementRepository = evenementRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.emailService = emailService;
    }

    public Reservation reserverPlaces(Long evenementId, Long utilisateurId, int nombrePlaces) {
        Evenement evenement = evenementRepository.findById(evenementId)
                .orElseThrow(() -> new RuntimeException("Événement non trouvé !"));
        
        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé !"));

        if (evenement.getPlacesDisponibles() < nombrePlaces) {
            throw new RuntimeException("Places insuffisantes !");
        }

        Reservation reservation = new Reservation();
        reservation.setEvenement(evenement);
        reservation.setUtilisateur(utilisateur);
        reservation.setNombreDePlaces(nombrePlaces);

        evenement.setPlacesDisponibles(evenement.getPlacesDisponibles() - nombrePlaces);
        evenementRepository.save(evenement);

        emailService.envoyerConfirmationReservation(utilisateur, evenement, nombrePlaces);

        return reservationRepository.save(reservation);
    }
    
    public List<Reservation> obtenirReservationsParUtilisateur(Long utilisateurId) {
        return reservationRepository.findByUtilisateurId(utilisateurId);
    }
}
