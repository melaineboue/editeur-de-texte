package com.istic.m1.tp.editeur.enregistrable;

import javax.swing.JLabel;

public interface Enregistreur {
      public  void demarrer();
      public void arreter();
      public void rejouer(JLabel texte);
      public void undo();
      public void redo();
}
