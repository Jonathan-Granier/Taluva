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
import Action.Actions_Tour;
import Joueur.IA_Generique;
import Joueur.Joueur_Generique;
import Joueur.Joueur_Humain;
import Liste_coup.Liste_coup_construction;
import terrain.Case;
import terrain.Case.Couleur_Joueur;
import terrain.Terrain;
import terrain.Tuile;
import test.Game;

public class Moteur extends Phase{
	protected Terrain T;
	private Stack<Etat_de_jeu> annul, redo;
	//private ArrayList<Joueur_Humain> prev, next;
	private ArrayList<Etat_de_jeu> histo_jeu;
	Joueur_Humain prev, next;
	
	private Stack<Tuile> pioche;
	private Tuile tuile_pioche;
	private Case.Type_Batiment bat_choisi;
	private Liste_coup_construction liste_coup_construction;
	private int taille_Pioche_initiale;
	
	
	protected Joueur_Generique j_courant;
	protected Joueur_Generique j1;
	protected Joueur_Generique j2;
	protected Joueur_Generique j3;
	protected Joueur_Generique j4;
	private Joueur_Generique j_gagnant;
	private ArrayList<Joueur_Generique> joueurs_gagnant;
	private ArrayList<Joueur_Generique> joueurs_elimines;
	
	private Game game;
	
	protected int nb_Joueur;
	
	public static final int nb_max_Huttes = 20;
	public static final int nb_max_Tours = 2;
	public static final int nb_max_Temples = 3;
	public static final boolean PIOCHE_ALEATOIRE = true;
	public static final boolean PIOCHE_AUTRE_GROUPE = false;
	
	
	
	
	
	// Constructeur du moteur
	public Moteur(Terrain T,Joueur_Generique j1,Joueur_Generique j2,int taille_Pioche_initiale){
		this.T = T;
		annul = new Stack<Etat_de_jeu>();
		redo = new Stack<Etat_de_jeu>();
		pioche = new Stack<Tuile>();
		joueurs_gagnant = new ArrayList<Joueur_Generique>();
		joueurs_elimines = new ArrayList<Joueur_Generique>();
		this.taille_Pioche_initiale = taille_Pioche_initiale;
		init(pioche);
		this.j1 = j1;
		j_courant = j1;
		this.j2 = j2;
		
		init_phase_jeu();
		bat_choisi = Case.Type_Batiment.VIDE;
		histo_jeu = new ArrayList<Etat_de_jeu>();
		nb_Joueur = 2;
		liste_coup_construction = new Liste_coup_construction();
		
		this.game = null;
	}
	
	// Constructeur du moteur sans joueurs
	public Moteur(Terrain T, int taille_Pioche_initiale){
		this.T = T;
		annul = new Stack<Etat_de_jeu>();
		redo = new Stack<Etat_de_jeu>();
		pioche = new Stack<Tuile>();
		joueurs_gagnant = new ArrayList<Joueur_Generique>();
		joueurs_elimines = new ArrayList<Joueur_Generique>();
		this.taille_Pioche_initiale = taille_Pioche_initiale;
		init(pioche);
		init_phase_jeu();
		bat_choisi = Case.Type_Batiment.VIDE;
		histo_jeu = new ArrayList<Etat_de_jeu>();
		nb_Joueur = 0;
		liste_coup_construction = new Liste_coup_construction();
		
		this.game = null;
	}
	
	// Adders de joueurs
	public void add_j1(Joueur_Generique j1){
		this.j1 = j1;
		j_courant = j1;
		nb_Joueur = 1;
	}
		
	public void add_j2(Joueur_Generique j2){
		this.j2 = j2;
		nb_Joueur = 2;
	}
	
	public void add_j3(Joueur_Generique j3){
		this.j3 = j3;
		nb_Joueur = 3;
	}
	
	public void add_j4(Joueur_Generique j4){
		this.j4 = j4;
		nb_Joueur = 4;
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
			default :	System.out.println("[char_to_char] Erreur de type dans le fichier");
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
	
	//Ajout à l'ensemble de tuiles avec le system de fichier de l'autre groupe
	private void rajoute_tuile_Autre_Groupe(String line,ArrayList<Tuile> pioche_fichier){
		int nb;
		nb = 1;
		for(int i=1; i<=nb;i++){
			pioche_fichier.add(new Tuile(char_to_case(line.charAt(0)),char_to_case(line.charAt(2))));
		}
	}
	
	
	// Creer la pioche dans une pile selon le fichier PIECES , Aléatoirement ou non selon PIOCHE_ALEATOIRE est vrai ou non
	private void init(Stack<Tuile> pioche){
		ArrayList<Tuile> pioche_fichier = new ArrayList<Tuile>();
		try {
			File file;
			if (PIOCHE_AUTRE_GROUPE)
				file = new File("../stack");
			else
				file = new File("../PIECES");
			
			FileInputStream fis = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			String line = null;
			try {
				while ((line = br.readLine()) != null) {
					if (PIOCHE_AUTRE_GROUPE)
						 rajoute_tuile_Autre_Groupe(line,pioche_fichier);
					else
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
		pioche_fichier.clear();
		
	}
	

	// Melange l'ArrayList pioche_fichier pour le mettre dans la pioche
	private void melange(Stack<Tuile> pioche, ArrayList<Tuile> pioche_fichier) {
		Random r = new Random();
		int nbElement = 0;
		while(!pioche_fichier.isEmpty() && nbElement < taille_Pioche_initiale)
		{
			pioche.push(pioche_fichier.remove(r.nextInt(pioche_fichier.size())));
			nbElement++;
		}
		
	}
	// Transfert l'Arraylist dans la pioche
	private void transfert(Stack<Tuile> pioche, ArrayList<Tuile> pioche_fichier) {
		int nbElement = 0;
		while(!pioche_fichier.isEmpty() && nbElement < taille_Pioche_initiale)
		{
			pioche.push(pioche_fichier.remove(pioche_fichier.size()-1));
			nbElement++;
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
	
	// Renvoi le nombre joueur
	public int get_NbJoueur()
	{
		return nb_Joueur;
	}
	
	public ArrayList<Joueur_Generique> get_joueurs_gagnant()
	{
		return joueurs_gagnant;
	}
	
	// Initialise le nombre de joueurs ( de 2 à 4) renvoi 0 si tout est correct, 1 sinon
	public int set_NbJoueur(int n)
	{
		if(n>=2 && n<=4)
		{
			nb_Joueur = n;
		}
		else
		{
			return 1;
		}
		return 0;
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
	
	// Renvoie le joueur 3
	public Joueur_Generique getJ3(){
		return j3;
	}
	
	// Renvoie le joueur 4
	public Joueur_Generique getJ4(){
		return j4;
	}
	
	
	// Renvoie le joueur courant
	public int get_num_Jcourant(){
	
		if(EstLeMemeJoueur(j_courant,j1))
			return 1;
		else if(EstLeMemeJoueur(j_courant,j2))
			return 2;
		else if(EstLeMemeJoueur(j_courant,j3))
			return 3;
		return 4;
	}
	
	
	public int get_num_Joueur(Joueur_Generique j){
		
		if(EstLeMemeJoueur(j,j1))
			return 1;
		else if(EstLeMemeJoueur(j,j2))
			return 2;
		else if(EstLeMemeJoueur(j,j3))
			return 3;
		return 4;
	}
	
	
	//Renvoi vrai si a et b sont le même joueur
	public boolean EstLeMemeJoueur(Joueur_Generique a, Joueur_Generique b)
	{
		return a.getCouleur().equals(b.getCouleur());
	}
	
	// Récupère la joueur du joueur adverse (opposé du joueur courant) (Seulement à 2 jouer , fonction pour l'IA
	public Couleur_Joueur get_Couleur_Joueur_Adverse()
	 {
		if(Est_joueur_Courant(j1))
		{
			return j2.getCouleur();
		}
		return j1.getCouleur();
	 }
	
	//Renvoie la couleur de l'IA 
	// Attention cette fonction n'est utilisable que par l'IA et avec seulement 2 joueurs
	public Couleur_Joueur get_Couleur_IA()
	{
		if(j1 instanceof IA_Generique)
			return j1.getCouleur();
		return j2.getCouleur();
	}
	
	// Renvoie vrai si j est le joueur courant
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
			if(nb_Joueur == 2)
			{
				if(Est_joueur_Courant(j1))
					j_courant = j2;
				else
					j_courant = j1;
			}
			if(nb_Joueur == 3)
			{
				if(Est_joueur_Courant(j1))
					j_courant = j2;
				else if(Est_joueur_Courant(j2))
					j_courant = j3;
				else
					j_courant = j1;
			}
			if(nb_Joueur == 4)
			{
				if(Est_joueur_Courant(j1))
					j_courant = j2;
				else if(Est_joueur_Courant(j2))
					j_courant = j3;
				else if(Est_joueur_Courant(j3))
					j_courant = j4;
				else
					j_courant = j1;
			}
			//Si le joueurs selectioné est eliminer et qu'il reste au moins 2 joueurs
			if(joueurs_elimines.contains(j_courant)&& nb_Joueur - joueurs_elimines.size()>=2)
				swap_joueur();
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
				case HUTTE : if (j_courant.getHutte()>0)
								return false;
				case TOUR : if (j_courant.getTour()>0)
					return false;
				case TEMPLE : if (j_courant.getTemple()>0)
					return false;
				default :
					System.out.println("[joueur_elimine] Type non défini d'action construction");
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
			return null;
		}
		
		tuile_pioche = pioche.pop();
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
	
	// Tourne la tuile piochée dans le sens anti-horaire
	public void tourner_tuile_Anti_Horaire(){
		tuile_pioche.Tourner_anti_horaire();
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
			Incremente_Phase_Jeu();
			if(joueur_elimine()){
				joueurs_elimines.add(j_courant);
				fin_de_tour();
			}
			return 0;
		}
		else return 1;
	}
	
	// SELECTEURS DES BATIMENTS DU JOUEUR
	//Le batiment choisi est le vide
	public void select_vide()
	{
		if(get_etat_jeu() == Phase_Jeu.CONSTRUIRE_BATIMENT)
			bat_choisi = Case.Type_Batiment.VIDE;
	}
	
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
				case HUTTE : 
					j_courant.decrementeHutte(1);
					break;
				case TOUR : j_courant.decrementeTour();
					break;
				case TEMPLE : j_courant.decrementeTemple();
					break;
				default : System.out.println("[placer_batiment] Message d'erreur pour bat_choisi");
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
			Incremente_Phase_Jeu();
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
	
	//Lance le jeu au cas ou le j_courant est une IA
	
	public void lancer_partie()
	{
		if (j_courant instanceof IA_Generique)
			jouer_IA();
	}
	
	
	// Termine le tour du joueur courant, renvoie 0 si la partie est terminée, 1 sinon
	// Actualise aussi les données et change de joueur
	public int fin_de_tour(){
		if (Victoire())
			return 0;
		else{
			swap_joueur();
			init_phase_jeu();
			bat_choisi = Case.Type_Batiment.VIDE;
			if(j_courant instanceof IA_Generique)
			{
				jouer_IA();
			}
			return 1;
		}
	}
	
	public void afficher_score()
	{
		System.out.println("Le(s) gagnant(s) est : ");
		for(Joueur_Generique j :joueurs_gagnant)
		{
			System.out.print(j.getCouleur() + "   ");
			System.out.print(j.getnomFaction() + "   ");
		}
		System.out.println();
		System.out.println("Score j1 : "+score(j1)+"\nScore j2 : "+score(j2));
		if(nb_Joueur >=3)
			System.out.println("Score j3 : "+score(j3));
		if(nb_Joueur == 4)
			System.out.println("Score j4 : "+score(j4));
	}
	
	public boolean Victoire()
	{
		//Cas ou le j_courant a consommé 2 types de batiment
		if(victoire_aux_batiments()){
			joueurs_gagnant.add(j_courant);
			finir_partie();
			return true;
		}
		//Cas ou il rest qu'un seul joueur en vie (les autres sont eliminé)
		else if(nb_Joueur - joueurs_elimines.size()==1)
		{
			joueurs_gagnant.add(j1);
			joueurs_gagnant.add(j2);
			if(nb_Joueur >= 3)
			joueurs_gagnant.add(j3);
			if(nb_Joueur == 4)
			joueurs_gagnant.add(j4);
			
			if(joueurs_elimines.contains(j1))
				joueurs_gagnant.remove(j1);
			else if(joueurs_elimines.contains(j2))
				joueurs_gagnant.remove(j2);
			else if(nb_Joueur >= 3 && joueurs_elimines.contains(j3))
				joueurs_gagnant.remove(j3);
			else if(nb_Joueur == 4 && joueurs_elimines.contains(j4))
				joueurs_gagnant.remove(j4);
			return true;
		}
		//Cas pioche vide
		else if(pioche_vide()){
			if(ScoreMax(j1))
				joueurs_gagnant.add(j1);
			if(ScoreMax(j2))
				joueurs_gagnant.add(j2);
			if(nb_Joueur >= 3 && ScoreMax(j3))
				joueurs_gagnant.add(j3);
			if(nb_Joueur == 4 && ScoreMax(j4))
				joueurs_gagnant.add(j4);
			finir_partie();
			return true;
		}
		return false;
	}
	
	
	//Renvoie vrai si le joueur J à le score le plus haut (il peut etre egal à d'autre score)
	public boolean ScoreMax(Joueur_Generique j)
	{
		 boolean retour = score(j)>= score(j1) && score(j) >= score(j2);
		if(nb_Joueur >= 3)
			retour = retour && score(j)>= score(j3);
		if(nb_Joueur == 4)
			retour = retour && score(j)>= score(j4);
		return retour;
	}
	
	
	// Fait jouer le tour pour une IA
	public int jouer_IA()
	{
		
		if(game != null){
			if(j1 instanceof IA_Generique && j2 instanceof IA_Generique)
				game.getCamera().setVueIA();
			else
				Game.BRIGHT = true;
			game.update(null, null);
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Actions_Tour action_tour;
		piocher();
		action_tour = j_courant.get_coup_tour(tuile_pioche);
		
		tuile_pioche.set_Orientation_Volcan(action_tour.getAction_tuile().getTuile().get_Orientation_Volcan());
		if (placer_tuile(action_tour.getAction_tuile().getPosition())!=0)
		{
			System.out.println("[jouer_IA] Impossible de poser la tuile " + action_tour.getAction_tuile().getPosition());
			return 1;
		}
		
		Maj_liste_coup_construction();
		Action_Construction action_construction = action_tour.getAction_construction();
		if(action_construction == null){
			fin_de_tour();
			return 1;
		}
		// SI c'est une extension
		Point point_construction = action_construction.get_coord();
		

		if(game != null){
			game.update(null, null);
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		if(action_construction.get_type() == Action_Construction.Type.EXTENSION)
		{

			if((etendre_cite(point_construction,action_construction.get_type_extension()))!= 0)
			{
				System.out.println("[jouer_IA] Impossible d'etendre la cité " + point_construction + " " + action_construction.get_type_extension());
				return 1;
			}
		}
		else
		{
			bat_choisi = Action_vers_Batiment(action_construction.get_type());
			if(placer_batiment(point_construction) != 0)
			{
				System.out.println("[jouer_IA] Impossible de poser un batiment " + point_construction);
				return 1;
			}
		}
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
		redo.push(this.get_EDJ_courant());
		this.set_etat_de_jeu(tmp);
		this.Decremente_Phase_Jeu();
		return 0;
	}
	
	// Permet de reposer une tuile qui a été annulée
	// Renvoie 0 si tout s'est bien passé, 1 sinon.
	public int refaire(){
		if(redo.isEmpty()) 
			return 1;
		Etat_de_jeu tmp = redo.pop();
		annul.push(this.get_EDJ_courant());
		this.Incremente_Phase_Jeu();
		this.set_etat_de_jeu(tmp);
		return 0;
	}
	
	//Renvoie vrai si la pile Redo est vide
	public boolean PileRefaireVide()
	{
		return redo.isEmpty();
	}
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
		
		Moteur m_copie = new Moteur(T.clone(false),taille_Pioche_initiale);
		// On instancie chaque joueur_copie en fonction du type de l'original
		
		m_copie.prev = this.prev;
		m_copie.next = this.next;
		m_copie.tuile_pioche = this.tuile_pioche;
		m_copie.bat_choisi = this.bat_choisi;
		m_copie.add_j1(j1.clone(m_copie));
		m_copie.add_j2(j2.clone(m_copie));
		m_copie.getJ1().CleanListeners();
		m_copie.getJ2().CleanListeners();
		
		if(this.Est_joueur_Courant(j1))
		
			m_copie.j_courant = m_copie.j1;
		else
			m_copie.j_courant = m_copie.j2;
	
		
		while(m_copie.get_etat_jeu() != this.get_etat_jeu())m_copie.Incremente_Phase_Jeu();
		return m_copie;
	}
	
	// Prend en parametre une action construction et simule une construction avec les données
	// Renvoie 0 si tout s'est bien passé, 1 sinon
	public int jouer_action(Action_Construction action){
		Point p = action.get_coord();
		bat_choisi = Action_vers_Batiment(action.get_type());
		if(action.get_type() == Action_Construction.Type.EXTENSION){
			if((etendre_cite(p,action.get_type_extension()))!= 0){
				return 1;
			}
		}
		else {
			if(placer_batiment(p) != 0){
				return 1;
			}
		}
		return 0;
	}
	
	// Prend en parametre une action tuile et simule une construction avec les données
	// Renvoie 0 si tout s'est bien passé, 1 sinon
	public int jouer_action(Action_Tuile action){
		tuile_pioche = action.getTuile();
		Point p = action.getPosition();
		if(placer_tuile(p) != 0){
			return 1;
		}
		return 0;
	}
	
	// Modifie l'état de jeu actuel
	
	private void set_etat_de_jeu(Etat_de_jeu edj)
	{
		
		this.j1 = edj.getj1().clone(this);
		this.j2 = edj.getj2().clone(this);
		j1.MajListeners();
		j2.MajListeners();
		if(edj.get_nb_Joueur() >= 3 )
		{
			this.j3 = edj.getj3().clone(this);
			j3.MajListeners();
		}
		if(edj.get_nb_Joueur() == 4 )
		{
			this.j4 = edj.getj4().clone(this);
			j4.MajListeners();
		}
		//On recreer la référance de j_courant vers j1 ou j2
		if(EstLeMemeJoueur(edj.getj1(),edj.getj_courant()))
		{
			this.j_courant = j1;
		}
		else if (EstLeMemeJoueur(edj.getj2(),edj.getj_courant()))
		{
			this.j_courant = j2;
		}
		else if (EstLeMemeJoueur(edj.getj3(),edj.getj_courant()))
		{
			this.j_courant = j3;
		}
		else if(EstLeMemeJoueur(edj.getj4(),edj.getj_courant()))
		{
			this.j_courant = j4;
		}
		else
		{
			System.out.println("[MOTEUR/set_etat_de_jeu] Erreur de link entre j_courant et un joueur");
		}
		
		this.T = edj.getTerrain();
	}
	
	// Revoie l'état de jeu actuel
	private Etat_de_jeu get_EDJ_courant()
	{
		Etat_de_jeu edj;
		if(nb_Joueur == 2)
			edj = new Etat_de_jeu(this.T, this.j1, this.j2, this.j_courant, get_etat_jeu());
		else if(nb_Joueur == 3)
			edj = new Etat_de_jeu(this.T, this.j1, this.j2, this.j3, this.j_courant, get_etat_jeu());
		else
			edj = new Etat_de_jeu(this.T, this.j1, this.j2, this.j3, this.j4, this.j_courant, get_etat_jeu());
		return edj;
	}
	
	public void addGame(Game game){
		this.game = game;
	}
	
	public Tuile getRandomTuile(){
		Random R = new Random();
		return pioche.get(R.nextInt(pioche.size()));
	}
	
	public Moteur_light to_Moteur_Light()
	{
		Moteur_light retour = new Moteur_light(this.T.clone(false),taille_Pioche_initiale);
		retour.add_j1(j1.clone());
		retour.add_j2(j2.clone());
		if(this.nb_Joueur == 3)
			retour.add_j3(j3.clone());
		if( this.nb_Joueur == 4)
			retour.add_j4(j4.clone());
		return retour;
	}
}