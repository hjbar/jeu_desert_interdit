package vue;

import controleur.Controleur;
import modele.*;
import modele.cases.Case;
import modele.cases.Oasis;
import modele.joueurs.Archeologue;
import modele.joueurs.Explorateur;
import modele.joueurs.Personnage;
import modele.joueurs.PorteuseEau;

import javax.swing.*;
import java.awt.*;

public class VueControleur extends JPanel implements Observer {

    Controleur ctrl;
    private final JButton[] button;
    private final JLabel ctrlState;
    private final JLabel ctrlPLayer;
    private final JLabel ctrlAction;
    private final Dimension dim = new Dimension(125, 55);

    public VueControleur(Modele modele, Controleur ctrl){
        super();
        this.button = new JButton[7];
        this.ctrl = ctrl;

        this.setLayout(new GridLayout(6, 2));

        this.ctrlState = new JLabel();
        String s0 = "Etat : " + this.ctrl.getCtrlState().toString();
        this.ctrlState.setText(s0);
        this.add(this.ctrlState);

        this.ctrlPLayer = new JLabel();
        String s1 = "Joue : Joueur " + this.ctrl.getPlayer();
        this.ctrlPLayer.setText(s1);
        this.add(this.ctrlPLayer);

        this.ctrlAction = new JLabel();
        String s2 = "Actions : " + this.ctrl.getNbAction();
        this.ctrlAction.setText(s2);
        this.add(this.ctrlAction);

        JButton boutonFin = new JButton("Fin de tour");
        this.button[0] = boutonFin;


        JButton boutonExplore = new JButton("Explorer");
        this.button[1] = boutonExplore;


        JButton boutonRamasse = new JButton("Ramasser");
        this.button[2] = boutonRamasse;


        JButton boutonPoser = new JButton("Poser");
        this.button[3] = boutonPoser;


        JButton boutonDEau = new JButton("Donner Eau");
        this.button[4] = boutonDEau;


        JButton boutonPEau = new JButton("Prendre Eau");
        this.button[5] = boutonPEau;


        JButton boutonActSpe = new JButton("Action");
        this.button[6] = boutonActSpe;

        for (int i = 0; i < this.button.length; ++i){
            this.add(this.button[i]);
            this.button[i].addActionListener(ctrl);
        }


        this.ctrl.addObserver(this);
        this.update();
    }

    /** met à jour les boutons de commandes lorsqu'un observable utilise notifyObserver */
    public void update(){
        this.stateUpdate();
        this.exploreUpdate();
        this.ramasserUpdate();
        this.poserUpdate();
        this.actSpeUpdate();
        this.finTourUpdate();

        // On desactive tous les boutons en fin de partie
        if ( this.ctrl.getCtrlState() == Controleur.state.Defaite || this.ctrl.getCtrlState() == Controleur.state.Victoire )
            for (int i = 0; i < this.button.length; ++i)
                this.button[i].setEnabled(false);
    }

    /** met a jour les informations sur le jeu (etat, nbAction, Joueur courant)*/
    private void stateUpdate(){
        String s0 = "Etat : " + this.ctrl.getCtrlState().toString();
        this.ctrlState.setText(s0);

        String s1 = "Joue : Joueur " + (this.ctrl.getPlayer()+1);
        this.ctrlPLayer.setText(s1);

        String s2 = "Actions : " + this.ctrl.getNbAction();
        this.ctrlAction.setText(s2);
    }

    /** desactive le boutton de fin de tour tant qu'il y a des cartes tempetes a piocher */
    private void finTourUpdate(){
        boolean fini = this.ctrl.getNbPiocheply() == 0;
        this.button[0].setEnabled(fini);

    }
    /** desactive le bouton explore si une case n'est pas explorable ou que l'etat du jeu ne le permette pas*/
    private void exploreUpdate(){
        Case c = this.ctrl.getCurrentPly().getPos();
        boolean explorable = c.getSable() == 0 && ! c.estExplore() && this.ctrl.getCtrlState() != Controleur.state.FinTour;
        this.button[1].setEnabled(explorable);
    }

    /** desactive ou active le bouton ramasser en fonction de s'il y a une piece sur la case et de si la case est ensseveli*/
    private void ramasserUpdate(){
        Case c = this.ctrl.getCurrentPly().getPos();
        boolean r = c.getPieces().size() > 0 && c.getSable() <= 1;
        this.button[2].setEnabled(r);
    }
    /** desactive ou active le bouton poser en fonction de si le personnage a une piece et de si la case est ensseveli*/
    private void poserUpdate(){
        Personnage P = this.ctrl.getCurrentPly();
        boolean p = P.getPieces().size() > 0 && P.getPos().getSable() <= 1;
        this.button[3].setEnabled(p);
    }

    /** active ou desactive le boutton d'action des personnages en fonction du personnage courant */
    private void actSpeUpdate(){
        Personnage ply = this.ctrl.getCurrentPly();
        String txt = ply.getRole();
        this.button[6].setText(txt);

        boolean fl = ply instanceof Archeologue || ply instanceof Explorateur;
        this.button[6].setEnabled(!fl);
    }
}
