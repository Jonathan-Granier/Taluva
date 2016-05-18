package main;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import Joueur.joueur_Generique;
import terrain.Case;
import terrain.Case.Couleur_Joueur;
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
					type_extension = Case.Type.FORET;
					nb_huttes = t.nb_huttes_extension(p, type_extension);
					if(nb_huttes < joueur.getHutte())
					{
						extension.add(new Action_Construction(p, type_extension, nb_huttes));
					}
					type_extension = Case.Type.LAC;
					nb_huttes = t.nb_huttes_extension(p, type_extension);
					if(nb_huttes < joueur.getHutte())
					{
						extension.add(new Action_Construction(p, type_extension, nb_huttes));
					}
					type_extension = Case.Type.MONTAGNE;
					nb_huttes = t.nb_huttes_extension(p, type_extension);
					if(nb_huttes < joueur.getHutte())
					{
						extension.add(new Action_Construction(p, type_extension, nb_huttes));
					}
					type_extension = Case.Type.PLAINE;
					nb_huttes = t.nb_huttes_extension(p, type_extension);
					if(nb_huttes < joueur.getHutte())
					{
						extension.add(new Action_Construction(p, type_extension, nb_huttes));
					}
					type_extension = Case.Type.SABLE;
					nb_huttes = t.nb_huttes_extension(p, type_extension);
					if(nb_huttes < joueur.getHutte())
					{
						extension.add(new Action_Construction(p, type_extension, nb_huttes));
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
	public Action_Construction get_random_action() throws Exception
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
		System.out.println("L'index random tiré n'est pas correct.");
		throw new Exception("L'index est incorrect (interne ou pas d'action possible.)");
	}
	
}
