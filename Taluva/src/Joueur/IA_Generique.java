package Joueur;

import java.awt.Point;
import java.util.ArrayList;

import Action.Action_Tuile;
import Action.Actions_Tour;
import Moteur.Moteur;
import terrain.Case.Couleur_Joueur;
import terrain.Case;
import terrain.Cite;
import terrain.Terrain;
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
	protected static int score_case_3_adj = 300;
	protected static int score_case_2_adj = 5;
	protected static int score_reduction_volcan_adj = 3;
	
	public IA_Generique(Couleur_Joueur c, Moteur m) {
		super(c);
		this.m = m;
	}
	
	public abstract Actions_Tour get_coup_tour(Tuile tuile);
	
	protected static int Calculer_points_heur(Joueur_Generique c, Moteur m) {
		// on compte le score d'un joueur dans le terrain.
		int score =0;
		score += (Moteur.nb_max_Temples - c.getTemple()) * score_temple;
		score += (Moteur.nb_max_Tours - c.getTour()) * score_tour;
		//score += (Moteur.nb_max_Huttes - c.getHutte()) * score_hutte;
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
			score += valeur_cite(liste_cite.get(i), c, m);
		return score;
	}
	
	// Calculer la valeur d'une cité.
	private static int valeur_cite(Cite c, Joueur_Generique j, Moteur m)
	{
		int score_cite = score_city + c.getTaille() * score_zone_city;

		if(c.getNbTemples() == 0 && j.getTemple()>0){
			score_cite += c.getTaille() * score_taille_cite_sans_temple;
			if(c.getTaille()>=3){
				if(cite_non_reductible_sous_3(c,m.getTerrain())){
					score_cite += score_cite_indestructible_sans_temple;
				}
				else{
					score_cite = 0;
				}
			}
		}
		
		// Si la cité permet de construire une tour
		if(c.getNbTours() == 0 && j.getTour()>0){
			for(Case.Type t : Case.Type.values()){
				ArrayList<Point> ptsVoisins = m.getTerrain().getPts_extension_cite(c, t);
				for(Point p : ptsVoisins){
					if(m.getTerrain().getCase(p).est_Libre() && m.getTerrain().getCase(p).getType() != Case.Type.VOLCAN){
						if(m.getTerrain().getCase(p).getNiveau()>=3){
							if(j.getTemple() != 0)
								score_cite+=score_case_3_adj;
							else
								score_cite+=score_case_3_adj*2;
						}
						else if(m.getTerrain().getCase(p).getNiveau()==2){
							if(j.getTemple() != 0)
								score_cite+=score_case_2_adj;
							else
								score_cite+=score_case_2_adj*2;
						}
					}
				}
			}
		}
		if(c.getNbTemples() > 0)
		{
			score_cite -= c.getTaille() * score_cite_petite_avec_temple;
		}
		if( c.getNbTemples()>0 && c.getNbTours()>0)
		{
			score_cite = score_cite / score_div_city_temple_tower;
		}
		return score_cite;
	}
	
	private static boolean cite_non_reductible_sous_3(Cite c, Terrain T){
		if(c.getTaille()<3) return false;
		boolean non_reductible ;
		for(Action_Tuile actTuile : T.liste_coups_tuile_possibles(new Tuile(Case.Type.FORET,Case.Type.FORET))){
			// On simule tous les coups tuile
			non_reductible = false;
			Terrain T_after = T.consulter_coup_tuile(actTuile.getTuile(), actTuile.getPosition());
			for(Point P : c.getPts()){
				// Pour chaque point de l'ancienne cite, on verifie qu'il reste au moins une taille 3 parmi eux
				Cite cite;
				cite = T_after.getCite(P);
				if(cite.getCouleur() != Couleur_Joueur.NEUTRE && cite.getTaille() >= 3)
					non_reductible = true;
			}
			if(!non_reductible)
				return false;
		}
		return true;
	}
	
	protected static int Calculer_points_heur_joueur_adverse(Joueur_Generique c, Moteur m) {
		// on compte le score d'un joueur dans le terrain.
		int score =0;
		score += (Moteur.nb_max_Temples - c.getTemple()) * score_temple;
		score += (Moteur.nb_max_Tours - c.getTour()) * score_tour;
		//score += (Moteur.nb_max_Huttes - c.getHutte()) * score_hutte;
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
			score += valeur_cite_adverse(liste_cite.get(i), c, m);
		return score;
	}
	
	private static int valeur_cite_adverse(Cite c, Joueur_Generique j, Moteur m)
	{
		int score_cite = score_city + c.getTaille() * score_zone_city;

		if(c.getNbTemples() == 0 && j.getTemple()>0){
			score_cite += c.getTaille() * score_taille_cite_sans_temple;
			if(c.getTaille()>=3){
				score_cite += score_temple;
			}
		}
		
		if(c.getNbTemples() == 0){
			for(Case.Type t : Case.Type.values()){
				ArrayList<Point> ptsVoisins = m.getTerrain().getPts_extension_cite(c, t);
				for(Point p : ptsVoisins){
					if(m.getTerrain().getCase(p).getType() == Case.Type.VOLCAN){
						score_cite -= score_reduction_volcan_adj;
					}
				}
			}
		}
		
		// Si la cité permet de construire une tour
		if(c.getNbTours() == 0 && j.getTour()>0){
			for(Case.Type t : Case.Type.values()){
				ArrayList<Point> ptsVoisins = m.getTerrain().getPts_extension_cite(c, t);
				for(Point p : ptsVoisins){
					if(m.getTerrain().getCase(p).est_Libre() && m.getTerrain().getCase(p).getType() != Case.Type.VOLCAN){
						if(m.getTerrain().getCase(p).getNiveau()>=3){
							score_cite+=score_tour;
						}
						else if(m.getTerrain().getCase(p).getNiveau()==2){
							score_cite+=score_case_2_adj;
						}
					}
				}
			}
		}
		if(c.getNbTemples() > 0)
		{
			score_cite -= c.getTaille() * score_cite_petite_avec_temple;
		}
		if( c.getNbTemples()>0 && c.getNbTours()>0)
		{
			score_cite = score_cite / score_div_city_temple_tower;
		}
		return score_cite;
	}
}
