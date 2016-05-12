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
	public int placer_tuile(Tuile t/*, Position P*/){
		return 0;
	}
	
	public boolean placement_tuile_autorise(Tuile t/*, Position P*/){
		return true;
	}
	
	public int placer_batiment(Case.Type_Batiment b/*, Position P*/){
		return 0;
	}
	
	public boolean placement_batiment_autorise(Case.Type_Batiment b/*, Position P*/){
		return true;
	}
	
	public Terrain getT(){
		return T;
	}
	
	public int setT(Terrain T){
		this.T = T;
		return 0;
	}
}

