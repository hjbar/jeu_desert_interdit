package controleur;

import modele.*;
import modele.cases.Case;
import modele.cases.Oasis;
import modele.cases.Tunnel;
import modele.joueurs.*;
import vue.vueMetereologue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** Classe Controleur qui sert d'intermediaire entre la vue principale (celle avec le plateauà et le modele
 * Observable par vueControleur, cueTasTempete
 */
public class Controleur extends Observable implements ActionListener {
    /** La classe state permet de controler la façon dont reagi le programme aux differents inputs
     * Wainting : etat general du controleur
     * FinTour : le joueur n'a plus d'action ou a commence à tirer des cartes tempetes. Plus aucune action autre que tirer des cartes tempetes et appuyer sur le bouton Fin de tour dont dispoinible
     * Peau : L'utilisateur a appuye sur le bouton Peau. Le joueur peut prendre de l'eau à un autre joueur.
     * Deau : L'utilisateur a appuye sur le bouton Deau. Le joueur peut donner de l'eau à un autre joueur.
     * Alp_select : L'alpiniste peut selectionner un autre joueur qu'il va transporter.
     * Nav_select : La navigatrice selectionne le personnage qu'elle deplace.
     * NavAlp_select : La navigatrice a choisi l'alpiniste, l'alpiniste peut choisir un personnage qui sera guidé avec lui.
     * Nav_guide : La navigatrice deplace un personnage.
     * Victoire : La partie est gagnée.
     * Defaite : La partie est perdue.
     * */
    public enum state{
      Waiting, FinTour, Peau, Deau, Alp_select, Nav_select, NavAlp_select, Nav_guide, Victoire, Defaite;

        /** methode qui converti un objet de type etat en chaine de caractere
         * @return une chaine de caractere correspondant au nom de l'etat du jeu actuel
         */
        public String toString(){
          switch (this){
              case Waiting:
                  return "wainting";
              case FinTour:
                  return "finTour";
              case Peau:
                  return "Prend eau";
              case Deau:
                  return "Donne eau";
              case Alp_select:case NavAlp_select:
                  return "Alp Select";
              case Nav_select:
                  return "Nav Select";
              case Nav_guide:
                  return "Guide";
              case Defaite:
                  return "Defaite";
              default:
                  return "Victoire";
          }
        }
    }
    /** Le modele du jeu, toutes interactions avec le modele se fait a travers cette attribut  */
    private final Modele modele;
    /** L'id-1 du joueur à qui c'est le tour */
    private int player;
    /** Le nombre d'action restante dans le tour */
    private int nbAction;
    /** L'attribut de state qu'on utilise pour determiner quelle fonction peuvent être utilise */
    private state ctrlState;
    /** Id du joueur qui est transporté par la navigatrice lors de l'utilisation de son action speciale */
    private int secondary_persNav;
    /** Le nombre de deplacement que la navigatrice peut encore faire faire aux personnage secondaire */
    private int nbNavMove;
    /** Le nombre de carte qu'il reste a piocher avant de ne pouvoir mettre fin au tour */
    private int nbPiocheply;
    private final ControleurMeteorologue ctrlM;

    /** Constructeur du controleur
     * Modele mod : modele du jeu en cours
     */
    public Controleur(Modele mod){
        this.player = 0;
        this.modele = mod;
        this.ctrlState = state.Waiting;
        this.nbAction = 4;
        // On initialise secondary_persNav à -1, pour indiquer qu'il n'y a pas de personnage secondaire couramment
        this.secondary_persNav = -1;
        this.nbNavMove = 0;
        this.nbPiocheply = (int) this.modele.getTempete_niveau();
        this.ctrlM = new ControleurMeteorologue(this);
    }

    /** Methode qui gere la reponse aux inputs des bouttons de vueControlleur
     */
    @Override
    public void actionPerformed(ActionEvent e){
        // On verifie si le joueur peut encore performer une action
        boolean fin = this.ctrlState == state.Victoire || this.ctrlState == state.Defaite;
        boolean finTour = this.ctrlState == state.FinTour;
        boolean act = this.nbAction > 0 && ! fin && ! finTour;

        if ( ! fin && e.getActionCommand() == "Fin de tour") {
            // Lors d'un changement de tour, on change le joueuer qui joue et on reinitialise
            this.player = (this.player+1) % (this.modele.getPersonnages().size());
            // les attributs qui track le nombre d'action restantes dans le tour
            this.nbAction = 4;
            // Et le nombre de carteTempete a tiree
            this.nbPiocheply = (int) this.modele.getTempete_niveau();
            this.setCtrlState(state.Waiting);
        } else if ( act && e.getActionCommand() == "Explorer"){
            Personnage P = this.getCurrentPly();
            // Le joueur explore la case si le boutton Explorer est appuye
            P.explorer();
            // On peut annuler une action speciale en appuyant sur Explorer
            this.setCtrlState(state.Waiting);
            // On decremente le nombre d'action restante dans le cours
            this.decrNbAction();
        } else if ( act && e.getActionCommand() == "Ramasser"){
            this.getCurrentPly().prendrePiece();
            // On peut annuler une action speciale en appuyant sur Ramasser
            this.setCtrlState(state.Waiting);
            // On decremente le nombre d'action restante
            this.decrNbAction();
        } else if ( act && e.getActionCommand() == "Poser") {
            // Le joueur pose une piece s'il appuie sur le bouton Poser
            this.getCurrentPly().poserPiece();
            // On peut annuler une action speciale en appuyant sur Pose
            this.setCtrlState(state.Waiting);
            // On decremente le nombre d'action restante
            this.decrNbAction();
            // Une des conditions de victoire est d'avoir posé toutes les pieces sur la piste de decollage
            // On verifie si la partie est remporte à chaque fois qu'une piece est poser
            if ( this.modele.victoire() )
                this.setCtrlState(state.Victoire);
        } else if ( act && e.getActionCommand() == "Donner Eau"){
            // L'action donner de l'eau est desormais realisable.
            // Le joueur peut clicker sur un autre personnage pour lui donner de l'eau
            // L'action annule d'autre action speciale
            this.setCtrlState(state.Deau);
        } else if ( act && e.getActionCommand() == "Prendre Eau"){
            // L'action prendre de l'eau est desormais realisable.
            // Le joueur peut clicker sur un autre personnage pour lui prendre de l'eau
            // L'action annule d'autre action speciale
            this.setCtrlState(state.Peau);
        } else if ( act && ( e.getActionCommand() == "Alpiniste" || e.getActionCommand() == "Navigatrice" || e.getActionCommand() == "Porteuse d'eau" || e.getActionCommand() == "Meteorologue")){
            // Cette fonction gere le bouton qui change de nom, en fonction du role du joueur
            this.actionSpePlayer();
        }
    }
    /** Methode qui renvoie le modele du jeu*/
    protected Modele getModele() {
        return modele;
    }
    /** Methode qui renvoie l'id du joueur courant */
    public int getPlayer(){
        return this.player;
    }

    /** Methode qui renvoie le nombre d'action restante */
    public int getNbAction() { return this.nbAction; }
    /** Methode qui renvoie l'etat du jeu */
    public state getCtrlState() { return this.ctrlState; }
    /** Methode qui renvoie le nombre de carte qu'il reste à piocher */
    public int getNbPiocheply() { return this.nbPiocheply; }

    /** Methode qui renvoie le personnage du joueur courant */
    public Personnage getCurrentPly() { return this.modele.getPers(this.player); }

    /** Methode qui renvoie le controleurMeteorologique de ce controleur */
    public ControleurMeteorologue getCtrlM() { return this.ctrlM; }

    /** Methode qui decremente le nombre d'action restante et qui met Fin au tour du joueur s'il n'y a plus d'action restante */
    protected void decrNbAction() {
        --nbAction;
        if (nbAction == 0)
            this.setCtrlState(state.FinTour);
        // On met à jour les observers pour que la vue affiche le nombre d'action restante au joueur
        this.notifyObservers();
    }
    /** decremente le nombre de carte a piocher dans un tour*/
    protected void decrNbPiochePly() {
        --nbPiocheply;
        // On noitife les observers pour que la vue affiche le nombre de carte à piocher
        this.notifyObservers();
    }

    /** Methode remplacant l'etat actuel du jeu par l'etat s. */
    private void setCtrlState(state s){
        state tmp = this.ctrlState;
        this.ctrlState = s;
        // Une fois que l'on veut arreter de faire naviguer un personnage on reset le personnage secondaire
        // Ces conditions permettent d'arreter de guider un joueur avec la navigatrice a tout moment
        // la premiere des deux conditions du || permet d'eviter un bug ou la navigatrice à -1 action restante
        // la seconde permet d'arreter le guidage de l'alpiniste avec la navigatrice pendant la selection du
        // personnage que l'alpiniste veut deplacer avec lui
        if ( tmp == state.Nav_guide || ( tmp == state.NavAlp_select && s != state.Nav_guide)){
            System.out.println("please");
            Personnage second_pers = this.modele.getPers(secondary_persNav);
            if ( second_pers instanceof Alpiniste)
                // On s'assure que le personne qui etait guider avec l'alpiniste, ne soit plus lié à l'alpiniste
                // pour pas qu'il soit deplacer lors du prochain tour de l'alpiniste
                ((Alpiniste) second_pers).setPers(null);
            this.secondary_persNav = -1;
            // Si la navigatrice a fait deplacer un personnage au moins une fois alors la navigatrice perd une action
            if ( this.nbNavMove < 3) {
                this.nbNavMove = 0;
                // le bug dont a parler avant se passe ici parce que dans decrNbAction on peut appeler setCtrlState(state.FinTour)
                if (s != state.FinTour)
                    this.decrNbAction();
            }
        }
        // On notifie la vue du changement d'etat afin qu'elle puisse affichier le bon etat à l'ecran.
        this.notifyObservers();
    }

    /** Methode qui tire une carteTempete et applique son effet au modele */
    public void piocheCarteTempete() {
        // S'il n'y a plus de carte dans le paquet, on le reinitialise
        if (this.modele.getTdd().getNbCarte() == 0)
            this.modele.getTdd().resetPaquet();
        // S'il y a encore une carte dans le paquet on en tire une applique sont effet
        if ( this.nbPiocheply > 0) {
            this.modele.getTdd().pioche().effetCarte();
            --this.nbPiocheply;
            // Les actions de tempete peuvent entrainer des fins de parties, on verifie donc si la partie est perdu
            // apres avoir tirer une carte
            if (this.modele.defaite())
                this.setCtrlState(state.Defaite);
            else
                // Lorsqu'un joueur tire une carte alors son tour s'arrete (il ne peut que tirer des cartes tempetes)
                this.setCtrlState(state.FinTour);
        }
    }

    /** Methode deplacant le joueur à la case c passe en parametre si le mouvement est un mouvement reglementaire
     * @param c : La case sur laquelle le joueur a clique
     * */
    public void movePlayer(Case c){
        Personnage ply = this.getCurrentPly();
        int x = c.getX();
        int y = c.getY();
        // On calcule la distance du deplacement (distance induite par la norme 1 sur R2)
        int dx = x - ply.getPos().getX();
        int dy = y - ply.getPos().getY();
        int norm = Math.abs(dx) + Math.abs(dy);
        this.setCtrlState(state.Waiting);
        // Si la case sur laquelle le joueur a clique est un tunnel et que le joueur
        // est un tunnel alors on deplace le joueur jusqu'au tunnel
        boolean tunnel = ply.getPos() instanceof Tunnel
                && ply.getPos().estExplore()
                && c instanceof Tunnel && c.estExplore()
                && ((c.getSable() < 2 && ply.getPos().getSable() < 2) || c.haveAlpiniste()) ;
        if ( norm > 0 && tunnel ) {
            this.decrNbAction();
            ply.setCoord(new Coord(x, y));
            if ( ply instanceof Alpiniste)
                ((Alpiniste) ply).setPers(null);
        // Si la distance à la case est de 1 alors on deplace le joueur à la case
        } else if ( norm == 1 && ply.deplacer(Coord.coordToDir(new Coord(dx, dy)))){
            this.decrNbAction();
            if ( ply instanceof Alpiniste)
                ((Alpiniste) ply).setPers(null);
        // Si le joueur est l'Explorateur et que la distance est de deux alors on deplace le joueur sur la case (deplacement en diagonale)
        } else if ( ply instanceof Explorateur && norm == 2 && ((Explorateur) ply).deplacer(Coord.coordToDirExplo(new Coord(dx, dy)))){
            this.decrNbAction();
        }
        if ( this.modele.victoire() ){
            this.setCtrlState(state.Victoire);
        }
    }

    /** Methode qui desable la case C, si la case est accessible au joueur
     * @param c : La case sur laquelle le joueur a clique
     * */
    public void digPlayer(Coord c){
        Personnage ply = this.getCurrentPly();
        // On calcule la distance à la case que l'on veut desabler
        int dx = c.getX() - ply.getPos().getX();
        int dy = c.getY() - ply.getPos().getY();
        int norm = Math.abs(dx) + Math.abs(dy);
        this.setCtrlState(state.Waiting);
        // si la distance est strictement inferieur à 2 alors on desable
        if ( norm < 2 && ply.desabler(Coord.coordToDir(new Coord(dx, dy)))) {
            this.decrNbAction(); // On ne reduit le nombre d'action uniqument si la case a ete desable
        // si la distance est egale à 2 et que le joueur est l'explorateur alors on desable
        } else if ( ply instanceof Explorateur && norm == 2 && ((Explorateur) ply).desabler(Coord.coordToDirExplo(new Coord(dx, dy)))){
            this.decrNbAction(); // On ne reduit le nombre d'action uniqument si la case a ete desable
        }
    }

    /** Methode qui fait un transfert d'eau du joueur au personnage P si possible
     * @param P : Le personnage sur lequel le joueur a clique.
     */
    public void donneEauPlayer(Personnage P){
        Personnage ply = this.getCurrentPly();
        Case cP = P.getPos();
        Case cply = ply.getPos();
        // On traite le cas où c'est la porteuse d'eau qui realise l'action
        boolean pe = ply instanceof PorteuseEau &&  Math.abs(cply.getX() - cP.getX()) <= 1 && Math.abs(cply.getY() - cP.getY()) <= 1;
        // La porteuse d'eau peut donner de l'eau au personnage sur des cases adjacentes a la sieen
        // Les autres joueurs ne peuvent donner de l'eau uniquement au personnage sur leur propre case
        if (pe || (ply.getId() != P.getId() && cply.getX() == cP.getX() && cply.getY() == cP.getY() ) ) {
            this.setCtrlState(state.Waiting);
            if ( ply.donneEau(1, P) ) {
                this.decrNbAction(); // On ne reduit le nombre d'action qui si l'eau a bien ete donne
            }
        }
    }
    /** Methode qui fait un transfert d'eau du personnage P au joueur  si possible
     * @param P : Le personnage sur lequel le joueur a clique.
     */
    public void prendreEauPlayer(Personnage P){
        Personnage ply = this.getCurrentPly();
        Case cP = P.getPos();
        Case cply = ply.getPos();
        boolean pe = P instanceof PorteuseEau &&  Math.abs(cply.getX() - cP.getX()) <= 1 && Math.abs(cply.getY() - cP.getY()) <= 1;
        // Les autres joueurs peuvent prendre de l'eau au personnage sur leur propre case et a la porteuse d'eau sur les cases
        // adjacentes
        if (pe || (ply.getId() != P.getId() && cply.getX() == cP.getX() && cply.getY() == cP.getY() )) {
            this.setCtrlState(state.Waiting);
            if ( P.donneEau(1, ply) )
                this.decrNbAction(); // On ne reduit le nombre d'action qui si l'eau a bien ete donne
        }
    }

    /** Methode qui permet de gerer ce qui se passe lorsque le bouton d'action des personnages est appuyés **/
    private void actionSpePlayer(){
        Personnage ply = this.getCurrentPly();
        // Si le joueur est la porteuse et qu'il est sur une case Oasis exploree, il rempli sa gourde
        if ( ply instanceof PorteuseEau && ply.getPos() instanceof Oasis && ply.getPos().estExplore()) {
            ply.remplirGourde(2);
            this.decrNbAction();
        // Si le joueur est l'alpiniste alors on change l'etat du jeu en mode selection alpiniste
        } else if ( ply instanceof Alpiniste ) {
            this.setCtrlState(state.Alp_select);
        // Si le joueur est la navigatrice alors on change l'etat du jeu en mode selection navigatrice
        } else if ( ply instanceof Navigatrice){
            this.setCtrlState(state.Nav_select);
        // Si le joueur est le meteorologue alors on ouvre une nouvelle fenetre et on initialise le conrolleur de cette fenetre
        } else if ( ply instanceof Meteorologue){
            System.out.println("please");
            this.ctrlM.setCloseWindow(false);
        }
    }

    /** Methode qui assigne à l'alpiniste le joueur avec lequel il se deplace
     * @param P : Personnage assigne à l'alpiniste
     * */
    public void assignToAlp(Personnage P){
        Alpiniste pers;
        boolean plyEstAlp = this.getCurrentPly() instanceof Alpiniste;
        // la methode et utilise dans le contexte ou le joueur est l'alpiniste
        if ( plyEstAlp )
            pers = (Alpiniste) this.getCurrentPly();
        // ou lorsque la navigatrice guide l'alpiniste
        else
            pers = (Alpiniste) this.modele.getPers(secondary_persNav);
        // Un joueur peut etre assigne a condition qu'il soit dans la meme case que l'alpiniste
        if ( pers.getId() != P.getId() && pers.getPos().getX() == P.getPos().getX() && pers.getPos().getY() == P.getPos().getY()) {
            pers.setPers(P);
            // En fonction du context dans lequel on utilise la fonction l'etat du jeu est different
            if ( plyEstAlp )
                // Etat normal lors d'un mouvement
                this.setCtrlState(state.Waiting);
            else
                // Etat lorsque le joueur est guide par la navigatrice
                this.setCtrlState(state.Nav_guide);
        }
    }

    /** Methode qui assigne au personnage secondaire le personnage P
     * @param P : Personnage choisi par la navigatrice
     */
    public void assignToNav(Personnage P){
        Navigatrice ply = (Navigatrice) this.getCurrentPly();
        // la navigatrice ne peut pas se guider elle meme
        if ( ply.getId() != P.getId() ) {
            this.secondary_persNav = P.getId();
            // Si l'alpiniste est choisi alors on peut utiliser son action speciale
            if (P instanceof Alpiniste)
                this.setCtrlState(state.NavAlp_select);
            else
                this.setCtrlState(state.Nav_guide);
            this.nbNavMove = 3;
        }
    }

    /** Methode qui realise le deplacement du personnage secondaire à la case si le mouvement est reglementaire
     * @param c : Case cliquee par la navigatrice.
     */
   public void navGuide(Case c){
       if ( this.nbNavMove > 0){
           // deplace le personnage secondaire de la meme maniere que l'on deplace le joueur dans MovePlayer
           Personnage second_pers = this.modele.getPers(secondary_persNav);
           int x = c.getX();
           int y = c.getY();
           int dx = x - second_pers.getPos().getX();
           int dy = y - second_pers.getPos().getY();
           int norm = Math.abs(dx) + Math.abs(dy);
           boolean tunnel = second_pers.getPos() instanceof Tunnel
                   && second_pers.getPos().estExplore()
                   && c instanceof Tunnel && c.estExplore()
                   && (( c.getSable() < 2 && second_pers.getPos().getSable() < 2) || c.haveAlpiniste());
           if ( norm > 0 && tunnel ) {
               --nbNavMove; // On reduit le nombre de deplacement que la navigatrice peut faire faire au personnage secondaire
               second_pers.setCoord(new Coord(x, y));
           } else if (norm == 1 && second_pers.deplacer(Coord.coordToDir(new Coord(dx, dy)))){
               --nbNavMove; // On reduit le nombre de deplacement que la navigatrice peut faire faire au personnage secondaire
           } else if ( second_pers instanceof Explorateur && norm == 2 && ( (Explorateur) second_pers).deplacer(Coord.coordToDirExplo(new Coord(dx, dy))))
               --nbNavMove; // On reduit le nombre de deplacement que la navigatrice peut faire faire au personnage secondaire
       }
       if ( this.ctrlState == state.NavAlp_select) // La navigatrice guide l'alpiniste tout seul
           this.setCtrlState(state.Nav_guide);
       if ( nbNavMove == 0)
           this.setCtrlState(state.Waiting); // si les mouvements sont epuise la partie reprend son cours normal
    }



}
