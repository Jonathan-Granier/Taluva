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
import Action.Action_Construction;
import Action.Action_Tuile;
import Joueur.IA_Alpha_Beta;
import Joueur.IA_Generique;
import Joueur.IA_Random;
import Joueur.Joueur_Generique;
import Joueur.Joueur_Humain;
import Liste_coup.Liste_coup_construction;
import Liste_coup.Liste_coup_tuile;
import terrain.Case;
import terrain.Terrain;
import terrain.Tuile;

public class Moteur extends Etat{
	private Terrain T;
	private ArrayList<Terrain> annul, redo;
	//private ArrayList<Joueur_Humain> prev, next;
	Joueur_Humain prev, next;
	private ArrayList<Tuile> tuiles;
	private Tuile tuile_pioche;
	private Case.Type_Batiment bat_choisi;
	private Liste_coup_tuile liste_coup_tuile;
	private Liste_coup_construction liste_coup_construction;
	
	private Joueur_Generique j_courant;
	private Joueur_Generique j1;
	private Joueur_Generique j2;
	private Joueur_Generique j_gagnant;


	public static int nb_max_Huttes = 20;
	public static int nb_max_Tours = 2;
	public static int nb_max_Temples = 3;
	
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
		annul = new ArrayList<Terrain>();
		annul.add(T.clone());
		redo = new ArrayList<Terrain>();
		tuiles = new ArrayList<Tuile>();
		init(tuiles);
		this.j1 = j1;
		j_courant = j1;
		this.j2 = j2;
		prev = new Joueur_Humain(j_courant.getCouleur());
		prev = ((Joueur_Humain) j_courant).clone();
		next = new Joueur_Humain(j_courant.getCouleur());
		//prev = new ArrayList<Joueur_Humain>();
		//prev.add(((Joueur_Humain) j_courant).clone());
		//next = new ArrayList<Joueur_Humain>();
		init_etat_jeu();
		//etat = Etat.DEBUT_DE_TOUR;
		
		bat_choisi = Case.Type_Batiment.VIDE;
	}
	
	// Constructeur du moteur sans joueurs
	public Moteur(Terrain T){
		this.T = T;
		annul = new ArrayList<Terrain>();
		annul.add(T.clone());
		redo = new ArrayList<Terrain>();
		tuiles = new ArrayList<Tuile>();
		init(tuiles);
		//etat = Etat.DEBUT_DE_TOUR;
		init_etat_jeu();
		bat_choisi = Case.Type_Batiment.VIDE;
	}
	
	// Adders de joueurs
	public void add_j1(Joueur_Generique j1){
		this.j1 = j1;
		j_courant = j1;
		prev = new Joueur_Humain(j_courant.getCouleur());
		prev = ((Joueur_Humain) j_courant).clone();
		next = new Joueur_Humain(j_courant.getCouleur());
	}
		
	public void add_j2(Joueur_Generique j2){
		this.j2 = j2;
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
	
	//Ajout à l'ensemble de tuiles
	private void rajoute_tuile(String line,ArrayList<Tuile> tuiles){
		int nb;
		nb = Character.getNumericValue(line.charAt(0));
		for(int i=1; i<=nb;i++){
			tuiles.add(new Tuile(char_to_case(line.charAt(2)),char_to_case(line.charAt(4))));
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
					//System.out.println(line);
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
	public Terrain getTerrain(){
		return T;
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
	public Joueur_Generique get_Jcourant(){
		return j_courant;
	}
	public Joueur_Generique getJ1(){
		return j1;
	}
	public Joueur_Generique getJ2(){
		return j2;
	}
	public int get_num_Jcourant(){
		if(j_courant==j1)return 1;
		else return 2;
	}
	
	
	//Renvoie le type de batiment choisi par le joueur
	public Case.Type_Batiment get_bat_choisi(){
		return bat_choisi;
	}
	
	//Renvoie le joueur qui a gagné la partie
	public Joueur_Generique getGagnant(){
		return j_gagnant;
	}
	
	//Renvoi la liste des coups possibles pour la tuile actuelle
	public Liste_coup_tuile get_liste_coup_tuile(){
		return liste_coup_tuile;
	}
	
	//Renvoi la liste des constructions possibles dans la configuration actuelle
	public Liste_coup_construction get_liste_coup_construction(){
		return liste_coup_construction;
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
		if(annul.size()==0){
			annul.add(T.clone());
			//prev.add(((Joueur_Humain) j_courant).clone());
			prev = ((Joueur_Humain) j_courant).clone();
		}
		Random r = new Random();
		tuile_pioche = tuiles.remove(r.nextInt(tuiles.size()));
		//etat = Etat.POSER_TUILE;
		Incremente_Etat_Jeu();
		return tuile_pioche;
	}
	
	///////////////////////////////////
	// FONCTIONS RELATIVES AU TERRAIN
	//////////////////////////////////
	
	public void tourner_tuile(){
		tuile_pioche.Tourner_horaire();
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
			//etat = Etat.CONSTRUIRE_BATIMENT;
			Incremente_Etat_Jeu();
			return 0;
		}
		else return 1;
	}
	
	//SELECTEURS DES BATIMENTS DU JOUEUR
	//Le batiment choisi est une hutte
	public void select_hutte(){
		if(get_etat_jeu() == Etat_Jeu.CONSTRUIRE_BATIMENT)
		bat_choisi = Case.Type_Batiment.HUTTE;
	}
	//Le batiment choisi est un temple
	public void select_temple(){
		if(get_etat_jeu() == Etat_Jeu.CONSTRUIRE_BATIMENT)
		bat_choisi = Case.Type_Batiment.TEMPLE;
	}
	//Le batiment choisi est une tour
	public void select_tour(){
		if(get_etat_jeu() == Etat_Jeu.CONSTRUIRE_BATIMENT)
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
			next = ((Joueur_Humain) j_courant).clone();
			//etat = Etat.FIN_DE_TOUR;
			Incremente_Etat_Jeu();
			return 0;
		}
		else return 1;
	}
	
	
	
	// Essaye d'etendre la cité, renvoi 0 si ça réussi , 1 si l'extension echoue, 2 si le joueur courant n'a pas assez de batiment
	public int etendre_cite(Point P, Case.Type type)
	{
		if(T.nb_huttes_extension(P,type) > j_courant.getHutte())
		{
			return 2;
		}
		return T.etendre_cite(P,type);
		
	}
	
	// Calcule le score d'un joueur selon la convention :
	//	Temple = 1000 points
	//	Tour = 100 points
	//	Hutte = 1 point
	private int score(Joueur_Generique j){
		return ((nb_max_Temples - j.getTemple())*1000 + (nb_max_Tours - j.getTour())*100 + (nb_max_Huttes - j.getHutte()));
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
			init_etat_jeu();
			bat_choisi = Case.Type_Batiment.VIDE;
			if(j_courant instanceof IA_Generique)
			{
				jouer_IA();
			}
			return 1;
		}
	}
	
	
	
	// Fait jouer le tour pour un IA
	public int jouer_IA()
	{
		Action_Tuile action_tuile;
		
		piocher();
		Maj_liste_coup_tuile();
		
		
		action_tuile = ((IA_Generique) j_courant).get_coup_tuile(tuile_pioche);
		if (placer_tuile(action_tuile.getPosition())!=0)
		{
			System.out.println("[jouer_IA] Impossible de poser la tuile");
			return 1;
		}
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
		
		
		fin_de_tour();
		return 0;
	}
	// Convertie une action en batiment
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
	
	
	//Permet d'annuler une tuile posée, et de la récupérer
	//Renvoie 0 si tout s'est bien passé, 1 sinon.
	public int annuler(){
		System.out.println("On est dans l'état : "+ get_etat_jeu()+" ^^\n");
		if(annul.size()<=1)return 1;
		redo.add(annul.remove(annul.size()-1));
		T = annul.get(annul.size()-1);
		int code_erreur = Decremente_Etat_Jeu();
		if(code_erreur == 0 && get_etat_jeu() == Etat_Jeu.CONSTRUIRE_BATIMENT)
			j_courant = prev;
		System.out.println("Et là maintenant on est dans l'état : "+ get_etat_jeu()+" TAVU ?\n");
		return code_erreur;
	}
	
	//Permet de reposer une tuile qui a été annulée qui a été annulée
	//Renvoie 0 si tout s'est bien passé, 1 sinon.
	public int refaire(){
		if(redo.isEmpty()) 
			return 1;
		annul.add(redo.remove(redo.size()-1));
		T = annul.get(annul.size()-1);
		int code_erreur = Incremente_Etat_Jeu();
		if(code_erreur == 0 && get_etat_jeu() == Etat_Jeu.FIN_DE_TOUR)
			j_courant = next;
		return code_erreur;
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
	
	// Met à jour la liste des coups possibles pour la tuile actuelle
	public void Maj_liste_coup_tuile()
	{
		liste_coup_tuile = new Liste_coup_tuile(T);
	}
	
	// Met à jour la liste des constructions possibles dans la configuration actuelle
	public void Maj_liste_coup_construction()
	{
		liste_coup_construction = new Liste_coup_construction(T,j_courant);
	}
	
	
	////////////////////////////////
	// Pour les tests de Gab
	
	public Moteur clone(){
		
		//TODO 
		// Rendre la copie de joueur propre
		
		
			
		Joueur_Generique j1_copie = new Joueur_Humain(j2.getCouleur());;
		Joueur_Generique j2_copie = new Joueur_Humain(j2.getCouleur());
		Joueur_Generique j_courant_copie = new Joueur_Humain(j_courant.getCouleur());
		Joueur_Generique j_gagnant_copie = new Joueur_Humain(j_gagnant.getCouleur());
		
		
		Moteur m_copie = new Moteur(T.clone());
		// On instancie chaque joueur_copie en fonction du type de l'original
		
		j1_copie = copie_type_joueur(j1,j1_copie,m_copie);
		j2_copie = copie_type_joueur(j1,j1_copie,m_copie);
		j_courant_copie = copie_type_joueur(j1,j1_copie,m_copie);
		j_gagnant_copie = copie_type_joueur(j1,j1_copie,m_copie);
			
		
		m_copie.add_j1(j1_copie);
		m_copie.add_j2(j2_copie);
		
		for(int i=1;i<this.annul.size();i++)m_copie.annul.add(this.annul.get(i));
		for(int i=0;i<this.redo.size();i++)m_copie.redo.add(this.redo.get(i));
		m_copie.prev = this.prev;
		m_copie.next = this.next;
		m_copie.tuile_pioche = this.tuile_pioche;
		m_copie.bat_choisi = this.bat_choisi;
		m_copie.liste_coup_construction = this.liste_coup_construction.clone();
		m_copie.liste_coup_tuile = this.liste_coup_tuile.clone();
		
		
		//private Joueur_Generique j_courant;
		j_courant.copie_Joueur_Generique(m_copie.j_courant);
		//private Joueur_Generique j1;
		j1.copie_Joueur_Generique(m_copie.j1);
		//private Joueur_Generique j2;
		j2.copie_Joueur_Generique(m_copie.j2);
		//private Joueur_Generique j_gagnant;
		j_gagnant.copie_Joueur_Generique(m_copie.j_gagnant);
		
		while(m_copie.get_etat_jeu() != this.get_etat_jeu())m_copie.Incremente_Etat_Jeu();
		return m_copie;
	}
	
	
	//TEST
	private Joueur_Generique copie_type_joueur(Joueur_Generique src, Joueur_Generique dest, Moteur m_copie)
	{
		if(src instanceof Joueur_Humain)
		{
			dest = new Joueur_Humain(src.getCouleur());
		}
		
		else if(src instanceof IA_Random)
		{
			dest = new IA_Random(src.getCouleur(),m_copie);
		}
		else if(src instanceof IA_Alpha_Beta)
		{
			dest = new IA_Alpha_Beta(((IA_Alpha_Beta) src).getProfondeur(),src.getCouleur(),m_copie);
		}
		
		return dest;
	}
	 
		
}

