package vue;


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class choixJoueur extends JPanel implements ChangeListener, ActionListener {


    // Le nombre de joueurs possible
    private final int min_joueur = 2;
    private final int max_joueur = 5;
    private final int nb_joueur_base = min_joueur;

    // La fenetre
    private final JFrame frame;

    // Le slider
    private JSlider choix;

    // Le timer pour choisir le nombre de joueur
    private Timer timer;
    private int delay = 840;
    private int limit = 5;
    private int nbFrame = 0;

    // Le nombre joueur qu'on récupère
    private boolean choixEffectue;
    private int nbJoueurChoisis;

    // On recupere la dimension de l'ecran
    public final static Dimension dim =
            new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width,
                    Toolkit.getDefaultToolkit().getScreenSize().height);


    // Constructeur du choix de joueur
    public choixJoueur() {

        // On initialise les valeurs par defaut

        choixEffectue = false;
        nbJoueurChoisis = nb_joueur_base;


        // On setup la fenetre

        this.frame = new JFrame();
        frame.setTitle("Choix nombre de joueurs");
        frame.setLayout(null);
        frame.setPreferredSize(dim);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // On creer le slider
        this.choix = new JSlider(JSlider.HORIZONTAL, min_joueur, max_joueur, nb_joueur_base);

        choix.addChangeListener(this);


        // On active les labels

        choix.setMajorTickSpacing(1);
        choix.setMinorTickSpacing(1);

        choix.setPaintTicks(true);
        choix.setPaintLabels(true);


        // On setup la police et l'emplacement du slider

        choix.setBounds(dim.width / 4 + dim.width / 128, 3 * dim.height / 4 - dim.height / 8, dim.width / 2, dim.height / 16);

        Font font = new Font("Arial", Font.BOLD, 15);
        choix.setFont(font);

        frame.add(choix);


        // On initialise le timer

        this.timer = new Timer(delay, this);


        // On ajoute du texte pour habiller la fenetre

        JLabel game_title = new JLabel("LE JEU DU DESERT INTERDIT", JLabel.CENTER);
        game_title.setBounds(0, dim.height / 4 - dim.height / 8, dim.width, dim.height / 8);
        Font font2 = new Font("Arial", Font.BOLD, 65);
        game_title.setFont(font2);

        frame.add(game_title);


        // On ajoute du texte pour habiller la fenetre

        JLabel choix_title = new JLabel("Choisissez le nombre de joueurs pour la partie :", JLabel.CENTER);
        choix_title.setBounds(0, dim.height / 2 - dim.height / 8, dim.width, dim.height / 8);
        Font font3 = new Font("Arial", Font.BOLD, 35);
        choix_title.setFont(font3);

        frame.add(choix_title);


        // On rend visible le choix de joueur

        frame.setVisible(true);

    }


    // Getter de l'etat du choix
    public boolean aChoisis() {
        return this.choixEffectue;
    }


    // Getter du nombre de joueurs
    public int getNbJoueurs() {
        return this.nbJoueurChoisis;
    }


    // Permet de fermer la fenetre depuis le main
    public void fermeFenetre() {
        frame.dispose();
    }


    // On ecoute le slider
    @Override
    public void stateChanged(ChangeEvent e) {

        timer.restart();
        nbFrame = 0;

        // On recupere la valeur du slider
        this.nbJoueurChoisis = choix.getValue();

    }


    // Est appele lorsque le timer augmente
    @Override
    public void actionPerformed(ActionEvent e) {

        if (nbFrame == limit) {
            this.choixEffectue = true;
        }
        else {
            nbFrame++;
        }

    }



}
