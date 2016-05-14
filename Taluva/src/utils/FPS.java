package utils;

import javax.swing.JFrame;

import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;

public class FPS {
	

    /** time at last frame */
    static long lastFrame;
     
    /** frames per second */
    static int fps;
    /** last fps time */
    static long lastFPS;
    
    static JFrame frame;
    
    public static void start(JFrame f){
    	getDelta(); // call once before loop to initialise lastFrame
        lastFPS = getTime(); // call before loop to initialise fps timer
        frame = f;
    }
    
    public static long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }
   
    public static int getDelta() {
        long time = getTime();
        int delta = (int) (time - lastFrame);
        lastFrame = time;
      
        return delta;
    }
    
    public static void updateFPS() {
        if (getTime() - lastFPS > 1000) {
        	frame.setTitle("Taluva: " + fps);
            fps = 0;
            lastFPS += 1000;
        }
        fps++;
    }
	
}
