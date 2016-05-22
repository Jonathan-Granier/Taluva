package water;

import java.util.List;

import models.Mesh;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.Window;
import loaders.Loader;
import utils.FPS;
import utils.Matrix;
import entities.Camera;
import entities.Light;

public class WaterRenderer {

    private static final String DIFFUSE_MAP = "Skybox/right.png";
    private static final String DUDV_MAP = "waterDUDV.png";
    private static final String NORMAL_MAP = "waterNormal.png";
	private static final float WAVE_SPEED = 0.002f;
    
	private Mesh quad;
	private WaterShader shader;
	private int textureID;
	private int dudvID;
	private int normalID;
	private float moveFactor = 0;

	public WaterRenderer(Loader loader, WaterShader shader, Matrix4f projectionMatrix) {
		this.shader = shader;
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
		setUpVAO(loader);
		textureID = loader.loadTexture(DIFFUSE_MAP);
		dudvID = loader.loadTexture(DUDV_MAP);
		normalID = loader.loadTexture(NORMAL_MAP);
	}

	public void render(WaterTile water, Camera camera, Light light) {
		prepareRender(camera,light);	
			Matrix4f modelMatrix = Matrix.createTransformationMatrix(
					new Vector3f(water.getX(), water.getHeight(), water.getZ()), 0, 0, 0,
					WaterTile.TILE_SIZE);
			shader.loadModelMatrix(modelMatrix);
			shader.conectTexture();
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D,textureID);
			GL13.glActiveTexture(GL13.GL_TEXTURE1);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D,dudvID);
			GL13.glActiveTexture(GL13.GL_TEXTURE2);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D,normalID);
			GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, quad.getVertexCount());

		unbind();
	}
	
	private void prepareRender(Camera camera, Light light){
		shader.start();
		shader.loadlight(light);
		shader.loadViewMatrix(camera);
		shader.loadCamera(camera);
		GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		moveFactor += WAVE_SPEED * (float)FPS.getDelta()/1000;
		moveFactor %= 1;
		shader.loadMoveFactor(moveFactor);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	private void unbind(){
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		GL11.glDisable(GL11.GL_BLEND);
		shader.stop();
	}

	private void setUpVAO(Loader loader) {
		// Just x and z vectex positions here, y is set to 0 in v.shader
		float[] vertices = { -100, -100, -100, 100, 100, -100, 100, -100, -100, 100, 100, 100 };
		quad = loader.loadToVAO(vertices,2);
	}

}
