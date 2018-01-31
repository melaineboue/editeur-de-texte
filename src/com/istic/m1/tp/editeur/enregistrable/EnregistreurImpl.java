package com.istic.m1.tp.editeur.enregistrable;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

import com.istic.m1.tp.editeur.caretaker.Caretaker;
import com.istic.m1.tp.editeur.invoker.MonIHM;
import com.istic.m1.tp.editeur.memento.ConcreteMemento;
import com.istic.m1.tp.editeur.memento.Memento;
import com.istic.m1.tp.editeur.observer.Observable;
import com.istic.m1.tp.editeur.observer.Observer;
import com.istic.m1.tp.editeur.receiver.MoteurEditionImpl;

public class EnregistreurImpl implements Enregistreur , Observable{
	//La classe qui enregistre

	private Caretaker gardien; //le caretaker
	private boolean enreg_activer =false;  //true : si l'enregistrement des commandes a demarré
	private Thread lecture;  //gestion de la lecture de la vidéo
	private MonIHM ihm; //on veut juste grisé les bouton Undo et Redo s'il n'y a plus de données à lire dans les piles


	//la liste des eventuelles observer car cette classe est observé par le moteur
	//à qui il fournit les données pendant la fonction rejouer
	private List<Observer> listeObserveurEnregistreur;

	/**
	 * Constructeur
	 */
	public EnregistreurImpl() {
		gardien=new Caretaker();
		listeObserveurEnregistreur = new ArrayList();
		lecture =new Thread();
	}




	public Caretaker getCareTaker() {
		return gardien;
	}




	public boolean isActiver() {
		return enreg_activer;
	}







	public void setIhm(MonIHM ihm) {
		this.ihm = ihm;
	}




	/**
	 * Demarre l'enregistrement des commandes
	 */
	@Override
	public void demarrer() {
		enreg_activer = true;
	}

	/**
	 *Arrete l'enregistrement des commandes
	 */
	@Override
	public void arreter() {
		enreg_activer = false;
		gardien.setPositionCurseur(0);
		lecture.stop();

	}

	/**
	 * Rejouer les commandes déjà enregistré
	 */
	@Override
	public void rejouer(JLabel texte) {

		//on a decidé de faire dans un processus pour permettre à l'utilisateur de faire d'autres actions
		// pendant la lecture comme pause
		lecture =new Thread()
		{
			public void run()
			{
				//on continue la lecture tant qu'on est pas à la fin la liste
				while(gardien.hasNextLecture()){
			    	ConcreteMemento m= (ConcreteMemento) gardien.donner();
			    	notifyAllObserver(m.getStart_cursor(), m.getEnd_cursor(),m.getPresse_papier(), m.getBuffer());
					try {
						Thread.sleep(600);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			     }
				texte.setText("");
			}
		};

		lecture.start(); // on demarre la lecture





	 }

	/**
	 * Crée un Memento qui contient les informations concernant la selection, le buffer et le presse papier,
	 * et il le passe au caretaker qui les garde
	 * @param debut_selection : debut de la selection
	 * @param fin_selection	: fin de la selection
	 * @param presse_papier : le contenu du presse papier
	 * @param buffer : le contenu du buffer
	 * @return un Memento qui contient les informations à enregistrer
	 */
	public Memento creerMemento(int debut_selection, int fin_selection, String presse_papier, String buffer){
		ConcreteMemento m= new ConcreteMemento(presse_papier, buffer, debut_selection, fin_selection);
		return m;

	}

	/**
	 * Met les informations dans un Memento et l'enregistre dans le caretaker
	 * @param debut_selection : debut de la selection
	 * @param fin_selection : fin de la selection
	 * @param presse_papier : contenu du presse papier
	 * @param buffer : contenu du buffer
	 */
	public void enregistrerEtat(int debut_selection, int fin_selection, String presse_papier, String buffer){
		Memento enveloppe=creerMemento(debut_selection, fin_selection, presse_papier, buffer);
		gardien.garderUndoRedo(enveloppe); //on garde dans undo

		if(enreg_activer)
		{
			gardien.garder(enveloppe);
		}

		boolean undo = gardien.hasPrevious();
		boolean redo = gardien.hasNext();
		ihm.activerDesactiverUndoRedo(undo, redo);


	}






	/**
	 * Prend l'état precedente dans le caretaker et met le moteur à jour
	 *
	 */
	@Override
	public void undo() {
		if(gardien.hasPrevious())
		{
			Memento enveloppe = gardien.donnerPrecedent();
			ConcreteMemento m= (ConcreteMemento) enveloppe;
			if(enreg_activer)
				gardien.garder(enveloppe);
			notifyAllObserver(m.getStart_cursor(), m.getEnd_cursor(),m.getPresse_papier(), m.getBuffer());
		}

		boolean undo = gardien.hasPrevious();
		boolean redo = gardien.hasNext();
		ihm.activerDesactiverUndoRedo(undo, redo);

	}

	/**
	 * Prend l'état suivant (redo) dans le caretaker et met le moteur à jour
	 *
	 */
	@Override
	public void redo() {
		if(gardien.hasNext())
		{
			Memento enveloppe = gardien.donnerSuivant();
			ConcreteMemento m= (ConcreteMemento) enveloppe;
			if(enreg_activer)
			{
				gardien.garder(enveloppe);
			}

	    	notifyAllObserver(m.getStart_cursor(), m.getEnd_cursor(),m.getPresse_papier(), m.getBuffer());
		}

		boolean undo = gardien.hasPrevious();
		boolean redo = gardien.hasNext();
		ihm.activerDesactiverUndoRedo(undo, redo);

	}






	//Enregistreur est observé par le moteur , donc observable

	public Caretaker getCarataker() {
		return gardien;
	}

	/**
	 *ajoute un observer à la liste d'observer
	 */
	@Override
	public void addObserver(Observer obs) {
		this.listeObserveurEnregistreur.add(obs);
	}

	/**
	 * supprime un observer de la liste d'observer
	 */
	@Override
	public void deleteObserver(Observer obs) {
		this.listeObserveurEnregistreur.remove(obs);
	}

	/**
	 * Notifie tous les observers
	 */
	@Override
	public void notifyAllObserver(int debut_selection, int fin_selection, String presse_papier, String buffer) {
		for(Observer obs : listeObserveurEnregistreur)
		{
			obs.update(debut_selection, fin_selection, presse_papier, buffer);
		}
	}

	/**
	 * supprime tous les observers de la liste
	 */
	@Override
	public void deleteObserverAll() {
		listeObserveurEnregistreur = new ArrayList();
	}

}
