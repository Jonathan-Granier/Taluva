package entities;

import org.lwjgl.util.vector.Vector3f;

import loaders.Loader;
import terrain.Case;

public class GraphicConstruction {

	private static final float HEIGHT_OF_TILE = 1;

	public enum GraphicType {
		HUT, TOWER, TEMPLE, NULL;
	}

	private GraphicType type;

	private Object3D object3d;
	private Vector3f colour;
	private float height;
	private Loader loader;

	public GraphicConstruction(GraphicType type, Vector3f colour, Loader loader) {
		switch (type) {
		case HUT:
			this.object3d = new Object3D("Hut", loader, new Vector3f(0, 0, 0), 0, 0, 0, 0.12f);
			break;
		case TOWER:
			this.object3d = new Object3D("Tower", loader, new Vector3f(0, 0, 0), 0, 0, 0, 0.12f);
			break;
		case TEMPLE:
			this.object3d = new Object3D("Temple", loader,true, new Vector3f(0, 0, 0), 0, 0, 0, 0.12f);
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
			this.object3d = new Object3D("Hut", loader, new Vector3f(0, 0, 0), 0, 0, 0, 0.12f);
			break;
		case TOWER:
			this.object3d = new Object3D("Tower", loader, new Vector3f(0, 0, 0), 0, 0, 0, 0.12f);
			break;
		case TEMPLE:
			this.object3d = new Object3D("Temple", loader,true, new Vector3f(0, 0, 0), 0, 0, 0, 0.12f);
			break;
		default:
			System.out.println("Unknow construction type");
		}
	}

	public Object3D getObject3d() {
		return object3d;
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
