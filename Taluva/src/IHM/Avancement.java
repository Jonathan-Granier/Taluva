package IHM;

import java.awt.Color;

import javax.swing.JFrame;

import Moteur.Phase.Phase_Jeu;
import Moteur.PhaseListener;
import Moteur.Phase;
import Moteur.Moteur;

public class Avancement implements PhaseListener {
	IHM ihm;
	public Avancement(IHM ihm){
		this.ihm=ihm;
	}
	
	//actualise l'affichage
	public void maj(Phase_Jeu NouveauEtat){
			
		if(ihm.getM().get_num_Jcourant()==1){
			ihm.getJoueur1().setBackground(Color.YELLOW);
			ihm.getJoueur2().setBackground(Color.WHITE);
		}
		else{
			ihm.getJoueur2().setBackground(Color.YELLOW);
			ihm.getJoueur1().setBackground(Color.WHITE);
		}
        switch (NouveauEtat)
		{
			case DEBUT_DE_TOUR:
				ihm.getAnnuler().setEnabled(false);
				if(ihm.getM().PileRefaireVide())
				{
					ihm.getRefaire().setEnabled(false);
				}
				else
				{
					ihm.getRefaire().setEnabled(true);
				}
				ihm.getPioche().setEnabled(true);
				ihm.getFDT().setEnabled(false);
				
				ihm.getTempleJ1().setEnabled(false);
				ihm.getTourJ1().setEnabled(false);
				ihm.getHutteJ1().setEnabled(false);
				
				ihm.getTempleJ2().setEnabled(false);
				ihm.getTourJ2().setEnabled(false);
				ihm.getHutteJ2().setEnabled(false);
				
				ihm.getP().setBackground(Color.YELLOW);
				ihm.getT().setBackground(Color.WHITE);
				ihm.getC().setBackground(Color.WHITE);
				ihm.getF().setBackground(Color.WHITE);
				break;
			
			case POSER_TUILE:
				ihm.getAnnuler().setEnabled(false);
				if(ihm.getM().PileRefaireVide())
				{
					ihm.getRefaire().setEnabled(false);
				}
				else
				{
					ihm.getRefaire().setEnabled(true);
				}
				ihm.getPioche().setEnabled(false);
				ihm.getFDT().setEnabled(false);
				
				ihm.getTempleJ1().setEnabled(false);
				ihm.getTourJ1().setEnabled(false);
				ihm.getHutteJ1().setEnabled(false);
				
				ihm.getTempleJ2().setEnabled(false);
				ihm.getTourJ2().setEnabled(false);
				ihm.getHutteJ2().setEnabled(false);
				
				ihm.getP().setBackground(Color.WHITE);
				ihm.getT().setBackground(Color.YELLOW);
				ihm.getC().setBackground(Color.WHITE);
				ihm.getF().setBackground(Color.WHITE);
				break;
				
			case CONSTRUIRE_BATIMENT:
				ihm.getAnnuler().setEnabled(true);
				if(ihm.getM().PileRefaireVide())
				{
					ihm.getRefaire().setEnabled(false);
				}
				else
				{
					ihm.getRefaire().setEnabled(true);
				}
				ihm.getPioche().setEnabled(false);
				ihm.getFDT().setEnabled(false);
				if(ihm.getM().get_num_Jcourant()==1){
					ihm.getTempleJ1().setEnabled(true);
					ihm.getTourJ1().setEnabled(true);
					ihm.getHutteJ1().setEnabled(true);
				}
				else{
				
					ihm.getTempleJ2().setEnabled(true);
					ihm.getTourJ2().setEnabled(true);
					ihm.getHutteJ2().setEnabled(true);
				}
				
				ihm.getP().setBackground(Color.WHITE);
				ihm.getT().setBackground(Color.WHITE);
				ihm.getC().setBackground(Color.YELLOW);
				ihm.getF().setBackground(Color.WHITE);
				break;
				
				
			case FIN_DE_TOUR:
				ihm.getAnnuler().setEnabled(true);
				ihm.getRefaire().setEnabled(false);
				ihm.getPioche().setEnabled(false);
				ihm.getFDT().setEnabled(true);
				
				ihm.getTempleJ1().setEnabled(false);
				ihm.getTourJ1().setEnabled(false);
				ihm.getHutteJ1().setEnabled(false);
				
				ihm.getTempleJ2().setEnabled(false);
				ihm.getTourJ2().setEnabled(false);
				ihm.getHutteJ2().setEnabled(false);
				
				ihm.getP().setBackground(Color.WHITE);
				ihm.getT().setBackground(Color.WHITE);
				ihm.getC().setBackground(Color.WHITE);
				ihm.getF().setBackground(Color.YELLOW);
				break;
			default:
				
				break;
        	
        }
	}

	@Override
	public void ChangementPhase(Phase_Jeu NouveauEtat) {
		maj(NouveauEtat);
		
	}
	
	
	
}
