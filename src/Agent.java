import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Random;

public class Agent {

    private Environnement env;
    private double kplus;
    private double kmoins;
    private String objetPorte;
    private ArrayDeque<String> memory;
    private int tailleMemory, renouvellementAppel;
    double[] f = new double[2];
    private boolean bloque = false;
    private boolean appelle = false;
    private boolean memeCase;
    Agent agentLier;


    public Agent(Environnement environnement, double kplus, double kmoins, String s, int tailleMemory,
                 int renouvellementAppel) {
        this.env = environnement;
        this.kplus = kplus;
        this.kmoins = kmoins;
        this.objetPorte = s;
        this.memory = new ArrayDeque<>();
        this.tailleMemory = tailleMemory;
        this.renouvellementAppel=renouvellementAppel;
    }

    public void action() {
        Random rand = new Random();
        HashMap<Integer ,Direction> mouvement = env.perceptionSeDeplacer(this);
        if(this.bloque==false){
            int direction = rand.nextInt(mouvement.size());
            env.deplacement(this,mouvement.get(direction));
            if(objetPorte==""){
                prendre();
            }else{
                deposer();
            }
        }else if (this.bloque){
            if(this.appelle==true){
                memeCase = env.seDeplacerV2(this,this.agentLier);
                if(memeCase==true){
                    prendreObjetC();
                }
            }else{
//                System.out.println("Ne prend pas l'objet C");
            }
        }
    }

    private void prendreObjetC() {  //Pb, prend l'objet 2 fois
 //       System.out.println("Prise objet C par " + this);
        this.objetPorte="C";
        this.bloque=false;
        agentLier.appelle=false;
    }

    public void prendre() {
        String objet = env.perceptionPrendre(this);
        double f[];
        if(env.getTauxErreur()==0){
            f = calculF();
        }else{
            f = calculF_TauxErreur(env.getTauxErreur());
        }
        if(objet == "A" || objet == "B"){
            calculProbaPrendre(objet, f);
        }else if(objet == "C"){
            this.bloque=true;
            envoyerSignal(this);
            env.prendre(this);
        }
    }

    public void envoyerSignal(Agent a) {        // Agent dispo doit aller vers l'agent qui lui demande de l'aides
        Agent agentDispo;
        agentDispo = env.diffusionSignal(a);
        if(agentDispo != null){
//            System.out.println(agentDispo);
            this.agentLier = agentDispo;    //Liaison de l'agent courant avec l'agent cible
            agentDispo.agentLier=this;      //Liaison de l'agent cible avec l'agent courant
            agentDispo.appelle=true;
            agentDispo.bloque=true;
        }
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
            }else if(objetPorte=="C"){
                env.depot(this,objetPorte);
                objetPorte="";
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

    public String getObjetPorte() {
        return objetPorte;
    }
}