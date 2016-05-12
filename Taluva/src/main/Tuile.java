package main;

public class Tuile {
	
	private char [] t;
	private int TYPE;	// 0 ou 1
	
	//		  Indices :
	//		     _	    _
	//		   _/2\    /1\_
	// Type 0 /0\_/    \_/3\ Type 1
	//	 	  \_/4\    /5\_/
	//		    \_/    \_/
	
	// Crée une tuile (type 0) avec le volcan à gauche, typeb en bas et typeh en haut.
	Tuile(char typeb, char typeh){
		t = new char[6];
		TYPE = 0;
		t[0] = Case.VOLCAN;
		t[2] = typeh;
		t[4] = typeb;
	}
	
	private void swap_type(){
		TYPE = 1-TYPE;
	}
	
	public void tourner_horaire(){
		for(int i=TYPE;i<5;i+=2){
			t[i+1]=t[i];
		}
		if(TYPE == 1) t[0] = t[5];
		swap_type();
	}
	
	public void tourner_anti_horaire(){
		for(int i=TYPE+1;i<=5;i+=2){
			t[i-1]=t[i];
		}
		if(TYPE == 1) t[0] = t[5];
		swap_type();
	}
}
