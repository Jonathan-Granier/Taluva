package charger_sauvegarder;

import java.io.File;

import test.Game;

import Moteur.Moteur;

public class Continuer {
	private String path;
	private File moreRecentFile = null;
	private Sauvegarde save;
	private Game game;
	private Moteur moteur;
	
	public Continuer(String path){
		this.path=path;
	}
	
	public void findFiles() {
		File directory = new File(path);
		File moreRecentFile = null;
		File[] subfiles = directory.listFiles();
		for (int i = 0; i < subfiles.length; i++) {
			File subfile = subfiles[i];
			if (moreRecentFile != null && subfile.lastModified()>moreRecentFile.lastModified()) {
				moreRecentFile = subfile;
			}
		}

		Charger load=new Charger(save,moreRecentFile.getAbsolutePath());
		save.Restore(game, moteur);
	}

	public Game getGame() {
		return game;
	}

	public Moteur getMoteur() {
		return moteur;
	}
	
}

