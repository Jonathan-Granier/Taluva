package main;

import java.awt.Point;

public class Action_Construction {

	public enum Type{
		HUTTE,
		EXTENSION,
		TOUR,
		TEMPLE;
	}
	private Type type;
	private Case.Type type_extension;
	private Point coord;
	private int nb_batiments;
	
	// Constructeur d'une action-construction hors extension
	Action_Construction(Type t, Point coord)
	{
		this.coord = coord;
		if(t == Type.EXTENSION){
			System.out.println("Erreur : constructeur Action_Construction invalide");
		}
		else{
			this.type = t;
		}
		this.type_extension = Case.Type.VIDE;
		this.nb_batiments = 1;
		
	}
	
	// Constructeur d'une action-construction d'extension (en coordonnée: mettre une case de la cité à étendre).
	Action_Construction(Point coord, Case.Type type_extension, int nb_huttes)
	{
		this.coord = coord;
		this.type = Type.EXTENSION;
		this.type_extension = type_extension;
		this.nb_batiments = nb_huttes;
	}
	
	public Point get_coord()
	{
		return this.coord;
	}
	public Type get_type()
	{
		return this.type;
	}
	public Case.Type get_type_extension()
	{
		return this.type_extension;
	}
	public int get_nb_batiments()
	{
		return this.nb_batiments;
	}
}
