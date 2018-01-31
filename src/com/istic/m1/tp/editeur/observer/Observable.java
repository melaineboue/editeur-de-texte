package com.istic.m1.tp.editeur.observer;

public interface Observable {

	public void addObserver(Observer obs);
	public void deleteObserver(Observer obs);
	public void notifyAllObserver(int debut_selection,int fin_selection, String presse_papier, String buffer);
	public void deleteObserverAll();
}
