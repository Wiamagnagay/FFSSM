package FFSSM;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Embauche {

    private final LocalDate debut;
    private LocalDate fin;
    private final DiplomeDeMoniteur employe;
    private final Club employeur;

    public Embauche(LocalDate debut, DiplomeDeMoniteur employe, Club employeur) {
        this.debut = debut;
        this.employe = employe;
        this.employeur = employeur;
    }
}