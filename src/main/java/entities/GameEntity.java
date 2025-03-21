package entities;
import map.Tile;

public class GameEntity {
    int id;             // Unique identifier for the entity
    Tile currentTile;   // The tile the entity is currently on

    protected GameEntity(int id, Tile currentTile) {
        this.id = id;
        this.currentTile = currentTile;
    }

    public void setTile(Tile tile) {
        this.currentTile = tile;
    }

    public Tile getCurrentTile() {
        return currentTile;
    }

    public void update() {
        // implementations vary in subclasses
    }
}
