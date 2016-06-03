package Menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import IHM.Avancement;
import IHM.IHM;
import Joueur.IA_Heuristique;
import Joueur.IA_Moyenne;
import Joueur.IA_Random;
import Joueur.Joueur_Generique;
import Joueur.Joueur_Humain;
import Moteur.Moteur;
import terrain.Case.Couleur_Joueur;
import terrain.Terrain;
import test.Game;

@SuppressWarnings("serial")
public class Nouveau extends JComponent {
	
	private JFrame m_fenetre;
	private JFrame principal;
	
	private JLabel joueur_1;
	private JComboBox<String> humain_j1;
	private JComboBox<String> faction_j1;
	
	private JLabel joueur_2;
	private JComboBox<String> humain_j2;
	private JComboBox<String> faction_j2;
	
	private JLabel pioche;
	private JComboBox<String> taille_pioche;
	
	private JButton accueil;
	private JButton lancer;
	
	//POUR LANCER UNE PARTIE
	private JFrame gameF;
	private Game game;
	private Moteur moteur;
	private Terrain terrain;
	private IHM ihm;
	private Avancement avancement;
	private Joueur_Generique j1,j2;
	
	// L'INSTANCIATION
	private void init_panels(JFrame frame){

		
		// Initialisation de l'Ecran de selection pour une nouvelle partie
		
		init_m_fenetre(frame);
		
		int width = m_fenetre.getWidth();
		int height = m_fenetre.getHeight();
		int width_b = width/9;
		int height_b = height/15;
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		
		c.insets = new Insets(height_b*2,-width_b,0,0);
		JLabel type = new JLabel("Type de joueur");
		type.setFont(new Font("", Font.BOLD+Font.ITALIC,15));
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridheight=1;
		c.gridwidth=1;
		c.gridx = 1;
		c.gridy = 0;
		c.ipadx = 0;
		c.ipady = 0;
		panel.add(type,c);
		c.insets = new Insets(0,0,0,0);
		
		c.insets = new Insets(height_b*2,-width_b*2,0,0);
		JLabel faction = new JLabel("Factions");
		faction.setFont(new Font("", Font.BOLD+Font.ITALIC,15));
		c.gridx = 2;
		c.gridy = 0;
		panel.add(faction,c);
		c.insets = new Insets(0,0,0,0);
		
		c.insets = new Insets(0,width_b,0,0);
		joueur_1 = new JLabel("Joueur 1");
		joueur_1.setFont(new Font("", Font.BOLD+Font.ITALIC,15));
		c.gridx = 0;
		c.gridy = 1;
		panel.add(joueur_1,c);
		c.insets = new Insets(0,0,0,0);
		
		
		String[] type_joueur = {" Humain", " IA_Facile", " IA_Moyenne", " IA_Difficile"};
		String[] factions = {" Occidentaux", " Orientaux", " Babyloniens", " Vikings"};
		String[] tailles = {" 24 Tuiles" , " 48 Tuiles"};
		
		c.insets = new Insets(0,-width_b,0,0);
		c.gridx = 1;
		c.gridy = 1;
		humain_j1 = new JComboBox<String>(type_joueur);
		humain_j1.setSelectedIndex(0);
		humain_j1.setFont(new Font("", Font.BOLD+Font.ITALIC,15));
		humain_j1.setPreferredSize( new Dimension(width_b*3/2, height_b) );
		panel.add(humain_j1,c);
		c.insets = new Insets(0,0,0,0);
		
		c.insets = new Insets(0,-width_b*2,0,0);
		c.gridx = 2;
		c.gridy = 1;
		faction_j1 = new JComboBox<String>(factions);
		faction_j1.setSelectedIndex(0);
		faction_j1.setFont(new Font("", Font.BOLD+Font.ITALIC,15));
		faction_j1.setPreferredSize( new Dimension(width_b*3/2, height_b) );
		panel.add(faction_j1,c);
		c.insets = new Insets(0,0,0,0);
		
		//JOUEUR 2
		c.insets = new Insets(0,width_b,0,0);
		c.gridx = 0;
		c.gridy = 3;
		joueur_2 = new JLabel("Joueur 2");
		joueur_2.setFont(new Font("", Font.BOLD+Font.ITALIC,15));
		panel.add(joueur_2,c);
		c.insets = new Insets(0,0,0,0);
		
		c.insets = new Insets(0,-width_b,0,0);
		c.gridx = 1;
		c.gridy = 3;
		humain_j2 = new JComboBox<String>(type_joueur);
		humain_j2.setSelectedIndex(0);
		humain_j2.setFont(new Font("", Font.BOLD+Font.ITALIC,15));
		humain_j2.setPreferredSize( new Dimension(width_b*3/2, height_b) );
		panel.add(humain_j2,c);
		c.insets = new Insets(0,0,0,0);
		
		c.insets = new Insets(0,-width_b*2,0,0);
		c.gridx = 2;
		c.gridy = 3;
		faction_j2 = new JComboBox<String>(factions);
		faction_j2.setSelectedIndex(1);
		faction_j2.setFont(new Font("", Font.BOLD+Font.ITALIC,15));
		faction_j2.setPreferredSize( new Dimension(width_b*3/2, height_b) );
		panel.add(faction_j2,c);
		c.insets = new Insets(0,0,0,0);
		
		c.insets = new Insets(0,width_b,0,0);
		c.gridx = 0;
		c.gridy = 4;
		pioche = new JLabel("Taille de la pioche");
		pioche.setFont(new Font("", Font.BOLD+Font.ITALIC,15));
		pioche.setPreferredSize( new Dimension(width_b*2, height_b) );
		panel.add(pioche,c);
		c.insets = new Insets(0,0,0,0);
		
		c.insets = new Insets(0,-width_b,0,0);
		c.gridx = 1;
		c.gridy = 4;
		taille_pioche = new JComboBox<String>(tailles);
		taille_pioche.setSelectedIndex(0);
		taille_pioche.setFont(new Font("", Font.BOLD+Font.ITALIC,15));
		taille_pioche.setPreferredSize( new Dimension(width_b*2, height_b) );
		panel.add(taille_pioche,c);
		c.insets = new Insets(0,0,0,0);
		
		c.insets = new Insets(-height_b,width_b*2,0,0);
		c.gridx = 1;
		c.gridy = 5;
		accueil = new JButton("Accueil");
		accueil.addActionListener(new Ecouteur_boutons_nouveau("Accueil",this));
		accueil.setFont(new Font("", Font.BOLD+Font.ITALIC,15));
		accueil.setPreferredSize( new Dimension(width_b, height_b) );
		panel.add(accueil,c);
		c.insets = new Insets(0,0,0,0);
		
		c.insets = new Insets(-height_b,0,0,0);
		c.gridx = 2;
		c.gridy = 5;
		lancer = new JButton("Lancer partie");
		lancer.addActionListener(new Ecouteur_boutons_nouveau("Lancer",this));
		lancer.setFont(new Font("", Font.BOLD+Font.ITALIC,15));
		lancer.setPreferredSize( new Dimension(width_b*2, height_b) );
		panel.add(lancer,c);
		c.insets = new Insets(0,0,0,0);
		
		m_fenetre.add(panel);

	}
	
	//Constructeur uniquement pour le menu Echap
	public Nouveau(JFrame frame){
		init_panels(frame);
	}
	
	public Nouveau(JFrame frame,JFrame gameF,Game game,Moteur moteur,Terrain terrain,IHM ihm,Avancement avancement){
		
		// Initialisation des parametres necessaires pour le lancement d'une partie
		this.gameF = gameF;
		this.game = game;
		this.moteur = moteur;
		this.terrain = terrain;
		this.ihm = ihm;
		this.avancement = avancement;
		init_panels(frame);
		
	}
	
	private void init_m_fenetre(JFrame frame){
		principal = frame;
		m_fenetre = new JFrame("Nouveau");
		m_fenetre.setResizable(false);
		m_fenetre.setUndecorated(true);
		m_fenetre.setLayout(new BorderLayout());
		m_fenetre.setVisible(true);
		m_fenetre.setLocation(frame.getWidth()*5/22, frame.getHeight()*5/22);
		m_fenetre.setSize(frame.getWidth()*6/11,frame.getHeight()*6/11);
		m_fenetre.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		m_fenetre.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we){
            	principal.setEnabled(true);
	        }
        });
	}
	
	// Actions des boutons
	public void accueil(){
		principal.remove(m_fenetre);
		m_fenetre.dispose();
		principal.setEnabled(true);
	}
	
	
	// Vérifie si tout s'est bien passe dans la selection des parametres
	// Affiche un message d'erreur detaille en cas de problème rencontre (meme faction des 2 joueurs par exemple)
	
	// Renvoie le nom correspondant a la faction choisie
	private String init_faction(JComboBox<String> faction){
		switch (faction.getSelectedIndex()){
		case 0 : 
			//System.out.println("[Nouveau/Init_joueurs] Occidentaux");
			return "Occidentaux";
		case 1 :
			//System.out.println("[Nouveau/Init_joueurs] Orientaux");
			return "Orientaux";
		case 2 :
			//System.out.println("[Nouveau/Init_joueurs] Bayloniens");
			return "Bayloniens";
		case 3 :
			//System.out.println("[Nouveau/Init_joueurs] Vikings");
			return "Vikings";
		default :
			//System.out.println("[Nouveau/Init_Faction] switch case default");
			return null;
		}
	}
	// Renvoie la couleur correspondant à la faction choisie
	private Couleur_Joueur init_faction_couleur(JComboBox<String> faction){
		switch (faction.getSelectedIndex()){
		case 0 : 
			//System.out.println("[Nouveau/Init_joueurs] Occidentaux");
			return Couleur_Joueur.BLEU;
		case 1 :
			//System.out.println("[Nouveau/Init_joueurs] Orientaux");
			return Couleur_Joueur.JAUNE;
		case 2 :
			//System.out.println("[Nouveau/Init_joueurs] Bayloniens");
			return Couleur_Joueur.BLANC;
		case 3 :
			//System.out.println("[Nouveau/Init_joueurs] Vikings");
			return Couleur_Joueur.ROSE;
		default :
			//System.out.println("[Nouveau/Init_Faction] switch case default");
			return null;
		}
	}
	
	// Initialise le joueur générique en fonction du type choisi et de la faction choisie
	private Joueur_Generique init_joueurs(Couleur_Joueur couleur,String faction,Moteur moteur,JComboBox<String> humain){
		switch (humain.getSelectedIndex()){
			case 0 :
				//System.out.println("[Nouveau/Init_joueurs] HUMAIN");
				return new Joueur_Humain(couleur,faction);
			case 1 :
				//System.out.println("[Nouveau/Init_joueurs] IA_Random");
				return new IA_Random(couleur,moteur,faction);
			case 2 :
				//System.out.println("[Nouveau/Init_joueurs] IA_Heuristique");
				return new IA_Moyenne(couleur,moteur,faction);
			case 3 :
				//System.out.println("[Nouveau/Init_joueurs] IA_Heuristique");
				return new IA_Heuristique(couleur,moteur,faction);
			default :
				//System.out.println("[Nouveau/Init_joueurs] switch case default");
				return null;
		}
	}
	
	//Taille de la pioche
	private int taille_pioche(){
		if(taille_pioche.getSelectedIndex()==0)return 24;
		return 48;
	}
	
	public void lancer(){
		if(faction_j1.getSelectedIndex() != faction_j2.getSelectedIndex()){
			terrain = new Terrain();
			moteur = new Moteur(terrain,taille_pioche());
	        j1=null;
	        j2=null;
	        j1 = init_joueurs(init_faction_couleur(faction_j1),init_faction(faction_j1),moteur,humain_j1);
	        j2 = init_joueurs(init_faction_couleur(faction_j2),init_faction(faction_j2),moteur,humain_j2);
	        
	        //On ferme l'ecran de selection
	        principal.remove(m_fenetre);
			m_fenetre.dispose();
			principal.setEnabled(true);
			principal.setVisible(false);
	        
	        lancer_jeu(j1,j2);
		}
		else 
			JOptionPane.showMessageDialog(m_fenetre, "Les 2 joueurs doivent appartenir a� differentes factions !");
		
	}
	
	private void lancer_jeu(Joueur_Generique j1,Joueur_Generique j2){
		gameF = new JFrame();
		gameF.setResizable(false);
		gameF.setUndecorated(true);
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
        
        moteur.add_j1(j1);
        moteur.add_j2(j2);
        ihm = new IHM(moteur, gameF);
        ihm.run();
        avancement = new Avancement(ihm,gameF,principal,game);
        moteur.addPhaseListener(avancement);
        moteur.getJ1().addBatimentCountListener(avancement);
        moteur.getJ2().addBatimentCountListener(avancement);
        moteur.MajListeners();
        ihm.getCanvas().setFocusable(false);
        
        
        
        game.init(principal,gameF,moteur,ihm.getCanvas(),ihm.getPioche_Tuile());
        moteur.addGame(game);
        moteur.lancer_partie();
        
	}

}
