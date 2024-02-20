package test;

import modele.*;
import modele.cartes.Carte;
import modele.cartes.CarteEquipement;
import modele.cartes.CarteTempete;
import modele.cartes.TasDeCarte;
import modele.enums.TypeCarteEquipement;
import modele.enums.TypeCarteTempete;
import modele.enums.TypePaquet;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TasDeCarteTest {
    Modele mod = new Modele(5);

    TasDeCarte tt;
    TasDeCarte te;

    @Before
    public void initTasDeCarte() {
        tt = new TasDeCarte(mod, TypePaquet.TEMPETE);
        te = new TasDeCarte(mod, TypePaquet.EQUIPEMENT);
    }

    // Test Constructeur
    @Test
    public void tasDeCarte0() {

        assertEquals(tt.getType(), TypePaquet.TEMPETE);
        assertEquals(tt.getNbCarte(), 31);

        for (Carte c : tt.getPaquet()) {
            assertTrue(c instanceof CarteTempete);
        }

    }

    @Test
    public void tasDeCarte1() {

        assertEquals(te.getType(), TypePaquet.EQUIPEMENT);
        assertEquals(te.getNbCarte(), 12);

        for (Carte c : te.getPaquet()) {
            assertTrue(c instanceof CarteEquipement);
        }

    }

    @Test
    public void tasDeCarte2() {
        int cptTemp1 = 0;
        int cptTemp2 = 0;
        int cptTemp3 = 0;
        int cptChal = 0;
        int cptDech = 0;

        for (Carte c : tt.getPaquet()) {
            CarteTempete t = (CarteTempete) c;
            TypeCarteTempete type = t.getType();

            switch (type) {

                case TEMPETE:
                    if (t.getNivVent() == 1) cptTemp1++;
                    else if (t.getNivVent() == 2) cptTemp2++;
                    else if (t.getNivVent() == 3) cptTemp3++;
                    break;

                case DECHAINEMENT:
                    cptDech++;
                    break;

                case VAGUE_CHALEUR:
                    cptChal++;
                    break;

            }

        }

        assertEquals(cptTemp1, 12);
        assertEquals(cptTemp2, 8);
        assertEquals(cptTemp3, 4);
        assertEquals(cptDech, 3);
        assertEquals(cptChal, 4);
    }

    @Test
    public void tasDeCarte3() {
        int cptBlas = 0;
        int cptJet = 0;
        int cptBou = 0;
        int cptTerr = 0;
        int cptAccel = 0;
        int cptRes = 0;

        for (Carte c : te.getPaquet()) {
            CarteEquipement t = (CarteEquipement) c;
            TypeCarteEquipement type = t.getType();

            switch (type) {

                case BLASTER:
                    cptBlas++; break;

                case JETPACK:
                    cptJet++; break;

                case BOUCLIER:
                    cptBou++; break;

                case TERRASCOPE:
                    cptTerr++; break;

                case ACCEL:
                    cptAccel++; break;

                case RESERVE_EAU:
                    cptRes++; break;

            }

        }

        assertEquals(cptBlas, 3);
        assertEquals(cptJet, 3);
        assertEquals(cptBou, 2);
        assertEquals(cptTerr, 2);
        assertEquals(cptAccel, 1);
        assertEquals(cptRes, 1);
    }

    // Test pioche
    @Test
    public void pioche0() {
        int nb = tt.getNbCarte();
        Carte c = tt.pioche();

        assertEquals(tt.getNbCarte(), nb - 1);
        assertTrue(c instanceof CarteTempete);
    }

    @Test
    public void pioche1() {
        int nb = te.getNbCarte();
        Carte c = te.pioche();

        assertEquals(te.getNbCarte(), nb - 1);
        assertTrue(c instanceof CarteEquipement);
    }

    // Test resetPaquet
    @Test
    public void resetPaquet0() {
        boolean res;
        assertEquals(tt.getNbCarte(), 31);

        while (tt.getNbCarte() > 0) tt.pioche();
        assertEquals(tt.getNbCarte(), 0);

        res = tt.resetPaquet();
        assertTrue(res);
        assertEquals(tt.getNbCarte(), 31);

        res = tt.resetPaquet();
        assertFalse(res);
        assertEquals(tt.getNbCarte(), 31);
    }

    @Test
    public void resetPaquet1() {
        boolean res;
        assertEquals(te.getNbCarte(), 12);

        while (te.getNbCarte() > 0) te.pioche();
        assertEquals(te.getNbCarte(), 0);

        res = te.resetPaquet();
        assertTrue(res);
        assertEquals(te.getNbCarte(), 12);

        res = te.resetPaquet();
        assertFalse(res);
        assertEquals(te.getNbCarte(), 12);
    }

}
