package com.istic.m1.tp.editeur.caretaker;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.omg.CORBA.VersionSpecHelper;

import com.istic.m1.tp.editeur.memento.Memento;

public class Caretaker {


	private Stack<Memento> mementosUndo;  //sauvegarde une liste d'etats (Undo)
	private Stack<Memento> mementosRedo;  //sauvegarde une liste d'etats (Redo)
	private List<Memento> mementos;  //sauvegarde une liste d'etats (pour Rejouer)
	private Memento valeurCourante;
	private int positionCurseur;


	/**
	 * Cronstructeur du Caretaker, on initialise les piles undo , redo
	 */
	public Caretaker() {
		mementosUndo = new Stack(); //creation de la pile Undo
		mementosRedo = new Stack(); //creation de la pile Redo
		mementos = new ArrayList(); //creation de la liste des etats (� Rejouer)
		valeurCourante = null;
		positionCurseur = 0;
	}





	public int getPositionCurseur() {
		return positionCurseur;
	}





	public void setPositionCurseur(int positionCurseur) {
		this.positionCurseur = positionCurseur;
	}





	public Stack<Memento> getMementosUndo() {
		return mementosUndo;
	}


	public Stack<Memento> getMementosRedo() {
		return mementosRedo;
	}


	public List<Memento> getMementos() {
		return mementos;
	}



	/**
	 * 
	 * Sauvegarde l'etat m dans la pile undo ou dans la valeur en cours si il n'y a pas encore de valeur
	 * @param m : l'etat du moteur � l'instant de l'enregistrement
	 */
	public void garderUndoRedo(Memento m) {
		if(valeurCourante != null)
		{
			mementosUndo.push(valeurCourante);  // on ajoute le memento dans la liste du Caretaker
			valeurCourante = m;
		}
		else
		{
			valeurCourante = m;
		}

		//quand on appuie sur le clavier, on doit vider la pile Redo
		//la pile redo est seulement aliment� quand on appuie sur Undo
		mementosRedo = new Stack();

	}


	/**
	 * Sauvegarde l'etat m dans la liste des etats
	 * @param m : l'etat du moteur � l'instant de l'enregistrement
	 */
	public void garder(Memento m) {
		mementos.add(m);  // on ajoute le memento dans la liste du Caretaker
	}

	/**
	 * Met la valeur courante dans la pile redo et donne � l'enregistreur l'etat
	 * qui se trouve au dessus de la pile undo
	 * @return l'etat qui est stock� au dessus de la pile undo
	 */
	public Memento donnerPrecedent() {
		mementosRedo.push(valeurCourante);
		valeurCourante=mementosUndo.pop();
		return valeurCourante;
	}


	/**
	 * Met la valeur courante dans la pile undo et donne � l'enregistreur l'etat
	 * qui se trouve au dessus de la pile redo
	 * @return l'etat qui est stock� au dessus de la pile redo
	 */
	public Memento donnerSuivant() {
		mementosUndo.push(valeurCourante);
		valeurCourante=mementosRedo.pop();
		return valeurCourante;
	}


	/**
	 * Donne � l'enregistreur l'etat qui se trouve � la position positionCurseur
	 * @return m : l'etat qui est stock� dans le cartaker � la position courante
	 */
	public Memento donner() {
		Memento m = mementos.get(positionCurseur); //on reccup�re le memento qui se trouve � la position positionCurseur
		positionCurseur++;

		return m;

	}


	/**
	 * V�rifie si il y a des donn�es � lire dans la liste du caretaker
	 * @return true s'il y a encore un element � lire
	 */
	public boolean hasNextLecture() {
		return positionCurseur <= mementos.size()-1;  //on teste si la position courante n'est pas la fin de la liste
	}

	/**
	 * V�rifie si il y a des donn�es � lire dans la pile redo
	 * @return true s'il y a encore un element � lire
	 */
	public boolean hasNext() {
		return mementosRedo.size()>=1;  //on teste si la pile redo contient au moins un element
	}


	/**
	 * V�rifie si il y a des donn�es � lire dans la pile undo
	 * @return true s'il y a encore un element � lire
	 */
	public boolean hasPrevious() {
		return mementosUndo.size()>=1;  //on teste si la pile undo contient au moins un element
	}

}
