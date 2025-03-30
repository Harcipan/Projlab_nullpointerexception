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

    public void heal() {
        UseCase.printWrapper(UseCase.logger.get(this)+".heal()", ArrowDirection.RIGHT, Indent.INDENT);
        // implementations vary in subclasses
        
        UseCase.printWrapper(UseCase.logger.get(this)+".heal()", ArrowDirection.LEFT, Indent.UNINDENT);
    }

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }
}
