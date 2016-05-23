package Ecouteur;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Moteur.Moteur;

public class Ecouteur_Boutons implements ActionListener {
	
	String action;
	Moteur moteur;
	private static boolean pick;
	
	public Ecouteur_Boutons(String action,Moteur moteur){
		this.action = action;
		this.moteur = moteur;
	}
	
    public void actionPerformed(ActionEvent e) {
    	
    	switch (action){
	    	case "Annuler" :
	    		moteur.annuler();
	    		break;
	    		
	    	case "Refaire" :
	    		moteur.refaire();
	    		break;
	    	
	    	case "Piocher" :
	    		moteur.piocher();
	    		break;
	    	
	    	case "Fin_de_tour" :
	    		moteur.fin_de_tour();
	    		break;
	    		
	    	case "Hutte j1" :
	    		if(moteur.get_Jcourant() == moteur.getJ1())
	    			moteur.select_hutte();		
	    		break;	
	    	
	    	case "Temple j1" :
	    		if(moteur.get_Jcourant() == moteur.getJ1())
	    			moteur.select_temple();
	    		break;	
	    	
	    	case "Tour j1" :
	    		if(moteur.get_Jcourant() == moteur.getJ1())
	    			moteur.select_tour();
	    		break;
	    		
	    	case "Hutte j2" :
	    		if(moteur.get_Jcourant() == moteur.getJ2())
	    			moteur.select_hutte();
	    		
	    		break;	
	    	
	    	case "Temple j2" :
	    		if(moteur.get_Jcourant() == moteur.getJ2())
	    			moteur.select_temple();
	    		break;	
	    	
	    	case "Tour j2" :
	    		if(moteur.get_Jcourant() == moteur.getJ2())
	    			moteur.select_tour();
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
