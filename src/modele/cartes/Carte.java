package modele.cartes;

import modele.Modele;

public abstract class Carte {

    protected Modele mod;

    public Carte(Modele mod) {
        this.mod = mod;
    }

    public Modele getMod() { return this.mod; }

    public abstract String toString();
    public abstract String nomCarte();
    public abstract void effetCarte();
}
