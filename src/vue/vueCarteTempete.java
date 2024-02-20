package vue;

import javax.swing.*;
import javax.swing.border.Border;

import controleur.Controleur;
import controleur.ControleurMeteorologue;
import modele.cartes.Carte;

import java.awt.*;

/** Vue des cartes tempetes dans dans la VueMeteotologue */
public class vueCarteTempete extends JPanel {

    private final static Color card_color = new Color(154, 86, 235);
    private final static Border card_border = BorderFactory.createEtchedBorder(card_color, Color.BLACK);
    public vueCarteTempete(ControleurMeteorologue ctrl, Carte C, int id, Dimension dim){
        super();
        this.setPreferredSize(dim);
        this.setBorder(card_border);
        this.setVisible(true);

        this.setLayout(new FlowLayout());
        JLabel carte_label = new JLabel(C.nomCarte());
        carte_label.setBounds(0, 0, dim.width, dim.height/8);
        carte_label.setHorizontalTextPosition(JLabel.CENTER);
        carte_label.setVerticalTextPosition(JLabel.CENTER);
        carte_label.setOpaque(false);
        this.add(carte_label);

        JButton carte_button = new JButton("en-dessous de Pioche");
        carte_button.setActionCommand(String.valueOf(id));
        carte_button.setBounds(0, dim.height/8, dim.width / 2, dim.height / 8);
        carte_button.addActionListener(ctrl);

        this.add(carte_button);
    }

}
