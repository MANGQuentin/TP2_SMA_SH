import javax.swing.*;

//A faire:
// Intensité du signal
// Phéromones qui sévaporent
// Renouvellement des appels

public class Main extends JPanel {
    public static void main(String[] args) {
        long chrono = 0 ;
        chrono = java.lang.System.currentTimeMillis() ;

            JFrame maFenetre = new JFrame();
        maFenetre.setTitle("TP2");
        maFenetre.setSize(600,450);
        maFenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        maFenetre.setLocationRelativeTo(null);

        Environnement e = new Environnement(50,50,5000000
                ,0.1,0.3,40,150,150,10, 0.0,maFenetre,
                150,20,0.50,3);
        maFenetre.add(e);

        maFenetre.setVisible(true);
        e.run();

        long chrono2 = java.lang.System.currentTimeMillis() ;
        long temps = chrono2 - chrono ;
        System.out.println("Temps ecoule = " + temps + " ms") ;
    }
}