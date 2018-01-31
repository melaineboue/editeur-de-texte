package com.istic.m1.tp.editeur.commandeConcrete;

import com.istic.m1.tp.editeur.commande.Command;
import com.istic.m1.tp.editeur.enregistrable.EnregistreurImpl;

public class Undo implements Command{
    EnregistreurImpl enregistreur;


	public Undo(EnregistreurImpl e) {
		this.enregistreur = e;  //la liaison entre la commande et l'enregistreur
	}

	/**
	 * Execute la commande rejouer
	 */
	@Override
	public void execute() {
		enregistreur.undo();
	}

}
