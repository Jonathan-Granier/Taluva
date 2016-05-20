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
	
	public Joueur_Humain clone(){
		Joueur_Humain j;
		j = new Joueur_Humain(this.getCouleur());
		j.setTemple(this.getTemple());
		j.setTour(this.getTour());
		j.setHutte(this.getHutte());
		j.setHutteDetruite(this.getHutteDetruite());
		return j;
	}


}
