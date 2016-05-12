package main;

import java.awt.Point;
import java.util.ArrayList;

public class Terrain {
	
	private final static int TAILLE = 200;
	private final static Point CENTRE = new Point(TAILLE/2,TAILLE/2);
	
	private Case [][] t;
	private boolean empty;
	
	public Terrain(){
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
	//  \    /  \    /  \
	//   \__/1,2 \__/3,2 \
	
	//	Position pour le placement :
	//		     _	    _
	//		   _/X\    /X\_
	// GAUCHE / \_/    \_/ \ DROITE
	//	 	  \_/ \    / \_/
	//		    \_/    \_/
	
	public int placer_tuile(Tuile t, Point P){
		return 0;
	}
	
	// Renvoie vrai ssi la tuile est dans le terrain
	private boolean dans_terrain(Tuile.Orientation o, Point P){
		if(o == Tuile.Orientation.GAUCHE)
			return P.x > 1 && P.y >= 1 && P.x < TAILLE-1 && P.y < TAILLE-2;
		else
			return P.x >= 1 && P.y >= 1 && P.x < TAILLE-2 && P.y < TAILLE-2;
	}
	
	private boolean dans_terrain(Point P){
		return P.x >= 0 && P.y >= 0 && P.x < TAILLE && P.y < TAILLE;
	}
	
	private ArrayList<Point> cases(Tuile.Orientation o, Point P){
		int x = P.x;
		int y = P.y;
		ArrayList<Point> res = new ArrayList<Point>();
		res.add(new Point(x,y));
		res.add(new Point(x,y+1));
		if(o == Tuile.Orientation.GAUCHE){
			if(x%2 == 0){
				res.add(new Point(x-1,y+1));				
			}
			else{
				res.add(new Point(x-1,y));
			}
		}
		else{
			if(x%2 == 0){
				res.add(new Point(x+1,y+1));
			}
			else{
				res.add(new Point(x+1,y));
			}
		}
		return res;
	}
	
	private ArrayList<Point> contact(Tuile.Orientation o, Point P){
		int x = P.x;
		int y = P.y;
		ArrayList<Point> res = new ArrayList<Point>();
		res.add(new Point(x,y-1));
		res.add(new Point(x,y+2));
		if(o == Tuile.Orientation.GAUCHE){
			res.add(new Point(x-2,y));
			res.add(new Point(x-2,y+1));
			res.add(new Point(x+1,y+1));
			res.add(new Point(x+1,y));
			if(x%2 == 0){
				res.add(new Point(x-1,y));
				res.add(new Point(x-1,y+2));
				res.add(new Point(x+1,y+2));
			}
			else{
				res.add(new Point(x-1,y-1));
				res.add(new Point(x-1,y+1));
				res.add(new Point(x+1,y-1));
			}
		}
		else{
			res.add(new Point(x+2,y));
			res.add(new Point(x+2,y+1));
			res.add(new Point(x-1,y+1));
			res.add(new Point(x-1,y));
			if(x%2 == 0){
				res.add(new Point(x+1,y));
				res.add(new Point(x-1,y+2));
				res.add(new Point(x+1,y+2));
			}
			else{
				res.add(new Point(x+1,y+1));
				res.add(new Point(x-1,y+1));
				res.add(new Point(x+1,y-1));
			}
		}
		return res;
	}
	
	private boolean en_contact(Tuile.Orientation o, Point P){
		boolean trouve = false;
		ArrayList<Point> voisins;
		voisins=contact(o,P);
		int i = 0;
		while(!trouve && i<voisins.size()){
			trouve = !t[voisins.get(i).x][voisins.get(i).y].est_Vide();
		}
		return trouve;
	}
	
	public boolean placement_tuile_autorise(Tuile t, Point P){
		if(empty) return dans_terrain(t.getOrientation(),P);
		else{
			boolean eleve = true;
			int i = 0;
			ArrayList<Point> cases = cases(t.getOrientation(),P);
			while(eleve && i<3){
				eleve = !this.t[cases.get(i).x][cases.get(i).y].est_Vide();
			}
			if(!eleve){
				return en_contact(t.getOrientation(),P);
			}
			else{
				//TODO
				return true;
			}
		}
	}
	
	public int placer_batiment(Case.Type_Batiment b, Point P){
		return 0;
	}
	
	public boolean placement_batiment_autorise(Case.Type_Batiment b, Point P){
		return true;
	}
}
