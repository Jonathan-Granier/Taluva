package Menu;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Menu_Demarrage extends JPanel {
	
	private Image backgroundImage;
	private JPanel menu;
	private JLabel taluva;
	private JButton continuer,nouveau,charger,regles,credits,quitter;

	// Some code to initialize the background image.
	// Here, we use the constructor to load the image. This
	// can vary depending on the use case of the panel.
	public Menu_Demarrage() throws IOException {
		backgroundImage = ImageIO.read(new File("../Images/image_taluva.jpg"));
		
		this.setLayout(new GridBagLayout());
		menu = new JPanel();
		menu.setLayout(new GridBagLayout());
		
		taluva = new JLabel("Taluva");
		taluva.setFont(new Font("Courier", Font.BOLD,48));
		taluva.setHorizontalAlignment(SwingConstants.CENTER);
		taluva.setOpaque(true);
	        
		GridBagConstraints gbc_Taluva = new GridBagConstraints();
		gbc_Taluva.weightx = 0.3;
		gbc_Taluva.weighty = 0.5;
		gbc_Taluva.gridx = 0;
		gbc_Taluva.gridy = 0;
		gbc_Taluva.fill = GridBagConstraints.BOTH;
		gbc_Taluva.gridwidth = 3;
		gbc_Taluva.gridheight = 1;
	    menu.add(taluva,gbc_Taluva);
		
		continuer = new JButton("Continuer");
		GridBagConstraints gbc_Continuer = new GridBagConstraints();
		gbc_Continuer.weightx = 0.3;
		gbc_Continuer.weighty = 0.5;
		gbc_Continuer.gridx = 2;
		gbc_Continuer.gridy = 1;
		gbc_Continuer.fill = GridBagConstraints.BOTH;
		gbc_Continuer.gridwidth = 3;
		gbc_Continuer.gridheight = 1;
		menu.add(continuer,gbc_Continuer);
		
		nouveau = new JButton("Nouveau");
		charger = new JButton("Charger");
		regles = new JButton("Règles");
		credits = new JButton("Crédits");
		quitter = new JButton("Quitter");
		
		this.add(menu);
		//this.setLayout(new BorderLayout());
		//this.add(continuer,BorderLayout.EAST);
		//this.add(quitter,BorderLayout.WEST);
		//this.add(credits, BorderLayout.NORTH);
	}

  public void paintComponent(Graphics g) {
    super.paintComponent(g);	

    // Draw the background image.
    g.drawImage(backgroundImage, 0, 0, this.getWidth(),this.getHeight(),this);
}

}