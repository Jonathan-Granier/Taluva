package main;
import java.awt.Color;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import Joueur.joueur_Humain;
import terrain.Case;
import terrain.Terrain;
import terrain.Tuile;

public class Moteur {
	private Terrain T;
	private ArrayList<Terrain> annul, redo;
	private ArrayList<Tuile> tuiles;
	private Tuile tuile_pioche;
	private Case.Type_Batiment bat_choisi;
	joueur_Humain j_courant;
	
	joueur_Humain j1;
	joueur_Humain j2;
	joueur_Humain j_gagnant;
	
	public enum Etat{
		DEBUT_DE_TOUR,
		POSER_TUILE,
		CONSTRUIRE_BATIMENT,
		FIN_DE_TOUR;
	}
	public Etat etat;
	
	// Constructeur du moteur
	public Moteur(Terrain T,joueur_Humain j1,joueur_Humain j2){
		this.T = T;
		annul = new ArrayList<Terrain>();
		annul.add(T.clone());
		redo = new ArrayList<Terrain>();
		tuiles = new ArrayList<Tuile>();
		init(tuiles);
		this.j1 = j1;
		j_courant = j1;
		this.j2 = j2;
		etat = Etat.DEBUT_DE_TOUR;
		bat_choisi = Case.Type_Batiment.VIDE;
	}
	
	///////////////////////////////////////////////////////////////
	//LECTURE DES PIECES ET INITIALISATION DE L'ENSEMBLE DE TUILES
	///////////////////////////////////////////////////////////////
	
	private Case.Type switch_case(char c){
		switch (c){
			case 'V' :	return Case.Type.VOLCAN;
			case 'M' :	return Case.Type.MONTAGNE;
			case 'P' :	return Case.Type.PLAINE;
			case 'L' :	return Case.Type.LAC;
			case 'S' :	return Case.Type.SABLE;
			case 'F' :	return Case.Type.FORET;
			default :	System.out.println("Erreur de type dans le fichier");
						return null;
		}
	}
	
	//Ajout à l'ensemble de tuiles
	private void rajoute_tuile(String line,ArrayList<Tuile> tuiles){
		int nb;
		nb = Character.getNumericValue(line.charAt(0));
		for(int i=1; i<=nb;i++){
			tuiles.add(new Tuile(switch_case(line.charAt(2)),switch_case(line.charAt(4))));
		}
	}
	
	
	private void init(ArrayList<Tuile> tuiles){
		try {
			File file = new File("../PIECES");
			FileInputStream fis = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			String line = null;
			try {
				while ((line = br.readLine()) != null) {
					System.out.println(line);
					rajoute_tuile(line,tuiles);
				}
				br.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	///////////////////////
	// Getters / Setters
	///////////////////////
	
	// Récupère le terrain courant
	public Terrain getT(){
		return T;
	}
	
	//Affecte un terrain au moteur
	public int setT(Terrain T){
		this.T = T;
		return 0;
	}
	
	//Renvoie le nombre de Tuiles restantes
	public int get_nbTuiles(){
		return tuiles.size();
	}
	
	//Renvoie la tuile piochée 
	public Tuile get_tuile_pioche(){
		return tuile_pioche;
	}
	
	//Renvoie le joueur courant
	public joueur_Humain get_Jcourant(){
		return j_courant;
	}
	
	//Renvoie l'état actuel du tour du jeu
	public Etat get_etat_jeu(){
		return etat;
	}
	
	//Renvoie le type de batiment choisi par le joueur
	public Case.Type_Batiment get_bat_choisi(){
		return bat_choisi;
	}
	
	//Renvoie le joueur qui a gagné la partie
	public joueur_Humain getGagnant(){
		return j_gagnant;
	}
	
	//////////////////////////////////////////////////
	//	FONCTIONS RELATIVES A UN TOUR DE JEU
	/////////////////////////////////////////////////
	
	//Echange le joueur courant
		public void swap_joueur(){
			j_courant = (j_courant==j1)? j1 : j2;
		}
	
	//Renvoie vrai si la pioche est vide
	public boolean pioche_vide(){
		return tuiles.size()==0;
	}
	
	//Test si le joueur courant a posé tous les batiments de 2 types differents
	public boolean victoire_aux_batiments(){
		return (j_courant.getTour()==0 && j_courant.getTemple()==0)
				||(j_courant.getTour()==0 && j_courant.getHutte()==0)
				||(j_courant.getTemple()==0 && j_courant.getHutte()==0);
	}
	
	//Test si le joueur courant est incapable de jouer (impossible de poser des batiments)
	public boolean joueur_elimine (){
		// TODO
		//return (T.liste_coups_construction_possibles(Case.Couleur_Joueur.BLANC).size() == 0);
		return false;
	}
	
	//Renvoie une tuile piochée aléatoirement dans la pioche
	public Tuile piocher(){
		if(annul.size()==0)annul.add(T.clone());
		Random r = new Random();
		tuile_pioche = tuiles.remove(r.nextInt(tuiles.size()));
		etat = Etat.POSER_TUILE;
		return tuile_pioche;
	}
	
	// Renvoie vrai ssi le placement de la tuile piochée est autorisé au point P.
	public boolean placement_tuile_autorise(Point P){
		return T.placement_tuile_autorise(tuile_pioche,P);
	}
	
	//Renvoie 0 si la tuile piochée a pu être placée, -1 si elle est placée, mais le joueur ne peux plus jouer, 1 sinon
	public int placer_tuile(Point P){
		if(T.placer_tuile(tuile_pioche, P) == 0){
			if(joueur_elimine())return -1;
			annul.add(T.clone());
			etat = Etat.CONSTRUIRE_BATIMENT;
			return 0;
		}
		else return 1;
	}
	
	//SELECTEURS DES BATIMENTS DU JOUEUR
	//Le batiment choisi est une hutte
	public void select_hutte(){
		if(etat == Etat.CONSTRUIRE_BATIMENT)
		bat_choisi = Case.Type_Batiment.HUTTE;
	}
	//Le batiment choisi est un temple
	public void select_temple(){
		if(etat == Etat.CONSTRUIRE_BATIMENT)
		bat_choisi = Case.Type_Batiment.TEMPLE;
	}
	//Le batiment choisi est une tour
	public void select_tour(){
		if(etat == Etat.CONSTRUIRE_BATIMENT)
		bat_choisi = Case.Type_Batiment.TOUR;
	}
	
	
	//Renvoie vrai ssi le placement du batiment choisi est autorisé au point P.
	public boolean placement_batiment_autorise(Point P){
		return T.placement_batiment_autorise(bat_choisi,j_courant.getCouleur(), P);
	}
	
	//Renvoie 0 si le batiment a pu être placé, 1 sinon
	public int placer_batiment(Point P){
		if(T.placer_batiment(bat_choisi,j_courant.getCouleur(), P) == 0){
			annul.add(T.clone());
			etat = Etat.FIN_DE_TOUR;
			return 0;
		}
		else return 1;
	}
	
	//Termine le tour du joueur courant, renvoie 0 si la partie est terminée, 1 sinon
	//Actualise aussi les données et change de joueur
	public int fin_de_tour(){
		if(victoire_aux_batiments()){
			if(j_courant == j1)System.out.println("Le joueur 1 a gagné!!!");
			else System.out.println("Le joueur 2 a gagné!!!");
			j_gagnant = j_courant;
			return 0;
		}
		else if(pioche_vide()){
			if(j1.getScore()>j2.getScore()){
				System.out.println("Le joueur 1 a gagné!!!");
				j_gagnant = j1;
			}
			else if(j1.getScore()<j2.getScore()){
				System.out.println("Le joueur 1 a gagné!!!");
				j_gagnant = j2;
			}
			else {
				System.out.println("Il y a une égalité parfaite, vous avez tous les 2 gagné!!!");
				j_gagnant = j_courant;
			}
			return 0;
		}
		else{
			annul.clear();
			redo.clear();
			swap_joueur();
			etat = Etat.DEBUT_DE_TOUR;
			bat_choisi = Case.Type_Batiment.VIDE;
			return 1;
		}
	}
	
	//Permet d'annuler une tuile posée, et de la récupérer
	//Renvoie 0 si tout s'est bien passé, 1 sinon.
	public int annuler(){
		if(annul.size()<=1)return 1;
		redo.add(annul.remove(annul.size()-1));
		T = annul.get(annul.size()-1);
		int code_erreur = DecrementeEtat();
		return code_erreur;
	}
	
	//Permet de reposer une tuile qui a été annulée qui a été annulée
	//Renvoie 0 si tout s'est bien passé, 1 sinon.
	public int refaire(){
		if(redo.isEmpty())return 1;
		annul.add(redo.remove(redo.size()-1));
		T = annul.get(annul.size()-1);
		int code_erreur = IncrementeEtat();
		return code_erreur;
	}
	
	// ---------------- Fonction Pour le type Etat -------------------
	
	
	public int IncrementeEtat()
	{
		switch (etat)
		{
			case DEBUT_DE_TOUR:
				etat = Etat.POSER_TUILE;
				break;
			case POSER_TUILE:
				etat = Etat.CONSTRUIRE_BATIMENT;
				break;
			case CONSTRUIRE_BATIMENT:
				etat = Etat.FIN_DE_TOUR;
				break;
			case FIN_DE_TOUR:
				etat = Etat.DEBUT_DE_TOUR;
				break;
			default:
				return 1;
		}
		return 0;
	}
	
	public int DecrementeEtat()
	{
		switch (etat)
		{
			case DEBUT_DE_TOUR:
				break;
			case POSER_TUILE:
				break;
			case CONSTRUIRE_BATIMENT:
				etat = Etat.POSER_TUILE;
				break;
			case FIN_DE_TOUR:
				etat = Etat.CONSTRUIRE_BATIMENT;
				break;
			default:
				return 1;
		}
		return 0;
	}
	
}

