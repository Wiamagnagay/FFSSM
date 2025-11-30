/**
 * @(#) Plongee.java
 */
package FFSSM;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Plongee {

	public Site lieu;
	public DiplomeDeMoniteur chefDePalanquee;
	public LocalDate date;
	public int profondeur;
	public int duree;
	private Set<Plongeur> participants;

	public Plongee(Site lieu, DiplomeDeMoniteur chefDePalanquee, LocalDate date, int profondeur, int duree) {
		this.lieu = lieu;
		this.chefDePalanquee = chefDePalanquee;
		this.date = date;
		this.profondeur = profondeur;
		this.duree = duree;
		this.participants = new HashSet<>();
		this.participants.add(chefDePalanquee.getPlongeur());
	}

	public void ajouteParticipant(Plongeur participant) {
		participants.add(participant);
	}

	/**
	 * Détermine si la plongée est conforme. 
	 * Une plongée est conforme si tous les plongeurs de la palanquée ont une
	 * licence valide à la date de la plongée
	 * @return vrai si la plongée est conforme
	 */
	public boolean estConforme() {
		for (Plongeur p : participants) {
			if (!p.possedeLicenceValide(date)) {
				return false;
			}
		}
		return true;
	}
	public Site getSite() {
    return lieu;
}

public int getProfondeur() {
    return profondeur;
}

public int getDuree() {
    return duree;
}

public int nombreParticipants() {
    return participants.size(); // ou 1 + participants.size() selon l'implémentation
}

public boolean contientParticipant(Plongeur plongeur) {
    return participants.contains(plongeur);
}

public void ajouterParticipant(Plongeur plongeur) {
    participants.add(plongeur);
}}