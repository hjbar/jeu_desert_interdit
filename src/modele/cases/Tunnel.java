package modele.cases;

import modele.Plateau;

import java.util.ArrayList;

public class Tunnel extends Case {

    private static ArrayList<Tunnel> tunnelRelie = new ArrayList<>();

    public Tunnel(Plateau plateau, int x, int y){
        super(plateau, x, y);
    }

    // Renvoie le nom de la case
    @Override
    public String getType() {
        return "Tunnel";
    }

    /**
     * explore si pas deja fait
     * ajoute cette case a la liste des tunnels dispinible
     * @return vrai si maintenant exploree, false sinon
     */
    @Override
    public boolean Explore() {
        boolean b = super.Explore();
        if (b)  this.addTunnel(this);
        return b;
    }

    // Ajoute le tunnel en parametre a la liste des tunnels disponibles
    private void addTunnel(Tunnel t){
        this.tunnelRelie.add(t);
    }

    /**
     * Renvoie la liste des tunnels accessibles
     * @return ArrayList<Tunnel>
     */
    public ArrayList<Tunnel> TunnelAccessible(){
        ArrayList<Tunnel> ta = new ArrayList<>();
        for (int i = 0; i < this.tunnelRelie.size(); ++i) {
            if (this.tunnelRelie.get(i).getSable() < 2){
                ta.add(this.tunnelRelie.get(i));
            }
        }
        return ta;
    }
}