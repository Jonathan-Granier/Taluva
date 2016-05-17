package main;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import Joueur.joueur_Generique;
import terrain.Case;
import terrain.Case.Couleur_Joueur;
import terrain.Terrain;
import terrain.Terrain.Coord;

public class List_coup_construction {
	private ArrayList<Point> huttes;
	private ArrayList<Action_Construction> extension;
	private ArrayList<Point> tour;
	private ArrayList<Point> temple;
	
	List_coup_construction(Terrain t, joueur_Generique joueur)
	{
		Point p;
		Coord limites = t.getLimites();
		for(int i= limites.xmin; i< limites.xmax; i++)
		{
			for(int j = limites.ymin; j < limites.ymax; j++)
			{
				p = new Point(i,j);
				if(t.placement_batiment_autorise(Case.Type_Batiment.HUTTE, joueur.getCouleur(), p))
				{
					huttes.add(p);
				}
				if(t.placement_batiment_autorise(Case.Type_Batiment.TEMPLE, joueur.getCouleur(), p))
				{
					temple.add(p);
				}
				if(t.placement_batiment_autorise(Case.Type_Batiment.TOUR, joueur.getCouleur(), p))
				{
					temple.add(p);
				}
				if()
			}
		}
	}
	
	public boolean coup_possible (Action_Construction a)
	{
		switch(a.get_type())
		{
			case HUTTE:
				return huttes.contains(a.get_coord());
			case EXTENSION:
				return extension.contains(a);
			case TEMPLE:
				return temple.contains(a.get_coord());
			case TOUR:
				return tour.contains(a.get_coord());
			default:
				System.out.println("List_coup_construction [coup_possible]:Le type d'action de construction est incorrecte.");
		}
		return false;
	}
	
}
