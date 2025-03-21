package entities;
public class GameEntity {
    int id;             // Unique identifier for the entity
    Tile currentTile;   // The tile the entity is currently on

    public GameEntity(int id, Tile currentTile) {
        this.id = id;
        this.currentTile = currentTile;
    }

    public void setTile(Tile tile) {
        this.currentTile = tile;
    }

    public Tile getCurrentTile() {
        return currentTile;
    }
}
