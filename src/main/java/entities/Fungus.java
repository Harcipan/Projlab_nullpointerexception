package entities;
import map.Tile;

import static use_cases.UseCase.*;

public abstract class Fungus extends GameEntity {
    int health;

    protected Fungus(int id, int health, Tile currentTile) {
        super(id, currentTile);
        this.health = health;
    }

    protected Fungus()
    {
        super();
    }

    @Override
    public void die() {
        printWrapper("Fungus (typeof " + this.getClass().getName()
                + ") " + System.identityHashCode(this) + " is dying", ArrowDirection.RIGHT, Indent.KEEP);
    }

    public int getHealth() {
        return health;
    }

}
