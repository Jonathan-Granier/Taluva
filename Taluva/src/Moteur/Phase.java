package Moteur;

import javax.swing.event.EventListenerList;

public class Phase {
	public enum Phase_Jeu{
		DEBUT_DE_TOUR,
		POSER_TUILE,
		CONSTRUIRE_BATIMENT,
		FIN_DE_TOUR,
		FIN_DE_PARTIE;
	}
	private Phase_Jeu phase_jeu;
	private final EventListenerList listeners = new EventListenerList();
	
	
	
	protected Phase ()
	{
		phase_jeu = Phase_Jeu.DEBUT_DE_TOUR;
	}
	
	
	//Renvoi l'etat de jeu actuelle
	public Phase_Jeu get_etat_jeu()
	{
		return phase_jeu;
	}
	// POUR LES TEST DE DIM
	protected void set_Phase_Jeu(Phase_Jeu e)
	{
		phase_jeu = e;
	}
		
	
	
	//Ajoute un ecouteur d'Etat listener
	public void addPhaseListener(PhaseListener listener)
	{
		listeners.add(PhaseListener.class, listener);
	}
	
	//Supprime un ecouteur d'Etat listener
	public void removePhaseListener(PhaseListener listener)
	{
		listeners.remove(PhaseListener.class, listener);
	}
	//Appel tous les listeners pour les mettre à jour
	public void MajListeners()
	{
		for(PhaseListener listener : getPhaseListeners()) 
		{
			listener.ChangementPhase(phase_jeu);
		}
	}
	
	//Renvoi la liste des ecouteurs d'Etat
	public PhaseListener[] getPhaseListeners()
	{
		 return listeners.getListeners(PhaseListener.class);
	}
	
	
	//Initialise l'etat de jeu à DEBUT_DE_TOUR
	protected void init_phase_jeu()
	{
		phase_jeu = Phase_Jeu.DEBUT_DE_TOUR;
		//TODO
		//Appeler Listener
		for(PhaseListener listener : getPhaseListeners()) 
		{
			listener.ChangementPhase(phase_jeu);
		}
		
	}
	
	// Incrémente l'état du jeu
	protected int Incremente_Phase_Jeu()
	{
		switch (phase_jeu)
		{
			case DEBUT_DE_TOUR:
				phase_jeu = Phase_Jeu.POSER_TUILE;
				break;
			case POSER_TUILE:
				phase_jeu = Phase_Jeu.CONSTRUIRE_BATIMENT;
				break;
			case CONSTRUIRE_BATIMENT:
				phase_jeu = Phase_Jeu.FIN_DE_TOUR;
				break;
			case FIN_DE_TOUR:
				phase_jeu = Phase_Jeu.DEBUT_DE_TOUR;
				break;
			case FIN_DE_PARTIE:
				break;
			default:
				return 1;
		}
		//TODO
		//Appeler listener
		for(PhaseListener listener : getPhaseListeners()) 
		{
			listener.ChangementPhase(phase_jeu);
		}
		
		return 0;
	}
	
	// Décrémente l'état du jeu
	protected int Decremente_Phase_Jeu()
	{
		switch (phase_jeu)
		{
			case DEBUT_DE_TOUR:
				break;
			case POSER_TUILE:
				break;
			case CONSTRUIRE_BATIMENT:
				phase_jeu = Phase_Jeu.POSER_TUILE;
				break;
			case FIN_DE_TOUR:
				phase_jeu = Phase_Jeu.CONSTRUIRE_BATIMENT;
				break;
			case FIN_DE_PARTIE:
				break;
			default:
				return 1;
		}
		//TODOcopie_Joueur_Generique(m_copie.j_cou
		//Appeler listener
		for(PhaseListener listener : getPhaseListeners()) 
		{
			listener.ChangementPhase(phase_jeu);
		}
		
		return 0;
	}
	
	protected void finir_partie(){
		phase_jeu = Phase_Jeu.FIN_DE_PARTIE;
		for(PhaseListener listener : getPhaseListeners()) 
		{
			listener.ChangementPhase(phase_jeu);
		}
	}
	
	public Phase clone_Phase()
	{
		Phase clone = new Phase();
		clone.phase_jeu = this.phase_jeu;
		for(PhaseListener listener : getPhaseListeners()) 
		{
			clone.addPhaseListener(listener);
		}
		return clone;
	}

}
