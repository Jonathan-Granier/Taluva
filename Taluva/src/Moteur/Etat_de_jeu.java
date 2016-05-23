package Moteur;
import Joueur.Joueur_Generique;
import Joueur.Joueur_Humain;
import terrain.Terrain;

public class Etat_de_jeu {
	@SuppressWarnings("unused")
	private Terrain terrain;
	private Joueur_Generique joueur;
	
	public Etat_de_jeu(Terrain t, Joueur_Generique j){
		terrain = t.clone();
		joueur = new Joueur_Humain(j.getCouleur());
		j.copie_Joueur_Generique(joueur);
	}
	
}
