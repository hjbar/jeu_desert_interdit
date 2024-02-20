package test;

import modele.*;
import modele.cases.*;
import modele.enums.Dir;
import modele.enums.Piece;
import org.junit.Before;
import org.junit.Test;

import static modele.Modele.DESERT_DIM;
import static org.junit.Assert.*;

public class PlateauTest {

    Modele mod = new Modele();
    Modele mod2 = new Modele(5);

    Plateau plat;
    Plateau plat2;

    @Before
    public void initPlateau() {
        plat = mod.getPlateau();
        plat2 = mod2.getPlateau();
    }

    // Test constructeur
    @Test
    public void plateau0() {
        Coord co = plat.getEye();
        int x = co.getX();
        int y = co.getY();

        assertEquals(x, 2);
        assertEquals(y, 2);

        assertEquals(plat.getTailleX(), DESERT_DIM);
        assertEquals(plat.getTailleY(), DESERT_DIM);

        int cpt = 0;
        for (int i = 0; i < DESERT_DIM; i++) {
            for (int j = 0; j < DESERT_DIM; j++) {
                if (! (i == 2 && j == 2)) {
                    Case c = plat.getCase(i, j);
                    cpt += c.getSable();
                }
            }
        }

        assertEquals(cpt, 8);
    }

    @Test
    public void plateau1() {
        Coord co = plat2.getEye();
        int x = co.getX();
        int y = co.getY();

        assertEquals(x, 2);
        assertEquals(y, 2);

        assertEquals(plat2.getTailleX(), DESERT_DIM);
        assertEquals(plat2.getTailleY(), DESERT_DIM);

        int cpt = 0;
        for (int i = 0; i < DESERT_DIM; i++) {
            for (int j = 0; j < DESERT_DIM; j++) {
                if (! (i == 2 && j == 2)) {
                    Case c = plat2.getCase(i, j);
                    cpt += c.getSable();
                }
            }
        }

        assertEquals(cpt, 8);
    }

    @Test
    public void plateau2() {
        int cptOasis = 0;
        int cptMirage = 0;
        int cptIndice = 0;
        int cptTunnel = 0;
        int cptCrash = 0;
        int cptCase = 0;
        int cptPiste = 0;
        int cptEye = 0;

        for (int i = 0; i < DESERT_DIM; i++) {
            for (int j = 0; j < DESERT_DIM; j++) {

                if ( i == 2 && j == 2 ) cptEye++;
                else {
                    Case c = plat2.getCase(i, j);
                    if (c instanceof Oasis) cptOasis++;
                    else if (c instanceof Mirage) cptMirage++;
                    else if (c instanceof Indice) cptIndice++;
                    else if (c instanceof Tunnel) cptTunnel++;
                    else if (c instanceof CaseCrash) cptCrash++;
                    else if (c instanceof PisteDecollage) cptPiste++;
                    else if (c instanceof Case) cptCase++;

                }

            }
        }

        assertEquals(cptOasis, 2);
        assertEquals(cptMirage, 1);
        assertEquals(cptIndice, 8);
        assertEquals(cptTunnel, 3);
        assertEquals(cptCrash, 1);
        assertEquals(cptCase, 8);
        assertEquals(cptPiste, 1);
        assertEquals(cptEye, 1);
    }

    @Test
    public void plateau3() {
        int cptHelice = 0;
        int cptBoite = 0;
        int cptCristal = 0;
        int cptSysteme = 0;

        for (int i = 0; i < DESERT_DIM; i++) {
            for (int j = 0; j < DESERT_DIM; j++) {

                if (! (i == 2 && j == 2) ) {

                    Case c = plat2.getCase(i, j);

                    if (c instanceof Indice) {

                        Indice ind = (Indice) c;

                        if (ind.getPiece() == Piece.BOITE) cptBoite++;
                        else if (ind.getPiece() == Piece.HELICE) cptHelice++;
                        else if (ind.getPiece() == Piece.SYSTEME) cptSysteme++;
                        else if (ind.getPiece() == Piece.CRISTAL) cptCristal++;

                    }

                }

            }
        }

        assertEquals(cptHelice, 2);
        assertEquals(cptBoite, 2);
        assertEquals(cptCristal, 2);
        assertEquals(cptSysteme, 2);

    }

    // Test souffle
    @Test
    public void souffle0() {
        Case c22 = plat2.getCase(2, 2);
        Case c12 = plat2.getCase(1, 2);
        Case c02 = plat2.getCase(0, 2);

        plat2.souffle(2, Dir.LEFT);

        assertSame(c22, plat2.getCase(0, 2));
        assertSame(c12, plat2.getCase(2, 2));
        assertSame(c02, plat2.getCase(1, 2));

        assertEquals(plat2.getEye().getX(), 0);
        assertEquals(plat2.getEye().getY(), 2);
    }

    @Test
    public void souffle1() {
        Case c22 = plat2.getCase(2, 2);
        Case c32 = plat2.getCase(3, 2);
        Case c42 = plat2.getCase(4, 2);

        plat2.souffle(3, Dir.RIGHT);

        assertSame(c22, plat2.getCase(4, 2));
        assertSame(c32, plat2.getCase(2, 2));
        assertSame(c42, plat2.getCase(3, 2));

        assertEquals(plat2.getEye().getX(), 4);
        assertEquals(plat2.getEye().getY(), 2);
    }

    @Test
    public void souffle2() {
        Case c22 = plat2.getCase(2, 2);
        Case c23 = plat2.getCase(2, 1);

        plat2.souffle(1, Dir.UP);

        assertSame(c22, plat2.getCase(2, 1));
        assertSame(c23, plat2.getCase(2, 2));

        assertEquals(plat2.getEye().getX(), 2);
        assertEquals(plat2.getEye().getY(), 1);
    }

    @Test
    public void souffle3() {
        plat2.souffle(1, Dir.UP);

        Case c21 = plat2.getCase(2, 1);
        Case c22 = plat2.getCase(2, 2);
        Case c23 = plat2.getCase(2, 3);
        Case c24 = plat2.getCase(2, 4);

        plat2.souffle(3, Dir.DOWN);

        assertSame(c21, plat2.getCase(2, 4));
        assertSame(c22, plat2.getCase(2, 1));
        assertSame(c23, plat2.getCase(2, 2));
        assertSame(c24, plat2.getCase(2, 3));

        assertEquals(plat2.getEye().getX(), 2);
        assertEquals(plat2.getEye().getY(), 4);
    }

    @Test
    public void souffle4() {
        plat2.souffle(0, Dir.UP);

        assertEquals(plat2.getEye().getX(), 2);
        assertEquals(plat2.getEye().getY(), 2);
    }

    // Test totalSable
    @Test
    public void totalSable0() {
        assertEquals(plat.totalSable(), 8);
        assertEquals(plat2.totalSable(), 8);
    }

    @Test
    public void totalSable1() {
        for (int i = plat2.totalSable(); i < Plateau.lim_sable; i++) plat2.getCase(0, 0).ensabler();

        assertEquals(plat2.totalSable(), Plateau.lim_sable);

        plat2.getCase(0, 0).ensabler();
        assertEquals(plat2.totalSable(), Plateau.lim_sable + 1);
    }


}
