package IHM;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;

import Joueur.BatimentCountListener;
import Joueur.Joueur_Generique;
import Moteur.Moteur;
import Moteur.Phase.Phase_Jeu;
import Moteur.PhaseListener;

public class Avancement implements PhaseListener, BatimentCountListener {
	IHM ihm;
	private JFrame fenetreJeu, menuPrincipale;
	
	public Avancement(IHM ihm){
		this.ihm=ihm;
	}
	
	public Avancement(IHM ihm, JFrame fenetreJeu, JFrame menuPrincipale)
	{
		
	}
	
	//actualise l'affichage
	public void maj(Phase_Jeu NouveauEtat){
		
		
		ihm.getJoueur1().setBackground(Color.WHITE);
		ihm.getPanelJ1().setEnabled(false);
		ihm.getJoueur2().setBackground(Color.WHITE);
		ihm.getPanelJ2().setEnabled(false);
		if(ihm.getM().get_NbJoueur() >= 3)
		{
			ihm.getJoueur3().setBackground(Color.WHITE);
			ihm.getPanelJ3().setEnabled(false);
		}
		if(ihm.getM().get_NbJoueur() == 4)
		{
			ihm.getJoueur4().setBackground(Color.WHITE);
			ihm.getPanelJ4().setEnabled(false);
		}
		if(ihm.getM().get_num_Jcourant()==1)
		{
			ihm.getPanelJ1().setEnabled(true);
			ihm.getJoueur1().setBackground(ihm.getM().get_Jcourant().getCouleur().getcolor());
		}
		else if(ihm.getM().get_num_Jcourant()==2)
		{
			ihm.getPanelJ2().setEnabled(true);
			ihm.getJoueur2().setBackground(ihm.getM().get_Jcourant().getCouleur().getcolor());
		}
		else if(ihm.getM().get_num_Jcourant()==3)
		{
			ihm.getPanelJ3().setEnabled(true);
			ihm.getJoueur3().setBackground(ihm.getM().get_Jcourant().getCouleur().getcolor());
		}
		else if(ihm.getM().get_num_Jcourant()==4)
		{
			ihm.getPanelJ4().setEnabled(true);
			ihm.getJoueur4().setBackground(ihm.getM().get_Jcourant().getCouleur().getcolor());
		}
		switch (NouveauEtat)
		{
			case DEBUT_DE_TOUR:
				
				
				//ihm.getAnnuler().setEnabled(false);
				ihm.getAnnuler().Activer(false);
				ihm.getRefaire().Activer(!ihm.getM().PileRefaireVide());
				
				ihm.getPioche().setEnabled(true);
				//Pioche
				ihm.getPioche_Tuile().cleanAngle();
				ihm.getToute_la_Pioche().setVisible(false);
				ihm.getBouton_Pioche().setVisible(true);
				ihm.getBouton_Pioche().Activer(true);
				
				ihm.getFDT().Activer(false);
				
				ihm.getTemple().Activer(false);
				ihm.getTour().Activer(false);
				ihm.getHutte().Activer(false);
				
				ihm.getPiocher().setEtat(3);
				ihm.getPoser().setEtat(2);
				ihm.getConstruire().setEtat(2);
				ihm.getFinir().setEtat(2);
				break;
			
			case POSER_TUILE:

				//ihm.getAnnuler().setEnabled(false);
				ihm.getAnnuler().Activer(false);
				ihm.getRefaire().Activer(!ihm.getM().PileRefaireVide());
				ihm.getPioche().setEnabled(false);
				//Pioche
				ihm.getPioche_Tuile().setTuile();
				ihm.getToute_la_Pioche().setVisible(true);
				ihm.getBouton_Pioche().setVisible(false);
				activerPioche(true);
				ihm.getTaille_Pioche().setText("X"+Integer.toString(ihm.getM().get_nbTuiles()+1));
				
				ihm.getFDT().Activer(false);
				
				ihm.getTemple().Activer(false);
				ihm.getTour().Activer(false);
				ihm.getHutte().Activer(false);
				
				ihm.getPiocher().setEtat(1);
				ihm.getPoser().setEtat(3);
				ihm.getConstruire().setEtat(2);
				ihm.getFinir().setEtat(2);
				break;
				
			case CONSTRUIRE_BATIMENT:
				
				
				//ihm.getAnnuler().setEnabled(true);
				ihm.getAnnuler().Activer(true);
				ihm.getRefaire().Activer(!ihm.getM().PileRefaireVide());
				ihm.getPioche().setEnabled(false);
				activerPioche(false);
				ihm.getTaille_Pioche().setText("X"+Integer.toString(ihm.getM().get_nbTuiles()));
				
				
				ihm.getFDT().Activer(false);
				if(ihm.getM().get_num_Jcourant()==1){
					ihm.getTemple().Activer(!(ihm.getM().getJ1().getTemple() <= 0));
					ihm.getTour().Activer(!(ihm.getM().getJ1().getTour() <= 0));
					ihm.getHutte().Activer(!(ihm.getM().getJ1().getHutte() <= 0));

				}
				else if (ihm.getM().get_num_Jcourant()==2){
					ihm.getTemple().Activer(!(ihm.getM().getJ2().getTemple() <= 0));
					ihm.getTour().Activer(!(ihm.getM().getJ2().getTour() <= 0));
					ihm.getHutte().Activer(!(ihm.getM().getJ2().getHutte() <= 0));
				}
				else if(ihm.getM().get_num_Jcourant()==3)
				{
					ihm.getTemple().Activer(!(ihm.getM().getJ3().getTemple() <= 0));
					ihm.getTour().Activer(!(ihm.getM().getJ3().getTour() <= 0));
					ihm.getHutte().Activer(!(ihm.getM().getJ3().getHutte() <= 0));
					
				}else if (ihm.getM().get_num_Jcourant()==4)
				{
					ihm.getTemple().Activer(!(ihm.getM().getJ4().getTemple() <= 0));
					ihm.getTour().Activer(!(ihm.getM().getJ4().getTour() <= 0));
					ihm.getHutte().Activer(!(ihm.getM().getJ4().getHutte() <= 0));
					
				}
				ihm.getPiocher().setEtat(1);
				ihm.getPoser().setEtat(1);
				ihm.getConstruire().setEtat(3);
				ihm.getFinir().setEtat(2);
				
				break;
				
				
			case FIN_DE_TOUR:
				ihm.getAnnuler().Activer(true);
				ihm.getRefaire().Activer(false);
				ihm.getPioche().setEnabled(false);
				activerPioche(false);
				ihm.getTaille_Pioche().setText("X"+Integer.toString(ihm.getM().get_nbTuiles()));
				
				ihm.getFDT().Activer(true);
				
				ihm.getTemple().Activer(false);
				ihm.getTour().Activer(false);
				ihm.getHutte().Activer(false);
				
				ihm.getPiocher().setEtat(1);
				ihm.getPoser().setEtat(1);
				ihm.getConstruire().setEtat(1);
				ihm.getFinir().setEtat(3);
				colorStat();
				break;
			case FIN_DE_PARTIE:
				ihm.getAnnuler().Activer(false);
				ihm.getRefaire().Activer(false);
				ihm.getPioche().setEnabled(false);
				activerPioche(false);
				ihm.getFDT().Activer(false);
				
				ihm.getTemple().Activer(false);
				ihm.getTour().Activer(false);
				ihm.getHutte().Activer(false);
				
				ihm.getPiocher().setEtat(2);
				ihm.getPoser().setEtat(2);
				ihm.getConstruire().setEtat(2);
				ihm.getFinir().setEtat(2);
				
				//Lancer la fream avec la fin de partie
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
		if(ihm.getM().EstLeMemeJoueur(j,ihm.getM().getJ1()))
		{

			ihm.getM();
			ihm.getInfoJ1()[1].setText(Integer.toString(hutte)+" / "+Integer.toString(Moteur.nb_max_Huttes));
			ihm.getM();
			ihm.getInfoJ1()[3].setText(Integer.toString(tour)+" / "+Integer.toString(Moteur.nb_max_Tours));
			ihm.getM();
			ihm.getInfoJ1()[5].setText(Integer.toString(temple)+" / "+Integer.toString(Moteur.nb_max_Temples));
			
		}
		else if(ihm.getM().EstLeMemeJoueur(j,ihm.getM().getJ2()))
		{
			ihm.getM();
			ihm.getInfoJ2()[1].setText(Integer.toString(hutte)+" / "+Integer.toString(Moteur.nb_max_Huttes));
			ihm.getM();
			ihm.getInfoJ2()[3].setText(Integer.toString(tour)+" / "+Integer.toString(Moteur.nb_max_Tours));
			ihm.getM();
			ihm.getInfoJ2()[5].setText(Integer.toString(temple)+" / "+Integer.toString(Moteur.nb_max_Temples));
			
		}else if(ihm.getM().EstLeMemeJoueur(j,ihm.getM().getJ3()))
		{
			ihm.getM();
			ihm.getInfoJ3()[1].setText(Integer.toString(hutte)+" / "+Integer.toString(Moteur.nb_max_Huttes));
			ihm.getM();
			ihm.getInfoJ3()[3].setText(Integer.toString(tour)+" / "+Integer.toString(Moteur.nb_max_Tours));
			ihm.getM();
			ihm.getInfoJ3()[5].setText(Integer.toString(temple)+" / "+Integer.toString(Moteur.nb_max_Temples));
			
		}else if(ihm.getM().EstLeMemeJoueur(j,ihm.getM().getJ4()))
		{
			ihm.getM();
			ihm.getInfoJ4()[1].setText(Integer.toString(hutte)+" / "+Integer.toString(Moteur.nb_max_Huttes));
			ihm.getM();
			ihm.getInfoJ4()[3].setText(Integer.toString(tour)+" / "+Integer.toString(Moteur.nb_max_Tours));
			ihm.getM();
			ihm.getInfoJ4()[5].setText(Integer.toString(temple)+" / "+Integer.toString(Moteur.nb_max_Temples));
		}
	}
	
	private void activerPioche(boolean activer)
	{
		ihm.getBouton_Pioche().Activer(false);
		ihm.getToute_la_Pioche().setEnabled(activer);
		ihm.getRotation_Horaire().Activer(activer);
		ihm.getRotation_Anti_Horaire().Activer(activer);
	}
	
	private void colorStat()
	{
		if(ihm.getM().get_num_Jcourant()==1){
			colorElement(!(ihm.getM().getJ1().getTemple() <= 0),ihm.getInfoJ1()[5]);
			colorElement(!(ihm.getM().getJ1().getTour() <= 0),ihm.getInfoJ1()[3]);
			colorElement(!(ihm.getM().getJ1().getHutte() <= 0),ihm.getInfoJ1()[1]);

		}
		else if (ihm.getM().get_num_Jcourant()==2){
			colorElement(!(ihm.getM().getJ2().getTemple() <= 0),ihm.getInfoJ2()[5]);
			colorElement(!(ihm.getM().getJ2().getTour() <= 0),ihm.getInfoJ2()[3]);
			colorElement(!(ihm.getM().getJ2().getHutte() <= 0),ihm.getInfoJ2()[1]);
		}
		else if(ihm.getM().get_num_Jcourant()==3)
		{
			colorElement(!(ihm.getM().getJ3().getTemple() <= 0),ihm.getInfoJ3()[5]);
			colorElement(!(ihm.getM().getJ3().getTour() <= 0),ihm.getInfoJ3()[3]);
			colorElement(!(ihm.getM().getJ3().getHutte() <= 0),ihm.getInfoJ3()[1]);
			
		}else if (ihm.getM().get_num_Jcourant()==4)
		{
			colorElement(!(ihm.getM().getJ4().getTemple() <= 0),ihm.getInfoJ4()[5]);
			colorElement(!(ihm.getM().getJ4().getTour() <= 0),ihm.getInfoJ4()[3]);
			colorElement(!(ihm.getM().getJ4().getHutte() <= 0),ihm.getInfoJ4()[1]);
			
		}
		
		
	}
	private void colorElement(boolean bool,JLabel label)
	{
		if(bool)
			label.setForeground(Color.GREEN);
		else
			label.setForeground(Color.RED);
	}
	
}
