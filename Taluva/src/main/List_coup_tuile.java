package main;

import java.awt.Point;
import java.util.ArrayList;

import main.Terrain.Coord;

public class List_coup_tuile {

	private ArrayList<Point> coup_O;
	private ArrayList<Point> coup_N_O;
	private ArrayList<Point> coup_N_E;
	private ArrayList<Point> coup_E;
	private ArrayList<Point> coup_S_E;
	private ArrayList<Point> coup_S_O;
	
	// un coup est possible si: Il recouvre 3 cases de niveau égales
	// Si le niveau est 0, une case au moins est voisine d'une case de niveau 1 ou plus.
	// Si le niveau est 1 ou plus, le volcan recouvre un volcan 
	//								&& il n'a pas la même direction.
	// 								&& il ne recouvre pas une cité entière.
	
	List_coup_tuile (Terrain t)
	{
		// Stratégie: Parcours de toutes les cases.
		Tuile tuile = new Tuile(Case.Type.VIDE, Case.Type.VIDE);
		Point p;
		Case.Orientation orientation;
		Coord limites = t.getLimites();
		for(int o =0; o <6; o++)
		{
			orientation = tuile.get_Orientation_Volcan();
			for(int h = limites.xmin -2; h < limites.xmax +1; h++)
			{
				for( int l= limites.ymin -2; l < limites.ymax +2 ; l++)
				{
					p = new Point(l,h);
					if(t.placement_tuile_autorise(tuile,p))
					{
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
			tuile.Tourner_horaire();
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
}
