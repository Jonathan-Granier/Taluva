package Moteur;
import Joueur.Joueur_Generique;
import Joueur.Joueur_Humain;
import Moteur.Phase.Phase_Jeu;
import terrain.Terrain;

public class Etat_de_jeu {
	@SuppressWarnings("unused")
	private Terrain terrain;
	private Joueur_Generique j1,j2,j_courant;
	private Phase_Jeu pdj;

	public Etat_de_jeu(Terrain t, Joueur_Generique j1, Joueur_Generique j2, Joueur_Generique j_courant, Phase_Jeu phase_de_jeu){
		this.terrain = t.clone();
		this.j1 = j1.clone();
		this.j2 = j2.clone();
		this.j_courant = j_courant.clone();
		this.pdj = phase_de_jeu;
	}
	public Etat_de_jeu()
	{
		//Constructeur vide
	}
	public Terrain getTerrain()
	{
		return terrain.clone();
	}
	public Joueur_Generique getj1()
	{
		return j1.clone();
	}
	public Joueur_Generique getj2()
	{
		return j2.clone();
	}
	public Joueur_Generique getj_courant()
	{
		return j_courant.clone();
	}
	public Phase_Jeu getPdj() {
		return pdj;
	}
	public Etat_de_jeu clone()
	{
		Etat_de_jeu retour = new Etat_de_jeu();
		retour.j1 = this.j1.clone();
		retour.j2 = this.j2.clone();
		retour.j_courant = this.j_courant.clone();
		retour.terrain = this.terrain.clone();
		retour.pdj = this.pdj;
		return retour;
	}
	
	public void afficher()
	{
		System.out.println(this.pdj);
		this.terrain.afficher();
	}
}
