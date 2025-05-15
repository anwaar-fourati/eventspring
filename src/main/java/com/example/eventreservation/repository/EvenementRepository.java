package com.example.eventreservation.repository;
import com.example.eventreservation.model.Evenement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;
import java.util.List;
public interface EvenementRepository extends JpaRepository<Evenement, Long> {
	
    List<Evenement> findByDateAfter(LocalDateTime date);
 
    List<Evenement> findByOrganisateurId(Long organisateurId);

    @Query("SELECT e FROM Evenement e WHERE e.titre LIKE %:keyword% OR e.description LIKE %:keyword%")
    List<Evenement> searchByKeyword(String keyword);
}
