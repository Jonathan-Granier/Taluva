package test;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import Loaders.Loader;
import entities.Camera;
import entities.Light;
import entities.Object3D;
import gui.Drawable;
import gui.Texture;
import renderEngine.Renderer;
import renderEngine.Window;
import shaders.StaticShader;
import utils.FPS;
import utils.MousePicker;

public class Game {

	public void play(){
		Window.createDislay();
		
		Camera camera = new Camera();
		Loader loader = new Loader();
		StaticShader shader = new StaticShader();
		Renderer renderer = new Renderer(shader,camera);
		
		FPS.start();
		
		//Init guis
		Texture gui = new Texture(loader.loadTexture("fond.png"),new Vector2f(Display.getWidth()-250,0), new Vector2f(250,Display.getHeight()));
		MyButton buttonA = new MyButton(loader.loadTexture("Button_A.png"),new Vector2f(Display.getWidth()-200,50), new Vector2f(124,64),"A" );
		MyButton buttonB = new MyButton(loader.loadTexture("Button_B.png"),new Vector2f(Display.getWidth()-200,200), new Vector2f(124,64),"B" );
		
		Drawable drawable = new Drawable(loader);
		drawable.bindTexture(gui);
		drawable.bindTexture(buttonA.getTexture());
		drawable.bindTexture(buttonB.getTexture());
		
		Object3D king = new Object3D("1","king", loader,new Vector3f(0,0,0),0,0,0,0.1f);
		Object3D plateau = new Object3D("plateau","plateau",loader, new Vector3f(0,0,0),0,0,0,0.1f);
		Object3D king2 = new Object3D("3","king_tex",loader, new Vector3f(0,0,20),0,0,0,0.1f);

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
		
		List<Object3D> objects = new ArrayList<Object3D>();
		objects.add(king);
		objects.add(king2);
		
		MousePicker picker = new MousePicker(camera,renderer.getProjectionMatrix(),plateau);
		
		while(!Display.isCloseRequested()){
			FPS.updateFPS();
			//entity.increaseRotation(1, 1, 0);
			camera.move();
			
			picker.update();
			Vector3f point = picker.getCurrentObjectPoint();
			if(point!=null){
				king.setPosition(point);
			}

			buttonA.update();
			buttonB.update();
			
			renderer.prepare();
			
			renderer.renderShadowMap(objects, sun);
			
			shader.start();
			shader.loadLights(lights);
			
			shader.loadViewMatrix(camera);
			renderer.draw(king,shader);
			renderer.draw(plateau,shader);
			renderer.draw(king2,shader);
			shader.stop();
			
			drawable.draw();
			
			Window.updateDisplay();
			
		}
		
		drawable.cleanUp();
		renderer.cleanUp();
		shader.cleanUp();
		loader.cleanUp();
		Window.closeDisplay();
	}
	
}
