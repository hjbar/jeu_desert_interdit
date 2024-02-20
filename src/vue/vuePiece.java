package vue;

import modele.enums.Piece;

import javax.swing.*;
import java.awt.*;

public class vuePiece extends JLabel{

    public vuePiece(Piece p){
        super();

        Color c;
        String txt = "";
        switch (p){
            case BOITE:
                c = Color.ORANGE;
                txt += "B";
                break;
            case CRISTAL:
                c = Color.CYAN;
                txt += "C";
                break;
            case HELICE:
                c = Color.DARK_GRAY;
                txt += "H";
                break;
            default:
                // Couleur du systeme
                c = Color.RED;
                txt += "H";
                break;
        }

        this.setLayout(null);
        this.setBackground(c);
        this.setForeground(Color.BLACK);
        this.setText(txt);
        this.setHorizontalTextPosition(JLabel.CENTER);
        this.setVerticalTextPosition(JLabel.CENTER);
        this.setOpaque(true);


        this.setVisible(true);
    }

}
