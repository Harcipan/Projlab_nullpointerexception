package entities;
import map.Tile;

public abstract class Fungus extends GameEntity {
    int health;

    /*protected Fungus(int health, Tile currentTile) {
        super(1, currentTile);
        this.health = health;
    }*/

    protected Fungus()
    {
        super();
    }

    public void growMycelium(Tile neighbor) {

        // Will implement later
        
    }

    public void growBody(Tile neighbor) {

        // Will implement later

    }

    public void die() {
        // Will implement later
    }


    // private boolean checkNeighbor(Tile neighbor) {
    //     // Will implement later
    //     return false;
    // }
}
