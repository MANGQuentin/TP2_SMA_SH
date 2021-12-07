import javax.swing.*;
import java.awt.*;

public class Main extends JPanel {
    public static void main(String[] args) {

        JFrame maFenetre = new JFrame();
        maFenetre.setTitle("TP2 version 1");
        maFenetre.setSize(600,450);
        maFenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        maFenetre.setLocationRelativeTo(null);

        Environnement e = new Environnement(50,50,50000
                ,0.1,0.3,50,200,200,10, 0.0,maFenetre);
        maFenetre.add(e);

        maFenetre.setVisible(true);
        e.run();

    }
}