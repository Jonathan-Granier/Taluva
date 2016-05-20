package terrain;

import java.awt.Point;
import java.util.ArrayList;

public class Cite {
	private ArrayList<Point> pts;
	private ArrayList<Case.Type_Batiment> bats;
	private Case.Couleur_Joueur c;
	private int taille;
	private int nb_tours;
	private int nb_temples;
	
	public Cite(Point P, Case.Couleur_Joueur c, Case.Type_Batiment bt){
		pts = new ArrayList<Point>();
		pts.add(P);
		bats = new ArrayList<Case.Type_Batiment>();
		bats.add(bt);
		taille = 1;
		nb_tours = (bt == Case.Type_Batiment.TOUR) ? 1 : 0;
		nb_temples = (bt == Case.Type_Batiment.TEMPLE) ? 1 : 0;
		this.c = c;
	}
	
	public int fusionner_avec(Cite C){
		if(C.c == this.c){
			this.pts.addAll(C.pts);
			this.bats.addAll(C.bats);
			this.taille += C.taille;
			this.nb_tours += C.nb_tours;
			this.nb_temples += C.nb_temples;
			return 0;
		}
		else return 1;
	}
	
	public void ajouter(Point P, Case.Type_Batiment bt){
		pts.add(P);
		bats.add(bt);
		if(bt == Case.Type_Batiment.TOUR) nb_tours ++;
		else if(bt == Case.Type_Batiment.TEMPLE) nb_temples ++;
		taille ++;
	}
	
	// Attention : ne gere pas le cas de deconnexion en deux cites
	public void retirer(Point P){
		int index = pts.indexOf(P);
		if(bats.get(index) == Case.Type_Batiment.TOUR) nb_tours --;
		else if(bats.get(index) == Case.Type_Batiment.TEMPLE) nb_temples --;
		taille --;
		pts.remove(index);
		bats.remove(index);
	}
	
	public ArrayList<Point> getPts(){
		return pts;
	}
	
	public ArrayList<Case.Type_Batiment> getBatiments(){
		return bats;
	}
	
	public int getNbTours(){
		return nb_tours;
	}
	
	public int getNbTemples(){
		return nb_temples;
	}
	
	public int getTaille(){
		return taille;
	}
	
	public Case.Couleur_Joueur getCouleur(){
		return c;
	}
}
