package com.istic.m1.tp.editeur.receiver;

public interface MoteurEdition {

	public void selectionner(int debut,int fin);
	public void toutSelectionner();
	public void coller();
	public void copier();
	public void couper();
	public void taper(char character, int codeKey);
	public void inserer(String text);
	public void save(String path);
	public void ouvrir(String path);
}
