package loaders;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import models.Mesh;

public class Loader {
	
	private List<Integer> vaos = new ArrayList<Integer>();
	private List<Integer> vbos = new ArrayList<Integer>();
	private List<Integer> textures = new ArrayList<Integer>();
	
	/*********************************
	 * VAO
	 *  -------------------------------
	 * | 0 | vertex
	 * | 1 | textureCoord
	 * | 2 | normals
	 * |   |
	 * |   |
	 *  -------------------------------
	 */
	
	
	public Mesh loadToVAO(float[] positions, float[] textureCoords,float[] normals,int[] indices,Vector3f min,Vector3f max){
		int vaoID = createVAO();
		bindIndicesBuffer(indices);
		storeDataInAttributeList(0,3,positions);
		storeDataInAttributeList(1,3,textureCoords);
		storeDataInAttributeList(2,3,normals);
		unbindVAO();
		return new Mesh(vaoID,indices.length,min,max);
	}
	
	public Mesh loadToVAO(float[] positions){
		int vaoID = createVAO();
		storeDataInAttributeList(0,2,positions);
		unbindVAO();
		return new Mesh(vaoID,positions.length/2,new Vector3f(0,0,0),new Vector3f(0,0,0));
	}
	
	public int loadTexture(String fileName){
		Texture texture = null;

		try {
			texture = TextureLoader.getTexture("PNG", new FileInputStream("Assets/Texture/"+fileName));
		} catch (FileNotFoundException e) {
			System.out.println("file :"+fileName+" not found.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		int textureID = texture.getTextureID();
		textures.add(textureID);
		return textureID;
	}
	
	private int createVAO(){
		int vaoID = GL30.glGenVertexArrays();
		vaos.add(vaoID);
		GL30.glBindVertexArray(vaoID);
		return vaoID;
	}
	
	public void cleanUp(){
		for(int vao:vaos){
			GL30.glDeleteVertexArrays(vao);
		}
		for(int vbo:vbos){
			GL15.glDeleteBuffers(vbo);
		}
		for(int texture:textures){
			GL11.glDeleteTextures(texture);
		}
	}
	
	private void storeDataInAttributeList(int attributeNumber,int coordinateSize, float[] data){
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW); // Cannot be modified
		GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false,0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	private void unbindVAO(){
		GL30.glBindVertexArray(0);
	}
	
	private void bindIndicesBuffer(int[] indices){
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer buffer = storeDataInIntBuffer(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STREAM_DRAW); // Cannot be modified
	}
	
	private IntBuffer storeDataInIntBuffer(int[] data){
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();// prepare for reading
		return buffer;
	}

	private FloatBuffer storeDataInFloatBuffer(float[] data){
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();// prepare for reading
		return buffer;
	}

}
