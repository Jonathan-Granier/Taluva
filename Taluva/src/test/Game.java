package test;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import Loaders.Loader;
import entities.Camera;
import entities.GraphicTile;
import entities.Light;
import entities.Object3D;
import gui.Drawable;
import gui.Texture;
import main.Case;
import main.Moteur;
import main.Terrain;
import main.Tuile;
import renderEngine.Renderer;
import renderEngine.Window;
import shaders.StaticShader;
import utils.FPS;
import utils.InputHandler;
import utils.MousePicker;

public class Game {
	
	private static float SIZE_OF_HEXA = 68;
	private Terrain terrain;
	private Moteur moteur;
	
	public List<GraphicTile> createTerrain(Loader loader){
		terrain = new Terrain();
		//moteur = new Moteur(terrain);
		Case [][] t = terrain.getT();
		List<GraphicTile> Tiles = new ArrayList<GraphicTile>();
		for(int i=0; i<4;i++)
			Tiles.add(new GraphicTile(new Tuile(Case.Type.VOLCAN,Case.Type.VOLCAN), loader,new Vector3f((i+1)*SIZE_OF_HEXA,0,0)));
		
		return Tiles;
	}
	
	public void play(){
		Window.createDislay();
		
		Camera camera = new Camera();
		Loader loader = new Loader();
		StaticShader shader = new StaticShader();
		Renderer renderer = new Renderer(shader,camera);
		
		FPS.start();
		
		GraphicTile Tile = new GraphicTile(new Tuile(Case.Type.VOLCAN,Case.Type.VOLCAN),loader,new Vector3f(0,0,0));
		List<GraphicTile> Tiles = new ArrayList<GraphicTile>(createTerrain(loader));

		List<Light> lights = new ArrayList<Light>();
		Light sun = new Light(new Vector3f(20000,15000,-1000),new Vector3f(1,1,1));
		Light light = new Light(new Vector3f(-20,10,10),new Vector3f(1,1,1));
		Light light2 = new Light(new Vector3f(20,10,-10),new Vector3f(0,1,1));
		Light light3 = new Light(new Vector3f(80,20,-100),new Vector3f(1,0,1));
		Light light4 = new Light(new Vector3f(50,10,80),new Vector3f(1,1,0));
		lights.add(sun);
		/*lights.add(light);
		lights.add(light2);
		lights.add(light3);
		lights.add(light4);*/

		MousePicker picker = new MousePicker(camera,renderer.getProjectionMatrix());
		
		while(!Display.isCloseRequested()){
			FPS.updateFPS();
			//entity.increaseRotation(1, 1, 0);
			camera.move();
			
			picker.update();
			Vector3f point = picker.getCurrentObjectPoint();
			if(point!=null){
				Tile.getObject3D().setPosition(new Vector3f(point.x,0,point.z));
			}

			if(InputHandler.isButtonDown(1))
				Tile.rotate();
				
			renderer.prepare();
			
			shader.start();
			shader.loadLights(lights);
			
			shader.loadViewMatrix(camera);
			renderer.draw(Tile.getObject3D(),shader);
			for(GraphicTile tile:Tiles)
				renderer.draw(tile.getObject3D(), shader);
			shader.stop();
			
			
			Window.updateDisplay();
			
		}
		
		renderer.cleanUp();
		shader.cleanUp();
		loader.cleanUp();
		Window.closeDisplay();
	}
	
}
