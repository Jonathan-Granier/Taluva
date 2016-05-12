package main;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.lwjgl.opengl.Display;

import test.Game;


public class Test_Taluva {
	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	
	private Game game;
	
	public Test_Taluva(){
        JFrame frame = new JFrame();        
        
        // The exit button.
        JButton button = new JButton("Exit");
         
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
         
        // Create a new canvas and set its size.
        Canvas canvas = new Canvas();
        // Must be 640*480 to match the size of an Env3D window
        canvas.setSize(WIDTH, HEIGHT);
        // This is the magic!  The setParent method attaches the 
        // opengl window to the awt canvas.
        try {
            Display.setParent(canvas);
        } catch (Exception e) {
        }
         
        // Construct the GUI as normal
        frame.add(button, BorderLayout.NORTH);
        frame.add(canvas, BorderLayout.CENTER);
         
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
         
        // Make sure you run the game, which 
        // executes on a separate thread.
        game = new Game();
        game.play();
	}
		
	public static void main(String[] args) {
		new Test_Taluva();		
	}
 

}
