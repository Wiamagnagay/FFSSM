package FFSSM;

import lombok.Data;
import lombok.NonNull;

@Data
public class Site {
	@NonNull
	private String nom;

	@NonNull
	private String details;

	public static void main(String[] args) {
		Site s = new Site("Site1", "Site1 details");
		s.setNom("Mont Saint Michel");
		s.setDetails("Plongee autour du Mont Saint Michel");
		System.out.printf("Le site : %s\n", s);
	}
}