package modele.cartes;

import modele.enums.Dir;
import modele.Modele;
import modele.enums.TypeCarteTempete;

public class CarteTempete extends Carte {

    private TypeCarteTempete type;
    private Dir dir;
    private int nivVent;

    // Pour le souffle
    public CarteTempete(Modele mod, Dir dir, int niv, TypeCarteTempete type) {
        super(mod);
        this.type = type;
        this.dir = dir;
        this.nivVent = niv;
    }

    // Pour le dechainement et la vague de chaleur
    public CarteTempete(Modele mod, TypeCarteTempete type) {
        super(mod);
        this.type = type;
        this.dir = null;
        this.nivVent = -1;
    }

    // Renvoie le type de la carte tempete
    public TypeCarteTempete getType() { return this.type; }

    // Renvoie la direction de la carte (peut etre null)
    public Dir getDir() { return this.dir; }

    // Renvoie la force du vent (peut etre null)
    public int getNivVent() { return this.nivVent; }

    // Pour afficher la carte
    @Override
    public String toString() {
        return "Type de carte Tempete : " + this.type + ", avec comme direction : " + this.dir
                + ", avec un niveau de vent : " + this.nivVent;
    }

    // Renvoie le nom de la carte
    @Override
    public String nomCarte(){

        switch (this.type){

            case TEMPETE:
                return "Vent souffle" + this.dir.toString() + " " + this.nivVent + " Cases";
            case VAGUE_CHALEUR:
                return "Vague de chaleur";
            case DECHAINEMENT:
                return "La tempete se dechaine";
            default:
                throw new IllegalArgumentException("Mauvais type de carte Tempete !");

        }
    }

    // Active l'effet de la carte
    @Override
    public void effetCarte() {

        switch (this.type) {

            case TEMPETE:
                super.getMod().ventSouffle(nivVent, dir);
                break;

            case VAGUE_CHALEUR:
                super.getMod().vagueChaleur();
                break;

            case DECHAINEMENT:
                super.getMod().tempeteDechaine();
                break;

            default:
                throw new IllegalArgumentException("Mauvais type de carte Tempete !");

        }

    }
}
