package main;

public class Piece {
	public enum Type_p{
		HUTTE,
		TEMPLE,
		TOUR;
	}
	
	private Type_p type_p;

	Piece(Type_p type, Type_p type_p){
		this.type_p = type_p;
	}
	
	//Renvoie le type de la piece
	public Type_p get_Type() {
		return type_p;
	}

	//Modifie le type de la piece
	public void set_Type(Type_p type_p) {
		this.type_p = type_p;
	}
	
}
