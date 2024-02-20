package test;

import modele.*;
import modele.cartes.CarteTempete;
import modele.enums.Dir;
import modele.enums.TypeCarteTempete;
import modele.joueurs.Personnage;
import modele.joueurs.PorteuseEau;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CarteTempeteTest {

    Modele mod = new Modele(5);

    CarteTempete c0;
    CarteTempete c1;
    CarteTempete c2;
    CarteTempete c3;
    CarteTempete c4;

    @Before
    public void initCarte() {
        c0 = new CarteTempete(mod, Dir.UP, 1, TypeCarteTempete.TEMPETE);
        c1 = new CarteTempete(mod, Dir.LEFT, 2, TypeCarteTempete.TEMPETE);
        c2 = new CarteTempete(mod, Dir.RIGHT, 3, TypeCarteTempete.TEMPETE);
        c3 = new CarteTempete(mod, TypeCarteTempete.DECHAINEMENT);
        c4 = new CarteTempete(mod, TypeCarteTempete.VAGUE_CHALEUR);
    }

    // Test constructeur
    @Test
    public void carte0() {
        assertEquals(c0.getType(), TypeCarteTempete.TEMPETE);
        assertEquals(c0.getDir(), Dir.UP);
        assertEquals(c0.getNivVent(), 1);

        assertEquals(c1.getType(), TypeCarteTempete.TEMPETE);
        assertEquals(c1.getDir(), Dir.LEFT);
        assertEquals(c1.getNivVent(), 2);

        assertEquals(c2.getType(), TypeCarteTempete.TEMPETE);
        assertEquals(c2.getDir(), Dir.RIGHT);
        assertEquals(c2.getNivVent(), 3);

        assertEquals(c3.getType(), TypeCarteTempete.DECHAINEMENT);
        assertEquals(c4.getType(), TypeCarteTempete.VAGUE_CHALEUR);
    }

    // Test effet
    @Test
    public void effet0() {
        c0.effetCarte();
        assertEquals(c0.getMod().getPlateau().getEye().getX(), 2);
        assertEquals(c0.getMod().getPlateau().getEye().getY(), 1);
    }

    @Test
    public void effet1() {
        c1.effetCarte();
        assertEquals(c1.getMod().getPlateau().getEye().getX(), 0);
        assertEquals(c1.getMod().getPlateau().getEye().getY(), 2);
    }

    @Test
    public void effet2() {
        c2.effetCarte();
        assertEquals(c2.getMod().getPlateau().getEye().getX(), 4);
        assertEquals(c2.getMod().getPlateau().getEye().getY(), 2);
    }

    @Test
    public void effet3() {
        c3.effetCarte();

        double temp_niv = 2.5;
        assertTrue(c3.getMod().getTempete_niveau() == temp_niv);
    }

    @Test
    public void effet4() {
        c4.effetCarte();

        for (Personnage p : c4.getMod().getPersonnages()) {
            if (p instanceof PorteuseEau) assertEquals(p.getEau(), 4);
            else assertEquals(p.getEau(), 3);
        }

    }

}
