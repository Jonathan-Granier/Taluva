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
import shaders.StaticShader;
import utils.Matrix;

public class Renderer {
	
	public static final float FOV = 45;
	public static final float NEAR_PLANE = 0.1f;
	public static final float FAR_PLANE = 1000;
	
	private Matrix4f projectionMatrix;

	
	public Renderer(StaticShader shader, Camera camera){
		createProjectionMatrix();
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.connectTextureUnits();
		shader.stop();
		//this.shadowMapRender = new ShadowMapMasterRenderer(camera);
	}
	
	public void prepare(){
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f);

	}
	
	public Matrix4f getProjectionMatrix(){
		return projectionMatrix;
	}
	
	public void cleanUp(){
		//shadowMapRender.cleanUp();
	}
	
	public void renderShadowMap(List<Object3D> entities, Light sun){
		//shadowMapRender.render(entities, sun);
	}
	
	public int getShadowMapTexture(){
		//return shadowMapRender.getShadowMap();
		return 0;
	}

	public void draw(Object3D object3D, StaticShader shader){
		//shader.loadToShadowSpaceMatrix(shadowMapRender.getToShadowMapSpaceMatrix());
		Model texturedModel = object3D.getModel();
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
		boolean receiveShadow = object3D.getLabel().equals("plateau");
		shader.loadReceiveShadow(receiveShadow);
		
		if(texturedModel.getTexture().getIsTextured()){
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getID());
		}
		if(receiveShadow){
			GL13.glActiveTexture(GL13.GL_TEXTURE1);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, getShadowMapTexture());
		}
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	
	private void createProjectionMatrix(){
        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))));
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;
 
        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
        projectionMatrix.m33 = 0;		
	}
	
}
