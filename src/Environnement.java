import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Environnement {

    private String[][] map;
    private ArrayList listeAgent = new ArrayList<Agent>();
    private ArrayList arrayA = new ArrayList<Objet>();
    private ArrayList arrayB = new ArrayList<Objet>();
    private int longueur,largeur;

    public Environnement(int longueur, int largeur) {
        this.map=new String[longueur][largeur];
        this.longueur=longueur;
        this.largeur=largeur;
        for(int i =0;i<longueur;i++){
            for(int j=0;j<largeur;j++){
                map[i][j]=".";
            }
        }
        initAgent(longueur, largeur);
        initObjet(longueur, largeur);
    }

    public void run() {
        this.toString();
    }

    private ArrayList<Agent> initAgent(int longeur, int largeur) {
        //Initialisation des agents
        int x, y;

//        Scanner clavier = new Scanner(System.in);
//       System.out.println("Entrez le nb d'agent:");
//        int nbAgent = clavier.nextInt();
        int nbAgent = 10;
        for(int j = 0; j<nbAgent;j++){
            Random random = new Random();
            x = random.nextInt(longeur);
            y = random.nextInt(largeur);
            Agent agent = new Agent(this,x,y);  //Pb à régler : plusieurs agents avec les mêmes coordonnées
            map[x][y] = "Z";
            listeAgent.add(agent);  //Place les agents de manière random
        }
        return listeAgent;
    }

    public void initObjet(int longueur, int largeur){
//        Scanner clavier = new Scanner(System.in);
//       System.out.println("Entrez le nb d'objet A:");
//        int nbA = clavier.nextInt();
//       System.out.println("Entrez le nb d'objet B:");
//        int nbB = clavier.nextInt();
        int nbA = 6;
        int nbB = 7;
        int x,y;
        String[][] coord = new String[longueur][largeur];
        Random random = new Random();
        while(nbA>0){
            x = random.nextInt(longueur);
            y = random.nextInt(largeur);
            coord[x][y]="x";
            if(map[x][y]!="A" && map[x][y]!="B" && map[x][y]!="Z"){
                arrayA.add(new Objet(this, 'A',x,y));
                map[x][y]= "A";
                nbA--;
            }
        }
        while (nbB>0){
            x = random.nextInt(longueur);
            y = random.nextInt(largeur);
            coord[x][y]="x";
            if(map[x][y]!="A" && map[x][y]!="B" && map[x][y]!="Z"){
                arrayA.add(new Objet(this, 'B',x,y));
                map[x][y]= "B";
                nbB--;
            }
        }
    }

    public String toString(){       //Afficher la grille avec les Objets A et B
        System.out.println("A = Objet A ; B = Objet B et Z = Agent");
        for(int i =0;i<getLongueur();i++){
            System.out.println();
            for(int j=0;j<getLargeur();j++){
                System.out.print(" | ");
                System.out.print(map[i][j]);
            }
        }
        return null;
    }

    public int getLongueur() {
        return longueur;
    }

    public int getLargeur() {
        return largeur;
    }
}
