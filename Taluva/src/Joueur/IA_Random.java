package Joueur;

import main.Action_Construction;
import main.Action_Tuile;
import main.Moteur;
import terrain.Case.Couleur_Joueur;
/*
 * IA Random : Hérite de joueur_Générique
 * 	la fonction jouer joue des coups aléatoires parmis tous les coups possible à l'instant t.
 * 
 */




public class IA_Random extends IA_Generique{
	public IA_Random(Couleur_Joueur c, Moteur m)
	{
		super(c,m);
	}
	


	@Override
	Action_Tuile get_coup_tuile() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	Action_Construction get_coup_construction() {
		// TODO Auto-generated method stub
		return null;
	}

}
