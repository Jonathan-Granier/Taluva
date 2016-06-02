package Joueur;

import terrain.Case.Couleur_Joueur;

/*
 * joueur_Humain : Hérite de joueur_Générique 
 */


public class Joueur_Humain extends Joueur_Generique {

	public Joueur_Humain(Couleur_Joueur c)
	{
		super(c);
	}
	public Joueur_Humain(Couleur_Joueur c,String faction)
	{
		super(c);
		this.setNomFaction(faction);
	}

}
