package modele.joueurs;

import modele.Coord;
import modele.cases.Oeil;
import modele.enums.Dir;
import modele.cases.Case;

import static modele.Modele.DESERT_DIM;

public class Alpiniste extends Personnage {

    // Le personnage que l'alpiniste deplace avec lui
    private Personnage pers;

    public Alpiniste(Case c) { super(c); }

    // Renvoie le nom du personnage
    @Override
    public String getRole() {
        return "Alpiniste";
    }

    /**
     * Initialise le personnage transporte par l'Alpiniste
     * @param P -> Personnage
     */
    public void setPers(Personnage P) { this.pers = P; }

    // Initialise les coords du Personnage et du side-kick s'il est la
    @Override
    public void setCoord(Coord c){
        super.setCoord(c);
        if ( this.pers != null)
            this.pers.setCoord(c);
    }

    /**
     * Fonction de deplacement de l'alpiniste solo
     * @param d -> Dir
     * @return vrai si deplacement possible, false sinon
     */
    public boolean deplacerSolo(Dir d) {
        Coord dirCoord = Coord.dirToCoord(d);
        int dirX = dirCoord.getX();
        int dirY = dirCoord.getY();

        int newX = this.pos.getX() + dirX;
        int newY = this.pos.getY() + dirY;

        if ( (0 <= newX && newX < DESERT_DIM) && (0 <= newY && newY < DESERT_DIM) ) {
            Case place = this.pos.voisine(d);

            if (! (place instanceof Oeil)) {
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
     * Fonction de deplacement de l'Alpiniste et de son side-kick
     * @param d -> Dir (enum)
     * @param p -> Personnage
     * @return
     */
    public boolean deplacerDuo(Dir d, Personnage p) {
        Case prev = this.getPos();

        boolean res = deplacerSolo(d);

        if (! prev.getJoueurs().contains(p))
            throw new IllegalArgumentException("Ce personnage n'etait pas sur la meme que l'alpiniste !");

        int x = this.pos.getX();
        int y = this.pos.getY();

        Coord current = new Coord(x, y);

        p.setCoord(current);

        return res;
    }

    // Choisis le mode de deplacement de l'alpiniste (seul ou non)
    @Override
    public boolean deplacer(Dir d){
        if (this.pers == null)
            return this.deplacerSolo(d);
        else
            return this.deplacerDuo(d, this.pers);
    }

}
