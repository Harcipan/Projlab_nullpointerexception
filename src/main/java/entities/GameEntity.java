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

    protected GameEntity(int id, Tile currentTile) {
        this.id = id;
        this.currentTile = currentTile;
    }

    protected GameEntity(){};

    public void setTile(Tile tile) {
        UseCase.printWrapper(UseCase.logger.get(this)+".setTile(" + UseCase.logger.get(tile)+")", ArrowDirection.RIGHT, Indent.KEEP);
        this.currentTile = tile;
        UseCase.printWrapper(UseCase.logger.get(this)+".setTile()", ArrowDirection.LEFT, Indent.KEEP);
    }

    public Tile getCurrentTile() {
        return currentTile;
    }

    public void update() {
        UseCase.printWrapper(UseCase.logger.get(this)+".update()", ArrowDirection.RIGHT, Indent.INDENT);
        // implementations vary in subclasses
        
        UseCase.printWrapper(UseCase.logger.get(this)+".step()", ArrowDirection.LEFT, Indent.UNINDENT);
    }

    public void damage() {
        UseCase.printWrapper(UseCase.logger.get(this)+".damage()", ArrowDirection.RIGHT, Indent.INDENT);
        // implementations vary in subclasses
        
        UseCase.printWrapper(UseCase.logger.get(this)+".damage()", ArrowDirection.LEFT, Indent.UNINDENT);
    }

    public void die() {
        UseCase.printWrapper(UseCase.logger.get(this)+".die()", ArrowDirection.RIGHT, Indent.INDENT);
        // implementations vary in subclasses
        
        UseCase.printWrapper(UseCase.logger.get(this)+".die()", ArrowDirection.LEFT, Indent.UNINDENT);
    }

    public void heal() {
        UseCase.printWrapper(UseCase.logger.get(this)+".heal()", ArrowDirection.RIGHT, Indent.INDENT);
        // implementations vary in subclasses
        
        UseCase.printWrapper(UseCase.logger.get(this)+".heal()", ArrowDirection.LEFT, Indent.UNINDENT);
    }

    public void getCut() {
        UseCase.printWrapper(UseCase.logger.get(this)+".getCut()", ArrowDirection.RIGHT, Indent.INDENT);
        // implementations vary in subclasses
        // used for insect vs mycelium currently
        // but insect vs insect could easily be added here too
        
        UseCase.printWrapper(UseCase.logger.get(this)+".getCut()", ArrowDirection.LEFT, Indent.UNINDENT);
    }
}
