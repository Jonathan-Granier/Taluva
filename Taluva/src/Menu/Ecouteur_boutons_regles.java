package Menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ecouteur_boutons_regles implements ActionListener {
	String action;
	Regle_panel page;
	
	Ecouteur_boutons_regles(String action,Regle_panel page){
		this.action = action;
		this.page = page;
	}
	
	public void actionPerformed(ActionEvent e) {
		switch (action){
    		case "Precedent" : 
    			page.precedent();
    			page.repaint();
    			break;
    		case "Suivant" : 
    			page.suivant();
    			page.repaint();
    			break;
    		case "Retour" :
    			page.retour();
    			break;
    		default : 
    			System.out.println("[Ecouteurs_boutons_regles] Bouton non implemente");
    			break;
		}
	}

}
