package com.istic.m1.tp.editeur.receiver;

import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import com.istic.m1.tp.editeur.caretaker.Caretaker;
import com.istic.m1.tp.editeur.commandeConcrete.Clavier;
import com.istic.m1.tp.editeur.enregistrable.EnregistreurImpl;
import com.istic.m1.tp.editeur.invoker.MonIHM;
import com.istic.m1.tp.editeur.memento.ConcreteMemento;
import com.istic.m1.tp.editeur.memento.Memento;
import com.istic.m1.tp.editeur.observer.Observable;
import com.istic.m1.tp.editeur.observer.Observer;
import com.sun.glass.events.KeyEvent;

//Le moteur observe l'IHM et est observé par l'IHM
public class MoteurEditionImpl implements MoteurEdition, Observable,Observer {


	private PressePapier presse_papier;
	private Selection selection;
	private Buffer buffer;
    private EnregistreurImpl enregistreur;

	private List<Observer> listeObserveurDuMoteur; //liste des observers du moteur

	/**
	 * Constructeur
	 */
	public MoteurEditionImpl() {
		//initialisation de : presse papier, buffer, selection et la liste des observers
		this.listeObserveurDuMoteur = new ArrayList<Observer>();
		presse_papier = new PressePapier();
		selection = new Selection();
		buffer = new Buffer();
		notifier();
	}



	/**
	 * setter de l'enregistreur
	 * @param enregistreur : l'enregistreur des commandes
	 */
	public void setEnregistreur(EnregistreurImpl enregistreur) {
		this.enregistreur = enregistreur;
	}



	/**
	 * coupe la selection et met le texte coupé dans le presse papier
	 * puis notifie l'IHM
	 */
	@Override
	public void couper() {
		// on va couper si du texte a été selectionné, c'est à dire
		// debut_selection est différent de fin_selection
		if (selection.getDebut() != selection.getFin()) {
			presse_papier.setPressePapier(buffer.getText().substring(selection.getDebut(), selection.getFin()));
			StringBuffer b = buffer.getStringBuffer().delete(selection.getDebut(), selection.getFin());
			selection.setFin(selection.getDebut());
			notifier();
		}
	}



	/**
	 * copie la selection et met le texte copié dans le presse papier
	 * puis notifie l'IHM
	 */
	@Override
	public void copier() {
		//on copie le texte si il y a du texte selectionné
		if (selection.getDebut() != selection.getFin()) {
			presse_papier.setPressePapier(buffer.getText().substring(selection.getDebut(), selection.getFin()));
			notifier();
		}
	}



	/**
	 * colle (met) le contenu du presse papier dans le buffer à la position courante du curseur
	 * et notifie l'IHM
	 */
	@Override
	public void coller() {
		if (presse_papier.getPressePapier() != "") {
			buffer.getStringBuffer().delete(selection.getDebut(), selection.getFin());
			inserer(presse_papier.getPressePapier());
		}
		notifierEnregistrer();
		notifier();
	}

	/**
	 * verifie le caractère tapé et le met dans le buffer à la position du curseur,
	 * puis notifie l'IHM
	 * @param character : le caractère tapé
	 * @param codeKey : le code du caractère tapé
	 */
	@Override
	public void taper(char character, int codeKey) {
		switch (codeKey) {


			case KeyEvent.VK_BACKSPACE : //touche backspace

				if(selection.getDebut()==selection.getFin())  //si rien n'est selectionné
				{
					if(selection.getDebut()>0) //le curseur n'est pas au debut du texte
					{
						buffer.getStringBuffer().delete(selection.getDebut()-1, selection.getDebut());
						selection.mouvedCursor(-1);
					}
				}
				else
				{
					buffer.getStringBuffer().delete(selection.getDebut(), selection.getFin());
					selection.setFin(selection.getDebut());
				}
				notifierEnregistrer();
				notifier();
				break;

			case KeyEvent.VK_END :  //touche fin du texte
				selection.setDebut(buffer.getText().length());
				selection.setFin(buffer.getText().length());
				notifierEnregistrer();
				notifier();
				break;

			case KeyEvent.VK_LEFT :  //direction gauche
				if(selection.getDebut()>0) //le curseur n'est pas au debut du texte
				{
					selection.setDebut(selection.getDebut()-1);
					selection.setFin(selection.getDebut());
				}
				notifierEnregistrer();
				notifier();
				break;
			case KeyEvent.VK_UP:  //direction haut
				notifier();
				break;
			case KeyEvent.VK_RIGHT:  //direction droite
				if(selection.getDebut()<buffer.getText().length()) //le curseur n'est pas au debut du texte
				{
					selection.setDebut(selection.getDebut()+1);
					selection.setFin(selection.getDebut());
				}
				notifierEnregistrer();
				notifier();
				break;
			case KeyEvent.VK_DOWN :  //direction bas
				notifierEnregistrer();
				notifier();
				break;

			case Clavier.TOP_ARROW :
				notifierEnregistrer();
				notifier();
				break;

			case Clavier.BOTTOM_ARROW :
				notifierEnregistrer();
				notifier();
				break;

			case Clavier.TOP_LEFT_ARROW :
				notifierEnregistrer();
				notifier();
				break;

			case KeyEvent.VK_DELETE :  //bouton suppr
				if(selection.getDebut()==selection.getFin())  //si rien n'est selectionné
				{
					if(selection.getDebut()<buffer.getText().length()) //le curseur n'est pas à la fin du texte
					{
						buffer.getStringBuffer().delete(selection.getDebut(), selection.getDebut()+1);
					}
				}
				else
				{
					buffer.getStringBuffer().delete(selection.getDebut(), selection.getFin());
					selection.setFin(selection.getDebut());
				}
				notifierEnregistrer();
				notifier();
				break;

			default: inserer(character+"");
				break;
			}

	}

	/**
	 * Insère du texte dans le buffer à la position du curseur et notifie l'IHM,
	 * cette fonction est utilisé par la fonction taper
	 * @param texte : le texte à inserer
	 */
	@Override
	public void inserer(String texte) {

		buffer.getStringBuffer().insert(selection.getDebut(), texte);
		selection.mouvedCursor(texte.length());
		notifierEnregistrer();
		notifier();
	}





	/**
	 * Enregistre le contenu du buffer dans un fichier sur le disque à l'emplacement path
	 * @param path : l'emplacement du fichier à enregistré
	 */
	@Override
	public void save(String path) {

		if (path != "") {
			try {
				FileWriter fw = new FileWriter(new File(path));
				BufferedWriter fluxOut = new BufferedWriter(fw);
				fluxOut.write(buffer.getText());
				fluxOut.close();

			} catch (IOException e) {

				e.printStackTrace();
			}
		}

	}

	/**
	 * Lit le contenu d'un fichier stocké sur le disque dont le chémin est passé en paramètre
	 *  et stock dans le buffer puis notifie l'IHM
	 *  @param path : le chémin du fichier à lire
	 */
	@Override
	public void ouvrir(String path) {

		if (path != "") {
			try {
				FileReader fichier_logique = new FileReader(new File(path));
				BufferedReader fluxIn = new BufferedReader(fichier_logique);

				String ligne = fluxIn.readLine();
				String texte = "";
				while (ligne != null) {
					texte += ligne;
					ligne = fluxIn.readLine();
				}
				buffer.setText(texte);
				selection.setDebut(texte.length());
				selection.setFin(texte.length());

				fichier_logique.close();
				fluxIn.close();

				notifierEnregistrer();
				notifier();

			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}


	/**
	 * Selectionne le texte de la position debut à la position fin (exclue)
	 * @param debut : le debut de la selection
	 * @param fin : fin de la selection
	 */
	@Override
	public void selectionner(int debut, int fin) {

		selection.setDebut(debut);
		selection.setFin(fin);
		notifierEnregistrer();
		notifier();
	}

	/**
	 * selectionne tout le texte, cette fonction utilise la fonction selectionner
	 */
	@Override
	public void toutSelectionner() {

		selection.setDebut(0);
		selection.setFin(buffer.getText().length());
		notifierEnregistrer();
		notifier();
	}







	public PressePapier getPresse_papier() {
		return presse_papier;
	}



	public Selection getSelection() {
		return selection;
	}



	public Buffer getBuffer() {
		return buffer;
	}



	public EnregistreurImpl getEnregistreur() {
		return enregistreur;
	}



	/**
	 * Met le moteur à jour pendant l'execution de la fonction rejouer de l'enregistreur, puis notifie l'IHM
	 * @param debut_selection : position du curseur debut
	 * @param fin_selection : position du curseur fin
	 * @param presse_papier : le contenu du presse papier
	 * @param buffer : le contenu du buffer
	 */
	@Override
	public void update(int debut_selection, int fin_selection, String presse_papier, String buffer) {

		selection.setDebut(debut_selection);
		selection.setFin(fin_selection);
		this.presse_papier.setPressePapier(presse_papier);
		this.buffer.setText(buffer);
		notifyAllObserver(debut_selection, fin_selection, presse_papier, buffer);
	}

	/**
	 * Ajoute un observer dans la liste des observer
	 * @param obs : l'observer
	 */
	@Override
	public void addObserver(Observer obs) {

		this.listeObserveurDuMoteur.add(obs);
	}

	/**
	 * Supprime un observer de la liste des observer
	 * @param obs : l'observer
	 */
	@Override
	public void deleteObserver(Observer obs) {

		this.listeObserveurDuMoteur.remove(obs);
	}

	/**
	 *Notifie tous les observers
	 *@param debut_selection : position du curseur debut
	 *@param fin_selection : position du curseur fin
	 *@param presse_papier : le contenu du presse papier
	 *@param buffer : le contenu du buffer
	 */
	@Override
	public void notifyAllObserver(int debut_selection, int fin_selection, String presse_papier, String buffer) {

		for (Observer obs : this.listeObserveurDuMoteur) {
			obs.update(debut_selection, fin_selection, presse_papier, buffer);
		}
	}


	/**
	 * Notifie tous les observers,
	 * cette fonction utilise la fonction notifyAllObserver
	 */
	public void notifier()
	{
		notifyAllObserver(this.selection.getDebut(), this.selection.getFin(), this.presse_papier.getPressePapier(),
				this.buffer.getText());
	}



	/**
	 * Notifie l'enregistreur quand il y a une nouvelle donnée à enregistrer
	 */
	public void notifierEnregistrer(){
		enregistreur.enregistrerEtat(selection.getDebut(), selection.getFin(), presse_papier.getPressePapier(), buffer.getText());
	}


	/**
	 * Supprime tous les observers du moteur
	 */
	@Override
	public void deleteObserverAll() {
		this.listeObserveurDuMoteur = new ArrayList<Observer>();
	}
}
