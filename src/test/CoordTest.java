package test;

import modele.*;
import modele.enums.Dir;
import modele.enums.DirExplo;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CoordTest {

    Coord c0;
    Coord c1;

    @Before
    public void initCoord() {
        c0 = new Coord(0, 0);
        c1 = new Coord(2, 4);
    }

    // Test constructeur
    @Test
    public void coord0() {
        assertEquals(c0.getX(), 0);
        assertEquals(c0.getY(), 0);

        assertEquals(c1.getX(), 2);
        assertEquals(c1.getY(), 4);
    }

    // Test set
    @Test
    public void set0() {
        c0.setX(5);
        assertEquals(c0.getX(), 5);

        c0.setY(3);
        assertEquals(c0.getY(), 3);

        c0.setX(2);
        assertEquals(c0.getX(), 2);
    }

    // Test de dirToCoord
    @Test
    public void dirToCoord0() {
        Coord c = Coord.dirToCoord(Dir.UP);
        assertEquals(c.getX(), 0);
        assertEquals(c.getY(), -1);
    }

    @Test
    public void dirToCoord1() {
        Coord c = Coord.dirToCoord(Dir.DOWN);
        assertEquals(c.getX(), 0);
        assertEquals(c.getY(), 1);
    }

    @Test
    public void dirToCoord2() {
        Coord c = Coord.dirToCoord(Dir.LEFT);
        assertEquals(c.getX(), -1);
        assertEquals(c.getY(), 0);
    }

    @Test
    public void dirToCoord3() {
        Coord c = Coord.dirToCoord(Dir.RIGHT);
        assertEquals(c.getX(), 1);
        assertEquals(c.getY(), 0);
    }

    @Test
    public void dirToCoord4() {
        Coord c = Coord.dirToCoord(Dir.CENTER);
        assertEquals(c.getX(), 0);
        assertEquals(c.getY(), 0);
    }

    // Test coordToDir
    @Test
    public void CoordToDir0() {
        Dir d = Coord.coordToDir(new Coord(1, 0));
        assertEquals(d, Dir.RIGHT);
    }

    @Test
    public void CoordToDir1() {
        Dir d = Coord.coordToDir(new Coord(-1, 0));
        assertEquals(d, Dir.LEFT);
    }

    @Test
    public void CoordToDir2() {
        Dir d = Coord.coordToDir(new Coord(0, 0));
        assertEquals(d, Dir.CENTER);
    }

    @Test
    public void CoordToDir3() {
        Dir d = Coord.coordToDir(new Coord(0, -1));
        assertEquals(d, Dir.UP);
    }

    @Test
    public void CoordToDir4() {
        Dir d = Coord.coordToDir(new Coord(0, 1));
        assertEquals(d, Dir.DOWN);
    }

    // Test de dirToCoord pour Explorateur
    @Test
    public void dirToCoordExplo0() {
        Coord c = Coord.dirToCoord(DirExplo.HautDroite);
        assertEquals(c.getX(), 1);
        assertEquals(c.getY(), -1);
    }

    @Test
    public void dirToCoordExplo1() {
        Coord c = Coord.dirToCoord(DirExplo.HautGauche);
        assertEquals(c.getX(), -1);
        assertEquals(c.getY(), -1);
    }

    @Test
    public void dirToCoordExplo2() {
        Coord c = Coord.dirToCoord(DirExplo.BasDroite);
        assertEquals(c.getX(), 1);
        assertEquals(c.getY(), 1);
    }

    @Test
    public void dirToCoordExplo() {
        Coord c = Coord.dirToCoord(DirExplo.BasGauche);
        assertEquals(c.getX(), -1);
        assertEquals(c.getY(), 1);
    }

}
