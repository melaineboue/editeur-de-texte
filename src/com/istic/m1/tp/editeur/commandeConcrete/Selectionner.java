package com.istic.m1.tp.editeur.commandeConcrete;

import com.istic.m1.tp.editeur.commande.Command;
import com.istic.m1.tp.editeur.receiver.MoteurEdition;
import com.istic.m1.tp.editeur.receiver.MoteurEditionImpl;

public class Selectionner implements Command{

	MoteurEdition moteur;
	int debut,fin;  //le debut et la fin de la selection

	public Selectionner(MoteurEdition moteur) {
		this.moteur = moteur; //la liaison entre la commande et le moteur
	}

	/**
	 * Execute la commande selectionner pour selectionner le texte
	 */
	@Override
	public void execute() {
		moteur.selectionner(debut,fin);
	}


	/**
	 * Le setter de l'attribut debut
	 * @param debut : le debut de la selection
	 */
	public void setDebut(int debut)
	{
		this.debut=debut;
	}

	/**
	 * setter de l'attribut fin
	 * @param fin : la fin de la selection
	 */
	public void setFin(int fin)
	{
		this.fin=fin;
	}

}
