package modele.cases;

import modele.Plateau;

public class Mirage extends Case {

    public Mirage(Plateau plateau, int x, int y) {
        super(plateau, x, y);
    }

    // Pour afficher la case
    @Override
    public String getType() {
        return "Mirage";
    }

}
