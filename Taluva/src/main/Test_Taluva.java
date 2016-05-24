package main;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.lwjgl.opengl.Display;

import IHM.Avancement;
import IHM.IHM;
import Joueur.IA_Random;
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
        Moteur m = new Moteur(table);
        Joueur_Generique ia = new IA_Random(Couleur_Joueur.ROUGE,m);
        m.add_j1(j1);
        m.add_j2(ia);
        IHM ihm = new IHM(m, frame);
        ihm.run();
        Avancement avancement = new Avancement(ihm);
        m.addPhaseListener(avancement);
        m.MajListeners();
        
        game = new Game();
        game.play(frame,m);
        
	}
		
	public static void main(String[] args) {
		new Test_Taluva();
	}
 

}
