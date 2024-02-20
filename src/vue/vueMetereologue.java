package vue;

import controleur.ControleurMeteorologue;
import modele.Modele;
import modele.Observer;
import modele.cartes.Carte;
import modele.cartes.TasDeCarte;

import javax.swing.*;
import java.awt.*;

public class vueMetereologue extends JFrame implements Observer {

    ControleurMeteorologue ctrl;
    private final JButton skip_button;

    private final JButton look_button;

    private final JPanel vue_cartesT;

    private final static Dimension dim_vue = new Dimension(DIVue.dim.width/2, DIVue.dim.height/3);
    public vueMetereologue(ControleurMeteorologue ctrl){
        super();
        this.ctrl = ctrl;

        this.setPreferredSize(dim_vue);
        this.pack();

        this.setLayout(new FlowLayout());

        this.skip_button = new JButton("Skip");
        this.skip_button.setPreferredSize(new Dimension(dim_vue.width/8, dim_vue.height/8));
        this.skip_button.setVisible(true);
        this.add(skip_button);

        this.look_button = new JButton("Look");
        this.look_button.setPreferredSize(new Dimension(dim_vue.width/8, dim_vue.height/8));
        this.look_button.setVisible(true);
        this.add(look_button);

        this.skip_button.addActionListener(ctrl);
        this.look_button.addActionListener(ctrl);
        ctrl.addObserver(this);

        this.vue_cartesT = new JPanel();
        this.vue_cartesT.setPreferredSize(new Dimension(dim_vue.width, 7*dim_vue.height/8));
        this.vue_cartesT.setVisible(false);
        this.vue_cartesT.setLayout(null);
        this.add(vue_cartesT);

        this.setVisible(false);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    @Override
    public void update() {
        if (this.ctrl.getShowCards()){
            this.skip_button.setVisible(false);
            this.look_button.setVisible(false);
            this.showCards();
            this.vue_cartesT.setVisible(true);
        } else {
            this.skip_button.setVisible(true);
            this.look_button.setVisible(true);
            this.vue_cartesT.removeAll();
            this.vue_cartesT.setVisible(false);
        }

        this.setVisible(!this.ctrl.getCloseWindow());
    }

    private void showCards(){
        Modele m = this.ctrl.getModele();

        int niv_tempete = (int) m.getTempete_niveau();
        TasDeCarte tas = m.getTdd();
        int i = 0;
        while ( i < niv_tempete && tas.getNbCarte() > 0 && i < tas.getNbCarte()){
            Carte c = tas.getPaquet().get(i);
            vueCarteTempete vct = new vueCarteTempete(this.ctrl, c, i, new Dimension(dim_vue.width / niv_tempete, 7*dim_vue.height/8));
            vct.setBounds(i * dim_vue.width / niv_tempete + 10, 0, dim_vue.width / niv_tempete, 7*dim_vue.height/8);
            this.vue_cartesT.add(vct);
            ++i;

        }

    }
}
