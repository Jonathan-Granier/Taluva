package Joueur;

import Action.Action_Construction;
import Action.Action_Tuile;
import Action.Actions_Tour;
import Moteur.Moteur;
import terrain.Case.Couleur_Joueur;
import terrain.Tuile;

public class IA_MonteCarlo extends IA_Generique {

	private int taille_echantillon;
	private Action_Tuile coup_tuile;
	private Action_Construction coup_construction;
	
	public IA_MonteCarlo(Couleur_Joueur c, Moteur m) {
		super(c, m);
	}
	
	public IA_MonteCarlo(int taille_echantillon, Couleur_Joueur c, Moteur m) {
		super(c, m);
		this.taille_echantillon = taille_echantillon;
	}

	public Actions_Tour get_coup_tour(Tuile tuile) {
		calculer_coup_tour(tuile);
		return new Actions_Tour(this.coup_tuile, this.coup_construction);
	}

	private void calculer_coup_tour(Tuile tuile) {
	}

}
