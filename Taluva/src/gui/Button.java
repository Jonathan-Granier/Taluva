package gui;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

public abstract class Button {

	private Texture gui;
	private Vector2f position;
	private Vector2f dimension;
	private boolean isPressed;
	
	public Button(int textureId, Vector2f position, Vector2f dimension){
		this.gui = new Texture(textureId,position,dimension);
		this.position = position;
		this.dimension = dimension;
		this.isPressed = false;
	}
	
	public Texture getTexture(){
		return gui;
	}
	
	protected abstract void action();
	
	public void update(){
		int mouseX = Mouse.getX();
		int mouseY = Display.getHeight() - Mouse.getY();
		if(Mouse.isButtonDown(0)){
			if(mouseX>position.x && mouseX<position.x+dimension.x && mouseY>position.y && mouseY<position.y+dimension.y && !isPressed){
				isPressed = true;
				action();
			}
		}else
			isPressed = false;
	
	}
	
}
