package main;

import java.awt.Point;

public class Action_Tuile {
	private Tuile tuile;
	private Point P;
	
	public Action_Tuile(Tuile tuile, Point P){
		this.tuile = tuile;
		this.P = P;
	}
	
	public Tuile getTuile(){
		return tuile;
	}
	
	public Point getPosition(){
		return P;
	}
}
