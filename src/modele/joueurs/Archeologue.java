package modele.joueurs;

import modele.Coord;
import modele.cases.Oeil;
import modele.enums.Dir;
import modele.cases.Case;

import static modele.Modele.DESERT_DIM;

public class Archeologue extends Personnage {

    public Archeologue(Case c) {
        super(c);
    }

    // Renvoie le nom du personnage
    @Override
    public String getRole() {
        return "Archeologue";
    }

    // Fonction qui desable (2 sables pour l'acheologue)
    @Override
    public boolean desabler(Dir d) {
        Coord dirCoord = Coord.dirToCoord(d);
        int dirX = dirCoord.getX();
        int dirY = dirCoord.getY();

        int newX = this.pos.getX() + dirX;
        int newY = this.pos.getY() + dirY;

        boolean res = false;

        if ( (0 <= newX && newX < DESERT_DIM) && (0 <= newY && newY < DESERT_DIM) ) {
            Case place = this.pos.voisine(d);
            int nbSable = 0;

            if (! (place instanceof Oeil)) {

                nbSable = place.getSable();
                if (nbSable > 0) { place.desabler(); res = true; }

                nbSable = place.getSable();
                if (nbSable > 0) place.desabler();

            }

        }

        return res;
    }

}
