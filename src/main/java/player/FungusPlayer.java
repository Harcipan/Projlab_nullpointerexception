package player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import entities.*;
import map.Tile;
import prototype.App;
import use_cases.UseCase;
import use_cases.UseCase.ArrowDirection;
import use_cases.UseCase.Indent;

import static use_cases.UseCase.logger;
import static use_cases.UseCase.printWrapper;
import static use_cases.UseCase.replace;

public class FungusPlayer extends Player {
    List<FungusBody> fungusBodies;
    List<Mycelium> mycelia;
    List<Spore> spores;

    public FungusPlayer() {
        super();
        replace(this);
        fungusBodies = new ArrayList<>();
        spores = new ArrayList<>();
        mycelia = new ArrayList<>();
        App.getFungusPlayers().add(this);
        printWrapper("Initializing FungusPlayer as " + logger.get(this), UseCase.ArrowDirection.RIGHT, UseCase.Indent.KEEP);
        UseCase.printWrapper("FungusPlayer: "+UseCase.logger.get(this), ArrowDirection.LEFT);
    }

    /**
     * consume an insect if it is paralyzed and a neighbor of a fungus body or mycelium
     * grow a fungus body in place of the insect
     * @param insect the insect to consume
     */
    public void consumeInsect(Insect insect){
        printWrapper("Player " + System.identityHashCode(this) + " trying to consume insect " + System.identityHashCode(insect), UseCase.ArrowDirection.RIGHT, UseCase.Indent.INDENT);
        //check if insect is paralyzed
        if(insect.getSpeed() == 0) {
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
            // grow fungus body in place of insect, omit checks
            insectTile.growBody(this);

        }
        printWrapper("Finished consuming insect " + System.identityHashCode(insect), UseCase.ArrowDirection.RIGHT, UseCase.Indent.UNINDENT);
    }

    

    public Mycelium growMycelium(Tile tile) {
        printWrapper("Player " + System.identityHashCode(this) + " trying to grow a mycelium...", UseCase.ArrowDirection.RIGHT, UseCase.Indent.INDENT);
        // neighbor testing happens here
        // (agreed on handling game logic as soon as possible to prevent chained function calls and constant dispatching)
        printWrapper("Checking if the tile " + System.identityHashCode(tile) + " is a neighbor of a living mycelium/fungus body");

        // check if the tile is a neighbor of a mycelium
        for (Mycelium myc: mycelia){
            if (myc.getCurrentTile().isNeighbor(tile)) {
                printWrapper(myc.getCurrentTile() + " myc neighbor: " + tile);
                return tile.growMycelium(this);
            }
        }

        // check if the tile is a neighbor of a fungus body (if this runs, it means that the tile is not a neighbor of a mycelium)
        for (FungusBody fb: fungusBodies){
            if (fb.getCurrentTile().isNeighbor(tile)) {
                printWrapper(fb.getCurrentTile() + " body neighbor: " + tile);
                return tile.growMycelium(this);
            }
        }

        // if the tile is not a neighbor of a fungus body or mycelium, return null
        printWrapper("The tile " + System.identityHashCode(tile) + " is not a neighbor of a fungus body or mycelium", UseCase.ArrowDirection.RIGHT, UseCase.Indent.UNINDENT);
        return null;
    }

    public FungusBody growBody(Tile tile) {
        printWrapper("Player " + System.identityHashCode(this) + " trying to grow a mushroom body...", UseCase.ArrowDirection.RIGHT, UseCase.Indent.INDENT);
        // neighbor testing happens here
        // (agreed on handling game logic as soon as possible to prevent chained function calls and constant dispatching)
        printWrapper("Checking if the tile " + System.identityHashCode(tile) + " is a neighbor of a living mycelium/fungus body");


        // check if this tekton already has a fungus body
        if (tile.getParentTekton().hasFungusBody()) {
            printWrapper("The tile " + System.identityHashCode(tile) + " already has a fungus body", UseCase.ArrowDirection.RIGHT, UseCase.Indent.UNINDENT);
            return null;
        }

        // check if we have enough spores on the tekton to grow a fungus body
        if (tile.getParentTekton().getPlayerSpores(this) < FungusBody.BODY_COST) {
            printWrapper("Not enough spore on tekton to grow a fungus body", UseCase.ArrowDirection.RIGHT, UseCase.Indent.UNINDENT);
            return null;
        }

        // check if the tile is a neighbor of a mycelium
        for (Mycelium myc: mycelia){
            if (myc.getCurrentTile().isNeighbor(tile) && tile.canGrowFungus()) {
                FungusBody body = tile.growBody(this);
                score++;
                return body;
            }
        }

        // if the tile is not a neighbor of a fungus body or mycelium, return null
        printWrapper("The tile " + System.identityHashCode(tile) + " is not a neighbor of a fungus body or mycelium", UseCase.ArrowDirection.RIGHT, UseCase.Indent.UNINDENT);
        return null;
    }



    public void sporeCloud(FungusBody target, int size) {
        if (target == null) {
            printWrapper("No target selected", UseCase.ArrowDirection.RIGHT);
            return;
        }
        if(target.getSporeCharge() >= size * FungusBody.SPORECLOUD_COST_MULTIPLIER){
            UseCase.printWrapper("FungusBody: "+UseCase.logger.get(this)+".sporeCloud("+size+")", ArrowDirection.RIGHT, Indent.INDENT);
            target.sporeCloud(size);
            UseCase.printWrapper("FungusBody: "+UseCase.logger.get(this)+".sporeCloud()", ArrowDirection.LEFT, Indent.UNINDENT);
        } else {
            UseCase.printWrapper("FungusBody: "+UseCase.logger.get(this)+".sporeCloud("+size+")", ArrowDirection.RIGHT, Indent.INDENT);
            UseCase.printWrapper("Not enough spore charge to create spore cloud", ArrowDirection.LEFT, Indent.UNINDENT);
        }
    }

    public void addFungusBody(FungusBody fb) {
        fungusBodies.add(fb);
    }
    public void removeFungusBody(FungusBody fb) {
        fungusBodies.remove(fb);
    }
    public List<FungusBody> getFungusBodies() {
        return fungusBodies;
    }
    public void addMycelium(Mycelium myc) {
        mycelia.add(myc);
    }
    public void removeMycelium(Mycelium myc) {
        mycelia.remove(myc);
    }
    public List<Mycelium> getMycelia() {
        return mycelia;
    }
    public void addSpore(Spore spore) {
        spores.add(spore);
    }
    public void removeSpore(Spore spore) {
        spores.remove(spore);
    }
    public List<Spore> getSpores() {
        return spores;
    }

    /**
     * Check mycelium graphs from all fungus bodies 
     */
    public void floodFillCheck(){
        for (FungusBody fb: fungusBodies){
            List<Mycelium> connectedMycelia = new ArrayList<>();
            List<Tile> connectedTiles = new ArrayList<>();
            // immediate neighbors
            for (Mycelium mycelium : mycelia) {
                if (mycelium.getCurrentTile().isNeighbor(fb.getCurrentTile())) {
                    connectedMycelia.add(mycelium);
                    connectedTiles.add(mycelium.getCurrentTile());
                }
            }
            // yeah just a guess, horribly inefficent algo, architecture is broken 
            for (int i = 0; i < mycelia.size(); i++){
                for (Mycelium mycelium : mycelia) {
                    // if connected mycelium is growing on a neighbor tile
                    if (!Collections.disjoint(mycelium.getCurrentTile().getNeighbors(), connectedTiles)){
                        connectedMycelia.add(mycelium);
                        connectedTiles.add(mycelium.getCurrentTile());
                    }
                    
                }
            }

            // set disconnected mycelia to not connected
            for (Mycelium mycelium : mycelia) {
                if (!connectedMycelia.contains(mycelium)) {
                    mycelium.setIsDying(true);
                }
            }

        }
    }
}
