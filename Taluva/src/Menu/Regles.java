package Menu;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Regles extends JPanel {
	private Image backgroundImage;
	private ArrayList<File> slides;
	private JButton prec,suiv;
	private JButton accueil,reprendre;
	private JFrame fenetre;
	
	public Regles(JFrame frame) throws IOException{
		for(int i=0;i<9;i++)
			slides.add(new File("../Images/Images-Regles/Regles_p"+(i+1)+".jpg"));
		backgroundImage = ImageIO.read(slides.get(0));
		
		
		fenetre = frame;
		
	}
	
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);	

	    // Draw the background image.
	    g.drawImage(backgroundImage, 0, 0, this.getWidth(),this.getHeight(),this);
	}
}
