package IHM;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.Dimension;

import Ecouteur.Ecouteur_Boutons;
import Moteur.Moteur;


public class IHM {
	
	private static final int WIDTH = 1200;
	private static final int HEIGHT = 600;
	
	public void run(Moteur moteur,JFrame fenetre) {
        
		// Creation d'une fenetre
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        java.awt.Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int height = (int)dimension.getHeight();
        int width  = (int)dimension.getWidth();
        
        JPanel ecran = new JPanel();
        //ecran.setLayout(new GridLayout(3,1));
        ecran.setLayout(new GridBagLayout());
        
        
        
        
        JPanel boutons = new JPanel();
        boutons.setLayout(new GridLayout(2,1));
        
        JPanel frise = new JPanel();
        frise.setLayout(new GridLayout(1,4));
        
        JPanel bas =new JPanel();
        bas.setLayout(new GridLayout(1,2));
        
        JPanel joueurs = new JPanel();
        joueurs.setLayout(new GridBagLayout());
        
        JPanel action = new JPanel();
        action.setLayout(new GridBagLayout());
        
        JPanel annuler_refaire = new JPanel();
        annuler_refaire.setLayout(new GridLayout(2,1));
        
        
        //frise
        JLabel p = new JLabel("Piocher");
        //p.setFont(new Font("Sherif", Font.PLAIN,32));
        //p.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel t = new JLabel("tuile");
        JLabel c = new JLabel("Construire");
        JLabel f = new JLabel("Fin de tour");
        
        
        
        
        //création du bouton annuler
        JButton Annuler = new JButton("Annuler");
        Ecouteur_Boutons annuler = new Ecouteur_Boutons("Annuler",moteur);
        Annuler.addActionListener(annuler);
        
        //création du bouton refaire
        JButton Refaire = new JButton("Refaire");
        Ecouteur_Boutons refaire = new Ecouteur_Boutons("Refaire",moteur);
        Refaire.addActionListener(refaire);
        
        //création du bouton pioche
        JButton Pioche = new JButton("Piocher");
        Ecouteur_Boutons pioche = new Ecouteur_Boutons("Piocher",moteur);
        Pioche.addActionListener(pioche);
        
        //création du bouton fin de tour
        JButton FDT = new JButton("Fin de tour");
        Ecouteur_Boutons fdt = new Ecouteur_Boutons("Fin_de_tour",moteur);
        FDT.addActionListener(fdt);        
        
        
        
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
        panelJ1.setLayout(new GridLayout(1,4));
        
        
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
        panelJ2.setLayout(new GridLayout(1,4));
        

        Canvas canvas = new Canvas();

        canvas.setSize(width, height-300);

        try {
            Display.setParent(canvas);
        } catch (Exception e) {
        }
        
        GridBagConstraints gbc = new GridBagConstraints();
        int largeur = 9;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth=largeur;
        gbc.gridheight = 9;
        gbc.fill = GridBagConstraints.BOTH;
        ecran.add(canvas,gbc);
        canvas.getSize();
        gbc.fill = GridBagConstraints.BOTH;

        
        //création de la frise
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth =largeur;
        
        gbc.gridheight = 1;
        gbc.gridx = 0;
        gbc.gridy = 9;
        frise.add(p,BorderLayout.CENTER);
        frise.add(t,BorderLayout.CENTER);
        frise.add(c,BorderLayout.CENTER);
        frise.add(f,BorderLayout.CENTER);
        ecran.add(frise,gbc);
        
        GridBagConstraints gbc3 = new GridBagConstraints();
        gbc3.fill = GridBagConstraints.BOTH;
        gbc3.gridwidth =1;
        gbc3.gridheight = 2;
        gbc3.weightx = gbc3.weighty = 1.0;
        gbc3.gridx = 0;
        gbc3.gridy = 10;
        joueurs.add(panelJ1,gbc3);
        gbc3.gridx = 0;
        gbc3.gridy = 12;
        joueurs.add(panelJ2,gbc3);
        
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridx = 1;
        gbc.gridy = 10;
        ecran.add(joueurs, gbc);
        
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.weightx = gbc2.weighty = 1.0;
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        gbc2.fill = GridBagConstraints.BOTH;
        gbc2.gridwidth = 2;
        gbc2.gridheight = 4;
        action.add(Pioche,gbc2);
        
        gbc2.gridx = 2;
        gbc2.gridy = 0;
        gbc2.fill = GridBagConstraints.BOTH;
        gbc2.gridwidth = 2;
        gbc2.gridheight = 2;
        action.add(Annuler,gbc2);

        gbc2.gridx = 2;
        gbc2.gridy = 2;
        gbc2.fill = GridBagConstraints.BOTH;
        gbc2.gridwidth = 2;
        gbc2.gridheight = 2;
        action.add(Refaire,gbc2);
        
        gbc2.gridx = 4;
        gbc2.gridy = 0;
        gbc2.fill = GridBagConstraints.BOTH;
        gbc2.gridwidth = gbc2.gridheight = 4;       
        action.add(FDT,gbc2);
        

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridx = 2;
        gbc.weightx = gbc.weighty = 1.0;
        gbc.gridy = 10;
        ecran.add(action, gbc);
        
        fenetre.add(ecran);
        
        
        panelJ1.add(Joueur1);
        panelJ1.add(templeJ1);
        panelJ1.add(tourJ1);
        panelJ1.add(hutteJ1);
        
        
        
        panelJ2.add(Joueur2);
        panelJ2.add(templeJ2);
        panelJ2.add(tourJ2);
        panelJ2.add(hutteJ2);


        
        
        if(moteur.get_Jcourant().equals(moteur.getJ1()))
	        switch (moteur.get_etat_jeu())
			{
				case DEBUT_DE_TOUR:/*
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
					break;*/
				default:
					break;
	        	
	        }
        
        
        //fenetre.pack();
        fenetre.setSize(width,height);
        fenetre.setVisible(true);
        
		
        
  

    }
}
