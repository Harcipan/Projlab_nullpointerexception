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
    printWrapper("Player " + System.identityHashCode(this) + " trying to grow a mushroom...", UseCase.ArrowDirection.RIGHT);
    // neighbor testing happens here
    // (agreed on handling game logic as soon as possible to prevent chained function calls and constant dispatching)
    printWrapper("Checking if the tile " + System.identityHashCode(tile) + " is a neighbor of a living mycelium");

    // presuming that the tile is a neighbor of a living mycelium
    printWrapper("The tile " + System.identityHashCode(tile) +  "is a neighbor of a living mycelium");
    FungusBody fb = new FungusBody(1, 1, tile);

    // adding the fungus body to the tile
    printWrapper("Adding the fungus body " + System.identityHashCode(fb) + " to tile " + System.identityHashCode(tile));
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
        printWrapper("Player " + System.identityHashCode(this) + " releasing spore cloud", UseCase.ArrowDirection.RIGHT);
        target.decrementSporeCharge();
        if (target.getSporeCharge() <= 0) {
            printWrapper("Target " + System.identityHashCode(target) + " has no spore charge left", UseCase.ArrowDirection.RIGHT);
            target.die();
            return;
        }
    }
}
