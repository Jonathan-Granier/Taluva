package Menu;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Moteur.Moteur;

public class Demarage {
	JFrame frame;
	JPanel ecran;
	Image image;
	ImageIcon fond;
	JLabel monLabel = new JLabel();
	int height,width;
	java.awt.Dimension dimension;
	Graphics g;
	
	public Demarage(Moteur moteur, JFrame fenetre){
		frame=fenetre;
		ecran = new JPanel();
		fond = new ImageIcon("Assets/Texture/KAMEN.png" );
		monLabel = new JLabel();
		dimension = Toolkit.getDefaultToolkit().getScreenSize();
		height = (int)dimension.getHeight();
        width  = (int)dimension.getWidth();
        try {
			image = ImageIO.read(new File("Assets/Texture/KAMEN.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
        Graphics g = null;
        g.drawImage(image, 0, 0, width,height,ecran);
        
	}
	
	public void MAJ(){
		monLabel.setIcon(fond);
		frame.setVisible(true);
		frame.setSize(width,height);
		frame.setIconImage(image);
		
		ecran.paintComponents(g);
		
	}

}
