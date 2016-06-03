package Ecouteur;


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import IHM.JPanelPioche;
import IHM.Menu_circulaire_creation;
import Moteur.Moteur;
import entities.GraphicConstruction;
import entities.GraphicTile;
import terrain.Case;
import utils.Grid;
import utils.Grid.Coords;
import utils.InputHandler;
import utils.InputHandler.inputType;
import utils.MousePicker;
import utils.OSValidator;




public class EcouteurDeSourisTerrain implements MouseListener {
	
	private Moteur m;
	private MousePicker picker;
	private Grid grid;
	private GraphicTile Tile;
	private List<GraphicTile> Tiles;
	private GraphicConstruction construction;
	private List<GraphicConstruction> constructions;
	private Menu_circulaire_creation marking_menu;
	private Coords snap;
	private Vector3f point;
	private float timer = 0;
	private static final long TIME = 20;
	private JPanelPioche Tuile_Pioche;
	
	public EcouteurDeSourisTerrain(Moteur m,MousePicker picker,Grid grid,
			GraphicTile Tile,List<GraphicTile> Tiles,GraphicConstruction construction, 
			List<GraphicConstruction> constructions,Menu_circulaire_creation marking_menu,JPanelPioche Tuile_Pioche)
	{
		this.m = m;
		this.picker = picker;
		this.grid = grid;
		this.Tiles = Tiles;
		this.Tile = Tile;
		this.construction = construction;
		this.constructions = constructions;
		this.marking_menu = marking_menu;
		this.Tuile_Pioche = Tuile_Pioche;
	}
	
	public void run()
	{
		picker.update(Tile.getHeight());
		point = picker.getPoint();
		snap = mouseMoved();
		if(timer>0)
			timer++;
		if(OSValidator.isWindows() && InputHandler.reset(InputHandler.isButtonDown(0) == inputType.INSTANT))
		{
			// Si le clique gauche est appuyé rapidement
			CliqueGaucheSouris_Rapide();

		
		}
		
	}
	
	private Coords mouseMoved(){
		Coords snap = null;
		
		switch (m.get_etat_jeu())
		{
			case DEBUT_DE_TOUR:
				return snap;
			case POSER_TUILE:
			
				if(point!=null){
					Tile.getObject3D().setPosition(new Vector3f(point.x,Tile.getHeight(),point.z));
				}
		
				if(OSValidator.isWindows()){
					if(InputHandler.reset(InputHandler.isButtonDown(1) == inputType.INSTANT)){
						Tile.rotate_horaire();
						m.tourner_tuile();
					}
				}
				
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
					construction.setPosition(new Vector3f(point.x,construction.getHeight(),point.z));
				}
			
				if(OSValidator.isWindows()){
					if(InputHandler.reset(InputHandler.isButtonDown(1) == inputType.INSTANT)){
						Ecouteur_Boutons.setPick(false);
					}
				}
				
				//Snap
				snap = grid.snap(construction.getObject3d(),construction.getPosition());
				if(snap!=null){
					construction.setPosition(snap.worldPos);
					int level = m.getTerrain().getNiveauTheoriqueBatiment(snap.indices)-1;
					construction.setHeight(0);
					construction.increaseHeight(level);
					construction.setPositionY(construction.getHeight());
					construction.setAllow(m.placement_batiment_autorise(snap.indices));
				}
				else{
					construction.setAllow(false);
				}
				
				return snap;
			case FIN_DE_TOUR:
				return snap;
			default:
				return snap;
		}
	}
	
	
	private void CliqueGaucheSouris_Rapide(){
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
				
				//Si anuler on enleve le sommet de la pile graphic
				//if(Ecouteur_Boutons.isUndo())
				//	Tiles.remove(Tiles.size()-1);
				
				 if(snap!=null && m.placer_tuile(snap.indices) == 0 )
				 {
					//Tiles.add(new GraphicTile(Tile));
					//Tiles.get(Tiles.size()-1).getObject3D().setPosition(snap.worldPos);
					//Tiles.get(Tiles.size()-1).getObject3D().setRotY(Tile.getObject3D().getRotY());
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
				//Si anuler on enleve le sommet de la pile graphic
				/*if(Ecouteur_Boutons.isUndo())
					constructions.remove(constructions.size()-1);*/
				
				if(point!=null)
					marking_menu.tuile_vide_cliquer(point);
				
				System.out.println("Etat :" + m.get_etat_jeu());
				if ( m.get_bat_choisi() == Case.Type_Batiment.VIDE)
				{
					// 	Menu deroulant
				}
				else
				{
					if(Ecouteur_Boutons.isPick()){
						if (snap!=null && m.placer_batiment(snap.indices)== 0)
						{
							
							
							//constructions.add(new GraphicConstruction(GraphicType.HUT,new Vector3f(0,0,0)));
							//constructions.get(constructions.size()-1).getObject3d().setPosition(snap.worldPos);
							m.Maj_liste_coup_construction();
						}
						else{
							System.out.println("Il est impossible de poser un batiment ici");
						}
					}
				}
				
				break;
			case FIN_DE_TOUR:
				break;
			default:
				break;
		}
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {

		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		timer++;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
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
				
				//Si anuler on enleve le sommet de la pile graphic
				//if(Ecouteur_Boutons.isUndo())
				//	Tiles.remove(Tiles.size()-1);
				
				if(e.getButton() == MouseEvent.BUTTON1 && timer < TIME ){
					 if(snap!=null && m.placer_tuile(snap.indices) == 0 )
					 {
						//Tiles.add(new GraphicTile(Tile));
						//Tiles.get(Tiles.size()-1).getObject3D().setPosition(snap.worldPos);
						//Tiles.get(Tiles.size()-1).getObject3D().setRotY(Tile.getObject3D().getRotY());
						m.Maj_liste_coup_construction();
				
					 }
					 else
					 {
						 System.out.println("Il est impossible de poser une tuille ici");
					 }
				}
				else if(e.getButton() == MouseEvent.BUTTON3){
						Tile.rotate_horaire();
						Tuile_Pioche.RotationHoraire();
						m.tourner_tuile();
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
				//Si anuler on enleve le sommet de la pile graphic
				/*if(Ecouteur_Boutons.isUndo())
					constructions.remove(constructions.size()-1);*/
				
				if(point!=null)
					marking_menu.tuile_vide_cliquer(point);
				
				System.out.println("Etat :" + m.get_etat_jeu());
				if ( m.get_bat_choisi() == Case.Type_Batiment.VIDE)
				{
					// 	Menu deroulant
				}
				else
				{
					if(e.getButton() == MouseEvent.BUTTON1){
						if(Ecouteur_Boutons.isPick()){
							if (snap!=null && m.placer_batiment(snap.indices)== 0)
							{
								//constructions.add(new GraphicConstruction(construction));
								//constructions.get(constructions.size()-1).getObject3d().setPosition(snap.worldPos);
								m.Maj_liste_coup_construction();
							}
							else{
								System.out.println("Il est impossible de poser un batiment ici");
							}
						}
					}
					else if(e.getButton() == MouseEvent.BUTTON3){
						Ecouteur_Boutons.setPick(false);
						m.select_vide();
					}
				}
				
				break;
			case FIN_DE_TOUR:
				break;
			default:
				break;
		}
		timer = 0;
	}
}
