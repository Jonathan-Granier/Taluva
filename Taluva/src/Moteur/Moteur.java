package Moteur;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import Action.Action_Construction;
import Action.Action_Tuile;
import Joueur.IA_Alpha_Beta;
import Joueur.IA_Generique;
import Joueur.IA_Random;
import Joueur.Joueur_Generique;
import Joueur.Joueur_Humain;
import Liste_coup.Liste_coup_construction;
import terrain.Case;
import terrain.Terrain;
import terrain.Tuile;
import test.Game;

public class Moteur extends Phase{
	private Terrain T;
	private Stack<Etat_de_jeu> annul, redo;
	//private ArrayList<Joueur_Humain> prev, next;
	private ArrayList<Etat_de_jeu> histo_jeu;
	Joueur_Humain prev, next;
	
	private Stack<Tuile> pioche;
	private Tuile tuile_pioche;
	private Case.Type_Batiment bat_choisi;
	private Liste_coup_construction liste_coup_construction;
	
	private Joueur_Generique j_courant;
	private Joueur_Generique j1;
	private Joueur_Generique j2;
	private Joueur_Generique j_gagnant;


	public static int nb_max_Huttes = 20;
	public static int nb_max_Tours = 2;
	public static int nb_max_Temples = 3;
	public static boolean PIOCHE_ALEATOIRE = true;
	
	/*public enum Etat{
		DEBUT_DE_TOUR,
		POSER_TUILE,
		CONSTRUIRE_BATIMENT,
		FIN_DE_TOUR;
	}
	
	private Etat etat;
	*/
	
	// Constructeur du moteur
	public Moteur(Terrain T,Joueur_Generique j1,Joueur_Generique j2){
		this.T = T;
		annul = new Stack<Etat_de_jeu>();
		redo = new Stack<Etat_de_jeu>();
		pioche = new Stack<Tuile>();
		init(pioche);
		this.j1 = j1;
		j_courant = j1;
		this.j2 = j2;
		//prev = new ArrayList<Joueur_Humain>();
		//prev.add(((Joueur_Humain) j_courant).clone());
		//next = new ArrayList<Joueur_Humain>();
		init_phase_jeu();
		//etat = Etat.DEBUT_DE_TOUR;
		bat_choisi = Case.Type_Batiment.VIDE;
		histo_jeu = new ArrayList<Etat_de_jeu>();
		//histo_jeu.add(new Etat_de_jeu(T,this.j1, this.j2, this.j_courant, this.clone_Phase()));
	}
	
	// Constructeur du moteur sans joueurs
	public Moteur(Terrain T){
		this.T = T;
		annul = new Stack<Etat_de_jeu>();
		redo = new Stack<Etat_de_jeu>();
		pioche = new Stack<Tuile>();
		init(pioche);
		//etat = Etat.DEBUT_DE_TOUR;
		init_phase_jeu();
		bat_choisi = Case.Type_Batiment.VIDE;
		histo_jeu = new ArrayList<Etat_de_jeu>();
	}
	
	// Adders de joueurs
	public void add_j1(Joueur_Generique j1){
		this.j1 = j1;
		j_courant = j1;
		/*prev = new Joueur_Humain(j_courant.getCouleur());
		prev = ((Joueur_Humain) j_courant).clone();
		next = new Joueur_Humain(j_courant.getCouleur());*/
		//histo_jeu.add(new Etat_de_jeu(T,j1,j2,j_courant, this.clone_Phase()));
	}
		
	public void add_j2(Joueur_Generique j2){
		this.j2 = j2;
		if (j1 instanceof IA_Generique)jouer_IA();
	}
	
	///////////////////////////////////////////////////////////////
	//LECTURE DES PIECES ET INITIALISATION DE L'ENSEMBLE DE TUILES
	///////////////////////////////////////////////////////////////
	
	private Case.Type char_to_case(char c){
		switch (c){
			case 'M' :	return Case.Type.MONTAGNE;
			case 'P' :	return Case.Type.PLAINE;
			case 'L' :	return Case.Type.LAC;
			case 'S' :	return Case.Type.SABLE;
			case 'F' :	return Case.Type.FORET;
			default :	System.out.println("Erreur de type dans le fichier");
						return null;
		}
	}
	
	// Ajout à l'ensemble de tuiles
	private void rajoute_tuile(String line,ArrayList<Tuile> pioche_fichier){
		int nb;
		nb = Character.getNumericValue(line.charAt(0));
		for(int i=1; i<=nb;i++){
			pioche_fichier.add(new Tuile(char_to_case(line.charAt(2)),char_to_case(line.charAt(4))));
		}
	}
	
	
	// Creer la pioche dans une pile selon le fichier PIECES , Aléatoirement ou non selon PIOCHE_ALEATOIRE est vrai ou non
	private void init(Stack<Tuile> pioche){
		ArrayList<Tuile> pioche_fichier = new ArrayList<Tuile>();
		try {
			
			
			File file = new File("../PIECES");
			FileInputStream fis = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			String line = null;
			try {
				while ((line = br.readLine()) != null) {
					//System.out.println(line);
					rajoute_tuile(line,pioche_fichier);
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
		
		if(PIOCHE_ALEATOIRE)
		{
			melange(pioche,pioche_fichier);
		}
		else
		{
			transfert(pioche,pioche_fichier);
		}
		
		
	}
	

	// Melange l'ArrayList pioche_fichier pour le mettre dans la pioche
	private void melange(Stack<Tuile> pioche, ArrayList<Tuile> pioche_fichier) {
		Random r = new Random();
		while(!pioche_fichier.isEmpty())
		{
			pioche.push(pioche_fichier.remove(r.nextInt(pioche_fichier.size())));
		}
		
	}
	// Transfert l'Arraylist dans la pioche
	private void transfert(Stack<Tuile> pioche, ArrayList<Tuile> pioche_fichier) {
		while(!pioche_fichier.isEmpty())
		{
			pioche.push(pioche_fichier.remove(pioche_fichier.size()-1));
		}
		
	}
	
	///////////////////////
	// Getters / Setters
	///////////////////////
	
	// Récupère le terrain courant
	public Terrain getTerrain(){
		return T;
	}
	
	// Renvoie le nombre de Tuiles restantes
	public int get_nbTuiles(){
		return pioche.size();
	}
	
	// Renvoie la tuile piochée 
	public Tuile get_tuile_pioche(){
		return tuile_pioche;
	}
	
	// Renvoie le joueur courant
	public Joueur_Generique get_Jcourant(){
		return j_courant;
	}
	
	// Renvoie le joueur 1
	public Joueur_Generique getJ1(){
		return j1;
	}
	
	// Renvoie le joueur 2
	public Joueur_Generique getJ2(){
		return j2;
	}
	
	// Renvoie le joueur courant
	public int get_num_Jcourant(){
		if(j_courant.getCouleur().equals(j1.getCouleur()))
		{
			System.out.println("Cest le J1 le joueur Courant");
			return 1;
		}
		System.out.println("Cest le J2 le joueur Courant");
		return 2;
	}
	
	public boolean Est_joueur_Courant(Joueur_Generique j)
	{
		return j_courant.getCouleur().equals(j.getCouleur());
	}
	
	// Renvoie le type de batiment choisi par le joueur
	public Case.Type_Batiment get_bat_choisi(){
		return bat_choisi;
	}
	
	// Renvoie le joueur qui a gagné la partie
	public Joueur_Generique getGagnant(){
		return j_gagnant;
	}
	
	// Renvoie la liste des coups possibles pour la tuile actuelle
	public ArrayList<Action_Tuile> get_liste_coup_tuile(Tuile tuile){
		return T.liste_coups_tuile_possibles(tuile);
	}
	
	// Renvoie la liste des constructions possibles dans la configuration actuelle
	public Liste_coup_construction get_liste_coup_construction(){
		return liste_coup_construction;
	}
	
	//////////////////////////////////////////////////
	//	FONCTIONS RELATIVES A UN TOUR DE JEU
	/////////////////////////////////////////////////
	
	// Echange le joueur courant
		public void swap_joueur(){
			if(j_courant == j1)
			{
				j_courant = j2;
			}
			else
			{
				j_courant = j1;
			}
		}
	
	// Renvoie vrai si la pioche est vide
	public boolean pioche_vide(){
		return pioche.size()==0;
	}
	
	// Test si le joueur courant a posé tous les batiments de 2 types differents
	public boolean victoire_aux_batiments(){
		return (j_courant.getTour()==0 && j_courant.getTemple()==0)
				||(j_courant.getTour()==0 && j_courant.getHutte()==0)
				||(j_courant.getTemple()==0 && j_courant.getHutte()==0);
	}
	
	// Test si le joueur courant est incapable de jouer (impossible de poser des batiments)
	public boolean joueur_elimine (){
		ArrayList<Action_Construction> liste_construction = new ArrayList<Action_Construction>();
		ArrayList<Action_Construction> liste_extension = new ArrayList<Action_Construction>();
		liste_construction = T.liste_coups_construction_possibles(j_courant.getCouleur());
		liste_extension = T.liste_extensions_possibles(j_courant.getCouleur());
		if(liste_construction.isEmpty()){
			if(liste_extension.isEmpty())
				return true;
			else {
				for(Action_Construction action : liste_extension){
					if(action.get_nb_batiments()<=j_courant.getHutte())
						return false;
				}
				return true;
			}
		}
		else {
			// Test si j_courant peut construire avec ses batiments
			for(Action_Construction action : liste_construction){
				switch (action.get_type()){
				case HUTTE : return !(j_courant.getHutte()>0);
				case TOUR : return !(j_courant.getTour()>0);
				case TEMPLE : return !(j_courant.getTemple()>0);
				default :
					System.out.println("Type non défini d'action construction");
					return false;
				}
			}
			// Sinon même chose que plus haut
			if(liste_extension.isEmpty())
				return true;
			else {
				for(Action_Construction action : liste_extension){
					if(action.get_nb_batiments()<=j_courant.getHutte())
						return false;
				}
				return true;
			}
		}
	}
	
	// Renvoie une tuile piochée aléatoirement dans la pioche
	public Tuile piocher(){
		if (pioche_vide())
		{
			System.out.println("Pioche vide");
			return null;
		}
		if(annul.size()==0){
			annul.add(new Etat_de_jeu(this.T,j1,j2,j_courant, this.get_etat_jeu()));
			//prev.add(((Joueur_Humain) j_courant).clone());
		}
		tuile_pioche = pioche.pop();
		//etat = Etat.POSER_TUILE;
		Incremente_Phase_Jeu();
		return tuile_pioche;
	}
	
	///////////////////////////////////
	// FONCTIONS RELATIVES AU TERRAIN
	//////////////////////////////////
	
	// Tourne la tuile piochée dans le sens horaire
	public void tourner_tuile(){
		tuile_pioche.Tourner_horaire();
	}
	
	// Renvoie vrai ssi le placement de la tuile piochée est autorisé au point P.
	public boolean placement_tuile_autorise(Point P){
		return T.placement_tuile_autorise(tuile_pioche,P);
	}
	
	// Renvoie 0 si la tuile piochée a pu être placée, -1 si elle est placée, mais le joueur ne peux plus jouer, 1 sinon
	public int placer_tuile(Point P){
		Etat_de_jeu edj = this.get_EDJ_courant();
		if(T.placer_tuile(tuile_pioche, P) == 0){
			annul.push(edj);
			redo.clear();
			//histo_jeu.add(this.get_EDJ_courant());
			//etat = Etat.CONSTRUIRE_BATIMENT;
			Incremente_Phase_Jeu();
			if(joueur_elimine())System.out.println("MOTEUR / Placer_tuile/ joueur elimine (marche?)");
			return 0;
		}
		else return 1;
	}
	
	// SELECTEURS DES BATIMENTS DU JOUEUR
	// Le batiment choisi est une hutte
	public void select_hutte(){
		if(get_etat_jeu() == Phase_Jeu.CONSTRUIRE_BATIMENT)
		bat_choisi = Case.Type_Batiment.HUTTE;
	}
	// Le batiment choisi est un temple
	public void select_temple(){
		if(get_etat_jeu() == Phase_Jeu.CONSTRUIRE_BATIMENT)
		bat_choisi = Case.Type_Batiment.TEMPLE;
	}
	// Le batiment choisi est une tour
	public void select_tour(){
		if(get_etat_jeu() == Phase_Jeu.CONSTRUIRE_BATIMENT)
		bat_choisi = Case.Type_Batiment.TOUR;
	}
	
	// Renvoie vrai ssi le placement du batiment choisi est autorisé au point P.
	public boolean placement_batiment_autorise(Point P){
		return T.placement_batiment_autorise(bat_choisi,j_courant.getCouleur(), P);
	}
	
	// Renvoie 0 si le batiment a pu être placé, 1 sinon
	public int placer_batiment(Point P){
		Etat_de_jeu edj = this.get_EDJ_courant();
		if(T.placer_batiment(bat_choisi,j_courant.getCouleur(), P) == 0){
			annul.push(edj);
			redo.clear();
			histo_jeu.add(this.get_EDJ_courant());
			switch (bat_choisi){
				case HUTTE : j_courant.decrementeHutte(1);
					break;
				case TOUR : j_courant.decrementeTour();
					break;
				case TEMPLE : j_courant.decrementeTemple();
					break;
				default : System.out.println("Message d'erreur pour bat_choisi");
					break;
			}
			Incremente_Phase_Jeu();
			return 0;
		}
		else return 1;
	}
	
	// Essaye d'etendre la cité, renvoi 0 si ça réussi , 1 si l'extension echoue, 2 si le joueur courant n'a pas assez de batiment
	public int etendre_cite(Point P, Case.Type type)
	{
		int res = T.nb_huttes_extension(P,type);
		if(res > j_courant.getHutte())
		{
			return 2;
		}
		Etat_de_jeu edj = this.get_EDJ_courant();
		if( T.etendre_cite(P,type) == 0)
		{
			j_courant.decrementeHutte(res);
			annul.push(edj);
			redo.clear();
			return 0;
		}
		return 1;
	}
	
	// Calcule le score d'un joueur selon la convention :
	//	Temple = 1000 points
	//	Tour = 100 points
	//	Hutte = 1 point
	private int score(Joueur_Generique j){
		return ((nb_max_Temples - j.getTemple())*1000 + (nb_max_Tours - j.getTour())*100 + (nb_max_Huttes - j.getHutte()));
	}
	
	// Termine le tour du joueur courant, renvoie 0 si la partie est terminée, 1 sinon
	// Actualise aussi les données et change de joueur
	public int fin_de_tour(){
		if(victoire_aux_batiments()){
			if(j_courant == j1)System.out.println("Le joueur 1 a gagné!!!");
			else System.out.println("Le joueur 2 a gagné!!!");
			j_gagnant = j_courant;
			return 0;
		}
		else if(pioche_vide()){
			if(score(j1)>score(j2)){
				System.out.println("Le joueur 1 a gagné!!!");
				j_gagnant = j1;
			}
			else if(score(j1)<score(j2)){
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
			//etat = Etat.DEBUT_DE_TOUR;
			init_phase_jeu();
			bat_choisi = Case.Type_Batiment.VIDE;
			if(j_courant instanceof IA_Generique)
			{
				jouer_IA();
			}
			return 1;
		}
	}
	
	// Fait jouer le tour pour une IA
	public int jouer_IA()
	{
		Action_Tuile action_tuile;
		
		piocher();
		action_tuile = ((IA_Generique) j_courant).get_coup_tuile(tuile_pioche);
		//TODO
		tuile_pioche.set_Orientation_Volcan(action_tuile.getTuile().get_Orientation_Volcan());
		if (placer_tuile(action_tuile.getPosition())!=0)
		{
			System.out.println("[jouer_IA] Impossible de poser la tuile");
			return 1;
		}

		//histo_jeu.add(new Etat_de_jeu(T,j1, j2, j_courant, this.clone_Phase()));
		Maj_liste_coup_construction();
		Action_Construction action_construction = ((IA_Generique) j_courant).get_coup_construction();
		// SI c'est une extension
		Point point_construction = action_construction.get_coord();
		
		if(action_construction.get_type() == Action_Construction.Type.EXTENSION)
		{
			if((T.etendre_cite(point_construction,action_construction.get_type_extension()))!= 0)
			{
				System.out.println("[jouer_IA] Impossible d'etendre la cité");
				return 1;
			}
		}
		else
		{
			bat_choisi = Action_vers_Batiment(action_construction.get_type());
			if(T.placer_batiment(bat_choisi, j_courant.getCouleur(), point_construction) != 0)
			{
				System.out.println("[jouer_IA] Impossible de poser un batiment");
				return 1;
			}
		}

		//histo_jeu.add(new Etat_de_jeu(T,j1, j2, j_courant, this.clone_Phase()));
		fin_de_tour();
		return 0;
	}
	
	// Convertit une action en batiment
	private Case.Type_Batiment Action_vers_Batiment(Action_Construction.Type A)
	{
		switch (A)
		{
			case HUTTE :
				return Case.Type_Batiment.HUTTE;
			case TOUR:
				return Case.Type_Batiment.TOUR;
			case TEMPLE:
				return Case.Type_Batiment.TEMPLE;
			default:
				return Case.Type_Batiment.VIDE;
		}
	}
	
	// Permet d'annuler une tuile posée, et de la récupérer
	// Renvoie 0 si tout s'est bien passé, 1 sinon.
	public int annuler(){
		if(annul.size()<1)
			return 1;
		Etat_de_jeu tmp = annul.pop();
		redo.push(tmp);
		this.set_etat_de_jeu(tmp);
		this.Decremente_Phase_Jeu();
		//histo_jeu.remove(histo_jeu.size()-1);
		return 0;
	}
	
	// Permet de reposer une tuile qui a été annulée qui a été annulée
	// Renvoie 0 si tout s'est bien passé, 1 sinon.
	public int refaire(){
		if(redo.isEmpty()) 
			return 1;
		Etat_de_jeu tmp = redo.pop();
		annul.push(tmp);
		this.Incremente_Phase_Jeu();
		this.set_etat_de_jeu(tmp);
		return 0;
	}
	
	// ---------------- Fonction Pour le type Etat -------------------
	/*	
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
				j_courant = next;
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
				j_courant = prev;
				break;
			default:
				return 1;
		}
		return 0;
	}
	*/
	// -------------------- Fonction pour les listes de coup --------------------------
	
	// Met à jour la liste des constructions possibles dans la configuration actuelle
	public void Maj_liste_coup_construction()
	{
		liste_coup_construction = new Liste_coup_construction(T,j_courant);
	}
	
	///////////////////////////////////////////
	//   /!\ FONCTIONS UNIQUEMENT POUR IA	/!\
	///////////////////////////////////////////
	
	// Clone le moteur actuel pour les tests de l'IA
	public Moteur clone(){
		
		Moteur m_copie = new Moteur(T.clone());
		
		// On instancie chaque joueur_copie en fonction du type de l'original
		/*
		j1_copie = copie_type_joueur(j1,j1_copie,m_copie);
		j2_copie = copie_type_joueur(j1,j1_copie,m_copie);
		j_courant_copie = copie_type_joueur(j1,j1_copie,m_copie);
		j_gagnant_copie = copie_type_joueur(j1,j1_copie,m_copie);
		*/
		//m_copie.j_gagnant = new Joueur_Humain(j_gagnant.getCouleur());
		
		for(int i=1;i<this.annul.size();i++)m_copie.annul.add(this.annul.get(i));
		for(int i=0;i<this.redo.size();i++)m_copie.redo.add(this.redo.get(i));
		m_copie.prev = this.prev;
		m_copie.next = this.next;
		m_copie.tuile_pioche = this.tuile_pioche;
		m_copie.bat_choisi = this.bat_choisi;
		m_copie.liste_coup_construction = this.liste_coup_construction.clone();
		m_copie.j_courant = j_courant.clone(m_copie);
		m_copie.j1 = j1.clone(m_copie);
		m_copie.j2 = j2.clone(m_copie);
		m_copie.j_gagnant = j_gagnant.clone(m_copie);
		
		while(m_copie.get_etat_jeu() != this.get_etat_jeu())m_copie.Incremente_Phase_Jeu();
		return m_copie;
	}
	
	
	
	// Prend en parametre une action construction et simule une construction avec les données
	// Renvoie 0 si tout s'est bien passé, 0 sinon
	public int jouer_action(Action_Construction action){
		Point p = action.get_coord();
		Case.Type_Batiment batiment = Action_vers_Batiment(action.get_type());
		if(action.get_type() == Action_Construction.Type.EXTENSION){
			if((T.etendre_cite(p,action.get_type_extension()))!= 0){
				System.out.println("Impossible de construire [EXTENSION]");
				return 1;
			}
		}
		else {
			if(T.placer_batiment(batiment, j_courant.getCouleur(),p) != 0){
				System.out.println("Impossible de construire ["+action.get_type()+"]");
				return 1;
			}
		}
		return 0;
	}
	
	private void set_etat_de_jeu(Etat_de_jeu edj)
	{
		//get_EDJ_courant();
		System.out.println("Set EDJ");
		edj.afficher();
		this.j1 = edj.getj1();
		this.j2 = edj.getj2();
		this.j_courant = edj.getj_courant();
		this.T.afficher();
		this.T = edj.getTerrain().clone();
		this.T.afficher();
		//this.set_Phase_Jeu(edj.getPdj().get_etat_jeu());
		get_EDJ_courant();
	}
	
	private Etat_de_jeu get_EDJ_courant()
	{
		System.out.println("Get EDJ");
		Etat_de_jeu edj = new Etat_de_jeu(this.T, this.j1, this.j2, this.j_courant, get_etat_jeu());
		//edj.afficher();
		return edj;
	}	
	
	private void afficher_pile(Stack<Etat_de_jeu> Stack_edj)
	{
		Stack<Etat_de_jeu> tmp = new Stack<Etat_de_jeu>();
		while(!Stack_edj.isEmpty())
		{
			Stack_edj.peek().afficher();
			tmp.push(Stack_edj.pop());
		}
		while(!tmp.isEmpty())
			Stack_edj.push(tmp.pop());
	}
		
	
}