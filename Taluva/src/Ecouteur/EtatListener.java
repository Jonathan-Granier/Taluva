package Ecouteur;

import java.util.EventListener;

import main.Etat;
import main.Moteur;

public interface EtatListener extends EventListener  {
	public void EtatChange(Etat.Etat_Jeu NouveauEtat);
}
