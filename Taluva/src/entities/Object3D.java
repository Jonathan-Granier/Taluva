package entities;

import org.lwjgl.util.vector.Vector3f;

import loaders.Loader;
import loaders.OBJLoader;
import models.Model;
import models.Models;

public class Object3D {
	
	private Model model;
	private Vector3f position;
	private float rotX,rotY,rotZ;
	private float scale;
	private String label;
	private boolean allow = true;
	private boolean multiObj = false;
	private boolean construction = false;
	private Models models;
	private Vector3f Color;
	
	public Object3D(Object3D object3d){
		this.model = object3d.getModel();
		this.models = object3d.getModels();
		this.multiObj = object3d.isMultiObj();
		this.position = new Vector3f(object3d.getPosition());
		this.rotX = object3d.getRotX();
		this.rotY = object3d.getRotY();
		this.rotZ = object3d.getRotZ();
		this.scale = object3d.getScale();
		this.construction = object3d.isConstruction();
		this.Color = new Vector3f(object3d.getColor());
	}
	
	public Object3D(Model model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		this.model = model;
		this.position = new Vector3f(position);
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
		this.Color = new Vector3f(0,0,0);
		this.construction = false;
	}
	
	public Object3D(String objFilename, Loader loader ) {
		this.model = OBJLoader.loadObjModel(objFilename, loader);
		this.position = new Vector3f(0,0,0);
		this.rotX = 0;
		this.rotY = 0;
		this.rotZ = 0;
		this.scale = 1;
		this.Color = new Vector3f(0,0,0);
		this.construction = false;
	}
	
	public Object3D(String objFilename, Loader loader,Vector3f position, float rotX, float rotY, float rotZ, float scale ) {
		this.model = OBJLoader.loadObjModel(objFilename, loader);
		this.position =new Vector3f(position);
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
		this.Color = new Vector3f(0,0,0);
		this.construction = false;
	}
	
	public Object3D(String objFilename, Loader loader,boolean multiObj) {
		this.models = OBJLoader.loadObjModels(objFilename, loader);
		this.multiObj = multiObj;
		this.position = new Vector3f(0,0,0);
		this.rotX = 0;
		this.rotY = 0;
		this.rotZ = 0;
		this.scale = 1;
		this.Color = new Vector3f(0,0,0);
		this.construction = false;
	}
	
	public Object3D(String objFilename, Loader loader,boolean multiObj,Vector3f position, float rotX, float rotY, float rotZ, float scale ) {
		this.models = OBJLoader.loadObjModels(objFilename, loader);
		this.multiObj = multiObj;
		this.position = new Vector3f(position);
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
		this.Color = new Vector3f(0,0,0);
		this.construction = false;
	}
	
	public Object3D(String objFilename, Loader loader,boolean multiObj,Vector3f position, float rotX, float rotY, float rotZ, float scale,boolean construction ) {
		this.models = OBJLoader.loadObjModels(objFilename, loader);
		this.multiObj = multiObj;
		this.position =new Vector3f(position);
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
		this.Color = new Vector3f(0,0,0);
		this.construction = construction;
	}

	
	public void setPositionY(float y){
		this.position.y = y;
	}
	
	public void increasePosition(float dx, float dy, float dz){
		this.position.x+=dx;
		this.position.y+=dy;
		this.position.z+=dz;
	}
	
	public void increaseRotation(float dx, float dy, float dz){
		this.rotX+=dx;
		this.rotY+=dy;
		this.rotZ+=dz;
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public float getRotX() {
		return rotX;
	}

	public void setRotX(float rotX) {
		this.rotX = rotX;
	}

	public float getRotY() {
		return rotY;
	}

	public void setRotY(float rotY) {
		this.rotY = rotY;
	}

	public float getRotZ() {
		return rotZ;
	}

	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public boolean isAllow() {
		return allow;
	}

	public void setAllow(boolean allow) {
		this.allow = allow;
	}

	public boolean isMultiObj() {
		return multiObj;
	}

	public Models getModels() {
		return models;
	}

	public Vector3f getColor() {
		return Color;
	}

	public void setColor(Vector3f color) {
		Color = color;
	}

	public boolean isConstruction() {
		return construction;
	}

	public void setConstruction(boolean construction) {
		this.construction = construction;
	}
	
}
