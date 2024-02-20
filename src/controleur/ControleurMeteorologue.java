package controleur;

import modele.Modele;
import modele.Observable;
import modele.cartes.Carte;
import modele.cartes.TasDeCarte;
import modele.joueurs.Meteorologue;
import modele.joueurs.Personnage;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ControleurMeteorologue extends Observable implements ActionListener{
    /** Le controleur de la fenetre principale **/
    Controleur ctrl;
    /** booleen a partir duquel on ferme la fenetre du meteorologue*/
    private boolean closeWindow = false;
    /** booleen a partir duquel on decide quel boutton montrer */
    private boolean showCards = false;

    /** Constructeur de ControleurMeteorologue **/
    public ControleurMeteorologue(Controleur ctrl){
        this.ctrl = ctrl;
    }

    /** Methode avec laquel on gere les inputs des bouttons de la nouvelle fenetre */
    public void actionPerformed(ActionEvent e) {
        // Si on verifie si on peut faire des actions
        boolean act = this.ctrl.getNbAction() > 0;
        Personnage ply = this.ctrl.getCurrentPly();
        if ( ! (ply instanceof Meteorologue)) {
            // un autre joueur interagit avec la fenetre alors on ferme la fenetre
            this.setCloseWindow(true);
        } else if ( act && e.getActionCommand() == "Skip"){
            // Si on peut encore piocher pendant ce tour on pioche une carte en moins en echange d'une action
            if (this.ctrl.getNbPiocheply() > 0) {
                this.ctrl.decrNbAction();
                this.ctrl.decrNbPiochePly();
                this.setCloseWindow(true);
            }
        // On notifie la vue afin qu'elle affiche les prochaines cartes
        } else if ( act && e.getActionCommand() == "Look") {
            this.setShowCards(true);
        } else if ( act ){
            int i = 0;
            boolean flag = true;
            // On cherche a trouve le bouton de carte qui a ete choisi dans vueMeteorologue
            while (flag && i < this.ctrl.getModele().getTempete_niveau()){

                if ( e.getActionCommand().equals( String.valueOf(i) )){
                    ArrayList<Carte> P = this.getModele().getTdd().getPaquet();
                    Carte c = P.remove(i);
                    P.add(c);

                    this.ctrl.decrNbAction();
                    this.setCloseWindow(true);
                    this.setShowCards(false);
                    flag = false;
                }
                ++i;
            }
        }

    }

    /** renvoie le modele du Controleur */
    public Modele getModele() { return this.ctrl.getModele(); }

    /** renvoie l'etat de la fenetre */
    public boolean getCloseWindow() { return this.closeWindow; }

    /** renvoie l'etat de la vue des cartes */
    public boolean getShowCards() { return this.showCards; }

    /** Indique si on affiche ou pas la fenetre
     * @param b : etat de la fenetre
     * */
    protected void setCloseWindow(boolean b) {
        this.closeWindow = b;
        if (! b) // Si jamais l'utilisateur quitte la fenetre avec la croix, on s'assure qu'au prochaine affichage la fenetre soit reinitialiser
            this.setShowCards(false);
        this.notifyObservers();
    }
    /** Indique si on affiche ou pas les cartes
     * @param b : etat de la vue des cartes dans la vueMeteorologique
     * */
    private void setShowCards(boolean b){
        this.showCards = b;
        this.notifyObservers();
    }
}
