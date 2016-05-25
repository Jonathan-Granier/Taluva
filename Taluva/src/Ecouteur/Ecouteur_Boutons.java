package Ecouteur;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import loaders.Loader;

import org.lwjgl.util.vector.Vector3f;

import terrain.Case;
import terrain.Tuile;
import entities.GraphicConstruction;
import entities.GraphicTile;
import entities.GraphicConstruction.GraphicType;
import Moteur.Moteur;

public class Ecouteur_Boutons implements ActionListener {
	
	String action;
	Moteur moteur;
	private static boolean pick;
	private static boolean undo;
	private static GraphicTile Tile;
	private static GraphicConstruction Construction;
	
	public Ecouteur_Boutons(String action,Moteur moteur){
		this.action = action;
		this.moteur = moteur;
	}
	
	public static void setTile(Loader loader,GraphicTile T){
		Tile = T;
	}
	
	public static void setConstruction(GraphicConstruction c){
		Construction = c;
	}
	
    public void actionPerformed(ActionEvent e) {
    	pick = false;
    	undo = false;
    	switch (action){
	    	case "Annuler" :
	    		moteur.annuler();
	    		undo = true;
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
	    		if(moteur.Est_joueur_Courant(moteur.getJ1())){
	    			System.out.println("hutte selectionnée");
	    			moteur.select_hutte();
	    			pick = true;
	    			Construction.setType(GraphicType.HUT);
	    			Construction.setObject3d();
	    			Construction.setColour(moteur.get_Jcourant().getCouleur());
	    		}
	    		break;	
	    	
	    	case "Temple j1" :
	    		if(moteur.Est_joueur_Courant(moteur.getJ1())){
	    			moteur.select_temple();
	    			pick = true;
	    			Construction.setType(GraphicType.TEMPLE);
	    			Construction.setObject3d();
	    		}
	    		break;	
	    	
	    	case "Tour j1" :
	    		if(moteur.Est_joueur_Courant(moteur.getJ1())){
	    			moteur.select_tour();
	    			pick = true;
	    			Construction.setType(GraphicType.TOWER);
	    			Construction.setObject3d();
	    		}
	    		break;
	    		
	    	case "Hutte j2" :
	    		if(moteur.Est_joueur_Courant(moteur.getJ2())){
	    			moteur.select_hutte();
	    			pick = true;
	    			Construction.setType(GraphicType.HUT);
	    			Construction.setObject3d();
	    		}
	    		
	    		break;	
	    	
	    	case "Temple j2" :
	    		if(moteur.Est_joueur_Courant(moteur.getJ2())){
	    			moteur.select_temple();
	    			pick = true;
	    			Construction.setType(GraphicType.TEMPLE);
	    			Construction.setObject3d();
	    		}
	    		break;	
	    	
	    	case "Tour j2" :
	    		if(moteur.Est_joueur_Courant(moteur.getJ2())){
	    			moteur.select_tour();
	    			pick = true;
	    			Construction.setType(GraphicType.TOWER);
	    			Construction.setObject3d();
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

	public static boolean isUndo(){
		return undo;
	}
	
	public static void setPick(boolean pick) {
		Ecouteur_Boutons.pick = pick;
	}
    
}
