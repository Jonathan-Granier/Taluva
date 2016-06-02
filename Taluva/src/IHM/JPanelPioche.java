package IHM;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


import entities.GraphicTile;

//Attention : Il existe un probleme de synchronisation si on fait une rotation ici et une rotation avec le clique droit rapidement


public class JPanelPioche extends JPanel{
	public static final double PI2 = 2 * Math.PI;
	private static final long serialVersionUID = 1L;
	private static RotatingImage Tuile;
	private Image imageActiver;
	private Image imageDesactiver;
	private Graphics graphics;
	private BufferedImage imageTuile;
	private BufferedImage bufferedImage;
	private int width,height;
	private double angle;
	
	public JPanelPioche(String imageNomActiver, String imageNomDesactiver)
	{
		this.setOpaque(false);
		imageActiver = new ImageIcon(imageNomActiver).getImage();
    	imageDesactiver = new ImageIcon(imageNomDesactiver).getImage();
		angle = 0;
	}
	
	
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //TODO
        Graphics2D g2d = (Graphics2D) g.create();
       // g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.40f )); 
       
       width = getWidth();
       height = getHeight();
       // g2d.drawImage(imageActiver, 0, 0, getWidth(), getHeight(), this);
       // g2d.drawImage(imageActiver,100, 100, getWidth(), getHeight(), this);
//        graphics
//        g2d.drawImage(graphics, 0, 0, getWidth(), getHeight(), this);
        
        int ratio_Width = getWidth()/6;
        int ratio_Height = getHeight()/6;

        g2d.drawImage(Tuile,ratio_Width /2  , ratio_Height/2, getWidth()-ratio_Width, getHeight()-ratio_Height, this);
       // Angle(g2d);
        
        //setTuile(g2d);
	}
	
	public void setTuile()
	{
		String nomTuileFile = "Assets/Texture/Tile/"+GraphicTile.getTexture()+".png";
		try{
			File TuileFile = new File(nomTuileFile);
			imageTuile = ImageIO.read(TuileFile);
		}catch (IOException e) {
			// Auto-generated catch block
			e.printStackTrace();			
		}
		boolean hasAlpha = imageTuile.getColorModel().hasAlpha();
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
		}
		Tuile = new RotatingImage(imageTuile,false,6);
		Tuile.setAngle(0);
		this.repaint();
	}
	
	
	
	public void RotationHoraire() 
	{
		Tuile.setAngle(Tuile.getAngle()+ Math.PI/3);
		//angle += PI2 * 30/360;
		this.repaint();
	}
	
	public void RotationAntiHoraire()
	{
		Tuile.setAngle(Tuile.getAngle()-Math.PI/3);
		//angle  -= PI2 * 30/360;
		this.repaint();
	}
	
	
	public void setAngle(double angle)
	{
		Tuile.setAngle(angle);
		this.repaint();
	}

}