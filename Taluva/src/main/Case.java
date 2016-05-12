package main;

public class Case {
	
	public enum Type{
		VOLCAN,
		MONTAGNE,
		PLAINE,
		LAC,
		SABLE,
		FORET;
	}
	
	public enum Orientation{
		N,
		N_E,
		N_O,
		S,
		S_O,
		S_E;
	}
	
	private Type type;
	private Orientation orientation;
	private Batiment.Type bt;
	private int nb_b;
	private int niveau;
	
	Case(Type type){
		this.type = type;
		orientation = Orientation.N;
		nb_b = 0;
		niveau = 1;
	}
	
	Case(Type type, Orientation orientation){
		this.type = type;
		this.orientation = orientation;
		nb_b = 0;
		niveau = 1;
	}
	
	// Renvoie le type de la case
	public Type getType(){
		return type;
	}
	
	public void setType(Type t){
		type = t;
	}
	
	// Renvoie l'orientation de la case. N'a de sens que si getType()=OrientationCase.VOLCAN
	public Orientation getOrientation(){
		return orientation;
	}
	
	public void setOrientation(Orientation o){
		orientation = o;
	}
	
	// Renvoie le niveau de la case
	public int getNiveau(){
		return niveau;
	}
	
	// Augmente le niveau de la case de 1
	public void incrNiveau(){
		niveau ++ ;
	}
	
	// Renvoie le nombre de batiments sur la case.
	public int getBNb(){
		return nb_b;
	}
	
	// Renvoie le type de batiment sur la case. N'a de sens que si getBNb()>0.
	public Batiment.Type getBType(){
		return bt;
	}

	// Ajoute n batiments b sur la case. n n'est pris en compte que si b est une HUTTE.
	// Renvoie 0 si le placement était autorisé et a réussi, 1 sinon.
	public int ajouter_batiment(Batiment.Type bt, int n){
		// Si on peut ajouter un batiment (la case est valide et libre)
		if(type != Type.VOLCAN && nb_b == 0 && n>0){
			// Si c'est une hutte, on peut uniquement en ajouter le meme nombre que le niveau de la case
			if(bt==Batiment.Type.HUTTE){
				if(n == niveau){
					nb_b = n;
					this.bt = bt;
				}
				else return 1;
			}
			else{
				nb_b = 1;
				this.bt = bt;
			}
			return 0;
		}
		else return 1;
	}
	
	// Retire tous les batiments de la case. Renvoie 1 si aucun batiment n'était présent, 0 sinon.
	public int retirer_batiments(){
		if(nb_b > 0){
			nb_b = 0;
			return 0;
		}
		else return 1;
	}
}
