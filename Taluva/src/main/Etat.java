package main;


public class Etat {
	public enum Etat_Jeu{
		DEBUT_DE_TOUR,
		POSER_TUILE,
		CONSTRUIRE_BATIMENT,
		FIN_DE_TOUR;
	}
	private Etat_Jeu etat_jeu;
	
	protected Etat ()
	{
		etat_jeu = Etat_Jeu.DEBUT_DE_TOUR;
	}
	
	
	
	
	public Etat_Jeu get_etat_jeu()
	{
		return etat_jeu;
	}
	
	protected void init_etat_jeu()
	{
		etat_jeu = Etat_Jeu.DEBUT_DE_TOUR;
		//TODO
		//Appeler Listener
	}
	
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
		return 0;
	}
	
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
		return 0;
	}

}
