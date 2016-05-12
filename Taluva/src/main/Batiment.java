package main;

public class Batiment {

	public enum Type{
		HUTTE,
		TOUR,
		TEMPLE;
	}
	
	Type t;
	
	Batiment(Type t){
		this.t = t;
	}
}
