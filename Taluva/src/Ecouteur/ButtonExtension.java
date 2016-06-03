package Ecouteur;

import java.awt.Point;

import org.lwjgl.util.vector.Vector2f;

import Moteur.Moteur;
import entities.GraphicConstruction;
import gui.Button;
import terrain.Case;

public class ButtonExtension extends Button {

	private Case.Type type;
	private static Point p;
	private Moteur m;
	private static boolean clicked;
	//private GraphicConstruction Construction;
	
	public ButtonExtension(int textureId, Vector2f position, Vector2f dimension,Case.Type type,Moteur m,GraphicConstruction Construction) {
		super(textureId, position, dimension);
		this.type = type;
		this.m = m;
		//this.Construction = Construction;
	}

	@Override
	protected void action() {
		clicked = true;
		m.etendre_cite(p,type);
		//Construction.setColour(m.get_Jcourant().getCouleur());
	}

	public static boolean isClicked() {
		if(clicked){
			clicked = false;
			return true;
		}
		return false;
	}

	
	public static void setP(Point p) {
		ButtonExtension.p = p;
	}

}
