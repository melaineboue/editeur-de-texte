package com.istic.m1.tp.editeur.commandeConcrete;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;

import com.istic.m1.tp.editeur.commande.Command;
import com.istic.m1.tp.editeur.receiver.MoteurEdition;
import com.istic.m1.tp.editeur.receiver.MoteurEditionImpl;

//la classe save permet d'enregister le texte tapé dans un fichier sur le disque
public class Save implements Command{


	private MoteurEdition moteur;
	private String path;  //enregistre le chemin d'enregistrement du fichier
	private JFrame fenetre;




	public Save(MoteurEdition moteur) {
		this.moteur = moteur; //la liaison entre la commande et le moteur
		this.path="";
	}




	/**
	 * Execution de la commande save
	 */
	@Override
	public void execute() {

		//on crée ou on met à jour le contenu du fichier à l'adresse path
		moteur.save(path);

	}

	/**
	 * setter de l'attribut path
	 * @param path : le repertoire où doit etre le fichier
	 */
	public void setPath(String path)
	{
		this.path=path;
	}


}
