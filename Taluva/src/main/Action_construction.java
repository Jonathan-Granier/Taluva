package main;

import java.awt.Point;

public class Action_construction {

	public enum Type{
		HUTTE,
		EXTENSION,
		TOUR,
		TEMPLE;
	}
	private Type type;
	private Case.Type type_extenstion;
	private Point coord;
	
	// Constructeur d'une action-construction hors extension
	Action_construction(Type t, Point coord)
	{
		this.coord = coord;
		this.type =t;
	}
	
	// Constructeur d'une action-construction d'extension (en coordonnée: mettre une case de la cité à étendre).
	Action_construction(Type t, Point coord, Case.Type type_extension)
	{
		this.coord = coord;
		this.type = t;
		this.type_extenstion = type_extenstion;
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
		if(this.ty)
		return this.type_extenstion;
	}
}
