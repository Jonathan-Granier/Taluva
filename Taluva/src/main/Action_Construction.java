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
	
	// Constructeur d'une action-construction hors extension
	Action_Construction(Type t, Point coord)
	{
		this.coord = coord;
		this.type =t;
		this.type_extension = Case.Type.VIDE;
		
	}
	
	// Constructeur d'une action-construction d'extension (en coordonnée: mettre une case de la cité à étendre).
	Action_Construction(Type t, Point coord, Case.Type type_extension)
	{
		this.coord = coord;
		this.type = t;
		this.type_extension = type_extension;
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
}
