package entities;

import map.Tile;

public class FungusBody extends Fungus{
    int sporeCharge = 0;

    public FungusBody(int health, Tile currentTile) {
        super(health, currentTile);
    }

    public void sporeCloud() {
        // Will implement later
    }

    public void update() {
        // Will implement later
        // Probably should be in GameEntity
    }



}
