package player;

import entities.Insect;
import map.Tile;
import use_cases.UseCase;
import use_cases.UseCase.Direction;
import entities.Spore;

public class InsectPlayer extends Player{
    Insect controlledInsect;

    public InsectPlayer() {
        super();
        this.controlledInsect = null;
        UseCase.printWrapper("InsectPlayer",Direction.RIGHT);
    }

    public void setControlledInsect(Insect controlledInsect) {
        this.controlledInsect = controlledInsect;
    }

    public void moveTo(Tile tile) {
        // Will implement later
        controlledInsect.step(tile);
    }

    public void eat(Spore s){
        // Will implement later
        controlledInsect.eat(s);
    }

    public void cut(Tile tile){
        // Will implement later
        controlledInsect.cut(tile);
    }

    
}
