package Joueur;

import java.awt.Color;

import main.Moteur;

/*
 * Un joueur est défini par :
 *  Son nombre de pions (Tour, Temple , Hutte)
 * 	Sa Couleur
 * 
 * La fonction Jouer() est défini par ses fils
 * 
 * 
 */





public abstract class joueur_Generique {
	private int tour, hutte, temple, score;
	private Color c;
	private Moteur m;
	
	public joueur_Generique(Color couleur_joueur, Moteur m)
	{
		tour = 2;
		hutte = 20;
		temple = 3;
		this.c = couleur_joueur;
		score = 0;
		
		this.m = m;
		
	}
	public int getTour()
	{
		return tour;
	}
	
	public int getHutte()
	{
		return hutte;
	}
	public int getTemple()
	{
		return temple;
	}
	public int getScore()
	{
		return score;
	}
	
	public void setTour(int tour)
	{
		this.tour = tour;
	}
	public void setHutte(int hutte)
	{
		this.hutte = hutte;
	}
	public void setTemple(int temple)
	{
		this.temple = temple;
	}
	public void setScore(int score)
	{
		this.score = score;
	}
	
	public abstract void jouer();
	
	

	
}
