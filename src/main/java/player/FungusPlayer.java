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
        // Will implement later
    }

    public void growMycelium(Tile tile) {
        // Will implement later
    }

    public void sporeCloud(FungusBody target, int size) {
        // Will implement later
    }
}
