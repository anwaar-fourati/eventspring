package com.example.eventreservation.service;

import com.example.eventreservation.model.Utilisateur;
import com.example.eventreservation.repository.UtilisateurRepository;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UtilisateurService implements UserDetailsService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    public UtilisateurService(UtilisateurRepository utilisateurRepository, 
                           @Lazy PasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Implémentation requise pour UserDetailsService
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email: " + email));

        return User.builder()
                .username(utilisateur.getEmail())
                .password(utilisateur.getMotDePasse())
                .roles("USER") // Vous pouvez personnaliser les rôles
                .build();
    }

    public Utilisateur inscrireUtilisateur(Utilisateur utilisateur) {
        if (utilisateurRepository.existsByEmail(utilisateur.getEmail())) {
            throw new RuntimeException("Cet email est déjà utilisé");
        }
        
        utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
        return utilisateurRepository.save(utilisateur);
    }

    public Optional<Utilisateur> connecterUtilisateur(String email, String motDePasse) {
        return utilisateurRepository.findByEmail(email)
                .filter(u -> passwordEncoder.matches(motDePasse, u.getMotDePasse()));
    }

    public Utilisateur mettreAJourUtilisateur(Long id, Utilisateur utilisateur) {
        return utilisateurRepository.findById(id)
                .map(u -> {
                    if (utilisateur.getNom() != null) {
                        u.setNom(utilisateur.getNom());
                    }
                    if (utilisateur.getTelephone() != null) {
                        u.setTelephone(utilisateur.getTelephone());
                    }
                    // Ne pas permettre la modification d'email ou mot de passe ici
                    return utilisateurRepository.save(u);
                })
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé !"));
    }

    // Méthode supplémentaire utile
    public Optional<Utilisateur> trouverParEmail(String email) {
        return utilisateurRepository.findByEmail(email);
    }
}