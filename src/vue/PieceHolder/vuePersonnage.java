package vue.PieceHolder;

import controleur.Controleur;
import modele.*;
import modele.enums.Piece;
import modele.joueurs.*;
import vue.PieceHolder.pieceHolder;
import vue.vuePiece;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;

public class vuePersonnage extends pieceHolder implements Observer {
    /** Personnage represente */
    private final Personnage pers;
    /** Controleur avec lequel la vue interagit */
    private final Controleur ctrl;
    /** Dimension de la fenetre */
    private final Dimension dim;

    private final static Color porteuseEau_color = new Color(81, 81, 189);
    private final static Color archeologue_color = new Color(164, 87, 16);
    private final static Color explorateur_color = new Color(90, 91, 70);
    private final static Color alpiniste_color = new Color(93, 196, 93);
    private final static Color navigatrice_color = new Color(26, 117, 122);
    private final static Color meteorologue_color = new Color(48, 224, 161);
    private final static Border ply_border = BorderFactory.createLineBorder(new Color(0, 0, 0));

    public vuePersonnage(Personnage ply, Controleur ctrl, int TailleX, int TailleY){
        super(TailleX, TailleY);
        this.pers = ply;
        ply.addObserver(this);

        this.ctrl = ctrl;
        this.dim = new Dimension(TailleX, TailleY);

        this.vuePieces.setBounds(TailleX-20, 1, 19, TailleY-2);
        this.setVisible(true);

        //this.setLayout(new BorderLayout());
        this.setBorder(ply_border);

        String s = "Player " + (ply.getId() + 1) + " (" + ply.getRole() + ")";
        JLabel plyVue = new JLabel(s);
        plyVue.setPreferredSize(new Dimension(15, 30));
        plyVue.setVisible(true);
        this.add(plyVue, BorderLayout.NORTH);

        this.infoHolder.setText("Eau : " + this.pers.getEau());
        this.infoHolder.setPreferredSize(new Dimension(25, 30));
        this.infoHolder.setForeground(Color.BLACK);

        this.setVisible(true);
        this.update();
    }

    public void update() {
        this.repaint();
        this.vueInfoUpdate();
        this.vuePiecesUpdate();
    }

    public void vueInfoUpdate(){
        String s = "Eau : ";
        this.infoHolder.setText(s+this.pers.getEau());
    }

    public void vuePiecesUpdate(){
        ArrayList<Piece> pieces = this.pers.getPieces();
        this.vuePieces.removeAll();
        for (int i = 0; i < pieces.size(); ++i){
            vuePiece vp = new vuePiece(pieces.get(i));
            this.vuePieces.add(vp);
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Color c = this.persoColor();
        g.setColor(c);

        g.fillRect(0, 0, this.dim.width, this.dim.height);
    }

    /** renvoie une couleur en fonction du type réel de l'attribut pers */
    private Color persoColor(){
        if ( this.pers instanceof Archeologue){
            return archeologue_color;
        } else if ( this.pers instanceof PorteuseEau){
            return porteuseEau_color;
        } else if ( this.pers instanceof Explorateur){
            return explorateur_color;
        } else if (this.pers instanceof Alpiniste){
            return alpiniste_color;
        } else if (this.pers instanceof Navigatrice){
            return navigatrice_color;
        } else if (this.pers instanceof Meteorologue)
            return meteorologue_color;
        else
            return Color.cyan;
    }

    public void clicGauche(){
        if (this.ctrl.getCtrlState() == Controleur.state.Deau) {
            this.ctrl.donneEauPlayer(this.pers);
        } else if (this.ctrl.getCtrlState() == Controleur.state.Peau){
            this.ctrl.prendreEauPlayer(this.pers);
        } else if ( this.ctrl.getCtrlState() == Controleur.state.Alp_select || this.ctrl.getCtrlState() == Controleur.state.NavAlp_select){
            this.ctrl.assignToAlp(this.pers);
        } else if ( this.ctrl.getCtrlState() == Controleur.state.Nav_select){
            this.ctrl.assignToNav(this.pers);
        }
    }

    public void clicDroit(){
        //this.clicGauche();
    }

}
