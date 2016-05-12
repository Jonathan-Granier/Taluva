package models;

import materials.Material;

// Mesh + texture

public class Model {
	
	private Mesh mesh;
	private Material texture;
	
	public Model(Mesh mesh, Material texture){
		this.mesh = mesh;
		this.texture = texture;
	}

	public Mesh getRawModel() {
		return mesh;
	}

	public Material getTexture() {
		return texture;
	}

	
	
}
