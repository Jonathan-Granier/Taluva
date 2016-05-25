package entities;

import org.lwjgl.util.vector.Vector3f;

import loaders.Loader;
import terrain.Case;
import terrain.Case.Couleur_Joueur;

public class GraphicConstruction {

	private static final float HEIGHT_OF_TILE = 1;

	public enum GraphicType {
		HUT, TOWER, TEMPLE, NULL;
	}

	private GraphicType type;

	private Object3D object3d;
	private Object3D hut;
	private Object3D temple;
	private Object3D tower;
	private Vector3f colour;
	private float height;
	private Loader loader;

	public GraphicConstruction(GraphicType type, Vector3f colour, Loader loader) {
		this.hut =  new Object3D("Hut", loader, new Vector3f(0, 0, 0), 0, 0, 0, 0.12f);
		this.temple = new Object3D("Temple", loader,true, new Vector3f(0, 0, 0), 0, 0, 0, 0.12f);
		this.tower = new Object3D("Tower", loader, new Vector3f(0, 0, 0), 0, 0, 0, 0.12f);
		switch (type) {
		case HUT:
			this.object3d = hut;
			break;
		case TOWER:
			this.object3d = tower;
			break;
		case TEMPLE:
			this.object3d = temple;
			break;
		default:
			System.out.println("Unknow construction type");
		}
		this.height = 0;
		this.colour = colour;
		this.loader = loader;
	}

	public Case.Type_Batiment getType_Batiment() {
		switch (type) {
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

	public void setType(Case.Type_Batiment type){
		switch(type){
		case HUTTE:
			this.type = GraphicType.HUT; 
			break;
		case TOUR:
			this.type = GraphicType.TOWER; 
			break;
		case TEMPLE:
			this.type = GraphicType.TEMPLE;
			break;
		case VIDE:
			this.type = GraphicType.NULL;
			break;
		default:
			System.out.println("Unknow construction type");	
		}
		this.setObject3d();
	}
	
	public GraphicConstruction(GraphicConstruction gc) {
		this.object3d = new Object3D(gc.getObject3d());
		this.type = gc.getType();
		this.colour = gc.getColour();
		this.height = gc.height;
	}

	public void setType(GraphicType type) {
		this.type = type;
	}

	public GraphicType getType() {
		return type;
	}

	public void setObject3d() {
		switch (type) {
		case HUT:
			this.object3d = hut;
			break;
		case TOWER:
			this.object3d = tower;
			break;
		case TEMPLE:
			this.object3d = temple;
			break;
		default:
			System.out.println("Unknow construction type");
		}
	}

	public Object3D getObject3d() {
		return object3d;
	}

	public void setColour(Couleur_Joueur colour) {
		switch(colour){
			case ROUGE:
				this.colour = new Vector3f(1,0,0);
			break;
			case JAUNE:
				this.colour = new Vector3f(1,1,0);
			break;
			case BLANC:
				this.colour = new Vector3f(0.5f,0.5f,0.5f);
			break;
			case MARRON:
				this.colour = new Vector3f(0.6f,0.3f,0);
			break;
			case NEUTRE:
				this.colour = new Vector3f(0,0,0);
			break;
			default :
				System.out.println("Unknow Color");
		}
		this.object3d.setColor(this.colour);
	}

	public Vector3f getColour() {
		return colour;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public void increaseHeight(int level) {
		height = level * HEIGHT_OF_TILE;
	}

	public void decreaseHeight() {
		if (height > 0) {
			height -= HEIGHT_OF_TILE;
		}
	}

}
