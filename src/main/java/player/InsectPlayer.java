package player;

import entities.Insect;
import map.Tile;

public class InsectPlayer extends Player{
    Insect controlledInsect;

    public InsectPlayer() {
        super();
        this.controlledInsect = null;
    }

    public void setControlledInsect(Insect controlledInsect) {
        this.controlledInsect = controlledInsect;
    }

    public void moveTo(Tile tile) {
        // Will implement later
    }

    public void eat(){
        // Will implement later
    }

    public void cut(){
        // Will implement later
    }

    
}
