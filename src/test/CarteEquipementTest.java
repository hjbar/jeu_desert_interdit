package test;

import modele.*;
import modele.cartes.CarteEquipement;
import modele.enums.TypeCarteEquipement;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CarteEquipementTest {

    Modele mod = new Modele(5);

    CarteEquipement c0;
    CarteEquipement c1;
    CarteEquipement c2;
    CarteEquipement c3;
    CarteEquipement c4;
    CarteEquipement c5;


    @Before
    public void initCarte() {
        c0 = new CarteEquipement(mod, TypeCarteEquipement.BLASTER);
        c1 = new CarteEquipement(mod, TypeCarteEquipement.JETPACK);
        c2 = new CarteEquipement(mod, TypeCarteEquipement.BOUCLIER);
        c3 = new CarteEquipement(mod, TypeCarteEquipement.TERRASCOPE);
        c4 = new CarteEquipement(mod, TypeCarteEquipement.ACCEL);
        c5 = new CarteEquipement(mod, TypeCarteEquipement.RESERVE_EAU);
    }

    // Test constructeur
    @Test
    public void carte0() {
        assertEquals(c0.getType(), TypeCarteEquipement.BLASTER);
        assertEquals(c1.getType(), TypeCarteEquipement.JETPACK);
        assertEquals(c2.getType(), TypeCarteEquipement.BOUCLIER);
        assertEquals(c3.getType(), TypeCarteEquipement.TERRASCOPE);
        assertEquals(c4.getType(), TypeCarteEquipement.ACCEL);
        assertEquals(c5.getType(), TypeCarteEquipement.RESERVE_EAU);
    }

}
