package entities;
import map.Tile;
import use_cases.UseCase;
import use_cases.UseCase.ArrowDirection;
import use_cases.UseCase.Indent;

public class GameEntity {
    int id;             // Unique identifier for the entity
    Tile currentTile;   // The tile the entity is currently on

    protected GameEntity(int id, Tile currentTile) {
        this.id = id;
        this.currentTile = currentTile;
    }

    protected GameEntity(){};

    public void setTile(Tile tile) {
        
        this.currentTile = tile;
    }

    public Tile getCurrentTile() {
        return currentTile;
    }

    public void update() {
        // implementations vary in subclasses
        
    }

    public void damage() {
        // implementations vary in subclasses
        
    }

    public void heal() {
        // implementations vary in subclasses
        
    }

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }
}
