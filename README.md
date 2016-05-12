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
	getOrientation();	
	
	// tourne la tuile dans le sens Horaire. Renvoie 1 si opération réussie.
	public int Tourner_horaire()
	
	// tourne la tuile dans le sens anti-Horaire. Renvoie 1 si opération réussie.
	public int Tourner_anti_horaire()
	
	// Retourne l'orientation du volcan
	public Case.Orientation get_Orientation_Volcan()
	
	// Renvoie le type de la case désigné par la direction indiqué.
	public Case.Type get_type_case(Case.Orientation orientation)
