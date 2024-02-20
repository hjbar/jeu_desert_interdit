package vue.PieceHolder;

import vue.ZoneCliquable;

import javax.swing.*;
import java.awt.*;

abstract public class pieceHolder extends ZoneCliquable {

    protected final JPanel vuePieces;

    protected final JLabel infoHolder;

    public pieceHolder(int width, int height){
        super(width, height);

        this.vuePieces = new JPanel();
        this.vuePieces.setLayout(new GridLayout(4, 1));
        this.vuePieces.setOpaque(false);
        this.setVisible(true);
        this.add(vuePieces);

        this.setLayout(new BorderLayout());

        this.infoHolder = new JLabel("");
        this.infoHolder.setVisible(true);
        this.add(this.infoHolder, BorderLayout.SOUTH);
    }

    /** Met à jour la vue des pieces des infoHolder */
    abstract protected void vuePiecesUpdate();

    /** Met a jour la vue donnant des informations de l'infoHolder ( le sable dans le cas des cases et l'eau pour les personnanges) */
    abstract protected void vueInfoUpdate();
}
