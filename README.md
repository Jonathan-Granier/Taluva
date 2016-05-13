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
	public Case [][] getT();  // Renvoie le tableau de cases : [TAILLE] [TAILLE]
	public boolean isEmpty();
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
	
	// Renvoie les 6 voisins de la case au Point P
	public Case [] getVoisins(Point P);
	
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
	
	// Renvoie le Terrain après placement de n batiments b au point P. Ne modifie pas la structure actuelle.
	// ATTENTION VA ETRE MODIFIE
	public Terrain consulter_coup_batiment(Case.Type_Batiment b, int n, Point P);
	
	// Place directement un batiment de type b au point P (hors extension de cite).
	// Renvoie 0 si le placement a réussi, 1 sinon.
	public int placer_batiment(Case.Type_Batiment b, Case.Couleur_Joueur c, Point P);
	
	// Etend la cité présente au point P sur les cases de Type type.
	public int etendre_cite(Point P, Case.Type type);
	
	// Renvoie vrai ssi le placement direct d'un batiment de type b au point P est autorisé.
	public boolean placement_batiment_autorise(Case.Type_Batiment b, Case.Couleur_Joueur c, Point P);
	
	// AFFICHAGE CONSOLE
	
	// Affiche le terrain dans la console
	public void afficher();

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
	
	// Récupère le terrain courant
	public Terrain getT();
	
	//Affecte un terrain au moteur
	public int setT(Terrain T);
	
	//Renvoie le nombre de Tuiles restantes
	public int get_nbTuiles();
		
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
	
	//Permet de jouer un tour
	//i.e poser une tuile (et une pièce) sur le terrain T.
	//Renvoie 0 si l'opération à réussi, 1 sinon.
	public int jouer_tour(Point p);
	
	//Permet d'annuler un tuile posée, et de la récupérer
	//Renvoie 1 si tout s'est bien passé, 0 sinon.
	public int annuler();
	
	//Permet de reposer une tuile qui a été annulée qui a été annulée
	//Renvoie 1 si tout s'est bien passé, 0 sinon.
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
