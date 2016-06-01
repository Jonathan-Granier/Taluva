package charger_sauvegarder;

import Joueur.Joueur_Generique;
import Joueur.Joueur_Humain;
import Moteur.Moteur;
import test.Game;

public class Sauvegarde {
	private Game game;
	private Moteur moteur;
	public Sauvegarde(Game game,Moteur moteur){
		this.game=game;
		this.moteur=moteur;
	}
	public void Restore(Game game,Moteur moteur){
		moteur=this.moteur;
		game=this.game;
	}

}

