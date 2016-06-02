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
import Ecouteur.Ecouteur_Boutons;
import Moteur.Moteur;
import Moteur.Phase.Phase_Jeu;

public class Menu_circulaire_creation {
	Moteur moteur;
	ButtonConstruction bouton_huteC,bouton_tourC,bouton_templeC,bouton_huteV,bouton_tourV,bouton_templeV,bouton_huteE,bouton_tourE,bouton_templeE,bouton_huteB,bouton_tourB,bouton_templeB;
	ButtonExtension bouton_foret,bouton_plage,bouton_montagne,bouton_plaine,bouton_lac;
	Grid grille;
	Drawable drawableC,drawableV,drawableB,drawableE;
	Drawable drawableExtension;
	float rayon;
	private static boolean draw = false;
	private static boolean drawExtension= false;
	private Camera camera;
	
	
	public Menu_circulaire_creation(Moteur moteur,Loader loader,GraphicConstruction Construction,Grid grille,Camera camera){
		this.moteur=moteur;
		bouton_huteC = new ButtonConstruction(loader.loadTexture("Icons/Chinese/Hut.png"),new Vector2f(Display.getWidth()-150,50),new Vector2f(36,36),"hut",Construction,moteur);
		bouton_tourC = new ButtonConstruction(loader.loadTexture("Icons/Chinese/Tower.png"),new Vector2f(Display.getWidth()-150,50),new Vector2f(36,36),"tower",Construction,moteur);
		bouton_templeC = new ButtonConstruction(loader.loadTexture("Icons/Chinese/Temple.png"),new Vector2f(Display.getWidth()-150,50),new Vector2f(36,36),"temple",Construction,moteur);
		
		bouton_huteV = new ButtonConstruction(loader.loadTexture("Icons/Viking/Hut.png"),new Vector2f(Display.getWidth()-150,50),new Vector2f(36,36),"hut",Construction,moteur);
		bouton_tourV = new ButtonConstruction(loader.loadTexture("Icons/Viking/Tower.png"),new Vector2f(Display.getWidth()-150,50),new Vector2f(36,36),"tower",Construction,moteur);
		bouton_templeV = new ButtonConstruction(loader.loadTexture("Icons/Viking/Temple.png"),new Vector2f(Display.getWidth()-150,50),new Vector2f(36,36),"temple",Construction,moteur);
		
		bouton_huteE = new ButtonConstruction(loader.loadTexture("Icons/Europe/Hut.png"),new Vector2f(Display.getWidth()-150,50),new Vector2f(36,36),"hut",Construction,moteur);
		bouton_tourE = new ButtonConstruction(loader.loadTexture("Icons/Europe/Tower.png"),new Vector2f(Display.getWidth()-150,50),new Vector2f(36,36),"tower",Construction,moteur);
		bouton_templeE = new ButtonConstruction(loader.loadTexture("Icons/Europe/Temple.png"),new Vector2f(Display.getWidth()-150,50),new Vector2f(36,36),"temple",Construction,moteur);
		
		bouton_huteB = new ButtonConstruction(loader.loadTexture("Icons/Babylon/Hut.png"),new Vector2f(Display.getWidth()-150,50),new Vector2f(36,36),"hut",Construction,moteur);
		bouton_tourB = new ButtonConstruction(loader.loadTexture("Icons/Babylon/Tower.png"),new Vector2f(Display.getWidth()-150,50),new Vector2f(36,36),"tower",Construction,moteur);
		bouton_templeB = new ButtonConstruction(loader.loadTexture("Icons/Babylon/Temple.png"),new Vector2f(Display.getWidth()-150,50),new Vector2f(36,36),"temple",Construction,moteur);
		
		bouton_foret = new ButtonExtension(loader.loadTexture("Tile/F.png"),new Vector2f(Display.getWidth()-150,50),new Vector2f(36,36),Case.Type.FORET,moteur,Construction);
		bouton_plage = new ButtonExtension(loader.loadTexture("Tile/S.png"),new Vector2f(Display.getWidth()-150,50),new Vector2f(36,36),Case.Type.SABLE,moteur,Construction);
		bouton_montagne = new ButtonExtension(loader.loadTexture("Tile/M.png"),new Vector2f(Display.getWidth()-150,50),new Vector2f(36,36),Case.Type.MONTAGNE,moteur,Construction);
		bouton_plaine = new ButtonExtension(loader.loadTexture("Tile/P.png"),new Vector2f(Display.getWidth()-150,50),new Vector2f(36,36),Case.Type.PLAINE,moteur,Construction);
		bouton_lac = new ButtonExtension(loader.loadTexture("Tile/L.png"),new Vector2f(Display.getWidth()-150,50),new Vector2f(36,36),Case.Type.LAC,moteur,Construction);
		this.grille=grille;
		drawableC=new Drawable(loader);
		drawableB=new Drawable(loader);
		drawableV=new Drawable(loader);
		drawableE=new Drawable(loader);
		drawableExtension = new Drawable(loader);
		drawableC.bindButton(bouton_huteC);
		drawableC.bindButton(bouton_tourC);
		drawableC.bindButton(bouton_templeC);
		
		drawableB.bindButton(bouton_huteB);
		drawableB.bindButton(bouton_tourB);
		drawableB.bindButton(bouton_templeB);
		
		drawableV.bindButton(bouton_huteV);
		drawableV.bindButton(bouton_tourV);
		drawableV.bindButton(bouton_templeV);
		
		drawableE.bindButton(bouton_huteE);
		drawableE.bindButton(bouton_tourE);
		drawableE.bindButton(bouton_templeE);

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
		float distance = camera.getDistanceFromPivot();
		if(camera.getDistanceFromPivot()<50){
			distance = 50;
		}
			bouton_huteC.setDimension(new Vector2f(50/(distance/ratio),50/(distance/ratio)));
			bouton_tourC.setDimension(new Vector2f(50/(distance/ratio),50/(distance/ratio)));
			bouton_templeC.setDimension(new Vector2f(50/(distance/ratio),50/(distance/ratio)));
			
			bouton_huteV.setDimension(new Vector2f(50/(distance/ratio),50/(distance/ratio)));
			bouton_tourV.setDimension(new Vector2f(50/(distance/ratio),50/(distance/ratio)));
			bouton_templeV.setDimension(new Vector2f(50/(distance/ratio),50/(distance/ratio)));
			
			bouton_huteB.setDimension(new Vector2f(50/(distance/ratio),50/(distance/ratio)));
			bouton_tourB.setDimension(new Vector2f(50/(distance/ratio),50/(distance/ratio)));
			bouton_templeB.setDimension(new Vector2f(50/(distance/ratio),50/(distance/ratio)));
			
			bouton_huteE.setDimension(new Vector2f(50/(distance/ratio),50/(distance/ratio)));
			bouton_tourE.setDimension(new Vector2f(50/(distance/ratio),50/(distance/ratio)));
			bouton_templeE.setDimension(new Vector2f(50/(distance/ratio),50/(distance/ratio)));
			
			bouton_foret.setDimension(new Vector2f(50/(distance/ratio),50/(distance/ratio)));
			bouton_plage.setDimension(new Vector2f(50/(distance/ratio),50/(distance/ratio)));
			bouton_montagne.setDimension(new Vector2f(50/(distance/ratio),50/(distance/ratio)));
			bouton_plaine.setDimension(new Vector2f(50/(distance/ratio),50/(distance/ratio)));
			bouton_lac.setDimension(new Vector2f(50/(distance/ratio),50/(distance/ratio)));
	
			rayon = Grid.HEIGHT_OF_HEXA*6/(distance/(ratio));
		
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
		
		if(coord!=null && (moteur.getTerrain().getCase(coord.indices).est_Vide() || moteur.getTerrain().getCase(coord.indices).est_Libre()))
			drawExtension = false;
		
		if(coord != null && moteur.get_etat_jeu() == Phase_Jeu.CONSTRUIRE_BATIMENT && !Ecouteur_Boutons.isPick()){
			i=coord.indices.x;
			j=coord.indices.y;
			if(moteur.getTerrain().getCase(i, j).ajout_batiment_autorise()){
				draw = true;
				ButtonConstruction.setPoint(new Point(i,j));
				//etat bouton hutte
				bouton_huteC.setGrey(!(moteur.getTerrain().placement_batiment_autorise(Type_Batiment.HUTTE,moteur.get_Jcourant().getCouleur() , coord.indices) && moteur.get_Jcourant().getHutte()>0));
				//gerer la position du bouton hutte
				bouton_huteC.setPosition(new Vector2f(Mouse.getX()-rayon/2,Display.getHeight()- Mouse.getY()-rayon/2-rayon));
				
				//etat bouton temple
				bouton_templeC.setGrey(!(moteur.getTerrain().placement_batiment_autorise(Type_Batiment.TEMPLE,moteur.get_Jcourant().getCouleur() , coord.indices) && moteur.get_Jcourant().getTemple()>0));
				//gerer la position du bouton temple
				bouton_templeC.setPosition(new Vector2f(Mouse.getX()-rayon/2-rayon*(float) Math.sin(Math.PI*2f/3f),Display.getHeight()- Mouse.getY()-rayon/2-rayon*(float) Math.cos(Math.PI*2f/3f)));
				
				//etat bouton tour
				bouton_tourC.setGrey(!(moteur.getTerrain().placement_batiment_autorise(Type_Batiment.TOUR,moteur.get_Jcourant().getCouleur() , coord.indices) && moteur.get_Jcourant().getTour()>0));
				//gerer la position du bouton tour
				bouton_tourC.setPosition(new Vector2f(Mouse.getX()-rayon/2-rayon*(float) Math.sin(Math.PI*4f/3f),Display.getHeight()- Mouse.getY()-rayon/2-rayon*(float) Math.cos(Math.PI*4f/3f)));

				bouton_huteV.setGrey(!(moteur.getTerrain().placement_batiment_autorise(Type_Batiment.HUTTE,moteur.get_Jcourant().getCouleur() , coord.indices) && moteur.get_Jcourant().getHutte()>0));
				//gerer la position du bouton hutte
				bouton_huteV.setPosition(new Vector2f(Mouse.getX()-rayon/2,Display.getHeight()- Mouse.getY()-rayon/2-rayon));
				
				//etat bouton temple
				bouton_templeV.setGrey(!(moteur.getTerrain().placement_batiment_autorise(Type_Batiment.TEMPLE,moteur.get_Jcourant().getCouleur() , coord.indices) && moteur.get_Jcourant().getTemple()>0));
				//gerer la position du bouton temple
				bouton_templeV.setPosition(new Vector2f(Mouse.getX()-rayon/2-rayon*(float) Math.sin(Math.PI*2f/3f),Display.getHeight()- Mouse.getY()-rayon/2-rayon*(float) Math.cos(Math.PI*2f/3f)));
				
				//etat bouton tour
				bouton_tourV.setGrey(!(moteur.getTerrain().placement_batiment_autorise(Type_Batiment.TOUR,moteur.get_Jcourant().getCouleur() , coord.indices) && moteur.get_Jcourant().getTour()>0));
				//gerer la position du bouton tour
				bouton_tourV.setPosition(new Vector2f(Mouse.getX()-rayon/2-rayon*(float) Math.sin(Math.PI*4f/3f),Display.getHeight()- Mouse.getY()-rayon/2-rayon*(float) Math.cos(Math.PI*4f/3f)));
	
				bouton_huteE.setGrey(!(moteur.getTerrain().placement_batiment_autorise(Type_Batiment.HUTTE,moteur.get_Jcourant().getCouleur() , coord.indices) && moteur.get_Jcourant().getHutte()>0));
				//gerer la position du bouton hutte
				bouton_huteE.setPosition(new Vector2f(Mouse.getX()-rayon/2,Display.getHeight()- Mouse.getY()-rayon/2-rayon));
				
				//etat bouton temple
				bouton_templeE.setGrey(!(moteur.getTerrain().placement_batiment_autorise(Type_Batiment.TEMPLE,moteur.get_Jcourant().getCouleur() , coord.indices) && moteur.get_Jcourant().getTemple()>0));
				//gerer la position du bouton temple
				bouton_templeE.setPosition(new Vector2f(Mouse.getX()-rayon/2-rayon*(float) Math.sin(Math.PI*2f/3f),Display.getHeight()- Mouse.getY()-rayon/2-rayon*(float) Math.cos(Math.PI*2f/3f)));
				
				//etat bouton tour
				bouton_tourE.setGrey(!(moteur.getTerrain().placement_batiment_autorise(Type_Batiment.TOUR,moteur.get_Jcourant().getCouleur() , coord.indices) && moteur.get_Jcourant().getTour()>0));
				//gerer la position du bouton tour
				bouton_tourE.setPosition(new Vector2f(Mouse.getX()-rayon/2-rayon*(float) Math.sin(Math.PI*4f/3f),Display.getHeight()- Mouse.getY()-rayon/2-rayon*(float) Math.cos(Math.PI*4f/3f)));
	
				bouton_huteB.setGrey(!(moteur.getTerrain().placement_batiment_autorise(Type_Batiment.HUTTE,moteur.get_Jcourant().getCouleur() , coord.indices) && moteur.get_Jcourant().getHutte()>0));
				//gerer la position du bouton hutte
				bouton_huteB.setPosition(new Vector2f(Mouse.getX()-rayon/2,Display.getHeight()- Mouse.getY()-rayon/2-rayon));
				
				//etat bouton temple
				bouton_templeB.setGrey(!(moteur.getTerrain().placement_batiment_autorise(Type_Batiment.TEMPLE,moteur.get_Jcourant().getCouleur() , coord.indices) && moteur.get_Jcourant().getTemple()>0));
				//gerer la position du bouton temple
				bouton_templeB.setPosition(new Vector2f(Mouse.getX()-rayon/2-rayon*(float) Math.sin(Math.PI*2f/3f),Display.getHeight()- Mouse.getY()-rayon/2-rayon*(float) Math.cos(Math.PI*2f/3f)));
				
				//etat bouton tour
				bouton_tourB.setGrey(!(moteur.getTerrain().placement_batiment_autorise(Type_Batiment.TOUR,moteur.get_Jcourant().getCouleur() , coord.indices) && moteur.get_Jcourant().getTour()>0));
				//gerer la position du bouton tour
				bouton_tourB.setPosition(new Vector2f(Mouse.getX()-rayon/2-rayon*(float) Math.sin(Math.PI*4f/3f),Display.getHeight()- Mouse.getY()-rayon/2-rayon*(float) Math.cos(Math.PI*4f/3f)));
	
				
			}
			else{
				ButtonExtension.setP(new Point(i,j));
				Cite cite = moteur.getTerrain().getCite(coord.indices);
				if(cite.getCouleur()==moteur.get_Jcourant().getCouleur()){
					drawExtension = true;
					//etat et position du bouton foret de l'extention
					ArrayList<Point> liste = moteur.getTerrain().getPts_extension_cite(cite, Case.Type.FORET);
					int n = moteur.getTerrain().nb_huttes_extension(coord.indices, Case.Type.FORET);
					bouton_foret.setGrey(liste.isEmpty() || n>moteur.get_Jcourant().getHutte());
					bouton_foret.setPosition(new Vector2f(Mouse.getX()-rayon/2,Display.getHeight()- Mouse.getY()-rayon/2-rayon));
					
					//etat et position du bouton montagne de l'extention
					liste = moteur.getTerrain().getPts_extension_cite(cite, Case.Type.MONTAGNE);
					n = moteur.getTerrain().nb_huttes_extension(coord.indices, Case.Type.MONTAGNE);
					bouton_montagne.setGrey(liste.isEmpty() || n>moteur.get_Jcourant().getHutte());
					bouton_montagne.setPosition(new Vector2f(Mouse.getX()-rayon/2-rayon*(float) Math.sin(Math.PI*2f/5f),Display.getHeight()- Mouse.getY()-rayon/2-rayon*(float) Math.cos(Math.PI*2f/5f)));
					
					//etat et position du bouton sable de l'extention
					liste = moteur.getTerrain().getPts_extension_cite(cite, Case.Type.SABLE);	
					n = moteur.getTerrain().nb_huttes_extension(coord.indices, Case.Type.SABLE);
					bouton_plage.setGrey(liste.isEmpty() || n>moteur.get_Jcourant().getHutte());
					bouton_plage.setPosition(new Vector2f(Mouse.getX()-rayon/2-rayon*(float) Math.sin(Math.PI*4f/5f),Display.getHeight()- Mouse.getY()-rayon/2-rayon*(float) Math.cos(Math.PI*4f/5f)));
					
					//etat et position du bouton plaine de l'extention
					liste = moteur.getTerrain().getPts_extension_cite(cite, Case.Type.PLAINE);
					n = moteur.getTerrain().nb_huttes_extension(coord.indices, Case.Type.PLAINE);
					bouton_plaine.setGrey(liste.isEmpty() || n>moteur.get_Jcourant().getHutte());
					bouton_plaine.setPosition(new Vector2f(Mouse.getX()-rayon/2-rayon*(float) Math.sin(Math.PI*6f/5f),Display.getHeight()- Mouse.getY()-rayon/2-rayon*(float) Math.cos(Math.PI*6f/5f)));
					
					liste = moteur.getTerrain().getPts_extension_cite(cite, Case.Type.LAC);
					n = moteur.getTerrain().nb_huttes_extension(coord.indices, Case.Type.LAC);
					bouton_lac.setGrey(liste.isEmpty() || n>moteur.get_Jcourant().getHutte());
					bouton_lac.setPosition(new Vector2f(Mouse.getX()-rayon/2-rayon*(float) Math.sin(Math.PI*8f/5f),Display.getHeight()- Mouse.getY()-rayon/2-rayon*(float) Math.cos(Math.PI*8f/5f)));
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
		
		if(Ecouteur_Boutons.isClicked()){
			draw = false;
			drawExtension = false;
		}
		
		if(draw){
			bouton_huteC.update();
			bouton_templeC.update();
			bouton_tourC.update();
			switch(moteur.get_Jcourant().getCouleur()){
			case JAUNE:
				drawableC.draw();
				break;
			case BLEU:
				drawableE.draw();
				break;
			case ROSE:
				drawableV.draw();
				break;
			case BLANC:
				drawableB.draw();
				break;
			default:
				break;
			}
			
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
		drawableC.cleanUp();
		drawableB.cleanUp();
		drawableV.cleanUp();
		drawableE.cleanUp();
		drawableExtension.cleanUp();
	}
	
}
