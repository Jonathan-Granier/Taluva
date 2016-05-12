package main;
import java.util.ArrayList;

public class Moteur {
	private Terrain T;
	private ArrayList<Terrain> annul;
	private ArrayList<Terrain> redo;
	
	Moteur(Terrain T){
		this.T = T;
		annul = new ArrayList<Terrain>();
		//annul.add(T.clone);
		redo = new ArrayList<Terrain>();
	}
	
	//
	
	
	public Terrain getT(){
		return T;
	}
	
	public int setT(Terrain T){
		this.T = T;
		return 0;
	}
}

