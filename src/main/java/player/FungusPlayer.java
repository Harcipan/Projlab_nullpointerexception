package player;

import java.util.List;

import entities.*;
import map.Tile;

public class FungusPlayer extends Player{
    List<FungusBody> fungusBodies;
    List<Mycelium> mycelia;

    public FungusPlayer() {
        super();
        fungusBodies = null;
        mycelia = null;
    }

    public void growBody(Tile tile) {
        // neighbor testing happens here
        // (aggreed on handling game logic as soon as possible to prevent chained function calls and constant dispatching)
        System.out.println("Checking if the tile is a neighbor of a living mycelium");

        // presuming that the tile is a neighbor of a living mycelium
        System.out.println("The tile is a neighbor of a living mycelium");
        FungusBody fb = new FungusBody(1, tile);

        // adding the fungus body to the tile
        System.out.println("Adding the fungus body to the tile");
        tile.addEntity(fb);
    }

    public void growMycelium(Tile tile) {
        // Will implement later
    }

    public void sporeCloud(FungusBody target, int size) {
        // Will implement later
    }
}
