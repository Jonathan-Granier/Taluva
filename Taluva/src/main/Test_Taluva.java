package main;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.lwjgl.opengl.Display;

import Ecouteur.KeyboardListener;
import IHM.Avancement;
import IHM.IHM;
import Joueur.IA_Random;
import Joueur.Joueur_Generique;
import Joueur.Joueur_Humain;
import Moteur.Moteur;
import terrain.Terrain;
import terrain.Case.Couleur_Joueur;
import test.Game;


public class Test_Taluva implements Runnable{

	public void run() {
    	final Game game;
        final JFrame frame = new JFrame();
        Joueur_Humain j1 = new Joueur_Humain(Couleur_Joueur.BLANC);
        Joueur_Humain j2 = new Joueur_Humain(Couleur_Joueur.VERT);
        Terrain table = new Terrain();
        Moteur m = new Moteur(table);
        Joueur_Generique ia = new IA_Random(Couleur_Joueur.VERT,m);
        m.add_j1(j1);
        m.add_j2(j2);
        IHM ihm = new IHM(m, frame);
        ihm.run();
        Avancement avancement = new Avancement(ihm);
        m.addPhaseListener(avancement);
        m.MajListeners();
        ihm.getCanvas().setFocusable(false);
        
        
        game = new Game();
        game.init(frame,m,ihm.getCanvas());
        
        //ihm.getBas().addMouseListener(new KeyboardListener());
        //frame.addMouseListener(new KeyboardListener());
        frame.addKeyListener(game);
        frame.setFocusable(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we){
                int result = JOptionPane.showConfirmDialog(frame, "Do you want to quit the Application?");
                if(result == JOptionPane.OK_OPTION){
                	game.cleanUp();
                    frame.setVisible(false);
                    frame.dispose(); //canvas's removeNotify() will be called
                }
            }
        });
	}
	
		
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Test_Taluva());
	}
 

}
