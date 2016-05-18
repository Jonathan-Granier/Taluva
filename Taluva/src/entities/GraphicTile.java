package entities;

import org.lwjgl.util.vector.Vector3f;

import Loaders.Loader;
import terrain.Case;
import terrain.Tuile;

public class GraphicTile {
	
	private static final float RAY = 45;
	private static final float HEIGHT_OF_TILE = 2;
	
	private Object3D object3d;
	private Tuile tile;
	private float angle;
	private float height;
	private Vector3f postionVolcano;
	
	public GraphicTile(Tuile tile,Loader loader,Vector3f position){
		this.object3d = new Object3D("","Tile",loader,position,0,0,0,0.5f);
		this.tile = tile;
		this.angle = 0;
		this.height = 0;
	}

	public GraphicTile(GraphicTile tile){
		this.object3d = new Object3D(tile.getObject3D());
		this.tile = tile.getTile();
		this.angle = tile.getAngle();
		this.height = tile.getHeight();
	}
	
	public GraphicTile(Tuile tile,Loader loader,Vector3f position,float rotY){
		this.object3d = new Object3D("","Tile",loader,position,0,rotY,0,0.5f);
		this.tile = tile;
		this.angle = rotY;
		this.height = 0;
	}
	
	public void increaseHeight(){
		height += HEIGHT_OF_TILE;
	}
	
	public void decreaseHeight(){
		if(height>0){
			height -= HEIGHT_OF_TILE;
		}
	}
	
	public void rotate(){
		tile.Tourner_horaire();
		switch(tile.get_Orientation_Volcan()){
			case O:
				object3d.setRotY(30);
				break;
			case S_O:
				object3d.setRotY(90);
				break;
			case S_E:
				object3d.setRotY(150);
				break;
			case E:
				object3d.setRotY(210);
				break;
			case N_E:
				object3d.setRotY(270);
				break;
			case N_O:
				object3d.setRotY(330);
				break;
			default:
				System.out.println("Rotate cannot be done");
		}
	}
	
	public Vector3f getPostionVolcano() {
		return postionVolcano;
	}

	public void setPostionVolcano() {
		Vector3f center = new Vector3f(object3d.getPosition());
		//postionVolcano
		switch(tile.get_Orientation_Volcan()){
			case O:
				postionVolcano = new Vector3f(center.x-RAY,0,center.z);
				break;
			case S_O:
				postionVolcano = new Vector3f((float) (center.x-RAY*Math.cos(30)),0,(float) (center.z+RAY*Math.sin(30)));
				break;
			case S_E:
				postionVolcano = new Vector3f((float) (center.x+RAY*Math.cos(30)),0,(float) (center.z+RAY*Math.sin(30)));
				break;
			case E:
				postionVolcano = new Vector3f(center.x+RAY,0,center.z);
				break;
			case N_E:
				postionVolcano = new Vector3f((float) (center.x+RAY*Math.cos(30)),0,(float) (center.z-RAY*Math.sin(30)));
				break;
			case N_O:
				postionVolcano = new Vector3f((float) (center.x-RAY*Math.cos(30)),0,(float) (center.z-RAY*Math.sin(30)));
				break;
			default:
				System.out.println("Orientation unknow");
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
