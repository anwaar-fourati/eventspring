package com.example.eventreservation.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Evenement {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false)
	    private String titre;

	    @Column(columnDefinition = "TEXT")
	    private String description;

	    @Column(nullable = false)
	    private LocalDateTime date;

	    @Column(nullable = false)
	    private String lieu;

	    @Column(nullable = false)
	    private int placesDisponibles;

	    @ManyToOne
	    @JoinColumn(name = "organisateur_id", nullable = false)
	    private Utilisateur organisateur;

	    @OneToMany(mappedBy = "evenement", cascade = CascadeType.ALL)
	    private List<Reservation> reservations;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getTitre() {
			return titre;
		}

		public void setTitre(String titre) {
			this.titre = titre;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public LocalDateTime getDate() {
			return date;
		}

		public void setDate(LocalDateTime date) {
			this.date = date;
		}

		public String getLieu() {
			return lieu;
		}

		public void setLieu(String lieu) {
			this.lieu = lieu;
		}

		public int getPlacesDisponibles() {
			return placesDisponibles;
		}

		public void setPlacesDisponibles(int placesDisponibles) {
			this.placesDisponibles = placesDisponibles;
		}

		public Utilisateur getOrganisateur() {
			return organisateur;
		}

		public void setOrganisateur(Utilisateur organisateur) {
			this.organisateur = organisateur;
		}

		public List<Reservation> getReservations() {
			return reservations;
		}

		public void setReservations(List<Reservation> reservations) {
			this.reservations = reservations;
		}
	    
	    
}
