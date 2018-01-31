package com.istic.m1.tp.editeur.receiver;

public class PressePapier {

	private String pressePapier; //le contenu du presse papier


	/**
	 * Constructeur
	 */
	public PressePapier() {
		this.pressePapier = "";
	}


	/**
	 * getter pressePapier
	 * @return le contenu du presse papier
	 */
	public String getPressePapier() {
		return pressePapier;
	}


	/**
	 * setter du pressePapier
	 * @param pressePapier : la valeur du contenu du pressePapier
	 */
	public void setPressePapier(String pressePapier) {
		this.pressePapier = pressePapier;
	}

}
