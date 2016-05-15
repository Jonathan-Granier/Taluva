package test;

import org.lwjgl.util.vector.Vector2f;

import entities.GraphicConstruction.GraphicType;
import gui.Button;

public class MyButton extends Button {

	public GraphicType type = GraphicType.NULL;
	
	public MyButton(int textureId, Vector2f position, Vector2f dimension) {
		super(textureId, position, dimension);
	}

	@Override
	protected void action() {
		type = GraphicType.TOWER;
	}
	
	
}
