package modele;

import modele.enums.Dir;
import modele.enums.DirExplo;

public class Coord {
    private int x, y;

    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Renvoie l'abscisse de la coordonnee
    public int getX() {
        return this.x;
    }

    // Renvoie l'ordonne de la coordonee
    public int getY() {
        return this.y;
    }

    /** Remplace l'abscisse de la coordonnee
     * @param x -> int
     */
    public void setX(int x) {
        this.x = x;
    }

    /** Remplace l'ordonne de la cordonnee
     * @param y -> int
     */
    public void setY(int y) {
        this.y = y;
    }

    /** Renvoie les coordonnees associees a une direction
     * Souleve une erreur si la direction n'est pas autorisee
     * @param d -> Dir (enum)
     * @return Coord
     */
    public static Coord dirToCoord(Dir d) {

        int dirX = 0, dirY = 0;
        switch (d) {
            case UP:
                dirX = 0;
                dirY = -1;
                break;
            case DOWN:
                dirX = 0;
                dirY = 1;
                break;
            case LEFT:
                dirX = -1;
                dirY = 0;
                break;
            case RIGHT:
                dirX = 1;
                dirY = 0;
                break;
            case CENTER:
                dirX = 0;
                dirY = 0;
                break;
            default:
                throw new IllegalArgumentException("La direction donnée n'est pas valide");
        }
        return new Coord(dirX, dirY);
    }

    /** Renvoie une direction associee a une coordonnee
     * Souleve une erreur si la coordonnee n'est pas autorisee
     * @param c -> Coord
     * @return Dir (enum)
     */
    public static Dir coordToDir(Coord c){
        int x = c.getX();
        int y = c.getY();
        if (x == 1 && y == 0)
            return Dir.RIGHT;
        else if ( x == -1 && y == 0 )
            return Dir.LEFT;
        else if ( x == 0 && y == -1 )
            return Dir.UP;
        else if ( x == 0 && y == 1 )
            return Dir.DOWN;
        else if ( x == 0 && y == 0 )
            return Dir.CENTER;
        else
            throw new IllegalArgumentException("Les coordonnées ne correspondent pas une direction");
    }

    /** Renvoie les coordonnees associees a une direction de l'explorateur
     * Souleve une erreur si la direction n'est pas autorisee
     * @param d -> DirExplo (enum)
     * @return Coord
     */
    public static Coord dirToCoord(DirExplo d) {

        int dirX = 0, dirY = 0;
        switch (d) {
            case HautDroite:
                dirX = 1;
                dirY = -1;
                break;
            case HautGauche:
                dirX = -1;
                dirY = -1;
                break;
            case BasDroite:
                dirX = 1;
                dirY = 1;
                break;
            case BasGauche:
                dirX = -1;
                dirY = 1;
                break;
            default:
                throw new IllegalArgumentException("La direction de l'explorateur donnée n'est pas valide");
        }
        return new Coord(dirX, dirY);
    }

    /** Renvoie une direction de l'exploration associee a une coordonnee
     * Souleve une erreur si la coordonnee n'est pas autorisee
     * @param c -> Coord
     * @return DirExplo (enum)
     */
    public static DirExplo coordToDirExplo(Coord c){
            int x = c.getX();
            int y = c.getY();
            if (x == 1 && y == -1)
                return DirExplo.HautDroite;
            else if ( x == -1 && y == -1 )
                return DirExplo.HautGauche;
            else if ( x == 1 && y == 1 )
                return DirExplo.BasDroite;
            else if ( x == -1 && y == 1 )
                return DirExplo.BasGauche;
            else
                throw new IllegalArgumentException("Les coordonnées ne correspondent pas une direction");
    }

}
