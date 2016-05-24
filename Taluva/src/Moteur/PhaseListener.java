package Moteur;

import java.util.EventListener;

public interface PhaseListener extends EventListener  {
	public void ChangementPhase(Phase.Phase_Jeu NouvellePhase);
}
