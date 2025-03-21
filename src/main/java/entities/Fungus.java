package entities;

<<<<<<< HEAD
import map.Tile;

=======
>>>>>>> 682187e326a4ae59cdee63ff95aeb6cb4ebcf6c7
public abstract class Fungus extends GameEntity {
    int health;

    protected Fungus(int health, Tile currentTile) {
        super(currentTile);
        this.health = health;
    }

    public void growMycelium(Tile neighbor) {
        boolean isValid = checkNeighbor(neighbor);
        // Will implement later
    }

    public void growBody(Tile neighbor) {
        boolean isValid = checkNeighbor(neighbor);
        // Will implement later
    }

    public void die() {
        // Will implement later
    }


    private boolean checkNeighbor(Tile neighbor) {
        // Will implement later
        return false;
    }
}
