package Joueur;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import Action.Action_Construction;
import Action.Action_Tuile;
import Liste_coup.Liste_coup_construction;
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
	
	public Actions_Tour get_coup_tour(Tuile tuile){
		// on génère la liste des coup TUILE possible, et on en choisit un.
		ArrayList<Action_Tuile> liste_coup = m.getTerrain().liste_coups_tuile_possibles(tuile);
		Random R = new Random();
		int index = R.nextInt(liste_coup.size());
		System.out.println(" IA rand(tuile): on me demande de jouer un coup parmi: "+ liste_coup.size() + ". J'ai roll " + index);
		Actions_Tour retour = new Actions_Tour(liste_coup.get(index), null);
		
		//   On génère la liste des coups CONSTRUCTION possible, et on en choisit un.
		// (on annule l'action pour ne pas modifier le terrain après coup)
		m.jouer_action(liste_coup.get(index));
		m.Maj_liste_coup_construction();
		retour.setAction_construction(m.get_liste_coup_construction().get_random_action());
		m.annuler();
		return retour;
	}
	
	public Action_Tuile get_coup_tuile(Tuile tuile) {
		// on génère la liste des coup possible, et on en choisit un.
		ArrayList<Action_Tuile> liste_coup = m.getTerrain().liste_coups_tuile_possibles(tuile);
		Random R = new Random();
		int index = R.nextInt(liste_coup.size());
		System.out.println(" IA rand(tuile): on me demande de jouer un coup parmi: "+ liste_coup.size() + ". J'ai roll " + index);
		return liste_coup.get(index);
	}


	@Override
	public Action_Construction get_coup_construction() {
		m.Maj_liste_coup_construction();
		return m.get_liste_coup_construction().get_random_action();
	}

}
