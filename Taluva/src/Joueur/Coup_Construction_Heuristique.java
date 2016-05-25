package Joueur;

import Action.Action_Construction;
import Action.Action_Tuile;

public class Coup_Construction_Heuristique {
	private int heuristique;
	private Action_Construction construction;
	
	public int getHeuristique() {
		return heuristique;
	}

	public void setHeuristique(int heuristique) {
		this.heuristique = heuristique;
	}

	public Action_Construction getConstruction() {
		return construction;
	}

	public void setConstruction(Action_Construction construction) {
		this.construction = construction;
	}

	public Coup_Construction_Heuristique(int score, Action_Construction construction)
	{
		this.construction = construction;
		this.heuristique = score;
	}
	
	public int get_Heuristique()
	{
		return heuristique;
	}
	public Action_Construction get_Action_Construction()
	{
		return construction;
	}
	
	
}
