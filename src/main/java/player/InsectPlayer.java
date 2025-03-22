package player;

import entities.Insect;
import map.Tile;
import use_cases.UseCase;
import use_cases.UseCase.ArrowDirection;

public class InsectPlayer extends Player{
    Insect controlledInsect;

    public InsectPlayer() {
        super();
        this.controlledInsect = null;
        UseCase.printWrapper("Initializing InsectPlayer as " + System.identityHashCode(this), ArrowDirection.RIGHT, UseCase.IndentDirection.STAY);
    }

    public void setControlledInsect(Insect controlledInsect) {
        this.controlledInsect = controlledInsect;
    }

    public void moveTo(Tile tile) {
        // Will implement later
        controlledInsect.step(tile);
    }

    public void eat(){
        // Will implement later
    }

    public void cut(){
        // Will implement later
        //controlledInsect.cut(tile);
    }

    
}
