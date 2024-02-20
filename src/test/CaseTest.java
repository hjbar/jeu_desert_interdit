package test;

import modele.*;
import modele.cases.*;
import modele.enums.Dir;
import modele.enums.DirExplo;
import modele.enums.Piece;
import modele.joueurs.Alpiniste;
import modele.joueurs.Personnage;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static modele.Modele.DESERT_DIM;

import static org.junit.Assert.*;

public class CaseTest {
    Modele mod = new Modele();
    Modele mod2 = new Modele(2);
    Modele mod3 = new Modele();

    Plateau plat = mod.getPlateau();
    Plateau plat2 = mod2.getPlateau();
    Plateau plat3 = mod3.getPlateau();

    Case c0;
    Case c1;
    Case c2;
    Case c3;
    Case c4;

    CaseCrash cc0;
    Indice i0;
    Indice i1;
    Oasis o0;
    PisteDecollage pd0;
    Tunnel t0;

    Personnage p0;
    Personnage p1;

    @Before
    public void initCase() {
        c0 = plat.getCase(0, 0);
        c1 = plat.getCase(1, 3);
        c2 = plat.getCase(3, 3);
        c3 = plat.getCase(4, 4);
        c4 = plat3.getCase(3, 3);

        cc0 = new CaseCrash(plat, 2, 4);
        o0 = new Oasis(plat, 2, 1);
        pd0 = new PisteDecollage(plat, 1, 0);
        t0 = new Tunnel(plat, 0, 1);

        p0 = new Personnage(c2);
        p1 = new Personnage(c2);


        boolean flag = false;
        for (int j = 0; j < DESERT_DIM; j++) {
            for (int i = 0; i < DESERT_DIM; i++) {
                Case c = plat2.getCase(i, j);
                if (c instanceof Indice && ((Indice) c).getPiece() == Piece.BOITE) {
                    if (!flag) {
                        i0 = (Indice) plat2.getCase(i, j);
                        flag = true;
                    }
                    else {
                        i1 = (Indice) plat2.getCase(i, j);
                    }
                }
            }
        }


    }

    // Test constructeur
    @Test
    public void case0() {
        ArrayList<Personnage> videPerso = new ArrayList<>();
        ArrayList<Piece> videPiece = new ArrayList<>();

        assertEquals(c0.toString(), "0");
        assertEquals(c0.getSable(), 0);
        assertEquals(c0.getX(), 0);
        assertEquals(c0.getY(), 0);
        assertFalse(c0.estExplore());
        assertEquals(c0.getJoueurs(), videPerso);
        assertEquals(c0.getPieces(), videPiece);
    }

    @Test
    public void case1() {
        ArrayList<Personnage> videPerso = new ArrayList<>();
        ArrayList<Piece> videPiece = new ArrayList<>();

        assertEquals(c1.toString(), "1");
        assertEquals(c1.getSable(), 1);
        assertEquals(c1.getX(), 1);
        assertEquals(c1.getY(), 3);
        assertFalse(c1.estExplore());
        assertEquals(c1.getJoueurs(), videPerso);
        assertEquals(c1.getPieces(), videPiece);
    }

    // Test getPlat
    @Test
    public void getPlat0() {
        assertSame(plat, c0.getPlateau());
        assertSame(plat2, i0.getPlateau());
    }

    @Test
    public void getPlat1() {
        assertNotSame(plat2, c0.getPlateau());
        assertNotSame(plat, i0.getPlateau());
    }

    // Test setCoord
    @Test
    public void setCoord0() {
        assertEquals(c0.getX(), 0);
        assertEquals(c0.getY(), 0);

        c0.setCoord(new Coord(2, 2));

        assertEquals(c0.getX(), 2);
        assertEquals(c0.getY(), 2);
    }

    @Test
    public void setCoord1() {
        assertEquals(c1.getX(), 1);
        assertEquals(c1.getY(), 3);

        c1.setCoord(new Coord(2, 2));

        assertEquals(c1.getX(), 2);
        assertEquals(c1.getY(), 2);
    }

    // Test addJoueur
    @Test
    public void addJoueur0() {
        assertEquals(c0.getJoueurs().size(), 0);

        c0.addJoueur(p0);
        assertEquals(c0.getJoueurs().size(), 1);

        c0.addJoueur(p1);
        assertEquals(c0.getJoueurs().size(), 2);
    }

    // Test delJoueur
    @Test
    public void delJoueur0() {
        c0.addJoueur(p0);
        c0.addJoueur(p1);
        assertEquals(c0.getJoueurs().size(), 2);

        c0.delJoueur(p0);
        assertEquals(c0.getJoueurs().size(), 1);

        c0.delJoueur(p1);
        assertEquals(c0.getJoueurs().size(), 0);
    }

    // Test addPiece
    @Test
    public void addPiece0() {
        assertEquals(c0.getPieces().size(), 0);

        c0.addPiece(Piece.CRISTAL);

        assertEquals(c0.getPieces().size(), 1);

        c0.addPiece(Piece.SYSTEME);

        assertEquals(c0.getPieces().size(), 2);
    }

    // Test delPiece
    @Test
    public void delPiece0() {
        c0.addPiece(Piece.CRISTAL);
        c0.addPiece(Piece.SYSTEME);
        assertEquals(c0.getPieces().size(), 2);

        c0.delPiece(Piece.CRISTAL);
        assertEquals(c0.getPieces().size(), 1);

        c0.delPiece(Piece.SYSTEME);
        assertEquals(c0.getPieces().size(), 0);
    }

    // Test ensabler
    @Test
    public void ensabler0() {
        assertEquals(c0.getSable(), 0);

        for(int i = 1; i <= 5; i++) {
            c0.ensabler();
            assertEquals(c0.getSable(), i);
        }

        assertEquals(c0.getSable(), 5);
    }

    // Test desabler
    @Test
    public void desabler0() {
        for(int i = 1; i <= 5; i++) { c0.ensabler(); }
        assertEquals(c0.getSable(), 5);

        c0.desabler();
        assertEquals(c0.getSable(), 4);

        for(int i = 3; i >= 0; i--) {
            c0.desabler();
            assertEquals(c0.getSable(), i);
        }

        assertEquals(c0.getSable(), 0);
    }

    // Test explore
    @Test
    public void explore0() {
        assertFalse(c0.estExplore());
        assertFalse(c1.estExplore());

        c0.Explore();
        assertTrue(c0.estExplore());

        c0.Explore();
        assertTrue(c0.estExplore());

        assertFalse(c1.estExplore());

        // c1 possede du sable
        c1.Explore();
        assertFalse(c1.estExplore());

        assertTrue(c0.estExplore());
    }

    // Test voisine
    @Test
    public void voisine0() {
        Case cc0 = c0.voisine(Dir.RIGHT);

        assertEquals(cc0.getX(), 1);
        assertEquals(cc0.getY(), 0);

        Case cc1 = c0.voisine(Dir.DOWN);

        assertEquals(cc1.getX(), 0);
        assertEquals(cc1.getY(), 1);
    }

    @Test
    public void voisine1() {
        Case cc0 = c2.voisine(Dir.LEFT);

        assertEquals(cc0.getX(), 2);
        assertEquals(cc0.getY(), 3);

        Case cc1 = c2.voisine(Dir.UP);

        assertEquals(cc1.getX(), 3);
        assertEquals(cc1.getY(), 2);
    }

    @Test
    public void voisine2() {
        try {
            c0.voisine(Dir.LEFT);
            assertTrue(false);
        }
        catch (RuntimeException e) {
            assertTrue(true);
        }

        try {
            c0.voisine(Dir.UP);
            assertTrue(false);
        }
        catch (RuntimeException e) {
            assertTrue(true);
        }

    }

    @Test
    public void voisine3() {
        try {
            c3.voisine(Dir.RIGHT);
            assertTrue(false);
        }
        catch (RuntimeException e) {
            assertTrue(true);
        }

        try {
            c3.voisine(Dir.DOWN);
            assertTrue(false);
        }
        catch (RuntimeException e) {
            assertTrue(true);
        }

    }

    @Test
    public void voisine4() {
        Case sameC = c0.voisine(Dir.CENTER);

        assertEquals(sameC.getX(), c0.getX());
        assertEquals(sameC.getY(), c0.getY());
        assertEquals(sameC.getSable(), c0.getSable());
        assertEquals(sameC.getJoueurs(), c0.getJoueurs());
        assertEquals(sameC.getPieces(), c0.getPieces());
        assertEquals(sameC.toString(), c0.toString());
        assertEquals(sameC.estExplore(), c0.estExplore());

    }

    // Test de CaseCrash
    @Test
    public void crash0() {
        assertTrue(cc0.estExplore());
    }

    // Test de Oasis
    @Test
    public void oasis0() {
        Personnage tmpP0 = new Personnage(o0);
        Personnage tmpP1 = new Personnage(o0);

        for(int i = 0; i < 4; i++) {
            tmpP0.boire();
            tmpP1.boire();
        }

        assertEquals(tmpP0.getEau(), 0);
        assertEquals(tmpP1.getEau(), 0);

        o0.Explore();

        assertEquals(tmpP0.getEau(), 2);
        assertEquals(tmpP1.getEau(), 2);

        o0.Explore();

        assertEquals(tmpP0.getEau(), 2);
        assertEquals(tmpP1.getEau(), 2);
    }

    @Test
    public void oasis1() {
        Personnage tmpP0 = new Personnage(o0);
        Personnage tmpP1 = new Personnage(o0);

        assertEquals(tmpP0.getEau(), 4);
        assertEquals(tmpP1.getEau(), 4);

        o0.Explore();

        assertEquals(tmpP0.getEau(), 4);
        assertEquals(tmpP1.getEau(), 4);
    }

    @Test
    public void oasis2() {
        Personnage tmpP0 = new Personnage(o0);
        Personnage tmpP1 = new Personnage(o0);

        tmpP0.boire();
        tmpP1.boire();

        assertEquals(tmpP0.getEau(), 3);
        assertEquals(tmpP1.getEau(), 3);

        o0.Explore();

        assertEquals(tmpP0.getEau(), 4);
        assertEquals(tmpP1.getEau(), 4);
    }

    // Test de piste
    @Test
    public void piste0() {
        assertTrue(pd0 instanceof PisteDecollage);
    }

    // Test de tunnel
    @Test
    public void tunnel0() {
        ArrayList<Tunnel> vide = new ArrayList<>();
        assertEquals(t0.TunnelAccessible(), vide);

        ArrayList<Tunnel> lt = new ArrayList<>();
        lt.add(t0);

        t0.Explore();
        assertEquals(t0.TunnelAccessible(), lt);

        t0.ensabler();
        t0.ensabler();
        assertEquals(t0.TunnelAccessible(), vide);

        t0.desabler();
        assertEquals(t0.TunnelAccessible(), lt);
    }

    // Test de indice
    @Test
    public void indice0() {
        assertEquals(i0.getPiece(), Piece.BOITE);
        assertEquals(i1.getPiece(), Piece.BOITE);
        assertNotSame(i0, i1);
        assertTrue(i0 instanceof Indice);
        assertTrue(i1 instanceof Indice);
        if (i0.getSable() != 0) i0.desabler();
        if (i1.getSable() != 0) i1.desabler();

        i0.Explore();
        assertTrue(i0.estExplore());

        for (int j = 0; j < DESERT_DIM; j++) {
            for (int i = 0; i < DESERT_DIM; i++) {
                Case c = plat2.getCase(i, j);
                if (i != 2 && j != 2)
                assertEquals(c.getPieces().size(), 0);
            }
        }

        i1.Explore();
        assertTrue(i1.estExplore());

        int cpt = 0;
        for (int j = 0; j < DESERT_DIM; j++) {
            for (int i = 0; i < DESERT_DIM; i++) {
                Case c = plat2.getCase(i, j);
                if (!(i == 2 && j == 2) && c.getPieces().size() >= 1) {
                    assertEquals(c.getPieces().size(), 1);
                    cpt++;
                }
            }
        }

        assertEquals(cpt, 1);
    }

    // Test voisineDirExplo
    @Test
    public void voisineExplo0() {
        try {
            Case cc0 = c0.voisine(DirExplo.HautDroite);
            assertTrue(false);
        }
        catch (RuntimeException e) {
            assertTrue(true);
        }
    }

    @Test
    public void voisineExplo1() {
        try {
            Case cc0 = c0.voisine(DirExplo.BasGauche);
            assertTrue(false);
        }
        catch (RuntimeException e) {
            assertTrue(true);
        }
    }

    @Test
    public void voisineExplo2() {
        try {
            Case cc0 = c0.voisine(DirExplo.BasGauche);
            assertTrue(false);
        }
        catch (RuntimeException e) {
            assertTrue(true);
        }
    }

    @Test
    public void voisineExplo3() {
        Case cc1 = c1.voisine(DirExplo.HautGauche);

        assertEquals(cc1.getX(), 0);
        assertEquals(cc1.getY(), 2);
    }

    @Test
    public void voisineExplo4() {
        try {
            Case cc1 = c1.voisine(DirExplo.HautDroite);
            assertTrue(false);
        }
        catch (RuntimeException e) {
            assertTrue(true);
        }
    }

    @Test
    public void voisineExplo5() {
        Case cc1 = c1.voisine(DirExplo.BasDroite);

        assertEquals(cc1.getX(), 2);
        assertEquals(cc1.getY(), 4);
    }

    @Test
    public void voisineExplo6() {
        Case cc1 = c1.voisine(DirExplo.BasGauche);

        assertEquals(cc1.getX(), 0);
        assertEquals(cc1.getY(), 4);
    }

    @Test
    public void voisineExplo7() {
        Case cc2 = c2.voisine(DirExplo.HautDroite);

        assertEquals(cc2.getX(), 4);
        assertEquals(cc2.getY(), 2);
    }

    // Test haveAlpi
    @Test
    public void haveAlpi0() {
        assertFalse(c4.haveAlpiniste());

        Alpiniste tmp = new Alpiniste(c4);

        assertTrue(c4.haveAlpiniste());

        tmp.deplacer(Dir.DOWN);

        assertFalse(c4.haveAlpiniste());
    }

    @Test
    public void haveAlpi1() {
        assertFalse(c4.haveAlpiniste());

        Alpiniste tmp = new Alpiniste(c4);
        Alpiniste tmp2 = new Alpiniste(c4);

        assertTrue(c4.haveAlpiniste());

        tmp.deplacer(Dir.DOWN);

        assertTrue(c4.haveAlpiniste());
    }

}
