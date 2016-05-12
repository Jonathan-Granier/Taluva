package main;

public class Case {
	
	public enum Type_c{
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
	
	private Type_c type_c;
	private Orientation orientation;
	
	Case(Type_c type_c){
		this.type_c = type_c;
		orientation = Orientation.N;
	}
	
	Case(Type_c type_c, Orientation orientation){
		this.type_c = type_c;
		this.orientation = orientation;
	}
	
	// Renvoie le type de la case
	public Type_c getType(){
		return type_c;
	}
	
	// Renvoie l'orientation de la case. N'a de sens que si getType()=OrientationCase.VOLCAN
	public Orientation getOrientation(){
		return orientation;
	}
}
