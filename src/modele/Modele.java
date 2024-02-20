package modele;

import modele.cartes.TasDeCarte;
import modele.cases.PisteDecollage;
import modele.cases.Tunnel;
import modele.enums.Dir;
import modele.enums.Piece;
import modele.enums.TypeCarteEquipement;
import modele.enums.TypePaquet;
import modele.joueurs.Personnage;

import java.util.ArrayList;

public class Modele extends Observable {
    public static final int DESERT_DIM = 5;

    private ArrayList<Personnage> personnages;
    private final Plateau plateau;
    private PisteDecollage piste;
    private TasDeCarte tdd;
    private TasDeCarte equips;
    private double tempete_niveau;

    // Constructeur utilise pour le jeu
    public Modele(int nbJoueur){
        this.tempete_niveau = 2.0;
        this.personnages = new ArrayList<>();
        this.piste = null; // Initialisé dans le constructeur de Plateau
        this.plateau = new Plateau(this, DESERT_DIM, DESERT_DIM, nbJoueur);
        this.tdd = new TasDeCarte(this, TypePaquet.TEMPETE);
        this.equips = new TasDeCarte(this, TypePaquet.EQUIPEMENT);
    }

    // Constructeur utilise pour realiser des tests
    public Modele(){
        this.plateau = new Plateau(this, DESERT_DIM, DESERT_DIM);
        this.tempete_niveau = 2.0;
        this.personnages = new ArrayList<>();
        this.piste = null;
        this.tdd = null;
        this.equips = null;
    }

    // Renvoie le plateau associe au Modele
    public Plateau getPlateau(){
        return this.plateau;
    }

    // Renvoie la tas de cartes tempetes
    public TasDeCarte getTdd() { return this.tdd; }

    // Renvoie le tas de cartes equipements
    public TasDeCarte getEquips() { return this.equips; }

    // Renvoie les personnages du Modele
    public ArrayList<Personnage> getPersonnages() {
        return personnages;
    }

    // Renvoie le niveau de la tempete du Modele
    public double getTempete_niveau() { return this.tempete_niveau; }

    // Renvoie la case Piste de Decollage du modele
    public PisteDecollage getPiste() { return this.piste; }

    /** Initialise la piste de decollage du Modele
     * Souleve une erreur si la piste est deja initialisee
     * Est uniquement utilisee dans le constructeur de Plateau
     * @param pisteDel -> PisteDecollage
     */
    public void setPiste(PisteDecollage pisteDel) {
        if (this.piste != null) {
            throw new RuntimeException("La piste de decollage est deja initialisee !");
        }

        this.piste = pisteDel;
    }

    // Augmente le niveau de la tempete
    public void tempeteDechaine() {
        tempete_niveau += 0.5;
        this.notifyObservers();
    }

    /** Applique le souffle du vent
     * Deplace les cases en fonctions de l'oeil, de la force et de la direction du vent
     * @param f -> int
     * @param d -> Dir (enum)
     */
    public void ventSouffle(int f, Dir d) {
        this.plateau.souffle(f, d);
    }

    /**
     * Les personnages du modele boivent
     * A l'exception des personnages places dans des tunnels
     * ou ceux equipe d'un bouclier solaire
     */
    public void vagueChaleur(){
        for (Personnage p : this.personnages){

            if (p.possede(TypeCarteEquipement.BOUCLIER)) continue;

            if (p.getPos() instanceof Tunnel && p.getPos().estExplore()) continue;

            p.boire();

        }
    }

    /**
     * Ajoute un personnage dans le modele
     * @param pers -> Personnage
     */
    public void addPers(Personnage pers) { this.personnages.add(pers); }

    /**
     * Renvoie le Personnage d'id id du Modele
     * @param id -> int
     * @return Personnage
     */
    public Personnage getPers(int id){

        for (Personnage pers : this.personnages) {
            if (pers.getId() == id) {
                return pers;
            }
        }

        throw new RuntimeException("Le personnage d'id " + id + " n'est pas dans le modele");

    }

    // Renvoie vrai si la voictoire est valide, false sinon
    public boolean victoire() {
        if (! this.piste.estExplore()) return false;

        if (this.piste.getSable() > 1) return false;

        if (this.piste.getJoueurs().size() != this.personnages.size()) return false;

        ArrayList<Piece> pieces = this.piste.getPieces();

        if (pieces.size() != 4) return false;

        if (! pieces.contains(Piece.CRISTAL)) return false;
        if (! pieces.contains(Piece.BOITE)) return false;
        if (! pieces.contains(Piece.HELICE)) return false;
        if (! pieces.contains(Piece.SYSTEME)) return false;

        return true;
    }

    // Renvoie vrai si la defaite est valide, false sinon
    public boolean defaite() {

        if (Plateau.lim_sable < this.plateau.totalSable()) return true;

        if (this.tempete_niveau >= 7.0) return true;

        for (Personnage p : this.personnages ) if (p.getEau() < 0) return true;

        return false;
    }

}

