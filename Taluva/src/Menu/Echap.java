package Menu;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Echap extends JComponent {

	private JFrame m_fenetre;
	private JFrame principal;
	
	private JButton reprendre;
	private JButton nouveau;
	private JButton regles;
	private JButton sauvegarder;
	private JButton charger;
	private JButton quitter;
	private JPanel pause;
	
	public Echap(JFrame frame){
		init_frame(frame);
		
		
	}
	
	private void init_frame(JFrame frame){
		principal = frame;
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
}
