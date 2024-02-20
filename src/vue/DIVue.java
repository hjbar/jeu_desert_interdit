package vue;

import controleur.Controleur;
import modele.Modele;
import modele.joueurs.Personnage;
import vue.PieceHolder.vuePersonnage;

import javax.swing.*;
import java.awt.*;

public class DIVue {
    /** Fenêtre de l'interface graphique */
    private final JFrame frame;

    /** Vee du plateau */
    private final VueDesert plateau;

    /** vue des personnages */
    private final JPanel vuePersonnages;

    /** vue des commandes */
    private final VueControleur commandes;

    /** vue du tas de carte tempete */
    private final vueTasTempete vTT;
    /** vue du niveau de l'oeil et de la quantite de sable sur la plateau */
    private final vueEye eye;

    public final static Dimension dim =
            new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width-100,
                    Toolkit.getDefaultToolkit().getScreenSize().height-100);

    private final static Dimension dim_plateau = new Dimension(dim.width/2, dim.height/2);
    private final static Dimension dim_personnage = new Dimension(dim.width / 5, dim.height / 10);
    private final static Dimension dim_controlleur = new Dimension(dim.width / 5, dim.height / 5);
    private final static Dimension dim_vtt = new Dimension(dim.width / 10, dim.height/5);

    public DIVue(Modele modele, Controleur ctrl){

        this.frame = new JFrame();
        frame.setTitle("Le Desert Interdit");
        frame.setLayout(null);
        frame.setPreferredSize(dim);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.plateau = new VueDesert(modele, ctrl, dim_plateau);
        this.plateau.setBounds(dim.width/4, dim.height/4, dim_plateau.width, dim_plateau.height);
        frame.add(plateau);

        this.vuePersonnages = new JPanel();
        this.vuePersonnages.setLayout(null);

        int nbJoueur = modele.getPersonnages().size();
        this.vuePersonnages.setBounds(0, 0, dim_personnage.width, dim_personnage.height*nbJoueur);

        for (int i = 0; i < nbJoueur; ++i){
            Personnage ply = modele.getPers(i);
            vuePersonnage vp = new vuePersonnage(ply, ctrl, dim_personnage.width, dim_personnage.height);
            vp.setBounds(0, dim_personnage.height*i, dim_personnage.width, dim_personnage.height);
            vp.setVisible(true);
            this.vuePersonnages.add(vp);
        }
        frame.add(vuePersonnages);


        this.commandes = new VueControleur(modele, ctrl);
        this.commandes.setBounds(0, dim_personnage.height*nbJoueur, dim_controlleur.width, dim_controlleur.height);
        frame.add(commandes);

        this.eye = new vueEye(modele, 150, 100);
        this.eye.setBounds(dim.width / 2, dim.height/8, 100, 100);
        frame.add(eye);

        this.vTT = new vueTasTempete(modele, ctrl, dim_vtt);
        this.vTT.setBounds(3*dim.width/4+10, dim.height/4, dim_vtt.width, dim_vtt.height);
        frame.add(vTT);

        frame.setVisible(true);
    }
}




