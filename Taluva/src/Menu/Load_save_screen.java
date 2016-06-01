package Menu;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import charger_sauvegarder.Sauvegarde;

public class Load_save_screen{
	private Image fond;
	private String path;
	
	public Load_save_screen(){
		path=new String();
	}
	public void run(){
		JFileChooser dialogue = new JFileChooser(new File("./Save"));
		File fichier=null;
		if (dialogue.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
		    fichier = dialogue.getSelectedFile();
		    
		}
		path=new String(fichier.getAbsolutePath());
	}
	public String getPath() {
		return path;
	}
	
}
