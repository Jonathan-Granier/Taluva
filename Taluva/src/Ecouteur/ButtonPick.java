package Ecouteur;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import Moteur.Etat.Etat_Jeu;
import entities.GraphicTile;
import Moteur.Moteur;
import gui.Button;
import loaders.Loader;
import terrain.Case;
import terrain.Tuile;

public class ButtonPick extends Button{

	private Moteur moteur;
	private Loader loader;
	private GraphicTile Tile;
	
	public ButtonPick(int textureId, Vector2f position, Vector2f dimension,Moteur moteur,GraphicTile Tile,Loader loader) {
		super(textureId, position, dimension);
		this.moteur = moteur;
		this.Tile = Tile;
		this.loader = loader;
	}

	@Override
	protected void action() {
		if(moteur.get_etat_jeu() == Etat_Jeu.DEBUT_DE_TOUR){
			moteur.piocher();
			System.out.println(moteur.get_etat_jeu());
			//this.Tile = new GraphicTile(new Tuile(Case.Type.MONTAGNE,Case.Type.SABLE),loader,new Vector3f(0,0,0),90);
			this.Tile.getObject3D().setRotY(90);
		}
	}



}
