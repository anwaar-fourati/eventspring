package com.example.eventreservation.service;

import com.example.eventreservation.model.Evenement;
import com.example.eventreservation.model.Utilisateur;
import com.example.eventreservation.repository.EvenementRepository;
import com.example.eventreservation.repository.UtilisateurRepository;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EvenementService {

    private final EvenementRepository evenementRepository;
    private final UtilisateurRepository utilisateurRepository;
    public EvenementService(EvenementRepository evenementRepository, UtilisateurRepository utilisateurRepository) {
        this.evenementRepository = evenementRepository;
        this.utilisateurRepository = utilisateurRepository; 
    }

    public Evenement creerEvenement(Evenement evenement, Long organisateurId) {
        Utilisateur organisateur = utilisateurRepository.findById(organisateurId)
                .orElseThrow(() -> new RuntimeException("Organisateur non trouvé"));
        evenement.setOrganisateur(organisateur);
        return evenementRepository.save(evenement);
    }

    public List<Evenement> obtenirEvenementsAVenir() {
        return evenementRepository.findByDateAfter(LocalDateTime.now());
    }

    public List<Evenement> rechercherEvenements(String keyword) {
        return evenementRepository.searchByKeyword(keyword);
    }

    public void supprimerEvenement(Long id, Long organisateurId) {
        evenementRepository.findById(id)
                .filter(e -> e.getOrganisateur().getId().equals(organisateurId))
                .ifPresent(evenementRepository::delete);
    }
}
