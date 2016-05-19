/*
--------------------------------------
-----------Class Obselete-------------
--------------------------------------
*/

package Ecouteur;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import main.Moteur;

public class EcouteurDeSourisPanneauInteraction implements MouseListener, MouseMotionListener{
	Moteur m;
	
	
	public EcouteurDeSourisPanneauInteraction(Moteur m)
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
		switch (m.get_etat_jeu())
		{
			case DEBUT_DE_TOUR:
				
				/*
				 * Si cliquerSurPioche
				 * 		Mettre à jour les coups possible terrain;
				 * 		Increment Etat;
				 */
				
				
				break;
			case POSER_TUILE:
				break;
			case CONSTRUIRE_BATIMENT:
				/*Si clique Sur Batiment Joueur 
				 * 		SelectionBatiment
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
	}
	

}
