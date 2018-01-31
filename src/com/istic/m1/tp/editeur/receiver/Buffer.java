package com.istic.m1.tp.editeur.receiver;

public class Buffer {

	private StringBuffer texte_du_buffer; //le buffer

	/**
	 * Le constructeur
	 */
	public Buffer() {
		this.texte_du_buffer =new StringBuffer("");
	}

	/**
	 * getter du buffer
	 * @return le buffer
	 */
	public StringBuffer getStringBuffer() {
		return texte_du_buffer;
	}

	/**
	 * setter du buffer
	 * @param texte_du_buffer : le contenu dans l'attribut texte_du_buffer
	 */
	public void setText(String texte_du_buffer) {
		this.texte_du_buffer = new StringBuffer(texte_du_buffer);
	}

	/**
	 * setter du buffer
	 * @param buffer : le buffer
	 */
	public void setStringBuffer(StringBuffer buffer) {
		this.texte_du_buffer = buffer;
	}



	/**
	 * getter
	 * @return le contenu du buffer
	 */
	public String getText()
	{
		return texte_du_buffer.toString();
	}


}
