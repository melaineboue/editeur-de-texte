package com.istic.m1.tp.editeur.commandeConcrete;

import javax.swing.JLabel;

import com.istic.m1.tp.editeur.commande.Command;
import com.istic.m1.tp.editeur.enregistrable.EnregistreurImpl;

public class Rejouer implements Command{
    EnregistreurImpl enregistreur;
    JLabel texte;

	public Rejouer(EnregistreurImpl e) {
		this.enregistreur = e;  //la liaison entre la commande et l'enregistreur
	}

	/**
	 * Execute la commande rejouer
	 */
	@Override
	public void execute() {
		enregistreur.rejouer(texte);
	}


	public void setParameter(JLabel texte) {
		this.texte = texte;


	}

}
