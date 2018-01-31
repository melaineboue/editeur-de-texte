package com.istic.m1.tp.editeur.invoker;

import java.awt.BorderLayout;


import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.awt.Component;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import com.istic.m1.tp.editeur.commande.Command;
import com.istic.m1.tp.editeur.commandeConcrete.Arreter;
import com.istic.m1.tp.editeur.commandeConcrete.Coller;
import com.istic.m1.tp.editeur.commandeConcrete.Copier;
import com.istic.m1.tp.editeur.commandeConcrete.Couper;
import com.istic.m1.tp.editeur.commandeConcrete.Demarrer;
import com.istic.m1.tp.editeur.commandeConcrete.Inserer;
import com.istic.m1.tp.editeur.commandeConcrete.Ouvrir;
import com.istic.m1.tp.editeur.commandeConcrete.Redo;
import com.istic.m1.tp.editeur.commandeConcrete.Rejouer;
import com.istic.m1.tp.editeur.commandeConcrete.Save;
import com.istic.m1.tp.editeur.commandeConcrete.Selectionner;
import com.istic.m1.tp.editeur.commandeConcrete.Taper;
import com.istic.m1.tp.editeur.commandeConcrete.Undo;
import com.istic.m1.tp.editeur.enregistrable.EnregistreurImpl;
import com.istic.m1.tp.editeur.observer.Observable;
import com.istic.m1.tp.editeur.observer.Observer;
import com.istic.m1.tp.editeur.receiver.MoteurEdition;

public class MonIHM extends JFrame implements Observer, ActionListener, KeyListener, MouseListener, MouseMotionListener{

	private static final String EDITOR_NAME="Mini Editeur v3";
	private static final int IHM_HEIGHT=600;
	private static final int IHM_WIDTH=800;

	private int tailleTexte;
	private JLabel label_taille_du_texte;
	private boolean publique=false;
	private JFrame fenetre_actuel;



	//Liste des observateurs
	private List<Observer> listeObservateur;
	private Command copier,couper,coller,inserer,taper,selectionner,save,ouvrir, demarrer, arreter, rejouer, undo, redo;  //les objets command qui vont executer les commandes de l'IHM

	private int debut_selection, fin_selection;
	private String presse_papier;

	//String buffer;

	//La barre de menu
	private JMenuBar barre_de_menu;

	//Les menus
	private JMenu menuFichier;
	private JMenu menuEdition;

	//Les JPanel pour gerer la mis en page de l'interface
	private JPanel panneauPrincipal;


	//Les JMenuItem pour les bouton
	private JMenuItem menuCopier;
	private JMenuItem menuCouper;
	private JMenuItem menuColler;
	private JMenuItem menuDemarrer;
	private JMenuItem menuArreter;
	private JMenuItem menuRejouer;
	private JMenuItem menuRedo;
	private JMenuItem menuUndo;
	private JMenuItem menuSelectionnerTout;
	private JMenuItem menuFermer;
	private JMenuItem menuSave;
	private JMenuItem menuOuvrir;

	//La barre d'outil
	private JToolBar barreOutils;
	private JButton boutonOutilSave;
	private JButton boutonOutilOuvrir;
	private JButton boutonOutilCouper;
	private JButton boutonOutilCopier;
	private JButton boutonOutilColler;
	private JButton boutonOutilDemarrer;
	private JButton boutonOutilArreter;
	private JButton boutonOutilRejouer;
	private JButton boutonOutilUndo;
	private JButton boutonOutilRedo;
	private JButton boutonOutilZoomOut;
	private JButton boutonOutilZoomIn;
	private JButton boutonOutilHelp;
	private JLabel texte;
	private Font police;






	/***********FIN MENU**************/

	//Les composants de la fenêtres
	private JTextArea zoneDeSaisie;
	private JTextArea zoneDeNotification;



	/*
	private JMenuItem menuCopier;*/






	public MonIHM()
	{

		this.setSize(IHM_WIDTH, IHM_HEIGHT);  //definit la taille de la fenetre
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);  //Pour fermer la fenetre quand on clique sur la croix rouge (bouton fermer) de la fenetre
		this.setTitle(EDITOR_NAME);  //defini le titre de l'editeur
		this.setLocationRelativeTo(null);


		//initialisation des composants de l'interface
		this.initialize();
		this.ajouterEvenement();

		zoneDeSaisie.setFont(police);

		menuFichier.setMnemonic('F');
		menuEdition.setMnemonic('E');
		menuCopier.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,KeyEvent.CTRL_MASK));
		menuCouper.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,KeyEvent.CTRL_MASK));
		menuColler.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,KeyEvent.CTRL_MASK));
		menuSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,KeyEvent.CTRL_MASK));
		menuOuvrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,KeyEvent.CTRL_MASK));
		menuSelectionnerTout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,KeyEvent.CTRL_MASK));

		//placement des composants sur le menu Fichier
		menuFichier.add(menuSave);
		menuFichier.add(menuOuvrir);
		menuFichier.add(menuFermer);


		//placement des composants sur le menu Edition
		menuEdition.add(menuCopier);
		menuEdition.add(menuCouper);
		menuEdition.add(menuColler);
		menuEdition.add(menuDemarrer);
		menuEdition.add(menuArreter);
		menuEdition.add(menuUndo);
		menuEdition.add(menuRedo);
		menuEdition.add(menuSelectionnerTout);

		//placement du Menu
		barre_de_menu.add(menuFichier);
		barre_de_menu.add(menuEdition);
		this.setJMenuBar(barre_de_menu);

		//Barre d'outil
		barreOutils=new JToolBar(SwingConstants.HORIZONTAL);
		barreOutils.setFloatable(false);
		barreOutils.setRollover(true);

		boutonOutilCopier.setToolTipText("Copier le texte");
		boutonOutilCouper.setToolTipText("Couper le texte");
		boutonOutilColler.setToolTipText("Coller le texte");
		boutonOutilDemarrer.setToolTipText("Enregistrer les commandes");
		boutonOutilArreter.setToolTipText("Arreter l'enregistrement");
		boutonOutilArreter.setToolTipText("Rejouer l'enregistrement");
		boutonOutilRedo.setToolTipText("Retablir");
		boutonOutilUndo.setToolTipText("Annuler");
		boutonOutilSave.setToolTipText("Enregistrer dans un fichier");
		boutonOutilOuvrir.setToolTipText("Ouvrir un fichier");
		boutonOutilZoomOut.setToolTipText("Dezoommer ");
		boutonOutilZoomIn.setToolTipText("Zoommer");
		boutonOutilHelp.setToolTipText("A propos");

		barreOutils.add(boutonOutilSave);
		barreOutils.add(boutonOutilOuvrir);
		barreOutils.add(boutonOutilCouper);
		barreOutils.add(boutonOutilCopier);
		barreOutils.add(boutonOutilColler);
		barreOutils.add(boutonOutilDemarrer);
		barreOutils.add(boutonOutilArreter);
		barreOutils.add(boutonOutilRejouer);
		barreOutils.add(boutonOutilUndo);
		barreOutils.add(boutonOutilRedo);
		barreOutils.add(new JToolBar.Separator());
		barreOutils.add(new JToolBar.Separator());
		barreOutils.add(boutonOutilZoomOut);
		barreOutils.add(label_taille_du_texte);
		barreOutils.add(boutonOutilZoomIn);
		barreOutils.add(boutonOutilHelp);
		barreOutils.add(texte);



		//Ajout au panneau
		panneauPrincipal.setLayout(new BorderLayout());
		this.zoneDeSaisie.setLineWrap(true);  //Aller automatiquement à la ligne
		this.zoneDeNotification.setLineWrap(true);
		this.zoneDeNotification.setEditable(false);
		this.zoneDeNotification.setBackground(Color.LIGHT_GRAY);

		if(!publique)panneauPrincipal.add(barreOutils,BorderLayout.NORTH);
		panneauPrincipal.add(new JScrollPane(zoneDeSaisie),BorderLayout.CENTER);
		panneauPrincipal.add(new JScrollPane(zoneDeNotification),BorderLayout.SOUTH);


		this.setContentPane(panneauPrincipal);
		this.fenetre_actuel=this;

		this.update(0, 0, "", "");


	}





	public void lancer()
	{
		this.setVisible(true);

	}


	/**
	 * Initialise toutes les commandes concrète et les lie avec le moteur
	 * @param moteur : le moteur de l'application
	 */
	public void initCommand(MoteurEdition moteur)
	{
		copier = new Copier(moteur);
		couper = new Couper(moteur);
		coller = new Coller(moteur);
		inserer = new Inserer(moteur);
		taper=new Taper(moteur);
		selectionner = new Selectionner(moteur);
		save = new Save(moteur);
		ouvrir=new Ouvrir(moteur);

	}
	public void initCommandEnregistreur(EnregistreurImpl enregistreur){
		demarrer = new Demarrer(enregistreur);
		arreter = new Arreter(enregistreur);
		rejouer = new Rejouer(enregistreur);
		undo = new Undo(enregistreur);
		redo = new Redo(enregistreur);

	}


	/**
	 * Initialise tous les composants présent sur l'IHM (Ménu et boutons ...)
	 */
	public void initialize()
	{
		listeObservateur=new ArrayList<Observer>();
		debut_selection=0;
		fin_selection=0;
		presse_papier="";
		tailleTexte=25;
		label_taille_du_texte=new JLabel(tailleTexte+"");

		barre_de_menu=new JMenuBar();
		menuFichier=new JMenu("Fichier");
		menuEdition=new JMenu("Edition");
		panneauPrincipal=new JPanel();

		menuCopier=new JMenuItem("Copier");
		menuCouper=new JMenuItem("Couper");
		menuColler=new JMenuItem("Coller");
		menuDemarrer=new JMenuItem("Demarrer enregistreur");
		menuArreter = new JMenuItem("Arreter ");
		menuArreter.setEnabled(false);
		menuRejouer = new JMenuItem("Rejouer");
		menuRejouer.setEnabled(false);
		menuRedo= new JMenuItem("Retablir ");
		menuUndo= new JMenuItem("Annuler ");
		menuFermer=new JMenuItem("Fermer");
		menuSelectionnerTout=new JMenuItem(" Tout selectionner");
		menuSave=new JMenuItem("Enregistrer");
		menuOuvrir=new JMenuItem("Ouvrir");
		police=new Font("Arial", Font.PLAIN, tailleTexte);


		InputStream inCopy = this.getClass().getResourceAsStream("/icones/copy.png");
		InputStream inCut = this.getClass().getResourceAsStream("/icones/cut.png");
		InputStream inPaste = this.getClass().getResourceAsStream("/icones/paste.png");
		InputStream inSave = this.getClass().getResourceAsStream("/icones/save.png");
		InputStream inOpen = this.getClass().getResourceAsStream("/icones/open.png");
		InputStream inZoomIn = this.getClass().getResourceAsStream("/icones/zoom-in.png");
		InputStream inZoomOut = this.getClass().getResourceAsStream("/icones/zoom-out.png");
		InputStream inHelp = this.getClass().getResourceAsStream("/icones/help.png");
		InputStream inRecord = this.getClass().getResourceAsStream("/icones/record.png");
		InputStream inPlay = this.getClass().getResourceAsStream("/icones/play.png");
		InputStream inStop = this.getClass().getResourceAsStream("/icones/stop.png");
		InputStream inUndo = this.getClass().getResourceAsStream("/icones/undo.png");
		InputStream inRedo = this.getClass().getResourceAsStream("/icones/redo.png");
		Image image1=null;
		Image image2=null;
		Image image3=null;
		Image image4=null;
		Image image5=null;
		Image image6=null;
		Image image7=null;
		Image image8=null;
		Image image9=null;
		Image image10=null;
		Image image11=null;
		Image image12=null;
		Image image13=null;
		try {
			image1 = ImageIO.read(inCopy);
			image2 = ImageIO.read(inCut);
			image3 = ImageIO.read(inPaste);
			image4 = ImageIO.read(inSave);
			image5 = ImageIO.read(inOpen);
			image6 = ImageIO.read(inZoomIn);
			image7 = ImageIO.read(inZoomOut);
			image8 = ImageIO.read(inHelp);
			image9 = ImageIO.read(inRecord);
			image10 = ImageIO.read(inPlay);
			image11 = ImageIO.read(inStop);
			image12 = ImageIO.read(inUndo);
			image13 = ImageIO.read(inRedo);

		} catch (IOException e) {
			e.printStackTrace();
		}

		//ImageIcon imm=new ImageIcon(image);

		boutonOutilCopier=new JButton(new ImageIcon(image1));
		boutonOutilCouper=new JButton(new ImageIcon(image2));
		boutonOutilColler=new JButton(new ImageIcon(image3));
		boutonOutilSave=new JButton(new ImageIcon(image4));
		boutonOutilOuvrir=new JButton(new ImageIcon(image5));
		boutonOutilZoomIn=new JButton(new ImageIcon(image6));
		boutonOutilZoomOut=new JButton(new ImageIcon(image7));
		boutonOutilHelp=new JButton(new ImageIcon(image8));
		boutonOutilDemarrer=new JButton(new ImageIcon(image9));
		boutonOutilRejouer=new JButton(new ImageIcon(image10));
		boutonOutilArreter=new JButton(new ImageIcon(image11));
		boutonOutilUndo=new JButton(new ImageIcon(image12));
		boutonOutilRedo=new JButton(new ImageIcon(image13));

		boutonOutilRejouer.setEnabled(false);
		boutonOutilArreter.setEnabled(false);

		/*boutonOutilCopier=new JButton(new ImageIcon("icones\\copy.png"));
		boutonOutilCouper=new JButton(new ImageIcon("icones\\cut.png"));
		boutonOutilColler=new JButton(new ImageIcon("icones\\paste.png"));
		boutonOutilDemarrer=new JButton(new ImageIcon("icones\\record.png"));
		boutonOutilArreter= new JButton(new ImageIcon("icones\\stop.png"));
		boutonOutilRejouer= new JButton(new ImageIcon("icones\\play.png"));
		boutonOutilRedo= new JButton(new ImageIcon("icones\\redo.png"));
		boutonOutilUndo= new JButton(new ImageIcon("icones\\undo.png"));
		boutonOutilSave=new JButton(new ImageIcon("icones\\save.png"));
		boutonOutilOuvrir=new JButton(new ImageIcon("C:icones\\open.png"));
		boutonOutilZoomOut=new JButton(new ImageIcon("icones\\zoom-out.png"));
		boutonOutilZoomIn=new JButton(new ImageIcon("icones\\zoom-in.png"));
		boutonOutilHelp=new JButton(new ImageIcon("icones\\help.png"));*/
		texte= new JLabel("");
		texte.setForeground(Color.red);


		zoneDeSaisie = new JTextArea("")
        {

            @Override
            public void cut() {
            	couper.execute();
            }
            @Override
            public void copy() {
            	copier.execute();
            }
            @Override
            public void paste() {
            	coller.execute();
            }


        }  ;

		zoneDeNotification=new JTextArea("",15,1);



	}









	public Command getSelectionner() {
		return selectionner;
	}

	public Command getRejouer() {
		return rejouer;
	}







	public String getPresse_papier() {
		return presse_papier;
	}





	public JFrame getFenetre_actuel() {
		return fenetre_actuel;
	}





	public Command getCopier() {
		return copier;
	}





	public Command getCouper() {
		return couper;
	}





	public Command getColler() {
		return coller;
	}





	public Command getInserer() {
		return inserer;
	}





	public Command getTaper() {
		return taper;
	}





	public Command getSave() {
		return save;
	}





	public Command getOuvrir() {
		return ouvrir;
	}





	public Command getDemarrer() {
		return demarrer;
	}





	public Command getArreter() {
		return arreter;
	}





	public Command getUndo() {
		return undo;
	}





	public Command getRedo() {
		return redo;
	}





	public int getDebut_selection() {
		return debut_selection;
	}





	public int getFin_selection() {
		return fin_selection;
	}





	public JTextArea getZoneDeSaisie() {
		return zoneDeSaisie;
	}





	public void ajouterEvenement()
	{
		//KEY LISTENER
		this.zoneDeSaisie.addKeyListener(this);
		//MOUSE LISTENER
		this.zoneDeSaisie.addMouseListener(this);


		//ACTION LISTENER
		this.menuFermer.addActionListener(this);

		this.boutonOutilSave.addActionListener(this);
		this.menuSave.addActionListener(this);

		this.boutonOutilOuvrir.addActionListener(this);
		this.menuOuvrir.addActionListener(this);

		this.menuCopier.addActionListener(this);
		this.boutonOutilCopier.addActionListener(this);

		this.menuCouper.addActionListener(this);
		this.boutonOutilCouper.addActionListener(this);

		this.menuColler.addActionListener(this);
		this.boutonOutilColler.addActionListener(this);

		this.menuDemarrer.addActionListener(this);
		this.boutonOutilDemarrer.addActionListener(this);

		this.menuArreter.addActionListener(this);
		this.boutonOutilArreter.addActionListener(this);

		this.menuRejouer.addActionListener(this);
		this.boutonOutilRejouer.addActionListener(this);


		this.menuRedo.addActionListener(this);
		this.boutonOutilRedo.addActionListener(this);

		this.menuUndo.addActionListener(this);
		this.boutonOutilUndo.addActionListener(this);



		this.menuSelectionnerTout.addActionListener(this);
		this.boutonOutilZoomIn.addActionListener(this);
		this.boutonOutilZoomOut.addActionListener(this);
		this.boutonOutilHelp.addActionListener(this);
	}




	//KEY LISTENER
	@Override
	public void keyPressed(KeyEvent k) {

	}


	@Override
	public void keyReleased(KeyEvent k) {
		if(k.getSource()==zoneDeSaisie)
		{
			Taper temp=(Taper)taper;
			temp.setCharacterTyped(k.getKeyChar());
			temp.setCodeKey(k.getKeyCode());
			taper=temp;
			taper.execute();
		}

	}


	@Override
	public void keyTyped(KeyEvent k) {

	}


	//MOUSE MOTION LISTENER
	@Override
	public void mouseDragged(MouseEvent e) {

	}


	@Override
	public void mouseMoved(MouseEvent e) {

	}


	//MOUSE LISTENER



	@Override
	public void mouseEntered(MouseEvent e) {

	}


	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}


	@Override
	public void mouseReleased(MouseEvent e) {


		if(e.getSource() == zoneDeSaisie )
		{
			Selectionner temp=(Selectionner)selectionner;
			temp.setDebut(zoneDeSaisie.getSelectionStart());
			temp.setFin(zoneDeSaisie.getSelectionEnd());
			selectionner=temp;
			selectionner.execute();
		}


	}




	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.menuFermer)
		{
			this.dispose();
		}


		if(e.getSource() == this.menuCouper || e.getSource() == this.boutonOutilCouper)
		{
			couper.execute();
		}


		if(e.getSource() == this.menuCopier || e.getSource() == this.boutonOutilCopier)
		{
			copier.execute();
		}




		if(e.getSource() == this.menuColler || e.getSource() == this.boutonOutilColler)
		{
			coller.execute();
		}

        if(e.getSource()==this.menuDemarrer|| e.getSource()==this.boutonOutilDemarrer){
        	demarrer.execute();
        	texte.setText("Enregistrement en cours...");

        	menuDemarrer.setEnabled(false);
        	boutonOutilDemarrer.setEnabled(false);
        	menuRejouer.setEnabled(false);
        	boutonOutilRejouer.setEnabled(false);
        	menuArreter.setEnabled(true);
        	boutonOutilArreter.setEnabled(true);
        }

        if(e.getSource()==this.menuArreter|| e.getSource()==this.boutonOutilArreter){
        	arreter.execute();
        	texte.setText("");

        	menuDemarrer.setEnabled(true);
        	boutonOutilDemarrer.setEnabled(true);
        	menuRejouer.setEnabled(true);
        	boutonOutilRejouer.setEnabled(true);
        	menuArreter.setEnabled(false);
        	boutonOutilArreter.setEnabled(false);
        }


        if(e.getSource()==this.menuRejouer|| e.getSource()==this.boutonOutilRejouer){
        	texte.setText("Lecture en cours");

        	Rejouer temp = (Rejouer)rejouer;
        	temp.setParameter(texte);
        	rejouer = temp;

        	rejouer.execute();

        	menuDemarrer.setEnabled(false);
        	boutonOutilDemarrer.setEnabled(false);
        	menuRejouer.setEnabled(false);
        	boutonOutilRejouer.setEnabled(false);
        	menuArreter.setEnabled(true);
        	boutonOutilArreter.setEnabled(true);
        }

        if(e.getSource()==this.menuRedo|| e.getSource()==this.boutonOutilRedo){
        	redo.execute();
        }

        if(e.getSource()==this.menuUndo|| e.getSource()==this.boutonOutilUndo){
        	undo.execute();
        }

		if(e.getSource() == this.menuSelectionnerTout)
		{
			Selectionner temp=(Selectionner)selectionner;
			temp.setDebut(0);
			temp.setFin(zoneDeSaisie.getText().length());
			selectionner=temp;
			selectionner.execute();
		}


		if(e.getSource() == this.boutonOutilZoomIn)
		{
			tailleTexte+=5;
			police=new Font("Arial", Font.PLAIN, tailleTexte);
			zoneDeSaisie.setFont(police);
			label_taille_du_texte.setText(tailleTexte+"");
		}


		if(e.getSource() == this.boutonOutilZoomOut)
		{
			tailleTexte-=5;
			police=new Font("Arial", Font.PLAIN, tailleTexte);
			zoneDeSaisie.setFont(police);
			label_taille_du_texte.setText(tailleTexte+"");
		}






		if(e.getSource() == this.boutonOutilSave || e.getSource() == this.menuSave )
		{
			JFileChooser fenetreEnregistrer = new JFileChooser();
			int operation = fenetreEnregistrer.showSaveDialog(this.fenetre_actuel);
			if(operation==JFileChooser.APPROVE_OPTION)
			{
				//on a bien choisi un repertoire
				String path = fenetreEnregistrer.getSelectedFile().getAbsolutePath();
				Save save_actuel=(Save)save;
				save_actuel.setPath(path);
				save=save_actuel;
				this.fenetre_actuel.setTitle("Mini Editeur v3                  "+path);

				save.execute();

			}
		}


		if(e.getSource() == this.boutonOutilOuvrir || e.getSource() == this.menuOuvrir )
		{
			Ouvrir ouvrir_actuel=(Ouvrir)ouvrir;

			// on ouvre pour selectionner le fichier
			// on recupère le chemin
			JFileChooser fenetreOuvrir = new JFileChooser();
			int operation = fenetreOuvrir.showOpenDialog(this.fenetre_actuel);

			if(operation==JFileChooser.APPROVE_OPTION)
			{
				File selectedFile = fenetreOuvrir.getSelectedFile();
				String path=selectedFile.getAbsolutePath();
				ouvrir_actuel.setPath(path);
				this.fenetre_actuel.setTitle(this.fenetre_actuel.getTitle()+"                  "+path);
				ouvrir = ouvrir_actuel;
				ouvrir.execute();
			}
		}



		if(e.getSource() == this.boutonOutilHelp )
		{
			JDialog aPropos=new JDialog();

			aPropos.setSize(400, 250);  //definit la taille de la fenetre
			aPropos.setDefaultCloseOperation(DISPOSE_ON_CLOSE);  //Pour fermer la fenetre quand on clique sur la croix rouge (bouton fermer) de la fenetre
			aPropos.setTitle(EDITOR_NAME+" version");  //defini le titre de l'editeur
			aPropos.setLocationRelativeTo(null);

			aPropos.getContentPane().add(new JLabel("    Version   :   version 3 "),BorderLayout.NORTH);
			aPropos.getContentPane().add(new JLabel("    PAR Seynabou SARR et Melaine BOUE"),BorderLayout.CENTER);
			aPropos.setVisible(true);
		}




	}


	public void activerDesactiverUndoRedo(boolean undo,boolean redo)
	{
		if(undo)
		{
			menuUndo.setEnabled(true);
			boutonOutilUndo.setEnabled(true);
		}
		else
		{
			menuUndo.setEnabled(false);
			boutonOutilUndo.setEnabled(false);
		}



		if(redo)
		{
			menuRedo.setEnabled(true);
			boutonOutilRedo.setEnabled(true);
		}
		else
		{
			menuRedo.setEnabled(false);
			boutonOutilRedo.setEnabled(false);
		}




	}




	/**
	 * Cette fonction met à jour l'IHM quand le moteur change d'état
	 * @param debut_selection : le debut de la selection
	 * @param fin_selection : la fin de la selection
	 * @param presse_papier : le presse papier
	 * @param buffer : le texte du buffer
	 */
	@Override
	public void update(int debut_selection, int fin_selection, String presse_papier, String buffer) {

		this.debut_selection=debut_selection;
		this.fin_selection=fin_selection;
		this.presse_papier=presse_papier;

		this.zoneDeSaisie.setText(buffer);


		//Update notification zone
		this.zoneDeNotification.setText(
				"PRESSE PAPIER : "+presse_papier+"\n"+
				"BUFFER : "+this.zoneDeSaisie.getText()+"\n"+
				"DEBUT SELECTION : "+debut_selection+"\n"+
				"FIN SELECTION : "+fin_selection+"\n");

		//this.zoneDeSaisie.setCaretPosition(debut_selection);
		this.zoneDeSaisie.requestFocus();
		this.zoneDeSaisie.select(debut_selection, fin_selection);


		//activation et desactivation des boutons de la barre d'outils et dans le menu
		if(debut_selection==fin_selection)
		{
			boutonOutilCopier.setEnabled(false);
			menuCopier.setEnabled(false);

			boutonOutilCouper.setEnabled(false);
			menuCouper.setEnabled(false);
		}
		else
		{
			boutonOutilCopier.setEnabled(true);
			menuCopier.setEnabled(true);

			boutonOutilCouper.setEnabled(true);
			menuCouper.setEnabled(true);
		}

		if(presse_papier.equals(""))
		{
			boutonOutilColler.setEnabled(false);
			menuColler.setEnabled(false);
		}
		else
		{
			boutonOutilColler.setEnabled(true);
			menuColler.setEnabled(true);
		}
	}








}
