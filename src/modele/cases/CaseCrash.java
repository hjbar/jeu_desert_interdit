package modele.cases;

import modele.joueurs.Personnage;
import modele.Plateau;

import java.util.ArrayList;

public class CaseCrash extends Case {

    public CaseCrash(Plateau plateau, int x, int y){
        super(plateau, x, y);
        this.Explore();
    }

    public CaseCrash(Plateau plateau, int x, int y, ArrayList<Personnage> P){
        super(plateau, x, y);
        for (Personnage ply : P){
            this.addJoueur(ply);
        }
        this.Explore();
    }

    // Pour afficher la case
    @Override
    public String getType() {
        return "Lieu Crash";
    }

}