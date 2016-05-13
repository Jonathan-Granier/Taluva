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
	private Point coord;
	
	Action_construction(Type t, Point coord)
	{
		this.coord = coord;
		this.type =t;
	}
	
	public Point get_coord()
	{
		return this.coord;
	}
	public Type get_type()
	{
		return this.type;
	}
}
