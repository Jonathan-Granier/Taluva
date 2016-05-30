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
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Ecouteur.Ecouteur_Boutons;


public class Menu_Demarrage extends JPanel {
	
	private Image backgroundImage;
	private JPanel menu;
	private JLabel taluva;
	private JButton continuer,nouveau,charger,regles,credits,quitter;

	public Menu_Demarrage() throws IOException {
		backgroundImage = ImageIO.read(new File("../Images/Taluva_Demarrage.png"));
		
		this.setLayout(new GridBagLayout());
		menu = new JPanel();
		JPanel vide = new JPanel();
		JButton rien = new JButton();
		rien.setVisible(false);
		vide.add(rien);
		vide.setOpaque(false);
		menu.setLayout(new GridLayout(7,1));
		
		taluva = new JLabel("Taluva");
		taluva.setFont(new Font("Courier", Font.BOLD+Font.ITALIC,84));
		taluva.setHorizontalAlignment(SwingConstants.CENTER);
		taluva.setForeground(Color.BLACK);
		taluva.setOpaque(false);
	    menu.add(taluva);
		
		continuer = new JButton("Continuer");
		continuer.setBackground(Color.WHITE);
		continuer.setFont(new Font("Continuer", Font.BOLD+Font.ITALIC,40));
		continuer.setOpaque(false);
		menu.add(continuer);
		
		nouveau = new JButton("Nouveau");
		nouveau.setBackground(Color.WHITE);
		nouveau.setFont(new Font("Nouveau", Font.BOLD+Font.ITALIC,40));
		nouveau.setOpaque(false);
		menu.add(nouveau);
		
		charger = new JButton("Charger");
		charger.setBackground(Color.WHITE);
		charger.setFont(new Font("Charger", Font.BOLD+Font.ITALIC,40));
		charger.setOpaque(false);
		menu.add(charger);
		
		regles = new JButton("Règles");
		regles.setBackground(Color.WHITE);
		regles.setFont(new Font("Règles", Font.BOLD+Font.ITALIC,40));
		regles.setOpaque(false);
		menu.add(regles);
		
		credits = new JButton("Crédits");
		credits.setBackground(Color.WHITE);
		credits.setFont(new Font("Crédits", Font.BOLD+Font.ITALIC,40));
		credits.setOpaque(false);
		menu.add(credits);
		
		quitter = new JButton("Quitter");
		quitter.setBackground(Color.WHITE);
		quitter.setFont(new Font("Quitter", Font.BOLD+Font.ITALIC,40));
		quitter.setOpaque(false);
		menu.add(quitter);
		
		menu.setBackground(Color.WHITE);
		menu.setOpaque(true);
/*		GridBagConstraints gbc_this = new GridBagConstraints();
		
		gbc_this.gridx = 0;
		gbc_this.gridy = 0;
		gbc_this.anchor = GridBagConstraints.LINE_END;
		GridBagConstraints aff = new GridBagConstraints();
		ArrayList<JButton> tmp = new ArrayList<JButton>();
		for(int i=0;i<9;i++){
			tmp.add(new JButton());
			tmp.get(i).setVisible(true);
		}
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				aff.gridx=i;
				aff.gridy=j;
				aff.gridheight=1;
				aff.gridwidth=1;
				aff.fill=GridBagConstraints.BOTH;
				if((i==2)&&(j==1)){
					aff.gridy=GridBagConstraints.REMAINDER;
					this.add(menu,aff);
				}
				else 
					this.add(tmp.get(i*3+j),aff);
			}
		}
		gbc_this.gridx=0;
		gbc_this.gridy=0;
		gbc_this.gridheight=1;
		gbc_this.gridwidth=1;
		gbc_this.fill=GridBagConstraints.BOTH;*/
		this.add(menu);
		
	}

  public void paintComponent(Graphics g) {
    super.paintComponent(g);	

    // Draw the background image.
    g.drawImage(backgroundImage, 0, 0, this.getWidth(),this.getHeight(),this);
}

}