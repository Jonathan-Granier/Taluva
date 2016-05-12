package test;

import org.lwjgl.util.vector.Vector2f;

import gui.Button;

public class MyButton extends Button {

	String message;
	
	public MyButton(int textureId, Vector2f position, Vector2f dimension, String label) {
		super(textureId, position, dimension);
		this.message = label;
	}

	@Override
	protected void action() {
			System.out.println("Button " + message + " pressed");
			
	}
	
	
}
