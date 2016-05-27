package Joueur;

import java.util.EventListener;

import Moteur.Moteur;

public interface BatimentCountListener extends EventListener {
	public void MajBatimentCount(Joueur_Generique j, int hutte, int tour, int temple);
}


