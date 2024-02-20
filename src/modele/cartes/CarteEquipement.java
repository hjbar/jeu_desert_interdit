package modele.cartes;

import modele.Coord;
import modele.Modele;
import modele.cases.Case;
import modele.enums.Dir;
import modele.enums.DirExplo;
import modele.enums.TypeCarteEquipement;
import modele.joueurs.Explorateur;
import modele.joueurs.Personnage;

import static modele.Modele.DESERT_DIM;

public class CarteEquipement extends Carte {

    private TypeCarteEquipement type;

    public CarteEquipement(Modele mod, TypeCarteEquipement type) {
        super(mod);
        this.type = type;
    }

    // Renvoie le type d'equipement
    public TypeCarteEquipement getType() { return this.type; }

    // Pour pouvoir afficher un equipement
    @Override
    public String toString() {
        return "Carte equipement de type " + this.type;
    }

    // Renvoie le nom de la carte
    @Override
    public String nomCarte() { return this.type.toString(); }

    /**
     * Fonction non utilisee mais obligee d'etre presente
     * car cette classe extends une classe abstract
     */
    @Override
    public void effetCarte() {
        return;
    }

    /**
     * Fonction qui utilise un equipement
     * c'est-a-dire joue l'effet de l'equipement
     * @param p -> Personnage qui utilise la carte
     * @param coop -> Personnage qui est implique par l'effet de la carte (peut etre null)
     * @param o -> Objet qui peut etre des Dir ou des Coords par exemple (peut etre null)
     * @return
     */
    public boolean useCarte(Personnage p, Personnage coop, Object o) {

        switch (this.type) {

            case BLASTER:
                return this.useBlaster(p, o);

            case JETPACK:
                if (! (o instanceof Coord)) return false;

                Coord place = (Coord) o;

                return this.useJetpack(p, coop, place);

            case TERRASCOPE:
                if (! (o instanceof Coord)) return false;

                Coord place2 = (Coord) o;

                return this.useTerrascope(place2);

            case RESERVE_EAU:
                return this.useReserveEau(p);

            case BOUCLIER:
                // Ne fait rien sur le modele
                return false;

            case ACCEL:
                // On n'a pas implemente cet equipement
                //return this.useAccel();
                return false;

            default:
                throw new IllegalArgumentException("Mauvais type de carte equipement !");

        }

    }

    /**
     * Utilise le blaster d'un personnage si possible
     * @param p -> Personnage
     * @param o -> Dir (enum) ou DirExplo (enum)
     * @return vrai si blaster utilisee, false sinon
     */
    public boolean useBlaster(Personnage p, Object o) {

        if (o instanceof Dir) {
            Dir d = (Dir) o;
            Case c = p.getPos().voisine(d);
            while (c.getSable() > 0) p.desabler(d);
            return true;
        }

        else if (o instanceof DirExplo) {

            if (! (p instanceof Explorateur) ) return false;

            DirExplo d = (DirExplo) o;
            Explorateur pers = (Explorateur) p;
            Case c = p.getPos().voisine(d);
            while (c.getSable() > 0) pers.desabler(d);
            return true;

        }

        return false;
    }

    /**
     * Utilise le jetpack d'un joueur
     * @param p1 -> Personnage
     * @param p2 -> Personnage
     * @param c -> Coord
     * @return vrai si jetpack utilise, false sinon
     */
    public boolean useJetpack(Personnage p1, Personnage p2, Coord c) {
        int x = c.getX();
        int y = c.getY();

        if (x < 0 || x >= DESERT_DIM || y < 0 || y >= DESERT_DIM) return false;

        Case place = this.mod.getPlateau().getCase(x, y);
        if (place.getSable() > 1) return false;

        p1.setCoord(c);

        if (p2 != null) p2.setCoord(c);

        return true;
    }

    /**
     * Utilise le terrascope d'un personnage
     * @param c -> coord
     * @return vrai si terrascope utilise, false sinon
     */
    public boolean useTerrascope(Coord c) {
        int x = c.getX();
        int y = c.getY();

        if (x < 0 || x >= DESERT_DIM || y < 0 || y >= DESERT_DIM) return false;

        Case place = this.mod.getPlateau().getCase(x, y);
        place.Explore();

        return true;
    }

    /**
     * Utilise la reserve d'eau d'un personnage
     * @param p -> Personnage
     * @return vrai si reserve d'eau utilise, false sinon
     */
    public boolean useReserveEau(Personnage p) {
        Case c = p.getPos();

        boolean aBu = false;

        for (Personnage pers : c.getJoueurs()) {
            if (pers.getEau() < pers.getMaxEau()) aBu = true;
            pers.remplirGourde(2);
        }

        return aBu;
    }

}
