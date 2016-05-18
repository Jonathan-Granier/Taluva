package test;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.GraphicConstruction;
import entities.GraphicConstruction.GraphicType;
import entities.GraphicTile;
import entities.Light;
import entities.Object3D;
import gui.Drawable;
import gui.Texture;
import loaders.Loader;
import terrain.Case;
import terrain.Case.Couleur_Joueur;
import main.Action_Tuile;
import main.Moteur;
import terrain.Terrain;
import terrain.Tuile;
import renderEngine.Renderer;
import renderEngine.Window;
import shaders.Shader;
import utils.FPS;
import utils.Grid;
import utils.Grid.Coords;
import utils.InputHandler;
import utils.MousePicker;

public class Game {
	
	private Terrain terrain;
	private Moteur moteur;
	private Grid grid;
	
	
	//Draw all Tile
	public void drawTile(Renderer renderer,Shader shader,List<GraphicTile> Tiles){
		List<Action_Tuile> listTile = new ArrayList<Action_Tuile> (terrain.getHistoTuiles());
		for(int i=0;i<listTile.size();i++){
			Vector3f worldPos = new Vector3f(grid.toWorldPos(listTile.get(i).getPosition(),Tiles.get(i).getObject3D().getRotY(),listTile.get(i).getNiveau()-1));
			Tiles.get(i).getObject3D().setPosition(worldPos);
			renderer.draw(Tiles.get(i).getObject3D(),shader);
		}
	}
	
	public void constructionGestion(Vector3f point,GraphicConstruction construction,List<GraphicConstruction> constructions,Grid grid){
		if(point!=null){
			construction.getObject3d().setPosition(new Vector3f(point.x,0,point.z));
		}
		
		//Snap
		Coords snap = grid.snap(construction.getObject3d(),construction.getObject3d().getPosition());
		if(snap!=null){
			construction.getObject3d().setPosition(snap.worldPos);
			if(!terrain.placement_batiment_autorise(Case.Type_Batiment.HUTTE,Couleur_Joueur.JAUNE, snap.indices)){
				construction.getObject3d().setAllow(false);
			}
		}
		
		if(InputHandler.isButtonDown(0) && !Keyboard.isKeyDown(Keyboard.KEY_SPACE) && snap!=null){
			putConstruction(constructions,construction,snap);
		}
		
	}
	
	public void tileGestion(Vector3f point,GraphicTile Tile,List<GraphicTile> Tiles,Grid grid){
		if(point!=null){
			Tile.getObject3D().setPosition(new Vector3f(point.x,Tile.getHeight(),point.z));
		}

		if(InputHandler.isButtonDown(1))
			Tile.rotate();

		InputHandler.isKeyDown(Tile);

		//Snap
		Tile.setPostionVolcano();
		Coords snap = grid.snap(Tile.getObject3D(),Tile.getObject3D().getPosition(),Tile.getObject3D().getRotY());
		if(snap!=null  && Tile.getTile()!=null){
			int level = terrain.getNiveauTheorique(Tile.getTile().getOrientation(), snap.indices)-1;
			Tile.getObject3D().setPosition(snap.worldPos);
			Tile.setHeight(0);
			Tile.increaseHeight(level);
			Tile.getObject3D().setPositionY(Tile.getHeight());
			if(!terrain.placement_tuile_autorise(Tile.getTile(), snap.indices)){
				Tile.getObject3D().setAllow(false);
			}
		}
		
		if(InputHandler.isButtonDown(0) && !Keyboard.isKeyDown(Keyboard.KEY_SPACE) && snap!=null){
			putTile(Tiles,Tile,snap);
		}
	}
	
	public void putConstruction(List<GraphicConstruction> constructions, GraphicConstruction construction,Coords coords){
		if(terrain.placement_batiment_autorise(Case.Type_Batiment.HUTTE,Couleur_Joueur.JAUNE, coords.indices)){
			constructions.add(new GraphicConstruction(construction));
			constructions.get(constructions.size()-1).getObject3d().setPosition(coords.worldPos);
			terrain.placer_batiment(Case.Type_Batiment.HUTTE, Couleur_Joueur.JAUNE, coords.indices);
		}
	}
	
	public void putTile(List<GraphicTile> Tiles, GraphicTile Tile,Coords coords){
		if(terrain.placement_tuile_autorise(Tile.getTile(), coords.indices)){
			Tiles.add(new GraphicTile(Tile));
			Tiles.get(Tiles.size()-1).getObject3D().setPosition(coords.worldPos);
			Tiles.get(Tiles.size()-1).getObject3D().setRotY(Tile.getObject3D().getRotY());
			terrain.placer_tuile(Tile.getTile(), coords.indices);
			terrain.afficher();
		}
	}
	
	public List<GraphicTile> createTerrain(Loader loader){
		terrain = new Terrain();
		//moteur = new Moteur(terrain);
		//Case [][] t = terrain.getT();
		List<GraphicTile> Tiles = new ArrayList<GraphicTile>();
		//for(int i=0; i<4;i++)
		//	Tiles.add(new GraphicTile(new Tuile(Case.Type.VOLCAN,Case.Type.VOLCAN), loader,new Vector3f((i+1)*SIZE_OF_HEXA,0,0)));
		
		return Tiles;
	}
	
	public void play(JFrame frame){
		Window.createDislay();
		
		Camera camera = new Camera();
		Loader loader = new Loader();
		Shader shader = new Shader();
		Renderer renderer = new Renderer(shader,camera);
		
		FPS.start(frame);
		
		GraphicTile Tile = new GraphicTile(new Tuile(Case.Type.MONTAGNE,Case.Type.SABLE),loader,new Vector3f(0,0,0),90);
		List<GraphicTile> Tiles = new ArrayList<GraphicTile>(createTerrain(loader));
		List<GraphicConstruction> Constructions = new ArrayList<GraphicConstruction>();
		
		GraphicConstruction Construction = new GraphicConstruction(GraphicType.TOWER,new Vector3f(0,0,0),loader);
		
		grid = new Grid(terrain,loader);
		
		Texture fond = new Texture(loader.loadTexture("fond.png"),new Vector2f(Display.getWidth()-200,0),new Vector2f(200,Display.getHeight()));
		MyButton button_tower = new MyButton(loader.loadTexture("Button_tower.png"),new Vector2f(Display.getWidth()-150,100),new Vector2f(100,100));
		Drawable drawable = new Drawable(loader);
		drawable.bindTexture(fond);
		drawable.bindTexture(button_tower.getTexture());
		
		
		Object3D table = new Object3D("Table",loader,new Vector3f(Terrain.TAILLE/2*Grid.HEIGHT_OF_HEXA*2f/3f,0,Terrain.TAILLE*Grid.WIDTH_OF_HEXA*3f/4f-200),0,0,0,0.3f);

		
		
		List<Light> lights = new ArrayList<Light>();
		Light sun = new Light(new Vector3f(20000,15000,-1000),new Vector3f(1,1,1));
		lights.add(sun);

		MousePicker picker = new MousePicker(camera,renderer.getProjectionMatrix());

		while(!Display.isCloseRequested()){
			FPS.updateFPS();

			//Button
			button_tower.update();
			
			camera.move();
			
			picker.update(Tile.getHeight());
			Vector3f point = picker.getCurrentObjectPoint();

			if(button_tower.type != GraphicType.NULL)
				constructionGestion(point,Construction,Constructions,grid);
			else
				tileGestion(point,Tile,Tiles,grid);
			
			renderer.prepare();
			
			shader.start();
			shader.loadLights(lights);
			
			shader.loadViewMatrix(camera);
			grid.draw(renderer, shader);
			
			if(button_tower.type != GraphicType.NULL)
				renderer.draw(Construction.getObject3d(),shader);
			else
				renderer.draw(Tile.getObject3D(),shader);
			
			/*for(GraphicTile tile:Tiles)
				renderer.draw(tile.getObject3D(), shader);*/
			
			drawTile(renderer,shader,Tiles);
			
			for(GraphicConstruction construction:Constructions)
				renderer.draw(construction.getObject3d(), shader);
			
			renderer.draw(table, shader);
			
			shader.stop();
			
			drawable.draw();
			
			Window.updateDisplay();
			
		}
		
		drawable.cleanUp();
		shader.cleanUp();
		loader.cleanUp();
		Window.closeDisplay();
	}
	
}
