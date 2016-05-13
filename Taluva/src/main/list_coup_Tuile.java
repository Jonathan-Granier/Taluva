package main;

import java.awt.Point;
import java.util.ArrayList;

public class list_coup_Tuile {

	private ArrayList<Point> coup_O;
	private ArrayList<Point> coup_N_O;
	private ArrayList<Point> coup_N_E;
	private ArrayList<Point> coup_E;
	private ArrayList<Point> coup_S_E;
	private ArrayList<Point> coup_S_O;
	
	// un coup est possible si: Il recouvre 3 cases de niveau égales
	// Si le niveau est 0, une case au moins est voisine d'une case de niveau 1 ou plus.
	// Si le niveau est 1 ou plus, le volcan recouvre un volcan && il n'a pas la même direction.
	
	list_coup_Tuile (Terrain t)
	{
		// Stratégie: Spirale autour du centre?
		// Stratégie: Parcours de toutes les cases.
	}
	
	// renvoie vrai si le placement de la tuile est valide.
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
}
