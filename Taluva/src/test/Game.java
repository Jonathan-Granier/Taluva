package test;

import java.awt.Canvas;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import loaders.Loader;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.Renderer;
import renderEngine.Window;
import shaders.Shader;
import skybox.SkyboxRenderer;
import terrain.Case;
import terrain.Case.Type_Batiment;
import terrain.Terrain;
import terrain.Tuile;
import utils.FPS;
import utils.Grid;
import utils.MousePicker;
import utils.OSValidator;
import water.WaterRenderer;
import water.WaterShader;
import water.WaterTile;
import Action.Action_Batiment;
import Action.Action_Tuile;
import Ecouteur.EcouteurDeSourisTerrain;
import Ecouteur.Ecouteur_Boutons;
import IHM.JPanelPioche;
import IHM.Menu_circulaire_creation;
import Menu.Echap;
import Moteur.Moteur;
import Moteur.Phase.Phase_Jeu;
import entities.Camera;
import entities.GraphicConstruction;
import entities.GraphicConstruction.GraphicType;
import entities.GraphicTile;
import entities.Light;

public class Game implements Observer,KeyListener {
	
	public static boolean BRIGHT = false;
	
	private Moteur moteur;
	private Grid grid;
	private ArrayList<GraphicTile> Tiles;
	private ArrayList<GraphicConstruction> constructions;
	private Loader loader;
	private static float delay = 0;
	private static final float TIME = 80;
	private static boolean runDelay=false;
	private Camera camera;
	private MousePicker picker;
	private Renderer renderer;
	private GraphicTile Tile;
	private GraphicConstruction Construction;
	private Shader shader;
	private ArrayList<Light> lights;
	private Light sun;
	private EcouteurDeSourisTerrain ecouteurSouris;
	private Menu_circulaire_creation marking_menu;
	private WaterShader waterShader;
	private WaterRenderer waterRenderer;
	private WaterTile water;
	private SkyboxRenderer skyboxRenderer;
	private Canvas canvas;
	private JFrame frame;
	private JFrame menu;
	private TimerOpenGL timer;
	
	private boolean[] keys = {false,false,false,false};
	private Vector2f[] pos;
	
	private int num_vue;
	
	//Draw all Tile
	public void drawTile(Renderer renderer,Shader shader){
		ArrayList<Action_Tuile> listTile = new ArrayList<Action_Tuile> (moteur.getTerrain().getHistoTuiles());
		//Check listTile with listActionTile("IA mode useful")
		
		while(listTile.size()<Tiles.size()){
			Tiles.remove(Tiles.size()-1);
		}
		
		//int loop = listTile.size()-1;
		//if(Game.checkDelay() || !runDelay)
		//	loop = listTile.size();
		
		if(Tiles.isEmpty() && !listTile.isEmpty()) camera.recenter();
		
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
		ArrayList<Action_Batiment> listConstruction = new ArrayList<Action_Batiment> (moteur.getTerrain().getHistoBatiments());
		//Check listConstruction with listAction_Batiment("IA mode useful")

		// Des constructions on été enlevées : on met à jour notre liste
		// (il faut supprimer les derniers éléments)
		while(listConstruction.size()<constructions.size()){
			constructions.remove(constructions.size()-1);
		}

		// Des constructions ont été ajoutées : on les ajoute à la liste
		if(constructions.size()<listConstruction.size()){
			if(BRIGHT){
				for(int i=0;i<constructions.size();i++){
					constructions.get(i).unsetBright();
				}
			}
			for(int i=constructions.size();i<listConstruction.size();i++){
				constructions.add(new GraphicConstruction(GraphicType.HUT,new Vector3f(0,0,0)));
			}
		}

		for(int i=0;i<listConstruction.size();i++){
			if(BRIGHT && listConstruction.get(i).getCouleur()==moteur.get_Couleur_IA() && listConstruction.get(i).isNew())
				constructions.get(i).setBright(new Vector3f(0.9f,0.0f,0.0f));
			constructions.get(i).setColour(listConstruction.get(i).getCouleur());
			constructions.get(i).setType(listConstruction.get(i).getTypeBatiment());
			Vector3f worldPos = new Vector3f(grid.toWorldPos(listConstruction.get(i).getPosition(),listConstruction.get(i).getNiveau()-1));
			constructions.get(i).setPosition(worldPos);
			if(listConstruction.get(i).getNiveau()>1 && constructions.get(i).getType_Batiment() == Type_Batiment.HUTTE){
				for(int j=0;j<listConstruction.get(i).getNiveau();j++){
					constructions.get(i).setPosition(new Vector3f(worldPos.x+pos[j].x,worldPos.y,worldPos.z+pos[j].y));
					renderer.draw(constructions.get(i),shader);
				}
			}
			else
				renderer.draw(constructions.get(i),shader);
		}
	}
	
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
	
	public void timerStop(){
		timer.stop();
	}
	
	public void init(JFrame menu,JFrame frame,Moteur m,Canvas canvas,JPanelPioche Tuile_Pioche){
		this.menu = menu;
		this.frame = frame;
		Window.createDislay();
		this.moteur = m;
		this.canvas = canvas;
		camera = new Camera();
		loader = new Loader();
		shader = new Shader();
		renderer = new Renderer(shader,camera);
		
		num_vue = 0;
		
		FPS.start(frame);
		Tile = new GraphicTile(new Tuile(Case.Type.MONTAGNE,Case.Type.SABLE),loader,new Vector3f(0,0,0),90);
		GraphicTile.setTexture(loader);
		Ecouteur_Boutons.setTile(loader,Tile);
		Tiles = new ArrayList<GraphicTile>();
		constructions = new ArrayList<GraphicConstruction>();
		
		GraphicConstruction.setUp(loader);
		Construction = new GraphicConstruction(GraphicType.HUT,new Vector3f(0,0,0));
		
		Construction.init();
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
		ecouteurSouris = new EcouteurDeSourisTerrain(moteur,picker,grid,Tile, Tiles, Construction, constructions,marking_menu,Tuile_Pioche);
		
		//Init pos
		pos = new Vector2f[9];
		pos[0] = new Vector2f(-Grid.WIDTH_OF_HEXA/2+Grid.WIDTH_OF_HEXA/4,0);
		pos[1] = new Vector2f(Grid.WIDTH_OF_HEXA/2-Grid.WIDTH_OF_HEXA/4,0);
		pos[2] = new Vector2f(0,-Grid.HEIGHT_OF_HEXA/2+Grid.HEIGHT_OF_HEXA/4);
		pos[3] = new Vector2f(0,Grid.HEIGHT_OF_HEXA/2-Grid.HEIGHT_OF_HEXA/4);
		pos[4] = new Vector2f(-Grid.WIDTH_OF_HEXA/2+Grid.WIDTH_OF_HEXA/4,-Grid.WIDTH_OF_HEXA/2+Grid.WIDTH_OF_HEXA/4);
		pos[5] = new Vector2f(Grid.WIDTH_OF_HEXA/2-Grid.WIDTH_OF_HEXA/4,Grid.WIDTH_OF_HEXA/2-Grid.WIDTH_OF_HEXA/4);
		pos[6] = new Vector2f(0,0);
		pos[7] = new Vector2f(0,0);
		pos[8] = new Vector2f(0,0);
		
		timer = new TimerOpenGL();
		timer.addObserver(this);
		this.canvas.addMouseListener(ecouteurSouris);
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		FPS.updateFPS();
		Game.increaseDelay();
		updateCamera();
		camera.move();
		
		picker.update(Tile.getHeight());
		//Vector3f point = picker.getCurrentObjectPoint();

		
		renderer.prepare();
		
		shader.start();
		shader.loadLights(lights);
		
		shader.loadViewMatrix(camera);
		
		
		if(moteur.get_etat_jeu() == Phase_Jeu.CONSTRUIRE_BATIMENT && Ecouteur_Boutons.isPick())
			renderer.draw(Construction,shader);
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
		if(keys[1]){
			camera.decreaseZ((float) Math.cos(Math.toRadians(camera.getAngleAroundPivot())));
			camera.decreaseX((float) Math.sin(Math.toRadians(camera.getAngleAroundPivot())));
		}
		if(keys[2]){
			camera.decreaseZ((float) Math.sin(Math.toRadians(camera.getAngleAroundPivot())));
			camera.increaseX((float) Math.cos(Math.toRadians(camera.getAngleAroundPivot())));
		}
		if(keys[3]){
			camera.increaseZ((float) Math.sin(Math.toRadians(camera.getAngleAroundPivot())));
			camera.decreaseX((float) Math.cos(Math.toRadians(camera.getAngleAroundPivot())));
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		Menu_circulaire_creation.setDraw(false);
		switch(e.getKeyCode()){
		case KeyEvent.VK_Z:
		case KeyEvent.VK_UP:
			keys[0] = true;
			break;
		case KeyEvent.VK_S:
		case KeyEvent.VK_DOWN:
			keys[1] = true;
			break;
		case KeyEvent.VK_Q:
		case KeyEvent.VK_LEFT:
			keys[2] = true;
			break;
		case KeyEvent.VK_D:
		case KeyEvent.VK_RIGHT:
			keys[3] = true;
			break;
		case KeyEvent.VK_A:
			num_vue = (num_vue - 1 + Camera.nb_vues) % Camera.nb_vues;
			camera.updateVue(num_vue);
			break;
		case KeyEvent.VK_E:
			num_vue = (num_vue + 1) % Camera.nb_vues;
			camera.updateVue(num_vue);
			break;
		case KeyEvent.VK_SPACE:
			camera.reset();
			num_vue = 0;
			break;
		case KeyEvent.VK_ESCAPE:
			frame.setEnabled(false);
			frame.add(new Echap(frame,menu,this));
			break;
		default:
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_Z:
		case KeyEvent.VK_UP:
			keys[0] = false;
			break;
		case KeyEvent.VK_S:
		case KeyEvent.VK_DOWN:
			keys[1] = false;
			break;
		case KeyEvent.VK_Q:
		case KeyEvent.VK_LEFT:
			keys[2] = false;
			break;
		case KeyEvent.VK_D:
		case KeyEvent.VK_RIGHT:
			keys[3] = false;
			break;
		default:
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}
	
	public Camera getCamera(){
		return camera;
	}
	
	public Moteur getMoteur(){
		return moteur;
	}
}
