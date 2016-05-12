package main;

import java.awt.Point;

public class Terrain {
	
	private final static int TAILLE = 200;
	private final static Point CENTRE = new Point(TAILLE/2,TAILLE/2);
	
	private Case [][] t;
	
	Terrain(){
		t = new Case[TAILLE][TAILLE];
		for(int i=0;i<TAILLE;i++){
			for(int j=0;j<TAILLE;j++){
				t[i][j] = new Case(Case.Type.VIDE);
			}
		}
	}
	
	public int placer_tuile(Tuile t, Point P){
		return 0;
	}
	
	public boolean placement_tuile_autorise(Tuile t, Point P){
		return true;
	}
	
	public int placer_batiment(Case.Type_Batiment b, Point P){
		return 0;
	}
	
	public boolean placement_batiment_autorise(Case.Type_Batiment b, Point P){
		return true;
	}
}
