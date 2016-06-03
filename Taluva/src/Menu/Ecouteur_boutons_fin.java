package Menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ecouteur_boutons_fin implements ActionListener{
	String action;
	Menu_fin_partie menu;
	
	Ecouteur_boutons_fin(String action,Menu_fin_partie menu){
		this.action = action;
		this.menu = menu;
	}
	
	public void actionPerformed(ActionEvent e) {
		switch (action){
    		case "Retour" : 
    			menu.retour();
    			break;
    		case "Accueil" :
    			menu.accueil();
    			break;
    		case "Rejouer" :
    			menu.rejouer();
    			break;
    		default :
    			break;
		}
	}

}
