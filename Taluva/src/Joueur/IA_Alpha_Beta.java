package Joueur;

import java.util.ArrayList;
import java.util.Random;

import Action.Action_Construction;
import Action.Action_Tuile;
import Action.Actions_Tour;
import Moteur.Moteur;
import terrain.Case;
import terrain.Case.Couleur_Joueur;
import terrain.Cite;
import terrain.Tuile;

public class IA_Alpha_Beta extends IA_Generique {
	private int profondeur;
	private Action_Construction coup_construction;
	//private Action_Tuile coup_tuile;
	private ArrayList<ArrayList<Action_Construction>> Action_Construction_Memoire;
	private ArrayList<ArrayList<Action_Tuile>> Action_Tuile_Memoire; 
	
	private static int score_temple = 5000;
	private static int score_tour = 1000;
	private static int score_hutte = 1;
	private static int score_deplete_mult = 20;
	private static int tower_deplete_mult = 4;
	private static int temple_deplete_mult = 4;
	private static int hut_deplete_mult = 1;
	private static int hut_deplete_cant_play_mult = 5;
	private static int score_city = 5;
	private static int score_zone_city = 15;
	private static int score_div_city_temple = 2;
	private static int score_div_city_temple_tower = Integer.MAX_VALUE;
	
	// Constructeur sans profondeur donné, joue seulement son tour.
	public IA_Alpha_Beta (Couleur_Joueur c, Moteur m)
	{
		super(c, m);
		this.profondeur = 1;
		
		// Initialisation des ArrayLists
		Action_Tuile_Memoire = new ArrayList<ArrayList<Action_Tuile>>(this.profondeur+1/2);
		Action_Construction_Memoire = new ArrayList<ArrayList<Action_Construction>>(this.profondeur+1/2);
		for(int i=0; i < this.profondeur+1/2; i++)
		{
			Action_Tuile_Memoire.add(new ArrayList<Action_Tuile>());
			Action_Construction_Memoire.add(new ArrayList<Action_Construction>());
		}
	}
	// Constructeur avec profondeur donnée
	public IA_Alpha_Beta (int profondeur, Couleur_Joueur c, Moteur m)
	{
		super(c, m);
		this.profondeur = profondeur;
		
		// Initialisation des ArrayLists
		Action_Tuile_Memoire = new ArrayList<ArrayList<Action_Tuile>>(this.profondeur+1/2);
		Action_Construction_Memoire = new ArrayList<ArrayList<Action_Construction>>(this.profondeur+1/2);
		for(int i=0; i < this.profondeur+1/2; i++)
		{
			Action_Tuile_Memoire.add(new ArrayList<Action_Tuile>());
			Action_Construction_Memoire.add(new ArrayList<Action_Construction>());
		}
	}
	
	@Override
	public Actions_Tour get_coup_tour(Tuile tuile)
	{
		Action_Tuile AT_retour = get_coup_tuile(tuile);
		return new Actions_Tour(AT_retour, this.coup_construction);
	}
	

	private Action_Tuile get_coup_tuile(Tuile tuile) {
		Moteur virtuel= m.clone();
		Moteur reel = m;
		this.m = virtuel;
		//ArrayList<Action_Tuile> list_tuile_possible = m.getTerrain().liste_coups_tuile_possibles(tuile);
		this.Action_Tuile_Memoire.set(profondeur/2, m.getTerrain().liste_coups_tuile_possibles(tuile));
		Coup_Tuile_Heuristique  coup_T_H= choisir_tuile_bon( this.Action_Tuile_Memoire.get(profondeur/2), tuile, Integer.MAX_VALUE, this.profondeur);
		this.m = reel;
		return coup_T_H.getActionTuile();
	}
	
	// Choisir une tuile, le score initial est infini a l'initial du terrain avant de jouer.
	private Coup_Tuile_Heuristique choisir_tuile_bon( ArrayList<Action_Tuile> liste, Tuile tuile, int score, int profondeur)
	{
		int i=0, score_max = Integer.MIN_VALUE;
		int score_courant;
		Random R = new Random();
		Coup_Tuile_Heuristique TH_retour = new Coup_Tuile_Heuristique(0,null);
		Coup_Construction_Heuristique retour_REC;
		Action_Construction coup_construction_retour = null;
		// Si la profondeur est a 0, on renvoie l'heuristique. -> profondeur = nb de phase a générer
		if(profondeur == 0 )
		{
			while(i < liste.size() && score_max < score)
			{
				// /!\ regarder dans moteur virtuel
				//Simulation du coup.
				m.placer_tuile(liste.get(i).getPosition());
				score_courant = Heuristique();
				// si le coup est aussi optimal que le plus optimal trouvé, on l'ajoute.
				if(score_courant == score_max && R.nextInt(100)<20)
				{
					TH_retour.setActionTuile(liste.get(i));
				}
				// si le coup est plus optimal, on vide la liste et on en commence une nouvelle.
				else if (score_courant > score_max)
				{
					score_max = score_courant;
					TH_retour.setActionTuile(liste.get(i));
					TH_retour.setHeuristique(score_courant);
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
				if(m.jouer_action(liste.get(i)) == 0)
				{
					m.Maj_liste_coup_construction();
					//liste_construction = m.get_liste_coup_construction().to_ArrayList();
					m.get_liste_coup_construction().into_ArrayList(this.Action_Construction_Memoire.get(profondeur/2));
					retour_REC = choisir_construction_bon(this.Action_Construction_Memoire.get(profondeur/2), score, profondeur -1);
					score_courant = retour_REC.getHeuristique();
					// si le coup est aussi optimal que le plus optimal trouvé, on l'ajoute.
					if(score_courant == score_max && R.nextInt(100)<20)
					{
						TH_retour.setActionTuile(liste.get(i));
						coup_construction_retour = retour_REC.getActionConstruction();
					}
					// si le coup est plus optimal, on vide la liste et on en commence une nouvelle.
					else if (score_courant > score_max)
					{
						score_max = score_courant;
						TH_retour.setActionTuile(liste.get(i));
						TH_retour.setHeuristique(retour_REC.getHeuristique());
						coup_construction_retour = retour_REC.getActionConstruction();
					}
					//annuler_coup();
					m.annuler();
				}
				i++;
			}
		}
		// on renvoie un coup random parmi les coups optimaux
		this.coup_construction = coup_construction_retour;
		return TH_retour;
	}
	
	private Coup_Construction_Heuristique choisir_construction_bon( ArrayList <Action_Construction> liste, int score, int profondeur)
	{
		int i=0, score_max = Integer.MIN_VALUE;
		int score_courant;
		ArrayList<Action_Construction> liste_construction_retour = new ArrayList<Action_Construction>();
		// Si la profondeur est a 0, on renvoie l'heuristique. -> profondeur = nb de phase a générer
		if(profondeur == 0 )
		{
			while( i < liste.size() && score_max < score)
			{
				// /!\ Simuler dans moteur virtuel
				m.jouer_action(liste.get(i));
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
				m.jouer_action(liste.get(i));

				// Generate generique tuile
				// TODO
				Tuile tuile = new Tuile(Case.Type.VIDE,Case.Type.VIDE);
				this.Action_Tuile_Memoire.set(profondeur/2, m.getTerrain().liste_coups_tuile_possibles(tuile));
				this.tuile_tri(this.Action_Tuile_Memoire.get(profondeur/2));
				score_courant = choisir_tuile_mauvais(this.Action_Tuile_Memoire.get(profondeur/2), score_max, profondeur -1);
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
		
		 int i=0, score_min = Integer.MAX_VALUE;
		int score_courant;
		// Si la profondeur est a 0, on renvoie l'heuristique. -> profondeur = nb de phase a générer
		if(profondeur == 0 )
		{
			while(i < liste_tuile.size() && score_min > score)
			{
				// /!\ regarder dans moteur virtuel
				//Simulation du coup.
				//m.placer_tuile(liste_tuile.get(i).getPosition());
				if(	m.placer_tuile(liste_tuile.get(i).getPosition()) != 0)
				{
					System.out.println("IA A&B, probleme au placement de tuile,( retour != 0)");
				}
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
			while( i < liste_tuile.size() && score_min > score)
			{
				// /!\ Simuler dans moteur virtuel
				m.placer_tuile(liste_tuile.get(i).getPosition());
				this.Action_Construction_Memoire.set(profondeur/2, m.getTerrain().liste_coups_construction_possibles(m.get_Couleur_Joueur_Adverse()));
				score_courant = choisir_construction_mauvais(this.Action_Construction_Memoire.get(profondeur/2), score, profondeur -1);
				// si le coup est plus optimal, on met a jour la val min
				if (score_courant < score_min)
				{
					score_min = score_courant;
				}
				//annuler_coup();
				m.annuler();
				i++;
			}
		}
		return score_min;
	}
	
	private int choisir_construction_mauvais(ArrayList<Action_Construction>liste_construction,int score,int profondeur)
	{
		
		int i=0, score_min = Integer.MAX_VALUE;
		int score_courant;
		// Si la profondeur est a 0, on renvoie c.getTemple()) l'heuristique. -> profondeur = nb de phase a générer
		if(profondeur == 0 )
		{
			// /!\ regarder dans moteur virtuel
			while( i < liste_construction.size() && score_min > score)
			{
				// /!\ Simuler dans moteur virtuel
				m.jouer_action(liste_construction.get(i));
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
			while( i < liste_construction.size() && score_min > score)
			{
				// /!\ Simuler dans moteur virtuel
				m.jouer_action(liste_construction.get(i));
				
				// Generate generique tuile
				//TODO
				Tuile tuile = new Tuile(Case.Type.VIDE,Case.Type.VIDE);
				this.Action_Tuile_Memoire.set(profondeur/2, m.getTerrain().liste_coups_tuile_possibles(tuile));
				this.tuile_tri(this.Action_Tuile_Memoire.get(profondeur/2));
				score_courant = choisir_tuile_bon(this.Action_Tuile_Memoire.get(profondeur/2), tuile, score_min, profondeur -1).getHeuristique();
				if (score_courant < score_min)
				{
					score_min = score_courant;
				}
				//annuler_coup();
				m.annuler();
				i++;
			}
		}
		return score_min;
	}
	
	private int Heuristique()
	{
		int bonPoints;
		int mauvaisPoints;
		if(m.EstLeMemeJoueur(this, m.getJ1()))
		{
			bonPoints = Calculer_points_heur(m.get_Jcourant());
			mauvaisPoints = Calculer_points_heur(m.getJ2());
		}
		else
		{
			bonPoints = Calculer_points_heur(m.get_Jcourant());
			mauvaisPoints = Calculer_points_heur(m.getJ1());
		}
		return bonPoints - mauvaisPoints;
	}
	
	private int Calculer_points_heur(Joueur_Generique c) {
		// on compte le score d'un joueur dans le terrain.
		int score =0;
		score += (Moteur.nb_max_Temples - c.getTemple()) * IA_Alpha_Beta.score_temple;
		score += (Moteur.nb_max_Tours - c.getTour()) * IA_Alpha_Beta.score_tour;
		score += (Moteur.nb_max_Huttes - c.getHutte()) * IA_Alpha_Beta.score_hutte;		
		// Si le joueur s'est débarassé de toutes ses pièces de 2 catégorie, il a gagné.
		if((c.getHutte() == 0 && c.getTemple() == 0) || (c.getTemple() ==0 && c.getTour() ==0) || (c.getHutte()==0 && c.getTour()==0))
		{
			score += 1000000000;
		}
		// Sinon, s'il s'en raproche:
		else if(c.getHutte() == 0 || c.getTour()==0 || c.getTemple()==0)
		{
			score += (m.get_nbTuiles()/ 2) * IA_Alpha_Beta.score_deplete_mult 
					/Math.max( c.getHutte()*IA_Alpha_Beta.hut_deplete_mult , Math.max(c.getTemple()* IA_Alpha_Beta.temple_deplete_mult, c.getTour()* IA_Alpha_Beta.tower_deplete_mult) );
			// Attention, c'est dangereux de ne plus avoir de huttes.
			if(c.getHutte() == 0)
			{
				score -= (m.get_nbTuiles()/ 2) * IA_Alpha_Beta.hut_deplete_cant_play_mult;
			}
		}
		// Ajouter les points dus aux qualités des cités;
		ArrayList<Cite> liste_cite = m.getTerrain().getCitesJoueur(c.getCouleur());
		for(int i=0; i < liste_cite.size(); i++)
			score += valeur_cite(liste_cite.get(i), c);
		return score;
	}
	
	// Calculer la valeur d'une cité.
	private int valeur_cite( Cite c, Joueur_Generique j)
	{
		int score_cite = IA_Alpha_Beta.score_city + c.getTaille() * IA_Alpha_Beta.score_zone_city;
		// Si la cité nous permettra de créer un temple, elle en vaux la moitié des points
		if(c.getTaille() >= 3 && c.getNbTemples() > 0 && indestructible_city(c) && m.get_nbTuiles()/2 > 1 && j.getTemple()>0)
		{
			// check destructible
			score_cite += IA_Alpha_Beta.score_temple;
		}
		if(c.getNbTemples() > 0)
		{
			score_cite = score_cite/ IA_Alpha_Beta.score_div_city_temple;
		}
		if( c.getNbTemples()>0 && c.getNbTours()>0)
		{
			score_cite = score_cite / IA_Alpha_Beta.score_div_city_temple_tower;
		}
		return score_cite;
	}
	
	// Renvoi vrai si la cité ne peut pas être detruite.
	private boolean indestructible_city ( Cite c)
	{
		return false;
	}
	
	public int getProfondeur()
	{
		return this.profondeur;
	}
	
	private ArrayList<Action_Tuile> tuile_tri(ArrayList<Action_Tuile> liste_tuile)
	{
		Random R = new Random();
		int i=0;
		while( i < liste_tuile.size())
		{
			if(liste_tuile.get(i).getNiveau() > 1)
			{
			}
			else if (R.nextInt(liste_tuile.size()) > 25)
			{
				liste_tuile.remove(i);
			}
			i++;
		}
		return liste_tuile;
	}
		
}
