package main;

import main.Tuile.Orientation;

	public class TuileV2 {
	/*public enum Orientation{
		GAUCHE,
		DROITE;
	}  Déjà importé*/
	
	private Case.Type[] type_case;
	private Case.Orientation o;
	
//  	Indices :
//		     _	    _
//		   _/N\    /N\_
// GAUCHE /E\_/    \_/O\ DROITE
//	 	  \_/S\    /S\_/
//		    \_/    \_/

// Crée une tuile (OUEST) avec le volcan à OUEST, le type 2 au NORD, et le type3 au SUD.
	
	TuileV2 (Case.Type case2, Case.Type case3)
	{
		type_case = new Case.Type[3];
		type_case[0] = Case.Type.VOLCAN;
		type_case[1] = case2;
		type_case[2] = case3;
		o = Case.Orientation.O;
	}
	
	public int Tourner_horaire()
	{
		switch (o)
		{
			case O:
				o = Case.Orientation.N_O;
				return 1;
			case N_O:
				o = Case.Orientation.N_E;
				return 1;
			case N_E:
				o = Case.Orientation.E;
				return 1;
			case E:
				o = Case.Orientation.S_E;
				return 1;
			case S_E:
				o = Case.Orientation.S_O;
				return 1;
			case S_O:
				o = Case.Orientation.O;
				return 1;
			default:
				return 0;
		}
	}
	
	public int Tourner_anti_horaire()
	{
		switch (o)
		{
			case O:
				o = Case.Orientation.S_O;
				return 1;
			case S_O:
				o = Case.Orientation.S_E;
				return 1;
			case S_E:
				o = Case.Orientation.E;
				return 1;
			case E:
				o = Case.Orientation.N_E;
			case N_E:
				o = Case.Orientation.N_O;
				return 1;
			case N_O:
				o = Case.Orientation.O;
				return 1;
			default:
				return 0;
		}
	}
	
	public Case.Orientation get_Orientation()
	{
		return o;
	}
	
	private boolean Orienter_droite()
	{
		return o == Case.Orientation.E || o == Case.Orientation.N_O || o == Case.Orientation.S_O;
	}
	
	public Case.Type get_type_case(Case.Orientation orientation)
	{
		if(Orienter_droite())
		{
			switch(orientation)
			{
				case N_O:
					return type_case[0];
				case N:
					return type_case[0];
				case E:
					return type_case[1];
				case S:
					return type_case[2];
				case S_O:
					return type_case[2];
				default:
					System.out.println(" L'orientation demandée semble ne pas convenir à  l'orientation de la tuile");
					return Case.Type.VIDE;
			}
		}
		
		switch(orientation)
		{
			case O:
				return type_case[0];
			case N:
				return type_case[1];
			case N_E:
				return type_case[1];
			case S:
				return type_case[2];
			case S_O:
				return type_case[2];
			default:
				System.out.println(" L'orientation demandée semble ne pas convenir à  l'orientation de la tuile");
				return Case.Type.VIDE;
		}
	}
	
	
	
}
