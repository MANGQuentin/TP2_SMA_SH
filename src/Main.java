import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        //Initialisation de l'environnement
/*        Scanner clavier = new Scanner(System.in);
        System.out.println("Entrez la longueur de la map:");
        int longueur = clavier.nextInt();
        System.out.println("Entrez la largeur de la map :");
        int largeur = clavier.nextInt();
*/
//        Environnement e = new Environnement(longueur,largeur);
        Environnement e = new Environnement(10,10);

        e.run();
    }
}