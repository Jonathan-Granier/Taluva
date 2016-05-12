package main;
import java.util.ArrayList;
import java.util.Random;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import Joueur.*;

public class Moteur {
	private Terrain T;
	private int nbTuiles;
	private ArrayList<Terrain> annul;
	private ArrayList<Terrain> redo;
	private ArrayList<Tuile> tuiles;
	joueur_Humain j_courant;
	
	joueur_Humain j1;
	joueur_Humain j2;
	
	//
	//LECTURE DES PIECES ET INITIALISATION DE L'ENSEMBLE DE TUILES
	//
	public Case.Type switch_case(char c){
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
	public void traiter(String line,ArrayList<Tuile> tuiles){
		int nb;
		char c;
		nb = Character.getNumericValue(line.charAt(0));
		for(int i=1; i<=nb;i++){
			tuiles.add(new Tuile(switch_case(line.charAt(2)),switch_case(line.charAt(4))));
		}
	}
	
	
	public void init_tuiles(ArrayList<Tuile> tuiles){
		try {
			File file = new File("PIECES.txt");
			FileInputStream fis = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			String line = null;
			try {
				while ((line = br.readLine()) != null) {
					System.out.println(line);
					traiter(line,tuiles);
				}
				br.close();
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Moteur(Terrain T,int nb_tuiles,joueur_Humain j1,joueur_Humain j2){
		this.T = T;
		this.nbTuiles = nb_tuiles;
		annul = new ArrayList<Terrain>();
		annul.add(T.clone());
		redo = new ArrayList<Terrain>();
		this.j1 = j1;
		j_courant = j1;
		this.j2 = j2;
	}
	
	public Terrain getT(){
		return T;
	}
	
	public int setT(Terrain T){
		this.T = T;
		return 0;
	}
	
	public int get_nbTuiles(){
		return nbTuiles;
	}
	
	public int set_nbTuiles(int nbTuiles){
		this.nbTuiles = nbTuiles;
		return 0;
	}
	//Echange le joueur courant
	public void swap_joueur(){
		j_courant = (j_courant==j1)? j1 : j2;
	}
	
	//Si en début de tour on n'a plus de tuile à jouer
	public boolean partie_terminee(){
		return nbTuiles==0;
	}
	
	//Test si un joueur qui vient de jouer a gagné
	public boolean a_gagne(){
		return (j_courant.getTour()==0 && j_courant.getTemple()==0)
				||(j_courant.getTour()==0 && j_courant.getHutte()==0)
				||(j_courant.getTemple()==0 && j_courant.getHutte()==0);
	}
	
	//Test si le joueur courant est incapable de jouer (impossible de poser des batiments)
	// A COMPLETER!!!
	public boolean a_perdu(){
		return false;
	}
	
	//Renvoie une tuile piochée aléatoirement dans la pioche
	// A COMPLETER!!!
	public Tuile piocher(){
		Random r = new Random();
		return null;
	}
	
	//Permet de jouer un tour
	//i.e poser une tuile (et une pièce) sur le terrain T.
	//Renvoie 0 si l'opération à réussi, 1 sinon.
	// A COMPLETER!!!
	public int jouer_tour(){
		if(partie_terminee()){
			if(j1.getScore()>j2.getScore())System.out.println("Joueur 1 gagne");
			else if (j1.getScore()<j2.getScore())System.out.println("Joueur 2 gagne");
			else System.out.println("Il y a egalite");
			return 0;
		}
		else{
			Tuile t = piocher();
			if(a_perdu()){
				if(j_courant==j1)System.out.println("Joueur 2 gagne");
				else if (j_courant==j2)System.out.println("Joueur 1 gagne");
			}
			
		}
		return 1;
	}
	
	//Permet d'annuler un coup, i.e de revenir dans une position antérieure
	//Renvoie 1 si tout s'est bien passé, 0 sinon.
	public int annuler(){
		if(annul.size()==1)return 1;
		redo.add(annul.remove(annul.size()-1));
		T = annul.get(annul.size()-1);
		//swap_joueur();
		return 0;
	}
	
	//Permet de revenir dans une position qui a été annulée
	//Renvoie 1 si tout s'est bien passé, 0 sinon.
	public int refaire(){
		if(redo.isEmpty())return 1;
		annul.add(redo.remove(redo.size()-1));
		T = annul.get(annul.size()-1);
		//swap_joueur();
		return 0;
	}
}

