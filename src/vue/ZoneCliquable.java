package vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public abstract class ZoneCliquable extends JPanel implements MouseListener {

    public Dimension dim;

    // reprend zoneCliquable de TP1 NREINES
    public ZoneCliquable(int x, int y) {
        this(new Dimension(x, y));
    }

    public ZoneCliquable(Dimension dim){
        this.dim = dim;
        this.setPreferredSize(dim);
        this.addMouseListener(this);
    }

    public abstract void clicGauche();
    public abstract void clicDroit();

    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            this.clicDroit();
        } else {
            this.clicGauche();
        }
    }
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
}

