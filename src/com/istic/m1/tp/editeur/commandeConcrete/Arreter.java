package com.istic.m1.tp.editeur.commandeConcrete;

import com.istic.m1.tp.editeur.commande.Command;
import com.istic.m1.tp.editeur.enregistrable.EnregistreurImpl;

public class Arreter implements Command {

	EnregistreurImpl enregistreur;

	public Arreter(EnregistreurImpl e) {
		this.enregistreur = e;  //la liaison entre la commande et l'enregistreur
	}

	/**
	 * Execution de la commande arreter
	 */
	@Override
	public void execute() {
		enregistreur.arreter();
	}

}
