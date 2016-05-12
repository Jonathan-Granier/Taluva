package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;


public class Window {
	
	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	private static final int FPS_CAP = 120;
	
	public static void createDislay(){
		
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH,HEIGHT));
			Display.create();
			Display.setTitle("Engine");
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
		
	}
	
	public static void updateDisplay(){
		
		Display.sync(FPS_CAP);
		Display.update();
		
	}
	
	public static void closeDisplay(){
		
		Display.destroy();
		
	}
	

}
