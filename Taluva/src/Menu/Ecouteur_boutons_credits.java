package Menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ecouteur_boutons_credits implements ActionListener {
	String action;
	Credits credit;
	
	public Ecouteur_boutons_credits(String action, Credits credit){
		this.action = action;
		this.credit = credit;
	}
	
	public void actionPerformed(ActionEvent e) {
		switch (action){
			case "Accueil" :
				credit.accueil();
				break;
			default : 
				System.out.println("[Ecouteur_boutons_credit] Bouton non défini");
				break;
		}
	}

}
