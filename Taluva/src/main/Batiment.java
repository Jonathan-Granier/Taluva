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
	public Type getType() {
		return t;
	}

	//Modifie le type du batiment
	public void setType(Type t) {
		this.t = t;
	}
		
}
