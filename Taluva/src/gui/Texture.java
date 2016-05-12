package gui;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

public class Texture {
	
	private int textureId;
	private Vector2f position;
	private Vector2f scale;
	
	public Texture(int textureId, Vector2f position, Vector2f dimension) {
		this.textureId = textureId;
		float x = (2f * (position.x + dimension.x/2)) / Display.getWidth() - 1;
		float y = (2f * (Display.getHeight() - position.y - dimension.y/2)) / Display.getHeight() -1;
		this.position = new Vector2f(x,y);
		this.scale = new Vector2f(dimension.x/Display.getWidth(),dimension.y/Display.getHeight());
	}

	public int getTextureId() {
		return textureId;
	}

	public Vector2f getPosition() {
		return position;
	}

	public Vector2f getScale() {
		return scale;
	}
	

}
