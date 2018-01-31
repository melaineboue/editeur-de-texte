package com.istic.m1.tp.editeur.client;

import com.istic.m1.tp.editeur.enregistrable.EnregistreurImpl;

import com.istic.m1.tp.editeur.invoker.MonIHM;
import com.istic.m1.tp.editeur.receiver.MoteurEditionImpl;

import javafx.scene.input.KeyCode;

/**
 *Lanceur de l'editeur
 * @author SARR - BOUE
 * @version : 3.2
 */
public class Client {
	public static void main(String[] args) {
		MonIHM fenetre=new MonIHM();
		EnregistreurImpl enregistreur = new EnregistreurImpl();
		enregistreur.setIhm(fenetre);
		MoteurEditionImpl moteur=new MoteurEditionImpl();
		moteur.addObserver(fenetre);
		fenetre.initCommand(moteur);// initialisation de mon moteur dans mes commandes concrètes

		enregistreur.addObserver(moteur);
		moteur.setEnregistreur(enregistreur);

		fenetre.initCommandEnregistreur(enregistreur);

		fenetre.lancer();
	}

}
