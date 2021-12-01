import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Environnement {

    private String[][] map;
    private HashMap<Agent, int[]> hm_Agent = new HashMap<Agent, int[]>();
    private int longueur,largeur,iter,nbAgent, nbA, nbB, tailleMemory;
    private double kplus, kmoins;
    public int[] tab = new int[2];

    public Environnement(int longueur, int largeur,int iter, double kplus, double kmoins,int nbAgent, int nbA, int nbB,int tailleMemory) {
        this.map=new String[longueur][largeur];
        this.longueur=longueur;
        this.largeur=largeur;
        this.iter=iter;
        this.kplus=kplus;
        this.kmoins=kmoins;
        this.nbAgent=nbAgent;
        this.nbA=nbA;
        this.nbB=nbB;
        this.tailleMemory=tailleMemory;
        for(int i =0;i<longueur;i++){
            for(int j=0;j<largeur;j++){
                map[i][j]=".";
            }
        }
        initAgent(longueur, largeur,nbAgent,tailleMemory);
        initObjet(longueur, largeur,nbA,nbB);
    }

    public void run() {

        System.out.println("Etat initial :");
        System.out.println("A = Objet A ; B = Objet B");
        this.toString();
        System.out.println("------------------------------------------");
        for(int i=0;i<iter;i++){
            for ( Agent key : hm_Agent.keySet() ) {
                key.action();
//                System.out.println(key.getMemory());
            }
            this.toString();
        }
//        this.toString();
    }

    private HashMap<Agent,int[]> initAgent(int longeur, int largeur,int nbAgent,int tailleMemory) {
        //Initialisation des agents
        int x, y;
        while(nbAgent>0){
            Random random = new Random();
            x = random.nextInt(longeur);
            y = random.nextInt(largeur);
            if(map[x][y]!="Z") {
                Agent agent = new Agent(this,kplus,kmoins,"",tailleMemory);
//                map[x][y] = "Z";
                int[] coordXY = new int[2];
                coordXY[0] = x;
                coordXY[1] = y;
                hm_Agent.put(agent,coordXY); //Place les agents de manière random
                nbAgent--;
            }
        }
        return hm_Agent;
    }

    public void initObjet(int longueur, int largeur, int nbA, int nbB){
        int x,y;
        String[][] coord = new String[longueur][largeur];
        Random random = new Random();
        while(nbA>0){
            x = random.nextInt(longueur);
            y = random.nextInt(largeur);
            coord[x][y]="x";
            if(map[x][y]!="A" && map[x][y]!="B" && map[x][y]!="Z"){
                map[x][y]= "A";
                nbA--;
            }
        }
        while (nbB>0){
            x = random.nextInt(longueur);
            y = random.nextInt(largeur);
            coord[x][y]="x";
            if(map[x][y]!="A" && map[x][y]!="B" && map[x][y]!="Z"){
                map[x][y]= "B";
                nbB--;
            }
        }
    }

    public HashMap<Integer ,Direction> perceptionSeDeplacer(Agent a) {
        HashMap<Integer, Direction> coordonneePossible = new HashMap<>();
        int[] coordonneAgent = hm_Agent.get(a);

        coordonneePossible.put(0, new Direction(coordonneAgent[0] - 1, coordonneAgent[1] - 1));
        coordonneePossible.put(1, new Direction(coordonneAgent[0] - 1, coordonneAgent[1]));
        coordonneePossible.put(2, new Direction(coordonneAgent[0] - 1, coordonneAgent[1] + 1));
        coordonneePossible.put(3, new Direction(coordonneAgent[0], coordonneAgent[1] - 1));
        coordonneePossible.put(4, new Direction(coordonneAgent[0], coordonneAgent[1] + 1));
        coordonneePossible.put(5, new Direction(coordonneAgent[0] + 1, coordonneAgent[1] - 1));
        coordonneePossible.put(6, new Direction(coordonneAgent[0] + 1, coordonneAgent[1]));
        coordonneePossible.put(7, new Direction(coordonneAgent[0] + 1, coordonneAgent[1] + 1));

        return coordonneePossible;
    }

    public String toString(){       //Afficher la grille avec les Objets A et B
        for(int i =0;i<getLongueur();i++){
            System.out.println();
            for(int j=0;j<getLargeur();j++){
                System.out.print(" | ");
                System.out.print(map[i][j]);
            }
        }
        System.out.println();
        System.out.println();
        return null;
    }

    public int getLongueur() {
        return longueur;
    }

    public int getLargeur() {
        return largeur;
    }

    public void deplacement(Agent agent, Direction direction) {
//        map[hm_Agent.get(agent)[0]][hm_Agent.get(agent)[1]]="Z";
        tab[0]=direction.getX();
        tab[1]=direction.getY();
        if(tab[0]<=-1){
            tab[0]=0;
        }else if(tab[0]>=this.largeur){
            tab[0]=this.largeur-1;
        }
        if(tab[1]<=-1){
            tab[1]=0;
        }else if(tab[1]>=this.longueur){
            tab[1]=this.longueur-1;
        }
        agent.addMemoire(map[tab[0]][tab[1]]);
        hm_Agent.replace(agent,tab);
    }

    public String perceptionPrendre(Agent a) {
        return map[hm_Agent.get(a)[0]][hm_Agent.get(a)[1]];
    }

    public void prendre(Agent a){
        map[hm_Agent.get(a)[0]][hm_Agent.get(a)[1]]=".";    //Remplace la lettre par une case vide
    }

    public String perceptionDeposer(Agent a) {
        return map[hm_Agent.get(a)[0]][hm_Agent.get(a)[1]];
    }

    public void depot(Agent a, String o) {
        map[hm_Agent.get(a)[0]][hm_Agent.get(a)[1]]=o;
    }

}