package entities;

import map.Tile;

public class FungusBody extends Fungus{
    private int sporeCharge = 0;

    public FungusBody(int health, int initialSporeCharge, Tile currentTile) {
        super(health, currentTile);
        this.sporeCharge = initialSporeCharge;
    }

    public void decrementSporeCharge() {
        this.sporeCharge--;
    }

    public void decrementSporeCharge(int amount) {
        this.sporeCharge -= amount;
    }

    public int getSporeCharge() {
        return this.sporeCharge;
    }

    public void sporeCloud() {

    }





}
