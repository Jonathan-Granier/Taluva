package Ecouteur;


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
import Action.Action_Tuile;
import Moteur.Etat;
import Moteur.Etat.Etat_Jeu;
import Moteur.Moteur;
import terrain.Terrain;
import terrain.Tuile;
import renderEngine.Renderer;
import renderEngine.Window;
import shaders.Shader;
import utils.FPS;
import utils.Grid;
import utils.Grid.Coords;
import utils.InputHandler;
import utils.InputHandler.inputType;
import utils.MousePicker;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import terrain.Case;
import terrain.Tuile;




public class EcouteurDeSourisTerrain {
	
	private Moteur m;
	private MousePicker picker;
	private Grid grid;
	
	public EcouteurDeSourisTerrain(Moteur m,MousePicker picker,Grid grid)
	{
		this.m = m;
		this.picker = picker;
		this.grid = grid;
	}
	
	public void run(GraphicTile Tile,List<GraphicTile> Tiles,GraphicConstruction construction, List<GraphicConstruction> constructions)
	{
		picker.update(Tile.getHeight());
		Vector3f point = picker.getCurrentObjectPoint();
		Coords snap = mouseMoved(Tile,construction,point);
		if(InputHandler.reset(InputHandler.isButtonDown(0) == inputType.INSTANT))
		{
			// Si le clique gauche est appuyé rapidement
			CliqueGaucheSouris_Rapide(Tile,Tiles,construction,constructions,snap);
			
		}
		else if(InputHandler.reset(InputHandler.isButtonDown(1) == inputType.INSTANT))
		{
			CliqueDroitSouris(Tile);
		}
	}
	
	private Coords mouseMoved(GraphicTile Tile,GraphicConstruction construction,Vector3f point){
		Coords snap = null;
		
		switch (m.get_etat_jeu())
		{
			case DEBUT_DE_TOUR:
				m.piocher();
				//m.set_Etat_Jeu(Etat_Jeu.POSER_TUILE);
				return snap;
			case POSER_TUILE:
			
				if(point!=null){
					Tile.getObject3D().setPosition(new Vector3f(point.x,Tile.getHeight(),point.z));
				}
		
				if(InputHandler.reset(InputHandler.isButtonDown(1) == inputType.INSTANT))
					Tile.rotate();
		
		
				InputHandler.isKeyDown(Tile);
		
				//Snap
				Tile.setPostionVolcano();
				snap = grid.snap(Tile.getObject3D(),Tile.getObject3D().getPosition(),Tile.getObject3D().getRotY());
				if(snap!=null  && Tile.getTile()!=null){
					int level = m.getTerrain().getNiveauTheorique(Tile.getTile().getOrientation(), snap.indices)-1;
					Tile.getObject3D().setPosition(snap.worldPos);
					Tile.setHeight(0);
					Tile.increaseHeight(level);
					Tile.getObject3D().setPositionY(Tile.getHeight());
					if(!m.placement_tuile_autorise(snap.indices)){
						Tile.getObject3D().setAllow(false);
					}
				}
				
				return snap;
			case CONSTRUIRE_BATIMENT:
				if(point!=null){
					construction.getObject3d().setPosition(new Vector3f(point.x,construction.getHeight(),point.z));
				}
				
				//Snap
				snap = grid.snap(construction.getObject3d(),construction.getObject3d().getPosition());
				if(snap!=null){
					construction.getObject3d().setPosition(snap.worldPos);
					int level = m.getTerrain().getNiveauTheoriqueBatiment(snap.indices)-1;
					construction.getObject3d().setPosition(snap.worldPos);
					construction.setHeight(0);
					construction.increaseHeight(level);
					construction.getObject3d().setPositionY(construction.getHeight());
					if(!m.placement_batiment_autorise(snap.indices)){
						construction.getObject3d().setAllow(false);
					}
				}
				
				return snap;
			case FIN_DE_TOUR:
				return snap;
			default:
				return snap;
		}
	}
	
	private void CliqueGaucheSouris_Rapide(GraphicTile Tile,List<GraphicTile> Tiles,GraphicConstruction construction, List<GraphicConstruction> constructions,Coords coords)
	{
		Point Point_courant = new Point(Mouse.getX(),Mouse.getY());
		
		switch (m.get_etat_jeu())
		{
			case DEBUT_DE_TOUR:
				m.set_Etat_Jeu(Etat_Jeu.POSER_TUILE);
				break;
			case POSER_TUILE:
				/*
				 * 
				 * 
				 * 
				 * Essayer de poser Tuile
				 * Si Erreur 
				 * 	Afficher-erreur
				 * Sinon 
				 * 	Increment Etat;
				 * 	Mettre à jour les coups possible Batiment; 
				 *
				 */
				
				 if(coords!=null && m.placer_tuile(coords.indices) == 0)
				 {
					Tiles.add(new GraphicTile(Tile));
					Tiles.get(Tiles.size()-1).getObject3D().setPosition(coords.worldPos);
					Tiles.get(Tiles.size()-1).getObject3D().setRotY(Tile.getObject3D().getRotY());
					 m.Maj_liste_coup_construction();
			
				 }
				 else
				 {
					 System.out.println("Il est impossible de poser une tuille ici");
				 }
				
				
				
				break;
			case CONSTRUIRE_BATIMENT:
				/*
				 * Si batiment selection est vide
				 * 	Menu deroulant
				 * Sinon 
				 * 	Verifie construction possible
				 * 	Si erreur
				 * 		afficher erreur;
				 * 	Sinon
				 * 		Increment Etat;
				 * 		 
				 * 
				 */
				System.out.println("Etat :" + m.get_etat_jeu());
				if ( m.get_bat_choisi() == Case.Type_Batiment.VIDE)
				{
					// 	Menu deroulant
				}
				else
				{
					if (coords!=null && m.placer_batiment(coords.indices)== 0)
					{
						constructions.add(new GraphicConstruction(construction));
						constructions.get(constructions.size()-1).getObject3d().setPosition(coords.worldPos);
						m.Maj_liste_coup_construction();
					}
					else{
						System.out.println("Il est impossible de poser un batiment ici");
					}
				}
				
				break;
			case FIN_DE_TOUR:
				break;
			default:
				break;
		}
	}
	
	private void CliqueDroitSouris(GraphicTile Tile)
	{
		switch (m.get_etat_jeu())
		{
			case POSER_TUILE:
				Tile.rotate();
				break;
			default:
				break;
		}			
	}
}
