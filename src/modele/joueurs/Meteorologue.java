package modele.joueurs;

import modele.cases.Case;

public class Meteorologue extends Personnage{

    // On gere la meteorologue dans le controleur

    public Meteorologue(Case c){
        super(c);
    }

    // Renvoie le nom du personnage
    @Override
    public String getRole() {
        return "Meteorologue";
    }

}
