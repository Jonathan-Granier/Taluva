package test;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import Ecouteur.ButtonConstruction;
import Ecouteur.ButtonEndOfTurn;
import Ecouteur.ButtonPick;
import Ecouteur.EcouteurDeSourisTerrain;
import Ecouteur.Ecouteur_Boutons;
import Joueur.Joueur_Humain;
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
import Action.Action_Batiment;
import Action.Action_Construction;
import Action.Action_Tuile;
import Moteur.Phase.Phase_Jeu;
import Moteur.Moteur;
import terrain.Terrain;
import terrain.Tuile;
import renderEngine.Renderer;
import renderEngine.Window;
import shaders.Shader;
import skybox.SkyboxRenderer;
import utils.FPS;
import utils.Grid;
import utils.Grid.Coords;
import utils.InputHandler;
import utils.InputHandler.inputType;
import water.WaterRenderer;
import water.WaterShader;
import water.WaterTile;
import utils.MousePicker;

public class Game {
	
	private Moteur moteur;
	private Grid grid;
	private List<GraphicTile> Tiles;
	private List<GraphicConstruction> constructions;
	private Loader loader;
	private static float delay = 0;
	private static final float TIME = 200000;
	private static boolean draw=true;
	
	//Draw all Tile
	public void drawTile(Renderer renderer,Shader shader){
		List<Action_Tuile> listTile = new ArrayList<Action_Tuile> (moteur.getTerrain().getHistoTuiles());
		//Check listTile with listActionTile("IA mode useful")
		if(Tiles.size() < listTile.size()){
			for(int i = Tiles.size(); i<listTile.size();i++){
				Tiles.add(new GraphicTile(listTile.get(i).getTuile(),loader, new Vector3f(0,0,0)));
				Vector3f worldPos = new Vector3f(grid.toWorldPos(listTile.get(i).getPosition(),Tiles.get(i).getObject3D().getRotY(),listTile.get(i).getNiveau()-1));
				Tiles.get(i).setAngle();
				Tiles.get(i).getObject3D().setPosition(worldPos);
			}
		}
		
		for(int i=0;i<listTile.size();i++){
			Vector3f worldPos = new Vector3f(grid.toWorldPos(listTile.get(i).getPosition(),Tiles.get(i).getObject3D().getRotY(),listTile.get(i).getNiveau()-1));
			Tiles.get(i).getObject3D().setPosition(worldPos);
			renderer.draw(Tiles.get(i).getObject3D(),shader);
		}
	}
	
	public void drawConstruction(Renderer renderer,Shader shader){
		List<Action_Batiment> listConstruction = new ArrayList<Action_Batiment> (moteur.getTerrain().getHistoBatiments());
		//Check listConstruction with listAction_Batiment("IA mode useful")
		constructions = new ArrayList<GraphicConstruction>();
		
		if(constructions.size() < listConstruction.size()){
			for(int i=constructions.size();i<listConstruction.size();i++){
				constructions.add(new GraphicConstruction(GraphicType.HUT,new Vector3f(0,0,0),loader));
				constructions.get(i).setType(listConstruction.get(i).getTypeBatiment());
				Vector3f worldPos = new Vector3f(grid.toWorldPos(listConstruction.get(i).getPosition(),listConstruction.get(i).getNiveau()-1));
				constructions.get(i).getObject3d().setPosition(worldPos);
			}
		}
		
		for(int i=0;i<listConstruction.size();i++){
			Vector3f worldPos = new Vector3f(grid.toWorldPos(listConstruction.get(i).getPosition(),listConstruction.get(i).getNiveau()-1));
			constructions.get(i).getObject3d().setPosition(worldPos);
			renderer.draw(constructions.get(i).getObject3d(),shader);
		}
	}
	
	public void constructionGestion(Vector3f point,GraphicConstruction construction,List<GraphicConstruction> constructions,Grid grid){
		if(point!=null){
			construction.getObject3d().setPosition(new Vector3f(point.x,construction.getHeight(),point.z));
		}
		
		//Snap
		Coords snap = grid.snap(construction.getObject3d(),construction.getObject3d().getPosition());
		if(snap!=null){
			construction.getObject3d().setPosition(snap.worldPos);
			int level = moteur.getTerrain().getNiveauTheoriqueBatiment(snap.indices)-1;
			construction.getObject3d().setPosition(snap.worldPos);
			construction.setHeight(0);
			construction.increaseHeight(level);
			construction.getObject3d().setPositionY(construction.getHeight());
			if(!moteur.getTerrain().placement_batiment_autorise(construction.getType_Batiment(),Couleur_Joueur.JAUNE, snap.indices)){
				construction.getObject3d().setAllow(false);
			}
		}
		
		if(InputHandler.reset(InputHandler.isButtonDown(0) == inputType.INSTANT) && !Keyboard.isKeyDown(Keyboard.KEY_SPACE) && snap!=null){
			putConstruction(constructions,construction,snap);
		}
		
	}
	
	public void tileGestion(Vector3f point,GraphicTile Tile,List<GraphicTile> Tiles,Grid grid){
		if(point!=null){
			Tile.getObject3D().setPosition(new Vector3f(point.x,Tile.getHeight(),point.z));
		}

		if(InputHandler.reset(InputHandler.isButtonDown(1) == inputType.INSTANT))
			Tile.rotate();


		InputHandler.isKeyDown(Tile);

		//Snap
		Tile.setPostionVolcano();
		Coords snap = grid.snap(Tile.getObject3D(),Tile.getObject3D().getPosition(),Tile.getObject3D().getRotY());
		if(snap!=null  && Tile.getTile()!=null){
			int level = moteur.getTerrain().getNiveauTheorique(Tile.getTile().getOrientation(), snap.indices)-1;
			Tile.getObject3D().setPosition(snap.worldPos);
			Tile.setHeight(0);
			Tile.increaseHeight(level);
			Tile.getObject3D().setPositionY(Tile.getHeight());
			if(!moteur.getTerrain().placement_tuile_autorise(Tile.getTile(), snap.indices)){
				Tile.getObject3D().setAllow(false);
			}
		}
		
		if(InputHandler.reset(InputHandler.isButtonDown(0) == inputType.INSTANT) && snap!=null){
			putTile(Tiles,Tile,snap);
		}
		

		
	}
	
	public void putConstruction(List<GraphicConstruction> constructions, GraphicConstruction construction,Coords coords){
		if(moteur.getTerrain().placement_batiment_autorise(construction.getType_Batiment(),Couleur_Joueur.JAUNE, coords.indices)){
			constructions.add(new GraphicConstruction(construction));
			constructions.get(constructions.size()-1).getObject3d().setPosition(coords.worldPos);
			moteur.getTerrain().placer_batiment(construction.getType_Batiment(), Couleur_Joueur.JAUNE, coords.indices);
		}
	}
	
	public void putTile(List<GraphicTile> Tiles, GraphicTile Tile,Coords coords){
		if(moteur.getTerrain().placement_tuile_autorise(Tile.getTile(), coords.indices)){
			Tiles.add(new GraphicTile(Tile));
			Tiles.get(Tiles.size()-1).getObject3D().setPosition(coords.worldPos);
			Tiles.get(Tiles.size()-1).getObject3D().setRotY(Tile.getObject3D().getRotY());
			moteur.getTerrain().placer_tuile(Tile.getTile(), coords.indices);
			moteur.getTerrain().afficher();
		}
	}

	public static boolean delay(){
		if(delay == 0){
			draw = false;
		}
		if(delay>TIME && delay!=0 ){
			delay = 0;
			draw = true;
			return true;
		}
		return false;
	}
	
	public static void increaseDelay(){
		if(!draw)
			delay ++;
		
	}
	
	public void play(JFrame frame,Moteur m,JPanel panel){
		Window.createDislay();
		this.moteur = m;
		Camera camera = new Camera();
		loader = new Loader();
		Shader shader = new Shader();
		Renderer renderer = new Renderer(shader,camera);
		
		FPS.start(frame);
		GraphicTile Tile = new GraphicTile(new Tuile(Case.Type.MONTAGNE,Case.Type.SABLE),loader,new Vector3f(0,0,0),90);
		Ecouteur_Boutons.setTile(loader,Tile);
		Tiles = new ArrayList<GraphicTile>();
		constructions = new ArrayList<GraphicConstruction>();
		
		GraphicConstruction Construction = new GraphicConstruction(GraphicType.HUT,new Vector3f(0,0,0),loader);
		Ecouteur_Boutons.setConstruction(Construction);
		grid = new Grid();
		
		//*************GUI Renderer Set-up******************
		
		Texture fond = new Texture(loader.loadTexture("fond.png"),new Vector2f(Display.getWidth()-200,0),new Vector2f(200,Display.getHeight()));
		ButtonConstruction button_hut = new ButtonConstruction(loader.loadTexture("Button_Hut.png"),new Vector2f(Display.getWidth()-150,50),new Vector2f(100,100),"hut",Construction,moteur);
		ButtonConstruction button_tower = new ButtonConstruction(loader.loadTexture("Button_tower.png"),new Vector2f(Display.getWidth()-150,200),new Vector2f(100,100),"tower",Construction,moteur);
		ButtonConstruction button_temple = new ButtonConstruction(loader.loadTexture("Button_Temple.png"),new Vector2f(Display.getWidth()-150,350),new Vector2f(100,100),"temple",Construction,moteur);
		ButtonEndOfTurn button_end = new ButtonEndOfTurn(loader.loadTexture("Button_Fin.png"),new Vector2f(Display.getWidth()-150,500),new Vector2f(100,100),moteur);
		ButtonPick button_pick = new ButtonPick(loader.loadTexture("Button_pioche.png"),new Vector2f(Display.getWidth()-125,650),new Vector2f(50,50),moteur,Tile,loader);
		
		Drawable drawable = new Drawable(loader);
		/*drawable.bindTexture(fond);
		drawable.bindButton(button_hut);
		drawable.bindButton(button_tower);
		drawable.bindButton(button_temple);
		drawable.bindButton(button_end);
		drawable.bindButton(button_pick);*/
		
		//Object3D table = new Object3D("Table",loader,new Vector3f(Terrain.TAILLE/2*Grid.HEIGHT_OF_HEXA*2f/3f,0,Terrain.TAILLE*Grid.WIDTH_OF_HEXA*3f/4f-200),0,0,0,0.3f);


		
		List<Light> lights = new ArrayList<Light>();
		Light sun = new Light(new Vector3f(Terrain.TAILLE/2*Grid.HEIGHT_OF_HEXA*2f/3f,15000,Terrain.TAILLE*Grid.WIDTH_OF_HEXA*3f/4f),new Vector3f(1,1,1));
		lights.add(sun);

		MousePicker picker = new MousePicker(camera,renderer.getProjectionMatrix());

		//Create listener
		EcouteurDeSourisTerrain ecouteurSouris = new EcouteurDeSourisTerrain(moteur,picker,grid);
		
		//*************Water Renderer Set-up******************
		
		WaterShader waterShader = new WaterShader();
		WaterRenderer waterRenderer = new WaterRenderer(loader, waterShader,renderer.getProjectionMatrix());
		WaterTile water = new WaterTile(0,0,0);
		
		SkyboxRenderer skyboxRenderer = new SkyboxRenderer(loader,renderer.getProjectionMatrix());
		
		while(!Display.isCloseRequested()){
			if(Mouse.getX()>0 && Mouse.getX()<Display.getWidth() && Mouse.getY()>0 && Mouse.getY()<Display.getHeight()){
				Display.getParent().setFocusable(true);
				panel.setFocusable(false);
			}
			else{
				panel.setFocusable(true);
				Display.getParent().setFocusable(false);
			}
			FPS.updateFPS();
			Game.increaseDelay();
			//Button
			/*button_hut.update();
			button_tower.update();
			button_temple.update();
			button_end.update();
			button_pick.update();*/
			
			camera.move();
			
			picker.update(Tile.getHeight());
			Vector3f point = picker.getCurrentObjectPoint();

			/*if(button_tower.type != GraphicType.NULL || button_temple.type != GraphicType.NULL || button_hut.type != GraphicType.NULL)
				constructionGestion(point,Construction,Constructions,grid);
			else
				tileGestion(point,Tile,Tiles,grid);*/
			
			renderer.prepare();
			
			shader.start();
			shader.loadLights(lights);
			
			shader.loadViewMatrix(camera);
			
			

			if(moteur.get_etat_jeu() == Phase_Jeu.CONSTRUIRE_BATIMENT && Ecouteur_Boutons.isPick())
				renderer.draw(Construction.getObject3d(),shader);
			if(moteur.get_etat_jeu() == Phase_Jeu.POSER_TUILE)
				renderer.draw(Tile.getObject3D(),shader);
			
			/*for(GraphicTile tile:Tiles)
				renderer.draw(tile.getObject3D(), shader);*/
			
			ecouteurSouris.run(Tile, Tiles, Construction, constructions);
			
			drawTile(renderer,shader);
			
			//for(GraphicConstruction construction:Constructions)
			//	renderer.draw(construction.getObject3d(), shader);
			
			drawConstruction(renderer,shader);
			
			//renderer.draw(table, shader);
			
			shader.stop();
			
			waterRenderer.render(water, camera,sun);
			
			skyboxRenderer.render(camera);
			
			drawable.draw();

			Window.updateDisplay();

		}
		
		waterShader.cleanUp();
		drawable.cleanUp();
		shader.cleanUp();
		loader.cleanUp();
		Window.closeDisplay();
	}
	
}
