package main;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import terrain.*;
import terrain.Terrain.Coord;

public class Liste_coup_tuile {

	private ArrayList<Point> coup_O;
	private ArrayList<Point> coup_N_O;
	private ArrayList<Point> coup_N_E;
	private ArrayList<Point> coup_E;
	private ArrayList<Point> coup_S_E;
	private ArrayList<Point> coup_S_O;
	private Point coup_rand_coord;
	private Case.Orientation coup_rand_orientation;
	
	// un coup est possible si: Il recouvre 3 cases de niveau égales
	// Si le niveau est 0, une case au moins est voisine d'une case de niveau 1 ou plus.
	// Si le niveau est 1 ou plus, le volcan recouvre un volcan 
	//								&& il n'a pas la même direction.
	// 								&& il ne recouvre pas une cité entière.
	
	Liste_coup_tuile (Terrain t)
	{
		// Stratégie: Parcours de toutes les cases.
		Tuile tuile = new Tuile(Case.Type.VIDE, Case.Type.VIDE);
		Point p;
		Case.Orientation orientation;
		Coord limites = t.getLimites();
		for(int o =0; o <6; o++)
		{
			
			for(int h = limites.xmin -2; h < limites.xmax +1; h++)
			{
				for( int l= limites.ymin -2; l < limites.ymax +2 ; l++)
				{
					p = new Point(l,h);
					if(t.placement_tuile_autorise(tuile,p))
					{
						orientation = tuile.get_Orientation_Volcan();
						switch(orientation)
						{
							case O:
								coup_O.add(p);
								break;
							case N_O:
								coup_N_O.add(p);
								break;
							case N_E:
								coup_N_E.add(p);
								break;
							case E:
								coup_E.add(p);
								break;
							case S_E:
								coup_S_E.add(p);
								break;
							case S_O:
								coup_S_O.add(p);
								break;
							default:
								System.out.println("list_coup[Constructeur]: L'orientation n'est pas correcte.");
								break;
						}
					}
				}
			}
			try {
				tuile.Tourner_horaire();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	// renvoie vrai si le placement de la tuile est valide.
	// Risque de devoir changer le .contains, ce n'est pas sur qu'il fonctionnera.
	public boolean coup_possible ( Case.Orientation o, Point p)
	{
		switch(o)
		{
			case O:
				return coup_O.contains(p);
			case N_O:
				return coup_N_O.contains(p);
			case N_E:
				return coup_N_E.contains(p);
			case E:
				return coup_E.contains(p);
			case S_E:
				return coup_S_E.contains(p);
			case S_O:
				return coup_S_O.contains(p);
			default:
				System.out.println("list_coup[Coup_possible]: L'orientation demandé n'est pas correcte.");
				return false;
		}
	}
	
	public void affichage()
	{
		System.out.println(" Coup O:");
		for(int i=0; i < coup_O.size(); i++)
		{
			System.out.print("(" + coup_O.get(i).x + "," + coup_O.get(i).y + ")");
		}
		System.out.println("");
		
		System.out.println(" Coup N_O:");
		for(int i=0; i < coup_O.size(); i++)
		{
			System.out.print("(" + coup_N_O.get(i).x + "," + coup_N_O.get(i).y + ")");
		}
		System.out.println("");
		
		System.out.println(" Coup N_E:");
		for(int i=0; i < coup_O.size(); i++)
		{
			System.out.print("(" + coup_N_E.get(i).x + "," + coup_N_E.get(i).y + ")");
		}
		System.out.println("");
		
		System.out.println(" Coup E:");
		for(int i=0; i < coup_E.size(); i++)
		{
			System.out.print("(" + coup_E.get(i).x + "," + coup_E.get(i).y + ")");
		}
		System.out.println("");
		
		System.out.println(" Coup S_E:");
		for(int i=0; i < coup_S_E.size(); i++)
		{
			System.out.print("(" + coup_S_E.get(i).x + "," + coup_S_E.get(i).y + ")");
		}
		System.out.println("");
		
		System.out.println(" Coup S_O:");
		for(int i=0; i < coup_S_O.size(); i++)
		{
			System.out.print("(" + coup_S_O.get(i).x + "," + coup_S_O.get(i).y + ")");
		}
		System.out.println("");
	}
	
	// Calcul un coup random, renvoie -1 s'il échoue.
	public int next_coup_random()
	{
		int nb_action = 0;
		nb_action += coup_O.size();
		nb_action += coup_S_O.size();
		nb_action += coup_N_O.size();
		nb_action += coup_E.size();
		nb_action += coup_S_E.size();
		nb_action += coup_N_E.size();

		Random r = new Random();
		int index = r.nextInt(nb_action);
		if(index < coup_O.size())
		{
			this.coup_rand_coord = coup_O.get(index);
			this.coup_rand_orientation = Case.Orientation.O;
			return index;
		}
		index -= coup_O.size();
		if(index < coup_S_O.size())
		{
			this.coup_rand_coord = coup_S_O.get(index);
			this.coup_rand_orientation = Case.Orientation.S_O;
			return index;
		}
		index -= coup_S_O.size();
		if(index < coup_N_O.size())
		{
			this.coup_rand_coord = coup_N_O.get(index);
			this.coup_rand_orientation = Case.Orientation.N_O;
			return index;
		}
		index -= coup_N_O.size();
		if(index < coup_E.size())
		{
			this.coup_rand_coord = coup_E.get(index);
			this.coup_rand_orientation = Case.Orientation.E;
			return index;
		}
		index -= coup_E.size();
		if(index < coup_S_E.size())
		{
			this.coup_rand_coord = coup_S_E.get(index);
			this.coup_rand_orientation = Case.Orientation.S_E;
			return index;
		}
		index -= coup_S_E.size();
		if(index < coup_N_E.size())
		{
			this.coup_rand_coord = coup_N_E.get(index);
			this.coup_rand_orientation = Case.Orientation.N_E;
			return index;
		}
		index -= coup_N_E.size();
		System.out.println("L'index random tiré n'est pas correct ou il n'y avait pas de coup possible. RNG:" + index);
		return -1;
	}
	
	// Renvoie les coordonées du point random calculé
	public Point coup_rand_coord()
	{
		return this.coup_rand_coord;
	}
	// Renvoie l'orienation du coup random calculé
	public Case.Orientation coup_rand_orienation()
	{
		return this.coup_rand_orientation;
	}
}
