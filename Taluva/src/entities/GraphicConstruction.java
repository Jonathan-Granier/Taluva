package entities;

import org.lwjgl.util.vector.Vector3f;

import Loaders.Loader;
import main.Case;

public class GraphicConstruction {
	
	public enum GraphicType{
		HUT,
		TOWER,
		TEMPLE,
		NULL;
	}
	
	private GraphicType type;
	
	private Object3D object3d;
	private Vector3f colour;
	
	public GraphicConstruction(GraphicType type,Vector3f colour, Loader loader){
		switch(type){
			case HUT:
				this.object3d = new Object3D("","Hut",loader,new Vector3f(0,0,0),0,0,0,0.5f);
			break;
			case TOWER:
				this.object3d = new Object3D("","Tower",loader,new Vector3f(0,0,0),0,0,0,0.5f);
			break;
			case TEMPLE:
				this.object3d = new Object3D("","Temple",loader,new Vector3f(0,0,0),0,0,0,0.5f);
			break;
			default:
				System.out.println("Unknow construction type");
		}
		this.colour = colour;
		
	}

	public Case.Type_Batiment getType_Batiment(){
		switch(type){
			case HUT:
				return Case.Type_Batiment.HUTTE;
			case TOWER:
				return Case.Type_Batiment.TOUR;
			case TEMPLE:
				return Case.Type_Batiment.TEMPLE;
			case NULL:
				return Case.Type_Batiment.VIDE;
			default:
				System.out.println("Unknow construction type");	
		}
		return Case.Type_Batiment.VIDE;
	}
	
	public GraphicConstruction(GraphicConstruction gc){
		this.object3d = new Object3D(gc.getObject3d());
		this.type = gc.getType();
		this.colour = gc.getColour();
	}
	
	public GraphicType getType() {
		return type;
	}

	public Object3D getObject3d() {
		return object3d;
	}

	public Vector3f getColour() {
		return colour;
	}

}
