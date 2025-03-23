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
        printWrapper("Initializing FungusPlayer as " + System.identityHashCode(this), UseCase.ArrowDirection.RIGHT, UseCase.Indent.KEEP);
    }

    public FungusBody growBody(Tile tile) {
        printWrapper("Player " + System.identityHashCode(this) + " trying to grow a mushroom...", UseCase.ArrowDirection.RIGHT, UseCase.Indent.INDENT);
        // neighbor testing happens here
        // (agreed on handling game logic as soon as possible to prevent chained function calls and constant dispatching)
        printWrapper("Checking if the tile " + System.identityHashCode(tile) + " is a neighbor of a living mycelium");

        // presuming that the tile is a neighbor of a living mycelium
        printWrapper("The tile " + System.identityHashCode(tile) +  "is a neighbor of a living mycelium/fungus body", UseCase.ArrowDirection.RIGHT);

        // check if there is a body already on the tekton
        FungusBody fb = null;

        // validate if the tile has a parent tekton
        if (tile.getParentTekton() == null) {
            printWrapper("Tile " + System.identityHashCode(tile) + " has no parent tekton", UseCase.ArrowDirection.RIGHT);
            return null;
        }

        // check for existing tiles next to the tile
        for (Tile t : tile.getParentTekton().getTiles()) {
            for (GameEntity e : t.getEntities()) {
                if (e instanceof FungusBody) {
                    fb = (FungusBody) e;
                    break;
                }
            }
        }
        if (fb != null) {
            printWrapper("Body " + System.identityHashCode(fb) + " on tekton "
                    + System.identityHashCode(tile.getParentTekton()) + " already exists", UseCase.ArrowDirection.RIGHT);
            return null;
        }
        FungusBody new_fb = new FungusBody(1, 1, tile);

        // adding the fungus body to the tile
        printWrapper("Adding the fungus body " + System.identityHashCode(new_fb) + " to tile " + System.identityHashCode(tile));
        tile.addEntity(new_fb);

        // increment forwards
        printWrapper("Finished growing a fungus body.", UseCase.ArrowDirection.RIGHT, UseCase.Indent.UNINDENT);
        return fb;
    }

    public Mycelium growMycelium(Tile tile) {
        printWrapper("Player " + System.identityHashCode(this) + " trying to grow a mycelium...", UseCase.ArrowDirection.RIGHT, UseCase.Indent.INDENT);
        // neighbor testing happens here
        // (agreed on handling game logic as soon as possible to prevent chained function calls and constant dispatching)
        printWrapper("Checking if the tile " + System.identityHashCode(tile) + " is a neighbor of a living mycelium/fungus body");

        // TODO presuming that the tile is a neighbor of a living mycelium
        printWrapper("The tile " + System.identityHashCode(tile) +  "is a neighbor of a living mycelium/fungus body", UseCase.ArrowDirection.RIGHT);

        // validate if the tile has a parent tekton
        if (tile.getParentTekton() == null) {
            printWrapper("Tile " + System.identityHashCode(tile) + " has no parent tekton", UseCase.ArrowDirection.RIGHT);
            return null;
        }

        // check if there is a mycelium already on the tile
        Mycelium myc = null;
        for (GameEntity e : tile.getEntities()) {
            if (e instanceof Mycelium) {
                myc = (Mycelium) e;
                break;
            }
        }
        if (myc != null) {
            printWrapper("Mycelium " + System.identityHashCode(myc) + " on tile " + System.identityHashCode(tile) + " already exists", UseCase.ArrowDirection.RIGHT);
            return null;
        }

        Mycelium new_myc = new Mycelium();

        // adding the fungus body to the tile
        printWrapper("Adding the mycelium " + System.identityHashCode(new_myc) + " to tile " + System.identityHashCode(tile));
        tile.addEntity(new_myc);

        // increment forwards
        printWrapper("Finished growing a mycelium.", UseCase.ArrowDirection.RIGHT, UseCase.Indent.UNINDENT);
        return myc;
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
