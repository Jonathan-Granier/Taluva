package Action;

import java.awt.Point;
import terrain.Case;

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
	public Action_Construction(Type t, Point coord)
	{
		this.coord = new Point(coord.x,coord.y);
		if(t == Type.EXTENSION){
			System.out.println("Erreur : constructeur Action_Construction invalide");
		}
		else{
			this.type = t;
		}
		this.type_extension = Case.Type.VIDE;
		this.nb_batiments = 1;
		
	}
	
	// Constructeur d'une action-construction d'extension (en paramètres: 
	// une case de la cité à étendre, le nb de huttes nécessaire et le type de terrain de l'extension).
	public Action_Construction(Point coord, Case.Type type_extension, int nb_huttes)
	{
		this.coord = new Point(coord.x,coord.y);
		this.type = Type.EXTENSION;
		this.type_extension = type_extension;
		this.nb_batiments = nb_huttes;
	}
	
	// Retourne la coordonné de la construction
	public Point get_coord()
	{
		return this.coord;
	}
	// Retourne le type de la construction
	public Type get_type()
	{
		return this.type;
	}
	// Si le type est une extension renvoie sur qu'elle type de terrain elle prend place.
	public Case.Type get_type_extension()
	{
		return this.type_extension;
	}
	// Renvoie le nombre de batiment nécessaire. (surtout interessant pour extension)
	public int get_nb_batiments()
	{
		return this.nb_batiments;
	}
	
	public void afficher()
	{
		System.out.print(this.type + "(" + this.coord.x + "," + this.coord.y + ")");
		if(this.type == Type.EXTENSION)
			System.out.print("[" + this.type_extension + "]");
	}
}
