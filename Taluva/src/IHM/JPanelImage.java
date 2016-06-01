package IHM;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/*
 * Gere des JPanel avec des image qui change selon les evenements du jeu (Activer/Desactiver)
 */

public class JPanelImage extends JPanel{

	private static final long serialVersionUID = 1L;
	private Image imageActiver;
	private Image imageDesactiver;
	private int ImageEnCours; 
	// 1 pour imageActiver
	// 2 pour imageDesactiver
	
	public JPanelImage(String imageNomActiver, String imageNomDesactiver)
	{
		try{
			imageActiver = new ImageIcon(imageNomActiver).getImage();
	    	imageDesactiver = new ImageIcon(imageNomDesactiver).getImage();
		}catch (Exception e) {
				// Auto-generated catch block
				e.printStackTrace();
		}	
		this.setOpaque(false);
		
	}
	
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        switch (ImageEnCours)
        {
        case 1:
        	if (imageActiver == null) 
        		{
        			return;
        		}
 	        	g.drawImage(imageActiver, 0, 0, getWidth(), getHeight(), this);
        	break;
        case 2:
        	if (imageDesactiver == null) 
        		{
        			return;
        		}
 	        	g.drawImage(imageDesactiver, 0, 0, getWidth(), getHeight(), this);
        	break;
        default:
        	break;
        }
    }
	
	public void setEnabled(boolean activer)
	{
		if(activer)
			ImageEnCours = 1;
		else
			ImageEnCours = 2;
		this.repaint();
			
	}
}
