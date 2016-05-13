package main;

import java.awt.Point;
import java.util.ArrayList;

public class List_coup_construction {
	private ArrayList<Point> huttes;
	private ArrayList<Point> extension;
	private ArrayList<Point> tour;
	private ArrayList<Point> temple;
	
	List_coup_construction(Terrain t)
	{
		
	}
	
	public boolean coup_possible (Action_construction a)
	{
		switch(a.get_type())
		{
			case HUTTE:
				return huttes.contains(a.get_coord());
			case EXTENSION:
				return extension.contains(a.get_coord());
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
