package Joueur;

import java.awt.Point;

import main.Action_Construction;
import main.Action_Tuile;
import main.Liste_coup_tuile;
import main.Moteur;
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
	Action_Tuile get_coup_tuile() {
		// on génère la liste des coup possible, et on en choisit un
		Liste_coup_tuile liste_coup = m.get_liste_coup_tuile();
		liste_coup.next_coup_random();
		
		// On récupère la tuile à poser, et on l'oriente dans le bon sens
		Tuile tuile = m.piocher();
		Case.Orientation o = liste_coup.coup_rand_orienation();
		Point p = liste_coup.coup_rand_coord();
		
		while( tuile.get_Orientation_Volcan() != o)
			tuile.Tourner_horaire();
		
		// On renvoit l'action, avec le bon niveau.
		return new Action_Tuile(tuile, p, m.getT().getCase(p).getNiveau()+1);
	}


	@Override
	Action_Construction get_coup_construction() {
		return m.get_liste_coup_construction().get_random_action();
	}

}
