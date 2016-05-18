package Ecouteur;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import main.Moteur;

public class EcouteurDeSourisTerrain implements MouseListener, MouseMotionListener{
	
	Moteur m;
	
	public EcouteurDeSourisTerrain(Moteur m)
	{
		this.m = m;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		switch (m.etat)
		{
			case DEBUT_DE_TOUR:
				break;
			case POSER_TUILE:
				/*
				 * 
				 * Essayer de poser Tuile
				 * Si Erreur 
				 * 	Afficher-erreur
				 * Sinon 
				 * 	Increment Etat;
				 * 	Mettre à jour les coups possible Batiment; 
				 *
				 */
				
				break;
			case CONSTRUIRE_BATIMENT:
				/*
				 * Si batiment selection est vide
				 * 	Menu deroulant
				 * Sinon 
				 * 	Verifie construction possible
				 * 	Si erreur
				 * 		afficher erreur;
				 * 	Sinon
				 * 		Increment Etat;
				 * 		 
				 * 
				 */
				
				
				break;
			case FIN_DE_TOUR:
				break;
			default:
				break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		switch (m.etat)
		{
			case POSER_TUILE:
				/*
				*	La tuile suit la souris
				*/
				break;
			case CONSTRUIRE_BATIMENT:
				/*
				 * Si batiment selectionné
				 * 		Le batiment suit la souris
				 * 
				 */
				break;
			default:
				break;
		}
		
	}
}
