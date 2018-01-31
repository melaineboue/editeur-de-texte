package com.istic.m1.tp.editeur.commandeConcrete;

import com.istic.m1.tp.editeur.commande.Command;
import com.istic.m1.tp.editeur.receiver.MoteurEdition;
import com.istic.m1.tp.editeur.receiver.MoteurEditionImpl;

public class Coller implements Command{

	MoteurEdition moteur;


	public Coller(MoteurEdition moteur) {
		this.moteur = moteur;  //la liaison entre la commande et le moteur
	}

	/**
	 * Execute la commande coller
	 */
	@Override
	public void execute() {
		moteur.coller();
	}

}
