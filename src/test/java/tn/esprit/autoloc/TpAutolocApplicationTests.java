package tn.esprit.autoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.autoloc.domain.*;
import tn.esprit.autoloc.repository.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TpAutolocApplicationTests {

	@Test
	void contextLoads() {
	}

    @Autowired
    private IAgenceRepository agenceRepository;

    @Autowired
    private IClientRepository clientRepository;

    @Autowired
    private IVehiculeRepository vehiculeRepository;
    Long idAgence;
    String nom;
    String ville;
    String adresse;
    String telephone;

    @Autowired
    private IEmployeRepository employeRepository;

    @Autowired
    private IEquipementRepository equipementRepository;

    @Autowired
    private IPaiementRepository paiementRepository;

    @Test
    void testAgenceRepository() {
        long avant = agenceRepository.count();

        Agence agence = new Agence();
        agence.setNom("AutoLoc Tunis Centre");
        agence.setAdresse("Avenue Habib Bourguiba");
        agence.setVille("Tunis");

        // test save
        System.out.println("Test Save");
        Agence saved = agenceRepository.save(agence);
        System.out.println("Agence enregistree : " + saved);
        assertNotNull(saved);
        assertNotNull(saved.getIdAgence());

        //test find all
        System.out.println("Test Find All");
        List<Agence> agences = agenceRepository.findAll();
        System.out.println("Liste des agences : " + agences);
        assertFalse(agences.isEmpty());

        Optional<Agence> trouvee = agenceRepository.findById(saved.getIdAgence());
        System.out.println("findById : " + trouvee);
        assertTrue(trouvee.isPresent());
        assertEquals("AutoLoc Tunis Centre", trouvee.get().getNom());

        // existsById()
        assertTrue(agenceRepository.existsById(saved.getIdAgence()));

        // count()
        System.out.println("Test Count");
        long apres = agenceRepository.count();
        System.out.println("Nombre d'agences : " + apres);
        assertEquals(avant + 1, apres);

        // deleteById()
        System.out.println("Test Delete");
        agenceRepository.deleteById(saved.getIdAgence());
        assertFalse(agenceRepository.existsById(saved.getIdAgence()));
        assertEquals(avant, agenceRepository.count());
    }

    @Test
    void testClientRepository() {
        long avant = clientRepository.count();

        System.out.println("Creation de deux clients...");
        Client c1 = new Client();
        c1.setNom("Ben Ali");
        c1.setPrenom("Sami");
        c1.setEmail("sami.benali@mail.com");

        Client c2 = new Client();
        c2.setNom("Trabelsi");
        c2.setPrenom("Amel");
        c2.setEmail("amel.trabelsi@mail.com");


        System.out.println("Save des deux clients...");
        Client s1 = clientRepository.save(c1);
        Client s2 = clientRepository.save(c2);
        assertNotNull(s1.getIdClient());
        assertNotNull(s2.getIdClient());
        assertNotEquals(s1.getIdClient(), s2.getIdClient());

        System.out.println("Find all clients...");
        List<Client> clients = clientRepository.findAll();
        System.out.println("Liste des clients : " + clients);
        assertEquals(avant + 2, clients.size());

        Optional<Client> trouve = clientRepository.findById(s1.getIdClient());
        assertTrue(trouve.isPresent());
        System.out.println("Client trouve : " + trouve.get());

        // existant puis inexistant

        assertTrue(clientRepository.existsById(s1.getIdClient()));
        assertFalse(clientRepository.existsById(999999L));

        System.out.println("Calcul nb total clients...");
        System.out.println("Nombre de clients : " + clientRepository.count());
        assertEquals(avant + 2, clientRepository.count());

        // nettoyage
        System.out.println("Netoyage du test...");
        clientRepository.deleteById(s1.getIdClient());
        clientRepository.deleteById(s2.getIdClient());

    }

    @Test
    void testVehiculeRepository() {
        long avant = vehiculeRepository.count();

        Vehicule v = new Vehicule();
        v.setImmatriculation("123 TUN 4567");
        v.setMarque("Peugeot");
        v.setModele("208");

        Vehicule saved = vehiculeRepository.save(v);
        assertNotNull(saved.getIdVehicule());

        List<Vehicule> vehicules = vehiculeRepository.findAll();
        System.out.println("Liste des vehicules : " + vehicules);
        assertFalse(vehicules.isEmpty());

        System.out.println("Nombre de vehicules : " + vehiculeRepository.count());
        assertEquals(avant + 1, vehiculeRepository.count());

        // Modification puis save()
        saved.setMarque("Renault");
        Vehicule modifie = vehiculeRepository.save(saved);
        assertEquals(saved.getIdVehicule(), modifie.getIdVehicule());
        assertEquals("Renault", vehiculeRepository.findById(saved.getIdVehicule()).get().getMarque());
        assertEquals(avant + 1, vehiculeRepository.count()); // pas de doublon

        vehiculeRepository.deleteById(saved.getIdVehicule());
        assertFalse(vehiculeRepository.existsById(saved.getIdVehicule()));
        assertTrue(vehiculeRepository.findById(saved.getIdVehicule()).isEmpty());
    }

    @Test
    void testEmployeRepository() {
        Employe e = new Employe();
        e.setNom("Gharbi");
        e.setPrenom("Karim");

        Employe saved = employeRepository.save(e);               // creation
        System.out.println("Employe enregistre : " + saved);
        assertNotNull(saved.getIdEmploye());

        List<Employe> employes = employeRepository.findAll();    // lecture
        System.out.println("Liste des employes : " + employes);
        assertFalse(employes.isEmpty());

        assertTrue(employeRepository.existsById(saved.getIdEmploye())); // verification

        employeRepository.deleteById(saved.getIdEmploye());             // suppression
        assertFalse(employeRepository.existsById(saved.getIdEmploye()));
    }

    @Test
    void testEquipementRepository() {
        Equipement eq = new Equipement();
        eq.setLibelle("GPS");

        Equipement saved = equipementRepository.save(eq);
        System.out.println("Equipement enregistre : " + saved);
        assertNotNull(saved.getIdEquipement());

        assertTrue(equipementRepository.findById(saved.getIdEquipement()).isPresent());
        System.out.println("findById : " + equipementRepository.findById(saved.getIdEquipement()));

        assertTrue(equipementRepository.existsById(saved.getIdEquipement()));

        equipementRepository.deleteById(saved.getIdEquipement());
        assertFalse(equipementRepository.existsById(saved.getIdEquipement()));
    }

    @Test
    void testPaiementRepository() {
        Paiement p = new Paiement();
        p.setMontant(BigDecimal.valueOf(250.0));
        p.setDatePaiement(LocalDate.now());

        Paiement saved = paiementRepository.save(p);
        System.out.println("Paiement enregistre : " + saved);
        assertNotNull(saved.getIdPaiement());

        List<Paiement> paiements = paiementRepository.findAll();
        System.out.println("Liste des paiements : " + paiements);
        assertFalse(paiements.isEmpty());

        assertTrue(paiementRepository.existsById(saved.getIdPaiement()));

        paiementRepository.deleteById(saved.getIdPaiement());
        assertFalse(paiementRepository.existsById(saved.getIdPaiement()));
    }
}
