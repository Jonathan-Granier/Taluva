package Moteur;
import Joueur.Joueur_Generique;
import Joueur.Joueur_Humain;
import Moteur.Phase.Phase_Jeu;
import terrain.Terrain;
//TODO SURCHARGER LA CLASSE POUR 3 ET 4 JOUEURS
public class Etat_de_jeu {
	@SuppressWarnings("unused")
	private Terrain terrain;
	private Joueur_Generique j1,j2,j3,j4,j_courant;
	private Phase_Jeu pdj;
	private int nb_Joueur;

	public Etat_de_jeu(Terrain t, Joueur_Generique j1, Joueur_Generique j2, Joueur_Generique j_courant, Phase_Jeu phase_de_jeu){
		this.terrain = t.clone();
		this.j1 = j1.clone();
		this.j2 = j2.clone();
		this.j_courant = j_courant.clone();
		this.pdj = phase_de_jeu;
		this.nb_Joueur = 2;
	}
	
	public Etat_de_jeu(Terrain t, Joueur_Generique j1, Joueur_Generique j2,Joueur_Generique j3, Joueur_Generique j_courant, Phase_Jeu phase_de_jeu){
		this.terrain = t.clone();
		this.j1 = j1.clone();
		this.j2 = j2.clone();
		this.j3 = j3.clone();
		this.j_courant = j_courant.clone();
		this.pdj = phase_de_jeu;
		this.nb_Joueur = 4;
	}
	
	public Etat_de_jeu(Terrain t, Joueur_Generique j1, Joueur_Generique j2, Joueur_Generique j3,Joueur_Generique j4,Joueur_Generique j_courant, Phase_Jeu phase_de_jeu){
		this.terrain = t.clone();
		this.j1 = j1.clone();
		this.j2 = j2.clone();
		this.j3 = j3.clone();
		this.j4 = j4.clone();
		this.j_courant = j_courant.clone();
		this.pdj = phase_de_jeu;
		this.nb_Joueur = 4;
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
	public Joueur_Generique getj3()
	{
		return j3.clone();
	}
	
	public Joueur_Generique getj4()
	{
		return j4.clone();
	}
	
	public int get_nb_Joueur()
	{
		return nb_Joueur;
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
		
		if(nb_Joueur >= 3)
			retour.j3 = this.j3.clone();
		if(nb_Joueur >= 4)
			retour.j4 = this.j4.clone();
		retour.j_courant = this.j_courant.clone();
		retour.terrain = this.terrain.clone();
		retour.pdj = this.pdj;
		retour.nb_Joueur = this.nb_Joueur;
		return retour;
	}
	
	public void afficher()
	{
		System.out.println(this.pdj);
		//this.terrain.afficher();
	}
}
