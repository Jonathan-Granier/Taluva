package Joueur;

import Action.Actions_Tour;
import Moteur.Moteur;
import terrain.Case.Couleur_Joueur;
import terrain.Tuile;

public abstract class IA_Generique extends Joueur_Generique{
	Moteur m;
	
	protected static int score_temple = 5000;
	protected static int score_tour = 1000;
	protected static int score_hutte = 1;
	protected static int score_deplete_mult = 20;
	protected static int tower_deplete_mult = 4;
	protected static int temple_deplete_mult = 4;
	protected static int hut_deplete_mult = 1;
	protected static int hut_deplete_cant_play_mult = 5;
	protected static int score_city = 5;
	protected static int score_zone_city = 15;
	protected static int score_div_city_temple = 2;
	protected static int score_div_city_temple_tower = Integer.MAX_VALUE;
	protected static int score_cite_petite_avec_temple = 5;
	protected static int score_taille_cite_sans_temple = 2;
	protected static int score_cite_indestructible_sans_temple = 100;
	
	public IA_Generique(Couleur_Joueur c, Moteur m) {
		super(c);
		this.m = m;
	}
	
	public abstract Actions_Tour get_coup_tour(Tuile tuile);
	/*
	protected int Heuristique()
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
			return Integer.MAX_VALUE;
		}
		// Sinon, s'il s'en raproche:
		else if(c.getTour()==0 || c.getTemple()==0)
		{
			score += (m.get_nbTuiles()/ 2) * score_deplete_mult 
					/Math.max( c.getHutte()*hut_deplete_mult , Math.max(c.getTemple()* temple_deplete_mult, c.getTour()* tower_deplete_mult) );
		}
		// Attention, c'est dangereux de ne plus avoir de huttes.
		if(c.getHutte() == 0)
		{
			return 0;
		}
		
		// Ajouter les points dus aux qualités des cités;
		ArrayList<Cite> liste_cite = m.getTerrain().getCitesJoueur(c.getCouleur());
		for(int i=0; i < liste_cite.size(); i++)
			score += valeur_cite(liste_cite.get(i), c);
		return score;
	}
	
	// Calculer la valeur d'une cité.
	private int valeur_cite(Cite c, Joueur_Generique j)
	{
		int score_cite = 0;//score_city + c.getTaille() * score_zone_city;
		// Si la cité nous permettra de créer un temple, elle en vaux la moitié des points
		if(c.getTaille() >= 3 && c.getNbTemples() == 0 && m.get_nbTuiles() > 2 && j.getTemple()>0)
		{
			// check destructible
			score_cite += score_temple/2;
		}
		// Si la cité permet de construire une tour
		if(c.getNbTours() == 0 && m.get_nbTuiles() > 2 && j.getTour()>0){
			for(Case.Type t : Case.Type.values()){
				ArrayList<Point> ptsVoisins = m.getTerrain().getPts_extension_cite(c, t);
				for(Point p : ptsVoisins){
					if(m.getTerrain().getCase(p).getNiveau()>=3 && m.getTerrain().getCase(p).est_Libre()){
						score_cite+=score_tour/2;
					}
				}
			}
		}
		if(c.getNbTemples() > 0)
		{
			score_cite = score_cite / score_div_city_temple;
		}
		if( c.getNbTemples()>0 && c.getNbTours()>0)
		{
			score_cite = score_cite / score_div_city_temple_tower;
		}
		return score_cite;
	}*/
}
