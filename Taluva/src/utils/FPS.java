package utils;

import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;

public class FPS {
	

    /** time at last frame */
    static long lastFrame;
     
    /** frames per second */
    static int fps;
    /** last fps time */
    static long lastFPS;
    
    public static void start(){
    	getDelta(); // call once before loop to initialise lastFrame
        lastFPS = getTime(); // call before loop to initialise fps timer
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
        	System.out.println(fps);
        	Display.setTitle("Engine: " + fps);
            fps = 0;
            lastFPS += 1000;
        }
        fps++;
    }
	
}
