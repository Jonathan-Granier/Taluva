package Joueur;

import Action.Action_Construction;

public class Coup_Construction_Heuristique {
	private int heuristique;
	private Action_Construction construction;

	public Coup_Construction_Heuristique(int score, Action_Construction construction)
	{
		this.construction = construction;
		this.heuristique = score;
	}
	
	public int getHeuristique() {
		return heuristique;
	}

	public void setHeuristique(int heuristique) {
		this.heuristique = heuristique;
	}

	public Action_Construction getActionConstruction() {
		return construction;
	}

	public void setActionConstruction(Action_Construction construction) {
		this.construction = construction;
	}

}
