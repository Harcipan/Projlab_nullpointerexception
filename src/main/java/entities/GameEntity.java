package entities;
import map.Tile;
import use_cases.UseCase;
import use_cases.UseCase.ArrowDirection;
import use_cases.UseCase.Indent;

/**
 * GameEntity is the base class for all entities in the game.
 * It contains common properties and methods that all entities share.
 * This includes the unique identifier for the entity and the tile it is currently on.
 * The class also provides methods to set the tile, update the entity, and manage health.
 */
public class GameEntity {
    int id;             // Unique identifier for the entity
    Tile currentTile;   // The tile the entity is currently on
    static int idCounter = 0; // Counter for generating unique ids

    protected GameEntity(int id, Tile currentTile) {
        this.id = id;
        this.currentTile = currentTile;
    }

    public static int getNextId() {
        return idCounter++;
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

    public void die() {
        UseCase.printWrapper(UseCase.logger.get(this)+".die()", ArrowDirection.RIGHT, Indent.INDENT);
        // implementations vary in subclasses
        
        UseCase.printWrapper(UseCase.logger.get(this)+".die()", ArrowDirection.LEFT, Indent.UNINDENT);
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

    public void getCut() {
        UseCase.printWrapper(UseCase.logger.get(this)+".getCut()", ArrowDirection.RIGHT, Indent.INDENT);
        // implementations vary in subclasses
        // used for insect vs mycelium currently
        // but insect vs insect could easily be added here too
        
        UseCase.printWrapper(UseCase.logger.get(this)+".getCut()", ArrowDirection.LEFT, Indent.UNINDENT);
    }
}
