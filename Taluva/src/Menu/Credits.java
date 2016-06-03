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

@SuppressWarnings("serial")
public class Credits extends JComponent {

	private Image backgroundImage;
	private JFrame fenetre;
	private JPanel panel;
	private JButton accueil;
	
	public Credits(JFrame frame){
		try {
			backgroundImage = ImageIO.read(new File("../Images/credits.jpg"));
		} catch (IOException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}
		fenetre = frame;
		
		accueil = new JButton("Accueil");
		accueil.setFont(new Font("Accueil", Font.BOLD+Font.ITALIC,fenetre.getWidth()/120));
		accueil.setPreferredSize(new Dimension(fenetre.getWidth()/10,fenetre.getHeight()/15));
		accueil.addActionListener(new Ecouteur_boutons_credits("Accueil",this));
		
		panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new BorderLayout());
		panel.add(accueil,BorderLayout.SOUTH);
		panel.setPreferredSize(new Dimension(fenetre.getWidth()/10,fenetre.getHeight()/10));
		
		JPanel panel_vide = new JPanel();
		panel_vide.setOpaque(false);
		this.setLayout(new BorderLayout());
		this.add(panel_vide, BorderLayout.SOUTH);
		this.add(panel,BorderLayout.EAST);
		
	}
	
	public void accueil(){
		this.setVisible(false);
		fenetre.remove(this);
		fenetre.add(new Menu_Demarrage(fenetre));
	}
	
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);	
	
	    // Draw the background image.
	    g.drawImage(backgroundImage, 0, 0, this.getWidth(),this.getHeight(),this);
	}
	
}
