package com.istic.m1.tp.editeur.commandeConcrete;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.istic.m1.tp.editeur.commande.Command;
import com.istic.m1.tp.editeur.receiver.MoteurEdition;

public class Taper implements Command{

	char characterTyped; //le caractère correspondant à la touche frappée
	int codeKey;   //le code clavier de la touche frappée
	MoteurEdition moteur;
	List<Integer> touchesDisabled;  //liste des touches claviers à desactiver







	public Taper(MoteurEdition moteur) {
		this.characterTyped = ' ';
		this.codeKey = 0;
		this.moteur=moteur; //la liaison entre la commande et l'enregistreur

		//enregistrement du code des touches à désactiver dans un tableau
		int codes_disabled[]={
				KeyEvent.VK_SHIFT,
				KeyEvent.VK_CONTROL,
				KeyEvent.VK_ALT,
				KeyEvent.VK_ALT_GRAPH,
				KeyEvent.VK_CAPS_LOCK,
				KeyEvent.VK_ESCAPE,
				KeyEvent.VK_F2,
				KeyEvent.VK_F3,
				KeyEvent.VK_F4,
				KeyEvent.VK_F5,
				KeyEvent.VK_F6,
				KeyEvent.VK_F7,
				KeyEvent.VK_F8,
				KeyEvent.VK_F9,
				KeyEvent.VK_F10,
				KeyEvent.VK_F11,
				KeyEvent.VK_F12,
				KeyEvent.VK_F11,
				KeyEvent.VK_PRINTSCREEN,
				KeyEvent.VK_INSERT,
				KeyEvent.VK_NUM_LOCK,
				KeyEvent.VK_WINDOWS
		};


		touchesDisabled=new ArrayList();

		//ajout des codes dans une liste pour pouvoir utiliser la fonction contains
		for(int code:codes_disabled)
		{
			touchesDisabled.add(code);
		}


	}

	/**
	 * setter de l'attribut characterType
	 * @param characterTyped : le caractère frappé au clavier
	 */
	public void setCharacterTyped(char characterTyped) {
		this.characterTyped = characterTyped;
	}

	/**
	 * setter de l'attribut codeKey
	 * @param codeKey : code du caratère frappé
	 */
	public void setCodeKey(int codeKey) {
		this.codeKey = codeKey;
	}

	/**
	 * Execute la commande taper
	 */
	@Override
	public void execute() {
		//Si la touche appuyé n'est pas parmi les interdits
		if(!touchesDisabled.contains(codeKey))
		{
			moteur.taper(characterTyped, codeKey);
		}
	}


}
