package charger_sauvegarder;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import Menu.Load_save_screen;

public class Charger {
	
	public Charger(Sauvegarde save,String path){
		save = null;
		try
		{
			Load_save_screen screen = new Load_save_screen();
	        screen.run();
	        Sauvegarder Save = new Sauvegarder(save,screen.getPath());
			FileInputStream fileIn = new FileInputStream(path);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			save = (Sauvegarde) in.readObject();
			
			in.close();
			fileIn.close();
		}catch(IOException i)
		{
			i.printStackTrace();
			return;
		}catch(ClassNotFoundException c)
		{
			System.out.println("Class Sauvegarde inexistante");
			c.printStackTrace();
			return;
		}
		System.out.println("normalement ça c'est bien passer");

	}
}
