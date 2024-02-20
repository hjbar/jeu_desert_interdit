package modele.cases;

import modele.*;
import modele.enums.Dir;
import modele.enums.DirExplo;
import modele.enums.Piece;
import modele.joueurs.Alpiniste;
import modele.joueurs.Personnage;

import java.util.ArrayList;

public class Case {

    // désert auquel appartient la case
    protected final Plateau plateau;
    // les coordonnés de la case dans le plateau
    private Coord coord;
    // le nombre de couche de sable a supprimé avant de pouvoir explore
    private int sable;
    // est-ce que le contenu de la case est revele
    private boolean explore;
    // Liste des joueurs se trouvant sur la case
    ArrayList<Personnage> joueurs;
    // La liste des pieces sur la case
    ArrayList<Piece> pieces;

    public Case(Plateau plateau, int x, int y) {
        this.plateau = plateau;
        this.sable = 0;
        this.explore = false;
        coord = new Coord(x, y);
        this.joueurs = new ArrayList<>();
        this.pieces = new ArrayList<>();
    }

    // Pour afficher une cae
    public String toString() {
        return String.valueOf(this.sable);
    }

    // Renvoie la nom de la case
    public String getType() {
        return "Cite";
    }

    // Renvoie le nombre de sable sur la case
    public int getSable() {
        return this.sable;
    }

    // Renvoie l'abscisse de la case
    public int getX() {
        return this.coord.getX();
    }

    // Renvoie l'ordonnee de la case
    public int getY() {
        return this.coord.getY();
    }

    /**
     * Renvoie l'etat de la case
     * @return vrai si case deja exploree, false sinon
     */
    public boolean estExplore() {
        return this.explore;
    }

    // Renvoie le plateau associe a la case
    public Plateau getPlateau() { return this.plateau; }

    // Renoie les joueurs present sur la case
    public ArrayList<Personnage> getJoueurs() {
        return this.joueurs;
    }

    // Renvoie les pieces present sur la case
    public ArrayList<Piece> getPieces() { return this.pieces; }

    /**
     * Modifie les coordonnees de la case
     * @param C -> Coord
     */
    public void setCoord(Coord C) {
        this.coord = C;
        this.plateau.notifyObservers();
    }

    /**
     * Ajoute le personnage sur cette case
     * @param P -> Personnage
     */
    public void addJoueur(Personnage P) {
        this.joueurs.add(P);
        this.plateau.notifyObservers();
    }

    /**
     * Supprime le personnage de la case
     * @param P -> Personnage
     */
    public void delJoueur(Personnage P) {
        this.joueurs.remove(P);
        this.plateau.notifyObservers();
    }

    // Renvoie vrai si un alpiniste est present sur la case, false sinon
    public boolean haveAlpiniste() {

        for (Personnage p : this.joueurs) {

            if (p instanceof Alpiniste) return true;

        }

        return false;
    }

    // Ajoute la piece sur la case
    public void addPiece(Piece P) {
        this.pieces.add(P);
        this.plateau.notifyObservers();
    }

    // Supprime la piece de la case
    public void delPiece(Piece P) {
        this.pieces.remove(P);
        this.plateau.notifyObservers();
    }

    /**
     * Le niveau de sable sur la case est incremente
     */
    public void ensabler() {
        ++this.sable;
        this.plateau.notifyObservers();
    }

    /**
     * Le niveau de sable est decremente
     */
    public void desabler() {
        --this.sable;
        this.plateau.notifyObservers();
    }

    /**
     * Explore la case si pas deja fait
     * @return renvoie vrai si la case a ete explore maintenant, false sinon
     */
    public boolean Explore() {
        // la case est desormais explore
        if (this.sable == 0) {
            this.explore = true;
            this.plateau.notifyObservers();
            return true;
        }
        return false;
    }

    /**
     * Renvoie la case voisine a celle-ci dans le direction d
     * @param d -> Dir (enum)
     * @return
     */
    public Case voisine(Object d) {
        Coord c = null;

        if (d instanceof Dir) {
            c = Coord.dirToCoord((Dir) d);
        }

        else if (d instanceof DirExplo) {
            c = Coord.dirToCoord((DirExplo) d);
        }

        else {
            throw new IllegalArgumentException("L'argument n'est pas une direction !");
        }

        int vX = this.getX() + c.getX();
        int vY = this.getY() + c.getY();

        int taillePlateauX = this.plateau.getTailleX();
        int taillePlateauY = this.plateau.getTailleY();

        int eyeX = this.plateau.getEye().getX();
        int eyeY = this.plateau.getEye().getY();
        if (vX < 0 || vX >= taillePlateauX || vY < 0 || vY >= taillePlateauY || (vX == eyeX && vY == eyeY)) {
            throw new RuntimeException("Il n'y a pas de case voisine dans cette direction");
        }
        return this.plateau.getCase(vX, vY);
    }

}




