package com.example.eventreservation.service;

import com.example.eventreservation.model.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void envoyerConfirmationReservation(Utilisateur utilisateur, Evenement evenement, int places) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(utilisateur.getEmail());
        message.setSubject("Confirmation de réservation");
        message.setText(String.format(
            "Bonjour %s,\n\nVotre réservation pour l'événement \"%s\" (%s) a été confirmée.\nPlaces réservées : %d\n\nCordialement,\nL'équipe EventApp",
            utilisateur.getNom(),
            evenement.getTitre(),
            evenement.getDate().toString(),
            places
        ));
        mailSender.send(message);
    }
}
