package IHM;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Ecouteur.Ecouteur_Boutons;
import Moteur.Moteur;


public class IHM {
	public void run(Moteur moteur,JFrame fenetre) {
        
		// Creation d'une fenetre
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel fenetre_de_jeu = new JPanel();
        fenetre_de_jeu.setLayout(new GridLayout(2,1));
        
        JPanel terrain = new JPanel();
        
        JPanel boutons = new JPanel();
        boutons.setLayout(new GridLayout(2,1));
        
        JPanel frise = new JPanel();
        frise.setLayout(new GridLayout(1,4));
        
        JPanel bas =new JPanel();
        bas.setLayout(new GridLayout(2,1));
        
        JPanel joueurs = new JPanel();
        joueurs.setLayout(new GridLayout(1,2));
        
        JPanel action = new JPanel();
        action.setLayout(new GridLayout(1,3));
        
        JPanel annuler_refaire = new JPanel();
        annuler_refaire.setLayout(new GridLayout(2,1));
        
        
        
        fenetre_de_jeu.add(terrain);
        fenetre_de_jeu.add(boutons);
        
        boutons.add(frise);
        boutons.add(bas);
        
        bas.add(joueurs);
        bas.add(action);
        
        //création du bouton annuler
        JButton Annuler = new JButton();
        Ecouteur_Boutons annuler = new Ecouteur_Boutons("Annuler",moteur);
        Annuler.addActionListener(annuler);
        
        //création du bouton refaire
        JButton Refaire = new JButton();
        Ecouteur_Boutons refaire = new Ecouteur_Boutons("Refaire",moteur);
        Refaire.addActionListener(refaire);
        
        //création du bouton pioche
        JButton Pioche = new JButton();
        Ecouteur_Boutons pioche = new Ecouteur_Boutons("Piocher",moteur);
        Pioche.addActionListener(pioche);
        
        //création du bouton fin de tour
        JButton FDT = new JButton();
        Ecouteur_Boutons fdt = new Ecouteur_Boutons("Fin_de_tour",moteur);
        FDT.addActionListener(fdt);        
        
        annuler_refaire.add(Annuler);
        annuler_refaire.add(Refaire);
        
        //création de la ligne de J1
        JLabel Joueur1 = new JLabel("Joueur1");
        
        JButton templeJ1 = new JButton();
        Ecouteur_Boutons tp1 = new Ecouteur_Boutons("Temple j1",moteur);
        ImageIcon image_temple = new ImageIcon("Assets/Texture/Button_Temple.png");
        templeJ1.addActionListener(tp1);
        templeJ1.setIcon(image_temple);
        
        JButton tourJ1 = new JButton();
        Ecouteur_Boutons tr1 = new Ecouteur_Boutons("Tour j1",moteur);
        ImageIcon image_tour = new ImageIcon("Assets/Texture/Button_tower.png");
        tourJ1.addActionListener(tr1);
        tourJ1.setIcon(image_tour);
        
        JButton hutteJ1 = new JButton();
        Ecouteur_Boutons ht1 = new Ecouteur_Boutons("Hutte j1",moteur);
        ImageIcon image_hutte = new ImageIcon("Assets/Texture/Button_Hut.png");
        hutteJ1.addActionListener(ht1);
        hutteJ1.setIcon(image_hutte);
        
        JPanel panelJ1 = new JPanel();
        panelJ1.add(Joueur1);
        panelJ1.add(templeJ1);
        panelJ1.add(tourJ1);
        panelJ1.add(hutteJ1);
        
        //création de la ligne de J2
        JLabel Joueur2 = new JLabel("Joueur2");
        
        
        JButton templeJ2 = new JButton();
        Ecouteur_Boutons tp2 = new Ecouteur_Boutons("Temple j2",moteur);
        templeJ2.addActionListener(tp2);
        templeJ2.setIcon(image_temple);
        
        JButton tourJ2 = new JButton();
        Ecouteur_Boutons tr2 = new Ecouteur_Boutons("Tour j2",moteur);
        tourJ2.addActionListener(tr2);
        tourJ2.setIcon(image_tour);
        
        JButton hutteJ2 = new JButton();
        Ecouteur_Boutons ht2 = new Ecouteur_Boutons("Hutte jhutteJ12",moteur);
        hutteJ2.addActionListener(ht2);
        hutteJ2.setIcon(image_hutte);
        
        JPanel panelJ2 = new JPanel();
        panelJ2.add(Joueur2);
        panelJ2.add(templeJ2);
        panelJ2.add(tourJ2);
        panelJ2.add(hutteJ2);
        
        
        joueurs.add(panelJ1);
        joueurs.add(panelJ2);
                
        fenetre.add(fenetre_de_jeu, BorderLayout.SOUTH);
        
        if(moteur.get_Jcourant().equals(moteur.getJ1()))
	        switch (moteur.get_etat_jeu())
			{
				case DEBUT_DE_TOUR:
					Annuler.setEnabled(false);
					Refaire.setEnabled(false);
					Pioche.setEnabled(true);
					FDT.setEnabled(false);
					
					templeJ1.setEnabled(false);
					tourJ1.setEnabled(false);
					hutteJ1.setEnabled(false);
					
					templeJ2.setEnabled(false);
					tourJ2.setEnabled(false);
					hutteJ2.setEnabled(false);
					break;
				default:
					break;
	        	
	        }
        
        
        
        
        
        
        fenetre.pack();
        fenetre.setSize(500, 500);
        fenetre.setVisible(true);
        
		
        
  

    }
}
