package com.istic.m1.tp.editeur.memento;

public class ConcreteMemento implements Memento{

	//informations à stocker
	private String presse_papier;  //le contenu du presse papier
	private String buffer;		   // le contenu du buffer
	private int start_cursor;	   //le curseur debut
	private int end_cursor;		   //le curseur fin



	/**
	 * Constructeur
	 * @param presse_papier : le contenu du presse papier
	 * @param buffer : le contenu du buffer
	 * @param start_cursor : la position debut du curseur
	 * @param end_cursor : la position fin du curseur
	 */
	public ConcreteMemento(String presse_papier, String buffer, int start_cursor, int end_cursor) {

		this.presse_papier = presse_papier;
		this.buffer = buffer;
		this.start_cursor = start_cursor;
		this.end_cursor = end_cursor;
	}


	/**
	 * getter de presse_papier
	 * @return le contenu du presse_papier
	 */
	public String getPresse_papier() {
		return presse_papier;
	}

	public void setPresse_papier(String presse_papier) {
		this.presse_papier = presse_papier;
	}

	/**
	 * getter de buffer
	 * @return le contenu du buffer
	 */
	public String getBuffer() {
		return buffer;
	}


	public void setBuffer(String buffer) {
		this.buffer = buffer;
	}

	/**
	 * getter de start_cursor
	 * @return la valeur du start_cursor ( curseur debut )
	 */
	public int getStart_cursor() {
		return start_cursor;
	}

	public void setStart_cursor(int start_cursor) {
		this.start_cursor = start_cursor;
	}

	/**
	 * getter de end_cursor
	 * @return la valeur du start_cursor ( curseur end )
	 */
	public int getEnd_cursor() {
		return end_cursor;
	}


	public void setEnd_cursor(int end_cursor) {
		this.end_cursor = end_cursor;
	}



}
