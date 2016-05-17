package Joueur;

import main.Moteur;
import terrain.Case.Couleur_Joueur;
/*
 * IA Random : Hérite de joueur_Générique
 * 	la fonction jouer joue des coups aléatoires parmis tous les coups possible à l'instant t.
 * 
 */




public class IA_Random extends joueur_Generique {
	private Moteur m;
	public IA_Random(Couleur_Joueur c, Moteur m)
	{
		super(c);//, m);
		this.m = m;
	}
	

	public void jouer() {
		// Faire des coups Randoms

	}

}
