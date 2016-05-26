package Joueur;

import Action.Action_Construction;
import Action.Action_Tuile;
import Action.Actions_Tour;
import terrain.Tuile;
import terrain.Case.Couleur_Joueur;


/*
 * joueur_Humain : Hérite de joueur_Générique 
 */


public class Joueur_Humain extends Joueur_Generique {

	public Joueur_Humain(Couleur_Joueur c)
	{
		super(c);
	}

	public Actions_Tour get_coup_tour(Tuile tuile)
	{
		System.out.println("[Joueur Humain]: get_coup_tour not overrun");
		return null;
	}
	@Override
	public Action_Tuile get_coup_tuile(Tuile tuile) {
		System.out.println("[Joueur Humain]: get_coup_tuile not overrun");
		return null;
	}

	@Override
	public Action_Construction get_coup_construction() {
		System.out.println("[Joueur Humain]: get_coup_construction not overrun");
		return null;
	}

}
