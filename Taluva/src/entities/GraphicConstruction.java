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
	private static Object3D hutC;
	private static Object3D templeC;
	private static Object3D towerC;
	private static Object3D hutE;
	private static Object3D templeE;
	private static Object3D towerE;
	private static Object3D hutV;
	private static Object3D templeV;
	private static Object3D towerV;
	private static Object3D hutB;
	private static Object3D templeB;
	private static Object3D towerB;
	private boolean allow;
	private Vector3f colour;
	private Couleur_Joueur color;
	private float height;
	private boolean bright;
	private Vector3f brightColor;

	public GraphicConstruction(GraphicType type, Vector3f colour) {
		switch (type) {
		case HUT:
			this.object3d = new Object3D(hutC);
			break;
		case TOWER:
			this.object3d = new Object3D(towerC);
			break;
		case TEMPLE:
			this.object3d = new Object3D(templeC);
			break;
		default:
			System.out.println("Unknow construction type");
		}
		this.height = 0;
		this.colour = new Vector3f(colour);
		this.allow = true;
		this.position = new Vector3f(0,0,0);
		this.bright = false;
		this.brightColor = new Vector3f(0,0,0);
	}

	public static void setUp(Loader loader){
		hutC =  new Object3D("Chinese/Hut", loader,true, new Vector3f(0, 0, 0), 0, 0, 0, 0.12f,true);
		templeC = new Object3D("Chinese/Temple", loader,true, new Vector3f(0, 0, 0), 0, 0, 0, 0.12f,true);
		towerC = new Object3D("Chinese/Tower", loader,true, new Vector3f(0, 0, 0), 0, 0, 0, 0.12f,true);
		
		hutE =  new Object3D("Europe/Hut", loader,true, new Vector3f(0, 0, 0), 0, 0, 0, 0.12f,true);
		templeE = new Object3D("Europe/Temple", loader,true, new Vector3f(0, 0, 0), 0, 0, 0, 0.12f,true);
		towerE = new Object3D("Europe/Tower", loader,true, new Vector3f(0, 0, 0), 0, 0, 0, 0.12f,true);
		
		hutV =  new Object3D("Viking/Hut", loader,true, new Vector3f(0, 0, 0), 0, 0, 0, 0.12f,true);
		templeV = new Object3D("Viking/Temple", loader,true, new Vector3f(0, 0, 0), 0, 0, 0, 0.12f,true);
		towerV = new Object3D("Viking/Tower", loader,true, new Vector3f(0, 0, 0), 0, 0, 0, 0.12f,true);
		
		hutB =  new Object3D("Babylon/Hut", loader,true, new Vector3f(0, 0, 0), 0, 0, 0, 0.12f,true);
		templeB = new Object3D("Babylon/Temple", loader,true, new Vector3f(0, 0, 0), 0, 0, 0, 0.12f,true);
		towerB = new Object3D("Babylon/Tower", loader,true, new Vector3f(0, 0, 0), 0, 0, 0, 0.12f,true);
	}
	
	public void init(){
		this.object3d = hutC;
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
		this.bright = gc.bright;
		this.brightColor = gc.brightColor;
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
				switch(color){
				case VERT:
					this.object3d = hutV;
				break;
				case JAUNE:
					this.object3d = hutC;
				break;
				case BLANC:
					this.object3d = hutB;
				break;
				case BLEU:
					this.object3d = hutE;
				break;
				case NEUTRE:
					this.colour = new Vector3f(0,0,0);
				break;
				default :
					System.out.println("Unknow Color");
				}
			break;
		case TOWER:
			switch(color){
			case VERT:
				this.object3d = towerV;
			break;
			case JAUNE:
				this.object3d = towerC;
			break;
			case BLANC:
				this.object3d = towerB;
			break;
			case BLEU:
				this.object3d = towerE;
			break;
			case NEUTRE:
				this.colour = new Vector3f(0,0,0);
			break;
			default :
				System.out.println("Unknow Color");
			}
			break;
		case TEMPLE:
			switch(color){
			case VERT:
				this.object3d = templeV;
			break;
			case JAUNE:
				this.object3d = templeC;
			break;
			case BLANC:
				this.object3d = templeB;
			break;
			case BLEU:
				this.object3d = templeE;
			break;
			case NEUTRE:
				this.colour = new Vector3f(0,0,0);
			break;
			default :
				System.out.println("Unknow Color");
			}
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
		color = colour;
		if(!bright){
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
		}
		else{
			this.colour = brightColor;
		}
		this.object3d.setColor(this.colour);
	}
	
	public void unsetBright(){
		this.bright = false;
	}
	
	public void setBright(Vector3f brightColor){
		this.bright = true;
		this.brightColor = brightColor;
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
