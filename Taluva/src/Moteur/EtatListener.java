package Moteur;

import java.util.EventListener;

public interface EtatListener extends EventListener  {
	public void EtatChange(Etat.Etat_Jeu NouveauEtat);
}
