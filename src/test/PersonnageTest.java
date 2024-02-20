package test;

import modele.*;
import modele.cartes.Carte;
import modele.cartes.CarteEquipement;
import modele.cases.Case;
import modele.cases.CaseCrash;
import modele.cases.Oasis;
import modele.enums.Dir;
import modele.enums.DirExplo;
import modele.enums.Piece;
import modele.enums.TypeCarteEquipement;
import modele.joueurs.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static modele.Modele.DESERT_DIM;
import static org.junit.Assert.*;

public class PersonnageTest {

    Modele mod = new Modele();
    Modele mod2 = new Modele();
    Modele mod3 = new Modele(5);
    Modele mod4 = new Modele();
    Modele mod5 = new Modele(0);

    Plateau plat = mod.getPlateau();
    Plateau plat2 = mod2.getPlateau();
    Plateau plat3 = mod3.getPlateau();
    Plateau plat4 = mod4.getPlateau();
    Plateau plat5 = mod5.getPlateau();

    Case c0 = plat.getCase(1, 3);
    Case c1 = plat.getCase(0, 0);
    Case c2 = plat.getCase(4, 4);
    Case c3 = plat.getCase(1, 0);
    Case c4 = plat.getCase(3, 4);
    Case c5 = plat.getCase(1, 1);
    Case c6 = plat2.getCase(1, 1);
    Case c7 = plat4.getCase(3, 3);
    Case c8 = plat4.getCase(4, 4);
    Case c9 = plat4.getCase(0, 0);
    Case c10 = plat4.getCase(4, 4);
    Case c11 = plat4.getCase(1, 0);

    Oasis o0;

    Personnage p0;
    Personnage p1;
    Personnage p2;
    Personnage p3;
    Personnage p4;
    Personnage p5;
    Archeologue a0;
    Explorateur e0;
    PorteuseEau pe0;
    PorteuseEau pe1;
    Alpiniste al0;
    Navigatrice n0;
    Navigatrice n1;
    Navigatrice n2;

    @Before
    public void initPersonnage() {
        p0 = new Personnage(c0);
        p1 = new Personnage(c1);
        p2 = new Personnage(c2);
        p3 = new Personnage(c3);
        p4 = new Personnage(c4);
        a0 = new Archeologue(c5);
        e0 = new Explorateur(c6);

        for (int i = 0; i < DESERT_DIM; i++) {
            for (int j = 0; j < DESERT_DIM; j++) {
                if (! (i == 2 && j == 2) ) {
                    Case c = plat3.getCase(j ,i);
                    if (c instanceof Oasis) o0 = (Oasis) c;
                    if (o0 != null) break;
                }
            }
        }

        pe0 = new PorteuseEau(o0);
        pe1 = new PorteuseEau(c8);
        al0 = new Alpiniste(c7);
        n0 = new Navigatrice(c9);
        n1 = new Navigatrice(c10);
        n2 = new Navigatrice(c11);

        for (int i = 0; i < DESERT_DIM; i++) {
            for (int j = 0; j < DESERT_DIM; j++) {
                Case c = plat5.getCase(i, j);
                if (c instanceof CaseCrash) {
                    p5 = new Personnage(c);
                    break;
                }
            }
        }

    }

    // Test constructeur
    @Test
    public void perso0() {
        Set<CarteEquipement> vide = new HashSet<>();
        ArrayList<Piece> videP = new ArrayList<>();

        assertEquals(p0.getEau(), 4);
        assertEquals(p0.getPos(), c0);
        assertEquals(p0.getPos().getX(), 1);
        assertEquals(p0.getPos().getY(), 3);
        assertEquals(p0.getBag().toString(), vide.toString());
        assertEquals(p0.getPieces(), videP);
    }

    // Test setCoord
    @Test
    public void setCoord0() {
        Case cTmp = plat.getCase(3, 3);

        assertEquals(c0.getJoueurs().size(), 1);
        assertEquals(cTmp.getJoueurs().size(), 0);
        assertSame(c0, p0.getPos());
        assertNotSame(cTmp, p0.getPos());

        p0.setCoord(new Coord(3, 3));

        assertEquals(c0.getJoueurs().size(), 0);
        assertEquals(cTmp.getJoueurs().size(), 1);
        assertNotSame(c0, p0.getPos());
        assertSame(cTmp, p0.getPos());

    }

    // Tests deplacer
    @Test
    public void deplacer0() {
        int size = p0.getPos().getJoueurs().size();
        boolean res = p0.deplacer(Dir.UP);

        assertTrue(res);
        assertNotSame(p0.getPos(), c0);
        assertEquals(c0.getJoueurs().size(), size - 1);
        assertEquals(p0.getPos().getJoueurs().size(), 1);
        assertEquals(p0.getPos().getSable(), 0);
        assertEquals(p0.getPos().getX(), 1);
        assertEquals(p0.getPos().getY(), 2);
    }

    @Test
    public void deplacer1() {
        int size = p0.getPos().getJoueurs().size();
        boolean res = p0.deplacer(Dir.LEFT);

        assertTrue(res);
        assertNotSame(p0.getPos(), c0);
        assertEquals(c0.getJoueurs().size(), size - 1);
        assertEquals(p0.getPos().getJoueurs().size(), 1);
        assertEquals(p0.getPos().getSable(), 0);
        assertEquals(p0.getPos().getX(), 0);
        assertEquals(p0.getPos().getY(), 3);
    }

    @Test
    public void deplacer2() {
        int size = p0.getPos().getJoueurs().size();
        boolean res = p0.deplacer(Dir.RIGHT);

        assertTrue(res);
        assertNotSame(p0.getPos(), c0);
        assertEquals(c0.getJoueurs().size(), size - 1);
        assertEquals(p0.getPos().getJoueurs().size(), 1);
        assertEquals(p0.getPos().getSable(), 0);
        assertEquals(p0.getPos().getX(), 2);
        assertEquals(p0.getPos().getY(), 3);
    }

    @Test
    public void deplacer3() {
        int size = p0.getPos().getJoueurs().size();
        boolean res = p0.deplacer(Dir.DOWN);

        assertTrue(res);
        assertNotSame(p0.getPos(), c0);
        assertEquals(c0.getJoueurs().size(), size - 1);
        assertEquals(p0.getPos().getJoueurs().size(), 1);
        assertEquals(p0.getPos().getSable(), 0);
        assertEquals(p0.getPos().getX(), 1);
        assertEquals(p0.getPos().getY(), 4);
    }

    @Test
    public void deplacer4() {
        int size = p0.getPos().getJoueurs().size();
        boolean res = p1.deplacer(Dir.LEFT);

        assertFalse(res);
        assertSame(p1.getPos(), c1);
        assertEquals(p0.getPos().getJoueurs().size(), size);
        assertEquals(p1.getPos().getSable(), 0);
        assertEquals(p1.getPos().getX(), 0);
        assertEquals(p1.getPos().getY(), 0);

    }

    @Test
    public void deplacer5() {
        int size = p0.getPos().getJoueurs().size();
        boolean res = p1.deplacer(Dir.UP);

        assertFalse(res);
        assertSame(p1.getPos(), c1);
        assertEquals(p0.getPos().getJoueurs().size(), size);
        assertEquals(p1.getPos().getSable(), 0);
        assertEquals(p1.getPos().getX(), 0);
        assertEquals(p1.getPos().getY(), 0);

    }

    @Test
    public void deplacer6() {
        int size = p0.getPos().getJoueurs().size();
        boolean res = p2.deplacer(Dir.DOWN);

        assertFalse(res);
        assertSame(p2.getPos(), c2);
        assertEquals(p0.getPos().getJoueurs().size(), size);
        assertEquals(p2.getPos().getSable(), 0);
        assertEquals(p2.getPos().getX(), 4);
        assertEquals(p2.getPos().getY(), 4);

    }

    @Test
    public void deplacer7() {
        int size = p0.getPos().getJoueurs().size();
        boolean res = p2.deplacer(Dir.RIGHT);

        assertFalse(res);
        assertSame(p2.getPos(), c2);
        assertEquals(p0.getPos().getJoueurs().size(), size);
        assertEquals(p2.getPos().getSable(), 0);
        assertEquals(p2.getPos().getX(), 4);
        assertEquals(p2.getPos().getY(), 4);

    }

    @Test
    public void deplacer8() {
        Case place = p0.getPos();
        assertEquals(place.getSable(), 1);

        place.ensabler();
        assertEquals(place.getSable(), 2);

        boolean res = p0.deplacer(Dir.RIGHT);
        assertFalse(res);
        assertSame(p0.getPos(), place);

    }

    @Test
    public void deplacer9() {
        Case posJ = p0.getPos();
        Case place = p0.getPos().voisine(Dir.RIGHT);
        assertEquals(place.getSable(), 0);

        place.ensabler();
        place.ensabler();
        assertEquals(place.getSable(), 2);

        boolean res = p0.deplacer(Dir.RIGHT);
        assertFalse(res);
        assertSame(p0.getPos(), posJ);

    }

    @Test
    public void deplacer10() {
        Case place = p0.getPos().voisine(Dir.RIGHT);
        assertEquals(place.getSable(), 0);

        place.ensabler();
        assertEquals(place.getSable(), 1);

        boolean res = p0.deplacer(Dir.RIGHT);
        assertTrue(res);
        assertSame(p0.getPos(), place);

    }

    @Test
    public void deplacer11() {
        Case posJ = p0.getPos(); Case place = p0.getPos().voisine(Dir.RIGHT);
        place.ensabler(); place.ensabler();
        assertEquals(place.getSable(), 2);
        boolean res = p0.deplacer(Dir.RIGHT);
        assertFalse(res); assertSame(p0.getPos(), posJ);

        Alpiniste tmp = new Alpiniste(posJ);
        res = p0.deplacer(Dir.RIGHT);
        assertFalse(res);
        assertSame(p0.getPos(), posJ);
        assertNotSame(p0.getPos(), plat.getCase(2, 3));
    }

    @Test
    public void deplacer12() {
        Case place = p0.getPos(); place.ensabler();
        assertEquals(place.getSable(), 2);
        boolean res = p0.deplacer(Dir.RIGHT);
        assertFalse(res); assertSame(p0.getPos(), place);

        Alpiniste tmp = new Alpiniste(place);
        res = p0.deplacer(Dir.RIGHT);
        assertTrue(res);
        assertNotSame(p0.getPos(), place);
        assertSame(p0.getPos(), plat.getCase(2, 3));

    }

    @Test
    public void deplacer13() {
        Case posJ = p0.getPos(); Case place = p0.getPos().voisine(Dir.RIGHT);
        place.ensabler(); place.ensabler();
        assertEquals(place.getSable(), 2);
        boolean res = p0.deplacer(Dir.RIGHT);
        assertFalse(res); assertSame(p0.getPos(), posJ);

        Alpiniste tmp = new Alpiniste(posJ);
        res = p0.deplacer(Dir.RIGHT);
        assertFalse(res);
        assertSame(p0.getPos(), posJ);
        assertNotSame(p0.getPos(), plat.getCase(2, 3));
    }

    @Test
    public void deplacer14() {
        Case place = p0.getPos(); place.ensabler();
        assertEquals(place.getSable(), 2);
        boolean res = p0.deplacer(Dir.RIGHT);
        assertFalse(res); assertSame(p0.getPos(), place);

        Alpiniste tmp = new Alpiniste(place.voisine(Dir.RIGHT));
        res = p0.deplacer(Dir.RIGHT);
        assertFalse(res);
        assertSame(p0.getPos(), place);
        assertNotSame(p0.getPos(), plat.getCase(2, 3));
    }

    @Test
    public void deplacer15() {
        Case place = p0.getPos(); place.ensabler();
        assertEquals(place.getSable(), 2);
        boolean res = p0.deplacer(Dir.RIGHT);
        assertFalse(res); assertSame(p0.getPos(), place);

        Alpiniste tmp = new Alpiniste(place);
        res = p0.deplacer(Dir.RIGHT);
        assertTrue(res);
        assertNotSame(p0.getPos(), place);
        assertSame(p0.getPos(), plat.getCase(2, 3));
    }

    // Tests desabler
    @Test
    public void desabler0() {
        assertEquals(p0.getPos().getSable(), 1);
        boolean res = p0.desabler(Dir.CENTER);

        assertTrue(res);
        assertEquals(p0.getPos().getSable(), 0);
        assertSame(p0.getPos(), c0);
        assertEquals(p0.getPos().getX(), 1);
        assertEquals(p0.getPos().getY(), 3);
    }

    @Test
    public void desabler1() {
        assertEquals(p0.getPos().getSable(), 1);
        boolean res = p0.desabler(Dir.UP);

        assertFalse(res);
        assertEquals(p0.getPos().getSable(), 1);
        assertSame(p0.getPos(), c0);
        assertEquals(p0.getPos().getX(), 1);
        assertEquals(p0.getPos().getY(), 3);
    }

    @Test
    public void desabler2() {
        assertEquals(p0.getPos().getSable(), 1);
        boolean res = p0.desabler(Dir.DOWN);

        assertFalse(res);
        assertEquals(p0.getPos().getSable(), 1);
        assertSame(p0.getPos(), c0);
        assertEquals(p0.getPos().getX(), 1);
        assertEquals(p0.getPos().getY(), 3);
    }

    @Test
    public void desabler3() {
        assertEquals(p0.getPos().getSable(), 1);
        boolean res = p0.desabler(Dir.LEFT);

        assertFalse(res);
        assertEquals(p0.getPos().getSable(), 1);
        assertSame(p0.getPos(), c0);
        assertEquals(p0.getPos().getX(), 1);
        assertEquals(p0.getPos().getY(), 3);
    }

    @Test
    public void desabler4() {
        assertEquals(p0.getPos().getSable(), 1);
        boolean res = p0.desabler(Dir.RIGHT);

        assertFalse(res);
        assertEquals(p0.getPos().getSable(), 1);
        assertSame(p0.getPos(), c0);
        assertEquals(p0.getPos().getX(), 1);
        assertEquals(p0.getPos().getY(), 3);
    }

    @Test
    public void desabler5() {
        assertEquals(p3.getPos().getSable(), 0);
        boolean res = p3.desabler(Dir.CENTER);

        assertFalse(res);
        assertEquals(p3.getPos().getSable(), 0);
        assertSame(p3.getPos(), c3);
        assertEquals(p3.getPos().getX(), 1);
        assertEquals(p3.getPos().getY(), 0);
    }

    @Test
    public void desabler6() {
        assertEquals(p3.getPos().getSable(), 0);
        assertEquals(p3.getPos().voisine(Dir.RIGHT).getSable(), 1);
        boolean res = p3.desabler(Dir.RIGHT);

        assertTrue(res);
        assertEquals(p3.getPos().getSable(), 0);
        assertEquals(p3.getPos().voisine(Dir.RIGHT).getSable(), 0);
        assertSame(p3.getPos(), c3);
        assertEquals(p3.getPos().getX(), 1);
        assertEquals(p3.getPos().getY(), 0);
    }

    @Test
    public void desabler7() {
        assertEquals(p3.getPos().getSable(), 0);
        assertEquals(p3.getPos().voisine(Dir.DOWN).getSable(), 1);
        boolean res = p3.desabler(Dir.DOWN);

        assertTrue(res);
        assertEquals(p3.getPos().getSable(), 0);
        assertEquals(p3.getPos().voisine(Dir.DOWN).getSable(), 0);
        assertSame(p3.getPos(), c3);
        assertEquals(p3.getPos().getX(), 1);
        assertEquals(p3.getPos().getY(), 0);
    }

    @Test
    public void desabler8() {
        assertEquals(p4.getPos().getSable(), 0);
        boolean res = p4.desabler(Dir.CENTER);

        assertFalse(res);
        assertEquals(p4.getPos().getSable(), 0);
        assertSame(p4.getPos(), c4);
        assertEquals(p4.getPos().getX(), 3);
        assertEquals(p4.getPos().getY(), 4);
    }

    @Test
    public void desabler9() {
        assertEquals(p4.getPos().getSable(), 0);
        assertEquals(p4.getPos().voisine(Dir.UP).getSable(), 1);
        boolean res = p4.desabler(Dir.UP);

        assertTrue(res);
        assertEquals(p4.getPos().getSable(), 0);
        assertEquals(p4.getPos().voisine(Dir.UP).getSable(), 0);
        assertSame(p4.getPos(), c4);
        assertEquals(p4.getPos().getX(), 3);
        assertEquals(p4.getPos().getY(), 4);
    }

    @Test
    public void desabler10() {
        assertEquals(p4.getPos().getSable(), 0);
        assertEquals(p4.getPos().voisine(Dir.LEFT).getSable(), 1);
        boolean res = p4.desabler(Dir.LEFT);

        assertTrue(res);
        assertEquals(p4.getPos().getSable(), 0);
        assertEquals(p4.getPos().voisine(Dir.LEFT).getSable(), 0);
        assertSame(p4.getPos(), c4);
        assertEquals(p4.getPos().getX(), 3);
        assertEquals(p4.getPos().getY(), 4);
    }

    // Tests boire
    @Test
    public void boire0() {
        boolean res;

        assertEquals(p0.getEau(), 4);

        for(int i = 3; i >= 0; i--) {
            res = p0.boire();
            assertTrue(res);
            assertEquals(p0.getEau(), i);
        }

        res = p0.boire();
        assertFalse(res);
        assertEquals(p0.getEau(), -1);
    }

    // Tests remplirGourde
    @Test
    public void remplirGourde0() {
        assertEquals(p0.getEau(), 4);

        p0.remplirGourde(4);
        assertEquals(p0.getEau(), 4);

        for(int i = 0; i < 4; i++) {
            p0.boire();
        }
        assertEquals(p0.getEau(), 0);

        p0.remplirGourde(2);
        assertEquals(p0.getEau(), 2);

        p0.remplirGourde(2);
        assertEquals(p0.getEau(), 4);

        p0.remplirGourde(2);
        assertEquals(p0.getEau(), 4);
    }

    // Test explorer
    @Test
    public void explorer0() {
        boolean res;
        assertFalse(p1.getPos().estExplore());

        res = p1.explorer();
        assertTrue(p1.getPos().estExplore());
        assertTrue(res);

        res = p1.explorer();
        assertTrue(p1.getPos().estExplore());
        assertFalse(res);
    }

    @Test
    public void explorer1() {
        boolean res; mod5.addPers(p5);
        Case tmp = p5.getPos();
        assertEquals(p5.getBag().size(), 0);
        assertTrue(p5.getPos().estExplore());

        while(p5.getPos().getSable() > 0) p5.getPos().desabler();
        res = p5.explorer(); assertFalse(res);
        assertEquals(p5.getBag().size(), 0);

        try {
            res = p5.deplacer(Dir.UP);
            if (!res) return;
        }
        catch (RuntimeException e1) {
            try {
                res = p5.deplacer(Dir.DOWN);
                if (!res) return;
            }
            catch (RuntimeException e2) {
                try {
                    res = p5.deplacer(Dir.LEFT);
                    if (!res) return;
                }
                catch (RuntimeException e3) {
                    try {
                        res = p5.deplacer(Dir.RIGHT);
                        if (!res) return;
                    }
                    catch (RuntimeException e4) {
                        return;
                    }
                }
            }
        }

        assertNotSame(tmp, p5.getPos());
        while(p5.getPos().getSable() > 0) p5.getPos().desabler();

        assertFalse(p5.getPos().estExplore());
        res = p5.explorer(); assertTrue(res);
        assertEquals(p5.getBag().size(), 1);

        assertTrue(p5.getPos().estExplore());
        res = p5.explorer(); assertFalse(res);
        assertEquals(p5.getBag().size(), 1);


    }

    // Test nbEquip
    @Test
    public void nbEquip0() {
        assertEquals(p0.nbEquip(), 0);

        CarteEquipement jet0 = new CarteEquipement(mod, TypeCarteEquipement.JETPACK);
        p0.addEquip(jet0);
        assertEquals(p0.nbEquip(), 1);

        CarteEquipement accel0 = new CarteEquipement(mod, TypeCarteEquipement.ACCEL);
        p0.addEquip(accel0);
        assertEquals(p0.nbEquip(), 2);

        p0.delEquip(jet0);
        assertEquals(p0.nbEquip(), 1);

        p0.delEquip(accel0);
        assertEquals(p0.nbEquip(), 0);
    }

    // Test possede
    @Test
    public void possede0() {
        CarteEquipement terra0 = new CarteEquipement(mod, TypeCarteEquipement.TERRASCOPE);

        CarteEquipement jet0 = new CarteEquipement(mod, TypeCarteEquipement.JETPACK);
        p0.addEquip(jet0);
        assertTrue(p0.possede(jet0));
        assertFalse(p0.possede(terra0));

        CarteEquipement accel0 = new CarteEquipement(mod, TypeCarteEquipement.ACCEL);
        p0.addEquip(accel0);
        assertTrue(p0.possede(accel0));
        assertFalse(p0.possede(terra0));

        p0.delEquip(jet0);
        assertFalse(p0.possede(jet0));
        assertTrue(p0.possede(accel0));
        assertFalse(p0.possede(terra0));

        p0.delEquip(accel0);
        assertFalse(p0.possede(accel0));
        assertFalse(p0.possede(terra0));
    }


    //Test addEquip
    @Test
    public void addEquip0() {
        boolean res;

        assertEquals(p0.nbEquip(), 0);

        CarteEquipement jet0 = new CarteEquipement(mod, TypeCarteEquipement.JETPACK);
        res = p0.addEquip(jet0);
        assertTrue(res);

        assertEquals(p0.nbEquip(), 1);

        res = p0.addEquip(jet0);
        assertFalse(res);

        assertEquals(p0.nbEquip(), 1);

        CarteEquipement accel0 = new CarteEquipement(mod, TypeCarteEquipement.ACCEL);
        res = p0.addEquip(accel0);
        assertTrue(res);

        assertEquals(p0.nbEquip(), 2);

        res = p0.addEquip(accel0);
        assertFalse(res);

        assertEquals(p0.nbEquip(), 2);

        CarteEquipement jet1 = new CarteEquipement(mod, TypeCarteEquipement.JETPACK);
        res = p0.addEquip(jet1);
        assertTrue(res);

        assertEquals(p0.nbEquip(), 3);

        CarteEquipement accel1 = new CarteEquipement(mod, TypeCarteEquipement.ACCEL);
        res = p0.addEquip(accel1);
        assertTrue(res);

        assertEquals(p0.nbEquip(), 4);

    }

    //Test delEquip
    @Test
    public void delEquip0() {
        CarteEquipement jet0 = new CarteEquipement(mod, TypeCarteEquipement.JETPACK);
        p0.addEquip(jet0);
        CarteEquipement accel0 = new CarteEquipement(mod, TypeCarteEquipement.ACCEL);
        p0.addEquip(accel0);
        CarteEquipement jet1 = new CarteEquipement(mod, TypeCarteEquipement.JETPACK);
        p0.addEquip(jet1);
        CarteEquipement accel1 = new CarteEquipement(mod, TypeCarteEquipement.ACCEL);
        p0.addEquip(accel1);
        assertEquals(p0.nbEquip(), 4);

        boolean res;

        res = p0.delEquip(jet0);
        assertTrue(res);

        assertEquals(p0.nbEquip(), 3);

        res = p0.delEquip(jet0);
        assertFalse(res);

        assertEquals(p0.nbEquip(), 3);

        res = p0.delEquip(jet1);
        assertTrue(res);

        assertEquals(p0.nbEquip(), 2);

        res = p0.delEquip(accel1);
        assertTrue(res);

        assertEquals(p0.nbEquip(), 1);

        res = p0.delEquip(accel0);
        assertTrue(res);

        assertEquals(p0.nbEquip(), 0);

        res = p0.delEquip(accel1);
        assertFalse(res);

        assertEquals(p0.nbEquip(), 0);

    }

    // Test de prendrePiece
    @Test
    public void prendre0() {
        boolean res;

        assertEquals(c0.getPieces().size(), 0);
        c0.addPiece(Piece.CRISTAL);
        assertEquals(c0.getPieces().size(), 1);

        res = p0.prendrePiece();
        assertEquals(c0.getPieces().size(), 0);
        assertEquals(p0.getPieces().size(), 1);
        assertTrue(res);

        res = p0.prendrePiece();
        assertFalse(res);
    }

    // Test de poserPiece
    @Test
    public void poser0() {
        boolean res;

        assertEquals(p0.getPieces().size(), 0);
        res = p0.poserPiece();
        assertFalse(res);

        c0.addPiece(Piece.CRISTAL);
        p0.prendrePiece();
        assertEquals(c0.getPieces().size(), 0);
        assertEquals(p0.getPieces().size(), 1);

        res = p0.poserPiece();
        assertEquals(c0.getPieces().size(), 1);
        assertEquals(p0.getPieces().size(), 0);
        assertTrue(res);

        res = p0.poserPiece();
        assertEquals(c0.getPieces().size(), 1);
        assertEquals(p0.getPieces().size(), 0);
        assertFalse(res);
    }

    // Test Archeologue
    @Test
    public void archeologue0() {
        Set<CarteEquipement> vide = new HashSet<>();
        ArrayList<Piece> videP = new ArrayList<>();

        assertEquals(a0.getEau(), 4);
        assertEquals(a0.getPos(), c5);
        assertEquals(a0.getPos().getX(), 1);
        assertEquals(a0.getPos().getY(), 1);
        assertEquals(a0.getBag().toString(), vide.toString());
        assertEquals(a0.getPieces(), videP);
    }

    @Test
    public void archeologue1() {
        boolean res;
        assertEquals(c5.getSable(), 1);

        res = a0.desabler(Dir.CENTER);
        assertTrue(res);
        assertEquals(c5.getSable(), 0);

        c5.ensabler(); c5.ensabler();
        res = a0.desabler(Dir.CENTER);
        assertTrue(res);
        assertEquals(c5.getSable(), 0);

        c5.ensabler(); c5.ensabler(); c5.ensabler();
        res = a0.desabler(Dir.CENTER);
        assertTrue(res);
        assertEquals(c5.getSable(), 1);

        res = a0.desabler(Dir.CENTER);
        assertTrue(res);
        assertEquals(c5.getSable(), 0);

        res = a0.desabler(Dir.CENTER);
        assertFalse(res);
        assertEquals(c5.getSable(), 0);
    }

    @Test
    public void archeologue2() {
        boolean res;
        Case place = c5.voisine(Dir.UP);
        assertEquals(place.getSable(), 0);

        res = a0.desabler(Dir.UP);
        assertFalse(res);
        assertEquals(place.getSable(), 0);

        place.ensabler(); place.ensabler(); place.ensabler();
        res = a0.desabler(Dir.UP);
        assertTrue(res);
        assertEquals(place.getSable(), 1);
    }

    @Test
    public void archeologue3() {
        boolean res;
        Case place = c5.voisine(Dir.DOWN);
        assertEquals(place.getSable(), 0);

        res = a0.desabler(Dir.DOWN);
        assertFalse(res);
        assertEquals(place.getSable(), 0);

        place.ensabler(); place.ensabler(); place.ensabler();
        res = a0.desabler(Dir.DOWN);
        assertTrue(res);
        assertEquals(place.getSable(), 1);
    }

    @Test
    public void archeologue4() {
        boolean res;
        Case place = c5.voisine(Dir.RIGHT);
        assertEquals(place.getSable(), 0);

        res = a0.desabler(Dir.RIGHT);
        assertFalse(res);
        assertEquals(place.getSable(), 0);

        place.ensabler(); place.ensabler(); place.ensabler();
        res = a0.desabler(Dir.RIGHT);
        assertTrue(res);
        assertEquals(place.getSable(), 1);
    }

    @Test
    public void archeologue5() {
        boolean res;
        Case place = c5.voisine(Dir.LEFT);
        assertEquals(place.getSable(), 0);

        res = a0.desabler(Dir.LEFT);
        assertFalse(res);
        assertEquals(place.getSable(), 0);

        place.ensabler(); place.ensabler(); place.ensabler();
        res = a0.desabler(Dir.LEFT);
        assertTrue(res);
        assertEquals(place.getSable(), 1);
    }

    // Test Explorateur
    @Test
    public void explo0() {
        Set<CarteEquipement> vide = new HashSet<>();
        ArrayList<Piece> videP = new ArrayList<>();

        assertEquals(e0.getEau(), 4);
        assertEquals(e0.getPos(), c6);
        assertEquals(e0.getPos().getX(), 1);
        assertEquals(e0.getPos().getY(), 1);
        assertEquals(e0.getBag().toString(), vide.toString());
        assertEquals(e0.getPieces(), videP);
    }

    // Tests deplacerExplo
    @Test
    public void deplacerExplo0() {
        int size = e0.getPos().getJoueurs().size();
        boolean res = e0.deplacer(DirExplo.BasGauche);

        assertTrue(res);
        assertNotSame(e0.getPos(), c6);
        assertEquals(c6.getJoueurs().size(), size - 1);
        assertEquals(e0.getPos().getJoueurs().size(), 1);
        assertEquals(e0.getPos().getSable(), 1);
        assertEquals(e0.getPos().getX(), 0);
        assertEquals(e0.getPos().getY(), 2);
    }

    @Test
    public void deplacerExplo1() {
        try {
            boolean res = e0.deplacer(DirExplo.BasDroite);
            assertTrue(false);
        }
        catch (RuntimeException e) {
            assertTrue(true);
        }
    }

    @Test
    public void deplacerExplo2() {
        int size = e0.getPos().getJoueurs().size();
        boolean res = e0.deplacer(DirExplo.HautDroite);

        assertTrue(res);
        assertNotSame(e0.getPos(), c6);
        assertEquals(c6.getJoueurs().size(), size - 1);
        assertEquals(e0.getPos().getJoueurs().size(), 1);
        assertEquals(e0.getPos().getSable(), 1);
        assertEquals(e0.getPos().getX(), 2);
        assertEquals(e0.getPos().getY(), 0);
    }

    @Test
    public void deplacerExplo3() {
        int size = e0.getPos().getJoueurs().size();
        boolean res = e0.deplacer(DirExplo.HautGauche);

        assertTrue(res);
        assertNotSame(e0.getPos(), c6);
        assertEquals(c6.getJoueurs().size(), size - 1);
        assertEquals(e0.getPos().getJoueurs().size(), 1);
        assertEquals(e0.getPos().getSable(), 0);
        assertEquals(e0.getPos().getX(), 0);
        assertEquals(e0.getPos().getY(), 0);
    }

    // Tests desablerExplo
    @Test
    public void desablerExplo0() {
        Case c = e0.getPos().voisine(DirExplo.HautDroite);
        assertEquals(c.getSable(), 1);
        boolean res = e0.desabler(DirExplo.HautDroite);

        assertTrue(res);
        assertEquals(c.getSable(), 0);
        assertSame(e0.getPos(), c6);
        assertEquals(e0.getPos().getX(), 1);
        assertEquals(e0.getPos().getY(), 1);
    }

    @Test
    public void desablerExplo1() {
        Case c = e0.getPos().voisine(DirExplo.HautGauche);
        assertEquals(c.getSable(), 0);
        boolean res = e0.desabler(DirExplo.HautGauche);

        assertFalse(res);
        assertEquals(c.getSable(), 0);
        assertSame(e0.getPos(), c6);
        assertEquals(e0.getPos().getX(), 1);
        assertEquals(e0.getPos().getY(), 1);
    }

    @Test
    public void desablerExplo2() {
        Case c = e0.getPos().voisine(DirExplo.BasGauche);
        assertEquals(c.getSable(), 1);
        boolean res = e0.desabler(DirExplo.BasGauche);

        assertTrue(res);
        assertEquals(c.getSable(), 0);
        assertSame(e0.getPos(), c6);
        assertEquals(e0.getPos().getX(), 1);
        assertEquals(e0.getPos().getY(), 1);
    }

    @Test
    public void desablerExplo3() {
        try {
            e0.deplacer(DirExplo.BasDroite);
            assertTrue(false);
        }
        catch (RuntimeException e) {
            assertTrue(true);
        }
    }


    // Test PorteuseEau
    @Test
    public void porteuse0() {
        Set<CarteEquipement> vide = new HashSet<>();
        ArrayList<Piece> videP = new ArrayList<>();

        assertEquals(pe0.getEau(), 5);
        assertEquals(pe0.getPos(), o0);
        assertEquals(pe0.getPos().getX(), o0.getX());
        assertEquals(pe0.getPos().getY(), o0.getY());
        assertEquals(pe0.getBag().toString(), vide.toString());
        assertEquals(pe0.getPieces(), videP);
    }

    @Test
    public void porteuse1() {
        while(pe0.getEau() > 0) pe0.boire();
        assertEquals(pe0.getEau(), 0);

        pe0.explorer();
        assertEquals(pe0.getEau(), 2);

        pe0.explorer();
        assertEquals(pe0.getEau(), 4);

        pe0.explorer();
        assertEquals(pe0.getEau(), 5);

        pe0.explorer();
        assertEquals(pe0.getEau(), 5);
    }

    @Test
    public void porteuse2() {
        Personnage tmp = new Personnage(o0);
        mod3.addPers(tmp);

        while(o0.getSable() > 0) o0.desabler();

        while(pe0.getEau() > 0) pe0.boire();
        pe0.explorer(); pe0.explorer(); pe0.explorer();
        assertEquals(pe0.getEau(), 5);

        assertTrue(o0.estExplore());

        while(tmp.getEau() > 0) tmp.boire();
        assertEquals(tmp.getEau(), 0);

        tmp.explorer();
        assertEquals(tmp.getEau(), 0);
    }

    // Donne eau
    @Test
    public void donneEau0() {
        Personnage tmp = new Personnage(pe0.getPos());
        while(tmp.getEau() > 0) tmp.boire();
        assertEquals(tmp.getEau(), 0);
        assertEquals(pe0.getEau(), 5);

        boolean res;
        res = pe0.donneEau(4, tmp);
        assertTrue(res);
        assertEquals(tmp.getEau(), 4);
        assertEquals(pe0.getEau(), 1);
    }

    @Test
    public void donneEau1() {
        Personnage tmp = new Personnage(pe0.getPos());
        while(tmp.getEau() > 0) tmp.boire();
        assertEquals(tmp.getEau(), 0);
        assertEquals(pe0.getEau(), 5);

        boolean res;
        res = pe0.donneEau(6, tmp);
        assertTrue(res);
        assertEquals(tmp.getEau(), 4);
        assertEquals(pe0.getEau(), 1);
    }

    @Test
    public void donneEau2() {
        Personnage tmp = new Personnage(pe0.getPos());
        while(tmp.getEau() > 0) tmp.boire();
        pe0.boire(); pe0.boire(); pe0.boire();
        assertEquals(tmp.getEau(), 0);
        assertEquals(pe0.getEau(), 2);

        boolean res;
        res = pe0.donneEau(6, tmp);
        assertTrue(res);
        assertEquals(tmp.getEau(), 2);
        assertEquals(pe0.getEau(), 0);
    }

    @Test
    public void donneEau3() {
        Personnage tmp = new Personnage(pe0.getPos());
        while(tmp.getEau() > 0) tmp.boire();
        while(pe0.getEau() > 0) pe0.boire();
        assertEquals(tmp.getEau(), 0);
        assertEquals(pe0.getEau(), 0);

        boolean res;
        res = pe0.donneEau(6, tmp);
        assertFalse(res);
        assertEquals(tmp.getEau(), 0);
        assertEquals(pe0.getEau(), 0);
    }

    @Test
    public void donneEau4() {
        Personnage tmp = new Personnage(pe0.getPos());
        assertEquals(tmp.getEau(), 4);
        assertEquals(pe0.getEau(), 5);

        boolean res;
        res = pe0.donneEau(6, tmp);
        assertFalse(res);
        assertEquals(tmp.getEau(), 4);
        assertEquals(pe0.getEau(), 5);
    }

    @Test
    public void donneEau5() {
        Personnage tmp = new Personnage(pe0.getPos());
        pe0.boire(); pe0.boire(); pe0.boire();
        assertEquals(tmp.getEau(), 4);
        assertEquals(pe0.getEau(), 2);

        boolean res;
        res = tmp.donneEau(4, pe0);
        assertTrue(res);
        assertEquals(tmp.getEau(), 1);
        assertEquals(pe0.getEau(), 5);
    }

    @Test
    public void donneEau6() {
        boolean res;
        Personnage tmp = new Personnage(pe1.getPos());
        tmp.boire(); tmp.boire(); tmp.boire();
        res = tmp.deplacer(Dir.UP);
        assertTrue(res);

        assertEquals(tmp.getEau(), 1);
        assertEquals(pe1.getEau(), 5);

        res = pe1.donneEau(4, tmp);
        assertTrue(res);
        assertEquals(tmp.getEau(), 4);
        assertEquals(pe1.getEau(), 2);
    }

    @Test
    public void donneEau7() {
        boolean res;
        Personnage tmp = new Personnage(pe1.getPos());
        pe1.boire(); pe1.boire(); pe1.boire();
        res = pe1.deplacer(Dir.UP);
        assertTrue(res);

        assertEquals(tmp.getEau(), 4);
        assertEquals(pe1.getEau(), 2);

        res = tmp.donneEau(4, pe1);
        assertFalse(res);
        assertEquals(tmp.getEau(), 4);
        assertEquals(pe1.getEau(), 2);
    }

    @Test
    public void donneEau8() {
        Personnage tmp = new Personnage(pe0.getPos());
        while(tmp.getEau() > 0) tmp.boire();
        pe0.boire(); pe0.boire(); pe0.boire();
        assertEquals(tmp.getEau(), 0);
        assertEquals(pe0.getEau(), 2);

        boolean res;
        res = pe0.donneEau(1, tmp);
        assertTrue(res);
        assertEquals(tmp.getEau(), 1);
        assertEquals(pe0.getEau(), 1);
    }

    @Test
    public void donneEau9() {
        Personnage tmp = new Personnage(pe0.getPos());
        while(tmp.getEau() > 0) tmp.boire();
        pe0.boire(); pe0.boire(); pe0.boire();
        assertEquals(tmp.getEau(), 0);
        assertEquals(pe0.getEau(), 2);

        boolean res;
        res = pe0.donneEau(2, tmp);
        assertTrue(res);
        assertEquals(tmp.getEau(), 2);
        assertEquals(pe0.getEau(), 0);
    }

    @Test
    public void donneEau10() {
        Personnage tmp = new Personnage(pe0.getPos());
        while(tmp.getEau() > 0) tmp.boire();
        pe0.boire(); pe0.boire(); pe0.boire();
        assertEquals(tmp.getEau(), 0);
        assertEquals(pe0.getEau(), 2);

        boolean res;
        res = pe0.donneEau(0, tmp);
        assertTrue(res);
        assertEquals(tmp.getEau(), 0);
        assertEquals(pe0.getEau(), 2);
    }



    // Test Alpiniste
    @Test
    public void Alpiniste0() {
        Set<CarteEquipement> vide = new HashSet<>();
        ArrayList<Piece> videP = new ArrayList<>();

        assertEquals(al0.getEau(), 4);
        assertEquals(al0.getPos(), c7);
        assertEquals(al0.getPos().getX(), c7.getX());
        assertEquals(al0.getPos().getY(), c7.getY());
        assertEquals(al0.getBag().toString(), vide.toString());
        assertEquals(al0.getPieces(), videP);
    }

    // Test deplaceAlpiniste
    @Test
    public void deplaceAlpiniste0() {
        boolean res;
        c7.ensabler();
        assertEquals(c7.getSable(), 2);

        res = al0.deplacer(Dir.UP);
        assertTrue(res);
        assertSame(al0.getPos(), plat4.getCase(3, 2));
    }

    @Test
    public void deplaceAlpiniste1() {
        boolean res;
        Case tmp = plat4.getCase(3, 2);
        tmp.ensabler(); tmp.ensabler();
        assertEquals(tmp.getSable(), 2);

        res = al0.deplacer(Dir.UP);
        assertTrue(res);
        assertSame(al0.getPos(), plat4.getCase(3, 2));
    }

    @Test
    public void deplaceAlpiniste2() {
        boolean res;
        Case tmp = plat4.getCase(3, 2);
        Case prev = plat4.getCase(3, 3);
        assertEquals(tmp.getJoueurs().size(), 0);
        assertEquals(al0.getPos().getJoueurs().size(), 1);

        res = al0.deplacer(Dir.UP);
        assertTrue(res);
        assertEquals(tmp.getJoueurs().size(), 1);
        assertEquals(tmp.getJoueurs().size(), 1);
        assertEquals(prev.getJoueurs().size(), 0);
    }

    @Test
    public void deplaceAlpiniste3() {
        Personnage tmpP = new Personnage(al0.getPos());
        boolean res;
        Case tmpC1 = plat4.getCase(3, 2);
        Case tmpC2 = plat4.getCase(3, 3);
        assertEquals(tmpC1.getJoueurs().size(), 0);
        assertEquals(tmpC2.getJoueurs().size(), 2);

        res = al0.deplacerDuo(Dir.UP, tmpP);
        assertTrue(res);
        assertEquals(tmpC1.getJoueurs().size(), 2);
        assertEquals(tmpC2.getJoueurs().size(), 0);
    }

    // Test navigatrice
    @Test
    public void navigatrice0() {
        Set<CarteEquipement> vide = new HashSet<>();
        ArrayList<Piece> videP = new ArrayList<>();

        assertEquals(n0.getEau(), 4);
        assertEquals(n0.getPos(), c9);
        assertEquals(n0.getPos().getX(), c9.getX());
        assertEquals(n0.getPos().getY(), c9.getY());
        assertEquals(n0.getBag().toString(), vide.toString());
        assertEquals(n0.getPieces(), videP);
    }

    // Test donneEquip
    @Test
    public void donneEquip0() {
        boolean res;
        CarteEquipement blas = new CarteEquipement(mod, TypeCarteEquipement.BLASTER);
        p0.addEquip(blas); assertTrue(p0.possede(blas));

        Personnage tmp = new Personnage(c0);
        res = p0.donneEquip(blas, tmp); assertTrue(res);
        assertFalse(p0.possede(blas)); assertTrue(tmp.possede(blas));

        res = tmp.donneEquip(blas, p0); assertTrue(res);
        assertTrue(p0.possede(blas)); assertFalse(tmp.possede(blas));
    }

    // Test donneEquip
    @Test
    public void donneEquip1() {
        boolean res;
        CarteEquipement blas = new CarteEquipement(mod, TypeCarteEquipement.BLASTER);
        p0.addEquip(blas); assertTrue(p0.possede(blas));

        Personnage tmp = new Personnage(c1);
        res = p0.donneEquip(blas, tmp); assertFalse(res);
        assertTrue(p0.possede(blas)); assertFalse(tmp.possede(blas));
    }

    // Test donneEquip
    @Test
    public void donneEquip2() {
        boolean res;
        CarteEquipement blas = new CarteEquipement(mod, TypeCarteEquipement.BLASTER);
        p0.addEquip(blas); assertTrue(p0.possede(blas));

        Personnage tmp = new Personnage(c1);
        res = tmp.donneEquip(blas, p0); assertFalse(res);
        assertTrue(p0.possede(blas)); assertFalse(tmp.possede(blas));
    }

    // Test useEquip
    @Test
    public void useEquip0() {
        CarteEquipement equip = new CarteEquipement(p0.getPos().getPlateau().getModele(), TypeCarteEquipement.BLASTER);
        assertEquals(p0.getBag().size(), 0);
        assertFalse(p0.useEquip(equip, null, null));
    }

    @Test
    public void useEquip1() {
        CarteEquipement equip = new CarteEquipement(p0.getPos().getPlateau().getModele(), TypeCarteEquipement.BLASTER);
        p0.addEquip(equip);
        assertEquals(p0.getBag().size(), 1);

        assertEquals(p0.getPos().voisine(Dir.UP).getSable(), 0);
        for (int i = 0; i < 5; i++) p0.getPos().voisine(Dir.UP).ensabler();
        assertEquals(p0.getPos().voisine(Dir.UP).getSable(), 5);

        assertTrue(p0.useEquip(equip, null, Dir.UP));
        assertEquals(p0.getPos().voisine(Dir.UP).getSable(), 0);
        assertEquals(p0.getBag().size(), 0);
    }

    @Test
    public void useEquip2() {
        CarteEquipement equip = new CarteEquipement(p0.getPos().getPlateau().getModele(), TypeCarteEquipement.BLASTER);
        p0.addEquip(equip);
        assertEquals(p0.getBag().size(), 1);

        assertEquals(p0.getPos().voisine(DirExplo.BasGauche).getSable(), 0);
        for (int i = 0; i < 5; i++) p0.getPos().voisine(DirExplo.BasGauche).ensabler();
        assertEquals(p0.getPos().voisine(DirExplo.BasGauche).getSable(), 5);

        assertFalse(p0.useEquip(equip, null, DirExplo.BasGauche));
        assertEquals(p0.getPos().voisine(DirExplo.BasGauche).getSable(), 5);
        assertEquals(p0.getBag().size(), 1);
    }

    @Test
    public void useEquip3() {
        Explorateur expl = new Explorateur(p0.getPos());
        CarteEquipement equip = new CarteEquipement(expl.getPos().getPlateau().getModele(), TypeCarteEquipement.BLASTER);
        expl.addEquip(equip);
        assertEquals(expl.getBag().size(), 1);

        assertEquals(expl.getPos().voisine(DirExplo.BasGauche).getSable(), 0);
        for (int i = 0; i < 5; i++) expl.getPos().voisine(DirExplo.BasGauche).ensabler();
        assertEquals(expl.getPos().voisine(DirExplo.BasGauche).getSable(), 5);

        assertTrue(expl.useEquip(equip, null, DirExplo.BasGauche));
        assertEquals(expl.getPos().voisine(DirExplo.BasGauche).getSable(), 0);
        assertEquals(p0.getBag().size(), 0);
    }

    @Test
    public void useEquip4() {
        CarteEquipement equip = new CarteEquipement(p0.getPos().getPlateau().getModele(), TypeCarteEquipement.JETPACK);
        p0.addEquip(equip);
        assertEquals(p0.getBag().size(), 1);

        assertSame(p0.getPos(), plat.getCase(1, 3));
        assertTrue(p0.useEquip(equip, null, new Coord(4, 4)));
        assertSame(p0.getPos(), plat.getCase(4, 4));
        assertEquals(p0.getBag().size(), 0);
    }

    @Test
    public void useEquip5() {
        CarteEquipement equip = new CarteEquipement(p0.getPos().getPlateau().getModele(), TypeCarteEquipement.JETPACK);
        p0.addEquip(equip);
        assertEquals(p0.getBag().size(), 1);

        plat.getCase(4, 4).ensabler();
        plat.getCase(4, 4).ensabler();

        assertSame(p0.getPos(), plat.getCase(1, 3));
        assertFalse(p0.useEquip(equip, null, new Coord(4, 4)));
        assertSame(p0.getPos(), plat.getCase(1, 3));
        assertEquals(p0.getBag().size(), 1);
    }

    @Test
    public void useEquip6() {
        Personnage tmp = new Personnage(p0.getPos());
        CarteEquipement equip = new CarteEquipement(p0.getPos().getPlateau().getModele(), TypeCarteEquipement.JETPACK);
        p0.addEquip(equip);
        assertEquals(p0.getBag().size(), 1);

        assertSame(p0.getPos(), plat.getCase(1, 3));
        assertSame(tmp.getPos(), plat.getCase(1, 3));
        assertTrue(p0.useEquip(equip, tmp, new Coord(4, 4)));
        assertSame(p0.getPos(), plat.getCase(4, 4));
        assertSame(tmp.getPos(), plat.getCase(4, 4));
        assertEquals(p0.getBag().size(), 0);
    }

    @Test
    public void useEquip7() {
        CarteEquipement equip = new CarteEquipement(p0.getPos().getPlateau().getModele(), TypeCarteEquipement.JETPACK);
        assertEquals(p0.getBag().size(), 0);

        assertSame(p0.getPos(), plat.getCase(1, 3));
        assertFalse(p0.useEquip(equip, null, new Coord(4, 4)));
        assertSame(p0.getPos(), plat.getCase(1, 3));
        assertEquals(p0.getBag().size(), 0);
    }

    @Test
    public void useEquip8() {
        CarteEquipement equip = new CarteEquipement(p0.getPos().getPlateau().getModele(), TypeCarteEquipement.JETPACK);
        p0.addEquip(equip);
        assertEquals(p0.getBag().size(), 1);

        assertSame(p0.getPos(), plat.getCase(1, 3));
        assertFalse(p0.useEquip(equip, null, new Coord(5, 4)));
        assertSame(p0.getPos(), plat.getCase(1, 3));
        assertEquals(p0.getBag().size(), 1);
    }

    @Test
    public void useEquip9() {
        CarteEquipement equip = new CarteEquipement(p0.getPos().getPlateau().getModele(), TypeCarteEquipement.TERRASCOPE);
        assertEquals(p0.getBag().size(), 0);

        assertFalse(p0.useEquip(equip, null, null));
        assertEquals(p0.getBag().size(), 0);
    }

    @Test
    public void useEquip10() {
        CarteEquipement equip = new CarteEquipement(p0.getPos().getPlateau().getModele(), TypeCarteEquipement.TERRASCOPE);
        p0.addEquip(equip);
        assertEquals(p0.getBag().size(), 1);

        assertFalse(p0.useEquip(equip, null, null));
        assertEquals(p0.getBag().size(), 1);
    }

    @Test
    public void useEquip11() {
        CarteEquipement equip = new CarteEquipement(p0.getPos().getPlateau().getModele(), TypeCarteEquipement.TERRASCOPE);
        p0.addEquip(equip);
        assertEquals(p0.getBag().size(), 1);

        Coord co = new Coord(4, 4);
        Case place = plat.getCase(co.getX(), co.getY());

        assertFalse(place.estExplore());
        assertTrue(p0.useEquip(equip, null, co));
        assertTrue(place.estExplore());
        assertEquals(p0.getBag().size(), 0);
    }

    @Test
    public void useEquip12() {
        CarteEquipement equip = new CarteEquipement(p0.getPos().getPlateau().getModele(), TypeCarteEquipement.TERRASCOPE);
        p0.addEquip(equip);
        assertEquals(p0.getBag().size(), 1);

        assertFalse(p0.useEquip(equip, null, new Coord(5, 4)));
        assertEquals(p0.getBag().size(), 1);
    }

    @Test
    public void useEquip13() {
        CarteEquipement equip = new CarteEquipement(p0.getPos().getPlateau().getModele(), TypeCarteEquipement.RESERVE_EAU);
        p0.addEquip(equip);
        assertEquals(p0.getBag().size(), 1);
    }

    @Test
    public void useEquip14() {
        CarteEquipement equip = new CarteEquipement(p0.getPos().getPlateau().getModele(), TypeCarteEquipement.RESERVE_EAU);
        assertEquals(p0.getBag().size(), 0);

        assertFalse(p0.useEquip(equip, null, null));
    }

    @Test
    public void useEquip15() {
        CarteEquipement equip = new CarteEquipement(p0.getPos().getPlateau().getModele(), TypeCarteEquipement.RESERVE_EAU);
        p0.addEquip(equip);
        assertEquals(p0.getBag().size(), 1);

        assertEquals(p0.getEau(), 4);
        assertFalse(p0.useEquip(equip, null, null));
        assertEquals(p0.getEau(), 4);
    }

    @Test
    public void useEquip16() {
        CarteEquipement equip = new CarteEquipement(p0.getPos().getPlateau().getModele(), TypeCarteEquipement.RESERVE_EAU);
        p0.addEquip(equip);
        assertEquals(p0.getBag().size(), 1);

        p0.boire();
        assertEquals(p0.getEau(), 3);
        assertTrue(p0.useEquip(equip, null, null));
        assertEquals(p0.getEau(), 4);
    }

    @Test
    public void useEquip17() {
        CarteEquipement equip = new CarteEquipement(p0.getPos().getPlateau().getModele(), TypeCarteEquipement.RESERVE_EAU);
        p0.addEquip(equip);
        assertEquals(p0.getBag().size(), 1);

        p0.boire(); p0.boire(); p0.boire();
        assertEquals(p0.getEau(), 1);
        assertTrue(p0.useEquip(equip, null, null));
        assertEquals(p0.getEau(), 3);
    }

    @Test
    public void useEquip18() {
        CarteEquipement equip = new CarteEquipement(p0.getPos().getPlateau().getModele(), TypeCarteEquipement.RESERVE_EAU);
        p0.addEquip(equip);
        assertEquals(p0.getBag().size(), 1);

        Personnage tmp1 = new Personnage(p0.getPos());
        Personnage tmp2 = new Personnage(p0.getPos());
        Personnage tmp3 = new Personnage(p0.getPos());

        for (Personnage p : p0.getPos().getJoueurs()) p.boire();

        for (Personnage p : p0.getPos().getJoueurs()) assertEquals(p.getEau(), 3);

        assertTrue(p0.useEquip(equip, null, null));

        for (Personnage p : p0.getPos().getJoueurs()) assertEquals(p.getEau(), 4);
    }

    @Test
    public void useEquip19() {
        CarteEquipement equip = new CarteEquipement(p0.getPos().getPlateau().getModele(), TypeCarteEquipement.RESERVE_EAU);
        p0.addEquip(equip);
        assertEquals(p0.getBag().size(), 1);

        Personnage tmp1 = new Personnage(p0.getPos());
        Personnage tmp2 = new Personnage(p0.getPos());
        Personnage tmp3 = new Personnage(p0.getPos());

        for (Personnage p : p0.getPos().getJoueurs()) assertEquals(p.getEau(), 4);

        assertFalse(p0.useEquip(equip, null, null));

        for (Personnage p : p0.getPos().getJoueurs()) assertEquals(p.getEau(), 4);
    }

    // Test possede (avec Type)
    @Test
    public void possedeType0() {
        assertEquals(p0.getBag().size(), 0);

        assertFalse(p0.possede(TypeCarteEquipement.JETPACK));
    }

    @Test
    public void possedeType1() {
        CarteEquipement tmp = new CarteEquipement(p0.getPos().getPlateau().getModele(), TypeCarteEquipement.JETPACK);
        p0.addEquip(tmp);
        assertEquals(p0.getBag().size(), 1);

        assertTrue(p0.possede(TypeCarteEquipement.JETPACK));
        assertFalse(p0.possede(TypeCarteEquipement.BOUCLIER));

        p0.delEquip(tmp);
        assertFalse(p0.possede(TypeCarteEquipement.JETPACK));
    }

}
