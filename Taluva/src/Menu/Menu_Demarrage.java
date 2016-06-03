package Menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

import terrain.Terrain;
import test.Game;
import IHM.Avancement;
import IHM.IHM;
import Moteur.Moteur;
import charger_sauvegarder.Charger;
import charger_sauvegarder.Continuer;


@SuppressWarnings("serial")
public class Menu_Demarrage extends JComponent {
	
	private Image backgroundImage;
	private JPanel menu;
	private JLabel taluva;
	
	private JButton continuer,nouveau,charger,comment_jouer,credits,quitter;
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
			// Auto-generated catch block
			e.printStackTrace();
		}
		
		dimension = Toolkit.getDefaultToolkit().getScreenSize();
		height = (int)dimension.getHeight();
        width  = (int)dimension.getWidth();
		
		fenetre = frame;
		this.setLayout(new BorderLayout());
		
		menu = new JPanel();
		menu.setLayout(new GridLayout(7,1));
		menu.setOpaque(false);
		
		taluva = new JLabel("");
		taluva.setFont(new Font("Courier", Font.BOLD+Font.ITALIC,84));
		taluva.setHorizontalAlignment(SwingConstants.CENTER);
		taluva.setForeground(Color.BLACK);
		taluva.setOpaque(false);
	    menu.add(taluva);
		
		continuer = new JButton("Continuer");
		continuer.setBackground(Color.WHITE);
		continuer.setForeground(Color.YELLOW);
		continuer.setBorder(null);
		continuer.setFont(new Font("Continuer", Font.BOLD+Font.ITALIC,40));
		continuer.setOpaque(false);
		
		File directory = new File("./Save");
		if(directory.listFiles().length<=1){
			continuer.setEnabled(false);
		}
		continuer.addActionListener(new Ecouteur_boutons_demarrage("Continuer",this));
		menu.add(continuer);
		
		nouveau = new JButton("Nouveau");
		nouveau.setBackground(Color.WHITE);
		nouveau.setFont(new Font("Nouveau", Font.BOLD+Font.ITALIC,40));
		nouveau.setForeground(Color.YELLOW);
		nouveau.setBorder(null);
		nouveau.setOpaque(false);
		nouveau.addActionListener(new Ecouteur_boutons_demarrage("Nouveau",this));
		menu.add(nouveau);
		
		charger = new JButton("Charger");
		charger.setBackground(Color.WHITE);
		charger.setFont(new Font("Charger", Font.BOLD+Font.ITALIC,40));
		charger.setForeground(Color.YELLOW);
		charger.setBorder(null);
		charger.setOpaque(false);
		charger.addActionListener(new Ecouteur_boutons_demarrage("Charger",this));
		if(!continuer.isEnabled())
			charger.setEnabled(false);
		menu.add(charger);
		
		comment_jouer = new JButton("Comment jouer");
		comment_jouer.setBackground(Color.WHITE);
		comment_jouer.setFont(new Font("", Font.BOLD+Font.ITALIC,40));
		comment_jouer.setForeground(Color.YELLOW);
		comment_jouer.setBorder(null);
		comment_jouer.setOpaque(false);
		comment_jouer.addActionListener(new Ecouteur_boutons_demarrage("Comment jouer",this));
		menu.add(comment_jouer);
		
		credits = new JButton("Credits");
		credits.setBackground(Color.WHITE);
		credits.setFont(new Font("Credits", Font.BOLD+Font.ITALIC,40));
		credits.setForeground(Color.YELLOW);
		credits.setBorder(null);
		credits.setOpaque(false);
		credits.addActionListener(new Ecouteur_boutons_demarrage("Credits",this));
		menu.add(credits);
		
		quitter = new JButton("Quitter");
		quitter.setBackground(Color.WHITE);
		quitter.setFont(new Font("Quitter", Font.BOLD+Font.ITALIC,40));
		quitter.setForeground(Color.YELLOW);
		quitter.setBorder(null);
		quitter.setOpaque(false);
		quitter.addActionListener(new Ecouteur_boutons_demarrage("Quitter",this));
		menu.add(quitter);
		
		menu.setBackground(Color.WHITE);
		menu.setOpaque(false);

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
	
	// Pour restaurer une partie a partir d'un game, d'une JFrame et d'un moteur
	
	private void restore(Moteur moteur){
		gameF = new JFrame();
		game = new Game();
		gameF.addKeyListener(game);
        gameF.setFocusable(true);
        gameF.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        gameF.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we){
                int result = JOptionPane.showConfirmDialog(gameF, "Etes-vous sur de vouloir quitter ?", "Confirmation", JOptionPane.CANCEL_OPTION);
                if(result == JOptionPane.OK_OPTION){
                	game.cleanUp();
                	gameF.setVisible(false);
                	gameF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            		gameF.dispose();
                }
            }
        });
        
        fenetre.setVisible(false);
        ihm = new IHM(moteur, gameF);
        ihm.run();
        avancement = new Avancement(ihm);
        moteur.addPhaseListener(avancement);
        moteur.getJ1().addBatimentCountListener(avancement);
        moteur.getJ2().addBatimentCountListener(avancement);
        moteur.MajListeners();
        ihm.getCanvas().setFocusable(false);
        
	}
	
	
	public void continuer(){
		if(this.continuer.isEnabled()){
			Load_save_screen screen = new Load_save_screen();
			if(screen.getPath()!=null){
				Continuer cont = new Continuer("./Save");
				cont.getSave().restore(moteur);
				restore(moteur);
			}
		}
	}
	
	public void nouveau(){
		fenetre.setEnabled(false);
		fenetre.add(new Nouveau(fenetre,gameF,game,moteur,terrain,ihm,avancement));
	}
	
	// TODO
	//Tester si ca marche
	public void charger(){
		if(continuer.isEnabled()){
			Load_save_screen screen = new Load_save_screen();
			if(screen.getPath()!=null){
				Charger load = new Charger(screen.getPath());
				load.getSave().restore(moteur);
				restore(moteur);
			}
		}
	}

	
	public void comment_jouer(){
		fenetre.setEnabled(false);
		fenetre.add(new Regles(fenetre));
	}
	
	public void credits(){
		this.setVisible(false);
		fenetre.remove(this);
		fenetre.add(new Credits(fenetre));
	}
	
	public void quitter(){
		int result = JOptionPane.showConfirmDialog(fenetre, "Etes-vous sur de vouloir quitter ?", "Confirmation", JOptionPane.CANCEL_OPTION);
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