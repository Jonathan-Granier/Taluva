package entities;

import org.lwjgl.util.vector.Vector3f;

import loaders.Loader;
import materials.Material;
import models.Mesh;
import models.Model;

public class Plane {

	//Square
	private float[] vertices = {
			-0.5f, 0.5f, 0f,	//V0
			-0.5f, -0.5f, 0f,	//V1
			0.5f, -0.5f, 0f,	//V2
			0.5f, 0.5f, 0f  	//V3
	};
	
	private float[] normals={
			0, 1, 0
	};
	
	private int[] indices = {
			0,1,3, //Top left triangle (V0,V1,V3)
			3,1,2  // Bottom right triangle (V3,V1,V2)
	};
	
	private float[] textureCoords = {
			0,0, //V0
			0,1, //V1
			1,1, //V2
			1,0  //v3
	};
	
	public Plane(){}

	public Object3D createObject3D(String label,String fileName,Loader loader,Vector3f position,int rotX,int rotY,int rotZ,float scale){
		Mesh mesh = loader.loadToVAO(vertices, textureCoords,normals, indices,new Vector3f(0,0,0),new Vector3f(0,0,0));
		Material texture = new Material(loader.loadTexture(fileName));
		Model model = new Model(mesh,texture);
		Object3D object = new Object3D(model,position,rotX,rotY,rotZ,scale);
		return object;
	}
	
	public float[] getVertices() {
		return vertices;
	}

	public int[] getIndices() {
		return indices;
	}

	public float[] getNormals() {
		return normals;
	}

	public float[] getTextureCoords() {
		return textureCoords;
	}
	
	
	
}
