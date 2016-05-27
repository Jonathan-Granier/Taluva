package utils;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import entities.GraphicTile;
import gui.Button;

public class InputHandler {

	public enum inputType {
		LONG,INSTANT,NONE;
	}
	
	private static final long TIME = 200000; // En nano seconde
	private static boolean isMouseReleased = true;
	private static long time = 0;
	private static inputType type = inputType.NONE;
	private static int mouseEvent = -1;
	
	private static int getInput(){
		int button = -1;
		if(Mouse.isButtonDown(0))
			button = 0;
		else if(Mouse.isButtonDown(1))
			button = 1;
			
		return button;
	}
	
    private static long getTime() {
        return System.nanoTime();
    }
	
    private static boolean noInput(){
    	return !(Mouse.isButtonDown(0) || Mouse.isButtonDown(1));
    }
    
    public static boolean reset(boolean b){
    	if(b){
			type = inputType.NONE;
    	}
    	return b;
    }
    
	public static inputType isButtonDown(int event){
		if(type != inputType.INSTANT){
			if(getInput() == event && mouseEvent == -1)
				mouseEvent = event;
			if(getInput() == mouseEvent && isMouseReleased && time == 0 && mouseEvent != -1){
				isMouseReleased = false;
				time = getTime()/1000;
			}
			
			if(getInput() == mouseEvent && mouseEvent != -1 && time+TIME<getTime()/1000)
				return inputType.LONG;
			
			if(noInput() && mouseEvent==event){
				mouseEvent = -1;
				isMouseReleased = true;
				long temp = time+TIME ;
				if(time+TIME>getTime()/1000 && time!=0 ){
					time = 0;
					type = inputType.INSTANT;
					return inputType.INSTANT;
				}
	
				time = 0;
			}
		}
		return type;
	}
	
	public static void isKeyDown(GraphicTile tile){
		

	    
	}
	
}
