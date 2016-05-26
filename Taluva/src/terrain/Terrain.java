package terrain;

import java.awt.Point;
import java.util.ArrayList;

import Action.Action_Batiment;
import Action.Action_Construction;
import Action.Action_Tuile;
import test.Game;

public class Terrain {
	
	public class Coord{
		public int xmin,ymin,xmax,ymax;
		public Coord(int xmin, int ymin, int xmax, int ymax){
			this.xmin = xmin;
			this.ymin = ymin;
			this.xmax = xmax;
			this.ymax = ymax;
		}
		public Coord clone(){
			return new Coord(xmin,ymin,xmax,ymax);
		}
	}
	
	public final static int TAILLE = 200;
	public final static Point CENTRE = new Point(TAILLE/2,TAILLE/2);
	
	private Case [][] t;
	private boolean empty;
	private Coord limites;
	
	private ArrayList<Action_Tuile> histo_tuiles;
	private ArrayList<Action_Batiment> histo_batiments;
	private ArrayList<Cite> cites;
	private int [][] index_cite;
	
	//private int [] index_bat_supprime;
	
	public Terrain(){
		t = new Case[TAILLE][TAILLE];
		index_cite = new int [TAILLE][TAILLE];
		limites  = new Coord(CENTRE.x,CENTRE.y,CENTRE.x,CENTRE.y);
		//index_bat_supprime = new int [2];
		//index_bat_supprime[0] = -1;
		//index_bat_supprime[1] = -1;
		for(int i=0;i<TAILLE;i++){
			for(int j=0;j<TAILLE;j++){
				index_cite[i][j] = -1;
			}
		}
		for(int i=limites.xmin;i<=limites.xmax;i++){
			for(int j=limites.ymin;j<=limites.ymax;j++){
				t[i][j] = new Case(Case.Type.VIDE);
			}
		}
		empty = true;
		histo_tuiles = new ArrayList<Action_Tuile>();
		histo_batiments = new ArrayList<Action_Batiment>();
		cites = new ArrayList<Cite>();
	}
	
	public Terrain clone(){
		Terrain tmp = new Terrain();
		for(int i=0;i<TAILLE;i++){
			for(int j=0;j<TAILLE;j++){
				tmp.index_cite[i][j] = this.index_cite[i][j];
			}
		}
		for(int i=limites.xmin;i<=limites.xmax;i++){
			for(int j=limites.ymin;j<=limites.ymax;j++){
				tmp.t[i][j] = this.t[i][j].clone();
			}
		}
		//tmp.index_bat_supprime[0] = this.index_bat_supprime[0];
		//tmp.index_bat_supprime[1] = this.index_bat_supprime[1];
		tmp.limites  = this.limites.clone();
		tmp.empty = this.empty;
		for(int i = 0;i<this.histo_tuiles.size();i++){
			tmp.histo_tuiles.add(this.histo_tuiles.get(i).clone());
		}
		for(int i = 0;i<this.histo_batiments.size();i++){
			tmp.histo_batiments.add(this.histo_batiments.get(i).clone());
		}
		for(int i = 0;i<this.cites.size();i++){
			tmp.cites.add(this.cites.get(i).clone());
		}
		return tmp;
	}
	
	public Case getCase(Point P){
		return getCase(P.x,P.y);
	}
	
	public Case getCase(int i, int j){
		//if(i>=0 && j>=0 && i<TAILLE && j<TAILLE)
		if(i>=limites.xmin && j>=limites.ymin && i<=limites.xmax && j<=limites.ymax)
			return t[i][j];
		else{
			return new Case(Case.Type.VIDE);
		}
	}
	
	public boolean isEmpty(){
		return empty;
	}
	
	public ArrayList<Action_Tuile> getHistoTuiles(){
		return histo_tuiles;
	}
	
	public ArrayList<Action_Batiment> getHistoBatiments(){
		return histo_batiments;
	}
	
	// Renvoie la cite presente au point P
	public Cite getCite(Point P){
		int i = getIndexCite(P);
		if(i < 0 || i >= cites.size()){
			//System.out.println("Erreur : Terrain.getCite(" + P.x + "," + P.y + ") : index " + i + ", taille " + cites.size());
			System.out.println("Terrain.getCite(" + P.x + "," + P.y + ") : pas de cite ici");
			return new Cite(P, Case.Couleur_Joueur.NEUTRE, Case.Type_Batiment.VIDE);
		}
		return cites.get(i);
	}
	
	public ArrayList<Cite> getCitesJoueur(Case.Couleur_Joueur c){
		ArrayList<Cite> res = new ArrayList<Cite>();
		for(int i=0;i<cites.size();i++){
			if(cites.get(i).getCouleur()==c){
				res.add(cites.get(i));
			}
		}
		return res;
	}
	
	// Renvoie les coordonnees limites du terrain : toutes les tuiles sont comprises dans
	// (xmin,ymin)--------|
	//      |             |
	//      |--------(xmax,ymax)
	public Coord getLimites(){
		return limites;
	}
	
	//			    ___/ 3,0 \
	//			   /   \     /
	//		   ___/ 2,0 \___/
	//		  /   \     /   \
	//	  ___/ 1,0 \___/ 3,1 \
	//	 /	 \     /   \     /
	//	/ 0,0 \___/ 2,1 \___/
	//  \     /   \     /   \
	//   \___/ 1,1 \___/ 3,2 \
	//   /   \     /   \     /
	//  / 0,1 \___/ 2,2 \___/
	//  \     /   \     /   \
	//   \___/ 1,2 \___/ 3,3 \

	
	private Point [] getPtsVoisins(Point P){
		//   |   |
		// X | X |
		//___|___|___
		//   |@@@|
		// X |@@@| X
		//___|@@@|___
		//   |   |
		//   | X | X
		//   |   |
		Point [] res = new Point[6];
		int x = P.x;
		int y = P.y;
		res[0] = new Point(x-1,y-1);
		res[1] = new Point(x-1,y);
		res[2] = new Point(x,y-1);
		res[3] = new Point(x+1,y);
		res[4] = new Point(x,y+1);
		res[5] = new Point(x+1,y+1);
		return res;
	}
	
	private int getIndexCite(Point P){
		int i = P.x;
		int j = P.y;
		//if(i>=0 && j>=0 && i<TAILLE && j<TAILLE)
		if(i>=limites.xmin && j>=limites.ymin && i<=limites.xmax && j<=limites.ymax)
			return index_cite[i][j];
		else
			System.out.println("Terrain.getIndexCite(" + i + "," + j + ") : hors terrain.");
			return -1;
	}

/*
	// Renvoie la liste des cases calculee depuis une liste de points
	private ArrayList<Case> getCases(ArrayList<Point> pts){
		ArrayList<Case> res = new ArrayList<Case>();
		for(int i=0;i<pts.size();i++){
			res.add(getCase(pts.get(i)));
		}
		return res;
	}
*/
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////// PLACEMENT TUILE /////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	//	Position pour le placement :
	//		     _	    _
	//		   _/X\    /X\_
	// GAUCHE / \_/    \_/ \ DROITE
	//	 	  \_/ \    / \_/
	//		    \_/    \_/
	
	// Renvoie le Terrain apres placement de tuile au point P. Ne modifie pas la structure actuelle.
	public Terrain consulter_coup_tuile(Tuile tuile, Point P){
		Terrain T = this.clone();
		T.placer_tuile(tuile, P);
		return T;
	}
	
	// Place la tuile donnee au point P. Renvoie 0 si la tuile a pu etre placee, 1 sinon.
	public int placer_tuile(Tuile tuile, Point P){
		int x;
		int y;
		if(placement_tuile_autorise(tuile,P)){
			if(empty){
				empty = false;
				x = CENTRE.x;
				y = CENTRE.y;
			}
			else{
				x = P.x;
				y = P.y;
			}
			//////////
			if(y+1>limites.ymax){
				for(int i=limites.xmin;i<=limites.xmax;i++){
					t[i][y+1] = new Case(Case.Type.VIDE);
					if(y>limites.ymax)
						t[i][y] = new Case(Case.Type.VIDE);
				}
				limites.ymax = y+1;
			}
			if(y<limites.ymin){
				for(int i=limites.xmin;i<=limites.xmax;i++){
					t[i][y] = new Case(Case.Type.VIDE);
					if(y+1<limites.ymin)
						t[i][y+1] = new Case(Case.Type.VIDE);
				}
				limites.ymin = y;
			}
			//////////
			//if(y+1>limites.ymax) limites.ymax = y+1;
			//if(y<limites.ymin) limites.ymin = y;
			//poser_hexa(x,y,tuile.get_type_case(Case.Orientation.N),tuile.get_Orientation_Volcan());
			//poser_hexa(x,y+1,tuile.get_type_case(Case.Orientation.S),tuile.get_Orientation_Volcan());
			if(tuile.getOrientation()==Tuile.Orientation.GAUCHE){
				/////////
				if(x>limites.xmax){
					for(int j=limites.ymin;j<=limites.ymax;j++){
						t[x][j] = new Case(Case.Type.VIDE);
						if(x-1>limites.xmax)
							t[x-1][j] = new Case(Case.Type.VIDE);
					}
					limites.xmax = x;
				}
				if(x-1<limites.xmin){
					for(int j=limites.ymin;j<=limites.ymax;j++){
						t[x-1][j] = new Case(Case.Type.VIDE);
						if(x<limites.xmin)
							t[x][j] = new Case(Case.Type.VIDE);
					}
					limites.xmin = x-1;
				}
				/////////
				//if(x>limites.xmax) limites.xmax = x;
				//if(x-1<limites.xmin) limites.xmin = x-1;
				int indice_bat_suppr = 0;
				poser_hexa(x,y,tuile.get_type_case(Case.Orientation.N),tuile.get_Orientation_Volcan(),indice_bat_suppr);
				if(tuile.get_type_case(Case.Orientation.N) != Case.Type.VOLCAN) indice_bat_suppr ++;
				poser_hexa(x,y+1,tuile.get_type_case(Case.Orientation.S),tuile.get_Orientation_Volcan(),indice_bat_suppr);
				if(tuile.get_type_case(Case.Orientation.S) != Case.Type.VOLCAN) indice_bat_suppr ++;
				poser_hexa(x-1,y,tuile.get_type_case(Case.Orientation.O),tuile.get_Orientation_Volcan(),indice_bat_suppr);
			}
			else{
				/////////
				if(x+1>limites.xmax){
					for(int j=limites.ymin;j<=limites.ymax;j++){
						t[x+1][j] = new Case(Case.Type.VIDE);
						if(x>limites.xmax)
							t[x][j] = new Case(Case.Type.VIDE);
					}
					limites.xmax = x+1;
				}
				if(x<limites.xmin){
					for(int j=limites.ymin;j<=limites.ymax;j++){
						t[x][j] = new Case(Case.Type.VIDE);
						if(x+1<limites.xmin)
							t[x+1][j] = new Case(Case.Type.VIDE);
					}
					limites.xmin = x;
				}
				///////
				//if(x+1>limites.xmax) limites.xmax = x+1;
				//if(x<limites.xmin) limites.xmin = x;
				int indice_bat_suppr = 0;
				poser_hexa(x,y,tuile.get_type_case(Case.Orientation.N),tuile.get_Orientation_Volcan(),indice_bat_suppr);
				if(tuile.get_type_case(Case.Orientation.N) != Case.Type.VOLCAN) indice_bat_suppr ++;
				poser_hexa(x,y+1,tuile.get_type_case(Case.Orientation.S),tuile.get_Orientation_Volcan(),indice_bat_suppr);
				if(tuile.get_type_case(Case.Orientation.S) != Case.Type.VOLCAN) indice_bat_suppr ++;
				poser_hexa(x+1,y+1,tuile.get_type_case(Case.Orientation.E),tuile.get_Orientation_Volcan(),indice_bat_suppr);
			}
			histo_tuiles.add(new Action_Tuile(tuile,new Point(x,y),getCase(x,y).getNiveau()));
			return 0;
		}
		else{
			return 1;
		}
	}
	
	// Renvoie l'indice dans l'historique des batiments de l'action_batiment du point P
	private int getIndexHistoBatiments(Point P){
		int i = 0;
		boolean trouve = false;
		int res = -1;
		while(!trouve && i<histo_batiments.size()){
			if(histo_batiments.get(i).getPosition().equals(P)){
				trouve = true;
				res = i;
			}
			i++;
		}
		return res;
	}
	
	// Renvoie les indices des deux cases dont les batiments ont ete ecrases lors de la derniere pose de tuile
	//public int [] getIndexBatSuppr(){
	//	return index_bat_supprime;
	//}
	
	private void poser_hexa(int x, int y, Case.Type type, Case.Orientation oV, int index_bat_suppr){
		Case c = getCase(x,y);
		c.setType(type);
		c.setOrientation(oV);
		c.incrNiveau();
		if(c.retirer_batiments() == 0){
			// On a retiré des batiments : il faut gerer l'historique et les cites
			Point P = new Point(x,y);
			int index_bat_histo = getIndexHistoBatiments(P);
			if(index_bat_histo != -1){
				// On met a jour l'historique (on supprime le dernier element et on le place a l'endroit à supprimer)
				// et on met a jour index_bat_supprime pour indiquer le batiment à actualiser
				//index_bat_supprime[index_bat_suppr] = index_bat_histo;
				histo_batiments.set(index_bat_histo,histo_batiments.get(histo_batiments.size()-1).clone());
				histo_batiments.remove(histo_batiments.size()-1);
				//Game.majHistoBatiments();
			}
			else System.out.println("Erreur poser_hexa : gestion de l'historique_batiments");
			Cite cite = getCite(P);
			ArrayList<Cite> citesSeparees = citesSeparation(P);
			// On enleve la cite d'origine
			if(citesSeparees.size()>0){
				int index_c = cites_indexOf(cite);
				cites.remove(index_c);
				// On separe une cite en 2 ou 3
				// On decale les indices
				for(int i=limites.xmin;i<=limites.xmax;i++){
					for(int j=limites.ymin;j<=limites.ymax;j++){
						if(index_cite[i][j]>index_c){
							index_cite[i][j] = index_cite[i][j]-1;
						}
					}
				}
				// On met a -1 les index de la cite
				for(int i=0;i<cite.getPts().size();i++){
					P = cite.getPts().get(i);
					index_cite[P.x][P.y] = -1;
				}
				// Puis on met a jour cites et index_cite[][]
				for(int i=0;i<citesSeparees.size();i++){
					cites.add(citesSeparees.get(i));
					ArrayList<Point> ptsCite = citesSeparees.get(i).getPts();
					for(int j=0;j<ptsCite.size();j++){
						index_cite[ptsCite.get(j).x][ptsCite.get(j).y] = cites.size()-1;
					}
				}
			}
			else{
				cite.retirer(P);
				index_cite[x][y] = -1;
			}
		}
		//else{
		//	if(index_bat_suppr<2) index_bat_supprime[index_bat_suppr] = -1;
		//}
	}
	
	// Renvoie l'ensemble de cites issu de la suppression des batiments au point P
	// Ensemble vide si la cite n'a pas ete separee
	private ArrayList<Cite> citesSeparation(Point P){
		ArrayList<Cite> res = new ArrayList<Cite>();
		ArrayList<Point> voisinsCite = ptsContactDansCite(P);
		if(voisinsCite.size()>1){
			// Il y a plusieurs voisins : c'est peut-etre separe
			ArrayList<ArrayList<Point>> citesSeparees= citesIsolees(P);
			if(citesSeparees.size()>1){
				for(int i=0;i<citesSeparees.size();i++){
					Cite cite = new Cite(citesSeparees.get(i).get(0), getCase(citesSeparees.get(i).get(0)).getCouleur(), getCase(citesSeparees.get(i).get(0)).getBType());
					for(int j=1; j<citesSeparees.get(i).size(); j++){
						cite.ajouter(citesSeparees.get(i).get(j), getCase(citesSeparees.get(i).get(j)).getBType());
					}
					res.add(cite);
				}
			}
		}
		return res;
	}
	
	// Renvoie un ensemble de listes de points isolees les unes des autres, resultat de la separation en P
	private ArrayList<ArrayList<Point>> citesIsolees(Point P){
		ArrayList<Point> voisinsDansCite = ptsContactDansCite(P);
		ArrayList<ArrayList<Point>> res = new ArrayList<ArrayList<Point>>();
		ArrayList<Point> visites = new ArrayList<Point>();
		for(int i=0;i<voisinsDansCite.size();i++){
			ArrayList<Point> citeP = getPtsConnectes(voisinsDansCite.get(i));
			if(!visites.contains(citeP.get(0))){
				visites.addAll(citeP);
				res.add(citeP);
			}
		}
		//System.out.println("Separation depuis " + P.x + "," + P.y + " en " + res.size() + " parties");
		return res;
	}
	
	private boolean point_touche_point(Point P1, Point P2){
		boolean contact = false;
		Point [] contactP2 = getPtsVoisins(P2);
		int i = 0;
		while(!contact && i<6){
			contact = P1.x==contactP2[i].x && P1.y==contactP2[i].y;
			i++;
		}
		return contact;
	}
	
	// Calcule l'ensemble de pointes connectes a P
	private ArrayList<Point> getPtsConnectes(Point P){
		ArrayList<Point> ptsCite = new ArrayList<Point>();
		if(getCase(P).getCouleur() != Case.Couleur_Joueur.NEUTRE){
			boolean [][] appartient_cite = new boolean[TAILLE][TAILLE];
			for(int i=0;i<TAILLE;i++){
				for(int j=0;j<TAILLE;j++){
					appartient_cite[i][j] = false;
				}
			}
			getPtsConnectes_rec(P,ptsCite,appartient_cite);
		}
		return ptsCite;
	}

	// Recursion de la fonction ci-dessus
	private void getPtsConnectes_rec(Point P, ArrayList<Point> res, boolean [][] appartient_cite){
		Point [] voisins = getPtsVoisins(P);
		res.add(P);
		appartient_cite[P.x][P.y] = true;
		Case.Couleur_Joueur c = getCase(P).getCouleur();
		for(int i=0;i<6;i++){
			if(getCase(voisins[i]).getCouleur() == c && !appartient_cite[voisins[i].x][voisins[i].y]){
				getPtsConnectes_rec(voisins[i],res,appartient_cite);
			}
		}
	}
/*
	private boolean separe_cite(Point P){
		ArrayList<Point> voisinsCite = ptsContactDansCite(P);
		if(voisinsCite.size()>1){
			boolean case_isolee = false;
			int i=0;
			while(!case_isolee && i<voisinsCite.size()-1){
				case_isolee = true;
				for(int j=i+1; j<voisinsCite.size(); j++){
					case_isolee = case_isolee && !point_contact_point(voisinsCite.get(i),voisinsCite.get(j));
				}
				i++;
			}
			return case_isolee;
		}
		else return false;
	}
*/
	private ArrayList<Point> ptsContactDansCite(Point P){
		Cite cite = getCite(P);
		ArrayList<Point> ptsCite = cite.getPts();
		ArrayList<Point> res = new ArrayList<Point>();
		for(int i = 0; i<ptsCite.size(); i++){
			if(point_touche_point(ptsCite.get(i),P)){
				res.add(ptsCite.get(i));
			}
		}
		return res;
	}
	
	// Renvoie vrai ssi la tuile est dans le terrain
	private boolean dans_terrain(Tuile.Orientation o, Point P){
		if(o == Tuile.Orientation.GAUCHE)
			return P.x > 1 && P.y >= 1 && P.x < TAILLE-1 && P.y < TAILLE-2;
		else
			return P.x >= 1 && P.y >= 1 && P.x < TAILLE-2 && P.y < TAILLE-2;
	}

	// Renvoie les 3 cases de la tuile
	private Case [] cases_tuile(Tuile.Orientation o, Point P){
		Point [] pts = pts_tuile(o,P);
		Case [] res = new Case[3];
		res[0] = getCase(pts[0]);
		res[1] = getCase(pts[1]);
		res[2] = getCase(pts[2]);
		return res;
	}
	
	private Point [] pts_tuile(Tuile.Orientation o, Point P){
		int x = P.x;
		int y = P.y;
		Point [] res = new Point[3];
		res[0] = P;
		res[1] = new Point(x,y+1);
		if(o == Tuile.Orientation.GAUCHE){
			res[2] = new Point(x-1,y);
		}
		else{
			res[2] = new Point(x+1,y+1);
		}
		return res;
	}
	
	// Renvoie la liste des cases en contact avec la tuile
	private ArrayList<Point> contact(Tuile.Orientation o, Point P){
		int x = P.x;
		int y = P.y;
		ArrayList<Point> res = new ArrayList<Point>();
		res.add(new Point(x,y-1));
		res.add(new Point(x,y+2));
		res.add(new Point(x+1,y));
		res.add(new Point(x-1,y+1));
		res.add(new Point(x+1,y+2));
		res.add(new Point(x-1,y-1));
		if(o == Tuile.Orientation.GAUCHE){
			res.add(new Point(x-2,y));
			res.add(new Point(x+1,y+1));
			res.add(new Point(x-2,y-1));
		}
		else{
			res.add(new Point(x+2,y+1));
			res.add(new Point(x-1,y));
			res.add(new Point(x+2,y+2));
		}
		return res;
	}
	
	// Renvoie vrai ssi la tuile est en contact avec au moins une autre tuile
	private boolean en_contact(Tuile.Orientation o, Point P){
		boolean trouve = false;
		ArrayList<Point> voisins = contact(o,P);
		int i = 0;
		while(!trouve && i<voisins.size()){
			trouve = !getCase(voisins.get(i)).est_Vide();
			i++;
		}
		return trouve;
	}
	
	// Renvoie le niveau THEORIQUE de placement de la tuile au point P
	public int getNiveauTheorique(Tuile.Orientation o, Point P){
		Case [] cases_t = cases_tuile(o,P);
		return Math.max(Math.max(cases_t[0].getNiveau(), cases_t[1].getNiveau()),cases_t[2].getNiveau())+1;
	}
	
	// Renvoie vrai ssi le placement de cette tuile est autorise au point P.
	public boolean placement_tuile_autorise(Tuile tuile, Point P)
	{
		if(empty) return true;
		if(!dans_terrain(tuile.getOrientation(),P)) return false;
		else{
			// Teste si la tuile est posee sur d'autres tuiles
			int x = P.x;
			int y = P.y;
			int n0,n1,n2;
			Case [] cases_t = cases_tuile(tuile.getOrientation(),P);	// Les cases de la tuile
			n0 = cases_t[0].getNiveau();
			n1 = cases_t[1].getNiveau();		// On regarde les niveaux en-dessous de la tuile
			n2 = cases_t[2].getNiveau();
			if(n0>0 || n1>0 || n2>0){
				// Si on tente de jouer sur au moins une tuile
				if(n0==n1 && n1==n2){
					// Si les 3 cases dessous sont au même niveau
					// On joue alors sur des tuiles
					// On verifie qu'on n'ecrase pas une cite entiere ni une tour ou un temple
					if(!cases_t[0].est_Libre() || !cases_t[1].est_Libre() || !cases_t[2].est_Libre()){
						//System.out.println("Ca ecrase pitetre des choses interdites");
						// Si on ecrase au moins un batiment
						if(cases_t[0].getBType() == Case.Type_Batiment.TEMPLE || cases_t[0].getBType() == Case.Type_Batiment.TOUR)
							return false;
						if(cases_t[1].getBType() == Case.Type_Batiment.TEMPLE || cases_t[1].getBType() == Case.Type_Batiment.TOUR)
							return false;
						if(cases_t[2].getBType() == Case.Type_Batiment.TEMPLE || cases_t[2].getBType() == Case.Type_Batiment.TOUR)
							return false;
						
						// On n'ecrase que des huttes
						Point [] pts_t = pts_tuile(tuile.getOrientation(),P);
						if(!cases_t[0].est_Libre()){
							ArrayList<Point> cite = getCite(pts_t[0]).getPts();
							if(cite.size()==1) // Une seule case : interdit
								return false;
							else if(cite.size()==2){ // Deux cases : interdit si la deuxieme est aussi sous la tuile
								if(cite.contains(pts_t[1]) || cite.contains(pts_t[2]))
									return false;
							}
						}
						if(!cases_t[1].est_Libre()){
							ArrayList<Point> cite = getCite(pts_t[1]).getPts();
							if(cite.size()==1) // Une seule case : interdit
								return false;
							else if(cite.size()==2){ // Deux cases : interdit si la deuxieme est aussi sous la tuile
								if(cite.contains(pts_t[2]))
									return false;
							}
						}
						if(!cases_t[2].est_Libre()){
							ArrayList<Point> cite = getCite(pts_t[2]).getPts();
							if(cite.size()==1) // Une seule case : interdit
								return false;
						}

						//System.out.println("Ah ba non");
					}
					
					// On vérifie la disposition des volcans
					if(tuile.get_type_case(Case.Orientation.N)==Case.Type.VOLCAN){
						// Si le Volcan est au Nord
						if(getCase(x,y).getType()==Case.Type.VOLCAN){
							return getCase(x,y).getOrientation() != tuile.get_Orientation_Volcan();
						}
						else return false;
					}
					else if(tuile.get_type_case(Case.Orientation.S)==Case.Type.VOLCAN){
						// Si le Volcan est au Sud
						if(getCase(x,y+1).getType()==Case.Type.VOLCAN){
							return getCase(x,y+1).getOrientation() != tuile.get_Orientation_Volcan();
						}
						else return false;
					}
					else{
						// Si le Volcan est sur le coté
						if(tuile.getOrientation()==Tuile.Orientation.GAUCHE){
							if(getCase(x-1,y).getType()==Case.Type.VOLCAN){
								return getCase(x-1,y).getOrientation() != tuile.get_Orientation_Volcan();
							}
							else return false;
						}
						else{
							if(getCase(x+1,y+1).getType()==Case.Type.VOLCAN){
								return getCase(x+1,y+1).getOrientation() != tuile.get_Orientation_Volcan();
							}
							else return false;
						}
					}
				}
				else{
					// On essaye de jouer partiellement sur de tuiles ou de niveaux différents : interdit
					return false;
				}
			}
			else{
				// On joue au niveau 1, il faut que ce soit en contact
				return en_contact(tuile.getOrientation(),P);
			}
		}
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////// EXTENSION CITE //////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	// Renvoie le nombre de huttes necessaires a l'extension de la cite presente au point P sur les cases de Type type.
	public int nb_huttes_extension(Point P, Case.Type type){
		Cite cite = getCite(P);
		ArrayList<Point> pts_extension = getPts_extension_cite(cite,type);
		int nb_huttes = 0;
		for(int i = 0; i<pts_extension.size(); i++){
			nb_huttes += getCase(pts_extension.get(i)).getNiveau();
		}
		return nb_huttes;
	}
	
	// Renvoie le Terrain apres extension d'une cite presente au point P sur les cases de Type type.
	// Ne modifie pas la structure actuelle.
	public Terrain consulter_extension_cite(Point P, Case.Type type){
		Terrain T = this.clone();
		T.etendre_cite(P,type);
		return T;
	}
	
	// Etend la cite presente au point P sur les cases de Type type.
	// Renvoie 0 si l'extension a reussi, 1 sinon
	public int etendre_cite(Point P, Case.Type type){
		Case.Couleur_Joueur c = getCase(P).getCouleur();
		if(!getCase(P).est_Libre() && c != Case.Couleur_Joueur.NEUTRE){
			Cite cite = getCite(P);
			int index_c = cites_indexOf(cite);
			ArrayList<Point> pts_extension = getPts_extension_cite(cite,type);
			if(pts_extension.size()>0){
				for(Point pt_extension : pts_extension){
					// On traite chaque case a etendre
					int n = getCase(pt_extension).getNiveau();
					ArrayList<Cite> cites_proches = getCitesContact(pt_extension,c);
					// On l'ajoute a l'historique
					histo_batiments.add(new Action_Batiment(Case.Type_Batiment.HUTTE,n,n,pt_extension,c));
					// On ajoute les batiments sur la case
					getCase(pt_extension).ajouter_batiment(Case.Type_Batiment.HUTTE,c);
					// On ajoute a la cite
					cite.ajouter(pt_extension, Case.Type_Batiment.HUTTE);
					index_cite[pt_extension.x][pt_extension.y]=index_c;
					if(cites_proches.size()>1){
						// Cette extension connecte deux cites ou plus
						for(int j=1;j<cites_proches.size();j++){
							fusion_cite(cites_proches.get(0),cites_proches.get(j));
						}
						cite = getCite(P);
						index_c = cites_indexOf(cite);
					}
				}
				return 0;
			}
			else return 1;
		}
		else return 1;
	}
	
	// Renvoie la liste des cites de couleur c en contact avec P
	private ArrayList<Cite> getCitesContact(Point P, Case.Couleur_Joueur c){
		Point [] ptsVoisins = getPtsVoisins(P);
		ArrayList<Cite> cites_trouvees = new ArrayList<Cite>();
		for(int i=0; i<6; i++){
			if(!getCase(ptsVoisins[i]).est_Libre() && getCase(ptsVoisins[i]).getCouleur()==c && !cites_trouvees.contains(getCite(ptsVoisins[i]))){
				cites_trouvees.add(getCite(ptsVoisins[i]));
			}
		}
		return cites_trouvees;
	}

	// Renvoie l'ensemble des points concernes par l'extension de la cite sur les cases de Type type.
	public ArrayList<Point> getPts_extension_cite(Cite cite, Case.Type type){
		ArrayList<Point> res = new ArrayList<Point>();
		ArrayList<Point> ptsCite = cite.getPts();
		boolean [][] appartient_res = new boolean[TAILLE][TAILLE];
		for(int i=0;i<TAILLE;i++){
			for(int j=0;j<TAILLE;j++){
				appartient_res[i][j] = false;
			}
		}
		if(type != Case.Type.VIDE){
			for(int i=0;i<ptsCite.size();i++){
				Point [] voisins = getPtsVoisins(ptsCite.get(i));
				for(int j=0;j<6;j++){
					if(getCase(voisins[j]).getType() == type && !appartient_res[voisins[j].x][voisins[j].y] && getCase(voisins[j]).est_Libre()){
						res.add(voisins[j]);
						appartient_res[voisins[j].x][voisins[j].y] = true;
					}
				}
			}
		}
		return res;
	}
/*
	// Renvoie l'ensemble des cases concernees par l'extension de la cite sur les cases de Type type.
	private ArrayList<Case> getCases_extension_cite(Cite cite, Case.Type type){
		ArrayList<Case> res = new ArrayList<Case>();
		ArrayList<Point> ptsCite = cite.getPts();
		boolean [][] appartient_res = new boolean[TAILLE][TAILLE];
		for(int i=0;i<TAILLE;i++){
			for(int j=0;j<TAILLE;j++){
				appartient_res[i][j] = false;
			}
		}
		if(type != Case.Type.VIDE){
			for(int i=0;i<ptsCite.size();i++){
				Point [] voisins = getPtsVoisins(ptsCite.get(i));
				for(int j=0;j<6;j++){
					if(getCase(voisins[j]).getType() == type && !appartient_res[voisins[j].x][voisins[j].y] && getCase(voisins[j]).est_Libre()){
						res.add(getCase(voisins[j]));
						appartient_res[voisins[j].x][voisins[j].y] = true;
					}
				}
			}
		}
		return res;
	}
*/
	// Renvoie vrai ssi la cite donnee contient au moins un batiment de type b
	private boolean cite_contient(Cite cite, Case.Type_Batiment b){
		switch(b){
		case TEMPLE: return cite.getNbTemples()>0;
		case TOUR: return cite.getNbTours()>0;
		default:
			return false;
		}
	}
	
	// Renvoie vrai ssi la cite donnee est de taille >=3
	private boolean cite_taille_3(Cite cite){
		return cite.getTaille()>=3;
	}
/*
	// Calcule dans res l'ensemble des points de la cite liee a P
	private ArrayList<Point> getPtsCite(Point P){
		ArrayList<Point> ptsCite = new ArrayList<Point>();
		if(getCase(P).getCouleur() != Case.Couleur_Joueur.NEUTRE){
			boolean [][] appartient_cite = new boolean[TAILLE][TAILLE];
			for(int i=0;i<TAILLE;i++){
				for(int j=0;j<TAILLE;j++){
					appartient_cite[i][j] = false;
				}
			}
			getPtsCite_rec(P,ptsCite,appartient_cite);
		}
		return ptsCite;
	}

	// Recursion de la fonction ci-dessus
	private void getPtsCite_rec(Point P, ArrayList<Point> res, boolean [][] appartient_cite){
		Point [] voisins = getPtsVoisins(P);
		res.add(P);
		appartient_cite[P.x][P.y] = true;
		Case.Couleur_Joueur c = getCase(P).getCouleur();
		for(int i=0;i<6;i++){
			if(getCase(voisins[i]).getCouleur() == c && !appartient_cite[voisins[i].x][voisins[i].y]){
				getPtsCite_rec(voisins[i],res,appartient_cite);
			}
		}
	}
*/
	///////////////////////////////////////////////////////////////////////////
	/////////////////////////// PLACEMENT BATIMENT ////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	// Renvoie le niveau THEORIQUE de placement d'un batiment au Point P
	public int getNiveauTheoriqueBatiment(Point P){
		return getCase(P).getNiveau();
	}
	
	// Renvoie le Terrain apres placement direct d'un batiment b de couleur c au point P.
	// Ne modifie pas la structure actuelle.
	public Terrain consulter_coup_batiment(Case.Type_Batiment b, Case.Couleur_Joueur c, Point P){
		Terrain T = this.clone();
		T.placer_batiment(b,c,P);
		return T;
	}
	
	// Place directement un batiment de type b au point P (hors extension de cite).
	// Renvoie 0 si le placement a reussi, 1 sinon.
	public int placer_batiment(Case.Type_Batiment b, Case.Couleur_Joueur c, Point P){
		int n;
		if((n = conditions_placement_batiment(b,c,P))>=0){
			histo_batiments.add(new Action_Batiment(b,getNiveauTheoriqueBatiment(P),1,P,c));
			if(n == 0){
				// C'est une nouvelle cite
				cites.add(new Cite(P,c,b));
				index_cite[P.x][P.y] = cites.size()-1;
			}
			else{
				/*Point [] ptsVoisins = getPtsVoisins(P);
				int nb_cites_trouvees = 0;
				Point [] coord_cite = new Point[6];
				for(int i=0; i<6; i++){
					if(!getCase(ptsVoisins[i]).est_Libre() && getCase(ptsVoisins[i]).getCouleur()==c){
						coord_cite[nb_cites_trouvees] = ptsVoisins[i];
						nb_cites_trouvees ++;
					}
				}*/
				ArrayList<Cite> cites_trouvees = getCitesContact(P,c);
				cites_trouvees.get(0).ajouter(P,b);
				index_cite[P.x][P.y] = cites_indexOf(cites_trouvees.get(0));
				int nb_cites_trouvees = cites_trouvees.size();
				if(n != nb_cites_trouvees) System.out.println("ERREUR placer batiment - gestion des cites");
				// On choisit arbitrairement la cite qui contiendra le batiment
				if(n>1){
					// S'il y a plusieurs cites a cote, on fusionne
					for(int i=1;i<n;i++){
						fusion_cite(cites_trouvees.get(0),cites_trouvees.get(i));
					}
				}
			}
			getCase(P).ajouter_batiment(b,c);
			return 0;
		}
		else return 1;
	}
	
	private int cites_indexOf(Cite C){
		return getIndexCite(C.getPts().get(0));
	}
	
	// Fusionne C et C2 => C
	private void fusion_cite(Cite C, Cite C2){
		int index_C = cites_indexOf(C);
		int index_C2 = cites_indexOf(C2);
		//System.out.println("FUSION " + index_C2 + " -> " + index_C);
		ArrayList<Point> ptsC2 = C2.getPts();
		Point P;
		for(int i=0;i<ptsC2.size();i++){
			P = ptsC2.get(i);
			index_cite[P.x][P.y] = index_C;
		}
		C.fusionner_avec(C2);
		cites.remove(index_C2);
		for(int i=limites.xmin;i<=limites.xmax;i++){
			for(int j=limites.ymin;j<=limites.ymax;j++){
				if(index_cite[i][j]>index_C2){
					index_cite[i][j] = index_cite[i][j]-1;
				}
			}
		}
	}
	
	// Renvoie vrai ssi le placement direct d'un batiment de type b au point P est autorise.
	public boolean placement_batiment_autorise(Case.Type_Batiment b, Case.Couleur_Joueur c, Point P){
		return conditions_placement_batiment(b,c,new Point(P.x,P.y))>=0;
	}
	
	// Renvoie -1 si le placement est interdit, sinon :
	// 0 si c'est une nouvelle cite
	// n>0 le nombre de cites adjacentes a P
	private int conditions_placement_batiment(Case.Type_Batiment b, Case.Couleur_Joueur c, Point P){
		if(getCase(P).ajout_batiment_autorise() && c != Case.Couleur_Joueur.NEUTRE){
			// Si le placement est autorise sur la case (independemment du reste du terrain)
			ArrayList<Cite> cites_trouvees = getCitesContact(P,c);
			int nb_cites_trouvees = cites_trouvees.size();
			if(nb_cites_trouvees>0){
				// C'est l'ajout d'une tour ou d'un temple (sinon c'est interdit)
				if(b == Case.Type_Batiment.HUTTE) return -1;
				boolean valide = false;
				for(int i=0;i<nb_cites_trouvees;i++){
					//Cite cite = getCite(coord_cite[i]);
					Cite cite = cites_trouvees.get(i);
					if(b == Case.Type_Batiment.TOUR)
						valide = valide || (getCase(P).getNiveau() >= 3 && !cite_contient(cite,Case.Type_Batiment.TOUR));
					else // (b == Case.Type_Batiment.TEMPLE)
						valide = valide || (cite_taille_3(cite) && !cite_contient(cite,Case.Type_Batiment.TEMPLE));
				}
				return (valide) ? nb_cites_trouvees : -1;
			}
			else{
				// C'est une nouvelle cite : ce doit etre une hutte au niveau 1
				return (b == Case.Type_Batiment.HUTTE && getCase(P).getNiveau()==1) ? 0 : -1;
			}
		}
		else return -1;
	}
	
	// Liste des extensions possibles
	public ArrayList<Action_Construction> liste_extensions_possibles(Case.Couleur_Joueur c){
		ArrayList<Action_Construction> res = new  ArrayList<Action_Construction>();
		ArrayList<Point> ptsCite_visites = new ArrayList<Point>();
		if(c == Case.Couleur_Joueur.NEUTRE) return res;
		Point P;
		int nb;
		for(int i=limites.xmin;i<=limites.xmax;i++){
			for(int j=limites.ymin;j<=limites.ymax;j++){
				P = new Point(i,j);
				if(getCase(P).getCouleur() == c){
					// Il y a une cite au point P, c'est une extension
					if(!ptsCite_visites.contains(P)){
						// Si on n'a pas deja regarde cette cite
						ArrayList<Point> ptsCite = getCite(P).getPts();
						for(int l=0; l<ptsCite.size();l++){
							ptsCite_visites.add(ptsCite.get(l));
						}
						// Gestion de tous les types d'extension
						Case.Type [] types = Case.Type.values();
						for(int k=0;k<types.length;k++){
							if(types[k] != Case.Type.VIDE && types[k] != Case.Type.VOLCAN){
								if((nb = nb_huttes_extension(P,types[k]))>0)
									res.add(new Action_Construction(P,types[k],nb));
							}
						}
					}
				}
			}
		}
		return res;
	}
	
	// Liste des constructions possibles hors extension
	public ArrayList<Action_Construction> liste_coups_construction_possibles(Case.Couleur_Joueur c){
		ArrayList<Action_Construction> res = new  ArrayList<Action_Construction>();
		if(c == Case.Couleur_Joueur.NEUTRE) return res;
		Point P;
		for(int i=limites.xmin;i<=limites.xmax;i++){
			for(int j=limites.ymin;j<=limites.ymax;j++){
				P = new Point(i,j);
				if(getCase(P).est_Libre()){
					if(placement_batiment_autorise(Case.Type_Batiment.HUTTE, c, P))
						res.add(new Action_Construction(Action_Construction.Type.HUTTE, P));
					if(placement_batiment_autorise(Case.Type_Batiment.TOUR, c, P))
						res.add(new Action_Construction(Action_Construction.Type.TOUR, P));
					if(placement_batiment_autorise(Case.Type_Batiment.TEMPLE, c, P))
						res.add(new Action_Construction(Action_Construction.Type.TEMPLE, P));
				}
			}
		}
		return res;
	}
	
	// Renvoie la liste des emplacements possibles pour la Tuile tuile
	public ArrayList<Action_Tuile> liste_coups_tuile_possibles(Tuile tuile){
		ArrayList<Action_Tuile> res = new  ArrayList<Action_Tuile>();
		for(int o=0;o<6;o++){
			for(int i=limites.xmin-2;i<=limites.xmax+2;i++){
				for(int j=limites.ymin-2;j<=limites.ymax+1;j++){
					if(placement_tuile_autorise(tuile, new Point(i,j)))
						res.add(new Action_Tuile(tuile.clone(),new Point(i,j),getCase(i,j).getNiveau()+1));
				}
			}
			tuile = tuile.clone();
			tuile.Tourner_horaire();
		}
		return res;
	}
	
	// Affiche le terrain dans la console
	public void afficher(){
		System.out.println("  " + limites.xmin + " - " + limites.xmax);
		System.out.println(limites.ymin);
		System.out.println("-");
		System.out.println(limites.ymax);
		for(int i=limites.ymin;i<=limites.ymax;i++){
			for(int j=limites.xmin;j<=limites.xmax;j++){
				System.out.print(t[j][i].getType().toChar());
			}
			System.out.print("  ");
			for(int j=limites.xmin;j<=limites.xmax;j++){
				System.out.print(t[j][i].getNiveau());
			}
			System.out.print("  ");
			for(int j=limites.xmin;j<=limites.xmax;j++){

				System.out.print(t[j][i].getBType().toChar());
			}
			System.out.print("  ");
			for(int j=limites.xmin;j<=limites.xmax;j++){
				System.out.print(t[j][i].getBNb());
			}
			System.out.print("  ");
			for(int j=limites.xmin;j<=limites.xmax;j++){
				if(index_cite[j][i] == -1) System.out.print("_");
				else System.out.print(index_cite[j][i]);
			}
			System.out.print("  ");
			for(int j=limites.xmin;j<=limites.xmax;j++){
				System.out.print(t[j][i].getCouleur().toChar());
			}

			System.out.print("  ");
			for(int j=limites.xmin;j<=limites.xmax;j++){
				if(index_cite[j][i] != -1)
					System.out.print(getCite(new Point(j,i)).getCouleur().toChar());
				else
					System.out.print("_");
			}
			System.out.println("");
		}
		for(int i = 0; i<cites.size();i++){
			System.out.println("Cite " + i + ", taille " + cites.get(i).getPts().size());
			for(int j = 0; j<cites.get(i).getPts().size(); j++){
				System.out.println(cites.get(i).getPts().get(j));
			}
		}
		System.out.println("histo_batiments, taille " + histo_batiments.size());
		for(int i = 0; i<histo_batiments.size();i++){
			System.out.println(histo_batiments.get(i).getPosition() + " - " + histo_batiments.get(i).getTypeBatiment() + " - " + histo_batiments.get(i).getCouleur());
		}
		System.out.println("");
		System.out.println("");
	}
}
