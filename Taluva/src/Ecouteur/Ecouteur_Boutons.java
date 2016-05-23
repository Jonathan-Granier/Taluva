package Ecouteur;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import loaders.Loader;

import org.lwjgl.util.vector.Vector3f;

import terrain.Case;
import terrain.Tuile;
import entities.GraphicTile;

import Moteur.Moteur;

public class Ecouteur_Boutons implements ActionListener {
	
	String action;
	Moteur moteur;
	private static boolean pick;
	private static GraphicTile Tile;
	
	public Ecouteur_Boutons(String action,Moteur moteur){
		this.action = action;
		this.moteur = moteur;
	}
	
	public static void setTile(Loader loader,GraphicTile T){
		Tile = T;
	}
	
    public void actionPerformed(ActionEvent e) {
    	pick = false;
    	switch (action){
	    	case "Annuler" :
	    		moteur.annuler();
	    		break;
	    		
	    	case "Refaire" :
	    		moteur.refaire();
	    		break;
	    	
	    	case "Piocher" :
	    		moteur.piocher();
	    		Tile.getObject3D().setRotY(90);
				Tile.setTile(new Tuile(Case.Type.MONTAGNE,Case.Type.SABLE));
	    		break;
	    	
	    	case "Fin_de_tour" :
	    		moteur.fin_de_tour();
	    		break;
	    		
	    	case "Hutte j1" :
	    		if(moteur.get_Jcourant() == moteur.getJ1()){
	    			moteur.select_hutte();
	    			pick = true;
	    		}
	    		break;	
	    	
	    	case "Temple j1" :
	    		if(moteur.get_Jcourant() == moteur.getJ1()){
	    			moteur.select_temple();
	    			pick = true;
	    		}
	    		break;	
	    	
	    	case "Tour j1" :
	    		if(moteur.get_Jcourant() == moteur.getJ1()){
	    			moteur.select_tour();
	    			pick = true;
	    		}
	    		break;
	    		
	    	case "Hutte j2" :
	    		if(moteur.get_Jcourant() == moteur.getJ2()){
	    			moteur.select_hutte();
	    			pick = true;
	    		}
	    		
	    		break;	
	    	
	    	case "Temple j2" :
	    		if(moteur.get_Jcourant() == moteur.getJ2()){
	    			moteur.select_temple();
	    			pick = true;
	    		}
	    		break;	
	    	
	    	case "Tour j2" :
	    		if(moteur.get_Jcourant() == moteur.getJ2()){
	    			moteur.select_tour();
	    			pick = true;
	    		}
	    		break;	
	    	
	    	default :
	    		System.out.println("Bouton non défini");
	    		break;
    	}	
    }

	public static boolean isPick() {
		return pick;
	}

	public static void setPick(boolean pick) {
		Ecouteur_Boutons.pick = pick;
	}
    
}
