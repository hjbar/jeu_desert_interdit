package modele.joueurs;

import modele.Coord;
import modele.cases.Case;
import modele.cases.Oeil;
import modele.enums.DirExplo;

import static modele.Modele.DESERT_DIM;

public class Explorateur extends Personnage {

    public Explorateur(Case c) {
        super(c);
    }

    // Renvoie le nom du personnage
    @Override
    public String getRole() {
        return "Explorateur";
    }

    /**
     * Fonction de deplacement en diagonale
     * (que pour l'explorateur)
     * @param d -> DirExplo
     * @return vrai si possible, false sinon
     */
    public boolean deplacer(DirExplo d) {
        // Pour se deplacer, il ne faut pas etre ensable
        if (this.pos.getSable() > 1) {
            return false;
        }

        Coord dirCoord = Coord.dirToCoord(d);
        int dirX = dirCoord.getX();
        int dirY = dirCoord.getY();

        int newX = this.pos.getX() + dirX;
        int newY = this.pos.getY() + dirY;

        if ( (0 <= newX && newX < DESERT_DIM) && (0 <= newY && newY < DESERT_DIM) ) {
            Case place = this.pos.voisine(d);
            int nbSable = place.getSable();

            if ((! (place instanceof Oeil)) && nbSable < 2) {
                this.pos.delJoueur(this);
                this.pos = place;
                this.pos.addJoueur(this);
                this.notifyObservers();
                return true;
            }

        }

        return false;
    }

    /**
     * Fonction de desablement en diagonale
     * (que pour l'explorateur)
     * @param d -> DirExplo (enum)
     * @return vrai si desablement possible, false sinon
     */
    public boolean desabler(DirExplo d) {
        Coord dirCoord = Coord.dirToCoord(d);
        int dirX = dirCoord.getX();
        int dirY = dirCoord.getY();

        int newX = this.pos.getX() + dirX;
        int newY = this.pos.getY() + dirY;

        if ( (0 <= newX && newX < DESERT_DIM) && (0 <= newY && newY < DESERT_DIM) ) {
            Case place = this.pos.voisine(d);
            int nbSable = place.getSable();

            if ((! (place instanceof Oeil)) && nbSable > 0) {
                place.desabler();
                return true;
            }

        }

        return false;

    }


}
