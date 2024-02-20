import controleur.Controleur;
import modele.*;
import vue.*;

public class DesertInterdit {


    public static void main(String[] args){

        choixJoueur choice = new choixJoueur();

        while(! choice.aChoisis()) {
            // On attend le choix
            System.out.print("");
        }

        int nbJoueurs = choice.getNbJoueurs();

        choice.fermeFenetre();

        Modele m = new Modele(nbJoueurs);
        Controleur ctrl = new Controleur(m);
        new DIVue(m, ctrl);
        new vueMetereologue(ctrl.getCtrlM());

    }


}
