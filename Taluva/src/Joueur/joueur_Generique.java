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
	private int temple, tour, hutte,hutteDetruite , score;
	private Color c;
	private Moteur m;
	
	public joueur_Generique(Color couleur_joueur, Moteur m)
	{
		temple = 3;
		tour = 2;
		hutte = 20;
		hutteDetruite = 0;
		score = 0;
		this.c = couleur_joueur;
		this.m = m;
		
	}
	// -------------- Fonction Get ---------------------
	
	public int getTemple()
	{
		return temple;
	}
	
	public int getTour()
	{
		return tour;
	}
	
	public int getHutte()
	{
		return hutte;
	}
	
	public int getHutteDetruite()
	{
		return hutteDetruite;
	}
	
	public int getScore()
	{
		return score;
	}
	
	public Color get_couleur()
	{
		return c;
	}
	
	// -------------- Fonction Set ---------------------
	
	public void decrementeTemple()
	{
		if(temple > 0)
		{
			this.temple = temple;
		}
	}

	
	public void decrementeTour()
	{
		if(tour > 0)
		{
			this.tour = tour;
		}
		
	}
	public void decrementeHutte(int n)
	{
		if(hutte - n > 0)
		{
			this.hutte = hutte - n;
		}
	}
	
	public void incrementeHutteDetruite(int n)
	{
		this.hutteDetruite = hutteDetruite + n;
	}
	
	public void setScore(int score)
	{
		this.score = score;
	}
	
	public abstract void jouer();
	
	

	
}
