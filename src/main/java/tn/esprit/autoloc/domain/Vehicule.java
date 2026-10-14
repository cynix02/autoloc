package tn.esprit.autoloc.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.autoloc.domain.enums.CategorieVehicule;
import tn.esprit.autoloc.domain.enums.StatutVehicule;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vehicule")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehicule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVehicule;
    private String immatriculation;
    private String marque;
    private String modele;
    private CategorieVehicule categorie;
    private BigDecimal tarifJournalier;
    private StatutVehicule statut;

    @ManyToOne
    Agence agence;

    @OneToMany(mappedBy = "vehicule")
    List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "vehicule", cascade = CascadeType.ALL)
    List<Maintenance> maintenances = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.PERSIST)
    List<Equipement> equipements = new ArrayList<>();
}
