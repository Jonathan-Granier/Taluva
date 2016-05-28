package entities;

import org.lwjgl.util.vector.Vector3f;

import loaders.Loader;
import terrain.Case;
import terrain.Case.Couleur_Joueur;

public class GraphicConstruction {

	private static final float HEIGHT_OF_TILE = 1.5f;

	public enum GraphicType {
		HUT, TOWER, TEMPLE, NULL;
	}

	private GraphicType type;

	private Vector3f position;
	private Object3D object3d;
	private static Object3D hut;
	private static Object3D temple;
	private static Object3D tower;
	private boolean allow;
	private Vector3f colour;
	private float height;

	public GraphicConstruction(GraphicType type, Vector3f colour) {
		switch (type) {
		case HUT:
			this.object3d = new Object3D(hut);
			break;
		case TOWER:
			this.object3d = new Object3D(tower);
			break;
		case TEMPLE:
			this.object3d = new Object3D(temple);
			break;
		default:
			System.out.println("Unknow construction type");
		}
		this.height = 0;
		this.colour = new Vector3f(colour);
		this.allow = true;
		this.position = new Vector3f(0,0,0);
	}

	public static void setUp(Loader loader){
		hut =  new Object3D("Hut", loader,true, new Vector3f(0, 0, 0), 0, 0, 0, 0.12f,true);
		temple = new Object3D("Temple", loader,true, new Vector3f(0, 0, 0), 0, 0, 0, 0.12f,true);
		tower = new Object3D("Tower", loader,true, new Vector3f(0, 0, 0), 0, 0, 0, 0.12f,true);
	}
	
	public void init(){
		this.object3d = hut;
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
		this.colour = new Vector3f(gc.getColour());
		this.height = gc.height;
		this.allow = true;
		this.position = new Vector3f(gc.getPosition());
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
		this.allow = true;
	}

	public Object3D getObject3d() {
		return object3d;
	}

	public void setColour(Couleur_Joueur colour) {
		switch(colour){
			case VERT:
				this.colour = new Vector3f(0,0.6f,0);
			break;
			case JAUNE:
				this.colour = new Vector3f(0.6f,0.6f,0);
			break;
			case BLANC:
				this.colour = new Vector3f(0.9f,0.9f,0.9f);
			break;
			case BLEU:
				this.colour = new Vector3f(0,0.3f,0.9f);
			break;
			case ROSE:
				this.colour = new Vector3f(0.9f,0.5f,0.5f);
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
		this.height = level * HEIGHT_OF_TILE;
	}

	public void decreaseHeight() {
		if (height > 0) {
			height -= HEIGHT_OF_TILE;
		}
	}

	public boolean isAllow() {
		return allow;
	}

	public void setAllow(boolean allow) {
		this.allow = allow;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPositionY(float y){
		this.position.y = y;
	}
	
	public void setPosition(Vector3f position) {
		this.position = position;
	}
	
}
