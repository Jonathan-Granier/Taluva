package Ecouteur;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import IHM.JPanelPioche;
import Moteur.Moteur;
import Moteur.Phase.Phase_Jeu;
import entities.GraphicConstruction;
import entities.GraphicConstruction.GraphicType;
import entities.GraphicTile;
import loaders.Loader;
import terrain.Case;
import terrain.Tuile;

public class Ecouteur_Boutons implements ActionListener {
	
	String action;
	Moteur moteur;
	private JPanelPioche panelpioche;
	private static boolean pick;
	private static boolean undo;
	private static boolean clicked = false;
	private static GraphicTile Tile;
	private static GraphicConstruction Construction;

	
	
	public Ecouteur_Boutons(String action,Moteur moteur){
		this.action = action;
		this.moteur = moteur;
	}
	
	public Ecouteur_Boutons(String action,Moteur moteur,JPanelPioche panelpioche)
	{
		this.action = action;
		this.moteur = moteur;
		this.panelpioche = panelpioche;
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
    	clicked = true;
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
	    		Case.Type nord = moteur.get_tuile_pioche().get_type_case(Case.Orientation.N);
	    		Case.Type sud = moteur.get_tuile_pioche().get_type_case(Case.Orientation.S);
				Tile.setTile(new Tuile(nord,sud));
				Tile.getObject3D().setRotY(90);
				panelpioche.cleanAngle();
				panelpioche.setTuile();
	    		break;
	    	
	    	case "Fin_de_tour" :
	    		// TODO
	    		if(moteur.get_etat_jeu() != Phase_Jeu.CONSTRUIRE_BATIMENT)moteur.fin_de_tour();
	    		break;
	    	case "Hutte" :
    			if(moteur.get_bat_choisi() != Case.Type_Batiment.HUTTE)
    			{
    				moteur.select_hutte();
	    			pick = true;
	    			Construction.setType(GraphicType.HUT);
	    			Construction.setColour(moteur.get_Jcourant().getCouleur());
	    			Construction.setObject3d();
    			}
    			else
    			{
    				moteur.select_vide();
    				pick = false;
    			}
	    		break;	
	    	
	    	case "Temple" :
	    		if(moteur.get_bat_choisi() != Case.Type_Batiment.TEMPLE)
	    		{
	    			moteur.select_temple();
	    			pick = true;
	    			Construction.setType(GraphicType.TEMPLE);
	    			Construction.setColour(moteur.get_Jcourant().getCouleur());
	    			Construction.setObject3d();
	    		}
    			else
    			{
    				moteur.select_vide();
    				pick = false;
    			}
	    		break;	
	    	
	    	case "Tour" :
	    		if(moteur.get_bat_choisi() != Case.Type_Batiment.TOUR)
	    		{
		    		moteur.select_tour();
	    			pick = true;
	    			Construction.setType(GraphicType.TOWER);
	    			Construction.setColour(moteur.get_Jcourant().getCouleur());
	    			Construction.setObject3d();
	    		}
	    		else
    			{
    				moteur.select_vide();
    				pick = false;
	    		}
	    		break;
	    	case "Rotation_Horaire":
	    		moteur.tourner_tuile();
	    		panelpioche.RotationHoraire();
	    		Tile.rotate_horaire();
	    		break;
	    	case "Rotation_Anti_Horaire":
	    		moteur.tourner_tuile_Anti_Horaire();
	    		panelpioche.RotationAntiHoraire();
	    		Tile.rotate_antihoraire();
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

	public static boolean isClicked() {
		if(clicked){
			clicked = false;
			return true;
		}
		return clicked;
	}

	public static void setPick(boolean p){
		pick = p;
	}
	
}
