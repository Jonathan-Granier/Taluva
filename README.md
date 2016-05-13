# Taluva

Spécifications :  
Mettez ici vos spécifications et prototypes ici : (pensez à les consulter !)

Terrain :

	// Constructeur de terrain vide  
	Terrain();

Tuile :
  
	// Constructeur d'une tuile triple (orienté ouest, avec un volcan).
	Tuile(Case.Type t1, Case.Type t2); 
	
	// Retourne l'orientation Gauche/Droite de la tuile.
	public Orientation getOrientation();	
	
	// tourne la tuile dans le sens Horaire. Renvoie 1 si opération réussie.
	public int Tourner_horaire();
	
	// tourne la tuile dans le sens anti-Horaire. Renvoie 1 si opération réussie.
	public int Tourner_anti_horaire();
	
	// Retourne l'orientation du volcan
	public Case.Orientation get_Orientation_Volcan();
	
	// Renvoie le type de la case désigné par la direction indiqué.
	public Case.Type get_type_case(Case.Orientation orientation);


Moteur :
	// Constructeur du moteur
	Moteur(Terrain T,joueur_Humain j1, joueur_Humain j2);
	
	//Echange le joueur courant;
	swap_joueur();
	
	//Renvoi vrai si la pioche est vide
	boolean pioche_vide();
	
	//Test si le joueur courant a posé tous les batiments de 2 types differents
	boolean victoire_aux_batiments();
	
	//Test si le joueur courant est incapable de jouer (impossible de poser des batiments)
	boolean joueur_elimine ();
	
	//Renvoie une tuile piochée aléatoirement dans la pioche
	Tuile piocher();
	
	//
	int jouer_tour(Point p);
	
	//
	int annuler();
	
	//
	int refaire();
	
	//Set et Get
	Terrain getT();
	int setT(Terrain T);
	get_nbTuiles();
	
	
	
	
	
