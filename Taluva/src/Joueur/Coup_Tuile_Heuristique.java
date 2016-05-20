package Joueur;

import Action.Action_Tuile;

public class Coup_Tuile_Heuristique {
	private int heuristique;
	private Action_Tuile tuile;
	
	public Coup_Tuile_Heuristique(int score, Action_Tuile tuile)
	{
		this.tuile = tuile;
		this.heuristique = score;
	}
	
	public int get_Heuristique()
	{
		return heuristique;
	}
	public Action_Tuile get_Action_Tuile()
	{
		return tuile;
	}
	
}
