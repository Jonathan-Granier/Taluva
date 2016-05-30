package Menu;

import java.awt.BorderLayout;
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
	private JPanel prec_suiv;
	private JPanel acc_rep;
	private int page_courante;
	
	public Regles(JFrame frame) throws IOException{
		slides = new ArrayList<File>();
		for(int i=0;i<8;i++){
			slides.add(new File("../Images/Images-Regles/Regles_p"+(i+1)+".jpg"));
		}
		backgroundImage = ImageIO.read(slides.get(0));
		page_courante = 1;		
		
		fenetre = frame;
		this.setLayout(new BorderLayout());
		
		prec_suiv = new JPanel();
		prec_suiv.setOpaque(false);
		acc_rep = new JPanel();
		acc_rep.setOpaque(false);
		
		prec = new JButton("Précedent");
		prec.addActionListener(new Ecouteur_boutons_regles("Précedent",this));
		prec.setEnabled(false);
		
		suiv = new JButton("Suivant");
		suiv.addActionListener(new Ecouteur_boutons_regles("Suivant",this));

		accueil = new JButton("Accueil");
		accueil.addActionListener(new Ecouteur_boutons_regles("Suivant",this));
		
		reprendre = new JButton("Reprendre");
		reprendre.addActionListener(new Ecouteur_boutons_regles("Suivant",this));

		prec_suiv.add(prec);
		prec_suiv.add(suiv);

		acc_rep.add(accueil);
		acc_rep.add(reprendre);
		this.add(prec_suiv,BorderLayout.SOUTH);
		//this.add(acc_rep,BorderLayout.NORTH);
	}
	
	public void suivant(){
		if(page_courante>=1)
			prec.setEnabled(true);
		if(page_courante < slides.size()){
			page_courante++;
			try {
				backgroundImage = ImageIO.read(slides.get(page_courante-1));
			} catch (IOException e) {
				// TODO Auto-generated catch block
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(page_courante==1)
				prec.setEnabled(false);
		}
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
