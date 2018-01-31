package com.istic.m1.tp.editeur.receiver;

public class Selection {

	//la position du curseur debut et du curseur fin
	//les deux valeurs sont différentes quand du texte a été selectionné, sinon ils ont les mêmes valeures
	//par exemple si debut égal à 0 et fin égal à 4, cela veut dire que
	//la selection commence du caractère 0 jusqu'avant le caractère 4 (donc le caractère 4 est exclu)
	private int debut;
	private int fin;


	/**
	 * Constructeurs
	 */
	public Selection() {
		this.setDebut(0);
		this.setFin(0);

	}
	/**
	 * getter debut
	 * @return la position du curseur debut
	 */
	public int getDebut() {
		return debut;
	}


	/**
	 * setter debut
	 * @param debut : la position debut
	 */
	public void setDebut(int debut) {
		this.debut = debut;
	}


	/**
	 * getter fin
	 * @return la position
	 */
	public int getFin() {
		return fin;
	}


	/**
	 * setter fin
	 * @param fin_selection :  la valeur de la position fin
	 */
	public void setFin(int fin_selection) {
		this.fin = fin_selection;
	}

	/**
	 * deplace les deux curseurs d'un certain pas
	 * @param pas : le nombre de pas qu'on doit faire avancer les deux curseurs
	 */
	public void mouvedCursor(int pas)
	{
		setDebut(getDebut()+pas);
		setFin(getFin()+pas);
	}



}
