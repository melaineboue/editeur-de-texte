package com.istic.m1.tp.editeur.test;

import static org.junit.Assert.*;


import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;

import com.istic.m1.tp.editeur.commande.Command;
import com.istic.m1.tp.editeur.commandeConcrete.Inserer;
import com.istic.m1.tp.editeur.commandeConcrete.Ouvrir;
import com.istic.m1.tp.editeur.commandeConcrete.Save;
import com.istic.m1.tp.editeur.commandeConcrete.Selectionner;
import com.istic.m1.tp.editeur.commandeConcrete.Taper;
import com.istic.m1.tp.editeur.enregistrable.EnregistreurImpl;
import com.istic.m1.tp.editeur.invoker.MonIHM;
import com.istic.m1.tp.editeur.memento.ConcreteMemento;
import com.istic.m1.tp.editeur.receiver.MoteurEditionImpl;

public class TestVersion3 {

	//Test de toutes les fonctions de la version 1

	/**
	 * Test de la commande Inserer
	 */
	@Test
	public void testInserer()
	{
		//initiation des composants
		MonIHM fenetre=new MonIHM();
		EnregistreurImpl enregistreur = new EnregistreurImpl();
		enregistreur.setIhm(fenetre);
		MoteurEditionImpl moteur=new MoteurEditionImpl();
		moteur.addObserver(fenetre);
		fenetre.initCommand(moteur);// initialisation de mon moteur dans mes commandes concrètes

		enregistreur.addObserver(moteur);
		moteur.setEnregistreur(enregistreur);

		fenetre.initCommandEnregistreur(enregistreur);

		

		//on insère du texte dans le moteur
		Command inserer=fenetre.getInserer();
		Inserer temp = (Inserer)inserer;
		temp.setTexte("test");
		inserer =temp;
		inserer.execute();

		//on test si le test est présent dans le moteur et l'IHM
		//puis on verifie aussi la position du curseur
		assertTrue(fenetre.getZoneDeSaisie().getText().equals("test"));
		/*assertTrue(fenetre.getDebut_selection()==4);
		assertTrue(fenetre.getFin_selection()==4);

		assertTrue(moteur.getBuffer().getText().equals("test"));
		assertTrue(moteur.getPresse_papier().getPressePapier().equals(""));
		assertTrue(moteur.getSelection().getDebut()==4);
		assertTrue(moteur.getSelection().getFin()==4);
*/
	}


	/**
	 * Test de la commande Coupers
	 */
	@Test
	public void testCouper()
	{
		//initialisation des composants
		//initiation des composants
		MonIHM fenetre=new MonIHM();
		EnregistreurImpl enregistreur = new EnregistreurImpl();
		enregistreur.setIhm(fenetre);
		MoteurEditionImpl moteur=new MoteurEditionImpl();
		moteur.addObserver(fenetre);
		fenetre.initCommand(moteur);// initialisation de mon moteur dans mes commandes concrètes

		enregistreur.addObserver(moteur);
		moteur.setEnregistreur(enregistreur);

		fenetre.initCommandEnregistreur(enregistreur);

		//on insère du texte "test" dans le moteur
		Command inserer=fenetre.getInserer();
		Inserer temp = (Inserer)inserer;
		temp.setTexte("dessiner");
		inserer =temp;
		inserer.execute();

		//on selectionner de 1 à 4 et je coupe la partie selectionnée
		Command selectionner=fenetre.getSelectionner();
		Command couper = fenetre.getCouper();
		Selectionner temp2 = (Selectionner)selectionner;
		temp2.setDebut(1);
		temp2.setFin(4);
		selectionner =temp2;
		selectionner.execute();
		couper.execute();



		//On teste si le texte du buffer a bien été coupé c'est à dire qu'il reste le mot diner
		//et aussi on verifie si la partie coupée est bel et bien dans le presse papier
		assertTrue(fenetre.getZoneDeSaisie().getSelectionStart()==1);
		assertTrue(fenetre.getZoneDeSaisie().getSelectionEnd()==1);
		assertTrue(fenetre.getDebut_selection()==1);
		assertTrue(fenetre.getFin_selection()==1);

		assertTrue(moteur.getSelection().getDebut()==1);
		assertTrue(moteur.getSelection().getFin()==1);

		assertTrue(fenetre.getZoneDeSaisie().getText().equals("diner"));
		assertTrue(moteur.getBuffer().getText().equals("diner"));
		assertTrue(moteur.getPresse_papier().getPressePapier().equals("ess"));
	}


	/**
	 * Test la commande coller
	 */
	@Test
	public void testColler()
	{
		//initialisation des composants
		//initiation des composants
		MonIHM fenetre=new MonIHM();
		EnregistreurImpl enregistreur = new EnregistreurImpl();
		enregistreur.setIhm(fenetre);
		MoteurEditionImpl moteur=new MoteurEditionImpl();
		moteur.addObserver(fenetre);
		fenetre.initCommand(moteur);// initialisation de mon moteur dans mes commandes concrètes

		enregistreur.addObserver(moteur);
		moteur.setEnregistreur(enregistreur);

		fenetre.initCommandEnregistreur(enregistreur);

		//on insère du texte "test" dans le moteur
		Command inserer=fenetre.getInserer();
		Inserer temp = (Inserer)inserer;
		temp.setTexte("dessin");
		inserer =temp;
		inserer.execute();

		//on selectionner de 1 à 4 et je copie la partie selectionnée
		//puis je me deplace et je colle
		Command selectionner=fenetre.getSelectionner();
		Command copier = fenetre.getCopier();
		Command coller = fenetre.getColler();
		Selectionner temp2 = (Selectionner)selectionner;
		temp2.setDebut(1);
		temp2.setFin(4);
		selectionner =temp2;
		selectionner.execute();
		copier.execute();

		temp2.setDebut(5);
		temp2.setFin(5);
		selectionner =temp2;
		selectionner.execute();
		coller.execute();





		//On teste si le texte du presse papier a bien été collé à la position 5 c'est à dire qu'il y a
		//le mot dessiessn dans le buffer
		//et aussi on verifie si le le presse papier n'a pas changé
		assertTrue(fenetre.getZoneDeSaisie().getSelectionStart()==8);
		assertTrue(fenetre.getZoneDeSaisie().getSelectionEnd()==8);
		assertTrue(fenetre.getDebut_selection()==8);
		assertTrue(fenetre.getFin_selection()==8);

		assertTrue(moteur.getSelection().getDebut()==8);
		assertTrue(moteur.getSelection().getFin()==8);

		assertTrue(fenetre.getZoneDeSaisie().getText().equals("dessiessn"));
		assertTrue(moteur.getBuffer().getText().equals("dessiessn"));
		assertTrue(moteur.getPresse_papier().getPressePapier().equals("ess"));


	}

	/**
	 * Test de la commande Ouvrir
	 */
	@Test
	public void testOuvrir()
	{
		//initialisation des composants
		//initiation des composants
		MonIHM fenetre=new MonIHM();
		EnregistreurImpl enregistreur = new EnregistreurImpl();
		enregistreur.setIhm(fenetre);
		MoteurEditionImpl moteur=new MoteurEditionImpl();
		moteur.addObserver(fenetre);
		fenetre.initCommand(moteur);// initialisation de mon moteur dans mes commandes concrètes

		enregistreur.addObserver(moteur);
		moteur.setEnregistreur(enregistreur);

		fenetre.initCommandEnregistreur(enregistreur);

		//Je crée un fichier avec un contenu
		FileWriter fw;
		try {
			fw = new FileWriter(new File("testOuvrir.txt"));
			BufferedWriter fluxOut = new BufferedWriter(fw);
			fluxOut.write("bonjour un test");
			fluxOut.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}


		//on ouvre le fichier testOuvrir.txt et on met son contenu dans le moteur
		Command ouvrir=fenetre.getOuvrir();
		Ouvrir temp = (Ouvrir)ouvrir;
		temp.setPath("testOuvrir.txt");
		ouvrir =temp;
		ouvrir.execute();

		//on teste si le contenu du buffer du moteur et de l'IHM est bien le contenu du texte du fichier
		//et le curseur est au bon endroit
		assertTrue(fenetre.getZoneDeSaisie().getSelectionStart()==15);
		assertTrue(fenetre.getZoneDeSaisie().getSelectionEnd()==15);
		assertTrue(fenetre.getDebut_selection()==15);
		assertTrue(fenetre.getFin_selection()==15);

		assertTrue(moteur.getSelection().getDebut()==15);
		assertTrue(moteur.getSelection().getFin()==15);

		assertTrue(fenetre.getZoneDeSaisie().getText().equals("bonjour un test"));
		assertTrue(fenetre.getPresse_papier().equals(""));
		assertTrue(moteur.getBuffer().getText().equals("bonjour un test"));
		assertTrue(moteur.getPresse_papier().getPressePapier().equals(""));


		//on a enregistré le texte "contenu du fichier" dans le fichier test.txt
		//On teste si ce fichier existe et
		//que ce texte est son actuel contenu
		FileReader fichier_logique;
		String texte="";
		try {
			fichier_logique = new FileReader(new File("test.txt"));
			BufferedReader fluxIn = new BufferedReader(fichier_logique);

			String ligne = fluxIn.readLine();
			while (ligne != null) {
				texte += ligne;
				ligne = fluxIn.readLine();
			}


		} catch (Exception e) {
			e.printStackTrace();
		}

		assertTrue(texte.equals("contenu du fichier"));
	}

	/**
	 * Test de la commande Save
	 */
	@Test
	public void testSave()
	{
		//initialisation des composants
		//initiation des composants
		MonIHM fenetre=new MonIHM();
		EnregistreurImpl enregistreur = new EnregistreurImpl();
		enregistreur.setIhm(fenetre);
		MoteurEditionImpl moteur=new MoteurEditionImpl();
		moteur.addObserver(fenetre);
		fenetre.initCommand(moteur);// initialisation de mon moteur dans mes commandes concrètes

		enregistreur.addObserver(moteur);
		moteur.setEnregistreur(enregistreur);

		fenetre.initCommandEnregistreur(enregistreur);

		//on insère du texte "test" dans le moteur
		Command inserer=fenetre.getInserer();
		Inserer temp = (Inserer)inserer;
		temp.setTexte("contenu du fichier");
		inserer =temp;
		inserer.execute();

		//on enregistre le contenu du buffer dans un fichier
		Command save = fenetre.getSave();
		Save temp2 = (Save)save;
		temp2.setPath("test.txt");
		save = temp2;
		save.execute();


		//on a enregistré le texte "contenu du fichier" dans le fichier test.txt
		//On teste si ce fichier existe et
		//que ce texte est son actuel contenu
		FileReader fichier_logique;
		String texte="";
		try {
			fichier_logique = new FileReader(new File("test.txt"));
			BufferedReader fluxIn = new BufferedReader(fichier_logique);

			String ligne = fluxIn.readLine();
			while (ligne != null) {
				texte += ligne;
				ligne = fluxIn.readLine();
			}


		} catch (Exception e) {
			e.printStackTrace();
		}

		assertTrue(texte.equals("contenu du fichier"));

	}

	/**
	 * Test de la commande Taper
	 */
	@Test
	public void testTaper()
	{
		//initialisation des composants
		//initiation des composants
		MonIHM fenetre=new MonIHM();
		EnregistreurImpl enregistreur = new EnregistreurImpl();
		enregistreur.setIhm(fenetre);
		MoteurEditionImpl moteur=new MoteurEditionImpl();
		moteur.addObserver(fenetre);
		fenetre.initCommand(moteur);// initialisation de mon moteur dans mes commandes concrètes

		enregistreur.addObserver(moteur);
		moteur.setEnregistreur(enregistreur);

		fenetre.initCommandEnregistreur(enregistreur);

		//on insère du texte "test" dans le moteur
		Command taper=fenetre.getTaper();
		Taper temp = (Taper)taper;
		temp.setCharacterTyped('s');
		temp.setCodeKey(83);
		taper =temp;
		taper.execute();

		temp.setCharacterTyped('a');
		temp.setCodeKey(65);
		taper =temp;
		taper.execute();


		//on a tapé le texte "sa"
		//On teste si le texte de la zone de saisie de l'IHM et du moteur ont vraiment le texte "sa"
		//et que le curseur est placé àla position 2
		assertTrue(fenetre.getZoneDeSaisie().getSelectionStart()==2);
		assertTrue(fenetre.getZoneDeSaisie().getSelectionEnd()==2);
		assertTrue(fenetre.getDebut_selection()==2);
		assertTrue(fenetre.getFin_selection()==2);

		assertTrue(moteur.getSelection().getDebut()==2);
		assertTrue(moteur.getSelection().getFin()==2);

		assertTrue(fenetre.getZoneDeSaisie().getText().equals("sa"));
		assertTrue(fenetre.getPresse_papier().equals(""));
		assertTrue(moteur.getBuffer().getText().equals("sa"));
		assertTrue(moteur.getPresse_papier().getPressePapier().equals(""));
	}


	/**
	 * Test de la commande selectionnerTout
	 */
	@Test
	public void testToutSelectionner()
	{
		//cette commande utilise la commande couper
		//initialisation des composants
		//initiation des composants
		MonIHM fenetre=new MonIHM();
		EnregistreurImpl enregistreur = new EnregistreurImpl();
		enregistreur.setIhm(fenetre);
		MoteurEditionImpl moteur=new MoteurEditionImpl();
		moteur.addObserver(fenetre);
		fenetre.initCommand(moteur);// initialisation de mon moteur dans mes commandes concrètes

		enregistreur.addObserver(moteur);
		moteur.setEnregistreur(enregistreur);

		fenetre.initCommandEnregistreur(enregistreur);

		//on insère du texte "test" dans le moteur
		Command inserer=fenetre.getInserer();
		Inserer temp = (Inserer)inserer;
		temp.setTexte("saisir");
		inserer =temp;
		inserer.execute();

		//on selectionner tout
		moteur.toutSelectionner();
		//On teste si la selection de la zone de saisie de l'IHM et du moteur ont vraiment selectionné de 0 à 2
		assertTrue(fenetre.getZoneDeSaisie().getSelectionStart()==0);
		assertTrue(fenetre.getZoneDeSaisie().getSelectionEnd()==6);
		assertTrue(fenetre.getDebut_selection()==0);
		assertTrue(fenetre.getFin_selection()==6);

		assertTrue(moteur.getSelection().getDebut()==0);
		assertTrue(moteur.getSelection().getFin()==6);
	}


	/**
	 * test de la fonction selectionner
	 */
	@Test
	public void testSelectionner()
	{
		//initialisation des composants
		//initiation des composants
		MonIHM fenetre=new MonIHM();
		EnregistreurImpl enregistreur = new EnregistreurImpl();
		enregistreur.setIhm(fenetre);
		MoteurEditionImpl moteur=new MoteurEditionImpl();
		moteur.addObserver(fenetre);
		fenetre.initCommand(moteur);// initialisation de mon moteur dans mes commandes concrètes

		enregistreur.addObserver(moteur);
		moteur.setEnregistreur(enregistreur);

		fenetre.initCommandEnregistreur(enregistreur);

		//on insère du texte "test" dans le moteur
		Command inserer=fenetre.getInserer();
		Inserer temp = (Inserer)inserer;
		temp.setTexte("arts");
		inserer =temp;
		inserer.execute();

		//on selectionner de 0 à 2
		Command selectionner=fenetre.getSelectionner();
		Selectionner temp2 = (Selectionner)selectionner;
		temp2.setDebut(0);
		temp2.setFin(2);
		selectionner =temp2;
		selectionner.execute();

		//On teste si la selection de la zone de saisie de l'IHM et du moteur ont vraiment selectionné de 0 à 2
		assertTrue(fenetre.getZoneDeSaisie().getSelectionStart()==0);
		assertTrue(fenetre.getZoneDeSaisie().getSelectionEnd()==2);
		assertTrue(fenetre.getDebut_selection()==0);
		assertTrue(fenetre.getFin_selection()==2);

		assertTrue(moteur.getSelection().getDebut()==0);
		assertTrue(moteur.getSelection().getFin()==2);
	}


	/**
	 * test de la commande demarrer
	 */
	@Test
	public void testDemarrer()
	{
		//initialisation des composants
		//initiation des composants
		MonIHM fenetre=new MonIHM();
		EnregistreurImpl enregistreur = new EnregistreurImpl();
		enregistreur.setIhm(fenetre);
		MoteurEditionImpl moteur=new MoteurEditionImpl();
		moteur.addObserver(fenetre);
		fenetre.initCommand(moteur);// initialisation de mon moteur dans mes commandes concrètes

		enregistreur.addObserver(moteur);
		moteur.setEnregistreur(enregistreur);

		fenetre.initCommandEnregistreur(enregistreur);




		//on insère du texte "test" dans le moteur
		Command inserer=fenetre.getInserer();
		Command demarrer=fenetre.getDemarrer();

		assertTrue(enregistreur.getCareTaker().getMementos().size()==0);
		assertFalse(enregistreur.isActiver());
		demarrer.execute();
		assertTrue(enregistreur.isActiver());

		Inserer temp = (Inserer)inserer;
		temp.setTexte("arts");
		inserer =temp;
		inserer.execute();

		assertTrue(enregistreur.getCareTaker().getMementos().size()==1);
		ConcreteMemento message = (ConcreteMemento)enregistreur.getCareTaker().donner();
		assertTrue(message.getBuffer().equals("arts"));


	}




	/**
	 * test de la commande demarrer
	 */
	@Test
	public void testArreter()
	{
		//initialisation des composants
		//initiation des composants
		MonIHM fenetre=new MonIHM();
		EnregistreurImpl enregistreur = new EnregistreurImpl();
		enregistreur.setIhm(fenetre);
		MoteurEditionImpl moteur=new MoteurEditionImpl();
		moteur.addObserver(fenetre);
		fenetre.initCommand(moteur);// initialisation de mon moteur dans mes commandes concrètes

		enregistreur.addObserver(moteur);
		moteur.setEnregistreur(enregistreur);

		fenetre.initCommandEnregistreur(enregistreur);

		//on insère du texte "test" dans le moteur
		Command inserer=fenetre.getInserer();
		Command demarrer=fenetre.getDemarrer();
		Command arreter=fenetre.getArreter();


		//on verifie si la taille de la liste est 0 au debut
		//et si la variable associé à l'enregistrement est false
		assertTrue(enregistreur.getCareTaker().getMementos().size()==0);
		assertFalse(enregistreur.isActiver());
		demarrer.execute();
		assertTrue(enregistreur.isActiver());

		//on insère du texte dans le careTaker
		Inserer temp = (Inserer)inserer;
		temp.setTexte("arts");
		inserer =temp;
		inserer.execute();

		//on verifie si le texte est bien rentré dans le cartaker
		assertTrue(enregistreur.getCareTaker().getMementos().size()==1);
		arreter.execute(); //on arrête maintenant l'enregistrement
		assertFalse(enregistreur.isActiver()); //on teste si c'est vraiment arrêté

		//on envoi encore du texte
		temp.setTexte("arts");
		inserer =temp;
		inserer.execute();

		//on verifie si c'est pas rentré dans le careTaker, car on a desactivé l'enregistrement
		//la longueur de la liste du careTaker doit être toujours 1
		assertTrue(enregistreur.getCareTaker().getMementos().size()==1);


	}



	@Test
	public void testRejouer()
	{

		//initialisation des composants
		//initiation des composants
		MonIHM fenetre=new MonIHM();
		EnregistreurImpl enregistreur = new EnregistreurImpl();
		enregistreur.setIhm(fenetre);
		MoteurEditionImpl moteur=new MoteurEditionImpl();
		moteur.addObserver(fenetre);
		fenetre.initCommand(moteur);// initialisation de mon moteur dans mes commandes concrètes

		enregistreur.addObserver(moteur);
		moteur.setEnregistreur(enregistreur);

		fenetre.initCommandEnregistreur(enregistreur);

		//on insère du texte "test" dans le moteur
		Command inserer=fenetre.getInserer();
		Command demarrer=fenetre.getDemarrer();
		Command arreter=fenetre.getArreter();
		Command rejouer = fenetre.getRejouer();
		demarrer.execute();


		//on insère du texte dans le careTaker
		Inserer temp = (Inserer)inserer;
		temp.setTexte("deux");
		inserer =temp;
		inserer.execute();

		rejouer.execute();

		//on teste si après la fonction rejouer, le texte d'avant est toujours la
		assertTrue(fenetre.getZoneDeSaisie().getText().equals("deux"));
		assertTrue(moteur.getBuffer().getText().equals("deux"));
	}



	@Test
	public void testUndo()
	{

		//initialisation des composants
		//initiation des composants
		MonIHM fenetre=new MonIHM();
		EnregistreurImpl enregistreur = new EnregistreurImpl();
		enregistreur.setIhm(fenetre);
		MoteurEditionImpl moteur=new MoteurEditionImpl();
		moteur.addObserver(fenetre);
		fenetre.initCommand(moteur);// initialisation de mon moteur dans mes commandes concrètes

		enregistreur.addObserver(moteur);
		moteur.setEnregistreur(enregistreur);

		fenetre.initCommandEnregistreur(enregistreur);

		//on insère du texte "test" dans le moteur et le careTaker l'enregistre
		Command inserer=fenetre.getInserer();
		Command undo=fenetre.getUndo();


		Inserer temp = (Inserer)inserer;
		temp.setTexte("u");
		inserer =temp;
		inserer.execute();

		temp.setTexte("n");
		inserer =temp;
		inserer.execute();

		temp.setTexte("i");
		inserer =temp;
		inserer.execute();


		undo.execute();

		//on teste si après la fonction undo, le texte d'avant revient
		assertTrue(fenetre.getZoneDeSaisie().getText().equals("un"));
		assertTrue(moteur.getBuffer().getText().equals("un"));

		undo.execute();
		assertTrue(fenetre.getZoneDeSaisie().getText().equals("u"));
		assertTrue(moteur.getBuffer().getText().equals("u"));


	}


	@Test
	public void testRedo()
	{
		//initialisation des composants
		//initiation des composants
		MonIHM fenetre=new MonIHM();
		EnregistreurImpl enregistreur = new EnregistreurImpl();
		enregistreur.setIhm(fenetre);
		MoteurEditionImpl moteur=new MoteurEditionImpl();
		moteur.addObserver(fenetre);
		fenetre.initCommand(moteur);// initialisation de mon moteur dans mes commandes concrètes

		enregistreur.addObserver(moteur);
		moteur.setEnregistreur(enregistreur);

		fenetre.initCommandEnregistreur(enregistreur);

		//on insère du texte "test" dans le moteur et le careTaker l'enregistre
		Command inserer=fenetre.getInserer();
		Command undo=fenetre.getUndo();
		Command redo=fenetre.getRedo();


		Inserer temp = (Inserer)inserer;
		temp.setTexte("u");
		inserer =temp;
		inserer.execute();

		temp.setTexte("n");
		inserer =temp;
		inserer.execute();

		temp.setTexte("i");
		inserer =temp;
		inserer.execute();


		undo.execute();
		undo.execute();
		assertTrue(fenetre.getZoneDeSaisie().getText().equals("u"));
		assertTrue(moteur.getBuffer().getText().equals("u"));



		//appel de la commande redo
		//puis on texte si le texte qu'on avait avant de faire undo revient
		redo.execute();
		assertTrue(fenetre.getZoneDeSaisie().getText().equals("un"));
		assertTrue(moteur.getBuffer().getText().equals("un"));

		redo.execute();
		assertTrue(fenetre.getZoneDeSaisie().getText().equals("uni"));
		assertTrue(moteur.getBuffer().getText().equals("uni"));

	}




}
