package Joueur;

import main.Action_Construction;
import main.Action_Tuile;

public class Coup_Construction_Heuristique {
	private int heuristique;
	private Action_Construction construction;
	
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
