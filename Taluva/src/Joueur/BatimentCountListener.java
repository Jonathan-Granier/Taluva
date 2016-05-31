package Joueur;

import java.util.EventListener;

public interface BatimentCountListener extends EventListener {
	public void MajBatimentCount(Joueur_Generique j, int hutte, int tour, int temple);
}


