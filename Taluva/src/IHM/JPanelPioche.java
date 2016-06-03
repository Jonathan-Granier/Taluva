package IHM;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import entities.GraphicTile;

//Attention : Il existe un probleme de synchronisation si on fait une rotation ici et une rotation avec le clique droit rapidement


public class JPanelPioche extends JPanel{
	public static final double PI2 = 2 * Math.PI;
	private static final long serialVersionUID = 1L;
	private static RotatingImage Tuile;
	private BufferedImage imageTuile;
	private double angle;
	private boolean NouvelleTuile;
	
	public JPanelPioche()
	{
		this.setOpaque(false);
		
	}
	
	
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //TODO
        Graphics2D g2d = (Graphics2D) g.create();
        
        int ratio_Width = getWidth()/6;
        int ratio_Height = getHeight()/6;

        g2d.drawImage(Tuile,ratio_Width /2  , ratio_Height/2, getWidth()-ratio_Width, getHeight()-ratio_Height, this);
	}
	
	public void setTuile()
	{
		if(NouvelleTuile)
		{
			String nomTuileFile = "Assets/Texture/Tile/"+GraphicTile.getTexture()+".png";
			try{
				File TuileFile = new File(nomTuileFile);
				imageTuile = ImageIO.read(TuileFile);
			}catch (IOException e) {
				// Auto-generated catch block
				e.printStackTrace();			
			}
			Tuile = new RotatingImage(imageTuile,false,6);
			Tuile.setAngle(0);
			NouvelleTuile = false;
		}
		else
			Tuile.setAngle(angle);
			
		
		
	/*	boolean hasAlpha = imageTuile.getColorModel().hasAlpha();
		int transparency = hasAlpha ? Transparency.BITMASK : Transparency.OPAQUE;
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	
		GraphicsDevice gs = ge.getDefaultScreenDevice();
	
		GraphicsConfiguration gc = gs.getDefaultConfiguration();
		
		bufferedImage = gc.createCompatibleImage(imageTuile.getWidth(null),
		imageTuile.getHeight(null), transparency);
		if (bufferedImage == null) { 
			// Si ça rate , on met un model par defautl
			int type = hasAlpha ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB;
			bufferedImage = new BufferedImage(imageTuile.getWidth(null), imageTuile.getHeight(null), type);
		}*/
		
		//this.repaint();
	}
	
	
	
	public void RotationHoraire() 
	{
		Tuile.setAngle(Tuile.getAngle()+ Math.PI/3);
		angle = Tuile.getAngle();
		this.repaint();
	}
	
	public void RotationAntiHoraire()
	{
		Tuile.setAngle(Tuile.getAngle()-Math.PI/3);
		angle = Tuile.getAngle();
		this.repaint();
	}
	
	
	public void setAngle(double angle)
	{
		Tuile.setAngle(angle);
		angle = Tuile.getAngle();
		this.repaint();
	}
	
	public void cleanAngle()
	{
		NouvelleTuile = true;
		angle = 0;
	}

}
