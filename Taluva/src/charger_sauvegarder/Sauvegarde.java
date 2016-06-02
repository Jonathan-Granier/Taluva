package charger_sauvegarder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import test.Game;
import Moteur.Moteur;

public class Sauvegarde {
	private Game game;
	private Moteur moteur;
	public Sauvegarde(Game game,Moteur moteur){
		this.game=game;
		this.moteur=moteur;
	}
	public void restore(Game game,Moteur moteur){
		moteur=this.moteur;
		game=this.game;
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

