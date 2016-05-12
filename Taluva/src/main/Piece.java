package main;

public class Piece {
	public enum Type{
		HUTTE,
		TEMPLE,
		TOUR;
	}
	
	private Type type;

	Piece(Type type){
		this.type = type;
	}
	
	public Type get_Type() {
		return type;
	}

	public void set_Type(Type type) {
		this.type = type;
	}
	
}
