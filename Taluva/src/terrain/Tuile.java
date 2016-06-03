package terrain;

public class Tuile {
	public enum Orientation{
		GAUCHE,
		DROITE;
	}
	
	private Case.Type[] type_case;
	private Case.Orientation o;
	
//  	Indices :
//		     _	    _
//		   _/N\    /N\_
// GAUCHE /O\_/    \_/E\ DROITE
//	 	  \_/S\    /S\_/
//		    \_/    \_/

// Crée une tuile (GAUCHE) avec le volcan à OUEST, le type 2 au NORD, et le type3 au SUD.
	public Tuile (Case.Type case2, Case.Type case3)
	{
		type_case = new Case.Type[3];
		type_case[0] = Case.Type.VOLCAN;
		type_case[1] = case2;
		type_case[2] = case3;
		o = Case.Orientation.O;
	}
	
	public Tuile clone(){
		Tuile tmp = new Tuile(type_case[1],type_case[2]);
		tmp.o = this.o;
		return tmp;
	}

	// Retourne l'orientation Gauche/Droite de la tuile.
	public Orientation getOrientation()
	{
		if(OrienteDroite())
		{
			return Orientation.DROITE;
		}
		return Orientation.GAUCHE;
	}
	
	// Tourne la tuile dans le sens Horaire. Renvoie 1 si opération réussie.
	public int Tourner_horaire()
	{
		switch (o)
		{
			case O:
				o = Case.Orientation.N_O;
				return 0;
			case N_O:
				o = Case.Orientation.N_E;
				return 0;
			case N_E:
				o = Case.Orientation.E;
				return 0;
			case E:
				o = Case.Orientation.S_E;
				return 0;
			case S_E:
				o = Case.Orientation.S_O;
				return 0;
			case S_O:
				o = Case.Orientation.O;
				return 0;
			default:
				return 1;
		}
	}
	
	// Tourne la tuile dans le sens anti-Horaire. Renvoie 1 si opération réussie.
	public int Tourner_anti_horaire()
	{
		switch (o)
		{
			case O:
				o = Case.Orientation.S_O;
				return 0;
			case S_O:
				o = Case.Orientation.S_E;
				return 0;
			case S_E:
				o = Case.Orientation.E;
				return 0;
			case E:
				o = Case.Orientation.N_E;
				return 0;
			case N_E:
				o = Case.Orientation.N_O;
				return 0;
			case N_O:
				o = Case.Orientation.O;
				return 0;
			default:
				return 1;
		}
	}
	
	// Retourne l'orientation du volcan
	public Case.Orientation get_Orientation_Volcan()
	{
		return o;
	}
	
	public int set_Orientation_Volcan(Case.Orientation oV){
		switch(oV){
			case O:
			case S_O:
			case S_E:
			case E:
			case N_E:
			case N_O:
				o = oV;
				return 0;
			default:
				return 1;
		}
	}
	
	// Renvoie vrai si la pointe est vers la droite.
	private boolean OrienteDroite()
	{
		return o == Case.Orientation.E || o == Case.Orientation.N_O || o == Case.Orientation.S_O;
	}
	
	// Renvoie le type de la case désigné par la direction indiqué.
	public Case.Type get_type_case(Case.Orientation orientation)
	{
		if(OrienteDroite())
		{
			switch(orientation)
			{
				case N:
					switch(o){
					case E: return type_case[2];
					case N_O: return type_case[0];
					case S_O: return type_case[1];
					default:
					}
				case E:
					switch(o){
					case E: return type_case[0];
					case N_O: return type_case[1];
					case S_O: return type_case[2];
					default:
					}
				case S:
					switch(o){
					case E: return type_case[1];
					case N_O: return type_case[2];
					case S_O: return type_case[0];
					default:
					}
				default:
					return null;
			}
		}
		else{
			switch(orientation)
			{
				case O:
					switch(o){
					case O: return type_case[0];
					case N_E: return type_case[2];
					case S_E: return type_case[1];
					default:
					}
				case N:
					switch(o){
					case O: return type_case[1];
					case N_E: return type_case[0];
					case S_E: return type_case[2];
					default:
					}
				case S:
					switch(o){
					case O: return type_case[2];
					case N_E: return type_case[1];
					case S_E: return type_case[0];
					default:
					}
				default:
					return null;
			}
		}
	}
	
	public void afficher(){
		System.out.println("Tuile :");
		System.out.println(" " + get_type_case(Case.Orientation.N).toChar());
		if(getOrientation() == Orientation.DROITE)
			System.out.println("  " + get_type_case(Case.Orientation.E).toChar());
		else
			System.out.println("" + get_type_case(Case.Orientation.O).toChar());
		System.out.println(" " + get_type_case(Case.Orientation.S).toChar());
		
	}
}