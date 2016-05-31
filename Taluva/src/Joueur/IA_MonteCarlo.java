package Joueur;

import java.util.ArrayList;
import java.util.Random;

import Action.Action_Construction;
import Action.Action_Tuile;
import Action.Actions_Tour;
import Moteur.Moteur;
import Moteur.Phase.Phase_Jeu;
import terrain.Case.Couleur_Joueur;
import terrain.Cite;
import terrain.Tuile;

public class IA_MonteCarlo extends IA_Generique {

	private int taille_echantillon;
	private Moteur moteur;
	
	public IA_MonteCarlo(Couleur_Joueur c, Moteur m){
		super(c, m);
	}
	
	public IA_MonteCarlo(int taille_echantillon, Couleur_Joueur c, Moteur m){
		super(c, m);
		this.taille_echantillon = taille_echantillon;
	}
	
	public Actions_Tour get_coup_tour(Tuile tuile){
		moteur = m.clone();
		ArrayList<Actions_Tour> res = new ArrayList<Actions_Tour>();
		int valeur_res = Integer.MIN_VALUE;
		int valeur_courante;
		ArrayList<Action_Tuile> coups_tuile_possibles = moteur.get_liste_coup_tuile(tuile);
		for(Action_Tuile actTuile : coups_tuile_possibles){
			moteur.jouer_action(actTuile);
			moteur.Maj_liste_coup_construction();
			ArrayList<Action_Construction> coups_construction_possibles = moteur.get_liste_coup_construction().to_ArrayList();
			for(Action_Construction actConstruction : coups_construction_possibles){
				moteur.jouer_action(actConstruction);
				valeur_courante = evaluation_configuration();
				if(valeur_courante > valeur_res){
					res.clear();
					valeur_res = valeur_courante;
					res.add(new Actions_Tour(actTuile,actConstruction));
				}
				else if(valeur_courante == valeur_res){
					res.add(new Actions_Tour(actTuile,actConstruction));
				}
				moteur.annuler();
			}
			moteur.annuler();
		}
		Random r = new Random();
		return res.get(r.nextInt(res.size()));
	}
	
	private int evaluation_configuration(){
		Moteur saved_moteur = moteur.clone();
		int balance = 0;
		for(int num_partie = 0; num_partie<taille_echantillon; num_partie ++){
			int i=0;
			while(moteur.get_etat_jeu() != Phase_Jeu.FIN_DE_PARTIE){
				moteur.swap_joueur();
				Actions_Tour AT = choisir_coup(moteur.piocher());
				if(moteur.jouer_action(AT.getAction_tuile()) != 0)
					System.out.println("Wololo");
				if(moteur.jouer_action(AT.getAction_construction()) != 0)
					System.out.println("Wololo aussi");
				System.out.println(i);
				i++;
			}
			if(moteur.getGagnant() == null) System.out.println("YOLO");
			if(moteur.getGagnant().getCouleur() == this.getCouleur())
				balance += 1;
			else
				balance -= 1;
			moteur = saved_moteur;
		}
		return balance;
	}
	
	private Actions_Tour choisir_coup(Tuile tuile){
		int i=0, score_max = Integer.MIN_VALUE;
		int score_courant;
		Random R = new Random();
		ArrayList<Action_Tuile> liste = moteur.get_liste_coup_tuile(tuile);
		int score = Integer.MAX_VALUE;
		//Coup_Tuile_Heuristique TH_retour = new Coup_Tuile_Heuristique(0,null);
		ArrayList<Action_Tuile> AT_retour = new ArrayList<Action_Tuile>();
		Coup_Construction_Heuristique retour_REC;
		Action_Construction coup_construction_retour = null;
		while( i < liste.size() && score_max < score)
		{
			if(moteur.jouer_action(liste.get(i)) == 0)
			{
				moteur.Maj_liste_coup_construction();

				retour_REC = choisir_construction_bon(moteur.get_liste_coup_construction().to_ArrayList(),score);
				score_courant = retour_REC.getHeuristique();
				// si le coup est aussi optimal que le plus optimal trouvé, on l'ajoute.
				if(score_courant == score_max)
				{
					//TH_retour.setActionTuile(liste.get(i));
					AT_retour.add(liste.get(i));
					coup_construction_retour = retour_REC.getActionConstruction();
				}
				// si le coup est plus optimal, on vide la liste et on en commence une nouvelle.
				else if (score_courant > score_max)
				{
					score_max = score_courant;
					//TH_retour.setActionTuile(liste.get(i));
					//TH_retour.setHeuristique(retour_REC.getHeuristique());
					AT_retour.clear();
					AT_retour.add(liste.get(i));
					coup_construction_retour = retour_REC.getActionConstruction();
				}
				moteur.annuler();
			}
			i++;
		}
		return new Actions_Tour(AT_retour.get(R.nextInt(AT_retour.size())),coup_construction_retour);
	}
	
	private Coup_Construction_Heuristique choisir_construction_bon(ArrayList <Action_Construction> liste, int score)
	{
		int i=0, score_max = Integer.MIN_VALUE;
		int score_courant;
		ArrayList<Action_Construction> liste_construction_retour = new ArrayList<Action_Construction>();
		while( i < liste.size() && score_max < score)
		{
			moteur.jouer_action(liste.get(i));
			score_courant = Heuristique();
			if(score_courant == score_max)
			{
				liste_construction_retour.add(liste.get(i));
			}
			else if (score_courant > score_max)
			{
				score_max = score_courant;
				liste_construction_retour.clear();
				liste_construction_retour.add(liste.get(i));
			}
			moteur.annuler();
			i++;
		}
		Random R = new Random();
		return new Coup_Construction_Heuristique(score_max, liste_construction_retour.get(R.nextInt(liste_construction_retour.size())));
	}
	
	private int Heuristique()
	{
		int bonPoints;
		int mauvaisPoints;
		if(moteur.EstLeMemeJoueur(this, moteur.getJ1()))
		{
			bonPoints = Calculer_points_heur(moteur.get_Jcourant());
			mauvaisPoints = Calculer_points_heur(moteur.getJ2());
		}
		else
		{
			bonPoints = Calculer_points_heur(moteur.get_Jcourant());
			mauvaisPoints = Calculer_points_heur(moteur.getJ1());
		}
		return bonPoints - mauvaisPoints;
	}
	
	private int Calculer_points_heur(Joueur_Generique c) {
		// on compte le score d'un joueur dans le terrain.
		int score =0;
		score += (Moteur.nb_max_Temples - c.getTemple()) * score_temple;
		score += (Moteur.nb_max_Tours - c.getTour()) * score_tour;
		score += (Moteur.nb_max_Huttes - c.getHutte()) * score_hutte;		
		// Si le joueur s'est débarassé de toutes ses pièces de 2 catégorie, il a gagné.
		if((c.getHutte() == 0 && c.getTemple() == 0) || (c.getTemple() ==0 && c.getTour() ==0) || (c.getHutte()==0 && c.getTour()==0))
		{
			score += 1000000000;
		}
		// Sinon, s'il s'en raproche:
		else if(c.getHutte() == 0 || c.getTour()==0 || c.getTemple()==0)
		{
			score += (moteur.get_nbTuiles()/ 2) * score_deplete_mult 
					/Math.max( c.getHutte()*hut_deplete_mult , Math.max(c.getTemple()* temple_deplete_mult, c.getTour()* tower_deplete_mult) );
			// Attention, c'est dangereux de ne plus avoir de huttes.
			if(c.getHutte() == 0)
			{
				score -= (moteur.get_nbTuiles()/ 2) * hut_deplete_cant_play_mult;
			}
		}
		// Ajouter les points dus aux qualités des cités;
		ArrayList<Cite> liste_cite = moteur.getTerrain().getCitesJoueur(c.getCouleur());
		for(int i=0; i < liste_cite.size(); i++)
			score += valeur_cite(liste_cite.get(i), c);
		return score;
	}
	
	// Calculer la valeur d'une cité.
		private int valeur_cite( Cite c, Joueur_Generique j)
		{
			int score_cite = score_city + c.getTaille() * score_zone_city;
			// Si la cité nous permettra de créer un temple, elle en vaux la moitié des points
			if(c.getTaille() >= 3 && c.getNbTemples() > 0 && moteur.get_nbTuiles()/2 > 1 && j.getTemple()>0)
			{
				// check destructible
				score_cite += score_temple;
			}
			if(c.getNbTemples() > 0)
			{
				score_cite = score_cite/ score_div_city_temple;
			}
			if( c.getNbTemples()>0 && c.getNbTours()>0)
			{
				score_cite = score_cite / score_div_city_temple_tower;
			}
			return score_cite;
		}
}
