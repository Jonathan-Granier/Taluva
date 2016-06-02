package IHM;
import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
* This class enable image rotations.
* 
* As it extends BufferedImage, it may be used
* to be displayed on a Component or on a
* Graphics object.
* 
* @author Philippe FERY(philippe.fery@gmail.com)
* @version 1.0
* 
* Created : November 2005
*/

public class RotatingImage extends BufferedImage {
	private BufferedImage sourceImage;
 
	private double angle;
 
	private int numberOfImages;
 
	private int sourceWidth;
 
	private int sourceHeight;
 
	private int rotatedWidth;
 
	private int rotatedHeight;
 
	private HashMap images;
 
	public RotatingImage(BufferedImage sourceImage, boolean preCalc, int numberOfImages) {
		super((int) Math.sqrt(sourceImage.getWidth() * sourceImage.getWidth() + sourceImage.getHeight() * sourceImage.getHeight()),
				(int) Math.sqrt(sourceImage.getWidth() * sourceImage.getWidth() + sourceImage.getHeight() * sourceImage.getHeight()),
				BufferedImage.TYPE_INT_ARGB);
		this.sourceImage = sourceImage;
		this.sourceWidth = sourceImage.getWidth();
		this.sourceHeight = sourceImage.getHeight();
		this.angle = 0;
		this.numberOfImages = numberOfImages;
		this.images = new HashMap();
		if (preCalc)
			preCalculateImages();
 
	}
 
	public RotatingImage(BufferedImage sourceImage, boolean preCalc) {
		this(sourceImage, preCalc, 36);
 
	}
 
	private void preCalculateImages() {
		long begin = System.currentTimeMillis();
		for (int i = 0; i < numberOfImages; i++) {
			setAngle(i * (2 * Math.PI) / numberOfImages);
			rotate();
		}
		long end = System.currentTimeMillis();
		long duration = end - begin;
		//System.out.println("Duration [" + images.keySet().size() + "]:" + duration);
	}
 
	public RotatingImage(BufferedImage sourceImage) {
		this(sourceImage, false);
	}
 
	/**
	 * Rotate the source image
	 * 
	 */
	/*
	public void rotate() {
		double hypotenuseLength = Math.sqrt(sourceWidth * sourceWidth + sourceHeight * sourceHeight);
		int rotatedWidth = (int) hypotenuseLength + 1;
		int rotatedHeight = (int) hypotenuseLength + 1;
 
		BufferedImage rotatedImage = new BufferedImage(rotatedWidth, rotatedHeight, BufferedImage.TYPE_INT_ARGB);
		
		
		BufferedImage bufferedImage;
		
		
		int x = -1;
		int y = -1;
		int sector = (int) (Math.round(angle / ((2 * Math.PI) / (double) numberOfImages)));
		if (images.get(new Integer(sector)) != null) {
			rotatedImage = (BufferedImage) images.get(new Integer(sector));
			boolean hasAlpha = rotatedImage.getColorModel().hasAlpha();
			int transparency = hasAlpha ? Transparency.BITMASK : Transparency.OPAQUE;

			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		
			GraphicsDevice gs = ge.getDefaultScreenDevice();
		
			GraphicsConfiguration gc = gs.getDefaultConfiguration();
			
			bufferedImage = gc.createCompatibleImage(rotatedImage.getWidth(null),rotatedImage.getHeight(null), transparency);
			
			images.put(new Integer(sector), rotatedImage);
		} else {
			for (int xRot = 0; xRot < rotatedWidth; xRot++) {
				for (int yRot = 0; yRot < rotatedHeight; yRot++) {
					Point xyCoord = getSourceXY(xRot - (rotatedWidth / 2), yRot - (rotatedHeight / 2));
					x = xyCoord.x;
					y = xyCoord.y;
					if (x >= 0 && x < sourceWidth && y >= 0 && y < sourceHeight) {
						int rgbColor = sourceImage.getRGB(x, y);
						try {
							rotatedImage.setRGB(xRot, yRot, rgbColor);
						} catch (Exception e) {
							System.out.print(" => Error");
						}
					}
				}
			}
			boolean hasAlpha = rotatedImage.getColorModel().hasAlpha();
			int transparency = hasAlpha ? Transparency.BITMASK : Transparency.OPAQUE;

			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		
			GraphicsDevice gs = ge.getDefaultScreenDevice();
		
			GraphicsConfiguration gc = gs.getDefaultConfiguration();
			
			bufferedImage = gc.createCompatibleImage(rotatedImage.getWidth(null),rotatedImage.getHeight(null), transparency);
			
			images.put(new Integer(sector), rotatedImage);
		}
		Graphics g = getGraphics();
		//IL FAUT LIER LES 2 GRAPHICS
		//Graphics g = bufferedImage.createGraphics();
		g.clearRect(0, 0, rotatedWidth, rotatedHeight);
		g.drawImage(rotatedImage, 0, 0, null);
		
	}
	
*/
	public void rotate() {
		double hypotenuseLength = Math.sqrt(sourceWidth * sourceWidth + sourceHeight * sourceHeight);
		int rotatedWidth = (int) hypotenuseLength + 1;
		int rotatedHeight = (int) hypotenuseLength + 1;
 
		BufferedImage rotatedImage = new BufferedImage(rotatedWidth, rotatedHeight, BufferedImage.TYPE_INT_ARGB);
		
		
		
		
		int x = -1;
		int y = -1;
		int sector = (int) (Math.round(angle / ((2 * Math.PI) / (double) numberOfImages)));
		if (images.get(new Integer(sector)) != null) {
			rotatedImage = (BufferedImage) images.get(new Integer(sector));
		} else {
			for (int xRot = 0; xRot < rotatedWidth; xRot++) {
				for (int yRot = 0; yRot < rotatedHeight; yRot++) {
					Point xyCoord = getSourceXY(xRot - (rotatedWidth / 2), yRot - (rotatedHeight / 2));
					x = xyCoord.x;
					y = xyCoord.y;
					if (x >= 0 && x < sourceWidth && y >= 0 && y < sourceHeight) {
						int rgbColor = sourceImage.getRGB(x, y);
						
						try {
							rotatedImage.setRGB(xRot, yRot, rgbColor);
						} catch (Exception e) {
							System.out.print(" => Error");
						}
					}
				}
			}
			images.put(new Integer(sector), rotatedImage);
		}
		Graphics g = getGraphics();
		((Graphics2D)g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
		g.fillRect(0, 0, rotatedWidth, rotatedHeight);
		//g.clearRect(0, 0, rotatedWidth, rotatedHeight);
		g.drawImage(rotatedImage, 0, 0, null);
	}
	/**
	 * Get the corresponding coordinates in the source image for the specified
	 * coordinates in the rotated image using the following transformation
	 * matrix: | xSrc | = | xRot | | cos(angle) sin(angle) | | | | | * | | |
	 * ySrc | = | yRot | | -sin(angle) cos(angle) |
	 * 
	 * @param xRot
	 *            The x ccordinate in the rotated image
	 * @param yRot
	 *            The y coordinate in the rotated image
	 * 
	 * @return The corresponding (x,y) coordinate in the source image
	 */
	private Point getSourceXY(int xRot, int yRot) {
		int dx = xRot;
		int dy = yRot;
		int x = ((int) (dx * Math.cos(angle) + dy * Math.sin(angle))) + (sourceWidth / 2);
		int y = ((int) (dy * Math.cos(angle) - dx * Math.sin(angle))) + (sourceHeight / 2);
		return new Point(x, y);
	}
 
	/**
	 * Get the corresponding coordinates in the rotated image for the specified
	 * coordinates in the source image using the following transformation
	 * matrix: | xSrc | = | xRot | | cos(angle) -sin(angle) | | | | | * | | |
	 * ySrc | = | yRot | | sin(angle) cos(angle) |
	 * 
	 * @param xRot
	 *            The x ccordinate in the source image
	 * @param yRot
	 *            The y coordinate in the source image
	 * 
	 * @return The corresponding (x,y) coordinate in the rotated image
	 */
	private Point getRotatedXY(int xSrc, int ySrc) {
		int dx = xSrc;
		int dy = ySrc;
		int x = (int) (dx * Math.cos(angle) - dy * Math.sin(angle) + (rotatedWidth / 2));
		int y = (int) (dy * Math.cos(angle) + dx * Math.sin(angle) + (rotatedHeight / 2));
		return new Point(x, y);
	}
 
	/**
	 * Get the angle value
	 * 
	 * @return The rotation angle value
	 */
	public double getAngle() {
		return angle;
	}
 
	/**
	 * Set the angle value
	 * 
	 * @param angle
	 *            The rotation angle value
	 */
	public void setAngle(double angle) {
		this.angle = angle;
		rotate();
	}
 
	/**
	 * Get the source image
	 * 
	 * @return The source image (not rotated)
	 */
	public BufferedImage getSourceImage() {
		return sourceImage;
	}
 
	/**
	 * Get the number of images
	 * 
	 * @return The number of images to generate
	 */
	public int getNumberOfImage() {
		return numberOfImages;
	}
 
	/**
	 * Set the number of images
	 * 
	 * @param numberOfImages
	 *            The number of images to generate
	 */
	public void setNumberOfImage(int numberOfImages) {
		this.numberOfImages = numberOfImages;
	}
 
}