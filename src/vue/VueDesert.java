package vue;

import controleur.Controleur;
import modele.*;
import vue.PieceHolder.VueCase;

import javax.swing.*;
import java.awt.*;

public class VueDesert extends JPanel implements Observer{

    private final Modele modele;
    private final Controleur ctrl;

    private final VueCase[][] vc;

    public VueDesert(Modele modele, Controleur ctrl, Dimension d){
        this.modele = modele;
        this.ctrl = ctrl;

        this.setBounds(0, 0, d.width, d.height);
        this.setLayout(new GridLayout( Modele.DESERT_DIM, Modele.DESERT_DIM));
        this.vc = new VueCase[Modele.DESERT_DIM][];
        Plateau P = this.modele.getPlateau();
        int caseWidth = d.width / Modele.DESERT_DIM;
        int caseHeight = d.height / Modele.DESERT_DIM;
        Dimension dim = new Dimension(caseWidth, caseHeight);
        for (int i = 0; i < Modele.DESERT_DIM; ++i){
            this.vc[i] = new VueCase[Modele.DESERT_DIM];
            for (int j = 0; j < Modele.DESERT_DIM; ++j){
                this.vc[i][j] = new VueCase(this, ctrl, P.getCase(j, i), dim);
            }
        }

        P.addObserver(this);
        ctrl.addObserver(this);
    }

    public void update() {
        Plateau P = this.modele.getPlateau();
        for (int i = 0; i < Modele.DESERT_DIM; ++i){
            for (int j = 0; j < Modele.DESERT_DIM; ++j){
                this.vc[i][j].setVCase(P.getCase(j, i));
            }
        }
        this.repaint();
    }

}


