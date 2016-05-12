package main;

public class Case {
	
	public enum Type{
		VOLCAN,
		MONTAGNE,
		PLAINE,
		LAC,
		SABLE,
		FORET;
	}
	
	public enum Orientation{
		N,
		N_E,
		N_O,
		S,
		S_O,
		S_E;
	}
	
	private Type type;
	private Orientation orientation;
	
	Case(Type type){
		this.type = type;
		orientation = Orientation.N;
	}
	
	Case(Type type, Orientation orientation){
		this.type = type;
		this.orientation = orientation;
	}
	
	// Renvoie le type de la case
	public Type getType(){
		return type;
	}
	
	// Renvoie l'orientation de la case. N'a de sens que si getType()=OrientationCase.VOLCAN
	public Orientation getOrientation(){
		return orientation;
	}
}
