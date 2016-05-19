package main;

import java.awt.Point;

import terrain.Case;

public class Action_Batiment {
	private Case.Type_Batiment bt;
	private int niveau;
	private int nb_batiments;
	private Point P;
	
	public Action_Batiment(Case.Type_Batiment bt, int niveau, int nb_batiments, Point P){
		this.bt = bt;
		this.niveau = niveau;
		if(bt != Case.Type_Batiment.HUTTE) this.nb_batiments = nb_batiments;
		else this.nb_batiments = 1;
		this.P = P;
	}
	
	public Action_Batiment clone(){
		return new Action_Batiment(bt,niveau,nb_batiments,new Point(P.x,P.y));
	}
	
	public Case.Type_Batiment getTypeBatiment(){
		return bt;
	}
	
	public int getNiveau(){
		return niveau;
	}
	
	public int getNbBatiments(){
		return nb_batiments;
	}
	
	public Point getPosition(){
		return P;
	}
	
}
