package vue;

import controleur.Controleur;
import modele.Modele;
import modele.Observer;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class vueTasTempete extends ZoneCliquable implements Observer{

    private Modele modele;
    private Controleur ctrl;
    private JLabel cpt_turn;
    private final static Color vue_color = new Color(169, 77, 77);
    private final static Border red_border = BorderFactory.createLineBorder(vue_color, 5);
    public vueTasTempete(Modele m, Controleur ctrl,Dimension D){
        super(D);
        this.modele = m;
        this.ctrl = ctrl;
        this.setBorder(red_border);
        this.setLayout(null);

        JLabel txt = new JLabel("Tas De Carte Tempete");
        txt.setBounds(5, 5, D.width-5, 30);
        txt.setForeground(vue_color);
        txt.setOpaque(false);
        this.add(txt);

        this.cpt_turn = new JLabel();
        String s = "A piocher: " + this.ctrl.getNbPiocheply();
        this.cpt_turn.setText(s);
        this.cpt_turn.setBounds(5, 35, D.width-5, 30);
        this.cpt_turn.setForeground(vue_color);
        this.cpt_turn.setOpaque(false);
        this.add(cpt_turn);

        this.ctrl.addObserver(this);
    }

    public void update() {
        String s = "A piocher: " + this.ctrl.getNbPiocheply();
        this.cpt_turn.setText(s);
    }

    @Override
    public void clicGauche() {
        this.ctrl.piocheCarteTempete();
    }
    @Override
    public void clicDroit(){

    }
}
