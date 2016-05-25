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

import entities.GraphicConstruction;
import gui.Drawable;

import Ecouteur.ButtonConstruction;
import Moteur.Moteur;
import Moteur.Phase.Phase_Jeu;

public class Menu_circulaire_creation {
	Moteur moteur;
	ButtonConstruction bouton_hute,bouton_tour,bouton_temple,bouton_foret,bouton_plage,bouton_montagne,bouton_plaine,bouton_lac;
	Grid grille;
	Drawable drawable;
	float rayon;
	private boolean draw = false;
	
	
	public Menu_circulaire_creation(Moteur moteur,Loader loader,GraphicConstruction Construction,Grid grille){
		this.moteur=moteur;
		bouton_hute = new ButtonConstruction(loader.loadTexture("Button_Hut.png"),new Vector2f(Display.getWidth()-150,50),new Vector2f(36,36),"hut",Construction,moteur);
		bouton_tour = new ButtonConstruction(loader.loadTexture("Button_tower.png"),new Vector2f(Display.getWidth()-150,50),new Vector2f(36,36),"tower",Construction,moteur);
		bouton_temple = new ButtonConstruction(loader.loadTexture("Button_Temple.png"),new Vector2f(Display.getWidth()-150,50),new Vector2f(36,36),"temple",Construction,moteur);
		
		bouton_foret = new ButtonConstruction(loader.loadTexture("Button_Hut.png"),new Vector2f(Display.getWidth()-150,50),new Vector2f(36,36),"hut",Construction,moteur);
		bouton_plage = new ButtonConstruction(loader.loadTexture("Button_tower.png"),new Vector2f(Display.getWidth()-150,50),new Vector2f(36,36),"tower",Construction,moteur);
		bouton_montagne = new ButtonConstruction(loader.loadTexture("Button_Temple.png"),new Vector2f(Display.getWidth()-150,50),new Vector2f(36,36),"temple",Construction,moteur);
		bouton_plaine = new ButtonConstruction(loader.loadTexture("Button_Hut.png"),new Vector2f(Display.getWidth()-150,50),new Vector2f(36,36),"hut",Construction,moteur);
		bouton_lac = new ButtonConstruction(loader.loadTexture("Button_tower.png"),new Vector2f(Display.getWidth()-150,50),new Vector2f(36,36),"tower",Construction,moteur);
		this.grille=grille;
		drawable=new Drawable(loader);
		drawable.bindButton(bouton_hute);
		drawable.bindButton(bouton_tour);
		drawable.bindButton(bouton_temple);
		rayon=Grid.HEIGHT_OF_HEXA*5;
		
	}
	
	public void tuile_vide_cliquer(Vector3f picker){
		float s_x,s_y;
		int i,j;
		Coords coord; 
		s_x=picker.x;
		s_y=picker.z;
		coord = grille.snap(new Vector3f(s_x,0,s_y));
		i=coord.indices.x;
		j=coord.indices.y;
		draw = false;
		if(moteur.get_etat_jeu() == Phase_Jeu.CONSTRUIRE_BATIMENT){
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
				Cite cite = moteur.getTerrain().getCite(coord.indices);
				if(cite.getCouleur()==moteur.get_Jcourant().getCouleur()){
					
					//etat et position du bouton foret de l'extention
					ArrayList<Point> liste = moteur.getTerrain().getPts_extension_cite(cite, Case.Type.FORET);
					bouton_foret.setGrey(liste==null);
					bouton_foret.setPosition(new Vector2f(coord.worldPos.x,coord.worldPos.z-rayon));
					
					//etat et position du bouton montagne de l'extention
					liste = moteur.getTerrain().getPts_extension_cite(cite, Case.Type.MONTAGNE);	
					bouton_montagne.setGrey(liste==null);
					bouton_montagne.setPosition(new Vector2f(coord.worldPos.x-rayon*(float) Math.sin(Math.PI*2f/5f),coord.worldPos.z-rayon*(float) Math.cos(Math.PI*2f/5f)));
					
					//etat et position du bouton sable de l'extention
					liste = moteur.getTerrain().getPts_extension_cite(cite, Case.Type.SABLE);	
					bouton_plage.setGrey(liste==null);
					bouton_plage.setPosition(new Vector2f(coord.worldPos.x-rayon*(float) Math.sin(Math.PI*4f/5f),coord.worldPos.z-rayon*(float) Math.cos(Math.PI*4f/5f)));
					
					//etat et position du bouton plaine de l'extention
					liste = moteur.getTerrain().getPts_extension_cite(cite, Case.Type.PLAINE);	
					bouton_montagne.setGrey(liste.isEmpty());
					bouton_montagne.setPosition(new Vector2f(coord.worldPos.x-rayon*(float) Math.sin(Math.PI*6f/5f),coord.worldPos.z-rayon*(float) Math.cos(Math.PI*6f/5f)));
					
					liste = moteur.getTerrain().getPts_extension_cite(cite, Case.Type.LAC);	
					bouton_lac.setGrey(liste.isEmpty());
					bouton_lac.setPosition(new Vector2f(coord.worldPos.x-rayon*(float) Math.sin(Math.PI*8f/5f),coord.worldPos.z-rayon*(float) Math.cos(Math.PI*8f/5f)));
				}
			}
		}
	}

	public void draw(){
		if(draw){
			bouton_hute.update();
			bouton_temple.update();
			bouton_tour.update();
			drawable.draw();
		}
	}
	
	public void cleanUp(){
		drawable.cleanUp();
	}
	
}
