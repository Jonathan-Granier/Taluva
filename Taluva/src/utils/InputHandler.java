package utils;

import org.lwjgl.input.Mouse;

public class InputHandler {

	private static boolean isReleased = true;
	
	private static int getInput(){
		int button = -1;
		if(Mouse.isButtonDown(0))
			button = 0;
		else if(Mouse.isButtonDown(1))
			button = 1;
		
		if(button == -1)
			isReleased = true;
		return button;
	}
	
	public static boolean isButtonDown(int event){
		if(getInput() == event && isReleased){
			isReleased = false;
			return true;
		}
		return false;
	}
	
}
