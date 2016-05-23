package Moteur;
import Joueur.Joueur_Generique;
import terrain.Terrain;

public class Etat_de_jeu {
	@SuppressWarnings("unused")
	private Terrain terrain;
	private Joueur_Generique joueur;
	
	public Etat_de_jeu(Terrain t, Joueur_Generique j){
		terrain = t.clone();
		j.copie_Joueur_Generique(joueur);
	}
	
}
