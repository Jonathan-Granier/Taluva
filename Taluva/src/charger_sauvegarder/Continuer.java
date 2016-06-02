package charger_sauvegarder;

import java.io.File;

public class Continuer {
	private Sauvegarde save;
	
	public Continuer(String path){
		findFiles(path);
	}
	
	private void findFiles(String path) {
		File directory = new File(path);
		File[] subfiles = directory.listFiles();
		File mostRecentFile = subfiles[0];
		for (int i = 0; i < subfiles.length; i++) {
			File subfile = subfiles[i];
			if (mostRecentFile != null && subfile.lastModified()>mostRecentFile.lastModified()) {
				mostRecentFile = subfile;
			}
		}
		Charger load=new Charger(mostRecentFile.getAbsolutePath());
		save = load.getSave();
	}

	public Sauvegarde getSave(){
		return save;
	}
	
}

