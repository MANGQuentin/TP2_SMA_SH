import javax.swing.*;

public class Main extends JPanel {
    public static void main(String[] args) {

        //Enlever seuil proba
        Environnement e = new Environnement(20,20,100000
                ,0.1,0.3,10,50,50,10, 0.0);

        e.run();

    }
}