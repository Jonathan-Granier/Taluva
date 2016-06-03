package Menu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Joueur.Joueur_Generique;
import Moteur.Moteur;
import test.Game;

public class Menu_fin_partie extends JComponent {
	private Image backgroundImage;
	
	private JFrame fenetre;
	private JFrame principal;
	private JFrame menu;
	
	private Game game;
	private Moteur moteur;
	private Joueur_Generique gagnant;
	
	private JButton retour,rejouer,acceuil;
	
	public Menu_fin_partie(JFrame fenetre,JFrame principal,JFrame main,Moteur moteur,Game game){
		
		this.fenetre = fenetre;
		this.principal = principal;
		this.moteur = moteur;
		this.menu = main;
		this.game = game;
		init_image();
		
		this.setLayout(new BorderLayout());
		
		int width = fenetre.getWidth()/10 ;
		int height = fenetre.getHeight()/10 ;
		int font = fenetre.getWidth()/100 ;
		
		
		JPanel east,west,all;
		west = new JPanel();
		west.setOpaque(false);
		west.setLayout(new BorderLayout());
		
		east = new JPanel();
		east.setOpaque(false);
		//west.setLayout(new BorderLayout());
		
		all = new JPanel();
		all.setOpaque(false);
		all.setLayout(new BorderLayout());
		
		retour = new JButton("Retour");
		retour.setPreferredSize(new Dimension(width,height));
		retour.setFont(new Font("", Font.BOLD+Font.ITALIC,font));
		retour.addActionListener(new Ecouteur_boutons_fin("Retour",this));
		
		rejouer = new JButton("Rejouer");
		rejouer.setPreferredSize(new Dimension(width,height));
		rejouer.setFont(new Font("", Font.BOLD+Font.ITALIC,font));
		rejouer.addActionListener(new Ecouteur_boutons_fin("Rejouer",this));
		
		acceuil = new JButton("Accueil");
		acceuil.setPreferredSize(new Dimension(width,height));
		acceuil.setFont(new Font("", Font.BOLD+Font.ITALIC,font));
		acceuil.addActionListener(new Ecouteur_boutons_fin("Accueil",this));
		
		west.add(retour, BorderLayout.WEST);
		
		east.add(rejouer);
		east.add(acceuil);
		
		all.add(west, BorderLayout.WEST);
		all.add(east, BorderLayout.EAST);
		
		this.add(all,BorderLayout.SOUTH);
		
	}
	
	private void init_image()
	{
		
		
		if(moteur.get_joueurs_gagnant().size()!=1){
			try {
				backgroundImage = ImageIO.read(new File("../Images/Victoire screen/Egalite_win.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			gagnant=moteur.get_joueurs_gagnant().get(0);
			switch(gagnant.getnomFaction()){
			case "Occidentaux":
				try {
					backgroundImage = ImageIO.read(new File("../Images/Victoire screen/Occident_win.jpg"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "Orientaux":
				try {
					backgroundImage = ImageIO.read(new File("../Images/Victoire screen/Orientales_win.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "Bayloniens":
				try {
					backgroundImage = ImageIO.read(new File("../Images/Victoire screen/Babylone_win.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "Vikings":
				try {
					backgroundImage = ImageIO.read(new File("../Images/Victoire screen/Viking_win.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
		}
	}
	
	// LES BOUTONS
	public void retour(){
		principal.remove(fenetre);
		fenetre.dispose();
		principal.setEnabled(true);
	}
	
	public void accueil(){
		principal.remove(fenetre);
		fenetre.dispose();
		principal.setVisible(false);
		game.cleanUp();
		game.timerStop();
		principal.dispose();
		
		menu.setVisible(true);
		
	}
	
	public void rejouer(){
		principal.remove(fenetre);
		fenetre.dispose();
		principal.setVisible(false);
		game.cleanUp();
		game.timerStop();
		principal.dispose();
		menu.setVisible(true);
		menu.add(new Nouveau(menu));
	}
	
	//Afficher l'image en background
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    g.drawImage(backgroundImage, 0, 0, this.getWidth(),this.getHeight(),this);
	}
}
