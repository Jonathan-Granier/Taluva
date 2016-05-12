package entities;

import org.lwjgl.util.vector.Vector3f;

import Loaders.Loader;
import Loaders.OBJLoader;
import materials.Material;
import models.Mesh;
import models.Model;

public class Cube {



	float[] vertices = {			
			-0.5f,0.5f,-0.5f,	
			-0.5f,-0.5f,-0.5f,	
			0.5f,-0.5f,-0.5f,	
			0.5f,0.5f,-0.5f,		
			
			-0.5f,0.5f,0.5f,	
			-0.5f,-0.5f,0.5f,	
			0.5f,-0.5f,0.5f,	
			0.5f,0.5f,0.5f,
			
			0.5f,0.5f,-0.5f,	
			0.5f,-0.5f,-0.5f,	
			0.5f,-0.5f,0.5f,	
			0.5f,0.5f,0.5f,
			
			-0.5f,0.5f,-0.5f,	
			-0.5f,-0.5f,-0.5f,	
			-0.5f,-0.5f,0.5f,	
			-0.5f,0.5f,0.5f,
			
			-0.5f,0.5f,0.5f,
			-0.5f,0.5f,-0.5f,
			0.5f,0.5f,-0.5f,
			0.5f,0.5f,0.5f,
			
			-0.5f,-0.5f,0.5f,
			-0.5f,-0.5f,-0.5f,
			0.5f,-0.5f,-0.5f,
			0.5f,-0.5f,0.5f
			
	};
	
	float[] textureCoords = {
			
			0,0,0,
			0,1,0,
			1,1,0,
			1,0,0,
			
			0,0,0,
			0,1,0,
			1,1,0,
			1,0,0,
			
			0,0,0,
			0,1,0,
			1,1,0,
			1,0,0,
			
			0,0,0,
			0,1,0,
			1,1,0,
			1,0,0,
			
			0,0,0,
			0,1,0,
			1,1,0,
			1,0,0,
			
			0,0,0,
			0,1,0,
			1,1,0,
			1,0,0

			
	};
	
	private float[] normals={
			0, -1, -0,
			0, 1, -0,
			0, 0, 1,
			1, 0, -0,
			0, 0, -1,
			-1, 0, -0
	};
	
	int[] indices = {
			0,1,3,
			3,1,2,
			
			4,5,7,
			7,5,6,
			
			8,9,11,
			11,9,10,
			
			12,13,15,
			15,13,14,	
			
			16,17,19,
			19,17,18,
			
			20,21,23,
			23,21,22

	};

	public Cube(){}
	
	public Object3D createObject3D(String label,String fileName,Loader loader,Vector3f position,int rotX,int rotY,int rotZ,float scale){
		Mesh mesh = loader.loadToVAO(vertices, textureCoords,normals, indices,new Vector3f(0,0,0),new Vector3f(0,0,0));
		Material texture = new Material(loader.loadTexture(fileName));
		Model model = new Model(mesh,texture);
		Object3D object = new Object3D(label,model,position,rotX,rotY,rotZ,scale);
		return object;
	}
	
	public float[] getVertices() {
		return vertices;
	}

	public float[] getTextureCoords() {
		return textureCoords;
	}
	
	public float[] getNormals() {
		return normals;
	}

	public int[] getIndices() {
		return indices;
	}
	
	
	
	
}
