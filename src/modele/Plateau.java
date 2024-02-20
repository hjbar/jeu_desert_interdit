package modele;

import modele.cases.*;
import modele.enums.Dir;
import modele.enums.Piece;
import modele.joueurs.*;

import java.util.ArrayList;
import java.util.Collections;

public class Plateau extends Observable {
    private final Modele modele;

    // l'oeuil de la tempete point de la grille où il n'est pas possible de se déplacer
    private final Coord eye;
    private final Case[][] grille;
    //Il y a au plus 43 tonnes de sables sur le plateau
    public final static int lim_sable = 43;
    private final int nbOasis = 2;
    private final int nbIndice = 8;
    private final int nbTunnel = 3;

    // Constructeur utilise pour le jeu
    public Plateau(Modele modele, int tailleX, int tailleY, int nbJoueur) {

        // On initialise le modele
        this.modele = modele;

        // On initialise l'oeuil de la tempete au centre du plateau
        this.eye = new Coord(2, 2);

        // On initialise la grille
        this.grille = new Case[tailleX][tailleY];

        // L'oeuil de la tempête ne contient aucune donnée
        this.grille[this.eye.getX()][this.eye.getY()] = new Oeil(this, this.eye.getX(), this.eye.getY());

        // On va creer une liste temporaire pour creer le plateau
        // avec les cases places de maniere aleatoire

        ArrayList<Character> tmpList = new ArrayList<>();
        int nbCaseNormal = (tailleX * tailleY) - (this.nbOasis + this.nbIndice + this.nbTunnel + 1 + 1 + 1 + 1);

        for (int i = 0; i < this.nbOasis; i++) { tmpList.add('o'); }
        for (int i = 0; i < this.nbTunnel; i++) { tmpList.add('t'); }
        for (int i = 0; i < nbCaseNormal; i++) { tmpList.add('c'); }
        tmpList.add('h'); tmpList.add('h');
        tmpList.add('b'); tmpList.add('b');
        tmpList.add('e'); tmpList.add('e');
        tmpList.add('s'); tmpList.add('s');
        tmpList.add('d'); tmpList.add('m');
        tmpList.add('r');

        Collections.shuffle(tmpList);

        // Initialisation du plateau
        Character ch;
        for (int i = 0; i < tailleY; ++i) {
            for (int j = 0; j < tailleX; ++j) {

                if ( !(i == eye.getX() && j == eye.getY()) ) {
                    ch = tmpList.get(0);
                    tmpList.remove(0);

                    switch (ch) {

                        case 'c':
                            this.grille[i][j] = new Case(this, j, i);
                            break;

                        case 'o':
                            this.grille[i][j] = new Oasis(this, j, i);
                            break;

                        case 't':
                            this.grille[i][j] = new Tunnel(this, j, i);
                            break;

                        case 'm':
                            this.grille[i][j] = new Mirage(this, j, i);
                            break;

                        case 'h':
                            this.grille[i][j] = new Indice(this, j, i, Piece.HELICE);
                            break;

                        case 'b':
                            this.grille[i][j] = new Indice(this, j, i, Piece.BOITE);
                            break;

                        case 'e':
                            this.grille[i][j] = new Indice(this, j, i, Piece.CRISTAL);
                            break;

                        case 's':
                            this.grille[i][j] = new Indice(this, j, i, Piece.SYSTEME);
                            break;

                        case 'd':
                            PisteDecollage pisteDel = new PisteDecollage(this, j, i);
                            this.grille[i][j] = pisteDel;
                            this.modele.setPiste(pisteDel);
                            break;

                        case 'r':
                            CaseCrash cch = new CaseCrash(this, j, i);
                            this.grille[i][j] = cch;
                            initJoueurs(nbJoueur, cch);
                            break;

                        default:
                            throw new IllegalArgumentException("Mauvais type de case dans l'initialisation du plateau.");

                    }

                }

            }
        }

        // On place le sable du debut de jeu
        this.grille[0][2].ensabler();
        this.grille[1][1].ensabler();
        this.grille[1][3].ensabler();
        this.grille[2][0].ensabler();
        this.grille[2][4].ensabler();
        this.grille[3][1].ensabler();
        this.grille[3][3].ensabler();
        this.grille[4][2].ensabler();

    }

    // Constructeur utilise pour realiser des tests
    public Plateau(Modele modele, int tailleX, int tailleY){

        this.modele = modele;
        // On initialise l'oeuil de la tempete au centre du plateau
        this.eye = new Coord(2, 2);
        // On initialise la grille
        this.grille = new Case[tailleX][tailleY];
        for (int i = 0; i < tailleY; ++i){
            for (int j = 0; j < tailleX; ++j){
                if ( i != eye.getX() || j != eye.getY() )
                    this.grille[i][j] = new Case(this, j, i);
                else
                    // L'oeuil de la tempête ne contient aucune donnée
                    this.grille[i][j] = new Oeil(this, this.eye.getX(), this.eye.getY());
            }
        }

        this.grille[0][2].ensabler();
        this.grille[1][1].ensabler();
        this.grille[1][3].ensabler();
        this.grille[2][0].ensabler();
        this.grille[2][4].ensabler();
        this.grille[3][1].ensabler();
        this.grille[3][3].ensabler();
        this.grille[4][2].ensabler();
    }

    /** Initialise les jouerus du Modele en fonction des paramtres
     * nbJoueurs le nombre de joueurs a ajoute dans le modele
     * Case la case ou commence les joueurs
     * @param nbJoueurs -> int
     * @param pos -> Case
     */
    private void initJoueurs(int nbJoueurs, Case pos) {

        ArrayList<Character> typeJoueur = new ArrayList<>();

        typeJoueur.add('c'); typeJoueur.add('a');
        typeJoueur.add('e'); typeJoueur.add('n');
        typeJoueur.add('p'); typeJoueur.add('m');

        Collections.shuffle(typeJoueur);

        for (int k = 0; k < nbJoueurs; k++) {

            Character ch = typeJoueur.get(0);
            typeJoueur.remove(0);

            switch (ch) {

                case 'c':
                    Archeologue arch = new Archeologue(pos);
                    this.modele.addPers(arch); break;

                case 'a':
                    Alpiniste alpi = new Alpiniste(pos);
                    this.modele.addPers(alpi); break;

                case 'e':
                    Explorateur expl = new Explorateur(pos);
                    this.modele.addPers(expl); break;

                case 'n':
                    Navigatrice navi = new Navigatrice(pos);
                    this.modele.addPers(navi); break;

                case 'p':
                    PorteuseEau port = new PorteuseEau(pos);
                    this.modele.addPers(port); break;

                case 'm':
                    Meteorologue mete = new Meteorologue(pos);
                    this.modele.addPers(mete); break;

                default:
                    throw new RuntimeException("Mauvais type de personnages");

            }

        }

    }

    // Renvoie le largueur du plateau
    public int getTailleX() { return this.grille[0].length; }

    // Renvoie la longueur du plateau
    public int getTailleY() { return this.grille.length; }

    // Renvoie les coordonnees de l'oeil
    public Coord getEye() { return this.eye; }

    /**
     * Renvoie la case de point (x, y)
     * @param x -> int
     * @param y -> int
     * @return
     */
    public Case getCase(int x, int y){
        return this.grille[y][x];
    }

    // Renvoie le modele associe au plateau
    public Modele getModele() { return this.modele; }

    // Surchage pour afficher le plateau
    public String toString(){
        String str = "";
        for (int i = 0; i < this.grille.length; ++i){
            for (int j = 0; j < this.grille[i].length; ++j){
                if ( i != this.eye.getX() || j != this.eye.getY() )
                    str += String.valueOf(this.grille[i][j]);
                else
                    str += '#';
            }
            str += '\n';
        }
        return str;
    }

    /** Methode souffle deplace l'oeuil de la tempete d'un nombre de case f et dans une direction d et des cases sont ensabler
     * f : int -> le nombre de case dont se deplace la tornade. f vaut Entre 1 et 3.
     * d : dir -> la direction du deplacement de l'oeuil.
     */
    public void souffle(int f, Dir d){

        Coord c = Coord.dirToCoord(d);
        int dirX = c.getX(), dirY = c.getY();

        while (f > 0){
            // On decremente la force
            --f;
            // On calcule les nouvelles coordonnes de la tornade
            int newX = this.eye.getX() + dirX;
            int newY = this.eye.getY() + dirY;
            // On s'assure que le deplacement de l'oeuil soit toujours dans le plateau
            if (newX >= 0 && newX < this.getTailleX() && newY >= 0 && newY < this.getTailleY() ){
                // On ensable la case qui va changer de position avec l'oeuil
                this.grille[newY][newX].ensabler();
                // On echange de position l'oeuil de la tempete et la case vers ou se deplace la tempete
                Oeil o = (Oeil) this.getCase(this.eye.getX(), this.eye.getY());
                this.grille[this.eye.getY()][this.eye.getX()] = this.getCase(newX, newY);
                this.grille[this.eye.getY()][this.eye.getX()].setCoord(new Coord(this.eye.getX(), this.eye.getY()));
                this.grille[newY][newX] = o;
                this.eye.setX(newX);
                this.eye.setY(newY);
                this.notifyObservers();
            } else {
                // On peut immediatement arreter l'execution de la methodes
                f = 0;
            }
        }

    }

    // Renvoie la quantite total de sable dans le plateau
    public int totalSable() {
        int cpt = 0;

        for (int i = 0; i < Modele.DESERT_DIM; i++) {
            for (int j = 0; j < Modele.DESERT_DIM; j++) {
                if (! (i == this.getEye().getY() && j == this.getEye().getX() )) cpt += this.getCase(j, i).getSable();
            }
        }

        return cpt;
    }




}


