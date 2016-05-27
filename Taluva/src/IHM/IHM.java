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
import Ecouteur.KeyboardListener;
import Moteur.Moteur;


public class IHM {
	
	private JFrame frame;
	private Moteur m;
	private int width,height;
	private JPanel ecran, boutons_Construction, frise, bas, joueurs, action, annuler_refaire,
  StatJ1, StatJ2;
	private JLabel  Poser, Construire, Finir, image_Temple_J1, image_Tour_J1, image_Hutte_J1,image_Temple_J2, image_Tour_J2, image_Hutte_J2,
	nb_Hutte_J1,nb_Tour_J1,nb_Temple_J1, nb_Hutte_J2,nb_Tour_J2,nb_Temple_J2;
	private JButton Annuler,Refaire,Piocher,FDT,temple,tour,hutte;
	
	// On factorise
	private JLabel[] InfoJ1,InfoJ2;
	private JPanel[] Image_Stat_J1,Image_Stat_J2;
	private JPanel panelJ1,panelJ2;
	private JLabel Joueur1,Joueur2;
	
	
	private static String Fichier_Temple = "Assets/Texture/Button_Temple.png";
	private static String Fichier_Tour = "Assets/Texture/Button_tower.png";
	private static String Fichier_Hutte = "Assets/Texture/Button_Hut.png";
	private static int largeur_total = 3;
    private static int hauteur_caneva = 9;
    private static int hauteur_total = 12;
    private static int taille_joueur = 1;
    private static int taille_annuler_refaire = 1;
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
       
       // A delete
        StatJ1 = new JPanel();
        StatJ2 = new JPanel();
        
        // A delete
        image_Temple_J1  = new JLabel();
        image_Tour_J1  = new JLabel(); 
        image_Hutte_J1  = new JLabel();
        
        canvas = new Canvas();
        
        //Joueurs
        InfoJ1 = new JLabel[6];
        InfoJ2 = new JLabel[6];
       
        Image_Stat_J1 = new JPanel[3];
        Image_Stat_J2 = new JPanel[3];
        
        
        
        Joueur1 = new JLabel("Joueur 1");
        Joueur2 = new JLabel("Joueur 2");
        panelJ1 = new JPanel();
        panelJ2 = new JPanel();
        
	}
	
	
	public void run() {
        
		// Creation d'une fenetre
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ecran.setLayout(new GridBagLayout());
       
        
        boutons_Construction.setLayout(new GridBagLayout());
        
        bas.setLayout(new GridLayout(1,2));
        joueurs.setLayout(new GridLayout(2,1));
        action.setLayout(new GridBagLayout());
        
        StatJ1.setLayout(new GridBagLayout());
        panelJ1.setLayout(new GridLayout(1,4));
        
        StatJ2.setLayout(new GridBagLayout());
        panelJ2.setLayout(new GridLayout(1,4));
        
        
        
        
        
        
        
        // On ajoute tout les elements au JPanel Ecran
        AjoutCaneva();
        AjoutJoueurs();
        AjoutFrise();
        AjoutBoutonAction();
        AjoutAnnulerRefaire();
        
        //Set du format des noms des joueurs
        
       
		
		/*
        nb_Hutte_J1 = InitStatConstruction(Integer.toString(m.nb_max_Huttes));
        nb_Tour_J1 = InitStatConstruction(Integer.toString(m.nb_max_Tours));
        nb_Temple_J1 = InitStatConstruction(Integer.toString(m.nb_max_Temples));
        
        */
      //  panelJ1.setLayout(new GridLayout(3,2));
        
	
     /*   
        nb_Hutte_J2 = InitStatConstruction(Integer.toString(m.nb_max_Huttes));
        nb_Tour_J2 = InitStatConstruction(Integer.toString(m.nb_max_Tours));
        nb_Temple_J2 = InitStatConstruction(Integer.toString(m.nb_max_Temples));
     */   
       // panelJ2.setLayout(new GridLayout(3,2));
        
        
        
        //Creer les images qui seront dans les données des joueurs
     /*   image_Temple_J1  = InitImage(Fichier_Temple);
        image_Tour_J1  = InitImage(Fichier_Tour); 
        image_Hutte_J1  = InitImage(Fichier_Hutte);
        image_Temple_J2  = InitImage(Fichier_Temple);
        image_Tour_J2  = InitImage(Fichier_Tour); 
        image_Hutte_J2  = InitImage(Fichier_Hutte);
        
        
     */   
        
        
        //Création des boutons
        
        
     
        
     
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        
      
        
        
        //Bouton Construction
        /*
        GridBagConstraints gbc3 = new GridBagConstraints();
        gbc3.fill = GridBagConstraints.BOTH;
        gbc3.gridwidth = 2;
        gbc3.gridheight = 2;
        gbc3.weightx = gbc3.weighty = 1.0;
        gbc3.gridx = 0;
        gbc3.gridy = 10;
        boutons_Construction.add(hutte,gbc3);
        gbc3.gridx = 0;
        gbc3.gridy = 11;
        boutons_Construction.add(tour,gbc3);
        gbc3.gridx = 0;
        gbc3.gridy = 12;
        boutons_Construction.add(temple,gbc3);
        
       */
        //Joueurs
        /*
        gbc3.fill = GridBagConstraints.NONE;
        gbc3.gridwidth = 1;
        gbc3.gridheight = 1;
        gbc3.weightx = 1.0;
        gbc3.weighty = 1.0;
        gbc3.gridx = 0;
        gbc3.gridy = 0;
    //    gbc3.gridheight = 1;
        StatJ1.add(image_Hutte_J1,gbc3);
        gbc3.gridx = 1;
        StatJ1.add(nb_Hutte_J1,gbc3);
        gbc3.gridx = 2;
        StatJ1.add(image_Tour_J1,gbc3);
        gbc3.gridx = 3;
        StatJ1.add(nb_Tour_J1,gbc3);
        gbc3.gridx = 4;
        StatJ1.add(image_Temple_J1,gbc3);
        gbc3.gridx = 5;
        StatJ1.add(nb_Temple_J1,gbc3);
        
        gbc3.fill = GridBagConstraints.NONE;
        gbc3.gridwidth = 1;
        gbc3.gridheight = 1;
        gbc3.weightx = 1.0;
        gbc3.weighty = 1.0;
        gbc3.gridx = 0;
        gbc3.gridy = 0;
    //    gbc3.gridheight = 1;
        StatJ2.add(Joueur2);
        StatJ2.add(image_Hutte_J2,gbc3);
        gbc3.gridx = 1;
        StatJ2.add(nb_Hutte_J2,gbc3);
        gbc3.gridx = 2;
        StatJ2.add(image_Tour_J2,gbc3);
        gbc3.gridx = 3;
        StatJ2.add(nb_Tour_J2,gbc3);
        gbc3.gridx = 4;
        StatJ2.add(image_Temple_J2,gbc3);
        gbc3.gridx = 5;
        StatJ2.add(nb_Temple_J2,gbc3);
        
       
        
     
        gbc3.fill = GridBagConstraints.VERTICAL;
        gbc3.gridwidth = 1;
        gbc3.gridheight = 1;
        gbc3.weightx = 1.0;
        gbc3.weighty = 1.0;
        gbc3.gridx = 0;
        gbc3.gridy = 0;
        panelJ1.add(Joueur1,gbc3);
        
        gbc3.fill = GridBagConstraints.VERTICAL;
        gbc3.gridx = 0;
        gbc3.gridy = 0;
        panelJ1.add(StatJ1,gbc3);
       
        
        gbc3.fill = GridBagConstraints.VERTICAL;
        gbc3.gridwidth = 1;
        gbc3.gridheight = 1;
        gbc3.weightx = 1.0;
        gbc3.weighty = 1.0;
        gbc3.gridx = 0;
        gbc3.gridy = 0;
        
        
        panelJ2.add(Joueur2,gbc3);
        gbc3.fill = GridBagConstraints.VERTICAL;
        gbc3.gridx = 1;
        gbc3.gridy = 0;
        panelJ2.add(StatJ2,gbc3);
       

        */
        /*
        
        //GridBagConstraints gbc3 = new GridBagConstraints();
        gbc3.fill = GridBagConstraints.VERTICAL;
        gbc3.gridwidth = 1;
        gbc3.gridheight = 2;
        gbc3.weightx = 1.0;
        gbc3.weighty = 1.0;
        gbc3.gridx = 0;
        gbc3.gridy = 9;
  //      joueurs.add(panelJ1,gbc3);
        gbc3.gridx = 1;
        gbc3.gridy = 10;
    //    joueurs.add(StatJ1,gbc3);
        
        gbc3.gridx = 0;
        gbc3.gridy = 11;
     //   joueurs.add(panelJ2,gbc3);
        
        gbc3.gridx = 0;
        gbc3.gridy = 12;
   //     joueurs.add(StatJ2,gbc3);
      */  
        
       
       
        //Joueur
 /*       gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.3;
        gbc.weighty = 0.5;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        action.add(joueurs, gbc);
  */      
        
        
        
        /*
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        gbc2.fill = GridBagConstraints.BOTH;
        gbc2.gridwidth = 1;
        gbc2.gridheight = 2;
        annuler_refaire.add(Annuler,gbc2);

        gbc2.gridx = 0;
        gbc2.gridy = 2;
        gbc2.fill = GridBagConstraints.BOTH;
        gbc2.gridwidth = 1;
        gbc2.gridheight = 2;
        annuler_refaire.add(Refaire,gbc2);
        
       
        
        */

        
       
        
  //      panelJ1.add(Joueur1);
       /* panelJ1.add(hutteJ1);
        panelJ1.add(tourJ1);
        panelJ1.add(templeJ1);*/
          
    //    panelJ2.add(Joueur2);
    /*    panelJ2.add(hutteJ2);
        panelJ2.add(tourJ2);
        panelJ2.add(templeJ2);*/
      
        
       
        
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
        canvas.getSize();
	}
	
	//Ajoute la frise au Panel Ecran
	private void AjoutFrise()
	{
		
		frise.setLayout(new GridLayout(1,3));
		// Pioche = initFriseElement("Pioche");
        Poser =  initFriseElement("Poser");
        Construire =  initFriseElement("Construire");
        Finir =  initFriseElement("Finir");
        //création de la frise
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = taille_frise;
        
        gbc.gridheight = 1;
        gbc.gridx = taille_joueur;
        gbc.gridy = hauteur_caneva;
        //frise.add(p,BorderLayout.CENTER);
        frise.add(Poser,BorderLayout.CENTER);
        frise.add(Construire,BorderLayout.CENTER);
        frise.add(Finir,BorderLayout.CENTER);
        ecran.add(frise,gbc);
        
        
	}
	
	//Ajoute les boutons action au Panel Ecran
	private void AjoutBoutonAction()
	{
		//TODO
		Piocher = FaireBouton("Piocher");
        FDT = FaireBouton("Fin_de_tour");
        hutte = FaireBouton("Hutte",Fichier_Hutte);
        tour = FaireBouton("Tour", Fichier_Tour);
        temple = FaireBouton("Temple", Fichier_Temple);
        
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
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        action.add(Piocher,gbc);
        

        //Bouton construction 
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.2;
        gbc.weighty = 0.5;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridx = 3;
        gbc.gridy = 0;
        action.add(boutons_Construction, gbc);
        
        
        
        
        //Fin de Tour
        gbc.gridx = 5;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;       
        action.add(FDT,gbc);
        
        
        
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = taille_frise;
        gbc.gridheight = hauteur_total - hauteur_caneva - 1;
        gbc.gridx = taille_joueur;
        gbc.weightx = gbc.weighty = 1.0;
        gbc.gridy = hauteur_caneva + 1;
        ecran.add(action, gbc);
        
        
        
        
	}
	
	//Ajoute les bouton Annuler et Refaire au Panel Ecran
    private void AjoutAnnulerRefaire()
    {
    	
    	annuler_refaire.setLayout(new GridLayout(2,1));
    	Annuler = FaireBouton("Annuler");
        Refaire = FaireBouton("Refaire");
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
        gbc.gridy = hauteur_caneva;
        ecran.add(annuler_refaire, gbc);
        
    }
	
	
	//Ajoute tous les joueurs à l'ecran le panel principal
	private void AjoutJoueurs()
	{
		int i;
		for(i=0;i<3;i++)
        {
        	Image_Stat_J1[i] = new JPanel();
        	Image_Stat_J2[i] = new JPanel();
        	Image_Stat_J1[i].setLayout(new GridLayout(1,2));
        	Image_Stat_J2[i].setLayout(new GridLayout(1,2));
        }
        
        for(i=0;i<6;i++)
        {
        	InfoJ1[i]= new JLabel();
        	InfoJ2[i]= new JLabel();
        }
        
        Joueur1.setHorizontalAlignment(SwingConstants.CENTER);
        Joueur1.setOpaque(true);
        Joueur2.setHorizontalAlignment(SwingConstants.CENTER);
        Joueur2.setOpaque(true);
        
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
		
		// Fusion Image et nombre en 1 JPanel chacun 
		
		for(i=0;i<3;i++)
		{
			Image_Stat_J1[i].add(InfoJ1[i*2]);
			Image_Stat_J1[i].add(InfoJ1[i*2+1]);
			Image_Stat_J2[i].add(InfoJ2[i*2]);
			Image_Stat_J2[i].add(InfoJ2[i*2+1]);
		}
		
		
		//Fusion du nom des joueurs et des Image_Stat en 1 JPanel
		panelJ1.add(Joueur1);
		panelJ2.add(Joueur2);
		for(i=0;i<3;i++)
		{
			panelJ1.add(Image_Stat_J1[i]);
			panelJ2.add(Image_Stat_J2[i]);
		}
		
		joueurs.add(panelJ1);
		joueurs.add(panelJ2);
		
		//Ajout des joueurs à l'ecran
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = taille_joueur;
        gbc.gridheight = hauteur_total-hauteur_caneva;
        gbc.gridx = 0;
        gbc.weightx = 0.1;
        gbc.weighty = 1.0;
        gbc.gridy = hauteur_caneva;
        ecran.add(joueurs, gbc);
	}
	
	
	// Initialise un element de la frise
	private JLabel initFriseElement(String nom)
	{
		 JLabel label = new JLabel(nom);
		 label.setFont(new Font("Sherif", Font.PLAIN,32));
	     label.setHorizontalAlignment(SwingConstants.CENTER);
	     label.setOpaque(true);
		 return label;
	}
	
	// Creer un bouton a partir de son nom
	private JButton FaireBouton(String nom_bouton)
	{
		JButton bouton = new JButton(nom_bouton);
		Ecouteur_Boutons ecouteur_bouton = new Ecouteur_Boutons(nom_bouton,m);
        bouton.addActionListener(ecouteur_bouton);
        return bouton;
        
	}
	
	
	// Creer un bouton a partir de son nom et son image
	private JButton FaireBouton(String nom_bouton, String nom_image)
	{
		JButton bouton = new JButton();
		Ecouteur_Boutons ecouteur_bouton = new Ecouteur_Boutons(nom_bouton,m);
        ImageIcon image = new ImageIcon(nom_image);
        bouton.addActionListener(ecouteur_bouton);
        bouton.setIcon(image);
        return bouton;  
	}
	
	private JLabel InitImage(String nom_image)
	{
		JLabel label = new JLabel();
		ImageIcon image = new ImageIcon(nom_image);
		label.setIcon(image);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		return label;
	}
	
	private JLabel InitStatConstruction(String nom)
	{
		 JLabel label = new JLabel(nom +" / "+ nom);
		 label.setFont(new Font("Sherif", Font.PLAIN,20));
	     label.setHorizontalAlignment(SwingConstants.CENTER);
	     label.setOpaque(true);
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


	public JPanel getPanelJ1() {
		return panelJ1;
	}


	public JPanel getPanelJ2() {
		return panelJ2;
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


	public JLabel getJoueur1() {
		return Joueur1;
	}


	public JLabel getJoueur2() {
		return Joueur2;
	}


	public JButton getAnnuler() {
		return Annuler;
	}


	public JButton getRefaire() {
		return Refaire;
	}


	public JButton getPiocher() {
		return Piocher;
	}


	public JButton getFDT() {
		return FDT;
	}


	public JButton getTemple() {
		return temple;
	}


	public JButton getTour() {
		return tour;
	}


	public JButton getHutte() {
		return hutte;
	}


	public Canvas getCanvas() {
		return canvas;
	}

	
}
