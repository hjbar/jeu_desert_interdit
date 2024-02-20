package modele.cases;

import modele.Plateau;
import modele.cases.Case;

public class PisteDecollage extends Case {

    public PisteDecollage(Plateau plateau, int x, int y){
        super(plateau, x, y);
    }

    // Renvoie le nom de la case
    @Override
    public String getType() {
        return "Piste Decollage";
    }

}