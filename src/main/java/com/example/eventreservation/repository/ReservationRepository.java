package com.example.eventreservation.repository;
import com.example.eventreservation.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	List<Reservation> findByUtilisateurId(Long utilisateurId);

    List<Reservation> findByEvenementId(Long evenementId);

    boolean existsByUtilisateurIdAndEvenementId(Long utilisateurId, Long evenementId);
}
