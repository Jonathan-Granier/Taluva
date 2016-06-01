package charger_sauvegarder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import Menu.Load_save_screen;

public class Sauvegarder {
	
	public Sauvegarder(Sauvegarde save,String path){
		Load_save_screen screen = new Load_save_screen();
		screen.run();
        Charger load = new Charger(save,screen.getPath());
        
		try
	      {
			System.out.println(path);
			File fichier = new File(path);
			fichier.createNewFile();
	        FileOutputStream fileOut = new FileOutputStream(path);
	        ObjectOutputStream out = new ObjectOutputStream(fileOut);
	        out.writeObject(save);
	        out.close();
	        fileOut.close();
	        System.out.println("Serialized data is saved in "+path);
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
	}

}
