package Ecouteur;

import org.lwjgl.util.vector.Vector2f;

import Moteur.Moteur;
import entities.GraphicConstruction;
import entities.GraphicConstruction.GraphicType;
import gui.Button;

public class ButtonConstruction extends Button {

	public GraphicType type = GraphicType.NULL;
	private String label;
	private GraphicConstruction Construction;
	private Moteur moteur;
	private static boolean pick;
	
	public ButtonConstruction(int textureId, Vector2f position, Vector2f dimension,String label,GraphicConstruction Construction,Moteur moteur) {
		super(textureId, position, dimension);
		this.label = label;
		this.Construction = Construction;
		this.moteur = moteur;
	}

	@Override
	protected void action() {
		pick = true;
		if(label.equals("tower")){
			type = GraphicType.TOWER;
			moteur.select_tour();
			Construction.setType(GraphicType.TOWER);
		}
		else if(label.equals("hut")){
			type = GraphicType.HUT;
			moteur.select_hutte();
			Construction.setType(GraphicType.HUT);
		}
		else if(label.equals("temple")){
			type = GraphicType.TEMPLE;
			moteur.select_temple();
			Construction.setType(GraphicType.TEMPLE);
		}
		else{
			type = GraphicType.NULL;
			Construction.setType(GraphicType.NULL);
		}
		Construction.setObject3d();
	}

	public static boolean isPick() {
		return pick;
	}

	public static void setPick(boolean pick) {
		ButtonConstruction.pick = pick;
	}
	
}
