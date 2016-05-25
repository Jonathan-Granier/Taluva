package Joueur;

import Moteur.Moteur;
import terrain.Case.Couleur_Joueur;

//import main.Moteur;

/*
 * Un joueur est défini par :
 *  Son nombre de pions (Tour, Temple , Hutte)
 * 	Sa Couleur
 * 
 * 
 */



public abstract class Joueur_Generique {
	private int temple, tour, hutte,hutteDetruite;
	private Couleur_Joueur c;
	
	public Joueur_Generique(Couleur_Joueur c)
	{
		temple = 3;
		tour = 2;
		hutte = 20;
		hutteDetruite = 0;
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
	
	public Couleur_Joueur getCouleur()
	{
		return c;
	}
	
	// -------------- Fonction Set ---------------------
	
	public void setTemple(int temple)
	{
		this.temple = temple;
	}
	
	public void setTour(int tour)
	{
		this.tour = tour;
	}
	
	public void setHutte(int hutte)
	{
		this.hutte = hutte;
	}
	
	public void setHutteDetruite(int hutteDetruite)
	{
		this.hutteDetruite = hutteDetruite;
	}
	
	public void decrementeTemple()
	{
		if(temple > 0)
		{
			this.temple = temple-1;
		}
	}

	
	public void decrementeTour()
	{
		if(tour > 0)
		{
			this.tour = tour-1;
		}
		
	}
	public void decrementeHutte(int n)
	{
		if(hutte - n >= 0)
		{
			this.hutte = hutte - n;
		}
	}
	
	public void incrementeHutteDetruite(int n)
	{
		this.hutteDetruite = hutteDetruite + n;
	}
	
	// Clone un Joueur_Generique avec Moteur
	//TODO
	//Rajouter les IA en plus
	public Joueur_Generique clone(Moteur m)
	{
		// Instancie le clone en fonction du type de la source
		Joueur_Generique clone = new Joueur_Humain(this.getCouleur());
		
		if(this instanceof IA_Random)
		{
			clone = new IA_Random(this.getCouleur(),m);
		}
		else if(this instanceof IA_Alpha_Beta)
		{
			clone = new IA_Alpha_Beta(((IA_Alpha_Beta) this).getProfondeur(),this.getCouleur(),m);
		}
		
		clone.setHutte(getHutte());
		clone.setTemple(getTemple());
		clone.setTour(getTour());
		clone.setHutteDetruite(getHutteDetruite());
		
		return clone;
	}
	// Clone un Joueur_Generique sans Moteur
	//TODO
	//Rajouter les IA en plus
	public Joueur_Generique clone()
	{
		// Instancie le clone en fonction du type de la source
		Joueur_Generique clone = new Joueur_Humain(this.getCouleur());
		
		if(this instanceof IA_Random)
		{
			clone = new IA_Random(this.getCouleur(),((IA_Generique) this).m);
		}
		else if(this instanceof IA_Alpha_Beta)
		{
			clone = new IA_Alpha_Beta(((IA_Alpha_Beta) this).getProfondeur(),this.getCouleur(),((IA_Generique) this).m);
		}
		
		clone.setHutte(getHutte());
		clone.setTemple(getTemple());
		clone.setTour(getTour());
		clone.setHutteDetruite(getHutteDetruite());
		
		return clone;
	}
	
}
