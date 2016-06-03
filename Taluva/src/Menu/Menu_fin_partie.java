package Menu;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;

import Joueur.Joueur_Generique;
import Moteur.Moteur;

public class Menu_fin_partie extends JComponent {
	private Image backgroundImage;
	private JFrame fenetre;
	private JFrame principal;
	private Moteur moteur;
	private Joueur_Generique gagnant;
	private JButton retour,rejouer,acceuil,quitter;
	
	public Menu_fin_partie(JFrame fenetre,JFrame principal, Moteur moteur){
		this.fenetre = fenetre;
		this.principal = principal;
		
		this.moteur = moteur;
		init();
		
	}
	
	private void init()
	{
		if(moteur.get_joueurs_gagnant().size()!=1){
			try {
				backgroundImage = ImageIO.read(new File("../Images/Egalite.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			gagnant=moteur.get_joueurs_gagnant().get(0);
			switch(gagnant.getnomFaction()){
			case "Occidentaux":
				try {
					backgroundImage = ImageIO.read(new File("../Images/Victoire screen/rome_by_takmaj.jpg"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "Orientaux":
				try {
					backgroundImage = ImageIO.read(new File("../Images/Victoire screen/lake_shrine_by_zanariya.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "Bayloniens":
				try {
					backgroundImage = ImageIO.read(new File("../Images/Victoire screen/Babylon_ andreiPervukhin.jpg"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "Vikings":
				try {
					backgroundImage = ImageIO.read(new File("../Images/Victoire screen/viking_0BO.jpg"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
		}
		
	}
	
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    g.drawImage(backgroundImage, 0, 0, this.getWidth(),this.getHeight(),this);
	}
}
