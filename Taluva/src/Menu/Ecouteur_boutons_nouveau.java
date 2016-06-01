package Menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ecouteur_boutons_nouveau  implements ActionListener {
	String action;
	Nouveau nouveau;
	
	Ecouteur_boutons_nouveau(String action,Nouveau nouveau){
		this.action = action;
		this.nouveau = nouveau;
	}
	
	public void actionPerformed(ActionEvent e) {
		switch (action){
			case "Accueil" :
				nouveau.accueil();
				break;
			case "Paramètres avancés" :
				nouveau.avance();
				break;
			case "Lancer" :
				nouveau.lancer();
    		default : 
    			break;
		}
	}
}
