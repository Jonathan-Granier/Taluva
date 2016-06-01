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
	
	public IA_Generique(Couleur_Joueur c, Moteur m) {
		super(c);
		this.m = m;
	}
	public abstract Actions_Tour get_coup_tour(Tuile tuile);
}
