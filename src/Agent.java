import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Agent {

    private Environnement env;
    private double kplus;
    private double kmoins;
    private String objetPorte;
    private ArrayDeque<String> memory;
    private int tailleMemory;

    public Agent(Environnement env, double kplus, double kmoins, String objetPorte, int tailleMemory) {
        this.env = env;
        this.kplus = kplus;
        this.kmoins = kmoins;
        this.objetPorte = objetPorte;
        this.memory = new ArrayDeque<>();
        this.tailleMemory = tailleMemory;
    }

    public void action(){
        Random rand = new Random();
        HashMap<Integer ,Direction> mouvement = env.perceptionSeDeplacer(this);
        int direction = rand.nextInt(mouvement.size());
        deplacer(mouvement.get(direction));
        if(objetPorte==""){ //Si on ne porte rien
            prendre();
        }else{              //Sinon on depose
            deposer();
        }
        env.seDeplacer();
    }

    public void prendre() {
        String objet = env.perceptionPrendre(this);
        double[] f = new double[2];
        f = calculF();
        if(objet=="A"){
            double pPrendre = Math.pow(kplus / (kplus + f[0]), 2);
            if (Math.random() <= pPrendre) {
                objetPorte = "A";
                env.prendre(this);
//                System.out.println("Prise A");
            }
        }else if(objet=="B"){
            double pPrendre = Math.pow(kplus / (kplus + f[1]), 2);
                if (Math.random() <= pPrendre) {
                    objetPorte = "B";
                    env.prendre(this);
//                    System.out.println("Prise B");
                }
        }
    }

    public void deposer() {
        String objet = env.perceptionDeposer(this);
        double[] f = new double[2];
        f = calculF();
        if(objet=="."){
            if(objetPorte!=""){
                double pDeposer = Math.pow(f[0] / (kmoins + f[0]), 2);
                if (Math.random() <= pDeposer) {
                    env.depot(this, objetPorte);
                    //                System.out.println("Depot de " + objetPorte);
                    objetPorte = "";
                }
            }
        }
    }

    public void deplacer(Direction direction) {
        env.deplacement(this, direction);
    }

    public void addMemoire(String o){
        if(memory.size() == tailleMemory){
            memory.removeFirst();
        }
        memory.addLast(o);
    }

    public ArrayDeque<String> getMemory() {
        return memory;
    }

    public double[] calculF(){
        double[] f = new double[2]; //Case[0] = Fa et case[0]=fb
        int nbA = 0;
        int nbB=0;
        for(int i=0;i<memory.size();i++){
            if (memory.contains("A")){
                nbA++;
            }else if(memory.contains("B")){
                nbB++;
            }
        }
        f[0]=nbA/memory.size();
        f[1]=nbB/memory.size();

        return f;
    }
}
