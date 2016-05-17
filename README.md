# Taluva

## Prochain RDV : mercredi 18 en  milieu d'aprèm

Spécifications :  
Mettez ici vos spécifications et prototypes ici : (pensez à les consulter !)

Terrain :

	public final static int TAILLE;
	public final static Point CENTRE;
	public class Coord{
		public int xmin,ymin,xmax,ymax;
		public Coord(int xmin, int ymin, int xmax, int ymax);
		public Coord clone();
	}

	// Constructeur de terrain vide  
	Terrain();
	
	public Terrain clone();   // Renvoie une copie du terrain, avec référence différente
	public Case getCase(Point P);
	public Case getCase(int i, int j);
	public boolean isEmpty();  // Renvoie vrai ssi le Terrain est vide
	public Coord getLimites();
	// Renvoie les coordonnées limites du terrain : toutes les tuiles sont comprises dans
	// (xmin,ymin)--------|
	//      |             |
	//      |--------(xmax,ymax)
	

	
	//              ___/ 3,0 \
	//             /   \     /
	//         ___/ 2,0 \___/
	//        /   \     /   \
	//    ___/ 1,0 \___/ 3,1 \
	//   /	 \     /   \     /
	//  / 0,0 \___/ 2,1 \___/
	//  \     /   \     /   \
	//   \___/ 1,1 \___/ 3,2 \
	//   /   \     /   \     /
	//  / 0,1 \___/ 2,2 \___/
	//  \     /   \     /   \
	//   \___/ 1,2 \___/ 3,3 \
	
		// PLACEMENT TUILE
		
	//	Position pour le placement :
	//           _      _
	//         _/X\    /X\_
	// GAUCHE / \_/    \_/ \ DROITE
	//        \_/ \    / \_/
	//          \_/    \_/
	
	// Renvoie le Terrain après placement de tuile au point P. Ne modifie pas la structure actuelle.
	public Terrain consulter_coup_tuile(Tuile tuile, Point P)
	
	// Place la tuile donnée au point P. Renvoie 0 si la tuile a pu etre placée, 1 sinon.
	public int placer_tuile(Tuile tuile, Point P);
	
	// Renvoie vrai ssi le placement de cette tuile est autorisé au point P.
	public boolean placement_tuile_autorise(Tuile tuile, Point P);
	
	// PLACEMENT BATIMENTS
	
	// PLACEMENT DIRECT (HORS EXTENSION)
	
	// Renvoie le Terrain après placement direct d'un batiment b de couleur c au point P.
	// Ne modifie pas la structure actuelle.
	public Terrain consulter_coup_batiment(Case.Type_Batiment b, Case.Couleur_Joueur c, Point P);
	
	// Place directement un batiment de type b au point P (hors extension de cite).
	// Renvoie 0 si le placement a réussi, 1 sinon.
	public int placer_batiment(Case.Type_Batiment b, Case.Couleur_Joueur c, Point P);
	
	// Renvoie vrai ssi le placement direct d'un batiment de type b au point P est autorisé.
	public boolean placement_batiment_autorise(Case.Type_Batiment b, Case.Couleur_Joueur c, Point P);
	
	// EXTENSION
	
	// Renvoie le nombre de huttes necessaires a l'extension de la cite presente au point P sur les cases de Type type.
	public int nb_huttes_extension(Point P, Case.Type type);
	
	// Renvoie le Terrain après extension d'une cité présente au point P sur les cases de Type type.
	// Ne modifie pas la structure actuelle.
	public Terrain consulter_extension_cite(Point P, Case.Type type);
	
	// Etend la cité présente au point P sur les cases de Type type.
	// Renvoie 0 si l'extension a réussi, 1 sinon
	public int etendre_cite(Point P, Case.Type type);
	
	// AFFICHAGE CONSOLE
	
	// Affiche le terrain dans la console
	public void afficher();
	
	// LISTE COUPS
	
	// Renvoie la liste des emplacements possibles pour la Tuile tuile
	public ArrayList<Action_Tuile> liste_coups_tuile_possibles(Tuile tuile);
	
	// Renvoie la liste des constructions possibles pour le joueur de couleur c
	public ArrayList<Action_Construction> liste_coups_construction_possibles(Case.Couleur_Joueur c);

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
	
	///////////////////////
	// Getters / Setters
	///////////////////////
	
	// Récupère le terrain courant
	public Terrain getT();
	
	//Affecte un terrain au moteur
	public int setT(Terrain T);
	
	//Renvoie le nombre de Tuiles restantes
	public int get_nbTuiles();
	
	//Renvoie la tuile piochée 
	public Tuile get_tuile_pioche();
	
	//Renvoie le joueur courant
	public joueur_Humain get_Jcourant();
	
	//Renvoie l'état actuel du tour du jeu
	public Etat get_etat_jeu();
	
	//Renvoie le type de batiment choisi par le joueur
	public Case.Type_Batiment get_bat_choisi();
	
	//Renvoie le joueur qui a gagné la partie
	public joueur_Humain getGagnant()
	
	//////////////////////////////////////////////////
	//	FONCTIONS RELATIVES A UN TOUR DE JEU
	/////////////////////////////////////////////////
	
	//Echange le joueur courant;
	swap_joueur();

	//Renvoie vrai si il ne reste plus de tuiles
	public boolean pioche_vide();
	
	//Test si le joueur courant a posé tous les batiments de 2 types differents
	public boolean victoire_aux_batiments();
	
	//Test si le joueur courant est incapable de jouer (impossible de poser des batiments)
	public boolean joueur_elimine ();
	
	//Renvoie une tuile piochée aléatoirement dans la pioche
	public Tuile piocher();
	
	// Renvoie vrai ssi le placement de la tuile piochée est autorisé au point P.
	public boolean placement_tuile_autorise(Point P);
	
	//Renvoie 0 si la tuile piochée a pu être placée, -1 si elle est placée, mais le joueur ne peux plus jouer, 1 sinon
	public int placer_tuile(Point P);
	
	//SELECTEURS DES BATIMENTS DU JOUEUR
	//Le batiment choisi est une hutte
	public void select_hutte();
	
	//Le batiment choisi est un temple
	public void select_temple();
	
	//Le batiment choisi est une tour
	public void select_tour();
	
	//Renvoie vrai ssi le placement du batiment choisi est autorisé au point P.
	public boolean placement_batiment_autorise(Point P);
	
	//Renvoie 0 si le batiment a pu être placé, 1 sinon
	public int placer_batiment(Point P);
	
	//Termine le tour du joueur courant, renvoie 0 si la partie est terminée, 1 sinon
	//Actualise aussi les données et change de joueur
	public int fin_de_tour();
	
	//Permet d'annuler un tuile posée, et de la récupérer
	//Renvoie 0 si tout s'est bien passé, 1 sinon.
	public int annuler();
	
	//Permet de reposer une tuile qui a été annulée qui a été annulée
	//Renvoie 0 si tout s'est bien passé, 1 sinon.
	public int refaire();
	
List_coup_tuile :
	
	//Constructeur:
	List_coup_tuile(Terrain t)
	
	//Renvoie vrai si un coup est possible
	public boolean coup_possible(Case.orientation o, Point coord);
	
	public void affichage();
	
List_coup_construction :
	
	//constructeur
	List_coup_construction(Terrain t)
	
	//Renvoie vrai si le coup est possible
	public boolean coup_possible (Action_construction a)
	
Action_construction :

	// Constructeur d'une action-construction hors extension
	Action_Construction(Type t, Point coord)
	
	// Constructeur d'une action-construction d'extension (en paramètres: 
	// une case de la cité à étendre, le nb de huttes nécessaire et le type de terrain de l'extension).
	Action_Construction(Point coord, Case.Type type_extension, int nb_huttes)
	
	// Retourne la coordonné de la construction
	public Point get_coord()

	// Retourne le type de la construction
	public Type get_type()

	// Si le type est une extension renvoie sur qu'elle type de terrain elle prend place.
	public Case.Type get_type_extension()
	
	// Renvoie le nombre de batiment nécessaire. (surtout interessant pour extension)
	public int get_nb_batiments()

