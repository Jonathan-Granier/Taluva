package Joueur;

import main.Action_Construction;
import main.Action_Tuile;
import main.Moteur;
import terrain.Case.Couleur_Joueur;
import terrain.Tuile;

public abstract class IA_Generique extends joueur_Generique{
	Moteur m;
	public IA_Generique(Couleur_Joueur c, Moteur m) {
		super(c);
		this.m = m;
		// TODO Auto-generated constructor stub
	}
	public abstract Action_Construction get_coup_construction();
	public abstract Action_Tuile get_coup_tuile(Tuile tuile);
	
	

}
