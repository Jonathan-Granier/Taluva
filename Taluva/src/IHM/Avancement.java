package IHM;

import java.awt.Color;

import javax.swing.JFrame;

import Joueur.BatimentCountListener;
import Joueur.Joueur_Generique;
import Moteur.Phase.Phase_Jeu;
import Moteur.PhaseListener;
import Moteur.Phase;
import Moteur.Moteur;

public class Avancement implements PhaseListener, BatimentCountListener {
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
				ihm.getPioche().setEnabled(true);
				ihm.getFDT().setEnabled(false);
				
				ihm.getTemple().setEnabled(false);
				ihm.getTour().setEnabled(false);
				ihm.getHutte().setEnabled(false);
				
				ihm.getPiocher().setBackground(ihm.getM().get_Jcourant().getCouleur().getcolor());
				ihm.getPoser().setBackground(Color.WHITE);
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
				ihm.getPioche().setEnabled(false);
				ihm.getFDT().setEnabled(false);
				
				ihm.getTemple().setEnabled(false);
				ihm.getTour().setEnabled(false);
				ihm.getHutte().setEnabled(false);
				
				
				ihm.getPiocher().setBackground(ihm.getM().get_Jcourant().getCouleur().getcolor());
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
				ihm.getPioche().setEnabled(false);
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
				
				ihm.getPiocher().setBackground(ihm.getM().get_Jcourant().getCouleur().getcolor());
				ihm.getPoser().setBackground(ihm.getM().get_Jcourant().getCouleur().getcolor());
				ihm.getConstruire().setBackground(ihm.getM().get_Jcourant().getCouleur().getcolor());
				ihm.getFinir().setBackground(Color.WHITE);
				
				break;
				
				
			case FIN_DE_TOUR:
				ihm.getAnnuler().setEnabled(true);
				ihm.getRefaire().setEnabled(false);
				ihm.getPioche().setEnabled(false);
				ihm.getFDT().setEnabled(true);
				
				ihm.getTemple().setEnabled(false);
				ihm.getTour().setEnabled(false);
				ihm.getHutte().setEnabled(false);
				
				ihm.getPiocher().setBackground(ihm.getM().get_Jcourant().getCouleur().getcolor());
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

	@Override
	public void MajBatimentCount(Joueur_Generique j, int hutte, int tour, int temple) {
		// TODO Auto-generated method stub
		if(ihm.getM().EstLeMemeJoueur(j,ihm.getM().getJ1()))
		{
			ihm.getInfoJ1()[1].setText(Integer.toString(hutte)+" / "+Integer.toString(ihm.getM().nb_max_Huttes));
			ihm.getInfoJ1()[3].setText(Integer.toString(tour)+" / "+Integer.toString(ihm.getM().nb_max_Tours));
			ihm.getInfoJ1()[5].setText(Integer.toString(temple)+" / "+Integer.toString(ihm.getM().nb_max_Temples));
			
		}
		else if(ihm.getM().EstLeMemeJoueur(j,ihm.getM().getJ2()))
		{
			ihm.getInfoJ2()[1].setText(Integer.toString(hutte)+" / "+Integer.toString(ihm.getM().nb_max_Huttes));
			ihm.getInfoJ2()[3].setText(Integer.toString(tour)+" / "+Integer.toString(ihm.getM().nb_max_Tours));
			ihm.getInfoJ2()[5].setText(Integer.toString(temple)+" / "+Integer.toString(ihm.getM().nb_max_Temples));
			
		}
	}
	
	
	
}
