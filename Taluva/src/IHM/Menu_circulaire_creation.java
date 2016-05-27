package IHM;

import java.awt.Point;
import java.util.ArrayList;

import loaders.Loader;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import terrain.Case;
import terrain.Case.Type_Batiment;
import terrain.Cite;
import utils.Grid;
import utils.Grid.Coords;
import entities.Camera;
import entities.GraphicConstruction;
import gui.Drawable;

import Ecouteur.ButtonConstruction;
import Ecouteur.ButtonExtension;
import Moteur.Moteur;
import Moteur.Phase.Phase_Jeu;

public class Menu_circulaire_creation {
	Moteur moteur;
	ButtonConstruction bouton_hute,bouton_tour,bouton_temple;
	ButtonExtension bouton_foret,bouton_plage,bouton_montagne,bouton_plaine,bouton_lac;
	Grid grille;
	Drawable drawable;
	Drawable drawableExtension;
	float rayon;
	private static boolean draw = false;
	private static boolean drawExtension= false;
	private Camera camera;
	
	
	public Menu_circulaire_creation(Moteur moteur,Loader loader,GraphicConstruction Construction,Grid grille,Camera camera){
		this.moteur=moteur;
		bouton_hute = new ButtonConstruction(loader.loadTexture("Button_Hut.png"),new Vector2f(Display.getWidth()-150,50),new Vector2f(36,36),"hut",Construction,moteur);
		bouton_tour = new ButtonConstruction(loader.loadTexture("Button_tower.png"),new Vector2f(Display.getWidth()-150,50),new Vector2f(36,36),"tower",Construction,moteur);
		bouton_temple = new ButtonConstruction(loader.loadTexture("Button_Temple.png"),new Vector2f(Display.getWidth()-150,50),new Vector2f(36,36),"temple",Construction,moteur);
		
		bouton_foret = new ButtonExtension(loader.loadTexture("Tile/F.png"),new Vector2f(Display.getWidth()-150,50),new Vector2f(36,36),Case.Type.FORET,moteur,Construction);
		bouton_plage = new ButtonExtension(loader.loadTexture("Tile/S.png"),new Vector2f(Display.getWidth()-150,50),new Vector2f(36,36),Case.Type.SABLE,moteur,Construction);
		bouton_montagne = new ButtonExtension(loader.loadTexture("Tile/M.png"),new Vector2f(Display.getWidth()-150,50),new Vector2f(36,36),Case.Type.MONTAGNE,moteur,Construction);
		bouton_plaine = new ButtonExtension(loader.loadTexture("Tile/P.png"),new Vector2f(Display.getWidth()-150,50),new Vector2f(36,36),Case.Type.PLAINE,moteur,Construction);
		bouton_lac = new ButtonExtension(loader.loadTexture("Tile/L.png"),new Vector2f(Display.getWidth()-150,50),new Vector2f(36,36),Case.Type.LAC,moteur,Construction);
		this.grille=grille;
		drawable=new Drawable(loader);
		drawableExtension = new Drawable(loader);
		drawable.bindButton(bouton_hute);
		drawable.bindButton(bouton_tour);
		drawable.bindButton(bouton_temple);

		drawableExtension.bindButton(bouton_foret);
		drawableExtension.bindButton(bouton_plage);
		drawableExtension.bindButton(bouton_montagne);
		drawableExtension.bindButton(bouton_plaine);
		drawableExtension.bindButton(bouton_lac);
		rayon=Grid.HEIGHT_OF_HEXA*5;
		this.camera = camera;
		
	}
	
	private void setDimension(){
		int ratio = 100;
		if(camera.getDistanceFromPivot() < 100)
			ratio = 50;
			
		bouton_hute.setDimension(new Vector2f(36/(camera.getDistanceFromPivot()/ratio),36/(camera.getDistanceFromPivot()/ratio)));
		bouton_tour.setDimension(new Vector2f(36/(camera.getDistanceFromPivot()/ratio),36/(camera.getDistanceFromPivot()/ratio)));
		bouton_temple.setDimension(new Vector2f(36/(camera.getDistanceFromPivot()/ratio),36/(camera.getDistanceFromPivot()/ratio)));
		
		bouton_foret.setDimension(new Vector2f(36/(camera.getDistanceFromPivot()/ratio),36/(camera.getDistanceFromPivot()/ratio)));
		bouton_plage.setDimension(new Vector2f(36/(camera.getDistanceFromPivot()/ratio),36/(camera.getDistanceFromPivot()/ratio)));
		bouton_montagne.setDimension(new Vector2f(36/(camera.getDistanceFromPivot()/ratio),36/(camera.getDistanceFromPivot()/ratio)));
		bouton_plaine.setDimension(new Vector2f(36/(camera.getDistanceFromPivot()/ratio),36/(camera.getDistanceFromPivot()/ratio)));
		bouton_lac.setDimension(new Vector2f(36/(camera.getDistanceFromPivot()/ratio),36/(camera.getDistanceFromPivot()/ratio)));

		rayon = Grid.HEIGHT_OF_HEXA*5/(camera.getDistanceFromPivot()/(ratio-15));
	}
	
	public void tuile_vide_cliquer(Vector3f picker){
		setDimension();
		float s_x,s_y;
		int i,j;
		Coords coord; 
		s_x=picker.x;
		s_y=picker.z;
		coord = grille.snap(new Vector3f(s_x,0,s_y));
		draw = false;
		if(coord != null && moteur.get_etat_jeu() == Phase_Jeu.CONSTRUIRE_BATIMENT){
			i=coord.indices.x;
			j=coord.indices.y;
			if(moteur.getTerrain().getCase(i, j).ajout_batiment_autorise()){
				draw = true;
				ButtonConstruction.setPoint(new Point(i,j));
				//etat bouton hutte
				bouton_hute.setGrey(!(moteur.getTerrain().placement_batiment_autorise(Type_Batiment.HUTTE,moteur.get_Jcourant().getCouleur() , coord.indices)));
				//gerer la position du bouton hutte
				bouton_hute.setPosition(new Vector2f(Mouse.getX(),Display.getHeight()- Mouse.getY()-rayon));
				
				//etat bouton temple
				bouton_temple.setGrey(!(moteur.getTerrain().placement_batiment_autorise(Type_Batiment.TEMPLE,moteur.get_Jcourant().getCouleur() , coord.indices)));
				//gerer la position du bouton temple
				bouton_temple.setPosition(new Vector2f(Mouse.getX()-rayon*(float) Math.sin(Math.PI*2f/3f),Display.getHeight()- Mouse.getY()-rayon*(float) Math.cos(Math.PI*2f/3f)));
				
				//etat bouton tour
				bouton_tour.setGrey(!(moteur.getTerrain().placement_batiment_autorise(Type_Batiment.TOUR,moteur.get_Jcourant().getCouleur() , coord.indices)));
				//gerer la position du bouton tour
				bouton_tour.setPosition(new Vector2f(Mouse.getX()-rayon*(float) Math.sin(Math.PI*4f/3f),Display.getHeight()- Mouse.getY()-rayon*(float) Math.cos(Math.PI*4f/3f)));
	
				
			}
			else{
				ButtonExtension.setP(new Point(i,j));
				Cite cite = moteur.getTerrain().getCite(coord.indices);
				if(cite.getCouleur()==moteur.get_Jcourant().getCouleur()){
					drawExtension = true;
					//etat et position du bouton foret de l'extention
					ArrayList<Point> liste = moteur.getTerrain().getPts_extension_cite(cite, Case.Type.FORET);
					bouton_foret.setGrey(liste.isEmpty());
					bouton_foret.setPosition(new Vector2f(Mouse.getX(),Display.getHeight()- Mouse.getY()-rayon));
					
					//etat et position du bouton montagne de l'extention
					liste = moteur.getTerrain().getPts_extension_cite(cite, Case.Type.MONTAGNE);	
					bouton_montagne.setGrey(liste.isEmpty());
					bouton_montagne.setPosition(new Vector2f(Mouse.getX()-rayon*(float) Math.sin(Math.PI*2f/5f),Display.getHeight()- Mouse.getY()-rayon*(float) Math.cos(Math.PI*2f/5f)));
					
					//etat et position du bouton sable de l'extention
					liste = moteur.getTerrain().getPts_extension_cite(cite, Case.Type.SABLE);	
					bouton_plage.setGrey(liste.isEmpty());
					bouton_plage.setPosition(new Vector2f(Mouse.getX()-rayon*(float) Math.sin(Math.PI*4f/5f),Display.getHeight()- Mouse.getY()-rayon*(float) Math.cos(Math.PI*4f/5f)));
					
					//etat et position du bouton plaine de l'extention
					liste = moteur.getTerrain().getPts_extension_cite(cite, Case.Type.PLAINE);	
					bouton_plaine.setGrey(liste.isEmpty());
					bouton_plaine.setPosition(new Vector2f(Mouse.getX()-rayon*(float) Math.sin(Math.PI*6f/5f),Display.getHeight()- Mouse.getY()-rayon*(float) Math.cos(Math.PI*6f/5f)));
					
					liste = moteur.getTerrain().getPts_extension_cite(cite, Case.Type.LAC);	
					bouton_lac.setGrey(liste.isEmpty());
					bouton_lac.setPosition(new Vector2f(Mouse.getX()-rayon*(float) Math.sin(Math.PI*8f/5f),Display.getHeight()- Mouse.getY()-rayon*(float) Math.cos(Math.PI*8f/5f)));
				}
			}
		}
		
	}

	public static void setDraw(boolean d){
		draw = d;
		drawExtension = d;
	}
	
	public void draw(){
		if(ButtonConstruction.isClicked() || drawExtension)
			draw = false;
		
		if(ButtonExtension.isClicked() || draw)
			drawExtension = false;
		
		if(draw){
			bouton_hute.update();
			bouton_temple.update();
			bouton_tour.update();
			drawable.draw();
		}
		if(drawExtension){
			bouton_foret.update();
			bouton_montagne.update();
			bouton_plage.update();
			bouton_plaine.update();
			bouton_lac.update();
			drawableExtension.draw();
		}
	}
	
	public void cleanUp(){
		drawable.cleanUp();
		drawableExtension.cleanUp();
	}
	
}
