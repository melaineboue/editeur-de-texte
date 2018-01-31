package com.istic.m1.tp.editeur.receiver;

public class Selection {

	//la position du curseur debut et du curseur fin
	//les deux valeurs sont diff�rentes quand du texte a �t� selectionn�, sinon ils ont les m�mes valeures
	//par exemple si debut �gal � 0 et fin �gal � 4, cela veut dire que
	//la selection commence du caract�re 0 jusqu'avant le caract�re 4 (donc le caract�re 4 est exclu)
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
