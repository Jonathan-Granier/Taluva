package gui;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

public class Texture {
	
	private int textureId;
	private Vector2f position;
	private Vector2f position2D;
	private Vector2f scale;
	private boolean hover;
	private boolean clicked;
	private Vector2f dimension;
	private boolean grey;
	
	public Texture(int textureId, Vector2f position, Vector2f dimension) {
		this.textureId = textureId;
		float x = (2f * (position.x - 10 + dimension.x/2)) / Display.getWidth() - 1;
		float y = (2f * (Display.getHeight() - position.y - dimension.y/2)) / Display.getHeight() -1;
		this.position = new Vector2f(x,y);
		this.scale = new Vector2f(dimension.x/Display.getWidth(),dimension.y/Display.getHeight());
		this.hover = false;
		this.clicked = false;
		this.dimension = dimension;
		this.grey = false;
	}

	public boolean isGrey() {
		return grey;
	}

	public void setGrey(boolean grey) {
		this.grey = grey;
	}
	
	public int getTextureId() {
		return textureId;
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setScale(Vector2f scale){
		this.scale = scale;
	}
	
	public Vector2f getScale() {
		return scale;
	}

	public boolean isHover() {
		return hover;
	}

	public void setHover(boolean hover) {
		this.hover = hover;
	}

	public boolean isClicked() {
		return clicked;
	}

	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}
	
	public void setPosition(Vector2f position) {
		this.position2D = position;
		float x = (2f * (position.x - 10 + dimension.x/2)) / Display.getWidth() - 1;
		float y = (2f * (Display.getHeight() - position.y - dimension.y/2)) / Display.getHeight() -1;
		this.position = new Vector2f(x,y);
	}
	
	public Vector2f getDimension() {
		return dimension;
	}
	
	public void setDimension(Vector2f dimension) {
		this.dimension = dimension;
	}
	
}
