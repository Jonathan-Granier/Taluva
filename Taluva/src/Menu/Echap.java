package Menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import renderEngine.Window;
import test.Game;

public class Echap extends JComponent {

	private Image backgroundImage;
	
	private JFrame m_fenetre;
	private JFrame principal;
	private JFrame menu;
	
	private JButton reprendre;
	private JButton nouveau;
	private JButton regles;
	private JButton sauvegarder;
	private JButton charger;
	private JButton menu_principal;
	private JButton quitter;
	private JPanel pause;
	
	private Game game;
	
	public Echap(JFrame frame, JFrame menu, Game game){
		init_frame(frame,menu);
		this.game = game;
		
		int width = m_fenetre.getWidth();
		int height = m_fenetre.getHeight();
		int width_b = width/2;
		int height_b = height/14;
		
		pause = new JPanel();
		pause.setBackground(Color.WHITE);
		pause.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		
		c.insets = new Insets(height_b*3/5,0,0,0);
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridheight=1;
		c.gridwidth=1;
		c.gridx = 0;
		c.gridy = 0;
		c.ipadx = 0;
		c.ipady = 0;
		
		reprendre = new JButton("Reprendre");
		reprendre.setFont(new Font("", Font.BOLD+Font.ITALIC,15));
		reprendre.setPreferredSize(new Dimension(width_b,height_b));
		reprendre.addActionListener(new Ecouteur_boutons_echap("Reprendre",this));
		pause.add(reprendre,c);
		c.insets = new Insets(0,0,0,0);
		
		c.insets = new Insets(-height_b*3/5,0,0,0);
		c.gridy = 1;
		
		nouveau = new JButton("Nouveau");
		nouveau.setFont(new Font("", Font.BOLD+Font.ITALIC,15));
		nouveau.setPreferredSize(new Dimension(width_b,height_b));
		nouveau.addActionListener(new Ecouteur_boutons_echap("Nouveau",this));
		pause.add(nouveau,c);
		
		c.gridy = 2;
		
		regles = new JButton("Règles");
		regles.setFont(new Font("", Font.BOLD+Font.ITALIC,15));
		regles.setPreferredSize(new Dimension(width_b,height_b));
		regles.addActionListener(new Ecouteur_boutons_echap("Règles",this));
		pause.add(regles,c);
		
		c.gridy = 3;
		
		sauvegarder = new JButton("Sauvegarder");
		sauvegarder.setFont(new Font("", Font.BOLD+Font.ITALIC,15));
		sauvegarder.setPreferredSize(new Dimension(width_b,height_b));
		pause.add(sauvegarder,c);

		c.gridy = 4;
		
		charger = new JButton("Charger");
		charger.setFont(new Font("", Font.BOLD+Font.ITALIC,15));
		charger.setPreferredSize(new Dimension(width_b,height_b));
		pause.add(charger,c);
		c.insets = new Insets(0,0,0,0);

		c.insets = new Insets(height_b*3/5,0,0,0);
		c.gridy = 6;
		
		menu_principal = new JButton("Menu Principal");
		menu_principal.setFont(new Font("", Font.BOLD+Font.ITALIC,15));
		menu_principal.setPreferredSize(new Dimension(width_b,height_b));
		menu_principal.addActionListener(new Ecouteur_boutons_echap("Menu Principal",this));
		pause.add(menu_principal,c);
		c.insets = new Insets(0,0,0,0);

		c.insets = new Insets(-height_b*3/5,0,height_b*3/5,0);
		c.gridy = 7;
		
		quitter = new JButton("Quitter");
		quitter.setFont(new Font("", Font.BOLD+Font.ITALIC,15));
		quitter.setPreferredSize(new Dimension(width_b,height_b));
		quitter.addActionListener(new Ecouteur_boutons_echap("Quitter",this));
		pause.add(quitter,c);
		
		m_fenetre.add(pause);
	}
	
	private void init_frame(JFrame frame,JFrame menu){
		principal = frame;
		this.menu = menu;
		m_fenetre = new JFrame("Pause");
		m_fenetre.setVisible(true);
		m_fenetre.setSize(frame.getWidth()/4,frame.getHeight()/2);
		m_fenetre.setLocation(frame.getWidth()*3/8, frame.getHeight()/8);
		m_fenetre.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		m_fenetre.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we){
            	principal.setEnabled(true);
	        }
        });
	}

	public void reprendre(){
		principal.remove(m_fenetre);
		m_fenetre.dispose();
		principal.setEnabled(true);
	}
	public void nouveau(){
		int result = JOptionPane.showConfirmDialog(m_fenetre, "Voulez-vous sauvegarder la partie en cours ?", "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);
		if(result == JOptionPane.YES_OPTION || result == JOptionPane.NO_OPTION){
        	if(result== JOptionPane.YES_OPTION)
        		sauvegarder();
        	principal.remove(m_fenetre);
    		m_fenetre.dispose();
    		principal.setVisible(false);
    		game.cleanUp();
    		game.timerStop();
    		principal.dispose();
    		menu.setVisible(true);
    		menu.add(new Nouveau(menu));
        }
	}
	public void regles(){
		sauvegarder();
		principal.remove(m_fenetre);
		m_fenetre.dispose();
		game.cleanUp();
		game.timerStop();
		principal.setVisible(false);
		principal.dispose();
		menu.setVisible(true);
		menu.getContentPane().removeAll();
		menu.add(new Regles(menu));
		menu.revalidate();
		menu.repaint();
	}
	
	//TODO
	public void sauvegarder(){
		//Voir avec Noha
	}
	public void charger(){
		//Voir avec Noha
	}
	
	public void menu_principal(){
		
		int result = JOptionPane.showConfirmDialog(m_fenetre, "Voulez-vous sauvegarder avant de retourner au menu principal ?", "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);
        if(result == JOptionPane.YES_OPTION || result == JOptionPane.NO_OPTION){
        	if(result== JOptionPane.YES_OPTION)
        		sauvegarder();
        	principal.remove(m_fenetre);
    		m_fenetre.dispose();
    		principal.setVisible(false);
    		game.cleanUp();
    		game.timerStop();
    		principal.dispose();
    		
    		menu.setVisible(true);
    	}
    }
	
	//Ca dépend si on veut quitter le jeu ou revenir au menu demarrage

	public void quitter(){
		int result = JOptionPane.showConfirmDialog(m_fenetre, "Voulez-vous sauvegarder avant de quitter ?", "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);
        if(result == JOptionPane.YES_OPTION || result == JOptionPane.NO_OPTION){
        	if(result== JOptionPane.YES_OPTION)
        		sauvegarder();
        	principal.remove(m_fenetre);
    		m_fenetre.dispose();
    		principal.setVisible(false);
    		principal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		principal.dispose();
    		
    		menu.setVisible(false);
    		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		menu.dispose();
	    }
    }
	
	// Pour rajouter une image en fond
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);	
	
	    // Draw the background image.
	    //g.drawImage(backgroundImage, 0, 0, this.getWidth(),this.getHeight(),this);
	}

}
