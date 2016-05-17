package Joueur;

import java.awt.Color;

import terrain.Case.Couleur_Joueur;

//import main.Moteur;

/*
 * Un joueur est défini par :
 *  Son nombre de pions (Tour, Temple , Hutte)
 * 	Sa Couleur
 * 
 * 
 */



public abstract class joueur_Generique {
	private int temple, tour, hutte,hutteDetruite , score;
	private Couleur_Joueur c;
	
	public joueur_Generique(Couleur_Joueur c)
	{
		temple = 3;
		tour = 2;
		hutte = 20;
		hutteDetruite = 0;
		score = 0;
		this.c = c;
		
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
	
	public Couleur_Joueur getCouleur()
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
	
}
