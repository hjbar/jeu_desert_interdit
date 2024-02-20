package modele.joueurs;

import modele.cases.Case;


public class Navigatrice extends Personnage {

    // On gere la navigatrice dans le controleur

    public Navigatrice(Case c) {
        super(c);
    }

    // Renvoie le nom du personnage
    @Override
    public String getRole() {
        return "Navigatrice";
    }

}
