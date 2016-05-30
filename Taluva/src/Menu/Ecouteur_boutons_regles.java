package Menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ecouteur_boutons_regles implements ActionListener {
	String action;
	Regles page;
	
	Ecouteur_boutons_regles(String action,Regles page){
		this.action = action;
		this.page = page;
	}
	
	public void actionPerformed(ActionEvent e) {
		switch (action){
    		case "Précedent" : 
    			page.precedent();
    			page.repaint();
    			break;
    		case "Suivant" : 
    			page.suivant();
    			page.repaint();
    			break;
    		default : System.out.println("[Ecouteurs_boutons_regles] Bouton non implémenté");
    			break;
		}
	}

}
