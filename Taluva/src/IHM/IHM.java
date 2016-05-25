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
	private JPanel ecran, boutons, frise, bas, joueurs, action, annuler_refaire,panelJ1;
	private JLabel p, t, c, f,Joueur1,Joueur2;
	private JButton Annuler,Refaire,Pioche,FDT,templeJ1,tourJ1,hutteJ1,templeJ2,tourJ2,hutteJ2;
	private Canvas canvas;
	 
	
	public IHM(Moteur moteur, JFrame fenetre){
		m=moteur;
		frame=fenetre;
		java.awt.Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		height = (int)dimension.getHeight();
        width  = (int)dimension.getWidth();
        ecran = new JPanel();
        boutons = new JPanel();
        frise = new JPanel();
        bas =new JPanel();
        joueurs = new JPanel();
        action = new JPanel();
        annuler_refaire = new JPanel();
        p = new JLabel("Piocher");
        t = new JLabel("Poser");
        c = new JLabel("Construire");
        f = new JLabel("Finir");
        Annuler = new JButton("Annuler");
        Refaire = new JButton("Refaire");
        Pioche = new JButton("Piocher");
        FDT = new JButton("Fin de tour");
        Joueur1 = new JLabel("Joueur1");
        templeJ1 = new JButton();
        tourJ1 = new JButton();
        hutteJ1 = new JButton();
        panelJ1 = new JPanel();
        Joueur2 = new JLabel("Joueur2");
        templeJ2 = new JButton();
        tourJ2 = new JButton();
        hutteJ2 = new JButton();
        canvas = new Canvas();


	}
	
	
	public void run() {
        
		// Creation d'une fenetre
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ecran.setLayout(new GridBagLayout());
        boutons.setLayout(new GridLayout(2,1));
        frise.setLayout(new GridLayout(1,4));
        bas.setLayout(new GridLayout(1,2));
        joueurs.setLayout(new GridBagLayout());
        action.setLayout(new GridBagLayout());
        annuler_refaire.setLayout(new GridLayout(2,1));
        
        
        //frise
        p.setFont(new Font("Sherif", Font.PLAIN,32));
        p.setHorizontalAlignment(SwingConstants.CENTER);
        p.setOpaque(true);
        
        t.setFont(new Font("Sherif", Font.PLAIN,32));
        t.setHorizontalAlignment(SwingConstants.CENTER);
        t.setOpaque(true);
        
        c.setFont(new Font("Sherif", Font.PLAIN,32));
        c.setHorizontalAlignment(SwingConstants.CENTER);
        c.setOpaque(true);
        
        f.setFont(new Font("Sherif", Font.PLAIN,32));
        f.setHorizontalAlignment(SwingConstants.CENTER);
        f.setOpaque(true);
        
        
        
        
        //création du bouton annuler
        Ecouteur_Boutons annuler = new Ecouteur_Boutons("Annuler",m);
        Annuler.addActionListener(annuler);
        
        //création du bouton refaire
        
        Ecouteur_Boutons refaire = new Ecouteur_Boutons("Refaire",m);
        Refaire.addActionListener(refaire);
        
        //création du bouton pioche
        
        Ecouteur_Boutons pioche = new Ecouteur_Boutons("Piocher",m);
        Pioche.addActionListener(pioche);
        
        //création du bouton fin de tour
        
        Ecouteur_Boutons fdt = new Ecouteur_Boutons("Fin_de_tour",m);
        FDT.addActionListener(fdt);        
        
        
        
        //création de la ligne de J1
        
        Joueur1.setHorizontalAlignment(SwingConstants.CENTER);
        Joueur1.setOpaque(true);
        Ecouteur_Boutons tp1 = new Ecouteur_Boutons("Temple j1",m);
        ImageIcon image_temple = new ImageIcon("Assets/Texture/Button_Temple.png");

        templeJ1.addActionListener(tp1);
        templeJ1.setIcon(image_temple);

        
        
        Ecouteur_Boutons tr1 = new Ecouteur_Boutons("Tour j1",m);
        ImageIcon image_tour = new ImageIcon("Assets/Texture/Button_tower.png");
        tourJ1.addActionListener(tr1);
        tourJ1.setIcon(image_tour);


        
        
        Ecouteur_Boutons ht1 = new Ecouteur_Boutons("Hutte j1",m);
        ImageIcon image_hutte = new ImageIcon("Assets/Texture/Button_Hut.png");
        hutteJ1.addActionListener(ht1);
        hutteJ1.setIcon(image_hutte);


        
        
        panelJ1.setLayout(new GridLayout(1,4));
        
        
        //création de la ligne de J2
        
        
        Joueur2.setHorizontalAlignment(SwingConstants.CENTER);
        Joueur2.setOpaque(true);
        
        Ecouteur_Boutons tp2 = new Ecouteur_Boutons("Temple j2",m);
        templeJ2.addActionListener(tp2);
        templeJ2.setIcon(image_temple);

        
        
        Ecouteur_Boutons tr2 = new Ecouteur_Boutons("Tour j2",m);
        tourJ2.addActionListener(tr2);
        tourJ2.setIcon(image_tour);

        
        
        Ecouteur_Boutons ht2 = new Ecouteur_Boutons("Hutte j2",m);
        hutteJ2.addActionListener(ht2);
        hutteJ2.setIcon(image_hutte);
        
        JPanel panelJ2 = new JPanel();
        panelJ2.setLayout(new GridLayout(1,4));

        canvas.setSize(width, height-height/3);
        //canvas.setFocusable(true);
        //frame.addKeyListener(new KeyboardListener());
        
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
        
        panelJ1.add(Joueur1);
        panelJ1.add(templeJ1);
        panelJ1.add(tourJ1);
        panelJ1.add(hutteJ1);
          
        panelJ2.add(Joueur2);
        panelJ2.add(templeJ2);
        panelJ2.add(tourJ2);
        panelJ2.add(hutteJ2);
        
        frame.add(ecran);
        
        //fenetre.pack();
        frame.setSize(width,height);
        frame.setVisible(true);
        
		
        
  

    }


	public JFrame getFrame() {
		return frame;
	}


	public void setFrame(JFrame frame) {
		this.frame = frame;
	}


	public Moteur getM() {
		return m;
	}

	public int getWidth() {
		return width;
	}


	public void setWidth(int width) {
		this.width = width;
	}


	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
	}


	public JPanel getEcran() {
		return ecran;
	}


	public void setEcran(JPanel ecran) {
		this.ecran = ecran;
	}


	public JPanel getBoutons() {
		return boutons;
	}


	public void setBoutons(JPanel boutons) {
		this.boutons = boutons;
	}


	public JPanel getFrise() {
		return frise;
	}


	public void setFrise(JPanel frise) {
		this.frise = frise;
	}


	public JPanel getBas() {
		return bas;
	}


	public void setBas(JPanel bas) {
		this.bas = bas;
	}


	public JPanel getJoueurs() {
		return joueurs;
	}


	public void setJoueurs(JPanel joueurs) {
		this.joueurs = joueurs;
	}

	public JPanel getAction() {
		return action;
	}


	public void setAction(JPanel action) {
		this.action = action;
	}


	public JPanel getAnnuler_refaire() {
		return annuler_refaire;
	}


	public void setAnnuler_refaire(JPanel annuler_refaire) {
		this.annuler_refaire = annuler_refaire;
	}


	public JPanel getPanelJ1() {
		return panelJ1;
	}


	public void setPanelJ1(JPanel panelJ1) {
		this.panelJ1 = panelJ1;
	}


	public JLabel getP() {
		return p;
	}


	public void setP(JLabel p) {
		this.p = p;
	}


	public JLabel getT() {
		return t;
	}


	public void setT(JLabel t) {
		this.t = t;
	}


	public JLabel getC() {
		return c;
	}


	public void setC(JLabel c) {
		this.c = c;
	}


	public JLabel getF() {
		return f;
	}


	public void setF(JLabel f) {
		this.f = f;
	}


	public JLabel getJoueur1() {
		return Joueur1;
	}


	public void setJoueur1(JLabel joueur1) {
		Joueur1 = joueur1;
	}


	public JLabel getJoueur2() {
		return Joueur2;
	}


	public void setJoueur2(JLabel joueur2) {
		Joueur2 = joueur2;
	}


	public JButton getAnnuler() {
		return Annuler;
	}


	public void setAnnuler(JButton annuler) {
		Annuler = annuler;
	}


	public JButton getRefaire() {
		return Refaire;
	}


	public void setRefaire(JButton refaire) {
		Refaire = refaire;
	}


	public JButton getPioche() {
		return Pioche;
	}


	public void setPioche(JButton pioche) {
		Pioche = pioche;
	}


	public JButton getFDT() {
		return FDT;
	}


	public void setFDT(JButton fDT) {
		FDT = fDT;
	}


	public JButton getTempleJ1() {
		return templeJ1;
	}


	public void setTempleJ1(JButton templeJ1) {
		this.templeJ1 = templeJ1;
	}


	public JButton getTourJ1() {
		return tourJ1;
	}


	public void setTourJ1(JButton tourJ1) {
		this.tourJ1 = tourJ1;
	}


	public JButton getHutteJ1() {
		return hutteJ1;
	}


	public void setHutteJ1(JButton hutteJ1) {
		this.hutteJ1 = hutteJ1;
	}


	public JButton getTempleJ2() {
		return templeJ2;
	}


	public void setTempleJ2(JButton templeJ2) {
		this.templeJ2 = templeJ2;
	}


	public JButton getTourJ2() {
		return tourJ2;
	}


	public void setTourJ2(JButton tourJ2) {
		this.tourJ2 = tourJ2;
	}


	public JButton getHutteJ2() {
		return hutteJ2;
	}


	public void setHutteJ2(JButton hutteJ2) {
		this.hutteJ2 = hutteJ2;
	}


	public Canvas getCanvas() {
		return canvas;
	}


	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}
	
}
