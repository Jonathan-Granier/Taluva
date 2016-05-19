package Ecouteur;

import org.lwjgl.util.vector.Vector2f;

import entities.GraphicConstruction;
import entities.GraphicConstruction.GraphicType;
import gui.Button;

public class ButtonConstruction extends Button {

	public GraphicType type = GraphicType.NULL;
	private String label;
	private GraphicConstruction Construction;
	
	public ButtonConstruction(int textureId, Vector2f position, Vector2f dimension,String label,GraphicConstruction Construction) {
		super(textureId, position, dimension);
		this.label = label;
		this.Construction = Construction;
	}

	@Override
	protected void action() {
		if(label.equals("tower")){
			type = GraphicType.TOWER;
			Construction.setType(GraphicType.TOWER);
		}
		else if(label.equals("hut")){
			type = GraphicType.HUT;
			Construction.setType(GraphicType.HUT);
		}
		else if(label.equals("temple")){
			type = GraphicType.TEMPLE;
			Construction.setType(GraphicType.TEMPLE);
		}
		else{
			type = GraphicType.NULL;
			Construction.setType(GraphicType.NULL);
		}
		Construction.setObject3d();
	}
	
	
}
