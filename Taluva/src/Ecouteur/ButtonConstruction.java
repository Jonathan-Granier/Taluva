package Ecouteur;

import java.awt.Point;

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
	private static Point p;
	
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
			moteur.select_tour();
		}
		else if(label.equals("hut")){
			moteur.select_hutte();
		}
		else if(label.equals("temple")){
			moteur.select_temple();
		}
		moteur.placer_batiment(p);
		System.out.println(p);
	}

	public static boolean isPick() {
		return pick;
	}

	public static void setPick(boolean pick) {
		ButtonConstruction.pick = pick;
	}
	
	public static void setPoint(Point point){
		p = point;
	}
	
}
