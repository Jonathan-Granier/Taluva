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
public class Regles extends JComponent {
	private Image backgroundImage;
	private ArrayList<File> slides;
	private JButton prec,suiv;
	private JButton accueil,reprendre;
	private JFrame fenetre;
	private JPanel prec_suiv;
	private JPanel acc_rep;
	private int page_courante;
	
	public Regles(JFrame frame){
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
		this.setLayout(new BorderLayout());
		
		prec_suiv = new JPanel();
		prec_suiv.setOpaque(false);
		prec_suiv.setPreferredSize(new Dimension(fenetre.getWidth()/10,fenetre.getHeight()/10));
		
		acc_rep = new JPanel();
		acc_rep.setLayout(new GridLayout(2,1));
		acc_rep.setOpaque(false);
		JPanel acc_rep2 = new JPanel();
		acc_rep2.setLayout(new BorderLayout());
		acc_rep2.add(acc_rep,BorderLayout.SOUTH);
		acc_rep2.setOpaque(false);
		acc_rep.setPreferredSize(new Dimension(fenetre.getWidth()/10,fenetre.getHeight()/10));
		
		prec = new JButton("Précedent");
		prec.addActionListener(new Ecouteur_boutons_regles("Précedent",this));
		prec.setPreferredSize(new Dimension(fenetre.getWidth()/10,fenetre.getHeight()/15));
		prec.setFont(new Font("Précédent", Font.BOLD+Font.ITALIC,fenetre.getWidth()/120));
		prec.setEnabled(false);
		
		suiv = new JButton("Suivant");
		suiv.addActionListener(new Ecouteur_boutons_regles("Suivant",this));
		suiv.setPreferredSize(new Dimension(fenetre.getWidth()/10,fenetre.getHeight()/15));
		suiv.setFont(new Font("Suivant", Font.BOLD+Font.ITALIC,fenetre.getWidth()/120));
		
		
		accueil = new JButton("Accueil");
		accueil.addActionListener(new Ecouteur_boutons_regles("Accueil",this));
		accueil.setFont(new Font("Accueil", Font.BOLD+Font.ITALIC,fenetre.getWidth()/120));
		
		reprendre = new JButton("Reprendre");
		reprendre.addActionListener(new Ecouteur_boutons_regles("Reprendre",this));
		reprendre.setFont(new Font("Reprendre", Font.BOLD+Font.ITALIC,fenetre.getWidth()/120));
		
		prec_suiv.add(prec);
		prec_suiv.add(suiv);		
		acc_rep.add(accueil);
		acc_rep.add(reprendre);
		this.add(prec_suiv,BorderLayout.SOUTH);
		this.add(acc_rep2,BorderLayout.EAST);
	}
	
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

	public void accueil(){
		this.setVisible(false);
		fenetre.remove(this);
		fenetre.add(new Menu_Demarrage(fenetre));
	}
	
	public void reprendre(){
		// TODO
		// Faut rajouter des choses
	}
	
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);	

	    // Draw the background image.
	    g.drawImage(backgroundImage, 0, 0, this.getWidth(),this.getHeight(),this);
	}

	public int getPage_courante() {
		return page_courante;
	}

}
