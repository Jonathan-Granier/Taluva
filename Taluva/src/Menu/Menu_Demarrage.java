package Menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import IHM.Avancement;
import IHM.IHM;
import Moteur.Moteur;
import charger_sauvegarder.Charger;
import charger_sauvegarder.Continuer;
import terrain.Terrain;
import test.Game;


@SuppressWarnings("serial")
public class Menu_Demarrage extends JComponent {
	
	private Image backgroundImage;
	private JPanel menu;
	private JLabel taluva;
	private JButton continuer,nouveau,charger,regles,credits,quitter;
	private JFrame fenetre;
	private int height,width;
	private Dimension dimension;
	
	//Pour nouveau
	private JFrame gameF;
	private Game game;
	private Moteur moteur;
	private Terrain terrain;
	private IHM ihm;
	private Avancement avancement;
	
	
	// L'INSTANCIATION
	
	public void init_all(JFrame frame) {
		
		try {
			backgroundImage = ImageIO.read(new File("../Images/Taluva_Demarrage.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dimension = Toolkit.getDefaultToolkit().getScreenSize();
		height = (int)dimension.getHeight();
        width  = (int)dimension.getWidth();
		
		fenetre = frame;
		this.setLayout(new BorderLayout());
		
		menu = new JPanel();
		menu.setLayout(new GridLayout(7,1));
		
		taluva = new JLabel("Taluva");
		taluva.setFont(new Font("Courier", Font.BOLD+Font.ITALIC,84));
		taluva.setHorizontalAlignment(SwingConstants.CENTER);
		taluva.setForeground(Color.BLACK);
		taluva.setOpaque(false);
	    menu.add(taluva);
		
		continuer = new JButton("Continuer");
		continuer.setBackground(Color.WHITE);
		continuer.setFont(new Font("Continuer", Font.BOLD+Font.ITALIC,40));
		continuer.setOpaque(false);
		
		File directory = new File("./Save");
		if(directory.listFiles().length<=1){
			continuer.setEnabled(false);
		}
		
		menu.add(continuer);
		
		nouveau = new JButton("Nouveau");
		nouveau.setBackground(Color.WHITE);
		nouveau.setFont(new Font("Nouveau", Font.BOLD+Font.ITALIC,40));
		nouveau.setOpaque(false);
		nouveau.addActionListener(new Ecouteur_boutons_demarrage("Nouveau",this));
		menu.add(nouveau);
		
		charger = new JButton("Charger");
		charger.setBackground(Color.WHITE);
		charger.setFont(new Font("Charger", Font.BOLD+Font.ITALIC,40));
		charger.setOpaque(false);
		charger.addActionListener(new Ecouteur_boutons_demarrage("Charger",this));
		menu.add(charger);
		
		regles = new JButton("Règles");
		regles.setBackground(Color.WHITE);
		regles.setFont(new Font("Règles", Font.BOLD+Font.ITALIC,40));
		regles.setOpaque(false);
		regles.addActionListener(new Ecouteur_boutons_demarrage("Règles",this));
		menu.add(regles);
		
		credits = new JButton("Crédits");
		credits.setBackground(Color.WHITE);
		credits.setFont(new Font("Crédits", Font.BOLD+Font.ITALIC,40));
		credits.setOpaque(false);
		credits.addActionListener(new Ecouteur_boutons_demarrage("Crédits",this));
		menu.add(credits);
		
		quitter = new JButton("Quitter");
		quitter.setBackground(Color.WHITE);
		quitter.setFont(new Font("Quitter", Font.BOLD+Font.ITALIC,40));
		quitter.setOpaque(false);
		quitter.addActionListener(new Ecouteur_boutons_demarrage("Quitter",this));
		menu.add(quitter);
		
		menu.setBackground(Color.WHITE);
		menu.setOpaque(true);

		JPanel east_panel = new JPanel();
		east_panel.setLayout(new BorderLayout());
		JPanel north_panel = new JPanel();
		north_panel.setPreferredSize( new Dimension(width/5, height/6) );
		north_panel.setOpaque(false);
		JPanel south_panel = new JPanel();
		south_panel.setOpaque(false);
		south_panel.setPreferredSize( new Dimension(width/5, height/6) );
		east_panel.add(north_panel, BorderLayout.NORTH);
		east_panel.add(south_panel, BorderLayout.SOUTH);
		east_panel.add(menu, BorderLayout.CENTER);
		east_panel.setOpaque(false);
		
		this.add(east_panel, BorderLayout.EAST);
		
	}
	public Menu_Demarrage(JFrame frame){
		init_all(frame);
	}
	
	public Menu_Demarrage(JFrame frame,JFrame gameF,Game game,Moteur moteur,Terrain terrain,IHM ihm,Avancement avancement) {
		init_all(frame);
		this.gameF = gameF;
		this.game = game;
		this.moteur = moteur;
		this.terrain = terrain;
		this.ihm = ihm;
		this.avancement = avancement;
	}
	
	
	// LES BOUTONS
	
	public void continuer(){
		Continuer continuer = new Continuer("./Save");
		continuer.findFiles();
		game = continuer.getGame();
		moteur = continuer.getMoteur();
	}
	
	public void nouveau(){
		fenetre.setEnabled(false);
		fenetre.add(new Nouveau(fenetre,gameF,game,moteur,terrain,ihm,avancement));
		//fenetre.add(new Echap(fenetre,gameF));
	}
	
	public void charger(){
		Load_save_screen screen = new Load_save_screen();
		screen.run();
		Charger load = new Charger(screen.getPath());
		// TODO
		//Tester si ça marche
		if(load.getSave()==null)System.out.println("Save pour charger null");
		else load.getSave().Restore(game, moteur);
	}

	
	public void regles(){
		this.setVisible(false);
		fenetre.remove(this);
		fenetre.add(new Regles(fenetre));
	}
	
	public void credits(){
		this.setVisible(false);
		fenetre.remove(this);
		fenetre.add(new Credits(fenetre));
	}
	
	public void quitter(){
		int result = JOptionPane.showConfirmDialog(fenetre, "Êtes-vous sûr de vouloir quitter ?", "Confirmation", JOptionPane.CANCEL_OPTION);
        if(result == JOptionPane.OK_OPTION){
        	fenetre.setVisible(false);
    		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            fenetre.dispose();
        }
	}
	
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);	
	
	    // Draw the background image.
	    g.drawImage(backgroundImage, 0, 0, this.getWidth(),this.getHeight(),this);
	}

}