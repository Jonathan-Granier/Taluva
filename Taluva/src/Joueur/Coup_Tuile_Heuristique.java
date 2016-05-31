package Joueur;

import Action.Action_Tuile;

public class Coup_Tuile_Heuristique {
	private int heuristique;
	private Action_Tuile actionTuile;

	public Coup_Tuile_Heuristique(int score, Action_Tuile tuile)
	{
		this.actionTuile = tuile;
		this.heuristique = score;
	}
	
	public int getHeuristique() {
		return heuristique;
	}

	public void setHeuristique(int heuristique) {
		this.heuristique = heuristique;
	}

	public Action_Tuile getActionTuile() {
		return actionTuile;
	}

	public void setActionTuile(Action_Tuile tuile) {
		this.actionTuile = tuile;
	}
}
