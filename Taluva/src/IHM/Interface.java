package IHM;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Interface {
	public void run() {
        // Creation d'une fenetre
        JFrame frame = new JFrame("Taluva");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel fenetre_de_jeu = new JPanel();
        fenetre_de_jeu.setLayout(new GridLayout(3,1));
        
        JPanel frise = new JPanel();
        frise.setLayout(new GridLayout(1,4));
        
        
        

    }
}
