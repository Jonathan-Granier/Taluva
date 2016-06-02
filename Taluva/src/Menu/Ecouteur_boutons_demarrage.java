package Menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ecouteur_boutons_demarrage  implements ActionListener {
	String action;
	Menu_Demarrage menu;
	
	Ecouteur_boutons_demarrage(String action,Menu_Demarrage menu){
		this.action = action;
		this.menu = menu;
	}
	
	public void actionPerformed(ActionEvent e) {
		switch (action){
			case "Continuer" :
				menu.continuer();
				break;
			case "Nouveau" :
				menu.nouveau();
				break;
			case "Charger" :
				menu.charger();
				break;
    		case "Comment jouer" : 
    			menu.comment_jouer();
    			break;
    		case "Credits" :
    			menu.credits();
    			break;
    		case "Quitter" :
    			menu.quitter();
    			break;
    		default : 
    			break;
		}
	}
}
