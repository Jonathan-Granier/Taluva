package IHM;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Panel;
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
import Ecouteur.KeyboardListener;
import Moteur.Moteur;


public class IHM {
	
	private JFrame frame;
	private Moteur m;
	private int width,height;
	private JPanel ecran, boutons_Construction, frise, bas, joueurs, action, annuler_refaire;
	private JLabel  Piocher, Poser, Construire, Finir;
	private JButton Pioche;
	private JBoutonImage Annuler,Refaire,FDT,temple,tour,hutte;;
	
	private JPanelImage Interface;
	
	//Pour les joueurs
	private JLabel[] InfoJ1,InfoJ2,InfoJ3,InfoJ4;
	private JPanel[] Image_Stat_J1,Image_Stat_J2,Image_Stat_J3,Image_Stat_J4;
	private JPanelImage panelJ1,panelJ2,panelJ3,panelJ4;
	private JLabel Joueur1,Joueur2,Joueur3,Joueur4;
	
	//Pour la pioche
	private JBoutonImage Bouton_Pioche;
	private JBoutonImage Rotation_Horaire, Rotation_Anti_Horaire;
	private JPanel Pioche_Droite,Pioche_Gauche;
	private JPanelPioche Pioche_Tuile;
	private JPanelImage Toute_la_Pioche; 
	
	//TODO
	/*
	 * CONSTANTES pour l'emplacement des images et pour le positionnement des differents elements
	 */
	
	


	private static final String Fichier_Temple = "Assets/Texture/Button_Temple.png";
	private static final String Fichier_Tour = "Assets/Texture/Button_tower.png";
	private static final String Fichier_Hutte = "Assets/Texture/Button_Hut.png";
	
	//Background
	
	private static final String Fichier_Back_Ground = "Assets/IHM/Back_ground.png";
	
	//Joueur
	private static final String Fichier_Joueur_Fond_Activer = "Assets/IHM/Joueurs/Cadre_highlight.png";
	private static final String Fichier_Joueur_Fond_Desactiver = "Assets/IHM/Joueurs/Cadre_deactivated.png";
	
	//Pioche Turn Horaire
	private static final String Fichier_Pioche_Turn_Horaire_Activer = "Assets/IHM/Image-pioche/pioche-turnH.png";
	private static final String Fichier_Pioche_Turn_Horaire_Desactiver = "Assets/IHM/Image-pioche/pioche-turnH.png";
	private static final String Fichier_Pioche_Turn_Horaire_Cliquer = "Assets/IHM/Image-pioche/Pioche-turn H-highlight.png";
	private static final String Fichier_Pioche_Turn_Horaire_Passage = "Assets/IHM/Image-pioche/Pioche-turn H-highlight.png";
	
	//Pioche Turn Anti Horaire
	private static final String Fichier_Pioche_Turn_Anti_Horaire_Activer = "Assets/IHM/Image-pioche/pioche-turnAH.png";
	private static final String Fichier_Pioche_Turn_Anti_Horaire_Desactiver = "Assets/IHM/Image-pioche/pioche-turnAH.png";
	private static final String Fichier_Pioche_Turn_Anti_Horaire_Cliquer = "Assets/IHM/Image-pioche/pioche-turnAH-highlight.png";
	private static final String Fichier_Pioche_Turn_Anti_Horaire_Passage = "Assets/IHM/Image-pioche/pioche-turnAH-highlight.png";

	
	
	//Pioche Fond
	private static final String Fichier_Pioche_Fond_Activer = "Assets/IHM/Image-pioche/pioche-pioché.png";
	private static final String Fichier_Pioche_Fond_Desactiver = "Assets/IHM/Image-pioche/Pioche-inactive.png";
	
	//Pioche Fond_Bouton
	private static final String Fichier_Pioche_Fond_Bouton_Activer = "Assets/IHM/Image-pioche/Pioche-pioché-carre-centre.png";
	private static final String Fichier_Pioche_Fond_Bouton_Desactiver = "Assets/IHM/Image-pioche/Pioche-inactive-carre-centre.png";
	
	
	//Pioche Bouton
	private static final String Fichier_Pioche_Bouton_Activer = "Assets/IHM/Image-pioche/Pioche-pre-pioche.png";
	private static final String Fichier_Pioche_Bouton_Desactiver = "Assets/IHM/Image-pioche/Pioche-pre-pioche.png";
	private static final String Fichier_Pioche_Bouton_Cliquer = "Assets/IHM/Image-pioche/Pioche_highlight.png";
	private static final String Fichier_Pioche_Bouton_Passage = "Assets/IHM/Image-pioche/Pioche_highlight.png";
	
	//Bouton_Hutte
	private static final String Fichier_Hutte_Bouton_Activer = "Assets/IHM/Constructions/Hutte_normal.png";
	private static final String Fichier_Hutte_Bouton_Desactiver = "Assets/IHM/Constructions/Hutte_deactivated.png";
	private static final String Fichier_Hutte_Bouton_Cliquer = "Assets/IHM/Constructions/Hutte_pushed.png";
	private static final String Fichier_Hutte_Bouton_Passage = "Assets/IHM/Constructions/Hutte_highlight.png";
	
	//Bouton Tour
	private static final String Fichier_Tour_Bouton_Activer = "Assets/IHM/Constructions/Tour_normal.png";
	private static final String Fichier_Tour_Bouton_Desactiver = "Assets/IHM/Constructions/Tour_deactivated.png";
	private static final String Fichier_Tour_Bouton_Cliquer = "Assets/IHM/Constructions/Tour_pushed.png";
	private static final String Fichier_Tour_Bouton_Passage = "Assets/IHM/Constructions/Tour_highlight.png";
	
	//Bouton Temple
	private static final String Fichier_Temple_Bouton_Activer = "Assets/IHM/Constructions/Temple_normal.png";
	private static final String Fichier_Temple_Bouton_Desactiver = "Assets/IHM/Constructions/Temple_deactivated.png";
	private static final String Fichier_Temple_Bouton_Cliquer = "Assets/IHM/Constructions/Temple_pushed.png";
	private static final String Fichier_Temple_Bouton_Passage = "Assets/IHM/Constructions/Temple_highlight.png";
	
	//Bouton_Fin de tour
	private static final String Fichier_FDT_Bouton_Activer = "Assets/IHM/Fin_de_tour/FDT_normal.png";
	private static final String Fichier_FDT_Bouton_Desactiver = "Assets/IHM/Fin_de_tour/FDT_deactivated.png";
	private static final String Fichier_FDT_Bouton_Cliquer = "Assets/IHM/Fin_de_tour/FDT_clicked.png";
	private static final String Fichier_FDT_Bouton_Passage = "Assets/IHM/Fin_de_tour/FDT_highlight.png";
	
	//Annuler
	private static final String Fichier_Annuler_Activer = "Assets/IHM/undo-redo/Undo-normal.png";
	private static final String Fichier_Annuler_Desactiver = "Assets/IHM/undo-redo/Undo-deactivated.png";
	private static final String Fichier_Annuler_Cliquer = "Assets/IHM/undo-redo/Undo-clicked.png";
	private static final String Fichier_Annuler_Passage = "Assets/IHM/undo-redo/Undo-highlight.png";

	//Refaire
	private static final String Fichier_Refaire_Activer = "Assets/IHM/undo-redo/Redo-normal.png";
	private static final String Fichier_Refaire_Desactiver = "Assets/IHM/undo-redo/Redo-deactivated.png";
	private static final String Fichier_Refaire_Cliquer = "Assets/IHM/undo-redo/Redo-clicked.png";
	private static final String Fichier_Refaire_Passage = "Assets/IHM/undo-redo/Redo-highlight.png";
		
	
	
	
	private static int largeur_total = 30;
    private static int hauteur_caneva = 9;
    private static int hauteur_total = 12;
    private static int taille_joueur = 1;
    private static int taille_annuler_refaire = 1;
    private static int taille_pioche = 4;
    private static int taille_construction = 4;
    private static int taille_fin_de_tour = 3;
    private static int taille_frise = largeur_total - taille_joueur - taille_annuler_refaire;
	private Canvas canvas;
	 
	
	public IHM(Moteur moteur, JFrame fenetre){
		m=moteur;
		frame=fenetre;
		java.awt.Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		height = (int)dimension.getHeight();
        width  = (int)dimension.getWidth();
        ecran = new JPanel();
        boutons_Construction = new JPanel();
        frise = new JPanel();
        bas =new JPanel();
        joueurs = new JPanel();
        action = new JPanel();
        annuler_refaire = new JPanel();
       
        canvas = new Canvas();
        
        Interface = new JPanelImage(Fichier_Back_Ground,Fichier_Back_Ground);
        
        //Joueurs
        InfoJ1 = new JLabel[6];
        InfoJ2 = new JLabel[6];
        InfoJ3 = new JLabel[6];
        InfoJ4 = new JLabel[6];
       
        Image_Stat_J1 = new JPanel[3];
        Image_Stat_J2 = new JPanel[3];
        Image_Stat_J3 = new JPanel[3];
        Image_Stat_J4 = new JPanel[3];
        
        Joueur1 = new JLabel("Joueur 1");
        Joueur2 = new JLabel("Joueur 2");
        Joueur3 = new JLabel("Joueur 3");
        Joueur4 = new JLabel("Joueur 4");
        panelJ1 = new JPanelImage(Fichier_Joueur_Fond_Activer,Fichier_Joueur_Fond_Desactiver);
        panelJ2 = new JPanelImage(Fichier_Joueur_Fond_Activer,Fichier_Joueur_Fond_Desactiver);
        panelJ3 = new JPanelImage(Fichier_Joueur_Fond_Activer,Fichier_Joueur_Fond_Desactiver);
        panelJ4 = new JPanelImage(Fichier_Joueur_Fond_Activer,Fichier_Joueur_Fond_Desactiver);
        
	}
	
	
	public void run() {

		// Creation d'une fenetre
        ecran.setLayout(new GridBagLayout());
        Interface.setLayout(new GridBagLayout());
        
        // On ajoute tout les elements au JPanel Ecran
        AjoutCaneva();
        AjoutJoueurs();
        AjoutFrise();
        AjoutBoutonAction();
        AjoutAnnulerRefaire();
        AjoutInterface();
        Interface.setEnabled(true);  
        frame.add(ecran);
        
        //fenetre.pack();
        frame.setSize(width,height);
        frame.setVisible(true);


    }
	
	//Ajoute le Caneva au Panel Ecran
	private void AjoutCaneva()
	{
		
		canvas.setSize(width, height-height/3);
        
        try {
            Display.setParent(canvas);
        } catch (Exception e) {
        }
        
        GridBagConstraints gbc = new GridBagConstraints();
        
        // le caneva fait exactement 2/3 de la fenetre
        

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = largeur_total;
        gbc.gridheight = hauteur_caneva;
        gbc.fill = GridBagConstraints.BOTH;
        ecran.add(canvas,gbc);
        //canvas.getSize();
	}
	
	private void AjoutInterface()
	{
		
		GridBagConstraints gbc = new GridBagConstraints();
        
        // le caneva fait exactement 2/3 de la fenetre
        
		
        gbc.gridx = 0;
        gbc.gridy = hauteur_caneva;
        gbc.weightx = 1.0;
        gbc.weighty = 0.33;
        gbc.gridwidth = largeur_total;
        gbc.gridheight = hauteur_total - hauteur_caneva;
        gbc.fill = GridBagConstraints.BOTH;
        ecran.add(Interface,gbc);
	}
	
	//Ajoute la frise au Panel Ecran
	private void AjoutFrise()
	{
		
		//Creation de Panel Temp
		JPanel[] PanelFrise = new JPanel[3];
		for(int i=0;i<3; i++)
		{
			PanelFrise[i] = new JPanel();
			PanelFrise[i].setLayout(new GridLayout(1,1));
		}
		
		PanelFrise[0].setLayout(new GridLayout(1,2));
		
		
		frise.setLayout(new GridBagLayout());
		Piocher = initFriseElement("Piocher");
		Poser =  initFriseElement("Poser");
        Construire =  initFriseElement("Construire");
        Finir =  initFriseElement("Finir");
        
        PanelFrise[0].add(Piocher,BorderLayout.CENTER);
        PanelFrise[0].add(Poser,BorderLayout.CENTER);
        PanelFrise[1].add(Construire,BorderLayout.CENTER);
        PanelFrise[2].add(Finir,BorderLayout.CENTER);
        
        
        
        //création de la frise
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.25;
        gbc.weighty = 1.0;
        gbc.gridwidth = taille_pioche;
        gbc.gridheight = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        frise.add(PanelFrise[0], gbc);
        gbc.gridwidth = taille_construction;
        gbc.gridx = taille_pioche;
        gbc.weightx = 0.135;
        frise.add( PanelFrise[1], gbc);
        gbc.gridwidth = taille_fin_de_tour;
        gbc.gridx = taille_pioche + taille_construction;
        gbc.weightx = 0.265;
        frise.add(PanelFrise[2] ,gbc);
        
        
    /*    
        frise.add(Poser,BorderLayout.CENTER);
        frise.add(Construire,BorderLayout.CENTER);
        frise.add(Finir,BorderLayout.CENTER);
  */      
        
        gbc.fill = GridBagConstraints.BOTH;
     //   gbc.gridwidth = taille_frise;
        gbc.weightx = 1.0;
        gbc.weighty = 0.1;
        
        gbc.gridwidth = taille_frise;
        gbc.gridheight = 1;
        gbc.gridx = taille_joueur;
        gbc.gridy = 0;
    
        Interface.add(frise,gbc);
        
        
	}
	
	//Ajoute la pioche au JPanel action
	private void AjoutPioche()
	{
		//TODO
		int i;
		Pioche_Tuile = new JPanelPioche(Fichier_Pioche_Fond_Bouton_Activer,Fichier_Pioche_Fond_Bouton_Desactiver);
		Pioche_Tuile.setOpaque(false);
		Pioche_Tuile.setBackground(Color.RED);
		Bouton_Pioche = FaireJButtonPioche("Piocher",Fichier_Pioche_Bouton_Activer,Fichier_Pioche_Bouton_Desactiver,
				Fichier_Pioche_Bouton_Cliquer,Fichier_Pioche_Bouton_Passage);
		Rotation_Horaire = FaireJButtonPioche("Rotation_Horaire",Fichier_Pioche_Turn_Horaire_Activer,Fichier_Pioche_Turn_Horaire_Desactiver,
				Fichier_Pioche_Turn_Horaire_Cliquer,Fichier_Pioche_Turn_Horaire_Passage);
		Rotation_Anti_Horaire = FaireJButtonPioche("Rotation_Anti_Horaire",Fichier_Pioche_Turn_Anti_Horaire_Activer,Fichier_Pioche_Turn_Anti_Horaire_Desactiver,
				Fichier_Pioche_Turn_Anti_Horaire_Cliquer,Fichier_Pioche_Turn_Anti_Horaire_Passage);
		Pioche_Droite = new JPanel();
		Pioche_Gauche = new JPanel();
		
	   
	    Toute_la_Pioche = new JPanelImage(Fichier_Pioche_Fond_Activer,Fichier_Pioche_Fond_Desactiver); 
	    Toute_la_Pioche.setLayout(new GridBagLayout());
	   
	   
	    JPanel[] Vide = new JPanel[6];
	    for(i=0;i<6;i++)
	    {
	    	Vide[i] = new JPanel();
	    	Vide[i].setOpaque(false);
	    }
	    Pioche_Droite.setLayout(new GridLayout(4,1));
	   	Pioche_Droite.add(Rotation_Anti_Horaire);
	   	for(i=0;i<3;i++)
	    	 Pioche_Droite.add(Vide[i]);
	   	
	   	Pioche_Gauche.setLayout(new GridLayout(4,1));
	    Pioche_Gauche.add(Rotation_Horaire);
	   	for(i=3;i<6;i++)
	   		Pioche_Gauche.add(Vide[i]);
	    
	    Pioche_Droite.setOpaque(false);
	    Pioche_Gauche.setOpaque(false);
	    
        GridBagConstraints gbc = new GridBagConstraints();
	    
	    //Pioche_Gauche
	    gbc.weightx = 0.2;
        gbc.weighty = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        Toute_la_Pioche.add(Pioche_Gauche,gbc);
	    
	    //Pioche_Tuile
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = 4;
        gbc.gridheight = 1;
        Toute_la_Pioche.add(Pioche_Tuile,gbc);
       
        //Pioche_Droite
        gbc.weightx = 0.2;
        gbc.weighty = 1.0;
        gbc.gridx = 6;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        Toute_la_Pioche.add(Pioche_Droite,gbc);
        
     
        //Ajout à Action
        gbc.weightx = 0.4;
        gbc.weighty = 0.5;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = taille_pioche;
        gbc.gridheight = 1;
        Toute_la_Pioche.setVisible(false);
        action.add(Toute_la_Pioche,gbc);
      
        //Ajout à Action
        gbc.weightx = 0.4;
        gbc.weighty = 0.5;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = taille_pioche;
        gbc.gridheight = 1;
        Bouton_Pioche.setVisible(true);
        action.add(Bouton_Pioche,gbc);
        
		

	}
	
	//Ajoute les boutons action au Panel Ecran
	private void AjoutBoutonAction()
	{
		boutons_Construction.setLayout(new GridBagLayout());
        action.setLayout(new GridBagLayout());
		
		Pioche = FaireBouton("Piocher");
        //FDT = FaireBouton("Fin_de_tour");
        FDT = FaireJButtonImage("Fin_de_tour",Fichier_FDT_Bouton_Activer,Fichier_FDT_Bouton_Desactiver,
				Fichier_FDT_Bouton_Cliquer,Fichier_FDT_Bouton_Passage);
		hutte = FaireJButtonImage("Hutte",Fichier_Hutte_Bouton_Activer,Fichier_Hutte_Bouton_Desactiver,
				Fichier_Hutte_Bouton_Cliquer,Fichier_Hutte_Bouton_Passage);
        tour = FaireJButtonImage("Tour",Fichier_Tour_Bouton_Activer,Fichier_Tour_Bouton_Desactiver,
				Fichier_Tour_Bouton_Cliquer,Fichier_Tour_Bouton_Passage);
        temple = FaireJButtonImage("Temple",Fichier_Temple_Bouton_Activer,Fichier_Temple_Bouton_Desactiver,
				Fichier_Temple_Bouton_Cliquer,Fichier_Temple_Bouton_Passage);
        
        boutons_Construction.setLayout(new GridLayout(3,1));
        boutons_Construction.add(hutte);
        boutons_Construction.add(tour);
        boutons_Construction.add(temple);
        
        
        GridBagConstraints gbc = new GridBagConstraints();
        
        //Pioche
       
        gbc.weightx = 0.4;
        gbc.weighty = 0.5;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = taille_pioche;
        gbc.gridheight = 1;
       // action.add(Pioche,gbc);
        
        AjoutPioche();

        //Bouton construction 
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.2;
        gbc.weighty = 0.5;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridx = taille_construction;
        gbc.gridy = 0;
        action.add(boutons_Construction, gbc);
        
        
        
        
        //Fin de Tour
        gbc.gridx = 5;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = taille_fin_de_tour;
        gbc.gridheight = 1;       
        action.add(FDT,gbc);
        
        
        
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = taille_frise;
        gbc.gridheight = hauteur_total - hauteur_caneva - 1;
        gbc.gridx = taille_joueur;
        gbc.weightx = gbc.weighty = 1.0;
        gbc.gridy = 1;
        Interface.add(action, gbc);
        
        
        
        
	}
	
	//Ajoute les bouton Annuler et Refaire au Panel Ecran
    private void AjoutAnnulerRefaire()
    {
    	//TODO
    	annuler_refaire.setLayout(new GridLayout(2,1));
    	//Annuler = FaireBouton("Annuler");
    //	Refaire = FaireBouton("Refaire");
    	Annuler = FaireJButtonImage("Annuler",Fichier_Annuler_Activer,
        		Fichier_Annuler_Desactiver,Fichier_Annuler_Cliquer,Fichier_Annuler_Passage);
    	
    	Refaire = FaireJButtonImage("Refaire",Fichier_Refaire_Activer,
        		Fichier_Refaire_Desactiver,Fichier_Refaire_Cliquer,Fichier_Refaire_Passage);
    	
        annuler_refaire.add(Annuler);
        annuler_refaire.add(Refaire);
        
        // ajout à l'ecran
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        
        gbc.gridwidth = taille_annuler_refaire;
        gbc.gridheight = hauteur_total-hauteur_caneva;
        gbc.gridx = largeur_total-taille_annuler_refaire;
        gbc.weightx = 0.3;
        gbc.weighty = 0.5;
        gbc.gridy = 0;
        Interface.add(annuler_refaire, gbc);
        
    }
	
	
	//Ajoute tous les joueurs à l'ecran le panel principal
	private void AjoutJoueurs()
	{
		int i;
		joueurs.setLayout(new GridLayout(m.get_NbJoueur(),1));
	    panelJ1.setLayout(new GridLayout(1,4));
	    panelJ2.setLayout(new GridLayout(1,4));
	    panelJ3.setLayout(new GridLayout(1,4));
	    panelJ4.setLayout(new GridLayout(1,4));
		
	    
	    
		for(i=0;i<3;i++)
        {
        	Image_Stat_J1[i] = new JPanel();
        	Image_Stat_J2[i] = new JPanel();
        	Image_Stat_J3[i] = new JPanel();
        	Image_Stat_J4[i] = new JPanel();
        	Image_Stat_J1[i].setLayout(new GridLayout(1,2));
        	Image_Stat_J2[i].setLayout(new GridLayout(1,2));
        	Image_Stat_J3[i].setLayout(new GridLayout(1,2));
        	Image_Stat_J4[i].setLayout(new GridLayout(1,2));
        	Image_Stat_J1[i].setOpaque(false);
        	Image_Stat_J2[i].setOpaque(false);
        	Image_Stat_J3[i].setOpaque(false);
        	Image_Stat_J4[i].setOpaque(false);
        	
        }
        
        for(i=0;i<6;i++)
        {
        	InfoJ1[i]= new JLabel();
        	InfoJ2[i]= new JLabel();
        	InfoJ3[i]= new JLabel();
        	InfoJ4[i]= new JLabel();
        	InfoJ1[i].setOpaque(false);
        	InfoJ2[i].setOpaque(false);
        	InfoJ3[i].setOpaque(false);
        	InfoJ4[i].setOpaque(false);
        	
        }
        
        Joueur1.setHorizontalAlignment(SwingConstants.CENTER);
        Joueur1.setOpaque(false);
        Joueur2.setHorizontalAlignment(SwingConstants.CENTER);
        Joueur2.setOpaque(false);
        Joueur3.setHorizontalAlignment(SwingConstants.CENTER);
        Joueur3.setOpaque(false);
        Joueur4.setHorizontalAlignment(SwingConstants.CENTER);
        Joueur4.setOpaque(false);
        
        // Info du joueur 1 avec l'image des constructions et leur nombres en stock
        InfoJ1[0] = InitImage(Fichier_Hutte);
		InfoJ1[1] = InitStatConstruction(Integer.toString(m.nb_max_Huttes));
        InfoJ1[2] = InitImage(Fichier_Tour); 
		InfoJ1[3] = InitStatConstruction(Integer.toString(m.nb_max_Tours));
		InfoJ1[4] = InitImage(Fichier_Temple);
		InfoJ1[5] = InitStatConstruction(Integer.toString(m.nb_max_Temples));
		
		// Info du joueur 2 avec l'image des constructions et leur nombres en stock
        InfoJ2[0] = InitImage(Fichier_Hutte);
		InfoJ2[1] = InitStatConstruction(Integer.toString(m.nb_max_Huttes));
        InfoJ2[2] = InitImage(Fichier_Tour); 
		InfoJ2[3] = InitStatConstruction(Integer.toString(m.nb_max_Tours));
		InfoJ2[4] = InitImage(Fichier_Temple);
		InfoJ2[5] = InitStatConstruction(Integer.toString(m.nb_max_Temples));
		
		// Info du joueur 3 avec l'image des constructions et leur nombres en stock
	    InfoJ3[0] = InitImage(Fichier_Hutte);
		InfoJ3[1] = InitStatConstruction(Integer.toString(m.nb_max_Huttes));
	    InfoJ3[2] = InitImage(Fichier_Tour); 
		InfoJ3[3] = InitStatConstruction(Integer.toString(m.nb_max_Tours));
		InfoJ3[4] = InitImage(Fichier_Temple);
		InfoJ3[5] = InitStatConstruction(Integer.toString(m.nb_max_Temples));
		// Info du joueur 4 avec l'image des constructions et leur nombres en stock
		InfoJ4[0] = InitImage(Fichier_Hutte);
		InfoJ4[1] = InitStatConstruction(Integer.toString(m.nb_max_Huttes));
        InfoJ4[2] = InitImage(Fichier_Tour); 
		InfoJ4[3] = InitStatConstruction(Integer.toString(m.nb_max_Tours));
		InfoJ4[4] = InitImage(Fichier_Temple);
		InfoJ4[5] = InitStatConstruction(Integer.toString(m.nb_max_Temples));
    
		
		// Fusion Image et nombre en 1 JPanel chacun 
		
		for(i=0;i<3;i++)
		{
			Image_Stat_J1[i].add(InfoJ1[i*2]);
			Image_Stat_J1[i].add(InfoJ1[i*2+1]);
			Image_Stat_J2[i].add(InfoJ2[i*2]);
			Image_Stat_J2[i].add(InfoJ2[i*2+1]);
			Image_Stat_J3[i].add(InfoJ3[i*2]);
			Image_Stat_J3[i].add(InfoJ3[i*2+1]);
			Image_Stat_J4[i].add(InfoJ4[i*2]);
			Image_Stat_J4[i].add(InfoJ4[i*2+1]);
		}
		
		
		//Fusion du nom des joueurs et des Image_Stat en 1 JPanel
		panelJ1.add(Joueur1);
		panelJ2.add(Joueur2);
		panelJ3.add(Joueur3);
		panelJ4.add(Joueur4);
		for(i=0;i<3;i++)
		{
			panelJ1.add(Image_Stat_J1[i]);
			panelJ2.add(Image_Stat_J2[i]);
			panelJ3.add(Image_Stat_J3[i]);
			panelJ4.add(Image_Stat_J4[i]);
		}
		
		joueurs.add(panelJ1);
		joueurs.add(panelJ2);
		//TODO
		if(m.get_NbJoueur() >= 3)
			joueurs.add(panelJ3);
		if(m.get_NbJoueur() == 4)
			joueurs.add(panelJ4);
	
		//Ajout des joueurs à l'ecran
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = taille_joueur;
        gbc.gridheight = hauteur_total-hauteur_caneva;
        gbc.gridx = 0;
        gbc.weightx = 0.1;
        gbc.weighty = 1.0;
        gbc.gridy = 0;
        Interface.add(joueurs, gbc);
	}
	
	
	



	// Initialise un element de la frise
	private JLabel initFriseElement(String nom)
	{
		 JLabel label = new JLabel(nom);
		 label.setFont(new Font("Sherif", Font.PLAIN,32));
	     label.setHorizontalAlignment(SwingConstants.CENTER);
	     label.setOpaque(false);
		 return label;
	}
	
	// Creer un bouton a partir de son nom
	private JButton FaireBouton(String nom_bouton)
	{
		JButton bouton = new JButton(nom_bouton);
		bouton.setFocusable(false);
		Ecouteur_Boutons ecouteur_bouton = new Ecouteur_Boutons(nom_bouton,m);
        bouton.addActionListener(ecouteur_bouton);
        return bouton;
        
	}
	
	
	// Creer un bouton a partir de son nom et son image
	private JButton FaireBouton(String nom_bouton, String nom_image)
	{ 
		
		ImageIcon image = new ImageIcon(nom_image);
		JButton bouton = new JButton(image);
		//bouton.setMargin(new Insets(0, 0, 0, 0));
		//bouton.setBorder(null);
		bouton.setFocusable(false);
		Ecouteur_Boutons ecouteur_bouton = new Ecouteur_Boutons(nom_bouton,m);
        
        bouton.addActionListener(ecouteur_bouton);
       // bouton.setIcon(image);
        return bouton;  
	}
	
	private JBoutonImage FaireJButtonPioche(String nom_bouton, String nom_image_activer, 
			String nom_image_desactiver, String nom_image_cliquer, String nom_image_passage)
	{
		JBoutonImage bouton = new JBoutonImage(nom_image_activer,nom_image_desactiver,nom_image_cliquer, nom_image_passage);
		bouton.setFocusable(false);
		Ecouteur_Boutons ecouteur_bouton = new Ecouteur_Boutons(nom_bouton,m,Pioche_Tuile);
		bouton.setMargin(new Insets(0, 0, 0, 0));
		bouton.setBorder(null);
		bouton.setOpaque(false);
		bouton.setContentAreaFilled(false);
		bouton.addActionListener(ecouteur_bouton);
        return bouton;  
	}
	
	private JBoutonImage FaireJButtonImage(String nom_bouton, String nom_image_activer, 
			String nom_image_desactiver, String nom_image_cliquer, String nom_image_passage)
	{
		JBoutonImage bouton = new JBoutonImage(nom_image_activer,nom_image_desactiver,nom_image_cliquer, nom_image_passage);
		bouton.setFocusable(false);
		Ecouteur_Boutons ecouteur_bouton = new Ecouteur_Boutons(nom_bouton,m);
		bouton.setMargin(new Insets(0, 0, 0, 0));
		bouton.setBorder(null);
		bouton.setOpaque(false);
		bouton.setContentAreaFilled(false);
		bouton.addActionListener(ecouteur_bouton);
        return bouton;  
	}
	
	private JLabel InitImage(String nom_image)
	{
		JLabel label = new JLabel();
		ImageIcon image = new ImageIcon(nom_image);
		label.setIcon(image);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setOpaque(false);
		return label;
	}
	
	private JLabel InitStatConstruction(String nom)
	{
		 JLabel label = new JLabel(nom +" / "+ nom);
		 label.setFont(new Font("Sherif", Font.PLAIN,20));
	     label.setHorizontalAlignment(SwingConstants.CENTER);
	     label.setOpaque(false);
	     return label;
	}


	public JFrame getFrame() {
		return frame;
	}


	public Moteur getM() {
		return m;
	}


	public int getWidth() {
		return width;
	}


	public int getHeight() {
		return height;
	}


	public JPanel getEcran() {
		return ecran;
	}


	public JPanel getBoutons_Construction() {
		return boutons_Construction;
	}


	public JPanel getFrise() {
		return frise;
	}


	public JPanel getBas() {
		return bas;
	}


	public JPanel getJoueurs() {
		return joueurs;
	}


	public JPanel getAction() {
		return action;
	}


	public JPanel getAnnuler_refaire() {
		return annuler_refaire;
	}


	public JLabel getPiocher() {
		return Piocher;
	}


	public JLabel getPoser() {
		return Poser;
	}


	public JLabel getConstruire() {
		return Construire;
	}


	public JLabel getFinir() {
		return Finir;
	}


	public JBoutonImage getRefaire() {
		return Refaire;
	}


	public JButton getPioche() {
		return Pioche;
	}


	public JBoutonImage getFDT() {
		return FDT;
	}


	public JBoutonImage getTemple() {
		return temple;
	}


	public JBoutonImage getTour() {
		return tour;
	}


	public JBoutonImage getHutte() {
		return hutte;
	}


	public JBoutonImage getAnnuler() {
		return Annuler;
	}


	public JLabel[] getInfoJ1() {
		return InfoJ1;
	}


	public JLabel[] getInfoJ2() {
		return InfoJ2;
	}


	public JLabel[] getInfoJ3() {
		return InfoJ3;
	}


	public JLabel[] getInfoJ4() {
		return InfoJ4;
	}


	public JPanel[] getImage_Stat_J1() {
		return Image_Stat_J1;
	}


	public JPanel[] getImage_Stat_J2() {
		return Image_Stat_J2;
	}


	public JPanel[] getImage_Stat_J3() {
		return Image_Stat_J3;
	}


	public JPanel[] getImage_Stat_J4() {
		return Image_Stat_J4;
	}


	public JPanelImage getPanelJ1() {
		return panelJ1;
	}


	public JPanelImage getPanelJ2() {
		return panelJ2;
	}


	public JPanelImage getPanelJ3() {
		return panelJ3;
	}


	public JPanelImage getPanelJ4() {
		return panelJ4;
	}


	public JLabel getJoueur1() {
		return Joueur1;
	}


	public JLabel getJoueur2() {
		return Joueur2;
	}


	public JLabel getJoueur3() {
		return Joueur3;
	}


	public JLabel getJoueur4() {
		return Joueur4;
	}


	public JBoutonImage getBouton_Pioche() {
		return Bouton_Pioche;
	}


	public JBoutonImage getRotation_Horaire() {
		return Rotation_Horaire;
	}


	public JBoutonImage getRotation_Anti_Horaire() {
		return Rotation_Anti_Horaire;
	}


	public JPanel getPioche_Droite() {
		return Pioche_Droite;
	}


	public JPanel getPioche_Gauche() {
		return Pioche_Gauche;
	}


	public JPanelPioche getPioche_Tuile() {
		return Pioche_Tuile;
	}


	public JPanelImage getToute_la_Pioche() {
		return Toute_la_Pioche;
	}

	public static int getLargeur_total() {
		return largeur_total;
	}


	public static int getHauteur_caneva() {
		return hauteur_caneva;
	}


	public static int getHauteur_total() {
		return hauteur_total;
	}


	public static int getTaille_joueur() {
		return taille_joueur;
	}


	public static int getTaille_annuler_refaire() {
		return taille_annuler_refaire;
	}


	public static int getTaille_pioche() {
		return taille_pioche;
	}


	public static int getTaille_construction() {
		return taille_construction;
	}


	public static int getTaille_fin_de_tour() {
		return taille_fin_de_tour;
	}


	public static int getTaille_frise() {
		return taille_frise;
	}


	public Canvas getCanvas() {
		return canvas;
	}



	
}
