import javax.swing.*;

public class Main extends JPanel {
    public static void main(String[] args) {
        long chrono = 0 ;
        chrono = java.lang.System.currentTimeMillis() ;

            JFrame maFenetre = new JFrame();
        maFenetre.setTitle("TP2");
        maFenetre.setSize(600,450);
        maFenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        maFenetre.setLocationRelativeTo(null);

        Environnement e = new Environnement(50,50,7000000
                ,0.1,0.3,40,200,200,10, 0.0,maFenetre,
                200,10,0.50,3);
        maFenetre.add(e);

        maFenetre.setVisible(true);
        e.run();

        long chrono2 = java.lang.System.currentTimeMillis() ;
        long temps = chrono2 - chrono ;
        System.out.println("Temps ecoule = " + temps + " ms") ;
    }
}