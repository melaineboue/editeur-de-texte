package com.istic.m1.tp.editeur.commandeConcrete;

import com.istic.m1.tp.editeur.commande.Command;
import com.istic.m1.tp.editeur.receiver.MoteurEdition;
import com.istic.m1.tp.editeur.receiver.MoteurEditionImpl;

public class Inserer implements Command{

	MoteurEdition moteur;
	private String texte; //le texte à inserer



	public Inserer(MoteurEdition moteur) {
		this.moteur = moteur;  //la liaison entre la commande et le moteur
	}

	/**
	 * getter de l'attribut texte
	 * @return le texte à inserer
	 */
	public String getTexte() {
		return texte;
	}

	/**
	 * setter de l'attribut texte
	 * @param texte : texte à inserer
	 */
	public void setTexte(String texte) {
		this.texte = texte;
	}


	/**
	 * Execute la commande inserer
	 */
	@Override
	public void execute() {
		moteur.inserer(texte);
	}

}
