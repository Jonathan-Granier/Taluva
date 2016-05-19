package Joueur;

import java.util.ArrayList;

import main.Action_Construction;
import main.Action_Tuile;
import main.Moteur;
import terrain.Case.Couleur_Joueur;
import terrain.Terrain;
import terrain.Tuile;

public class IA_Alpha_Beta extends IA_Generique {
	private int profondeur;
	
	private int score_temple = 500;
	private int score_tour = 100;
	private int score_hutte = 1;
	private int score_city = 15;
	private int score_deplete_mult = 5;
	private int score_zone_city = 15;
	private int score_div_city_base = 1;
	private int score_div_city_temple = 2;
	private int score_mult_city_temple_tower = 0;
	Action_Construction construction;
	Action_Tuile tuile;
	
	public IA_Alpha_Beta (int profondeur, Couleur_Joueur c, Moteur m)
	{
		super(c, m);
		this.profondeur = profondeur;
	}
	@Override
	public Action_Construction get_coup_construction() {
		return construction;
	}
	@Override
	public Action_Tuile get_coup_tuile(Tuile tuile) {
		ArrayList<Action_Tuile> list_tuile_possible = m.getT().liste_coups_tuile_possibles(tuile);
		choisir_tuile_bon( list_tuile_possible, tuile, Integer.MAX_VALUE, this.profondeur);
		return null;
	}
	/*
	// Choisir une tuile, le score initial est l'heuristique du terrain avant de jouer.
	private int choisir_tuile_bon( ArrayList<Action_Tuile> liste, Tuile tuile, int score, int profondeur)
	{
		int i=0, score_max = Integer.MIN_VALUE;
		int score_courant;
		ArrayList<Action_Tuile> liste_retour = new ArrayList<Action_Tuile>();
		if(profondeur == 0 )
		{
			// /!\ regarder dans moteur virtuel
			return Heuristique();
		}
		else
		{
			while( i < liste.size() && score_max < score)
			{
				// /!\ Simuler dans moteur virtuel
				simulercoup(liste.get(i));
				score_courant = choisir_construction_bon();
				if(score_courant == score_max)
				{
					liste_retour.add(liste.get(i));
				}
				else if (score_courant > score_max)
				{
					score_max = score_courant;
					liste_retour.clear();
					liste_retour.add(liste.get(i));
				}
				annuler_coup();
			}
		}
		return score_max;
	}
	*/
	private int choisir_construction_mauvais( ArrayList <Action_Construction> liste)
	{
		return 0;
	}
	
	private int Heuristique()
	{
		int bonPoints;
		int mauvaisPoints;
		if(this.getCouleur() == m.getJ1().getCouleur())
		{
			bonPoints = Calculer_points_heur(m.getJ1());
			mauvaisPoints = Calculer_points_heur(m.getJ2());
		}
			
		else
		{
			bonPoints = Calculer_points_heur(m.getJ2());
			mauvaisPoints = Calculer_points_heur(m.getJ1());
		}
		
		return bonPoints - mauvaisPoints;
	}
	
	private int Calculer_points_heur(Joueur_Generique c) {
		// on compte le score d'un joueur dans le terrain.
		int score =0;
		score += (m.nb_max_Temples - c.getTemple()) * score_temple;
		score += (m.nb_max_Tours - c.getTour()) * score_tour;
		score += (m.nb_max_Huttes - c.getHutte()) * score_hutte;
		//score += city_count( c.getCouleur()) * score_city;
		
		// Si le joueur s'est débarassé de toutes ses pièces de 2 catégorie, il a gagné.
		if((c.getHutte() == 0 && c.getTemple() == 0) || (c.getTemple() ==0 && c.getTour() ==0) || (c.getHutte()==0 && c.getTour()==0))
		{
			score += 1000000000;; 
		}
		// Sinon, s'il s'en raproche:
		else if(c.getHutte() == 0 || c.getTour()==0 || c.getTemple()==0)
		{
			score += (m.get_nbTuiles()/ 2) * 5 / Math.max(c.getHutte(), Math.max(c.getTemple()*4, c.getTour()*4));
			// Attention, c'est dangereux de ne plus avoir de huttes.
			if(c.getHutte() == 0)
			{
				score -= (m.get_nbTuiles()/ 2) * 5;
			}
		}
		// Ajouter les points dus au cités selon certaines conditions:
		//Cité de X zones: 3*zones pour X>=3
		//	  Cité de 3 zone plus non destructible: Temple/2 si reste un temple.
		return score;
	}
		
}
