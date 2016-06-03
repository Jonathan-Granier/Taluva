package Menu;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;

import Joueur.Joueur_Generique;
import Moteur.Moteur;

public class Fin_de_partie extends JComponent {
	private JFrame m_fenetre;
	private JFrame principal;
	private Menu_fin_partie panel;
	
	public Fin_de_partie(Moteur moteur,JFrame fenetre,JFrame main){
			init_m_fenetre(main);
			principal = fenetre;
			m_fenetre.add(this);
		}
		
		private void init_m_fenetre(JFrame frame){
			principal = frame;
			m_fenetre = new JFrame("Nouveau");
			m_fenetre.setLayout(new BorderLayout());
			m_fenetre.setVisible(true);
			m_fenetre.setLocation(frame.getWidth()*5/22, frame.getHeight()/11);
			m_fenetre.setSize(frame.getWidth()*6/11,frame.getHeight()*9/11);
			m_fenetre.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			m_fenetre.addWindowListener(new WindowAdapter() {
	            @Override
	            public void windowClosing(WindowEvent we){
	            	principal.setEnabled(true);
		        }
	        });
		}
}
