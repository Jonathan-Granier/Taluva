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









import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import main.Moteur;
import terrain.Case;
import terrain.Tuile;




public class EcouteurDeSourisTerrain {
	
	private Moteur m;
	
	public EcouteurDeSourisTerrain(Moteur m)
	{
		this.m = m;
	}
	
	public void run(GraphicTile Tile)
	{
		if(InputHandler.isButtonDown(0))
		{
			// Si le clique gauche est appuyé rapidement
			CliqueGaucheSouris_Rapide();
			// Si c'est Maintenu
			CliqueGaucheSouris_Maintenu();
			
		}
		else if(InputHandler.isButtonDown(1))
		{
			CliqueDroitSouris(Tile);
		}
	}
	
	private void CliqueGaucheSouris_Rapide()
	{
		Point Point_courant = new Point(Mouse.getX(),Mouse.getY());
		
		switch (m.get_etat_jeu())
		{
			case DEBUT_DE_TOUR:
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
				
				 if(m.placer_tuile(Point_courant) == 0)
				 {
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
				
				if ( m.get_bat_choisi() == Case.Type_Batiment.VIDE)
				{
					// 	Menu deroulant
				}
				else
				{
					
					if (m.placer_batiment(Point_courant)!= 0)
					{
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
	
	private void CliqueGaucheSouris_Maintenu()
	{
		//Rotation de camera
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
