package FFSSM;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Plongeur extends Personne {

    private int niveau;
    private GroupeSanguin groupeSanguin;
    private List<Licence> licences;

    public Plongeur(String numeroINSEE, String nom, String prenom, String adresse, 
                    String telephone, LocalDate naissance, int niveau) {
        super(nom);
        this.numeroINSEE = numeroINSEE;
        this.prenom = prenom;
        this.adresse = adresse;
        this.telephone = telephone;
        this.naissance = naissance;
        this.niveau = niveau;
        this.licences = new ArrayList<>();
    }

    public void ajouterLicence(Licence licence) {
        licences.add(licence);
    }

    public boolean possedeLicenceValide(LocalDate date) {
        for (Licence licence : licences) {
            if (licence.estValide(date)) {
                return true;
            }
        }
        return false;
    }

    public int getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    public GroupeSanguin getGroupeSanguin() {
        return groupeSanguin;
    }

  // MÉTHODES MANQUANTES AJOUTÉES
    public int getNiveauPlongeur() {
        return niveau;
    }

    public int nombreLicences() {
        return licences.size();
    }

    public void setGroupeSanguin(GroupeSanguin groupeSanguin) {
        this.groupeSanguin = groupeSanguin;
    }
}