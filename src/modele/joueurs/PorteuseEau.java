package modele.joueurs;

import modele.cases.Case;
import modele.cases.Oasis;
import modele.joueurs.Personnage;

public class PorteuseEau extends Personnage {

    // Le max_eau de la porteuse d'eau n'est pas le meme que les autres personnages
    private static final int max_eau = 5;

    public PorteuseEau(Case c) {
        super(c);
        this.eau = max_eau;
    }

    // Renvoie le nom du personnage
    @Override
    public String getRole() {
        return "Porteuse d'eau";
    }

    // Renvoie le maximum d'eau de la porteuse d'eau
    @Override
    public int getMaxEau() { return this.max_eau; }

    /**
     * Permet de re-explorer les oasis deja explorees
     * @return vrai si explore maintenant, false sinon
     */
    @Override
    public boolean explorer() {
        if (! this.pos.estExplore()) {
            return super.explorer();
        }
        else if (this.pos instanceof Oasis) {
            this.remplirGourde(2);
            this.getPos().getPlateau().notifyObservers();
            return true;
        }

        return false;
    }

    /**
     * Donne de l'eau a un personnage selon le maximum de la porteuse d'eau
     * @param quantite -> int
     * @param cible -> Personnage
     * @return vrai si action possible, false sinon
     */
    @Override
    public boolean donneEau(int quantite, Personnage cible) {
        // On verifie que le joueur est sur une case adjancente dans le controleur

        if (cible.getEau() == cible.getMaxEau()) return false;

        if (this.eau <= 0) return false;

        int i = 0;
        while (i < quantite && this.eau > 0 && cible.getEau() < cible.getMaxEau()) {
            this.boire();
            cible.remplirGourde(1);
            i++;
        }

        return true;
    }

}
