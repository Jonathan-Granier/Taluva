package Menu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Regle_panel extends JComponent {
	private Image backgroundImage;
	private ArrayList<File> slides;
	private JButton prec,suiv;
	private JButton retour;
	
	private JFrame fenetre;
	private JFrame principal;
	
	private JPanel prec_suiv;
	private int page_courante;
	
	public Regle_panel(JFrame frame,JFrame principal){
		slides = new ArrayList<File>();
		for(int i=0;i<8;i++){
			slides.add(new File("../Images/Images-Regles/Regles_p"+(i+1)+".jpg"));
		}
		
		try {
			backgroundImage = ImageIO.read(slides.get(0));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		page_courante = 1;		
		
		fenetre = frame;
		this.principal = principal;
		
		this.setLayout(new BorderLayout());
		
		int width = fenetre.getWidth()/10 ;
		int height = fenetre.getHeight()/10 ;
		int font = fenetre.getWidth()/100 ;
		
		// 2 Panels : 1 pour les boutons Précédent/Suivant
		// l'autre pour les boutons Accueil / Reprendre
		prec_suiv = new JPanel();
		prec_suiv.setOpaque(false);
		prec_suiv.setPreferredSize(new Dimension(width,height));
		
		JPanel all = new JPanel();
		all.setOpaque(false);
		all.setLayout(new BorderLayout());
		
		//Boutons précédent, suivant, accueil, reprendre
		prec = new JButton("Précedent");
		prec.addActionListener(new Ecouteur_boutons_regles("Précedent",this));
		prec.setPreferredSize(new Dimension(fenetre.getWidth()/10,fenetre.getHeight()/15));
		prec.setFont(new Font("Précédent", Font.BOLD+Font.ITALIC,font));
		prec.setEnabled(false);
		
		suiv = new JButton("Suivant");
		suiv.addActionListener(new Ecouteur_boutons_regles("Suivant",this));
		suiv.setPreferredSize(new Dimension(fenetre.getWidth()/10,fenetre.getHeight()/15));
		suiv.setFont(new Font("Suivant", Font.BOLD+Font.ITALIC,font));
		
		JPanel ret = new JPanel();
		ret.setOpaque(false);
		ret.setLayout(new BorderLayout());
		retour = new JButton("Retour");
		retour.addActionListener(new Ecouteur_boutons_regles("Retour",this));
		retour.setFont(new Font("Retour", Font.BOLD+Font.ITALIC,font));
		retour.setPreferredSize(new Dimension(width,height*10/13));
		ret.setPreferredSize(new Dimension(width,height));
		ret.add(retour,BorderLayout.NORTH);
		
		prec_suiv.add(prec);
		prec_suiv.add(suiv);		
		all.add(prec_suiv,BorderLayout.CENTER);
		all.add(ret, BorderLayout.EAST);
		this.add(all,BorderLayout.SOUTH);
	}
	
	// Affiche le slide suivant (dépendemment du slide courant)
	public void suivant(){
		if(page_courante>=1)
			prec.setEnabled(true);
		if(page_courante < slides.size()){
			page_courante++;
			try {
				backgroundImage = ImageIO.read(slides.get(page_courante-1));
			} catch (IOException e) {
				// Auto-generated catch block
				e.printStackTrace();
			}
			if(page_courante==slides.size())
				suiv.setEnabled(false);
		}
	}
	
	// Affiche le slide précédent (dépendemment du slide courant)
	public void precedent(){
		if(page_courante<=slides.size())
			suiv.setEnabled(true);
		if(page_courante > 1){
			page_courante--;
			try {
				backgroundImage = ImageIO.read(slides.get(page_courante-1));
			} catch (IOException e) {
				// Auto-generated catch block
				e.printStackTrace();
			}
			if(page_courante==1)
				prec.setEnabled(false);
		}
	}

	// Retour sur l'écran de démarrage
	public void retour(){
		principal.remove(fenetre);
		fenetre.dispose();
		principal.setEnabled(true);
	}
	
	// Retour sur une partie en cours si il y en a une
	public void reprendre(){
		// TODO
		// Faut rajouter des choses
	}
	
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);	

	    // Draw the background image.
	    g.drawImage(backgroundImage, 0, 0, this.getWidth(),this.getHeight(),this);
	}

}
