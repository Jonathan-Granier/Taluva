package main;

import java.awt.Point;

public class Terrain {
	
	private final static int TAILLE = 200;
	private final static Point CENTRE = new Point(TAILLE/2,TAILLE/2);
	
	private Case [][] t;
	private boolean empty;
	
	Terrain(){
		t = new Case[TAILLE][TAILLE];
		for(int i=0;i<TAILLE;i++){
			for(int j=0;j<TAILLE;j++){
				t[i][j] = new Case(Case.Type.VIDE);
			}
		}
		empty = true;
	}
	
	public Terrain clone(){
		Terrain tmp = new Terrain();
		for(int i=0;i<TAILLE;i++){
			for(int j=0;j<TAILLE;j++){
				tmp.t[i][j] = this.t[i][j];
			}
		}
		tmp.empty = this.empty;
		return tmp;
	}
	//		  __      __
	//		 /  \    /  \
	//	  __/1,0 \__/3,0 \
	//	 /	\    /  \    /
	//	/0,0 \__/2,0 \__/
	//  \    /  \    /  \
	//   \__/1,1 \__/3,1 \
	//   /  \    /  \    /
	//  /0,1 \__/2,1 \__/
	
	//	Position pour le placement :
	//		     _	    _
	//		   _/X\    /X\_
	// GAUCHE / \_/    \_/ \ DROITE
	//	 	  \_/ \    / \_/
	//		    \_/    \_/
	
	public int placer_tuile(Tuile t, Point P){
		return 0;
	}
	
	private boolean dans_terrain(Tuile.Orientation o, Point P){
		if(o == Tuile.Orientation.GAUCHE)
			return P.x > 0 && P.y >= 0 && P.x < TAILLE && P.y < TAILLE-1;
		else
			return P.x >= 0 && P.y >= 0 && P.x < TAILLE-1 && P.y < TAILLE-1;
	}
	
	private boolean contact(Tuile.Orientation o, Point P){
		if(o == Tuile.Orientation.GAUCHE)
			return P.x > 0 && P.y >= 0 && P.x < TAILLE && P.y < TAILLE-1;
		else
			return P.x >= 0 && P.y >= 0 && P.x < TAILLE-1 && P.y < TAILLE-1;
	}
	
	public boolean placement_tuile_autorise(Tuile t, Point P){
		if(empty) return dans_terrain(t.getOrientation(),P);
		else{
			return true;
		}
	}
	
	public int placer_batiment(Case.Type_Batiment b, Point P){
		return 0;
	}
	
	public boolean placement_batiment_autorise(Case.Type_Batiment b, Point P){
		return true;
	}
}
