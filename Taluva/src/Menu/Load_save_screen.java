package Menu;

import java.io.File;

import javax.swing.JFileChooser;

public class Load_save_screen{
	//private Image fond;
	private String path;
	
	public Load_save_screen(){
		run();
	}
	public void run(){
		path = null;
		JFileChooser dialogue = new JFileChooser(new File("./Save"));
		File fichier=null;
		if (dialogue.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
		    fichier = dialogue.getSelectedFile();
		    path=new String(fichier.getAbsolutePath());
		}
			
	}
	public String getPath() {
		return path;
	}
	
}
