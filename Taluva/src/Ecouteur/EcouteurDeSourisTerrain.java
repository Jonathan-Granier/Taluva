package Ecouteur;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import main.Moteur;
import terrain.Case;
import terrain.Tuile;

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
		Point Point_courant = new Point(e.getX(),e.getY());
		
		switch (m.etat)
		{
			case DEBUT_DE_TOUR:
				break;
			case POSER_TUILE:
				/*
				 * 
				 * 
				 * 
				 * Essayer de poser Tuile
				 * Si Erreur 
				 * 	Afficher-erreur
				 * Sinon 
				 * 	Increment Etat;
				 * 	Mettre à jour les coups possible Batiment; 
				 *
				 */
				
				 if(m.placer_tuile(Point_courant) == 0)
				 {
					 m.Maj_liste_coup_construction();
				 }
				 else
				 {
					 System.out.println("Il est impossible de poser une tuille ici");
				 }
				
				
				
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
				
				if ( m.get_bat_choisi() == Case.Type_Batiment.VIDE)
				{
					// 	Menu deroulant
				}
				else
				{
					
					if (m.placer_batiment(Point_courant)!= 0)
					{
						System.out.println("Il est impossible de poser un batiment ici");
					}
				}
				
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
				*	La tuile suit la souris qui est suivie par le chat
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
