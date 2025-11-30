package FFSSM;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests unitaires pour le système FFSSM
 */
class FFSSMTest {

    private Plongeur plongeur1;
    private Plongeur plongeur2;
    private Plongeur moniteur;
    private DiplomeDeMoniteur diplomeMoniteur;
    private Club club;
    private Site site;
    private Licence licence2024;
    private Licence licence2025;

    @BeforeEach
    void setUp() {
        // Création des plongeurs
        plongeur1 = new Plongeur("123456789", "Dupont", "Jean", 
                                 "1 rue de la Mer", "0601020304", 
                                 LocalDate.of(1990, 5, 15), 2);
        
        plongeur2 = new Plongeur("987654321", "Martin", "Sophie", 
                                 "2 avenue de l'Océan", "0605060708", 
                                 LocalDate.of(1985, 8, 20), 3);
        
        moniteur = new Plongeur("555555555", "Leblanc", "Pierre", 
                                "10 quai du Port", "0612345678", 
                                LocalDate.of(1975, 3, 10), 5);
        
        // Diplôme de moniteur E3
        diplomeMoniteur = new DiplomeDeMoniteur(moniteur, 3);
        
        // Création du club
        club = new Club(diplomeMoniteur, "Club Atlantique");
        club.setAdresse("Port de plaisance");
        club.setTelephone("0299887766");
        
        // Création du site
        site = new Site("Les Glénan", "Finistère");
        
        // Licences
        licence2024 = new Licence(plongeur1, "LIC2024-001", LocalDate.of(2024, 1, 15), club);
        licence2025 = new Licence(plongeur1, "LIC2025-001", LocalDate.of(2025, 1, 10), club);
    }

    // ========== TESTS PLONGEUR ==========
    
    @Test
    void testCreationPlongeur() {
        assertEquals("Dupont", plongeur1.getNom());
        assertEquals("Jean", plongeur1.getPrenom());
        assertEquals(2, plongeur1.getNiveauPlongeur());
    }

    @Test
    void testNiveauInvalide() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Plongeur("111", "Test", "Test", "Adresse", "Tel", 
                        LocalDate.of(1990, 1, 1), 6); // Niveau invalide
        });
    }

    @Test
    void testAjouterLicence() {
        // Supprimez licence2025 si vous ne l'utilisez pas
        assertEquals(1, plongeur1.nombreLicences()); // licence2024 seulement
        
        // Ajouter une licence à plongeur2
        new Licence(plongeur2, "LIC2024-002", LocalDate.of(2024, 2, 1), club);
        assertEquals(1, plongeur2.nombreLicences());
    }

    @Test
    void testPossedeLicenceValide() {
        // Assurez-vous que plongeur1 a une licence 2024
        assertTrue(plongeur1.possedeLicenceValide(LocalDate.of(2024, 6, 15)));
        assertTrue(plongeur1.possedeLicenceValide(LocalDate.of(2025, 3, 20)));
        assertFalse(plongeur1.possedeLicenceValide(LocalDate.of(2023, 12, 31)));
    }

    // ========== TESTS LICENCE ==========
    
    @Test
    void testLicenceEstValide() {
        assertTrue(licence2024.estValide(LocalDate.of(2024, 1, 1)));
        assertTrue(licence2024.estValide(LocalDate.of(2024, 12, 31)));
        assertFalse(licence2024.estValide(LocalDate.of(2025, 1, 1)));
    }

    @Test
    void testLicenceConstructeurNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Licence(null, "NUM", LocalDate.now(), club);
        });
    }

    @Test
    void testLicenceNumeroVide() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Licence(plongeur1, "", LocalDate.now(), club);
        });
    }

    // ========== TESTS DIPLOME DE MONITEUR ==========
    
    @Test
    void testCreationDiplome() {
        assertEquals(moniteur, diplomeMoniteur.getPlongeur());
        assertEquals(3, diplomeMoniteur.getNiveau());
        assertEquals("E3", diplomeMoniteur.getNiveauTexte());
    }

    @Test
    void testPeutPresiderClub() {
        DiplomeDeMoniteur e2 = new DiplomeDeMoniteur(plongeur1, 2);
        DiplomeDeMoniteur e3 = new DiplomeDeMoniteur(plongeur2, 3);
        
        assertFalse(e2.peutPresiderClub()); // E2 ne peut pas présider
        assertTrue(e3.peutPresiderClub());  // E3 peut présider
    }

    @Test
    void testPeutEncadrerNiveau() {
        assertTrue(diplomeMoniteur.peutEncadrerNiveau(1));
        assertTrue(diplomeMoniteur.peutEncadrerNiveau(2));
        assertTrue(diplomeMoniteur.peutEncadrerNiveau(3));
        assertFalse(diplomeMoniteur.peutEncadrerNiveau(4)); // E3 ne peut pas encadrer niveau 4
    }

    @Test
    void testDiplomeNiveauInvalide() {
        assertThrows(IllegalArgumentException.class, () -> {
            new DiplomeDeMoniteur(moniteur, 5); // Niveau 5 invalide
        });
    }

    // ========== TESTS PLONGEE ==========
    
    @Test
    void testCreationPlongee() {
        Plongee plongee = new Plongee(site, diplomeMoniteur, LocalDate.of(2024, 7, 15), 30, 45);
        
        assertEquals(site, plongee.getSite());
        assertEquals(30, plongee.getProfondeur());
        assertEquals(45, plongee.getDuree());
        assertEquals(1, plongee.nombreParticipants()); // Chef de palanquée inclus
    }

    @Test
    void testAjouterParticipants() {
        Plongee plongee = new Plongee(site, diplomeMoniteur, LocalDate.of(2024, 7, 15), 30, 45);
        
        plongee.ajouterParticipant(plongeur1);
        plongee.ajouterParticipant(plongeur2);
        
        assertEquals(3, plongee.nombreParticipants());
        assertTrue(plongee.contientParticipant(plongeur1));
        assertTrue(plongee.contientParticipant(plongeur2));
    }




    @Test
    void testPlongeeProfondeurInvalide() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Plongee(site, diplomeMoniteur, LocalDate.now(), -10, 45); // Profondeur négative
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Plongee(site, diplomeMoniteur, LocalDate.now(), 150, 45); // Profondeur excessive
        });
    }

    // ========== TESTS CLUB ==========
    
    @Test
    void testCreationClub() {
        assertEquals("Club Atlantique", club.getNom());
        assertEquals(diplomeMoniteur, club.getPresident());
    }

    @Test
    void testOrganiserPlongee() {
        Plongee plongee1 = new Plongee(site, diplomeMoniteur, LocalDate.of(2024, 7, 15), 30, 45);
        Plongee plongee2 = new Plongee(site, diplomeMoniteur, LocalDate.of(2024, 7, 20), 25, 40);
        
        club.organisePlongee(plongee1);
        club.organisePlongee(plongee2);
        
        assertEquals(2, club.nombrePlongeesOrganisees());
    }

   @Test
   void testPlongeeConforme() {
    // Plongée en 2024 avec plongeur ayant licence 2024
    Plongee plongee = new Plongee(site, diplomeMoniteur, LocalDate.of(2024, 7, 15), 30, 45);
    
    // Ajouter licence au moniteur
    new Licence(moniteur, "LIC-MON-2024", LocalDate.of(2024, 1, 1), club);
    
    plongee.ajouterParticipant(plongeur1); // A une licence 2024
    
    assertTrue(plongee.estConforme());
}

    @Test
     void testPlongeeNonConforme() {
    // Plongée en 2023, mais plongeur1 a seulement licences 2024 et 2025
    Plongee plongee = new Plongee(site, diplomeMoniteur, LocalDate.of(2023, 6, 15), 20, 40);
    
    // Ajouter licence au moniteur pour 2023
    new Licence(moniteur, "LIC-MON-2023", LocalDate.of(2023, 1, 1), club);
    
    plongee.ajouterParticipant(plongeur1); // Pas de licence 2023
    
    assertFalse(plongee.estConforme()); // Non conforme car plongeur1 n'a pas de licence 2023
}

    @Test
    void testClubPresidentNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Club(null, "Club Test");
        });
    }

    @Test
    void testClubNomVide() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Club(diplomeMoniteur, "");
        });
    }

    // ========== TESTS INTÉGRATION ==========
    
    @Test
    void testScenarioComplet() {
        // Créer un nouveau plongeur sans licence
        Plongeur nouveauPlongeur = new Plongeur("999999999", "Nouveau", "Plongeur", 
                                                "Adresse", "Tel", LocalDate.of(1995, 1, 1), 1);
        
        // Lui donner une licence 2024
        new Licence(nouveauPlongeur, "LIC-NEW-2024", LocalDate.of(2024, 1, 1), club);
        
        // Créer licence pour le moniteur
        new Licence(moniteur, "LIC-MON-2024", LocalDate.of(2024, 1, 1), club);
        
        // Créer une plongée en 2024
        Plongee plongee = new Plongee(site, diplomeMoniteur, LocalDate.of(2024, 9, 1), 25, 40);
        plongee.ajouterParticipant(plongeur1);      // A licence 2024
        plongee.ajouterParticipant(nouveauPlongeur); // A licence 2024
        
        // Organiser la plongée
        club.organisePlongee(plongee);
        
        // Vérifications
        assertTrue(plongee.estConforme()); 
        assertEquals(3, plongee.nombreParticipants());
        assertEquals(0, club.plongeesNonConformes().size());
    }
}  