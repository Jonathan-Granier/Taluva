package Action;

import java.awt.Point;
import terrain.Tuile;

public class Action_Tuile {
	private Tuile tuile;
	private Point P;
	private int niveau;
	
	public Action_Tuile(Tuile tuile, Point P, int niveau){
		this.tuile = tuile;
		this.P = P;
		this.niveau = niveau;
	}
	
	public Action_Tuile clone(){
		return new Action_Tuile(tuile.clone(),new Point(P.x,P.y),niveau);
	}
	
	public Tuile getTuile(){
		return tuile;
	}
	
	public Point getPosition(){
		return P;
	}
	
	public int getNiveau(){
		return niveau;
	}
}
