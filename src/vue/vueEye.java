package vue;

import modele.Modele;
import modele.Observer;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class vueEye extends JPanel implements Observer {

    private final Modele modele;
    private final JLabel niveau_temp;
    private final JLabel tonne_sand;
    private final JProgressBar bar;
    private final static Color eye_color = Color.GRAY;
    private final static Color sand_color = new Color(231, 190, 58);
    private final static Border bar_border = BorderFactory.createLineBorder(eye_color);
    public vueEye(Modele modele, int tailleX, int tailleY){
        super();

        this.modele = modele;

        this.bar = new JProgressBar(20, 70);
        int tempete_value = (int) (this.modele.getTempete_niveau() * 10);
        this.bar.setValue(tempete_value);
        this.bar.setForeground(eye_color);
        this.bar.setBounds(10, 0, tailleX-10, tailleY-40);
        this.bar.setVisible(true);
        this.add(bar);

        this.niveau_temp = new JLabel();
        String s0 = "tempete: " + this.modele.getTempete_niveau() + " / 7.0";
        this.niveau_temp.setText(s0);
        this.niveau_temp.setBounds(0, tailleY-40, tailleX, 20);
        this.niveau_temp.setForeground(eye_color);
        this.niveau_temp.setOpaque(false);
        this.setVisible(true);
        this.add(niveau_temp);

        this.tonne_sand = new JLabel();
        String s1 = "sable: " + this.modele.getPlateau().totalSable();
        this.tonne_sand.setText(s1);
        this.tonne_sand.setBounds(0, tailleY-20, tailleX, 20);
        this.tonne_sand.setForeground(sand_color);
        this.tonne_sand.setOpaque(false);
        this.setVisible(true);
        this.add(this.tonne_sand);


        this.modele.addObserver(this);
        this.modele.getPlateau().addObserver(this);
    }

    public void update(){
        int tempete_value = (int) (this.modele.getTempete_niveau() * 10);
        this.bar.setValue(tempete_value);

        String s = "tempete: " + this.modele.getTempete_niveau() + " / 7.0";
        this.niveau_temp.setText(s);

        String s1 = "sable: " + this.modele.getPlateau().totalSable();
        this.tonne_sand.setText(s1);
    }

}
