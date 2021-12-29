import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Random;

public class Environnement extends JPanel{

    private String[][] map, mapAgent;
    private HashMap<Agent, int[]> hm_Agent = new HashMap<Agent, int[]>();
    private int longueur,largeur,iter,nbAgent, nbA, nbB,nbC, tailleMemory, ds, renouvellementAppel;
    private double kplus, kmoins, tauxErreur, r;
    JFrame maFenetre = new JFrame();

    public Environnement(int largeur, int longueur, int iter, double kplus, double kmoins, int nbAgent,
                         int nbA, int nbB, int tailleMemory, double erreur, JFrame maFenetre, int nbC, int ds,
                         double r, int renouvellementAppel) {
        this.map=new String[largeur][longueur];
        this.mapAgent=new String[largeur][longueur];
        this.longueur=longueur;
        this.largeur=largeur;
        this.iter=iter;
        this.kplus=kplus;
        this.kmoins=kmoins;
        this.nbAgent=nbAgent;
        this.nbA=nbA;
        this.nbB=nbB;
        this.tailleMemory=tailleMemory;
        this.tauxErreur = erreur;
        this.maFenetre = maFenetre;
        this.nbC=nbC;
        this.ds=ds;
        this.r=r;
        this.renouvellementAppel=renouvellementAppel;
        for(int i =0;i<largeur;i++){
            for(int j=0;j<longueur;j++){
                map[i][j]=".";
            }
        }
        initObjet(nbA,nbB, nbC);
        initAgent(nbAgent);
    }


    public void run() {
        System.out.println("Map initiale :");
        afficherMap();
        System.out.println();
        System.out.println();
        System.out.println("Map agent initiale :");
        afficherMapAgent();
        System.out.println();
        System.out.println();
        for(int i=0;i<iter;i++){
            for ( Agent key : hm_Agent.keySet() ) {
                key.action();
            }
            maFenetre.repaint();
        }
        System.out.println("Map Finale :");
        afficherMap();
        System.out.println();
        System.out.println();
        System.out.println("Map agent Finale :");
        afficherMapAgent();
    }

    public void initObjet(int nbA, int nbB, int nbC) {
        int x,y;
        Random random = new Random();

        while(nbA>0){
            x = random.nextInt(largeur);
            y = random.nextInt(longueur);
            if(map[x][y]=="."){
                map[x][y]= "A";
                nbA--;
/*                System.out.println("Obj A");
                System.out.print("Ligne= "+x);
                System.out.println(" Colonne= "+y);
*/            }
        }
        while (nbB>0){
            x = random.nextInt(largeur);
            y = random.nextInt(longueur);
            if(map[x][y]=="."){
                map[x][y]= "B";
                nbB--;
/*                System.out.println("Obj B");
                System.out.print("Ligne= "+x);
                System.out.println(" Colonne= "+y);
*/            }
        }
        while (nbC>0){
            x = random.nextInt(largeur);
            y = random.nextInt(longueur);
            if(map[x][y]=="."){
                map[x][y]= "C";
                nbC--;
/*                System.out.println("Obj B");
                System.out.print("Ligne= "+x);
                System.out.println(" Colonne= "+y);
*/            }
        }
    }

    public HashMap<Agent,int[]> initAgent(int nbAgent) {
        int x,y;
        Random random = new Random();
        while(nbAgent>0){
            x = random.nextInt(largeur);
            y = random.nextInt(longueur);
            if(map[x][y]=="."){
                Agent agent = new Agent(this,kplus,kmoins,"",tailleMemory,renouvellementAppel);
                int[] coordXY = new int[2];
                coordXY[0] = x;
                coordXY[1] = y;
                if(hm_Agent.containsValue(coordXY)==false){
                    hm_Agent.put(agent,coordXY);
                    mapAgent[x][y] = "Z";
                    nbAgent--;
                    System.out.println("X= " + x + " Y = " + y);
                }
            }
        }
        return hm_Agent;
    }

    public HashMap<Integer,Direction> perceptionSeDeplacer(Agent agent) {
        HashMap<Integer, Direction> coordonneePossible = new HashMap<>();
        int[] coordonneAgent = hm_Agent.get(agent);
        this.largeur=largeur-1;
        this.longueur=longueur-1;
        mapAgent[coordonneAgent[0]][coordonneAgent[1]]=".";
        if(coordonneAgent[0]==0){
            if(coordonneAgent[1]==0){
                coordonneePossible.put(0, new Direction(0, 1));
                coordonneePossible.put(1, new Direction(1, 1));
                coordonneePossible.put(2, new Direction(1, 0));
            }else if(coordonneAgent[1]==longueur){
                coordonneePossible.put(0, new Direction(0, longueur-1));
                coordonneePossible.put(1, new Direction(1, longueur-1));
                coordonneePossible.put(2, new Direction(1, longueur));
            }else{
                coordonneePossible.put(0, new Direction(0, coordonneAgent[1]-1));
                coordonneePossible.put(1, new Direction(0, coordonneAgent[1]+1));
                coordonneePossible.put(2, new Direction(1, coordonneAgent[1]-1));
                coordonneePossible.put(3, new Direction(1, coordonneAgent[1]));
                coordonneePossible.put(4, new Direction(1, coordonneAgent[1]+1));
            }
        }else if(coordonneAgent[1]==0){
            if(coordonneAgent[0]==largeur){
                coordonneePossible.put(0, new Direction(largeur-1, 0));
                coordonneePossible.put(1, new Direction(largeur-1, 1));
                coordonneePossible.put(2, new Direction(largeur, 1));
            }else{
                coordonneePossible.put(0, new Direction(coordonneAgent[0]-1, 0));
                coordonneePossible.put(1, new Direction(coordonneAgent[0]+1, 0));
                coordonneePossible.put(2, new Direction(coordonneAgent[0]-1, 1));
                coordonneePossible.put(3, new Direction(coordonneAgent[0], 1));
                coordonneePossible.put(4, new Direction(coordonneAgent[0]+1, 1));
            }
        }else if(coordonneAgent[0]==largeur){
            if(coordonneAgent[1]==longueur){
                coordonneePossible.put(0, new Direction(largeur, longueur-1));
                coordonneePossible.put(1, new Direction(largeur-1, longueur-1));
                coordonneePossible.put(2, new Direction(largeur-1, longueur));
            }else{
                coordonneePossible.put(0, new Direction(largeur, coordonneAgent[1]-1));
                coordonneePossible.put(1, new Direction(largeur-1, coordonneAgent[1]-1));
                coordonneePossible.put(2, new Direction(largeur-1, coordonneAgent[1]));
                coordonneePossible.put(3, new Direction(largeur-1, coordonneAgent[1]+1));
                coordonneePossible.put(4, new Direction(largeur, coordonneAgent[1]+1));
            }
        }else if(coordonneAgent[1]==longueur){
            coordonneePossible.put(0, new Direction(coordonneAgent[0]-1, longueur));
            coordonneePossible.put(1, new Direction(coordonneAgent[0]-1, longueur-1));
            coordonneePossible.put(2, new Direction(coordonneAgent[0], longueur-1)); //
            coordonneePossible.put(3, new Direction(coordonneAgent[0]+1, longueur-1));
            coordonneePossible.put(4, new Direction(coordonneAgent[0]+1, longueur));
        }else{
            coordonneePossible.put(0, new Direction(coordonneAgent[0] - 1, coordonneAgent[1] - 1));
            coordonneePossible.put(1, new Direction(coordonneAgent[0] - 1, coordonneAgent[1]));
            coordonneePossible.put(2, new Direction(coordonneAgent[0] - 1, coordonneAgent[1] + 1));
            coordonneePossible.put(3, new Direction(coordonneAgent[0], coordonneAgent[1] - 1));
            coordonneePossible.put(4, new Direction(coordonneAgent[0], coordonneAgent[1] + 1));
            coordonneePossible.put(5, new Direction(coordonneAgent[0] + 1, coordonneAgent[1] - 1));
            coordonneePossible.put(6, new Direction(coordonneAgent[0] + 1, coordonneAgent[1]));
            coordonneePossible.put(7, new Direction(coordonneAgent[0] + 1, coordonneAgent[1] + 1));
        }
        this.largeur=largeur+1;
        this.longueur=longueur+1;
        return coordonneePossible;
    }

    public void deplacement(Agent agent, Direction mouvement) {
        int[] tab = new int[2];
        tab[0]=mouvement.getX();
        tab[1]=mouvement.getY();
        mapAgent[tab[0]][tab[1]]="Z";
        agent.addMemoire(map[tab[0]][tab[1]]);
        hm_Agent.get(agent)[0] = tab[0];
        hm_Agent.get(agent)[1] = tab[1];
    }

    public String perceptionPrendre(Agent a) {
        return map[hm_Agent.get(a)[0]][hm_Agent.get(a)[1]];
    }

    public void prendre(Agent a){
        map[hm_Agent.get(a)[0]][hm_Agent.get(a)[1]]=".";
    }

    public String perceptionDeposer(Agent a) {
        return map[hm_Agent.get(a)[0]][hm_Agent.get(a)[1]];
    }

    public void depot(Agent a, String o) {
//        System.out.println("Depot objet = "+o);
//        System.out.println("Aux coordonnées: "+ hm_Agent.get(a)[0] + " " + hm_Agent.get(a)[1]);
        map[hm_Agent.get(a)[0]][hm_Agent.get(a)[1]]=o;
    }

    public void afficherMap(){
        for(int i =0;i<largeur;i++){
            for(int j=0;j<longueur;j++){
                System.out.print(" | " + map[i][j]);
            }
            System.out.print(" |");
            System.out.println();
        }
    }

    public void afficherMapAgent() {
        System.out.println();
        for(int i =0;i<largeur;i++){
            for(int j=0;j<longueur;j++){
                if(mapAgent[i][j]==null){
                    System.out.print(" | .");
                }else{
                    System.out.print(" | " + mapAgent[i][j]);
                }
            }
            System.out.print(" |");
            System.out.println();
        }
    }

    public double getTauxErreur() {
        return tauxErreur;
    }

    public Agent diffusionSignal(Agent a){
        int x,y,debutCol,debutLig,finCol,finLig;

        x = hm_Agent.get(a)[0];     //Coordonnés de l'agent qui demande de l'aide
        y = hm_Agent.get(a)[1];

        if(x+ds>largeur){
            finCol=largeur;
        }else{
            finCol=x+ds;
        }
        if(y+ds>longueur){
            finLig=longueur;
        }else{
            finLig = y+ds;
        }

//        System.out.println("Coordonnées agent: " + x + " " + y);
        for(debutCol = x-ds;debutCol<finCol;debutCol++){    //Pb dans le repérage des agents ??
            if(debutCol<0){
                debutCol=0;
            }
            for (debutLig = y-ds; debutLig < finLig; debutLig++) {
                if(debutLig<0){
                    debutLig=0;
                }
//                System.out.println("Analyse de la case : "+debutCol +" " +debutLig);
                for (Agent key : hm_Agent.keySet()) {
                    if (hm_Agent.get(key)[0] == debutCol && hm_Agent.get(key)[1] == debutLig && hm_Agent.get(a) != hm_Agent.get(key)) {
                        if (hm_Agent.get(key.getObjetPorte()) == null) {        //Si l'agent ne porte rien
                            return key;
                        }
                    }
                }
            }
        }
        return null;
    }

    public boolean seDeplacerV2(Agent agentCourant, Agent agentDest) {
        int xSource, ySource, xDest, yDest,xDeplacement, yDeplacement;
        int [] tempo = new int[2];
        xDest = hm_Agent.get(agentDest)[0];
        yDest = hm_Agent.get(agentDest)[1];
        xSource = hm_Agent.get(agentCourant)[0];
        ySource = hm_Agent.get(agentCourant)[1];
        xDeplacement=xDest-xSource;
        yDeplacement=yDest-ySource;

        if(xDeplacement != 0 || yDeplacement != 0){
            if(xDeplacement<0){
                tempo[0] = xSource-1;
                tempo[1] = ySource;
                hm_Agent.replace(agentCourant,tempo);
//                coordonneAgent();
                return false;
            }else if(xDeplacement>0){
                tempo[0] = xSource+1;
                tempo[1] = ySource;
                hm_Agent.replace(agentCourant,tempo);
//                coordonneAgent();
                return false;
            }

            if(yDeplacement<0){
                tempo[0]=xSource;
                tempo[1]=ySource-1;
                hm_Agent.replace(agentCourant,tempo);
//                coordonneAgent();
                return false;
            }else if(yDeplacement>0){
                tempo[0]=xSource;
                tempo[1]=ySource+1;
                hm_Agent.replace(agentCourant,tempo);
//                coordonneAgent();
                return false;
            }
        }else{
//            coordonneAgent();
            return true;
        }
        //       coordonneAgent();
        return false;
    }

    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Dimension size = getSize();
        int w = size.width;
        int h = size.height;
        Random r = new Random();
        for(int i=0;i<largeur;i++){
            for(int j=0; j<longueur;j++){
                if(map[i][j]=="A"){
                    g2d.setColor(Color.blue);
                    int x = j * w / map.length;
                    int y = i * h / map.length;
                    g2d.drawString("⚈",x, y);
                }else if(map[i][j]=="B"){
                    g2d.setPaint(Color.red);
                    int x = j * w / map.length;
                    int y = i * h / map.length;
                    g2d.drawString("⚈",x, y);
                }else if(map[i][j]=="C"){
                    g2d.setPaint(Color.ORANGE);
                    int x = j * w / map.length;
                    int y = i * h / map.length;
                    g2d.drawString("⚈",x, y);
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    public void coordonneAgent(){
        for (Agent key : hm_Agent.keySet()) {
            System.out.println(key);
            System.out.println("X = "+hm_Agent.get(key)[0]);
            System.out.println("Y = "+hm_Agent.get(key)[1]);
        }
    }

}