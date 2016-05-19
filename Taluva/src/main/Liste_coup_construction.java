package main;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import Joueur.joueur_Generique;
import terrain.Case;
import terrain.Terrain;
import terrain.Terrain.Coord;

public class Liste_coup_construction {
	private ArrayList<Point> huttes;
	private ArrayList<Action_Construction> extension;
	private ArrayList<Point> tour;
	private ArrayList<Point> temple;
	
	Liste_coup_construction(Terrain t, joueur_Generique joueur)
	{
		Point p;
		Coord limites = t.getLimites();
		int nb_huttes;
		Case.Type type_extension;
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
				if(t.getCase(i, j).getCouleur() == joueur.getCouleur())
				{
					// Gestion de tous les types d'extension
					Case.Type [] types = Case.Type.values();
					for(int k=0;k<types.length;k++){
						if(types[k] != Case.Type.VIDE && types[k] != Case.Type.VOLCAN){
							type_extension = types[k];
							nb_huttes = t.nb_huttes_extension(p, type_extension);
							if(nb_huttes < joueur.getHutte())
							{
								extension.add(new Action_Construction(p, type_extension, nb_huttes));
							}
						}
					}
				}
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
	
	// Renvoie une action de construction aléatoire.
	public Action_Construction get_random_action()
	{
		int nb_action = huttes.size() + extension.size() + temple.size() + tour.size();
		Random r = new Random();
		int index = r.nextInt(nb_action);
		if(index < huttes.size())
			return new Action_Construction(Action_Construction.Type.HUTTE, huttes.get(index));
		index -= huttes.size();
		if(index < extension.size())
			return extension.get(index);
		index -= extension.size();
		if(index < temple.size())
			return new Action_Construction(Action_Construction.Type.TEMPLE, temple.get(index));
		index -= temple.size();
		if(index < tour.size())
			return new Action_Construction(Action_Construction.Type.TOUR, tour.get(index));
		System.out.println("L'index random tiré n'est pas correct ou il n'y avait pas d'action possible. RNG:" + index);
		return null;
	}
	
	public void affichage()
	{
		System.out.println("Liste des coups: huttes");
		for(int i=0; i< huttes.size(); i++)
		{
			System.out.print("(" +huttes.get(i).x +"," + huttes.get(i).y +")");
		}
		System.out.println("Liste des coups: tour");
		for(int i=0; i< huttes.size(); i++)
		{
			System.out.print("(" +tour.get(i).x +"," + tour.get(i).y +")");
		}
		System.out.println("Liste des coups: temple");
		for(int i=0; i< huttes.size(); i++)
		{
			System.out.print("(" +temple.get(i).x +"," + temple.get(i).y +")");
		}
		System.out.println("liste des coups: extension");
		for(int i=0; i< this.extension.size(); i++)
		{
			this.extension.get(i).affichage();
		}
	}
	
}
