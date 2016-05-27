package materials;

import org.lwjgl.util.vector.Vector3f;

public class Material {
	
	private int textureID;
	private boolean textured;
	private float shineDamper = 1;
	private Vector3f reflectivity = new Vector3f(0,0,0);
	private Vector3f diffuse = new Vector3f(1,1,1);
	
	public Material(int id){
		textureID = id;
		textured = true;
	}

	public Material(int id,float shineDamper, Vector3f reflectivity,Vector3f diffuse){
		textureID = id;
		textured = true;
		this.shineDamper = shineDamper;
		this.reflectivity = reflectivity;
		this.diffuse = diffuse;
	}
	
	public Material(float shineDamper, Vector3f reflectivity,Vector3f diffuse){
		textured = false;
		this.shineDamper = shineDamper;
		this.reflectivity = reflectivity;
		this.diffuse = diffuse;
	}
	
	public int getID(){
		return textureID;
	}
	
	public void setID(int id){
		textureID = id;
	}
	
	public boolean getIsTextured(){
		return textured;
	}

	public float getShineDamper() {
		return shineDamper;
	}

	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}

	public Vector3f getReflectivity() {
		return reflectivity;
	}

	public void setReflectivity(Vector3f reflectivity) {
		this.reflectivity = reflectivity;
	}
	
	public Vector3f getDiffuse() {
		return diffuse;
	}

	public void setDiffuse(Vector3f diffuse) {
		this.diffuse = diffuse;
	}
	
	
}
