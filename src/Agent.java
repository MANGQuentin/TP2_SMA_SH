//PERCEPTION ET ACTION

public class Agent {

    private Environnement e;
    private int coordonneeX,coordonneeY;

    public Agent(Environnement e,int x, int y) {
        this.e = e;
        this.coordonneeX = x;
        this.coordonneeY= y;
    }

    public int getCoordonneeX() {
        return coordonneeX;
    }

    public void setCoordonneeX(int coordonneeX) {
        this.coordonneeX = coordonneeX;
    }

    public int getCoordonneeY() {
        return coordonneeY;
    }

    public void setCoordonneeY(int coordonneeY) {
        this.coordonneeY = coordonneeY;
    }

}
