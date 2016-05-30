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
import Joueur.IA_Alpha_Beta;
import Joueur.IA_Random;
import Joueur.Joueur_Generique;
import Joueur.Joueur_Humain;
import Moteur.Moteur;
import terrain.Terrain;
import terrain.Case.Couleur_Joueur;
import test.Game;
import utils.OSValidator;


public class Test_Taluva implements Runnable{

	public void run() {
    	final Game game;
        final JFrame frame = new JFrame();
        Joueur_Humain j1 = new Joueur_Humain(Couleur_Joueur.BLEU);
        Joueur_Humain j2 = new Joueur_Humain(Couleur_Joueur.VERT);
        Terrain table = new Terrain();
        Moteur m = new Moteur(table);
        Joueur_Generique ia = new IA_Random(Couleur_Joueur.JAUNE,m);
        Joueur_Generique ia2 = new IA_Alpha_Beta(Couleur_Joueur.VERT,m);
        m.add_j1(j1);
        m.add_j2(ia);
        IHM ihm = new IHM(m, frame);
        ihm.run();
        Avancement avancement = new Avancement(ihm);
        m.addPhaseListener(avancement);
        m.getJ1().addBatimentCountListener(avancement);
        m.getJ2().addBatimentCountListener(avancement);
        m.MajListeners();
        ihm.getCanvas().setFocusable(false);
        
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        game = new Game();
        game.init(frame,m,ihm.getCanvas());
        
        frame.addKeyListener(game);
        frame.addMouseMotionListener(game);
        frame.setFocusable(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we){
                int result = JOptionPane.showConfirmDialog(frame, "Voulez-vous vraiment quitter ?", "Confirmation", JOptionPane.CANCEL_OPTION);
                if(result == JOptionPane.OK_OPTION){
                	game.cleanUp();
                    frame.setVisible(false);
            		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.dispose();
                }
            }
        });
	}
	
		
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Test_Taluva());
	}
 

}
