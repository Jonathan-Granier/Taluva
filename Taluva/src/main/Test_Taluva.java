package main;

import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import IHM.Avancement;
import IHM.IHM;
import Menu.Menu_Demarrage;
import Moteur.Moteur;
import terrain.Terrain;
import test.Game;


public class Test_Taluva implements Runnable{

	public void run() {
		final JFrame frame = new JFrame();
		frame.setResizable(false);
		frame.setUndecorated(true);
		final JFrame gameF = null;
		final Game game = null;
		final Moteur moteur = null;
		final Terrain terrain = null;
		final IHM ihm = null;
		final Avancement avancement = null;
		java.awt.Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int height = (int)dimension.getHeight();
        int width  = (int)dimension.getWidth();
        
        Menu_Demarrage demar = new Menu_Demarrage(frame,gameF,game,moteur,terrain,ihm,avancement);
		
        frame.add(demar);
        
		frame.setVisible(true);
		frame.setSize(width,height);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we){
            	int result = JOptionPane.showConfirmDialog(frame, "Etes-vous sur de vouloir quitter ?", "Confirmation", JOptionPane.CANCEL_OPTION);
            	if(result == JOptionPane.OK_OPTION){
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
