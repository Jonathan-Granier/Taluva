package Menu;

import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class Nouveau extends JComponent {

	JFrame m_fenetre;
	
	public Nouveau(JFrame frame){
		m_fenetre = new JFrame("Nouveau");
		//frame.add(m_fenetre);
		m_fenetre.setVisible(true);
		m_fenetre.setSize(frame.getWidth(),frame.getHeight());
		m_fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void paintComponent(Graphics g) {
	    //super.paintComponent(g);	
	
	    // Draw the background image.
	    //g.drawImage(backgroundImage, 0, 0, this.getWidth(),this.getHeight(),this);
	}
	
}
