package main;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import Joueur.*;

public class Moteur {
	private Terrain T;
	private ArrayList<Terrain> annul, redo;
	private ArrayList<Tuile> tuiles;
	private Tuile tuile_pioche;
	private Case.Type_Batiment bat_pioche;
	joueur_Humain j_courant;
	
	joueur_Humain j1;
	joueur_Humain j2;
	
	public enum Etat{
		DEBUT_DE_TOUR,
		POSER_TUILE,
		CONSTRUIRE_BATIMENT,
		FIN_DE_TOUR;
	}
	private Etat etat;
	
	public Moteur(Terrain T,joueur_Humain j1,joueur_Humain j2){
		this.T = T;
		annul = new ArrayList<Terrain>();
		annul.add(T.clone());  // Verifier que les reference soit different : Une modification dans T , ne modifie pas ce qu'il y a dans annul
		redo = new ArrayList<Terrain>();
		tuiles = new ArrayList<Tuile>();
		inits(tuiles);
		this.j1 = j1;
		j_courant = j1;
		this.j2 = j2;
		etat = Etat.DEBUT_DE_TOUR;
		bat_pioche = Case.Type_Batiment.VIDE;
	}
	
	///////////////////////////////////////////////////////////////
	//LECTURE DES PIECES ET INITIALISATION DE L'ENSEMBLE DE TUILES
	///////////////////////////////////////////////////////////////
	private Case.Type switch_case(char c){
		switch (c){
			case 'V' :	return Case.Type.VOLCAN;
				//break;
			case 'M' :	return Case.Type.MONTAGNE;
				//break;
			case 'P' :	return Case.Type.PLAINE;
				//break;
			case 'L' :	return Case.Type.LAC;
				//break;
			case 'S' :	return Case.Type.SABLE;
				//break;
			case 'F' :	return Case.Type.FORET;
				//break;
			default :	System.out.println("Erreur de type dans le fichier");
						return null;
		}
	}
	
	//Ajout à l'ensemble de tuiles
	private void rajout(String line,ArrayList<Tuile> tuiles){
		int nb;
		nb = Character.getNumericValue(line.charAt(0));
		for(int i=1; i<=nb;i++){
			tuiles.add(new Tuile(switch_case(line.charAt(2)),switch_case(line.charAt(4))));
		}
	}
	
	
	private void inits(ArrayList<Tuile> tuiles){
		try {
			File file = new File("PIECES.txt");
			FileInputStream fis = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			String line = null;
			try {
				while ((line = br.readLine()) != null) {
					System.out.println(line);
					rajout(line,tuiles);
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
	public Terrain getT(){
		return T;
	}
	
	public int setT(Terrain T){
		this.T = T;
		return 0;
	}
	
	public int get_nbTuiles(){
		return tuiles.size();
	}
	
	public Tuile get_tuile_pioche(){
		return tuile_pioche;
	}
	
	public Case.Type_Batiment get_bat_pioche(){
		return bat_pioche;
	}
	
	//Echange le joueur courant
	public void swap_joueur(){
		j_courant = (j_courant==j1)? j1 : j2;
	}
	
	//Renvoi vrai si la pioche est vide
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
	//TODO
	public boolean joueur_elimine (){
		return false;
	}
	
	//Renvoie une tuile piochée aléatoirement dans la pioche
	public Tuile piocher(){
		Random r = new Random();
		tuile_pioche = tuiles.remove(r.nextInt(tuiles.size()-1)+1);
		etat = Etat.POSER_TUILE;
		return tuile_pioche;
	}
	
	// Renvoie vrai ssi le placement de la tuile piochée est autorisé au point P.
	public boolean placement_tuile_autorise(Point P){
		return T.placement_tuile_autorise(tuile_pioche,P);
	}
	
	//Appelle la méthode placer_tuile du terrain, renvoie 0 si la tuile piochée a pu être placée, 1 sinon
	public int placer_tuile(Point P){
		if(T.placer_tuile(tuile_pioche, P) ==1){
			etat = Etat.CONSTRUIRE_BATIMENT;
			return 0;
		}
		return 1;
	}
	
	//SELECTEURS DES BATIMENTS DU JOUEUR
	//La cas piochée est une choisie
	public void select_hutte(){
		bat_pioche = Case.Type_Batiment.HUTTE;
	}
	//La cas piochée est une choisie
	public void select_temple(){
		bat_pioche = Case.Type_Batiment.TEMPLE;
	}
	//La cas piochée est une choisie
	public void select_tour(){
		bat_pioche = Case.Type_Batiment.TOUR;
	}
	
	
	//Permet de jouer un tour
	//i.e poser une tuile (et une pièce) sur le terrain T.
	//Renvoie 0 si l'opération à réussi, 1 sinon.
	//TODO
	public int jouer_tour(Point p){
		//Devra potentiellement être exécuté dans l'écouteur de "Piocher"
		if(pioche_vide()){
			if(j1.getScore()>j2.getScore())System.out.println("Joueur 1 gagne");
			else if (j1.getScore()<j2.getScore())System.out.println("Joueur 2 gagne");
			else System.out.println("Il y a egalite");
			return 0;
		}
		else{
			if(T.placer_tuile(tuile_pioche, p)==0){
				//Il faut ensuite placer le batiment
			}
		}
		return 1;
	}
	
	//Permet d'annuler un tuile posée, et de la récupérer
	//Renvoie 1 si tout s'est bien passé, 0 sinon.
	public int annuler(){
		if(annul.size()==1)return 1;
		redo.add(annul.remove(annul.size()-1));
		T = annul.get(annul.size()-1);
		if (etat == Etat.FIN_DE_TOUR)etat = Etat.CONSTRUIRE_BATIMENT;
		else if(etat == Etat.CONSTRUIRE_BATIMENT)etat = Etat.POSER_TUILE;
		else return 1;
		
		return 0;
	}
	
	//Permet de reposer une tuile qui a été annulée qui a été annulée
	//Renvoie 1 si tout s'est bien passé, 0 sinon.
	public int refaire(){
		if(redo.isEmpty())return 1;
		annul.add(redo.remove(redo.size()-1));
		T = annul.get(annul.size()-1);
		if (etat == Etat.POSER_TUILE)etat = Etat.CONSTRUIRE_BATIMENT;
		else if(etat == Etat.CONSTRUIRE_BATIMENT)etat = Etat.FIN_DE_TOUR;
		else return 1;
		
		return 0;
	}
}

