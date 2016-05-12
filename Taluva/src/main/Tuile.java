package main;

public class Tuile {
	
	public enum Orientation{
		GAUCHE,
		DROITE;
	}
	private Case.Type [] t;
	private Orientation o;
	
	//		  	Indices :
	//		     _	    _
	//		   _/2\    /1\_
	// GAUCHE /0\_/    \_/3\ DROITE
	//	 	  \_/4\    /5\_/
	//		    \_/    \_/
	
	// Crée une tuile (GAUCHE) avec le volcan à gauche, typeb en bas et typeh en haut.
	Tuile(Case.Type typeb, Case.Type typeh){
		t = new Case.Type[6];
		o = Orientation.GAUCHE;
		t[0] = Case.Type.VOLCAN;
		t[1] = Case.Type.VOLCAN;
		t[2] = typeh;
		t[3] = typeh;
		t[4] = typeb;
		t[5] = typeb;
	}
	
	private void swap_Orientation(){
		o = (o == Orientation.GAUCHE) ? Orientation.DROITE : Orientation.GAUCHE;
	}
	
	public Orientation getOrientation(){
		return o;
	}
	
	// Tourne la tuile d'1/6 de tour en sens horaire
	public void tourner_horaire(){
		int i = (o == Orientation.GAUCHE) ? 0 : 1;
		for(;i<5;i+=2){
			t[i+1]=t[i];
		}
		if(o == Orientation.DROITE) t[0] = t[5];
		swap_Orientation();
	}

	// Tourne la tuile d'1/6 de tour en sens anti-horaire
	public void tourner_anti_horaire(){
		swap_Orientation();
		int i = (o == Orientation.GAUCHE) ? 0 : 1;
		for(;i<5;i+=2){
			t[i]=t[i+1];
		}
		if(o == Orientation.DROITE) t[5] = t[0];
	}
	
	public Case.Type get(int i)
	{
		if(0<=i && i <3)
		{
			if(o == Orientation.GAUCHE)
				return t[2*i];
			else
				return t[2*i +1];
		}
		else
		{
			System.out.println("Tuile: Erreur l'index est inccorect. (get)");
			return Case.Type.VIDE;
		}
	}
}
