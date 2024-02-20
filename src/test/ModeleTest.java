package test;

import modele.*;
import modele.cartes.CarteEquipement;
import modele.cases.Case;
import modele.cases.Oasis;
import modele.cases.PisteDecollage;
import modele.cases.Tunnel;
import modele.enums.Dir;
import modele.enums.Piece;
import modele.enums.TypeCarteEquipement;
import modele.joueurs.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static modele.Modele.DESERT_DIM;
import static org.junit.Assert.*;

public class ModeleTest {

    Modele mod;
    Modele mod2;

    @Before
    public void initModele() {
        mod = new Modele();
        mod2 = new Modele(5);
    }

    // Test constructeur
    @Test
    public void modele0() {
        double temp_niv = 2.0;
        assertTrue(mod.getTempete_niveau() == temp_niv);

        assertEquals(mod.getPersonnages(), new ArrayList<Personnage>());
        assertEquals(mod.getTdd(), null);
        assertEquals(mod.getPiste(), null);
    }

    @Test
    public void modele1() {
        double temp_niv = 2.0;
        assertTrue(mod2.getTempete_niveau() == temp_niv);

        assertNotEquals(mod2.getTdd(), null);
        assertNotEquals(mod2.getPiste(), null);

        assertEquals(mod2.getPersonnages().size(), 5);
        assertEquals(mod2.getTdd().getNbCarte(), 31);
    }

    @Test
    public void modele2() {
        PisteDecollage pisteDel = null;

        for (int i = 0; i < DESERT_DIM; i++) {
            for (int j = 0; j < DESERT_DIM; j++) {
                if ( ! (i == 2 && j == 2) ) {
                    Case cas = mod2.getPlateau().getCase(i, j);
                    if (cas instanceof PisteDecollage) pisteDel = (PisteDecollage) cas;
                }
            }
        }

        assertSame(pisteDel, mod2.getPiste());
    }

    @Test
    public void modele3() {
        int cptArch = 0;
        int cptAlpi = 0;
        int cptExpl = 0;
        int cptNavi = 0;
        int cptPort = 0;
        int cptMete = 0;

        for (Personnage p : mod2.getPersonnages()) {

            if (p instanceof Archeologue) cptArch++;
            else if (p instanceof Alpiniste) cptAlpi++;
            else if (p instanceof Explorateur) cptExpl++;
            else if (p instanceof Navigatrice) cptNavi++;
            else if (p instanceof PorteuseEau) cptPort++;
            else if (p instanceof Meteorologue) cptMete++;
            else throw new RuntimeException("Type de joueur non valide !");

        }

        assertTrue(cptArch == 0 || cptArch == 1);
        assertTrue(cptAlpi == 0 || cptAlpi == 1);
        assertTrue(cptExpl == 0 || cptExpl == 1);
        assertTrue(cptNavi == 0 || cptNavi == 1);
        assertTrue(cptPort == 0 || cptPort == 1);
        assertTrue(cptMete == 0 || cptMete == 1);

        assertEquals(mod2.getPersonnages().size(), 5);
    }

    @Test
    public void modele4() {
        int cptArch = 0;
        int cptAlpi = 0;
        int cptExpl = 0;
        int cptNavi = 0;
        int cptPort = 0;
        int cptMete = 0;

        Modele tmp = new Modele(2);

        for (Personnage p : tmp.getPersonnages()) {

            if (p instanceof Archeologue) cptArch++;
            else if (p instanceof Alpiniste) cptAlpi++;
            else if (p instanceof Explorateur) cptExpl++;
            else if (p instanceof Navigatrice) cptNavi++;
            else if (p instanceof PorteuseEau) cptPort++;
            else if (p instanceof Meteorologue) cptMete++;
            else throw new RuntimeException("Type de joueur non valide !");

        }

        assertTrue(cptArch == 0 || cptArch == 1);
        assertTrue(cptAlpi == 0 || cptAlpi == 1);
        assertTrue(cptExpl == 0 || cptExpl == 1);
        assertTrue(cptNavi == 0 || cptNavi == 1);
        assertTrue(cptPort == 0 || cptPort == 1);
        assertTrue(cptMete == 0 || cptMete == 1);

        assertEquals(tmp.getPersonnages().size(), 2);
    }

    @Test
    public void modele5() {
        int cptArch = 0;
        int cptAlpi = 0;
        int cptExpl = 0;
        int cptNavi = 0;
        int cptPort = 0;
        int cptMete = 0;

        Modele tmp = new Modele(4);

        for (Personnage p : tmp.getPersonnages()) {

            if (p instanceof Archeologue) cptArch++;
            else if (p instanceof Alpiniste) cptAlpi++;
            else if (p instanceof Explorateur) cptExpl++;
            else if (p instanceof Navigatrice) cptNavi++;
            else if (p instanceof PorteuseEau) cptPort++;
            else if (p instanceof Meteorologue) cptMete++;
            else throw new RuntimeException("Type de joueur non valide !");

        }

        assertTrue(cptArch == 0 || cptArch == 1);
        assertTrue(cptAlpi == 0 || cptAlpi == 1);
        assertTrue(cptExpl == 0 || cptExpl == 1);
        assertTrue(cptNavi == 0 || cptNavi == 1);
        assertTrue(cptPort == 0 || cptPort == 1);
        assertTrue(cptMete == 0 || cptMete == 1);

        assertEquals(tmp.getPersonnages().size(), 4);
    }

    // Test dechaine
    @Test
    public void dechaine0() {
        double temp_niv;

        for (double i = 2.5; i < 5; i += 0.5) {
            mod2.tempeteDechaine();
            temp_niv = i;
            assertTrue(mod2.getTempete_niveau() == temp_niv);
        }

        for (double i = 2.5; i < 5; i += 0.5) {
            mod.tempeteDechaine();
            temp_niv = i;
            assertTrue(mod.getTempete_niveau() == temp_niv);
        }

    }

    // Test chaleur
    @Test
    public void chaleur0() {

        for (Personnage p : mod2.getPersonnages()) {
            if (p instanceof PorteuseEau) assertEquals(p.getEau(), 5);
            else assertEquals(p.getEau(), 4);
        }

        mod2.vagueChaleur();

        for (Personnage p : mod2.getPersonnages()) {
            if (p instanceof PorteuseEau) assertEquals(p.getEau(), 4);
            else assertEquals(p.getEau(), 3);
        }

        for (int i = 0; i < 3; i++) { mod2.vagueChaleur(); }

        for (Personnage p : mod2.getPersonnages()) {
            if (p instanceof PorteuseEau) assertEquals(p.getEau(), 1);
            else assertEquals(p.getEau(), 0);
        }

    }

    @Test
    public void chaleur1() {

        Tunnel tun = new Tunnel(mod.getPlateau(), 2, 2);
        Personnage pers = new Personnage(tun);
        int eau = pers.getEau();

        mod.vagueChaleur();
        assertEquals(pers.getEau(), eau);

    }

    @Test
    public void chaleur2() {

        for (Personnage p : mod2.getPersonnages()) {
            if (p instanceof PorteuseEau) assertEquals(p.getEau(), 5);
            else assertEquals(p.getEau(), 4);
        }

        mod2.vagueChaleur();

        for (Personnage p : mod2.getPersonnages()) {
            if (p instanceof PorteuseEau) assertEquals(p.getEau(), 4);
            else assertEquals(p.getEau(), 3);
        }

        Personnage tmp = null;
        if (mod2.getPersonnages().get(0) instanceof PorteuseEau) {
            tmp = mod2.getPersonnages().get(1);
        }
        else {
            tmp = mod2.getPersonnages().get(0);
        }

        tmp.addEquip(new CarteEquipement(mod2, TypeCarteEquipement.BOUCLIER));

        for (int i = 0; i < 3; i++) { mod2.vagueChaleur(); }

        for (Personnage p : mod2.getPersonnages()) {
            if (p instanceof PorteuseEau) assertEquals(p.getEau(), 1);
            else if (p == tmp) assertEquals(p.getEau(), 3);
            else assertEquals(p.getEau(), 0);
        }

    }

    @Test
    public void chaleur3() {

        Tunnel Otmp = new Tunnel(mod2.getPlateau(), 0, 0);
        Personnage Ptmp = new Personnage(Otmp);
        mod2.addPers(Ptmp);

        assertEquals(Ptmp.getEau(), 4);

        mod2.vagueChaleur();
        assertEquals(Ptmp.getEau(), 3);

        Ptmp.explorer();
        mod2.vagueChaleur();
        assertEquals(Ptmp.getEau(), 3);

    }

    // Test souffle
    @Test
    public void souffle0() {
        mod2.ventSouffle(2, Dir.UP);

        assertEquals(mod2.getPlateau().getEye().getX(), 2);
        assertEquals(mod2.getPlateau().getEye().getY(), 0);

        mod2.ventSouffle(2, Dir.LEFT);

        assertEquals(mod2.getPlateau().getEye().getX(), 0);
        assertEquals(mod2.getPlateau().getEye().getY(), 0);
    }

    // Test addPers
    @Test
    public void addPers0() {
        assertEquals(mod.getPersonnages().size(), 0);

        Personnage p = new Personnage(mod.getPlateau().getCase(1, 1));
        mod.addPers(p);
        assertEquals(mod.getPersonnages().size(), 1);
    }

    // Test getPers
    @Test
    public void getPers0() {
        Personnage p = mod2.getPersonnages().get(0);
        int id = p.getId();

        assertSame(p, mod2.getPers(id));
    }

    @Test
    public void getPers1() {
        try {
            Personnage p = mod2.getPersonnages().get(50);
            assertTrue(false);
        }
        catch (RuntimeException e) {
            assertTrue(true);
        }
    }

    // Test setPiece
    @Test
    public void setPiste0() {
        assertEquals(mod.getPiste(), null);

        PisteDecollage pisteDel = new PisteDecollage(mod.getPlateau(), 3, 3);
        mod.setPiste(pisteDel);

        assertSame(mod.getPiste(), pisteDel);

        try {
            mod.setPiste(pisteDel);
            assertTrue(false);
        }
        catch (RuntimeException e) {
            assertTrue(true);
        }
    }

    @Test
    public void setPiste1() {
        assertNotEquals(mod2.getPiste(), null);

        try {
            mod2.setPiste(new PisteDecollage(mod2.getPlateau(), 3, 3));
            assertTrue(false);
        }
        catch (RuntimeException e) {
            assertTrue(true);
        }
    }

    // Test victoire
    @Test
    public void victoire0() {
        assertFalse(mod2.victoire());
    }

    @Test
    public void victoire1() {
        while(mod2.getPiste().getSable() < 2) mod2.getPiste().ensabler();
        assertEquals(mod2.getPiste().getSable(), 2);

        assertFalse(mod2.victoire());
    }

    @Test
    public void victoire2() {
        PisteDecollage piste = mod2.getPiste();
        assertNotEquals(piste.getJoueurs().size(), 5);

        Coord c = new Coord(piste.getX(), piste.getY());
        for (Personnage p : mod2.getPersonnages()) p.setCoord(c);

        assertFalse(mod2.victoire());
    }

    @Test
    public void victoire3() {
        PisteDecollage piste = mod2.getPiste();
        assertEquals(piste.getPieces().size(), 0);

        assertFalse(mod2.victoire());
    }

    @Test
    public void victoire4() {
        PisteDecollage piste = mod2.getPiste();

        piste.addPiece(Piece.CRISTAL);
        piste.addPiece(Piece.BOITE);
        piste.addPiece(Piece.SYSTEME);
        piste.addPiece(Piece.HELICE);

        assertEquals(piste.getPieces().size(), 4);
        assertFalse(mod2.victoire());
    }

    @Test
    public void victoire5() {
        PisteDecollage piste = mod2.getPiste();
        assertFalse(piste.estExplore());

        assertFalse(mod2.victoire());
    }

    @Test
    public void victoire6() {
        PisteDecollage piste = mod2.getPiste();

        while(piste.getSable() > 0) piste.desabler();

        piste.Explore();

        Coord c = new Coord(piste.getX(), piste.getY());
        for (Personnage p : mod2.getPersonnages()) p.setCoord(c);

        piste.addPiece(Piece.CRISTAL);
        piste.addPiece(Piece.BOITE);
        piste.addPiece(Piece.SYSTEME);
        piste.addPiece(Piece.HELICE);

        assertTrue(mod2.victoire());
    }

    @Test
    public void victoire7() {
        PisteDecollage piste = mod2.getPiste();

        piste.Explore();

        Coord c = new Coord(piste.getX(), piste.getY());
        for (Personnage p : mod2.getPersonnages()) p.setCoord(c);

        assertFalse(mod2.victoire());
    }

    @Test
    public void victoire8() {
        PisteDecollage piste = mod2.getPiste();

        piste.Explore();

        piste.addPiece(Piece.CRISTAL);
        piste.addPiece(Piece.BOITE);
        piste.addPiece(Piece.SYSTEME);
        piste.addPiece(Piece.HELICE);

        assertFalse(mod2.victoire());
    }

    @Test
    public void victoire9() {
        PisteDecollage piste = mod2.getPiste();

        Coord c = new Coord(piste.getX(), piste.getY());
        for (Personnage p : mod2.getPersonnages()) p.setCoord(c);

        piste.addPiece(Piece.CRISTAL);
        piste.addPiece(Piece.BOITE);
        piste.addPiece(Piece.SYSTEME);
        piste.addPiece(Piece.HELICE);

        assertFalse(mod2.victoire());
    }

    @Test
    public void victoire10() {
        PisteDecollage piste = mod2.getPiste();

        piste.ensabler(); piste.ensabler();

        piste.Explore();

        Coord c = new Coord(piste.getX(), piste.getY());
        for (Personnage p : mod2.getPersonnages()) p.setCoord(c);

        piste.addPiece(Piece.CRISTAL);
        piste.addPiece(Piece.BOITE);
        piste.addPiece(Piece.SYSTEME);
        piste.addPiece(Piece.HELICE);

        assertFalse(mod2.victoire());
    }

    // Test defaite
    @Test
    public void defaite0() {
        assertFalse(mod.defaite());
        assertFalse(mod2.defaite());
    }

    @Test
    public void defaite1() {
        Plateau plat2 = mod2.getPlateau();
        for (int i = plat2.totalSable(); i < Plateau.lim_sable; i++) plat2.getCase(0, 0).ensabler();

        assertEquals(mod2.getPlateau().totalSable(), Plateau.lim_sable);
        assertFalse(mod2.defaite());

        plat2.getCase(0, 0).ensabler();
        assertEquals(plat2.totalSable(), Plateau.lim_sable + 1);
        assertTrue(mod2.defaite());
    }

    @Test
    public void defaite2() {

        for (int i = 0; i < 4; i++) {
            for (Personnage p : mod2.getPersonnages()) p.boire();
        }

        for (Personnage p : mod2.getPersonnages()) {
            if (p instanceof PorteuseEau) assertEquals(p.getEau(), 1);
            else assertEquals(p.getEau(), 0);
        }

        assertFalse(mod2.defaite());

        for (Personnage p : mod2.getPersonnages()) p.boire();

        if (mod2.getPersonnages().get(0) instanceof PorteuseEau)
            assertEquals(mod2.getPersonnages().get(0).getEau(), 0);
        else
            assertEquals(mod2.getPersonnages().get(0).getEau(), -1);

        assertTrue(mod2.defaite());
    }

    @Test
    public void defaite3() {
        double niv1 = 2.0;
        assertTrue(mod2.getTempete_niveau() == niv1);
        assertFalse(mod2.defaite());

        for (int i = 0; i <= 8; i++) mod2.tempeteDechaine();
        double niv2 = 6.5;
        assertTrue(mod2.getTempete_niveau() == niv2);
        assertFalse(mod2.defaite());

        mod2.tempeteDechaine();
        double niv3 = 7.0;
        assertTrue(mod2.getTempete_niveau() == niv3);
        assertTrue(mod2.defaite());
    }

}
