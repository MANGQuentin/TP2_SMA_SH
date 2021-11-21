public class Objet {

    private Environnement e;
    private int coordonneeX,coordonneeY;
    private char lettre;


    public Objet(Environnement e, char lettre, int coordonneeX, int coordonneeY) {
        this.e = e;
        this.coordonneeX = coordonneeX;
        this.coordonneeY = coordonneeY;
        this.lettre = lettre;
    }
}
