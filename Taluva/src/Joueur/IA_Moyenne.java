package Joueur;

import java.util.ArrayList;
import java.util.Random;

import Action.Action_Construction;
import Action.Action_Tuile;
import Action.Actions_Tour;
import Moteur.Moteur;
import terrain.Case.Couleur_Joueur;
import terrain.Cite;
import terrain.Tuile;

public class IA_Moyenne extends IA_Generique {
	
	private Random R;
	
	public IA_Moyenne(Couleur_Joueur c, Moteur m){
		super(c, m);
		this.R = new Random();
	}
	
	public IA_Moyenne(Couleur_Joueur c, Moteur m, String faction){
		super(c,m,faction);
		this.R = new Random();
	}

	public Actions_Tour get_coup_tour(Tuile tuile)
	{
		//System.out.println("IA Heuristique : On me demande un coup");
		Moteur virtuel= m.clone();
		Moteur reel = m;
		this.m = virtuel;
		Actions_Tour coup_AT= choisir_coup(tuile);
		this.m = reel;
		return coup_AT;
	}
	
	// Choisir une tuile, le score initial est infini a l'initial du terrain avant de jouer.
	private Actions_Tour choisir_coup(Tuile tuile)
	{
		int i=0, score_max = Integer.MIN_VALUE;
		int score_courant;
		ArrayList<Actions_Tour> AT_retour = new ArrayList<Actions_Tour>();
		Coup_Construction_Heuristique retour_ACH;
		ArrayList<Action_Tuile> liste_coups_tuile = m.get_liste_coup_tuile(tuile);
		while(i < liste_coups_tuile.size())
		{
			if(m.jouer_action(liste_coups_tuile.get(i)) == 0)
			{
				retour_ACH = choisir_construction_bon();
				if(retour_ACH != null){
					score_courant = retour_ACH.getHeuristique();
					// si le coup est aussi optimal que le plus optimal trouvé, on l'ajoute.
					if(score_courant == score_max)
					{
						AT_retour.add(new Actions_Tour(liste_coups_tuile.get(i),retour_ACH.getActionConstruction()));
					}
					// si le coup est plus optimal, on vide la liste et on en commence une nouvelle.
					else if (score_courant > score_max)
					{
						score_max = score_courant;
						AT_retour.clear();
						AT_retour.add(new Actions_Tour(liste_coups_tuile.get(i),retour_ACH.getActionConstruction()));
					}
				}
				m.annuler();
			}
			else System.out.println("Erreur IA H Tuile");
			i++;
		}
		// on renvoie un coup random parmi les coups optimaux
		if(AT_retour.isEmpty()){
			System.out.println("IA Heuristique : Impossible de construire");
			return new Actions_Tour(liste_coups_tuile.get(0), null);
		}
		else
			return AT_retour.get(R.nextInt(AT_retour.size()));
	}
	
	private Coup_Construction_Heuristique choisir_construction_bon()
	{
		int i=0, score_max = Integer.MIN_VALUE;
		int score_courant;
		ArrayList<Action_Construction> liste_construction_retour = new ArrayList<Action_Construction>();
		m.Maj_liste_coup_construction();
		ArrayList<Action_Construction> liste_coups_construction = m.get_liste_coup_construction().to_ArrayList();
		while(i < liste_coups_construction.size())
		{
			if(m.jouer_action(liste_coups_construction.get(i)) == 0){
				score_courant = Heuristique();
				if(score_courant == score_max)
				{
					liste_construction_retour.add(liste_coups_construction.get(i));
				}
				else if (score_courant > score_max)
				{
					score_max = score_courant;
					liste_construction_retour.clear();
					liste_construction_retour.add(liste_coups_construction.get(i));
				}
				m.annuler();
			}
			else System.out.println("Erreur IA H Construction");
			i++;
		}
		// on renvoie un coup parmi les coups optimaux.
		if(liste_construction_retour.isEmpty())
			return null;
		else
			return new Coup_Construction_Heuristique(score_max, liste_construction_retour.get(R.nextInt(liste_construction_retour.size())));
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
		score += (Moteur.nb_max_Temples - c.getTemple()) * score_temple;
		score += (Moteur.nb_max_Tours - c.getTour()) * score_tour;
		score += (Moteur.nb_max_Huttes - c.getHutte()) * score_hutte;
		// Si le joueur s'est débarassé de toutes ses pièces de 2 catégorie, il a gagné.
		if((c.getHutte() == 0 && c.getTemple() == 0) || (c.getTemple() ==0 && c.getTour() ==0) || (c.getHutte()==0 && c.getTour()==0))
		{
			score += Integer.MAX_VALUE;
		}
		// Sinon, s'il s'en raproche:
		else if(c.getHutte() == 0 || c.getTour()==0 || c.getTemple()==0)
		{
			score += (m.get_nbTuiles()/ 2) * score_deplete_mult 
					/Math.max( c.getHutte()*hut_deplete_mult , Math.max(c.getTemple()* temple_deplete_mult, c.getTour()* tower_deplete_mult) );
			// Attention, c'est dangereux de ne plus avoir de huttes.
			if(c.getHutte() == 0)
			{
				score -= (m.get_nbTuiles()/ 2) * hut_deplete_cant_play_mult;
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
		int score_cite = score_city + c.getTaille() * score_zone_city;
		// Si la cité nous permettra de créer un temple, elle en vaux la moitié des points
		if(c.getTaille() >= 3 && c.getNbTemples() > 0 && m.get_nbTuiles()/2 > 1 && j.getTemple()>0)
		{
			// check destructible
			score_cite += score_temple;
		}
		if(c.getNbTemples() > 0)
		{
			score_cite = score_cite/ score_div_city_temple;
		}
		if( c.getNbTemples()>0 && c.getNbTours()>0)
		{
			score_cite = score_cite / score_div_city_temple_tower;
		}
		return score_cite;
	}
	
}

