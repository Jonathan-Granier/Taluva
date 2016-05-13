# Taluva

Spécifications :  
Mettez ici vos spécifications et prototypes ici : (pensez à les consulter !)

Terrain :

	public final static int TAILLE;
	public final static Point CENTRE;

	// Constructeur de terrain vide  
	Terrain();
	
	public Terrain clone();   // Renvoie une copie du terrain, avec référence différente
	public Case [][] getT();  // Renvoie le tableau de cases : [TAILLE] [TAILLE]
	public boolean isEmpty(); 
	
	// PLACEMENT TUILE
	
	// Renvoie le Terrain après placement de tuile au point P. Ne modifie pas la structure actuelle.
	public Terrain consulter_coup_tuile(Tuile tuile, Point P)
	
	// Place la tuile donnée au point P. Renvoie 0 si la tuile a pu etre placée, 1 sinon.
	public int placer_tuile(Tuile tuile, Point P);
	
	// Renvoie vrai ssi le placement de cette tuile est autorisé au point P.
	public boolean placement_tuile_autorise(Tuile tuile, Point P);
	
	// PLACEMENT BATIMENTS
	
	// Renvoie le Terrain après placement de n batiments b au point P. Ne modifie pas la structure actuelle.
	public Terrain consulter_coup_batiment(Case.Type_Batiment b, int n, Point P);
	
	// Place n batiments de type b au point P. Renvoie 0 si le placement a réussi, 1 sinon.
	public int placer_batiment(Case.Type_Batiment b, int n, Point P);
	
	// Renvoie vrai ssi le placement de n batiments de type b au point P est autorisé.
	public boolean placement_batiment_autorise(Case.Type_Batiment b, int n, Point P);
	
	// AFFICHAGE CONSOLE
	
	// Affiche le terrain en un rectangle entre min et max :
	// min -----|
	//  |       |
	//  |------max
	public void afficher(Point min, Point max);
	
	// Affiche le terrain en un rectangle entre xmin,ymin et xmax,ymax :
	public void afficher(int xmin, int ymin, int xmax, int ymax);

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
	
	
	
	
	
