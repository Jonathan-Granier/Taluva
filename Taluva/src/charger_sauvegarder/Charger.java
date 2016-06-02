package charger_sauvegarder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Charger {
	private Sauvegarde save;
	
	public Charger(String path){
		save= null;

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
		System.out.println("normalement ca c'est bien passer");

	}
	public Sauvegarde getSave() {
		return save;
	}
}
