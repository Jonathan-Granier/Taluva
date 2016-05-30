package Menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class Menu_Demarrage extends JComponent {
	
	private Image backgroundImage;
	private JPanel menu;
	private JLabel taluva;
	private JButton continuer,nouveau,charger,regles,credits,quitter;
	private JFrame fenetre;
	private int height,width;
	private Dimension dimension;
	
	
	public Menu_Demarrage(JFrame frame) throws IOException {
		this.setBounds(0, 0, width, height);

		
		backgroundImage = ImageIO.read(new File("../Images/Taluva_Demarrage.png"));
		
		
		dimension = Toolkit.getDefaultToolkit().getScreenSize();
		height = (int)dimension.getHeight();
        width  = (int)dimension.getWidth();
		
		
		fenetre = frame;
		fenetre.setSize(width, height);
		fenetre.setVisible(true);
		this.setLayout(new BorderLayout());
//		this.setLayout(new GridBagLayout());
//		this.setSize(width, height);

		JPanel vide = new JPanel();
		JButton rien = new JButton();
		rien.setVisible(false);
		vide.add(rien);
		vide.setOpaque(false);
		
		menu = new JPanel();
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

		JPanel east_panel = new JPanel();
		east_panel.setLayout(new BorderLayout());
		JPanel north_panel = new JPanel();
		north_panel.setPreferredSize( new Dimension(width/5, height/6) );
		north_panel.setOpaque(false);
		JPanel south_panel = new JPanel();
		south_panel.setOpaque(false);
		south_panel.setPreferredSize( new Dimension(width/5, height/6) );
		east_panel.add(north_panel, BorderLayout.NORTH);
		east_panel.add(south_panel, BorderLayout.SOUTH);
		east_panel.add(menu, BorderLayout.CENTER);
		east_panel.setOpaque(false);
		
		this.add(east_panel, BorderLayout.EAST);
		/*
		GridBagConstraints aff = new GridBagConstraints();
		ArrayList<JButton> tmp = new ArrayList<JButton>();
		
		for(int i=0;i<81;i++){
			tmp.add(new JButton());
			tmp.get(i).setVisible(true);
		}
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				aff.gridx=i;
				aff.gridy=j;
				aff.gridheight=1;
				aff.gridwidth=1;
				aff.fill=GridBagConstraints.BOTH;
				if( (i==7) && (j==1) ){
					aff.gridheight = 6;
					this.add(menu,aff);
				}
				else 
					this.add(tmp.get(i*3+j),aff);
			}
		}*/
	}

  public void paintComponent(Graphics g) {
    super.paintComponent(g);	

    // Draw the background image.
    g.drawImage(backgroundImage, 0, 0, this.getWidth(),this.getHeight(),this);
}

}