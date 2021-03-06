package charger_sauvegarder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import Moteur.Moteur;

public class Sauvegarde {
	private Moteur moteur;
	public Sauvegarde(Moteur moteur){
		this.moteur=moteur;
	}
	public void restore(Moteur moteur){
		moteur=this.moteur;
	}
	
	public void sauvegarder(Sauvegarde save,String path){
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

