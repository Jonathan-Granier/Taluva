package gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import loaders.Loader;
import models.Mesh;
import utils.Matrix;

public class Drawable {

	public final Mesh quad;
	private GuiShader shader;
	
	private List<Texture> guis;
	private List<Button> buttons;
	
	public Drawable(Loader loader){
		float[] positions = {-1,1,
							-1,-1,
							1,1,
							1,-1};
		quad = loader.loadToVAO(positions,2);
		shader = new GuiShader();
		guis = new ArrayList<Texture>();
		buttons = new ArrayList<Button>();
	}
	
	public void bindTexture(Texture gui){
		guis.add(gui);
	}
	
	public void bindButton(Button button){
		buttons.add(button);
		bindTexture(button.getTexture());
	}
	
	public void draw(){
		//Check for block Game
		boolean block = false;
		for(Button button:buttons){
			if(button.isPressed())
				block = true;
		}
		Button.setGameBlocked(block);
		
		shader.start();
		GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		for(Texture gui:guis){
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D,gui.getTextureId());
			Matrix4f matrix;
			if(gui.isHover())
				matrix = Matrix.createTransformationMatrix(gui.getPosition(), new Vector2f(gui.getScale().x-0.002f,gui.getScale().y-0.002f));
			else
				matrix = Matrix.createTransformationMatrix(gui.getPosition(), gui.getScale());
			shader.loadTransformation(matrix);
			shader.loadHover(gui.isHover());

			shader.loadClicked(gui.isClicked());
			shader.loadGrey(gui.isGrey());
			GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
		}
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_BLEND);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		shader.stop();
	}
	
	public void cleanUp(){
		shader.cleanUp();
	}
	
}
