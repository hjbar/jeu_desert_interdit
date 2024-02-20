package modele.joueurs;

import modele.Coord;
import modele.Observable;
import modele.cartes.TasDeCarte;
import modele.cases.Oeil;
import modele.enums.Piece;
import modele.cartes.CarteEquipement;
import modele.cases.Case;
import modele.enums.Dir;
import modele.enums.TypeCarteEquipement;

import static modele.Modele.DESERT_DIM;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Personnage extends Observable {

    int eau;
    Case pos;
    private final int id;
    private final Set<CarteEquipement> bag;
    private final ArrayList<Piece> pieces;
    private static int cpt_id = 0;
    private static final int max_eau = 4;

    public Personnage(Case c) {
        this.eau = max_eau;
        this.pos = c;
        c.addJoueur(this);
        this.id = cpt_id;
        cpt_id++;
        this.bag = new HashSet<>();
        this.pieces = new ArrayList<>();
    }

    // Permet d'afficher le personnage
    @Override
    public String toString() {
        int x = this.pos.getX();
        int y = this.pos.getY();

        return "Le personnage d'id " + this.id + " possede " + this.eau +
                " en niveau d'eau et est place en case de coordonnees (" + x + ", " + y + ").";
    }

    // Renvoie le nom du personnage
    public String getRole() {
        return "Action";
    }

    // Renvoie la quantite d'eau du personnage
    public int getEau() {
        return this.eau;
    }

    // Renvoie la case ou se situe le personnage
    public Case getPos() {
        return this.pos;
    }

    // Renvoie l'id du personnage
    public int getId() { return this.id; }

    // Renvoie le sac d'equipement du personnage
    public Set<CarteEquipement> getBag() { return this.bag; }

    // Renvoie le maximum d'eau porte par le personnage
    public int getMaxEau() { return max_eau; }

    // Renvoie les pieces portees par le personnage
    public ArrayList<Piece> getPieces() { return this.pieces; }

    /**
     * Change les coordonnees donc la case du personnage
     * @param coord -> Coord
     */
    public void setCoord(Coord coord) {
        int x = coord.getX();
        int y = coord.getY();

        if (x < 0 || x >= DESERT_DIM || y < 0 || y >= DESERT_DIM) {
            throw new IllegalArgumentException("Les coordonnees ne sont pas dans le plateau !");
        }

        Case cas = this.pos.getPlateau().getCase(x, y);

        this.pos.delJoueur(this);
        this.pos = cas;
        this.pos.addJoueur(this);
        this.notifyObservers();
    }

    /**
     * Deplace le personnage sur une case adjacente
     * @param d -> Dir (enum)
     * @return vrai si deplacement possible, false sinon
     */
    public boolean deplacer(Dir d) {
        boolean Alpi = this.pos.haveAlpiniste();

        // Pour se deplacer, il ne faut pas etre ensable (sauf si Alpiniste)
        if (this.pos.getSable() > 1 && !Alpi) {
            return false;
        }

        Coord dirCoord = Coord.dirToCoord(d);
        int dirX = dirCoord.getX();
        int dirY = dirCoord.getY();

        int newX = this.pos.getX() + dirX;
        int newY = this.pos.getY() + dirY;

        if ( (0 <= newX && newX < DESERT_DIM) && (0 <= newY && newY < DESERT_DIM) ) {
            Case place = this.pos.voisine(d);
            int nbSable = place.getSable();

            if ( ((! (place instanceof Oeil)) && nbSable < 2) ) {
                this.pos.delJoueur(this);
                this.pos = place;
                this.pos.addJoueur(this);
                this.notifyObservers();
                return true;
            }

        }

        return false;

    }

    /**
     * Desable sur la case meme ou adjacente
     * @param d -> Dir (enum)
     * @return vrai si possible, false sinon
     */
    public boolean desabler(Dir d) {
        Coord dirCoord = Coord.dirToCoord(d);
        int dirX = dirCoord.getX();
        int dirY = dirCoord.getY();

        int newX = this.pos.getX() + dirX;
        int newY = this.pos.getY() + dirY;

        if ( (0 <= newX && newX < DESERT_DIM) && (0 <= newY && newY < DESERT_DIM) ) {
            Case place = this.pos.voisine(d);
            int nbSable = place.getSable();

            if ( (! (place instanceof Oeil)) && nbSable > 0) {
                place.desabler();
                return true;
            }

        }

        return false;

    }

    // Le personnage boit, et perd 1 d'eau
    public boolean boire() {
        this.eau -= 1;
        this.notifyObservers();
        return this.eau >= 0;
    }

    // Renvoie la gourde du personnage selon la quantite donnee en parametre
    public void remplirGourde(int quantite) {

        if (this.eau + quantite >= this.getMaxEau()) {
            this.eau = this.getMaxEau();
        }
        else {
            this.eau += quantite;
        }
        this.notifyObservers();
    }

    /**
     * Explore la case ou se situe le personnage si possible
     * @return vrai si pas deja explore, false sinon
     */
    public boolean explorer() {

        if (! this.pos.estExplore()) {
            this.pos.Explore();
            this.genereEquip();
            return true;
        }
        return false;

    }

    // Renvoie le nombre d'equipement du personnage
    public int nbEquip() {
        return this.bag.size();
    }

    // Renvoie true si le personnage possede cet equipement, false sinon
    public boolean possede(CarteEquipement equip) {
        return this.bag.contains(equip);
    }

    // Renvoie true si le personnage possede ce type d'equipement, false sinon
    public boolean possede(TypeCarteEquipement equip) {

        for (CarteEquipement carte : this.bag) {
            if (carte.getType() == equip) return true;
        }

        return false;
    }

    // Genere un equipement aleatoirement et l'ajoute au sac du personnage
    public void genereEquip() {
        TasDeCarte equips = this.pos.getPlateau().getModele().getEquips();

        // Si on tente d'acceder a la pioche avec le modele primitif (sans joueur)
        if (equips == null) return;
        // On actualise le paquet s'il y a plus d'equipements disponibles dans la pioche
        else equips.resetPaquet();

        CarteEquipement equip = (CarteEquipement) equips.pioche();
        this.addEquip(equip);
    }

    /**
     * Ajoute l'equipement au personnage si possible
     * @param equip -> CarteEquipement
     * @return vrai si le personnane ne possedait pas deja l'equipement, false sinon
     */
    public boolean addEquip(CarteEquipement equip) {

        if ( !this.possede(equip) ) {
            this.bag.add(equip);
            return true;
        }

        return false;
    }

    /**
     * Supprime l'equipement du personnage si possble
     * @param equip -> CarteEquipement
     * @return vrai si le personnage possede cet equipement, false sinon
     */
    public boolean delEquip(CarteEquipement equip) {

        if ( this.possede(equip) ) {
            this.bag.remove(equip);
            return true;
        }

        return false;
    }

    /**
     * Donne un equipement d'un personnage a un autre
     * @param equip -> CarteEquipement
     * @param pers -> Personnage
     * @return vrai si action possible, false sinon
     */
    public boolean donneEquip(CarteEquipement equip, Personnage pers) {
        if (! this.possede(equip)) return false;

        if (! this.pos.getJoueurs().contains(pers)) return false;

        pers.addEquip(equip);
        this.delEquip(equip);

        return true;
    }

    /**
     * Utilise l'equipement du personnage
     * @param equip -> Equipement
     * @param pers -> Personnage
     * @param o -> Object
     * @return true si utilisation possible, false sinon
     */
    public boolean useEquip(CarteEquipement equip, Personnage pers, Object o) {

        if ( this.possede(equip) ) {
            boolean isUse = equip.useCarte(this, pers, o);
            if (isUse) this.delEquip(equip);
            return isUse;
        }

        else return false;

    }

    /**
     * Prend la piece sur la case
     * @return vrai si action possible, false sinon
     */
    public boolean prendrePiece() {
        if (this.pos.getSable() > 1) return false;

        ArrayList<Piece> pieceL = this.pos.getPieces();

        if (pieceL.size() == 0) return false;

        Piece p = pieceL.get(0);
        this.pieces.add(p);
        this.pos.delPiece(p);
        this.notifyObservers();
        return true;
    }

    /**
     * Pose une piece possedee par la personnage sur la case ou il se situe
     * @return vrai si action possible, false sinon
     */
    public boolean poserPiece() {
        if (this.pos.getSable() > 1) return false;

        if (this.pieces.size() == 0) return false;

        Piece p = pieces.get(0);
        this.pieces.remove(0);
        this.pos.addPiece(p);
        this.notifyObservers();
        return true;
    }

    /**
     * Donne de l'eau a un personnage selon la quantite indiquee
     * @param quantite -> int
     * @param cible -> Personnage
     * @return vrai si action possible, false sinon
     */
    public boolean donneEau(int quantite, Personnage cible) {
        if (! this.pos.getJoueurs().contains(cible)) return false;

        if (cible.getEau() == cible.getMaxEau()) return false;

        if (this.eau <= 0) return false;

        int i = 0;
        while (i < quantite && this.eau > 0 && cible.getEau() < cible.getMaxEau()) {
            this.boire();
            cible.remplirGourde(1);
            ++i;
        }

        return true;
    }


}
