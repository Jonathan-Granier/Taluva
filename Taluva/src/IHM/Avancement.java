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
			ihm.getJoueur1().setBackground(ihm.getM().get_Jcourant().getCouleur().getcolor());
			ihm.getJoueur2().setBackground(Color.WHITE);
		}
		else{
			ihm.getJoueur2().setBackground(ihm.getM().get_Jcourant().getCouleur().getcolor());
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
				ihm.getPiocher().setEnabled(true);
				ihm.getFDT().setEnabled(false);
				
				ihm.getTemple().setEnabled(false);
				ihm.getTour().setEnabled(false);
				ihm.getHutte().setEnabled(false);
				
				
				ihm.getPoser().setBackground(ihm.getM().get_Jcourant().getCouleur().getcolor());
				ihm.getConstruire().setBackground(Color.WHITE);
				ihm.getFinir().setBackground(Color.WHITE);
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
				ihm.getPiocher().setEnabled(false);
				ihm.getFDT().setEnabled(false);
				
				ihm.getTemple().setEnabled(false);
				ihm.getTour().setEnabled(false);
				ihm.getHutte().setEnabled(false);
				
				
				
				ihm.getPoser().setBackground(ihm.getM().get_Jcourant().getCouleur().getcolor());
				ihm.getConstruire().setBackground(Color.WHITE);
				ihm.getFinir().setBackground(Color.WHITE);
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
				ihm.getPiocher().setEnabled(false);
				ihm.getFDT().setEnabled(false);
				if(ihm.getM().get_num_Jcourant()==1){
					ihm.getTemple().setEnabled(!(ihm.getM().getJ1().getTemple() <= 0));
					ihm.getTour().setEnabled(!(ihm.getM().getJ1().getTour() <= 0));
					ihm.getHutte().setEnabled(!(ihm.getM().getJ1().getHutte() <= 0));

				}
				else{
					ihm.getTemple().setEnabled(!(ihm.getM().getJ2().getTemple() <= 0));
					ihm.getTour().setEnabled(!(ihm.getM().getJ2().getTour() <= 0));
					ihm.getHutte().setEnabled(!(ihm.getM().getJ2().getHutte() <= 0));
				}
				
				
				ihm.getPoser().setBackground(ihm.getM().get_Jcourant().getCouleur().getcolor());
				ihm.getConstruire().setBackground(ihm.getM().get_Jcourant().getCouleur().getcolor());
				ihm.getFinir().setBackground(Color.WHITE);
				
				break;
				
				
			case FIN_DE_TOUR:
				ihm.getAnnuler().setEnabled(true);
				ihm.getRefaire().setEnabled(false);
				ihm.getPiocher().setEnabled(false);
				ihm.getFDT().setEnabled(true);
				
				ihm.getTemple().setEnabled(false);
				ihm.getTour().setEnabled(false);
				ihm.getHutte().setEnabled(false);
				
				
				ihm.getPoser().setBackground(ihm.getM().get_Jcourant().getCouleur().getcolor());
				ihm.getConstruire().setBackground(ihm.getM().get_Jcourant().getCouleur().getcolor());
				ihm.getFinir().setBackground(ihm.getM().get_Jcourant().getCouleur().getcolor());
				
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
