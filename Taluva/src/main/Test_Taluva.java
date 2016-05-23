package main;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.lwjgl.opengl.Display;

import IHM.IHM;
import Joueur.Joueur_Generique;
import Joueur.Joueur_Humain;
import Moteur.Moteur;
import terrain.Terrain;
import terrain.Case.Couleur_Joueur;
import test.Game;


public class Test_Taluva {

	
	private Game game;
	
	public Test_Taluva(){
        JFrame frame = new JFrame();
        Joueur_Humain j1 = new Joueur_Humain(Couleur_Joueur.BLANC);
        Joueur_Humain j2 = new Joueur_Humain(Couleur_Joueur.ROUGE);
        Terrain table = new Terrain();
        Moteur m = new Moteur(table,j1,j2);
        IHM ihm = new IHM(m, frame);
        ihm.run();
        
        game = new Game();
        game.play(frame,m);
        
	}
		
	public static void main(String[] args) {
		new Test_Taluva();
	}
 

}
