package main;

public class Batiment {

	public enum Type{
		HUTTE,
		TOUR,
		TEMPLE;
	}
	
	private Type t;
	
	Batiment(Type t){
		this.t = t;
	}
	
	//Renvoie le type du batiment
	public Type get_Type() {
		return t;
	}

	//Modifie le type du batiment
	public void set_Type(Type t) {
		this.t = t;
	}
		
}
