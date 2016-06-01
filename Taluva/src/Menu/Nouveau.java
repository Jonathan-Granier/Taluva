package Menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Nouveau extends JComponent {

	private JFrame m_fenetre;
	private JFrame principal;
	
	private JLabel joueur_1;
	private JButton humain_j1;
	private JButton couleur_j1;
	
	private JLabel joueur_2;
	private JButton humain_j2;
	private JButton couleur_j2;
	
	private JButton avance;
	
	private JButton accueil;
	private JButton lancer;
	
	public Nouveau(JFrame frame){
		init_m_fenetre(frame);
		
		int width = m_fenetre.getWidth();
		int height = m_fenetre.getHeight();
		int width_b = width/9;
		int height_b = height/15;
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.insets = new Insets(height_b*3,width_b,0,0);
		joueur_1 = new JLabel("Joueur 1");
		joueur_1.setFont(new Font("", Font.BOLD+Font.ITALIC,15));
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridheight=1;
		c.gridwidth=1;
		c.gridx = 0;
		c.gridy = 0;
		c.ipadx = 0;
		c.ipady = 0;
		panel.add(joueur_1,c);
		c.insets = new Insets(0,0,0,0);
		
		c.insets = new Insets(height_b*3,-width_b,0,0);
		c.gridx = 1;
		c.gridy = 0;
		humain_j1 = new JButton("humain _ J1");
		humain_j1.setFont(new Font("", Font.BOLD+Font.ITALIC,15));
		humain_j1.setPreferredSize( new Dimension(width_b*3/2, height_b) );
		panel.add(humain_j1,c);
		c.insets = new Insets(0,0,0,0);
		
		c.insets = new Insets(height_b*3,-width_b*2,0,0);
		c.gridx = 2;
		c.gridy = 0;
		couleur_j1 = new JButton("Faction");
		couleur_j1.setFont(new Font("", Font.BOLD+Font.ITALIC,15));
		couleur_j1.setPreferredSize( new Dimension(width_b*3/2, height_b) );
		panel.add(couleur_j1,c);
		c.insets = new Insets(0,0,0,0);
		
		//JOUEUR 2
		c.insets = new Insets(0,width_b,0,0);
		c.gridx = 0;
		c.gridy = 2;
		joueur_2 = new JLabel("Joueur 2");
		joueur_2.setFont(new Font("", Font.BOLD+Font.ITALIC,15));
		panel.add(joueur_2,c);
		c.insets = new Insets(0,0,0,0);
		
		c.insets = new Insets(0,-width_b,0,0);
		c.gridx = 1;
		c.gridy = 2;
		humain_j2 = new JButton("humain _ J2");
		humain_j2.setFont(new Font("", Font.BOLD+Font.ITALIC,15));
		humain_j2.setPreferredSize( new Dimension(width_b*3/2, height_b) );
		panel.add(humain_j2,c);
		c.insets = new Insets(0,0,0,0);
		
		c.insets = new Insets(0,-width_b*2,0,0);
		c.gridx = 2;
		c.gridy = 2;
		couleur_j2 = new JButton("Faction");
		couleur_j2.setFont(new Font("", Font.BOLD+Font.ITALIC,15));
		couleur_j2.setPreferredSize( new Dimension(width_b*3/2, height_b) );
		panel.add(couleur_j2,c);
		c.insets = new Insets(0,0,0,0);
		
		c.insets = new Insets(0,width_b,0,0);
		c.gridx = 0;
		c.gridy = 3;
		avance = new JButton("Parametres Avancés");
		avance.setFont(new Font("", Font.BOLD+Font.ITALIC,15));
		avance.setPreferredSize( new Dimension(width_b*2, height_b) );
		panel.add(avance,c);
		c.insets = new Insets(0,0,0,0);
		
		c.insets = new Insets(-height_b,width_b*2,0,0);
		c.gridx = 1;
		c.gridy = 4;
		accueil = new JButton("Accueil");
		accueil.setFont(new Font("", Font.BOLD+Font.ITALIC,15));
		accueil.setPreferredSize( new Dimension(width_b, height_b) );
		panel.add(accueil,c);
		c.insets = new Insets(0,0,0,0);
		
		c.insets = new Insets(-height_b,0,0,0);
		c.gridx = 2;
		c.gridy = 4;
		lancer = new JButton("Lancer");
		lancer.setFont(new Font("", Font.BOLD+Font.ITALIC,15));
		lancer.setPreferredSize( new Dimension(width_b, height_b) );
		panel.add(lancer,c);
		c.insets = new Insets(0,0,0,0);
		
		m_fenetre.add(panel);
	
	}
	
	private void init_m_fenetre(JFrame frame){
		principal = frame;
		m_fenetre = new JFrame("Nouveau");
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
	
}
