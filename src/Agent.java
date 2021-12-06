import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Random;

public class Agent {

    private Environnement env;
    private double kplus;
    private double kmoins;
    private String objetPorte;
    private ArrayDeque<String> memory;
    private int tailleMemory;
    double[] f = new double[2];


    public Agent(Environnement environnement, double kplus, double kmoins, String s, int tailleMemory) {
        this.env = environnement;
        this.kplus = kplus;
        this.kmoins = kmoins;
        this.objetPorte = s;
        this.memory = new ArrayDeque<>();
        this.tailleMemory = tailleMemory;
    }

    public void action() {
        Random rand = new Random();
        HashMap<Integer ,Direction> mouvement = env.perceptionSeDeplacer(this);
        int direction = rand.nextInt(mouvement.size());
        env.deplacement(this,mouvement.get(direction));
        if(objetPorte==""){
            prendre();
        }else{
            deposer();
        }

    }

    public void prendre() {
        String objet = env.perceptionPrendre(this);
        double f[];
        if(env.getTauxErreur()==0){
            f = calculF();
        }else{
            f = calculF_TauxErreur(env.getTauxErreur());
        }
        calculProbaPrendre(objet, f);
    }

    public void calculProbaPrendre(String objet, double[] f){
        double pPrendreA = Math.pow(kplus / (kplus + f[0]), 2);
        double pPrendreB = Math.pow(kplus / (kplus + f[1]), 2);
        if(objet=="A"){
            double test = Math.random();
            if (test<= pPrendreA) {
                objetPorte = "A";
                env.prendre(this);
            }
        }else if(objet=="B"){
            double test = Math.random();
            if (test <= pPrendreB) {
                objetPorte = "B";
                env.prendre(this);
            }
        }
    }

    public void deposer() {
        String objet = env.perceptionDeposer(this);
        double f[];
        Random random = new Random();
        if(env.getTauxErreur()==0){
            f = calculF();
        }else{
            f = calculF_TauxErreur(env.getTauxErreur());
        }
        calculProbaDeposer(objet, f);
    }

    public void calculProbaDeposer(String objet, double[] f){
        if(objet=="."){
            if(objetPorte=="A"){
                double pDeposer = Math.pow(f[0] / (kmoins + f[0]), 2);
                double test = Math.random();
                if (test <= pDeposer) {
                    env.depot(this, objetPorte);
                    objetPorte = "";
                }
            }else if(objetPorte=="B"){
                double pDeposer = Math.pow(f[1] / (kmoins + f[1]), 2);
                double test = Math.random();
                if (test <= pDeposer) {
                    env.depot(this, objetPorte);
                    objetPorte = "";
                }
            }
        }
    }

    public void addMemoire(String s) {
        if(memory.size() == tailleMemory){
            memory.removeFirst();
        }
        memory.addLast(s);
    }

    public double[] calculF(){
        int iterA = 0;
        int iterB=0;
        for(int i=0;i<memory.size();i++){
            if (memory.toArray()[i]==("A")){
                iterA++;
            }else if(memory.toArray()[i]==("B")){
                iterB++;
            }
        }
        f[0]=(double)iterA/(double)tailleMemory;
        f[1]=(double)iterB/(double)tailleMemory;
        return f;
    }

    public double[] calculF_TauxErreur(double e){
        int iterA = 0;
        int iterB=0;
        for(int i=0;i<memory.size();i++){
            if (memory.toArray()[i]==("A")){
                iterA++;
            }else if(memory.toArray()[i]==("B")){
                iterB++;
            }
        }
        f[0]=(double)(iterA+iterB*e)/(double)tailleMemory;
        f[1]=(double)(iterA*e+iterB)/(double)tailleMemory;
        return f;
    }
}