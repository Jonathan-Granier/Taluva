package Ecouteur;

import org.lwjgl.util.vector.Vector2f;

import gui.Button;
import main.Moteur;

public class ButtonEndOfTurn extends Button{

	private Moteur moteur;
	
	public ButtonEndOfTurn(int textureId, Vector2f position, Vector2f dimension,Moteur moteur) {
		super(textureId, position, dimension);
		this.moteur = moteur;
	}

	@Override
	protected void action() {
		moteur.fin_de_tour();
	}
	
}