package IHM;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class BoutonImageFond extends JButton implements MouseListener{
	private static final long serialVersionUID = 1L;
	private Image imageActiver;
	private Image imageDesactiver;
	private Image imageCliquer;
	private int ImageEnCours; 
	// 1 pour imageActiver
	// 2 pour imageDesactiver
	// 3 pour imageCliquer
	
	// private String imageName;
	     
	    //Un constructeur pour choisir plus simplement l'image
	    public BoutonImageFond(String imageNomActiver, String imageNomDesactiver, String imageNomCliquer) {
	    	imageActiver = new ImageIcon(imageNomActiver).getImage();
	    	imageDesactiver = new ImageIcon(imageNomDesactiver).getImage();
	    	imageCliquer = new ImageIcon(imageNomCliquer).getImage();
	    	this.addMouseListener(this);
	    }
	     
	    //On doit redéfinir la méthode paintComponent() pour les composant swing !!! et paint() pour awt
	    @Override
	    public void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        switch (ImageEnCours)
	        {
	        case 1:
	        	if (imageActiver == null) return;
	 	        	g.drawImage(imageActiver, 0, 0, getWidth(), getHeight(), this);
	        	break;
	        case 2:
	        	if (imageDesactiver == null) return;
	 	        	g.drawImage(imageDesactiver, 0, 0, getWidth(), getHeight(), this);
	        	break;
	        case 3:
	        	if (imageCliquer == null) return;
	 	        	g.drawImage(imageCliquer, 0, 0, getWidth(), getHeight(), this);
	        	break;
	        default:
	        	break;
	        }
	       
	     
	    }
	    
	    public void Activer(boolean activer)
	    {
	    	this.setEnabled(activer);
	    	if(activer)
	    		ImageEnCours = 1;
	    	else
	    		ImageEnCours = 2;
	    	this.repaint();
	    }
		@Override
		public void mouseClicked(MouseEvent arg0) {}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			//TODO
		}

		@Override
		public void mouseExited(MouseEvent arg0) {}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			ImageEnCours = 3;
			this.repaint();
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {}
		{
			ImageEnCours = 1;
			this.repaint();
		}
}
