package main;

public class Case {
	public final static char VOLCAN = 'V';
	public final static char MONTAGNE = 'M';
	public final static char PLAINE = 'P';
	public final static char LAC = 'L';
	public final static char SABLE = 'S';
	public final static char FORET = 'F';
	
	public final static int NORD = 0;
	public final static int SUD = 1;
	public final static int NORD_EST = 2;
	public final static int NORD_OUEST = 3;
	public final static int SUD_EST = 4;
	public final static int SUD_OUEST = 5;
	
	private char type;
	private int orientation;
	
	Case(char type){
		this.type = type;
		orientation = 0;
	}
	
	Case(char type, int orientation){
		this.type = type;
		this.orientation = orientation;
	}
	
	// Renvoie le type de la case
	public char getType(){
		return type;
	}
	
	// Renvoie l'orientation de la case. N'a de sens que si getType()=VOLCAN
	public int getOrientation(){
		return orientation;
	}
}
