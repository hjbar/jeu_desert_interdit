package vue.PieceHolder;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;

import controleur.Controleur;
import modele.*;
import modele.cases.*;
import modele.enums.Piece;
import modele.joueurs.Personnage;
import vue.PieceHolder.pieceHolder;
import vue.VueDesert;
import vue.vuePiece;

/** Vue d'une case du plateau */
public class VueCase extends pieceHolder {

    private final VueDesert vueDesert;
    private Case vCase;
    private final JPanel casePly;
    private final Controleur ctrl;

    private final Dimension dim_case;
    // couleur du sable sur les case avec une tonne de sable
    private final static Color sand_col1 = new Color(245, 202, 68);
    // couleur du sable sur les case avec au moins deux tonnes de sable
    private final static Color sand_col2 = new Color(246, 193, 20);
    private final static Color oasis_col = new Color(25, 150, 255);
    private final static Color oasis_col2 = new Color(12, 75, 255);
    private final static Color tunnel_col = new Color(200, 100, 100);
    private final static Color sandT_col = new Color(108, 81, 38);
    private final static Color decolle_col = new Color(143, 203, 84);
    private final static Border case_border = BorderFactory.createLineBorder(Color.GRAY, 1);
    private final static Border player_border = BorderFactory.createLineBorder(Color.GREEN, 1);

    public VueCase(VueDesert vid, Controleur ctrl, Case c, Dimension dim){
        super(dim.width, dim.height);
        this.vueDesert = vid;
        this.ctrl = ctrl;
        this.dim_case = dim;

        this.setPreferredSize(dim_case);
        this.vueDesert.add(this);
        this.setVisible(true);
        this.setBorder(case_border);

        this.vCase = c;

        this.vuePieces.setBounds(this.dim_case.width-20, 1, 20, this.dim_case.height-2);


        this.casePly = new JPanel();
        this.casePly.setLayout(null);
        this.casePly.setPreferredSize(new Dimension(this.dim_case.width, 20));
        this.casePly.setOpaque(false);
        this.casePly.setVisible(true);
        this.add(this.casePly, BorderLayout.NORTH);

        this.infoHolder.setPreferredSize(new Dimension(10, 20));
        this.infoHolder.setForeground(sandT_col);
        this.add(this.infoHolder, BorderLayout.SOUTH);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Color c;

        if (this.vCase instanceof Oeil) {
            c = Color.GRAY;
        } else if (this.vCase instanceof Oasis && !this.vCase.estExplore()) {
            c = oasis_col;
        } else if (this.vCase instanceof Mirage && !this.vCase.estExplore()) {
            c = oasis_col;
        }else if (this.vCase instanceof Oasis) {
            c = oasis_col2;
        } else if (this.vCase.getSable() == 1) {
            c = sand_col1;
        } else if (this.vCase.getSable() >= 2) {
            c = sand_col2;
        } else if (this.vCase instanceof Tunnel && this.vCase.estExplore()){
            c = tunnel_col;
        } else if (this.vCase instanceof PisteDecollage && this.vCase.estExplore()){
            c = decolle_col;
        } else {
            c = Color.WHITE;
        }
        g.setColor(c);
        g.fillRect(0, 0, dim_case.width, dim_case.height);
        this.vuePiecesUpdate();
        this.casePlyUpdate();
        this.vueInfoUpdate();
    }

    /** Modifie qui set la case que represente la vue
     * @param c : la case represente par la vue
     * */
    public void setVCase(Case c){
        this.vCase = c;
    }

    private void casePlyUpdate(){
        this.casePly.removeAll();
        if ( ! (this.vCase instanceof Oeil) ) {
            ArrayList<Personnage> P = this.vCase.getJoueurs();

            for (int i = 0; i < P.size(); ++i) {
                Personnage ply = P.get(i);
                JLabel ply_label = new JLabel();
                ply_label.setText(String.valueOf(ply.getId()+1));
                ply_label.setHorizontalTextPosition(JLabel.CENTER);
                ply_label.setVerticalTextPosition(JLabel.CENTER);
                ply_label.setForeground(Color.BLACK);
                ply_label.setBounds(15*i, 0, 15, 20);
                if ( this.ctrl.getPlayer() == ply.getId())
                    ply_label.setBorder(player_border);

                this.casePly.add(ply_label);
            }
        }
    }

    protected void vueInfoUpdate(){
        if (! (this.vCase instanceof Oeil)) {
            String s;
            s = String.valueOf(this.vCase.getSable());
            if (this.vCase.estExplore()){
                s += " " + this.vCase.getType();
            }
            this.infoHolder.setText(s);
        } else {
            this.infoHolder.setText("");
        }
    }

    protected void vuePiecesUpdate(){
        this.vuePieces.removeAll();
        if ( ! (this.vCase instanceof Oeil )){
            ArrayList<Piece> pieces = this.vCase.getPieces();

            for (int i = 0; i < pieces.size(); ++i){
                vuePiece vPiece = new vuePiece(pieces.get(i));
                vPiece.setPreferredSize(new Dimension(10, 19));
                this.vuePieces.add(vPiece);
            }

        }

    }
    /** Methode qui permet d'interagir avec le controleur afin de faire bouger les personnages*/
    public void clicGauche(){
        if ( this.ctrl.getCtrlState() != Controleur.state.FinTour
                && this.ctrl.getCtrlState() != Controleur.state.Victoire
                && this.ctrl.getCtrlState() != Controleur.state.Defaite) {
            if (this.ctrl.getCtrlState() == Controleur.state.Nav_guide || this.ctrl.getCtrlState() == Controleur.state.NavAlp_select) {
                this.ctrl.navGuide(this.vCase);
            } else {
                this.ctrl.movePlayer(this.vCase);
            }
        }
        this.vueDesert.update();
    }

    /** Methode qui permet d'interagir avec le controleur afin de desabler les cases */
    public void clicDroit(){
        if ( this.ctrl.getCtrlState() != Controleur.state.FinTour
                && this.ctrl.getCtrlState() != Controleur.state.Victoire
                && this.ctrl.getCtrlState() != Controleur.state.Defaite) {
            int x = this.vCase.getX();
            int y = this.vCase.getY();
            this.ctrl.digPlayer(new Coord(x, y));
        }
        this.vueDesert.update();
    }

}
