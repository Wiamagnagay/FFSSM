package FFSSM;

import java.util.ArrayList;
import java.util.List;

public class DiplomeDeMoniteur {
    private Plongeur plongeur;
    private int niveau;
    private String numeroDiplome;
    private List<Embauche> embauches;
    
    public DiplomeDeMoniteur(Plongeur plongeur, int niveau) {
        if (plongeur == null) {
            throw new IllegalArgumentException("Le plongeur ne peut pas être null");
        }
        if (niveau < 1 || niveau > 4) {
            throw new IllegalArgumentException("Le niveau doit être entre 1 et 4");
        }
        this.plongeur = plongeur;
        this.niveau = niveau;
        this.numeroDiplome = "DIP-" + System.currentTimeMillis();
        this.embauches = new ArrayList<>();
    }
    
    public Plongeur getPlongeur() {
        return plongeur;
    }
    
    public int getNiveau() {
        return niveau;
    }
    
    public String getNiveauTexte() {
        switch(niveau) {
            case 1: return "E1";
            case 2: return "E2";
            case 3: return "E3";
            case 4: return "E4";
            default: return "Inconnu";
        }
    }
    
    public boolean peutPresiderClub() {
        return niveau >= 3; // E3 ou E4 peuvent présider
    }
    
    public boolean peutEncadrerNiveau(int niveauPlongeur) {
        // E4 peut encadrer tous les niveaux
        if (niveau == 4) return true;
        
        // E3 peut encadrer jusqu'au niveau 3
        if (niveau == 3) return niveauPlongeur <= 3;
        
        // E2 peut encadrer jusqu'au niveau 2  
        if (niveau == 2) return niveauPlongeur <= 2;
        
        // E1 ne peut encadrer que le niveau 1
        if (niveau == 1) return niveauPlongeur == 1;
        
        return false;
    }
    
    // Méthodes pour gérer les embauches
    public void ajouterEmbauche(Embauche embauche) {
        embauches.add(embauche);
    }
    
    public List<Embauche> getEmbauches() {
        return new ArrayList<>(embauches);
    }
}