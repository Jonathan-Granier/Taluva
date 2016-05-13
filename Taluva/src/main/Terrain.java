package main;

import java.awt.Point;
import java.util.ArrayList;

public class Terrain {
	
	public final static int TAILLE = 200;
	public final static Point CENTRE = new Point(TAILLE/2,TAILLE/2);
	
	private Case [][] t;
	private boolean empty;
	
	public Terrain(){
		t = new Case[TAILLE][TAILLE];
		for(int i=0;i<TAILLE;i++){
			for(int j=0;j<TAILLE;j++){
				t[i][j] = new Case(Case.Type.VIDE);
			}
		}
		empty = true;
	}
	
	public Terrain clone(){
		Terrain tmp = new Terrain();
		for(int i=0;i<TAILLE;i++){
			for(int j=0;j<TAILLE;j++){
				tmp.t[i][j] = this.t[i][j].clone();
			}
		}
		tmp.empty = this.empty;
		return tmp;
	}
	
	public Case [][] getT(){
		return t;
	}
	
	public boolean isEmpty(){
		return empty;
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
	
	//	Position pour le placement :
	//		     _	    _
	//		   _/X\    /X\_
	// GAUCHE / \_/    \_/ \ DROITE
	//	 	  \_/ \    / \_/
	//		    \_/    \_/
	
	// Renvoie le Terrain après placement de tuile au point P. Ne modifie pas la structure actuelle.
	public Terrain consulter_coup_tuile(Tuile tuile, Point P){
		if(placement_tuile_autorise(tuile,P)){
			Terrain T = this.clone();
			T.placer_tuile(tuile, P);
			return T;
		}
		else{
			return this;
		}
	}
	
	// Place la tuile donnée au point P. Renvoie 0 si la tuile a pu etre placée, 1 sinon.
	public int placer_tuile(Tuile tuile, Point P){
		if(placement_tuile_autorise(tuile,P)){
			int x = P.x;
			int y = P.y;
			t[x][y].setType(tuile.get_type_case(Case.Orientation.N));
			t[x][y].setOrientation(tuile.get_Orientation_Volcan());
			t[x][y].incrNiveau();
			t[x][y+1].setType(tuile.get_type_case(Case.Orientation.S));
			t[x][y+1].setOrientation(tuile.get_Orientation_Volcan());
			t[x][y+1].incrNiveau();
			if(tuile.getOrientation()==Tuile.Orientation.GAUCHE){
				t[x-1][y].setType(tuile.get_type_case(Case.Orientation.O));
				t[x-1][y].setOrientation(tuile.get_Orientation_Volcan());
				t[x-1][y].incrNiveau();
			}
			else{
				t[x+1][y+1].setType((tuile.get_type_case(Case.Orientation.E)));
				t[x+1][y+1].setOrientation(tuile.get_Orientation_Volcan());
				t[x+1][y+1].incrNiveau();
			}
			empty=false;
			return 0;
		}
		else{
			return 1;
		}
	}
	
	// Renvoie vrai ssi la tuile est dans le terrain
	private boolean dans_terrain(Tuile.Orientation o, Point P){
		if(o == Tuile.Orientation.GAUCHE)
			return P.x > 1 && P.y >= 1 && P.x < TAILLE-1 && P.y < TAILLE-2;
		else
			return P.x >= 1 && P.y >= 1 && P.x < TAILLE-2 && P.y < TAILLE-2;
	}

	// Renvoie les 3 cases de la tuile
	private Point [] cases_tuile(Tuile.Orientation o, Point P){
		int x = P.x;
		int y = P.y;
		Point [] res = new Point[3];
		res[0] = new Point(x,y);
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
		if(o == Tuile.Orientation.GAUCHE){
			res.add(new Point(x-2,y));
			res.add(new Point(x+1,y+1));
			res.add(new Point(x-1,y-1));
		}
		else{
			res.add(new Point(x+1,y+2));
			res.add(new Point(x+2,y+1));
			res.add(new Point(x-1,y));
		}
		return res;
	}
	
	// Renvoie vrai ssi la tuile est en contact avec au moins une autre tuile
	private boolean en_contact(Tuile.Orientation o, Point P){
		boolean trouve = false;
		ArrayList<Point> voisins;
		voisins=contact(o,P);
		int i = 0;
		while(!trouve && i<voisins.size()){
			trouve = !t[voisins.get(i).x][voisins.get(i).y].est_Vide();
			i++;
		}
		return trouve;
	}
	
	// Renvoie vrai ssi le placement de cette tuile est autorisé au point P.
	public boolean placement_tuile_autorise(Tuile tuile, Point P){
		if(empty) return dans_terrain(tuile.getOrientation(),P);
		else{
			// Teste si la tuile est posée sur d'autres tuiles
			int x = P.x;
			int y = P.y;
			int n0,n1,n2;
			Point [] cases_t = cases_tuile(tuile.getOrientation(),P);	// Les cases de la tuile
			n0 = t[cases_t[0].x][cases_t[0].y].getNiveau();
			n1 = t[cases_t[1].x][cases_t[1].y].getNiveau();		// On regarde les niveaux en-dessous de la tuile
			n2 = t[cases_t[2].x][cases_t[2].y].getNiveau();
			if(n0>0 || n1>0 || n2>0){
				// Si on tente de jouer sur au moins une tuile
				if(n0==n1 && n1==n2){
					// Si les 3 cases dessous sont au même niveau
					// On joue alors sur des tuiles, on vérifie la disposition des volcans
					if(tuile.get_type_case(Case.Orientation.N)==Case.Type.VOLCAN){
						// Si le Volcan est au Nord
						if(t[x][y].getType()==Case.Type.VOLCAN){
							return t[x][y].getOrientation() != tuile.get_Orientation_Volcan();
						}
						else return false;
					}
					else if(tuile.get_type_case(Case.Orientation.S)==Case.Type.VOLCAN){
						// Si le Volcan est au Sud
						if(t[x][y+1].getType()==Case.Type.VOLCAN){
							return t[x][y+1].getOrientation() != tuile.get_Orientation_Volcan();
						}
						else return false;
					}
					else{
						// Si le Volcan est sur le coté
						if(tuile.getOrientation()==Tuile.Orientation.GAUCHE){
							if(tuile.get_type_case(Case.Orientation.O)==Case.Type.VOLCAN){
								if(t[x-1][y].getType()==Case.Type.VOLCAN){
									return t[x-1][y].getOrientation() != tuile.get_Orientation_Volcan();
								}
								else return false;
							}
							else{
								System.out.println("Erreur : pas de Volcan sur cette tuile !");
								return false;
							}
						}
						else{
							if(tuile.get_type_case(Case.Orientation.E)==Case.Type.VOLCAN){
								if(t[x+1][y+1].getType()==Case.Type.VOLCAN){
									return t[x+1][y+1].getOrientation() != tuile.get_Orientation_Volcan();
								}
								else return false;
							}
							else{
								System.out.println("Erreur : Pas de volcan sur cette tuile !");
								return false;
							}
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
	
	// Renvoie le Terrain après placement de n batiments b au point P. Ne modifie pas la structure actuelle.
	public Terrain consulter_coup_batiment(Case.Type_Batiment b, int n, Point P){
		if(placement_batiment_autorise(b,n,P)){
			Terrain T = this.clone();
			T.placer_batiment(b,n,P);
			return T;
		}
		else{
			return this;
		}
	}
	
	// Place n batiments de type b au point P. Renvoie 0 si le placement a réussi, 1 sinon.
	public int placer_batiment(Case.Type_Batiment b, int n, Point P){
		// TODO
		return 0;
	}
	
	// Renvoie vrai ssi le placement de n batiments de type b au point P est autorisé.
	public boolean placement_batiment_autorise(Case.Type_Batiment b, int n, Point P){
		// TODO
		return t[P.x][P.y].ajout_batiment_autorise(b, n);
	}
	
	// Affiche le terrain en un rectangle entre min et max :
	// min -----|
	//  |       |
	//  |------max
	public void afficher(Point min, Point max){
		afficher(min.x,min.y,max.x,max.y);
	}
	
	// Affiche le terrain en un rectangle entre xmin,ymin et xmax,ymax :
	public void afficher(int xmin, int ymin, int xmax, int ymax){
		for(int i=xmin;i<xmax;i++){
			for(int j=ymin;j<ymax;j++){
				switch (t[j][i].getType()){
				case FORET:
					System.out.print("F");
					break;
				case LAC:
					System.out.print("L");
					break;
				case MONTAGNE:
					System.out.print("M");
					break;
				case PLAINE:
					System.out.print("P");
					break;
				case SABLE:
					System.out.print("S");
					break;
				case VIDE:
					System.out.print("_");
					break;
				case VOLCAN:
					System.out.print("V");
					break;
				default:
					break;
				}
			}
			for(int j=ymin;j<ymax;j++){
				System.out.print(t[j][i].getNiveau());
			}
			System.out.println("");
		}
	}
}
