package player;

import java.util.List;

import entities.*;
import map.Tile;
import use_cases.UseCase;
import use_cases.UseCase.ArrowDirection;

import static use_cases.UseCase.logger;
import static use_cases.UseCase.printWrapper;
import static use_cases.UseCase.replace;

public class FungusPlayer extends Player{
    List<FungusBody> fungusBodies;
    List<Mycelium> mycelia;

    public FungusPlayer() {
        super();
        replace(this);
        fungusBodies = null;
        mycelia = null;
        printWrapper("Initializing FungusPlayer as " + logger.get(this), UseCase.ArrowDirection.RIGHT, UseCase.Indent.KEEP);
        UseCase.printWrapper("FungusPlayer: "+UseCase.logger.get(this), ArrowDirection.LEFT);
    }

    /*
     * consume an insect if it is paralyzed and a neighbor of a fungus body or mycelium
     * grow a fungus body in place of the insect
     */
    public void consumeInsect(Insect insect){
        printWrapper("Player " + System.identityHashCode(this) + " trying to consume insect " + System.identityHashCode(insect), UseCase.ArrowDirection.RIGHT, UseCase.Indent.INDENT);
        //check if insect is paralyzed
        if(insect.getSpeed() == 0){
            Tile insectTile = insect.getCurrentTile();
            // check if insect Tile is a neighbor of a fungus body or mycelium
            boolean isNeighbor = false;
            for (Mycelium myc: mycelia){
                if (myc.getCurrentTile().isNeighbor(insectTile)) {
                    isNeighbor = true;
                    break;
                }
            }
            if (!isNeighbor) {
                for (FungusBody fb: fungusBodies){
                    if (fb.getCurrentTile().isNeighbor(insectTile)) {
                        isNeighbor = true;
                        break;
                    }
                }
            }
            if (!isNeighbor) {
                printWrapper("The insect " + System.identityHashCode(insect) + " is not a neighbor of a fungus body or mycelium", UseCase.ArrowDirection.RIGHT, UseCase.Indent.UNINDENT);
                return;
            }
            insect.die();
            // grow fungus body in place of insect
            growBody(insectTile);

        }
        printWrapper("Finished consuming insect " + System.identityHashCode(insect), UseCase.ArrowDirection.RIGHT, UseCase.Indent.UNINDENT);
    }

    public FungusBody growBody(Tile tile) {
        printWrapper("Player " + System.identityHashCode(this) + " trying to grow a mushroom...", UseCase.ArrowDirection.RIGHT, UseCase.Indent.INDENT);
        // neighbor testing happens here
        // (agreed on handling game logic as soon as possible to prevent chained function calls and constant dispatching)
        printWrapper("Checking if the tile " + System.identityHashCode(tile) + " is a neighbor of a living mycelium");

        // presuming that the tile is a neighbor of a living mycelium
        printWrapper("The tile " + System.identityHashCode(tile) +  "is a neighbor of a living mycelium/fungus body", UseCase.ArrowDirection.RIGHT);

        // start checking if there is a body already on the tekton
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
        System.out.println("Does the player have enough action points to grow a body? Y/N");
        String answer = System.console().readLine();
        if (answer.equalsIgnoreCase("N")) {
            printWrapper("Player does not have enough points to grow a body, end of use-case", UseCase.ArrowDirection.RIGHT, UseCase.Indent.UNINDENT);
            return null;
        }
        // checking complete, create the body
        logger.put(null,"new_fb");
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

        logger.put(null, "new_myc");
        Mycelium new_myc = new Mycelium();

        // adding the mycelium to the tile
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
