package use_cases;

import map.*;
import entities.*;
import player.FungusPlayer;

public class FungusSpreadingSpores extends UseCase {

    @Override
    public void execute() {
        printWrapper("Initializing scene...", Direction.RIGHT, 0);

        printWrapper("Creating map...", Direction.RIGHT, 0);
        Map m = new Map();

        printWrapper("Creating tekton...", Direction.RIGHT, 0);
        map.Tekton tek = new map.Tekton(1, 1);

        m.addTekton(tek);

        printWrapper("Creating tile...", Direction.RIGHT, 0);
        Tile t1 = new Tile(1, 1, tek);

        printWrapper("Creating neighboring tile...", Direction.RIGHT, 0);
        Tile t2 = new Tile(1, 2, tek);

        printWrapper("Creating player...", Direction.RIGHT, 0);
        FungusPlayer fp = new FungusPlayer();

        printWrapper("Player trying to grow a body...", Direction.RIGHT, 0);

        //fp.growBody(

        printWrapper("Player trying to spread spores...", Direction.RIGHT, 0);
        //fp.sporeCloud(, 1);
    }

    public FungusSpreadingSpores() {
        super(3, "Fungus Spreading Spores");
    }
}
