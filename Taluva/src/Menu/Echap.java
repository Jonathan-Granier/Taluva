package Menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import IHM.Avancement;
import IHM.IHM;
import Moteur.Moteur;
import test.Game;
import charger_sauvegarder.Charger;
import charger_sauvegarder.Sauvegarde;

@SuppressWarnings("serial")
public class Echap extends JComponent {

	//private Image backgroundImage;
	
	private JFrame m_fenetre;
	private JFrame principal;
	private JFrame menu;
	
	private JButton reprendre;
	private JButton nouveau;
	private JButton comment_jouer;
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
		
		comment_jouer = new JButton("Comment jouer");
		comment_jouer.setFont(new Font("", Font.BOLD+Font.ITALIC,15));
		comment_jouer.setPreferredSize(new Dimension(width_b,height_b));
		comment_jouer.addActionListener(new Ecouteur_boutons_echap("Comment jouer",this));
		pause.add(comment_jouer,c);
		
		c.gridy = 3;
		
		sauvegarder = new JButton("Sauvegarder");
		sauvegarder.setFont(new Font("", Font.BOLD+Font.ITALIC,15));
		sauvegarder.setPreferredSize(new Dimension(width_b,height_b));
		sauvegarder.addActionListener(new Ecouteur_boutons_echap("Sauvegarder",this));
		File directory = new File("./Save");
		if(directory.listFiles().length<=1){
			sauvegarder.setEnabled(false);
		}
		pause.add(sauvegarder,c);

		c.gridy = 4;
		
		charger = new JButton("Charger");
		charger.setFont(new Font("", Font.BOLD+Font.ITALIC,15));
		charger.setPreferredSize(new Dimension(width_b,height_b));
		charger.addActionListener(new Ecouteur_boutons_echap("Charger",this));
		if(directory.listFiles().length<=1){
			charger.setEnabled(false);
		}
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
		int result = JOptionPane.showConfirmDialog(m_fenetre, "Etes-vous sur de vouloir quitter ?", "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);
		if(result == JOptionPane.YES_OPTION){
			result = JOptionPane.showConfirmDialog(m_fenetre, "Voulez-vous sauvegarder la partie en cours ?", "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);
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
	public void comment_jouer(){
		m_fenetre.setEnabled(false);
		m_fenetre.add(new Regles(m_fenetre,principal));
	}
	
	//TODO
	public void sauvegarder(){
		/*Sauvegarde save = new Sauvegarde(game.getMoteur());
		save.sauvegarder(save,"./Save");
		JOptionPane.showMessageDialog(m_fenetre, "Partie sauvegardee !");*/
	}
	/*private void restore(Moteur moteur){
		JFrame gameF = new JFrame();
		Game game = new Game();
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
        principal.remove(m_fenetre);
		m_fenetre.dispose();
		principal.setEnabled(true);
		
		principal.setVisible(false);
		game.cleanUp();
		game.timerStop();
		principal.dispose();
		
		IHM ihm = new IHM(moteur, gameF);
        ihm.run();
        Avancement avancement = new Avancement(ihm);
        moteur.addPhaseListener(avancement);
        moteur.getJ1().addBatimentCountListener(avancement);
        moteur.getJ2().addBatimentCountListener(avancement);
        moteur.MajListeners();
        ihm.getCanvas().setFocusable(false);
	}*/
	
	public void charger(){
		/*int result = JOptionPane.showConfirmDialog(m_fenetre, "Voulez-vous sauvegarder avant de retourner au menu principal ?", "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);
        if(result == JOptionPane.YES_OPTION || result == JOptionPane.NO_OPTION){
        	if(result== JOptionPane.YES_OPTION)
        		sauvegarder();
        	
			File directory = new File("./Save");
			if(directory.listFiles().length>1){
				Load_save_screen screen = new Load_save_screen();
				if(screen.getPath()!=null){
					Charger load = new Charger(screen.getPath());
					load.getSave().restore(game, null);
					
					principal.remove(m_fenetre);
					m_fenetre.dispose();
					principal.setEnabled(true);
					
					principal.setVisible(false);
		    		game.cleanUp();
		    		game.timerStop();
		    		principal.dispose();
		    		
					//restore(game,null);
				}
			}
        }*/
	}
	
	public void menu_principal(){
		int result = JOptionPane.showConfirmDialog(m_fenetre, "Voulez-vous sauvegarder avant de retourner au menu principal ?", "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);
        if(result == JOptionPane.YES_OPTION || result == JOptionPane.NO_OPTION){
        	if(result== JOptionPane.YES_OPTION)
        		sauvegarder();
        	
        	result = JOptionPane.showConfirmDialog(m_fenetre, "Etes-vous sur de vouloir quitter la partie en cours ?", "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);
	        if(result == JOptionPane.YES_OPTION){
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
    }
	
	public void quitter(){
		int result = JOptionPane.showConfirmDialog(m_fenetre, "Voulez-vous sauvegarder la partie en cours ?", "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);
        if(result == JOptionPane.YES_OPTION || result == JOptionPane.NO_OPTION){
        	if(result== JOptionPane.YES_OPTION)
        		sauvegarder();
        	
			result = JOptionPane.showConfirmDialog(m_fenetre, "Etes-vous sur de vouloir quitter ?", "Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);
	        if(result == JOptionPane.YES_OPTION){
	        	principal.remove(m_fenetre);
	    		m_fenetre.dispose();
	    		principal.setVisible(false);
	    		game.cleanUp();
	    		game.timerStop();
	    		principal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    		principal.dispose();
	    		
	    		menu.setVisible(false);
	    		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    		menu.dispose();
		    }
        }
    }
	
	/*
	// Pour rajouter une image en fond
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);	
	
	    // Draw the background image.
	    //g.drawImage(backgroundImage, 0, 0, this.getWidth(),this.getHeight(),this);
	}
	*/
}
