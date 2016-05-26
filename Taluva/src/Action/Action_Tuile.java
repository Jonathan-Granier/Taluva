package Action;

import java.awt.Point;
import terrain.Tuile;

public class Action_Tuile {
	private Tuile tuile;
	private Point P;
	private int niveau;
	
	public Action_Tuile(Tuile tuile, Point P, int niveau){
		this.tuile = tuile;
		this.P = new Point(P.x,P.y);
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
	
	public void afficher_Action_Tuile()
	{
		System.out.println("Action Tuile :");
		tuile.afficher();
		System.out.println("Position : " + P);
		System.out.println("Niveau : "+ niveau);
	}
	
}
