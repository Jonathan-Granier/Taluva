package Menu;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;

import Moteur.Moteur;
import test.Game;

@SuppressWarnings("serial")
public class Fin_de_partie extends JComponent {
	private JFrame m_fenetre;
	private JFrame principal;
	private Menu_fin_partie panel;
	
	public Fin_de_partie(Moteur moteur,JFrame fenetre,JFrame main,Game game){
			init_m_fenetre(fenetre);
			panel = new Menu_fin_partie(m_fenetre,principal,main,moteur,game);
			m_fenetre.add(panel);
		}
		
	private void init_m_fenetre(JFrame frame){
		principal = frame;
		m_fenetre = new JFrame("PARTIE TERMINEE !");
		m_fenetre.setResizable(false);
		m_fenetre.setUndecorated(true);
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
