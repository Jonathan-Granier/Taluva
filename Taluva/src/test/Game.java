package test;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

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
import IHM.Menu_circulaire_creation;
import Joueur.IA_Generique;
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

public class Game implements Observer,KeyListener  {
	
	private Moteur moteur;
	private Grid grid;
	private List<GraphicTile> Tiles;
	private List<GraphicConstruction> constructions;
	private Loader loader;
	private static float delay = 0;
	private static final float TIME = 80;
	private static boolean runDelay=false;
	private static boolean clear = false;
	private Camera camera;
	private MousePicker picker;
	private Renderer renderer;
	private GraphicTile Tile;
	private GraphicConstruction Construction;
	private Shader shader;
	private List<Light> lights;
	private Light sun;
	private EcouteurDeSourisTerrain ecouteurSouris;
	private Menu_circulaire_creation marking_menu;
	private WaterShader waterShader;
	private WaterRenderer waterRenderer;
	private WaterTile water;
	private SkyboxRenderer skyboxRenderer;
	private Canvas canvas;
	
	private boolean[] keys = {false,false,false,false};
	
	//Draw all Tile
	public void drawTile(Renderer renderer,Shader shader){
		List<Action_Tuile> listTile = new ArrayList<Action_Tuile> (moteur.getTerrain().getHistoTuiles());
		//Check listTile with listActionTile("IA mode useful")
		
		while(listTile.size()<Tiles.size()){
			Tiles.remove(Tiles.size()-1);
		}
		
		int loop = listTile.size()-1;
		if(Game.checkDelay() || !runDelay)
			loop = listTile.size();
		
		if(Tiles.size() < listTile.size()){
			for(int i = Tiles.size(); i<listTile.size();i++){
				Tiles.add(new GraphicTile(listTile.get(i).getTuile(),loader, new Vector3f(0,0,0)));
				Tiles.get(i).setTile(listTile.get(i).getTuile());
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
		//if(Game.clear){
		//if(listConstruction.size()<constructions.size()){
			// Des constructions on été enlevées : on met à jour notre liste selon getIndexBatSuppr
			// (il faut actualiser à ces indices et supprimer les derniers éléments)
			while(listConstruction.size()<constructions.size()){
				constructions.remove(constructions.size()-1);
			}
			/*int [] ind_bat_suppr = moteur.getTerrain().getIndexBatSuppr();
			for(int i=0;i<2;i++){
				if(ind_bat_suppr[i] >= 0  && ind_bat_suppr[i] < constructions.size()){
					//constructions.set(ind_bat_suppr[i],new GraphicConstruction(GraphicType.HUT,new Vector3f(0,0,0),loader));
					constructions.get(ind_bat_suppr[i]).setColour(listConstruction.get(ind_bat_suppr[i]).getCouleur());
					constructions.get(ind_bat_suppr[i]).setType(listConstruction.get(ind_bat_suppr[i]).getTypeBatiment());
					Vector3f worldPos = new Vector3f(grid.toWorldPos(listConstruction.get(ind_bat_suppr[i]).getPosition(),listConstruction.get(ind_bat_suppr[i]).getNiveau()-1));
					constructions.get(ind_bat_suppr[i]).getObject3d().setPosition(worldPos);
				}
			}
			Game.clear = false;*/
		//}
		

			
		if(constructions.size() < listConstruction.size()){
			for(int i=constructions.size();i<listConstruction.size();i++){
				constructions.add(new GraphicConstruction(GraphicType.HUT,new Vector3f(0,0,0),loader));
				constructions.get(i).setColour(listConstruction.get(i).getCouleur());
				constructions.get(i).setType(listConstruction.get(i).getTypeBatiment());
				Vector3f worldPos = new Vector3f(grid.toWorldPos(listConstruction.get(i).getPosition(),listConstruction.get(i).getNiveau()-1));
				constructions.get(i).getObject3d().setPosition(worldPos);
			}
		}
		
		int loop = listConstruction.size();
		//if(Game.checkDelay() || !runDelay)
		//	loop = listConstruction.size();
		
		for(int i=0;i<loop;i++){
			constructions.get(i).setColour(listConstruction.get(i).getCouleur());
			constructions.get(i).setType(listConstruction.get(i).getTypeBatiment());
			Vector3f worldPos = new Vector3f(grid.toWorldPos(listConstruction.get(i).getPosition(),listConstruction.get(i).getNiveau()-1));
			constructions.get(i).getObject3d().setPosition(worldPos);
			renderer.draw(constructions.get(i).getObject3d(),shader);
		}
	}
	
	//public static void majHistoBatiments(){
	//	clear = true;
	//}
	
	public static void initDelay(){
		runDelay = true;
		delay = 0;
	}
	
	public static boolean checkDelay(){
		if(delay>TIME){
			runDelay = false;
		}
		
		return delay>TIME;
	}
	
	public static void increaseDelay(){
		if(runDelay)
			delay++;
	}
	
	public void init(JFrame frame,Moteur m,Canvas canvas){
		Window.createDislay();
		this.moteur = m;
		this.canvas = canvas;
		camera = new Camera();
		loader = new Loader();
		shader = new Shader();
		renderer = new Renderer(shader,camera);
		
		FPS.start(frame);
		Tile = new GraphicTile(new Tuile(Case.Type.MONTAGNE,Case.Type.SABLE),loader,new Vector3f(0,0,0),90);
		GraphicTile.setTexture(loader);
		Ecouteur_Boutons.setTile(loader,Tile);
		Tiles = new ArrayList<GraphicTile>();
		constructions = new ArrayList<GraphicConstruction>();
		
		Construction = new GraphicConstruction(GraphicType.HUT,new Vector3f(0,0,0),loader);
		Ecouteur_Boutons.setConstruction(Construction);
		grid = new Grid();
		
		lights = new ArrayList<Light>();
		sun = new Light(new Vector3f(Terrain.TAILLE/2*Grid.HEIGHT_OF_HEXA*2f/3f,15000,Terrain.TAILLE*Grid.WIDTH_OF_HEXA*3f/4f),new Vector3f(1,1,1));
		lights.add(sun);

		picker = new MousePicker(camera,renderer.getProjectionMatrix());

		
		//*************Water Renderer Set-up******************
		
		waterShader = new WaterShader();
		waterRenderer = new WaterRenderer(loader, waterShader,renderer.getProjectionMatrix());
		water = new WaterTile(0,0,0);
		
		skyboxRenderer = new SkyboxRenderer(loader,renderer.getProjectionMatrix());
		
		//*************Marking Menu Set-up******************
		marking_menu = new Menu_circulaire_creation(moteur,loader,Construction,grid,camera);

		//Create listener
		ecouteurSouris = new EcouteurDeSourisTerrain(moteur,picker,grid,Tile, Tiles, Construction, constructions,marking_menu);
		
		TimerOpenGL timer = new TimerOpenGL();
		timer.addObserver(this);
		canvas.addMouseListener(ecouteurSouris);
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		FPS.updateFPS();
		Game.increaseDelay();
		updateCamera();
		camera.move();
		
		picker.update(Tile.getHeight());
		Vector3f point = picker.getCurrentObjectPoint();

		
		renderer.prepare();
		
		shader.start();
		shader.loadLights(lights);
		
		shader.loadViewMatrix(camera);
		
		
		if(moteur.get_etat_jeu() == Phase_Jeu.CONSTRUIRE_BATIMENT && Ecouteur_Boutons.isPick())
			renderer.draw(Construction.getObject3d(),shader);
		if(moteur.get_etat_jeu() == Phase_Jeu.POSER_TUILE)
			renderer.draw(Tile.getObject3D(),shader);
		
		
		ecouteurSouris.run();
		

		drawTile(renderer,shader);
		drawConstruction(renderer,shader);
		
		shader.stop();
		
		waterRenderer.render(water, camera,sun);
		
		skyboxRenderer.render(camera);

		marking_menu.draw();
		
		Window.updateDisplay();

	}
	
	public void cleanUp(){
		waterShader.cleanUp();
		shader.cleanUp();
		loader.cleanUp();
		marking_menu.cleanUp();
		Window.closeDisplay();
	}

	public void updateCamera(){
		if(keys[0]){
			camera.increaseZ((float) Math.cos(Math.toRadians(camera.getAngleAroundPivot())));
			camera.increaseX((float) Math.sin(Math.toRadians(camera.getAngleAroundPivot())));
		}
		else if(keys[1]){
			camera.decreaseZ((float) Math.cos(Math.toRadians(camera.getAngleAroundPivot())));
			camera.decreaseX((float) Math.sin(Math.toRadians(camera.getAngleAroundPivot())));
		}
		else if(keys[2]){
			camera.decreaseZ((float) Math.sin(Math.toRadians(camera.getAngleAroundPivot())));
			camera.increaseX((float) Math.cos(Math.toRadians(camera.getAngleAroundPivot())));
		}
		else if(keys[3]){
			camera.increaseZ((float) Math.sin(Math.toRadians(camera.getAngleAroundPivot())));
			camera.decreaseX((float) Math.cos(Math.toRadians(camera.getAngleAroundPivot())));
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_Z:
			keys[0] = true;
			break;
		case KeyEvent.VK_S:
			keys[1] = true;
			break;
		case KeyEvent.VK_Q:
			keys[2] = true;
			break;
		case KeyEvent.VK_D:
			keys[3] = true;
			break;
		default:
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_Z:
			keys[0] = false;
			break;
		case KeyEvent.VK_S:
			keys[1] = false;
			break;
		case KeyEvent.VK_Q:
			keys[2] = false;
			break;
		case KeyEvent.VK_D:
			keys[3] = false;
			break;
		default:
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	
}
