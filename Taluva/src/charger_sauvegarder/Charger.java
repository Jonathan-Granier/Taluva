package charger_sauvegarder;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Charger {
	
	public Charger(Sauvegarde save,String path){
		save = null;
		try
		{
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
