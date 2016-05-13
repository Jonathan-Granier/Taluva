package utils;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import entities.GraphicTile;

public class InputHandler {

	private static boolean isMouseReleased = true;
	private static boolean isKeyReleased = true;
	
	private static int getInput(){
		int button = -1;
		if(Mouse.isButtonDown(0))
			button = 0;
		else if(Mouse.isButtonDown(1))
			button = 1;
		
		if(button == -1)
			isMouseReleased = true;
		return button;
	}
	
	public static boolean isButtonDown(int event){
		if(getInput() == event && isMouseReleased){
			isMouseReleased = false;
			return true;
		}
		return false;
	}
	
	public static void isKeyDown(GraphicTile tile){
		
	    while (Keyboard.next()) {
	        if (Keyboard.getEventKeyState()) {
	            if (Keyboard.getEventKey() == Keyboard.KEY_W) {
	            	tile.increaseHeight();
	            }
	            if (Keyboard.getEventKey() == Keyboard.KEY_X) {
	            	tile.decreaseHeight();
	            }
	        }
	    }
	    
	}
	
}
