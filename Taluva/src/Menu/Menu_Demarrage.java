package Menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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
		backgroundImage = ImageIO.read(new File("../Images/Taluva_Demarrage.png"));
		
		this.setLayout(new BorderLayout());
		menu = new JPanel();
		JPanel vide = new JPanel();
		vide.setOpaque(false);
		menu.setLayout(new GridLayout(7,0));
		
		taluva = new JLabel("Taluva");
		taluva.setFont(new Font("Courier", Font.BOLD+Font.ITALIC,84));
		taluva.setHorizontalAlignment(SwingConstants.CENTER);
		taluva.setForeground(Color.BLACK);
		taluva.setOpaque(false);
	        
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
		continuer.setBackground(Color.WHITE);
		continuer.setFont(new Font("Continuer", Font.BOLD+Font.ITALIC,40));
		GridBagConstraints gbc_Continuer = new GridBagConstraints();
		gbc_Continuer.weightx = 0.3;
		gbc_Continuer.weighty = 0.5;
		gbc_Continuer.gridx = 0;
		gbc_Continuer.gridy = 1;
		gbc_Continuer.fill = GridBagConstraints.BOTH;
		gbc_Continuer.gridwidth = 3;
		gbc_Continuer.gridheight = 3;
		continuer.setOpaque(false);
		menu.add(continuer,gbc_Continuer);
		
		nouveau = new JButton("Nouveau");
		nouveau.setBackground(Color.WHITE);
		nouveau.setFont(new Font("Nouveau", Font.BOLD+Font.ITALIC,40));
		GridBagConstraints gbc_Nouveau = new GridBagConstraints();
		gbc_Nouveau.weightx = 0.3;
		gbc_Nouveau.weighty = 0.5;
		gbc_Nouveau.gridx = 0;
		gbc_Nouveau.gridy = 4;
		gbc_Nouveau.fill = GridBagConstraints.BOTH;
		gbc_Nouveau.gridwidth = 3;
		gbc_Nouveau.gridheight = 3;
		nouveau.setOpaque(false);
		menu.add(nouveau,gbc_Nouveau);
		
		charger = new JButton("Charger");
		charger.setBackground(Color.WHITE);
		charger.setFont(new Font("Charger", Font.BOLD+Font.ITALIC,40));
		GridBagConstraints gbc_Charger = new GridBagConstraints();
		gbc_Charger.weightx = 0.3;
		gbc_Charger.weighty = 0.5;
		gbc_Charger.gridx = 0;
		gbc_Charger.gridy = 7;
		gbc_Charger.fill = GridBagConstraints.BOTH;
		gbc_Charger.gridwidth = 3;
		gbc_Charger.gridheight = 3;
		charger.setOpaque(false);
		menu.add(charger,gbc_Charger);
		
		regles = new JButton("Règles");
		regles.setBackground(Color.WHITE);
		regles.setFont(new Font("Règles", Font.BOLD+Font.ITALIC,40));
		GridBagConstraints gbc_Regle = new GridBagConstraints();
		gbc_Regle.weightx = 0.3;
		gbc_Regle.weighty = 0.5;
		gbc_Regle.gridx = 0;
		gbc_Regle.gridy = 10;
		gbc_Regle.fill = GridBagConstraints.BOTH;
		gbc_Regle.gridwidth = 3;
		gbc_Regle.gridheight = 3;
		regles.setOpaque(false);
		menu.add(regles,gbc_Regle);
		
		credits = new JButton("Crédits");
		credits.setBackground(Color.WHITE);
		credits.setFont(new Font("Crédits", Font.BOLD+Font.ITALIC,40));
		GridBagConstraints gbc_Credits = new GridBagConstraints();
		gbc_Credits.weightx = 0.3;
		gbc_Credits.weighty = 0.5;
		gbc_Credits.gridx = 0;
		gbc_Credits.gridy = 13;
		gbc_Credits.fill = GridBagConstraints.BOTH;
		gbc_Credits.gridwidth = 3;
		gbc_Credits.gridheight = 3;
		credits.setOpaque(false);
		menu.add(credits,gbc_Credits);
		
		quitter = new JButton("Quitter");
		quitter.setBackground(Color.WHITE);
		quitter.setFont(new Font("Quitter", Font.BOLD+Font.ITALIC,40));
		GridBagConstraints gbc_Quitter = new GridBagConstraints();
		gbc_Quitter.weightx = 0.3;
		gbc_Quitter.weighty = 0.5;
		gbc_Quitter.gridx = 0;
		gbc_Quitter.gridy = 16;
		gbc_Quitter.fill = GridBagConstraints.BOTH;
		gbc_Quitter.gridwidth = 3;
		gbc_Quitter.gridheight = 3;
		quitter.setOpaque(false);
		menu.add(quitter,gbc_Quitter);
		
		menu.setBackground(Color.WHITE);
		menu.setOpaque(true);
		this.add(menu,BorderLayout.EAST);
		
		
	}

  public void paintComponent(Graphics g) {
    super.paintComponent(g);	

    // Draw the background image.
    g.drawImage(backgroundImage, 0, 0, this.getWidth(),this.getHeight(),this);
}

}