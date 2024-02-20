package modele.cases;

import modele.enums.Piece;
import modele.Plateau;

import java.util.ArrayList;

import static modele.Modele.DESERT_DIM;

public class Indice extends Case {

    // Piece sur laquelle l'indice donne une indication
    private Piece piece;

    public Indice(Plateau plateau, int x, int y, Piece p) {
        super(plateau, x, y);
        this.piece = p;
    }

    // Renvoie le type de piece de cette case indice
    public Piece getPiece() {
        return this.piece;
    }

    // Renvoie le nom de cette case
    @Override
    public String getType() {
        return "Indice";
    }

    /**
     * Explore la case si pas deja fait
     * @return vrai si explore maintenant, false sinon
     */
    @Override
    public boolean Explore() {

        if (!this.estExplore()) {
            super.Explore();
            this.placePiece();
            this.plateau.notifyObservers();
            return true;
        }
        return false;
    }

    // Place la piece sur le plateau si les 2 cases indices explorees
    public void placePiece() {
        ArrayList<Indice> indList = new ArrayList<>();

        for (int j = 0; j < DESERT_DIM; j++) {
            for (int i = 0; i < DESERT_DIM; i++) {
                Case c = this.plateau.getCase(i, j);

                if (c instanceof Indice && ((Indice) c).getPiece() == this.piece && c.estExplore()) {
                    indList.add((Indice) c);
                }

            }
        }

        if (indList.size() != 2) return;

        Indice ind0 = indList.get(0);
        Indice ind1 = indList.get(1);

        int x = ind1.getX();
        int y = ind0.getY();

        if (this.plateau.getEye().getX() != x && this.plateau.getEye().getY() != y) {
            Case cas = this.plateau.getCase(x, y);
            cas.addPiece(this.piece);
        }
        else {
            x = ind0.getX();
            y = ind1.getY();
            Case cas = this.plateau.getCase(x, y);
            cas.addPiece(this.piece);
        }

    }


}
