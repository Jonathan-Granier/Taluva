package IHM;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class JPanelFrise extends JPanel{

	private static final long serialVersionUID = 1L;
	private Image imageActiver;
	private Image imageDesactiver;
	private Image imageEnCours;
	private int ImageEnCours; 
	// 1 pour imageActiver
	// 2 pour imageDesactiver
	// 3 pour imageEncours
	public JPanelFrise(String imageNomActiver, String imageNomDesactiver,String imageNomEnCours)
	{
		try{
			imageActiver = new ImageIcon(imageNomActiver).getImage();
	    	imageDesactiver = new ImageIcon(imageNomDesactiver).getImage();
	    	imageEnCours = new ImageIcon(imageNomEnCours).getImage();
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
        case 3:
        	if (imageEnCours == null) 
        		{
        			return;
        		}
 	        	g.drawImage(imageEnCours, 0, 0, getWidth(), getHeight(), this);
        	break;
        default:
        	break;
        }
    }
	
	public void setEtat(int e)
	{
		ImageEnCours = e;
		this.repaint();
			
	}
}
