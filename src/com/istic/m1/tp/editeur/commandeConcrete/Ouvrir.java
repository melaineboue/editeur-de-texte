package com.istic.m1.tp.editeur.commandeConcrete;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import com.istic.m1.tp.editeur.commande.Command;
import com.istic.m1.tp.editeur.receiver.MoteurEdition;

public class Ouvrir implements Command {

	MoteurEdition moteur;
	private String path;
	private JFrame fenetre; //la fenetre JFrame qui va appeler une boite de dialogue open

	public Ouvrir(MoteurEdition moteur) {
		this.moteur = moteur; //la liaison entre la commande et le moteur
		this.path = "";
	}

	public void setFenetre(JFrame fenetre) {
		this.fenetre = fenetre;
	}

	/**
	 * Execute le commande
	 */
	@Override
	public void execute() {
		moteur.ouvrir(path);

	}


	/**
	 * setter de l'attribut path
	 * @param path : le chemin du fichier a charger
	 */
	public void setPath(String path) {
		this.path = path;
	}

}
