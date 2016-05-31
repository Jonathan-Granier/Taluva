package Joueur;

import Action.Actions_Tour;
import Moteur.Moteur;
import terrain.Case.Couleur_Joueur;
import terrain.Tuile;

public abstract class IA_Generique extends Joueur_Generique{
	Moteur m;
	public IA_Generique(Couleur_Joueur c, Moteur m) {
		super(c);
		this.m = m;
		// TODO Auto-generated constructor stub
	}
	public abstract Actions_Tour get_coup_tour(Tuile tuile);
}
