package modele.cases;

import modele.joueurs.Personnage;
import modele.Plateau;

public class Oasis extends Case {

    public Oasis(Plateau plateau, int x, int y){
        super(plateau, x, y);
    }

    // Pour afficher la case
    @Override
    public String getType() {
        return "Oasis";
    }

    /**
     * Explore la case si pas deja fait
     * De plus, il augmente de 2 l'eau des personnages
     * present sur cette case
     * @return vrai si maintenant exploree, false sinon
     */
    @Override
    public boolean Explore(){
        if (this.estExplore()) return false;

        for ( Personnage P : this.getJoueurs() ) {
            P.remplirGourde(2);
        }
        return super.Explore();
    }

}
