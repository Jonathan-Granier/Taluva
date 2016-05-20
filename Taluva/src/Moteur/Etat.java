package Moteur;

import javax.swing.event.EventListenerList;

public class Etat {
	public enum Etat_Jeu{
		DEBUT_DE_TOUR,
		POSER_TUILE,
		CONSTRUIRE_BATIMENT,
		FIN_DE_TOUR;
	}
	private Etat_Jeu etat_jeu;
	private final EventListenerList listeners = new EventListenerList();
	
	
	
	protected Etat ()
	{
		etat_jeu = Etat_Jeu.DEBUT_DE_TOUR;
	}
	
	
	//Ajoute un ecouteur d'Etat listener
	public void addEtatListener(EtatListener listener)
	{
		listeners.add(EtatListener.class, listener);
	}
	
	//Supprime un ecouteur d'Etat listener
	public void removeEtatListener(EtatListener listener)
	{
		listeners.remove(EtatListener.class, listener);
	}
	
	//Renvoi la liste des ecouteurs d'Etat
	public EtatListener[] getEtatListeners()
	{
		 return listeners.getListeners(EtatListener.class);
	}
	
	//Renvoi l'etat de jeu actuelle
	public Etat_Jeu get_etat_jeu()
	{
		return etat_jeu;
	}
	
	//Initialise l'etat de jeu à DEBUT_DE_TOUR
	protected void init_etat_jeu()
	{
		etat_jeu = Etat_Jeu.DEBUT_DE_TOUR;
		//TODO
		//Appeler Listener
		for(EtatListener listener : getEtatListeners()) 
		{
			listener.EtatChange(etat_jeu);
		}
		
	}
	
	// Incrémente l'état du jeu
	protected int Incremente_Etat_Jeu()
	{
		switch (etat_jeu)
		{
			case DEBUT_DE_TOUR:
				etat_jeu = Etat_Jeu.POSER_TUILE;
				break;
			case POSER_TUILE:
				etat_jeu = Etat_Jeu.CONSTRUIRE_BATIMENT;
				break;
			case CONSTRUIRE_BATIMENT:
				etat_jeu = Etat_Jeu.FIN_DE_TOUR;
				break;
			case FIN_DE_TOUR:
				etat_jeu = Etat_Jeu.DEBUT_DE_TOUR;
				break;
			default:
				return 1;
		}
		//TODO
		//Appeler listener
		for(EtatListener listener : getEtatListeners()) 
		{
			listener.EtatChange(etat_jeu);
		}
		
		return 0;
	}
	
	// Décrémente l'état du jeu
	protected int Decremente_Etat_Jeu()
	{
		switch (etat_jeu)
		{
			case DEBUT_DE_TOUR:
				break;
			case POSER_TUILE:
				break;
			case CONSTRUIRE_BATIMENT:
				etat_jeu = Etat_Jeu.POSER_TUILE;
				break;
			case FIN_DE_TOUR:
				etat_jeu = Etat_Jeu.CONSTRUIRE_BATIMENT;
				break;
			default:
				return 1;
		}
		//TODO
		//Appeler listener
		for(EtatListener listener : getEtatListeners()) 
		{
			listener.EtatChange(etat_jeu);
		}
		
		return 0;
	}

}
