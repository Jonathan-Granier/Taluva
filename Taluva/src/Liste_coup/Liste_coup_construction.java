package Liste_coup;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import Action.Action_Construction;
import Joueur.Joueur_Generique;
import terrain.Case;
import terrain.Terrain;
import terrain.Terrain.Coord;

public class Liste_coup_construction {
	private ArrayList<Point> huttes;
	private ArrayList<Action_Construction> extension;
	private ArrayList<Point> tour;
	private ArrayList<Point> temple;
	
	public Liste_coup_construction(Terrain t, Joueur_Generique joueur)
	{
		Point p;
		Coord limites = t.getLimites();
		huttes = new ArrayList<Point>();
		tour = new ArrayList<Point>();
		temple= new ArrayList<Point>();
		extension = new ArrayList<Action_Construction>();
		for(int i= limites.xmin; i<= limites.xmax; i++)
		{
			for(int j = limites.ymin; j <= limites.ymax; j++)
			{
				p = new Point(i,j);
				if(t.placement_batiment_autorise(Case.Type_Batiment.HUTTE, joueur.getCouleur(), p) && joueur.getHutte() >0)
				{
					huttes.add(new Point(p));
				}
				if(t.placement_batiment_autorise(Case.Type_Batiment.TEMPLE, joueur.getCouleur(), p) && joueur.getTemple() >0)
				{
					temple.add(new Point(p));
				}
				if(t.placement_batiment_autorise(Case.Type_Batiment.TOUR, joueur.getCouleur(), p) && joueur.getTour() >0 )
				{
					tour.add(new Point(p));
				}
			}
		}
		// On récupère les extension et on enlève celle pour lesquelles on a pas assez de huttes.
		ArrayList <Action_Construction> extension_tmp = t.liste_extensions_possibles(joueur.getCouleur());
		for(int i=0; i< extension_tmp.size(); i++)
		{
			if( joueur.getHutte() >= extension_tmp.get(i).get_nb_batiments())
				this.extension.add(extension_tmp.get(i));
		}
	}
	
	public Liste_coup_construction() {
		huttes = new ArrayList<Point>();
		tour = new ArrayList<Point>();
		temple= new ArrayList<Point>();
		extension = new ArrayList<Action_Construction>();
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
		//System.out.println("Il y a "+ nb_action + " action possibles.");
		int index = r.nextInt(nb_action);
		//System.out.println("J'ai tiré: " + index);

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
	
	public void afficher()
	{
		System.out.println("Liste des coups: huttes");
		for(int i=0; i< huttes.size(); i++)
		{
			System.out.print("(" +huttes.get(i).x +"," + huttes.get(i).y +")");
		}
		System.out.println();
		System.out.println("Liste des coups: tour");
		for(int i=0; i< tour.size(); i++)
		{
			System.out.print("(" +tour.get(i).x +"," + tour.get(i).y +")");
		}
		System.out.println();
		System.out.println("Liste des coups: temple");
		for(int i=0; i< temple.size(); i++)
		{
			System.out.print("(" +temple.get(i).x +"," + temple.get(i).y +")");
		}
		System.out.println();
		System.out.println("liste des coups: extension");
		for(int i=0; i< this.extension.size(); i++)
		{
			this.extension.get(i).afficher();
		}
		System.out.println();
	}
	
	public Liste_coup_construction clone()
	{
		Liste_coup_construction liste = new Liste_coup_construction();
		liste.extension = new ArrayList<Action_Construction>();
		for(int i =0; i < this.extension.size(); i++)
		{
			liste.extension.add(this.extension.get(i));
		}
		liste.huttes = new ArrayList<Point>();
		for(int i =0; i < this.huttes.size(); i++)
		{
			liste.huttes.add(this.huttes.get(i));
		}
		liste.temple = new ArrayList<Point>();
		for(int i =0; i < this.temple.size(); i++)
		{
			liste.temple.add(this.temple.get(i));
		}
		liste.tour = new ArrayList<Point>();
		for(int i =0; i < this.tour.size(); i++)
		{
			liste.tour.add(this.tour.get(i));
		}
		return null;
	}
	
	public int size()
	{
		return this.extension.size() + this.huttes.size() + this.temple.size() + this.tour.size();
	}
	
	public ArrayList<Action_Construction> to_ArrayList()
	{
		ArrayList<Action_Construction> res = new ArrayList<Action_Construction>();
		for(int i =0; i < this.tour.size(); i++)
		{
			res.add(new Action_Construction(Action_Construction.Type.TOUR, tour.get(i)));
		}
		for(int i =0; i < this.temple.size(); i++)
		{
			res.add(new Action_Construction(Action_Construction.Type.TEMPLE, temple.get(i)));
		}
		for(int i =0; i < this.huttes.size(); i++)
		{
			res.add(new Action_Construction(Action_Construction.Type.HUTTE, huttes.get(i)));
		}
		for(int i =0; i < this.extension.size(); i++)
		{
			res.add(this.extension.get(i));
		}
		return res;
	}
	public ArrayList<Action_Construction> into_ArrayList(ArrayList<Action_Construction> dest)
	{
		dest.clear();
		for(int i =0; i < this.tour.size(); i++)
		{
			dest.add(new Action_Construction(Action_Construction.Type.TOUR, tour.get(i)));
		}
		for(int i =0; i < this.temple.size(); i++)
		{
			dest.add(new Action_Construction(Action_Construction.Type.TEMPLE, temple.get(i)));
		}
		for(int i =0; i < this.huttes.size(); i++)
		{
			dest.add(new Action_Construction(Action_Construction.Type.HUTTE, huttes.get(i)));
		}
		for(int i =0; i < this.extension.size(); i++)
		{
			dest.add(this.extension.get(i));
		}
		return dest;
	}
}
