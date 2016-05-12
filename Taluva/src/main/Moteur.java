package main;

public class Moteur {
	private Terrain T;
	
	Moteur(Terrain T){
		this.T = T;
	}
	
	//
	public int placer_tuile(Tuile t/*, Position P*/){
		return 0;
	}
	
	public boolean placement_tuile_autorise(Tuile t/*, Position P*/){
		return true;
	}
	
	public int placer_batiment(Batiment b/*, Position P*/){
		return 0;
	}
	
	public boolean placement_batiment_autorise(Batiment b/*, Position P*/){
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

