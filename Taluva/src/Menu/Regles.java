package Menu;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Regles extends JComponent {
	
	private JFrame m_fenetre;
	private JFrame principal;
	private Regle_panel panel;
	
	public Regles(JFrame frame){
		init_m_fenetre(frame);
		panel = new Regle_panel(m_fenetre,principal);
		m_fenetre.add(new Regle_panel(m_fenetre,principal));
	}
	public Regles(JFrame frame, JFrame gameF){
		init_m_fenetre(gameF);
		principal = frame;
		panel = new Regle_panel(m_fenetre,principal);
		m_fenetre.add(panel);
	}

	private void init_m_fenetre(JFrame frame){
		principal = frame;
		m_fenetre = new JFrame("Nouveau");
		m_fenetre.setLayout(new BorderLayout());
		m_fenetre.setVisible(true);
		m_fenetre.setLocation(frame.getWidth()*5/22, frame.getHeight()/11);
		m_fenetre.setSize(frame.getWidth()*6/11,frame.getHeight()*9/11);
		m_fenetre.addKeyListener(new KeyboardListenerRegles(panel));
		m_fenetre.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		m_fenetre.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we){
            	principal.setEnabled(true);
	        }
        });
	}
	
}
