package entities;
import map.Tile;

import static use_cases.UseCase.*;

public abstract class Fungus extends GameEntity {
    int health;

    protected Fungus(int health, Tile currentTile) {
        super(1, currentTile);
        this.health = health;
    }

    protected Fungus()
    {
        super();
    }

    public void growMycelium(Tile neighbor) {

        // implemented in subclasses
        
    }

    public void growBody(Tile neighbor) {

        // implemented in subclasses

    }

    public void die() {
        printWrapper("Fungus (typeof " + this.getClass().getName()
                + ") " + System.identityHashCode(this) + " is dying", ArrowDirection.RIGHT, Indent.KEEP);
    }

    public int getHealth() {
        return health;
    }

}
