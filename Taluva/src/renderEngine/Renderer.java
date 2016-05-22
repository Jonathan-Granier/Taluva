package renderEngine;

import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import entities.Camera;
import entities.Object3D;
import materials.Material;
import entities.Light;
import models.Mesh;
import models.Model;
import shaders.Shader;
import utils.Matrix;

public class Renderer {
	
	public static final float FOV = 45;
	public static final float NEAR_PLANE = 0.1f;
	public static final float FAR_PLANE = 750;
	
	private Matrix4f projectionMatrix;

	
	public Renderer(Shader shader, Camera camera){
		createProjectionMatrix();
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.connectTextureUnits();
		shader.stop();
	}
	
	public void prepare(){
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f);

	}
	
	public Matrix4f getProjectionMatrix(){
		return projectionMatrix;
	}

	private void drawOne(Object3D object3D,Model texturedModel,Shader shader){
		Mesh model = texturedModel.getRawModel();
		GL30.glBindVertexArray(model.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		
		//Transform entity
		Matrix4f transformationMatrix = Matrix.createTransformationMatrix(object3D.getPosition(), object3D.getRotX(), object3D.getRotY(), object3D.getRotZ(), object3D.getScale());
		shader.loadTransformationMatrix(transformationMatrix);
		
		Material texture = texturedModel.getTexture();
		shader.loadDiffuse(texture.getDiffuse());
		shader.loadShineVariable(texture.getShineDamper(), texture.getReflectivity());
		shader.loadTextured(texturedModel.getTexture().getIsTextured());
		shader.loadNotAllow(!object3D.isAllow());

		if(texturedModel.getTexture().getIsTextured()){
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getID());
		}

		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	
	public void draw(Object3D object3D, Shader shader){


		if(object3D.isMultiObj()){
			for(Model model:object3D.getModels().getModels()){
				drawOne(object3D,model,shader);
			}
		}
		else{
			Model texturedModel = object3D.getModel();
			drawOne(object3D,texturedModel,shader);
		}

	}
	
	private void createProjectionMatrix(){
        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))));
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;
 
        System.out.println( Display.getWidth() + " " +Display.getHeight());
        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
        projectionMatrix.m33 = 0;		
	}
	
}
