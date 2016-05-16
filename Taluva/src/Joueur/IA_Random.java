package Joueur;

import java.awt.Color;
import main.Moteur;
/*
 * IA Random : Hérite de joueur_Générique
 * 	la fonction jouer joue des coups aléatoires parmis tous les coups possible à l'instant t.
 * 
 */




public class IA_Random extends joueur_Generique {
	private Moteur m;
	public IA_Random(Color couleur_joueur, Moteur m)
	{
		super(couleur_joueur);//, m);
		this.m = m;
	}
	

	public void jouer() {
		// Faire des coups Randoms

	}

}
