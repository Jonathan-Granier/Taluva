package gui;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

public abstract class Button {

	private Texture gui;
	private Vector2f position;
	private Vector2f dimension;
	private boolean isPressed;
	private static boolean gameBlocked = false;
	
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
		if(mouseX>=position.x && mouseX<=position.x+dimension.x && mouseY>=position.y && mouseY<=position.y+dimension.y && !gui.isGrey()){
			gui.setHover(true);
			if(Mouse.isButtonDown(0)){
				gui.setClicked(true);
				if(!isPressed){
					isPressed = true;
					action();
				}
			}
		}else{
			gui.setClicked(false);
			gui.setHover(false);
			isPressed = false;
		}
		
		
		
	}
	
	public boolean isGrey() {
		return gui.isGrey();
	}

	public void setGrey(boolean grey) {
		gui.setGrey(grey);
	}

	public static void setGameBlocked(boolean gameBlocked) {
		Button.gameBlocked = gameBlocked;
	}

	public static boolean isGameBlocked() {
		return gameBlocked;
	}

	public boolean isPressed() {
		return isPressed;
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
		this.gui.setPosition(position);
	}

	public Vector2f getDimension() {
		return dimension;
	}

	public void setDimension(Vector2f dimension) {
		this.dimension = dimension;
		this.gui.setDimension(dimension);
	}
	
}
