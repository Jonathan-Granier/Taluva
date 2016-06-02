package Moteur;

import java.util.Stack;

import Action.Action_Construction;
import Action.Action_Tuile;
import Joueur.Joueur_Generique;
import terrain.Case;
import terrain.Terrain;

public class Moteur_light extends Moteur
{
	// Pile d'annulation allegee
	private Stack <Terrain> undo_terrain;
	private Stack <Joueur_Generique> undo_j1, undo_j2, undo_j3, undo_j4;
	
	public Moteur_light(Terrain T,int taille_Pioche_initiale) {
		super(T, taille_Pioche_initiale);
		undo_terrain = new Stack<Terrain>();
		undo_j1 = new Stack<Joueur_Generique>();
		undo_j2 = new Stack<Joueur_Generique>();
		undo_j3 = new Stack<Joueur_Generique>();
		undo_j4 = new Stack<Joueur_Generique>();
		// TODO Auto-generated constructor stub
	}
	
	public Moteur_light(Terrain T, Joueur_Generique j1, Joueur_Generique j2,int taille_Pioche_initiale)
	{
		super(T,j1,j2,taille_Pioche_initiale);
		undo_terrain = new Stack<Terrain>();
		undo_j1 = new Stack<Joueur_Generique>();
		undo_j2 = new Stack<Joueur_Generique>();
		undo_j3 = new Stack<Joueur_Generique>();
		undo_j4 = new Stack<Joueur_Generique>();
	}
	
	// Place une tuile.
	public int jouer_action(Action_Tuile AT)
	{
		if(T.placement_tuile_autorise(AT.getTuile(), AT.getPosition()))
		{
			save_state();
			this.T = T.consulter_coup_tuile(AT.getTuile(), AT.getPosition());
		}
		System.out.println("[Moteur_Light] Jouer_Action_tuile: Pose de la tuile non autorisée.");
		return 1;
	}
	
	// Joue une action de Construction
	public int jouer_action(Action_Construction AC)
	{
		if(AC.get_type() == Action_Construction.Type.EXTENSION && this.j_courant.getHutte() >= AC.get_nb_batiments())
		{
			save_state();
			this.T = T.consulter_extension_cite(AC.get_coord(), AC.get_type_extension());
			this.j_courant.decrementeHutte(AC.get_nb_batiments());
			return 0;
		}
		switch(AC.get_type())
		{
			case HUTTE:
				if(this.j_courant.getHutte()>0)
				{
					save_state();
					this.T = T.consulter_coup_batiment(Case.Type_Batiment.HUTTE, this.j_courant.getCouleur(), AC.get_coord());
					this.j_courant.decrementeHutte(1);
					return 0;
				}
				break;
			case TOUR:
				if(this.j_courant.getTour()>0)
				{
					save_state();
					this.T = T.consulter_coup_batiment(Case.Type_Batiment.TOUR, this.j_courant.getCouleur(), AC.get_coord());
					this.j_courant.decrementeTour();
				}
				break;
			case TEMPLE:
				if(this.j_courant.getTour()>0)
				{
					save_state();
					this.T = T.consulter_coup_batiment(Case.Type_Batiment.TEMPLE, this.j_courant.getCouleur(), AC.get_coord());
					this.j_courant.decrementeTemple();
					return 0;
				}
				break;
			default:;
		}
		return 1;
	}
	
	public int annuler()
	{
		int j_courant_liant;
		if(undo_terrain.isEmpty())
			return 1;
		// On récupère la référence du j_courant
		if(this.j_courant == this.j1)
		{
			j_courant_liant = 1;
		}
		else if (this.j_courant == this.j2)
		{
			j_courant_liant = 2;
		}
		else if (this.j_courant == this.j3)
		{
			j_courant_liant = 3;
		}
		else
		{
			j_courant_liant = 4;
		}
		
		// On récupère les données précédentes.
		this.T = undo_terrain.pop();
		this.j1 = undo_j1.pop();
		this.j2 = undo_j2.pop();
		if(this.nb_Joueur == 3)
		{
			this.j3 = undo_j3.pop();
		}
		else if(this.nb_Joueur == 4)
		{
			this.j4 = undo_j4.pop();
		}
		
		// On lie la reference du J_courant.
		switch(j_courant_liant)
		{
			case 1:
				j_courant = this.j1;
				break;
			case 2:
				j_courant = this.j2;
				break;
			case 3:
				j_courant = this.j3;
				break;
			case 4:
				j_courant = this.j4;
				break;
		}
		return 0;
	}
	
	// Sauvegarde de l'état de jeu, de façon légère.
	private void save_state()
	{
		undo_terrain.push(T);
		undo_j1.push(j1).clone();
		undo_j2.push(j2).clone();
		if(this.nb_Joueur == 3)
		{
			undo_j3.push(j3).clone();
		}
		else if(this.nb_Joueur == 4)
		{
			undo_j4.push(j4).clone();
		}
	}
}
