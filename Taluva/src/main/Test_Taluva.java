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
        

        JButton button = new JButton("Exit");
         
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
         

        Canvas canvas = new Canvas();

        canvas.setSize(WIDTH, HEIGHT);

        try {
            Display.setParent(canvas);
        } catch (Exception e) {
        }
         

        frame.add(button, BorderLayout.NORTH);
        frame.add(canvas, BorderLayout.CENTER);
         
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
         

        game = new Game();
        game.play(frame);
	}
		
	public static void main(String[] args) {
		new Test_Taluva();		
	}
 

}
