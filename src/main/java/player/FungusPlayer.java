package player;

import java.util.List;

import entities.*;
import map.Tile;
import use_cases.UseCase;

import static use_cases.UseCase.printWrapper;

public class FungusPlayer extends Player{
    List<FungusBody> fungusBodies;
    List<Mycelium> mycelia;

    public FungusPlayer() {
        super();
        fungusBodies = null;
        mycelia = null;
        printWrapper("Initializing FungusPlayer as " + System.identityHashCode(this), UseCase.ArrowDirection.RIGHT, UseCase.IndentDirection.STAY);
    }

    public void growBody(Tile tile) {
    printWrapper("Player trying to grow a mushroom...", UseCase.ArrowDirection.RIGHT);
    // neighbor testing happens here
    // (agreed on handling game logic as soon as possible to prevent chained function calls and constant dispatching)
    printWrapper("Checking if the tile is a neighbor of a living mycelium");

    // presuming that the tile is a neighbor of a living mycelium
    printWrapper("The tile is a neighbor of a living mycelium");
    FungusBody fb = new FungusBody(1, tile);

    // adding the fungus body to the tile
    printWrapper("Adding the fungus body to the tile");
    tile.addEntity(fb);

    // increment forwards
    printWrapper("Finished growing a mushroom.", UseCase.ArrowDirection.RIGHT, UseCase.IndentDirection.RIGHT);
}

    public void growMycelium(Tile tile) {
        // Will implement later
    }

    public void sporeCloud(FungusBody target, int size) {
        if (target == null) {
            printWrapper("No target selected", UseCase.ArrowDirection.RIGHT);
            return;
        }
        printWrapper("Player trying to release a spore cloud...", UseCase.ArrowDirection.RIGHT);
    }
}
