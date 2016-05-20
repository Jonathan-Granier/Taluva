package Joueur;

import java.util.ArrayList;
import java.util.Random;

import Action.Action_Construction;
import Action.Action_Tuile;
import Moteur.Moteur;
import terrain.Case;
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
	Action_Construction coup_construction;
	
	public IA_Alpha_Beta (Couleur_Joueur c, Moteur m)
	{
		super(c, m);
		this.profondeur = 2;
	}
	public IA_Alpha_Beta (int profondeur, Couleur_Joueur c, Moteur m)
	{
		super(c, m);
		this.profondeur = profondeur;
	}
	@Override
	public Action_Construction get_coup_construction() {
		return coup_construction;
	}
	@Override
	public Action_Tuile get_coup_tuile(Tuile tuile) {
		Moteur virtuel= m.clone();
		Moteur reel = m;
		this.m = virtuel;
		ArrayList<Action_Tuile> list_tuile_possible = m.getTerrain().liste_coups_tuile_possibles(tuile);
		Coup_Tuile_Heuristique  coup_T_H= choisir_tuile_bon( list_tuile_possible, tuile, Integer.MAX_VALUE, this.profondeur);
		this.m = reel;
		return coup_T_H.get_Action_Tuile();
	}
	// Choisir une tuile, le score initial est infini a l'initial du terrain avant de jouer.
	private Coup_Tuile_Heuristique choisir_tuile_bon( ArrayList<Action_Tuile> liste, Tuile tuile, int score, int profondeur)
	{
		int i=0, score_max = Integer.MIN_VALUE;
		int score_courant;
		ArrayList<Action_Tuile> liste_tuile_retour = new ArrayList<Action_Tuile>();
		ArrayList<Action_Construction> liste_construction;
		// Si la profondeur est a 0, on renvoie l'heuristique. -> profondeur = nb de phase a générer
		if(profondeur == 0 )
		{
			while(i < liste.size() && score_max < score)
			{
				// /!\ regarder dans moteur virtuel
				//Simulation du coup.
				m.placer_tuile(liste.get(i).getPosition());
				if(	m.placer_tuile(liste.get(i).getPosition()) != 0)
				{
					System.out.println("IA A&B, probleme au placement de tuile,( retour != 0)");
				}
				score_courant = Heuristique();
				// si le coup est aussi optimal que le plus optimal trouvé, on l'ajoute.
				if(score_courant == score_max)
				{
					liste_tuile_retour.add(liste.get(i));
				}
				// si le coup est plus optimal, on vide la liste et on en commence une nouvelle.
				else if (score_courant > score_max)
				{
					score_max = score_courant;
					liste_tuile_retour.clear();
					liste_tuile_retour.add(liste.get(i));
				}
				//annuler_coup();
				m.annuler();
				i++;
			}
		}
		else
		{
			while( i < liste.size() && score_max < score)
			{
				// /!\ Simuler dans moteur virtuel
				//simulercoup(liste.get(i));
				m.placer_tuile(liste.get(i).getPosition());
				liste_construction = m.getTerrain().liste_coups_construction_possibles(this.getCouleur());
				score_courant = choisir_construction_bon(liste_construction, score, profondeur -1).get_Heuristique();
				// si le coup est aussi optimal que le plus optimal trouvé, on l'ajoute.
				if(score_courant == score_max)
				{
					liste_tuile_retour.add(liste.get(i));
				}
				// si le coup est plus optimal, on vide la liste et on en commence une nouvelle.
				else if (score_courant > score_max)
				{
					score_max = score_courant;
					liste_tuile_retour.clear();
					liste_tuile_retour.add(liste.get(i));
				}
				//annuler_coup();
				m.annuler();
				i++;
			}
		}
		// on renvoie un coup random parmi les coups optimaux
		Random R = new Random();
		Action_Tuile coup_tuile = liste_tuile_retour.get(R.nextInt(liste_tuile_retour.size()));
		return new Coup_Tuile_Heuristique(score_max, coup_tuile);
	}
	
	private Coup_Construction_Heuristique choisir_construction_bon( ArrayList <Action_Construction> liste, int score, int profondeur)
	{
		int i=0, score_max = Integer.MIN_VALUE;
		int score_courant;
		ArrayList<Action_Tuile> liste_tuile;
		ArrayList<Action_Construction> liste_construction_retour = new ArrayList<Action_Construction>();
		// Si la profondeur est a 0, on renvoie l'heuristique. -> profondeur = nb de phase a générer
		if(profondeur == 0 )
		{
			// /!\ regarder dans moteur virtuel
			while( i < liste.size() && score_max < score)
			{
				// /!\ Simuler dans moteur virtuel
				//TODO
				//simulercoup(liste.get(i));

				score_courant = Heuristique();
				if(score_courant == score_max)
				{
					liste_construction_retour.add(liste.get(i));
				}
				else if (score_courant > score_max)
				{
					score_max = score_courant;
					liste_construction_retour.clear();
					liste_construction_retour.add(liste.get(i));
				}
				//annuler_coup();
				m.annuler();
				i++;
			}
		}
		else
		{
			while( i < liste.size() && score_max < score)
			{
				// /!\ Simuler dans moteur virtuel
				//TODO
				//simulercoup(liste.get(i));
				
				//TODO
				// Generate generique tuile
				Tuile tuile = new Tuile(Case.Type.VIDE,Case.Type.VIDE);
				liste_tuile = m.getTerrain().liste_coups_tuile_possibles(tuile);
				// TODO
				score_courant = choisir_tuile_mauvais(liste_tuile, score_max, profondeur -1);
				if(score_courant == score_max)
				{
					liste_construction_retour.add(liste.get(i));
				}
				else if (score_courant > score_max)
				{
					score_max = score_courant;
					liste_construction_retour.clear();
					liste_construction_retour.add(liste.get(i));
				}
				//annuler_coup();
				m.annuler();
				i++;
			}
		}
		// on renvoie un coup parmi les coups optimaux.
		Random R = new Random();
		this.coup_construction = liste_construction_retour.get(R.nextInt(liste_construction_retour.size()));
		return new Coup_Construction_Heuristique(score_max, this.coup_construction);
	}
	
	private int choisir_tuile_mauvais(ArrayList<Action_Tuile> liste_tuile, int score, int profondeur)
	{
		/*
		 int i=0, score_min = Integer.MAX_VALUE;
		int score_courant;
		ArrayList<Action_Tuile> liste_tuile_retour = new ArrayList<Action_Tuile>();
		ArrayList<Action_Construction> liste_construction;
		// Si la profondeur est a 0, on renvoie l'heuristique. -> profondeur = nb de phase a générer
		if(profondeur == 0 )
		{
			while(i < liste.size() && score_min > score)
			{
				// /!\ regarder dans moteur virtuel
				//Simulation du coup.
				m.placer_tuile(liste.get(i).getPosition());
				if(	m.placer_tuile(liste.get(i).getPosition()) != 0)
				{
					System.out.println("IA A&B, probleme au placement de tuile,( retour != 0)");
				}
				score_courant = Heuristique();
				if (score_courant > score_min)
				{
					score_min = score_courant;
				}
				//annuler_coup();
				m.annuler();
				i++;
			}
		}
		else
		{
			while( i < liste.size() && score_min < score)
			{
				// /!\ Simuler dans moteur virtuel
				//simulercoup(liste.get(i));
				m.placer_tuile(liste.get(i).getPosition());
				liste_construction = m.getTerrain().liste_coups_construction_possibles(this.getCouleur());
				score_courant = choisir_construction_mauvais(liste_construction, score, profondeur -1).get_Heuristique();
				// si le coup est plus optimal, on met a jour la val min
				else if (score_courant > score_min)
				{
					score_min = score_courant;
				}
				//annuler_coup();
				m.annuler();
				i++;
			}
		}
		return score_min;
		 */
		return 0;
	}
	
	private int choisir_construction_mauvais(ArrayList<Action_Construction>liste_construction,int score,int profondeur)
	{
		/*
		 * int i=0, score_min = Integer.MAX_VALUE;
		int score_courant;
		// Si la profondeur est a 0, on renvoie l'heuristique. -> profondeur = nb de phase a générer
		if(profondeur == 0 )
		{
			// /!\ regarder dans moteur virtuel
			while( i < liste.size() && score_max < score)
			{
				// /!\ Simuler dans moteur virtuel
				//TODO
				//simulercoup(liste.get(i));

				score_courant = Heuristique();
				if (score_courant < score_min)
				{
					score_min = score_courant;

				}
				//annuler_coup();
				m.annuler();
				i++;
			}
		}
		else
		{
			while( i < liste.size() && score_max < score)
			{
				// /!\ Simuler dans moteur virtuel
				//TODO
				//simulercoup(liste.get(i));
				
				//TODO
				// Generate generique tuile
				Tuile tuile = new Tuile(Case.Type.VIDE,Case.Type.VIDE);
				liste_tuile = m.getTerrain().liste_coups_tuile_possibles(tuile);
				// TODO
				score_courant = choisir_tuile_mauvais(liste_tuile, score_max, profondeur -1);
				if (score_courant < score_min)
				{
					score_max = score_courant;
				}
				//annuler_coup();
				m.annuler();
				i++;
			}
		}
		return score_min;
		 */
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
		// Ajouter les points aux qualités cités selon certaines conditions:
		//Cité de X zones: 3*zones pour X>=3
		//	  Cité de 3 zone plus non destructible: Temple/2 si reste un temple.
		return score;
	}
	public int getProfondeur()
	{
		return this.profondeur;
	}
		
}
