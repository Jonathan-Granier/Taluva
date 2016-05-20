package Joueur;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import Action.Action_Construction;
import Action.Action_Tuile;
import Moteur.Moteur;
import terrain.Case;
import terrain.Case.Couleur_Joueur;
import terrain.Tuile;
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
	public Action_Tuile get_coup_tuile(Tuile tuile) {
		// on génère la liste des coup possible, et on en choisit un
		ArrayList<Action_Tuile> liste_coup = m.getTerrain().liste_coups_tuile_possibles(tuile);
		Random R = new Random();
		return liste_coup.get(R.nextInt(liste_coup.size()));
	}


	@Override
	public Action_Construction get_coup_construction() {
		return m.get_liste_coup_construction().get_random_action();
	}

}
