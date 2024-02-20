package modele.cartes;

import modele.Modele;
import modele.enums.Dir;
import modele.enums.TypeCarteEquipement;
import modele.enums.TypeCarteTempete;
import modele.enums.TypePaquet;

import java.util.ArrayList;
import java.util.Collections;

public class TasDeCarte {

    private Modele mod;
    private int nbCarte;
    private TypePaquet type;
    private ArrayList<Carte> paquet;

    /**
     * Initialise les cartes tempetes
     * @param mod -> Modele
     */
    private void initPaquetTempete(Modele mod) {
        this.paquet = new ArrayList<>();

        Dir[] directions = {Dir.UP, Dir.DOWN, Dir.LEFT, Dir.RIGHT};

        for(Dir d : directions) {

            for(int i = 0; i < 3; i++) {
                this.paquet.add(new CarteTempete(mod, d, 1, TypeCarteTempete.TEMPETE));
            }

            for(int i = 0; i < 2; i++) {
                this.paquet.add(new CarteTempete(mod, d, 2, TypeCarteTempete.TEMPETE));
            }

            this.paquet.add(new CarteTempete(mod, d, 3, TypeCarteTempete.TEMPETE));

        }

        for(int i = 0; i < 4; i++) {
            this.paquet.add(new CarteTempete(mod, TypeCarteTempete.VAGUE_CHALEUR));
        }

        for(int i = 0; i < 3; i++) {
            this.paquet.add(new CarteTempete(mod, TypeCarteTempete.DECHAINEMENT));
        }

        Collections.shuffle(this.paquet);
        this.nbCarte = this.paquet.size();
    }

    /**
     * Initialise les cartes equipements
     * @param mod -> modele
     */
    private void initPaquetEquipement(Modele mod) {
        this.paquet = new ArrayList<>();

        for(int i = 0; i < 3; i++) {
            this.paquet.add(new CarteEquipement(mod, TypeCarteEquipement.BLASTER));
        }

        for(int i = 0; i < 3; i++) {
            this.paquet.add(new CarteEquipement(mod, TypeCarteEquipement.JETPACK));
        }

        for(int i = 0; i < 2; i++) {
            this.paquet.add(new CarteEquipement(mod, TypeCarteEquipement.BOUCLIER));
        }

        for(int i = 0; i < 2; i++) {
            this.paquet.add(new CarteEquipement(mod, TypeCarteEquipement.TERRASCOPE));
        }

        this.paquet.add(new CarteEquipement(mod, TypeCarteEquipement.ACCEL));
        this.paquet.add(new CarteEquipement(mod, TypeCarteEquipement.RESERVE_EAU));

        Collections.shuffle(this.paquet);
        this.nbCarte = this.paquet.size();
    }

    /**
     * Constructeur du tas de carte
     * @param mod -> modele
     * @param type -> type de paquet (enum)
     */
    public TasDeCarte(Modele mod, TypePaquet type) {
        this.mod = mod;
        this.nbCarte = 0;
        this.type = type;

        switch (type) {

            case TEMPETE:
                this.initPaquetTempete(mod);
                break;

            case EQUIPEMENT:
                this.initPaquetEquipement(mod);
                break;

            default:
                throw new IllegalArgumentException("Mauvais type de paquet !");

        }

    }

    // Renvoie le modele associe au tas de carte
    public Modele getMod() { return this.mod; }

    // Renvoie le nombre de cartes non utilise dans le paquet
    public int getNbCarte() { return this.nbCarte; }

    // Renvoie le type du paquet
    public TypePaquet getType() { return this.type; }

    // Renvoie la paquet de cartes
    public ArrayList<Carte> getPaquet() { return this.paquet; }

    // Pour afficher le tas de carte
    @Override
    public String toString() {
        return "Ce paquet possede " + this.nbCarte + " cartes et est de type : "
                + this.type + "\nVoici son paquet : " + this.paquet;
    }

    /**
     * Pioche une carte et la suppriume du paquet
     * @return Carte
     */
    public Carte pioche() {
        Carte carte = this.paquet.get(0);
        this.paquet.remove(0);
        this.nbCarte--;

        return carte;
    }

    /**
     * Creer un nouveau paquet si le paquet en cours d'utilisation est vide
     * @return vrai si reset effectue, false sinon
     */
    public boolean resetPaquet() {
        if (this.nbCarte > 0) return false;

        switch (this.type) {

            case TEMPETE:
                initPaquetTempete(this.mod);
                break;

            case EQUIPEMENT:
                initPaquetEquipement(this.mod);
                break;

        }

        return true;
    }

}
