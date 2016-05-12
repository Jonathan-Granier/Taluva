package entities;

import org.lwjgl.util.vector.Vector3f;

import Loaders.Loader;
import main.Case;
import main.Tuile;

public class GraphicTile {
	
	private static float HEIGHT_OF_TILE = 2;
	
	private Object3D object3d;
	private Tuile tile;
	private float angle;
	private float height;
	
	public GraphicTile(Tuile tile,Loader loader,Vector3f position){
		this.object3d = new Object3D("","Tile",loader,position,0,0,0,0.5f);
		this.tile = tile;
		this.angle = 0;
		this.height = 0;
	}

	public void rotate(){
		tile.Tourner_anti_horaire();
		switch(tile.get_Orientation_Volcan()){
			case O:
				object3d.setRotY(270);
			case S_O:
				object3d.setRotY(330);
			case S_E:
				object3d.setRotY(60);
			case E:
				object3d.setRotY(90);
			case N_E:
				object3d.setRotY(120);
			case N_O:
				object3d.setRotY(240);
			default:
				System.out.println("rotate cannot be done");
		}
	}
	
	public Object3D getObject3D() {
		return object3d;
	}

	public void setObject3D(Object3D object3d) {
		this.object3d = object3d;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public Tuile getTile() {
		return tile;
	}

	public void setTile(Tuile tile) {
		this.tile = tile;
	}
	
}
