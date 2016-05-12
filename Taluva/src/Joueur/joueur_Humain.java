package Joueur;

import java.awt.Color;

import main.Moteur;

/*
 * joueur_Humain : Hérite de joueur_Générique 
 * la fonction jouer() est vide , car elle est gerer ailleurs
 */


public class joueur_Humain extends joueur_Generique {

	public joueur_Humain(Color couleur_joueur, Moteur m)
	{
		super(couleur_joueur, m);
	}
	
	public void jouer() {
	}

}
