package use_cases;

import map.*;
import player.FungusPlayer;

public class FungusSpreadingSpores extends UseCase {

    @Override
    public void execute() {
        printWrapper("Initializing scene...", ArrowDirection.RIGHT, 0);

        printWrapper("Creating map...", ArrowDirection.RIGHT, 0);
        Map m = new Map();

        printWrapper("Creating tekton...", ArrowDirection.RIGHT, 0);
        map.Tekton tek = new map.Tekton(1, 1);

        m.addTekton(tek);

        printWrapper("Creating tile...", ArrowDirection.RIGHT, 0);
        Tile t1 = new Tile();

        printWrapper("Creating neighboring tile...", ArrowDirection.RIGHT, 0);
        Tile t2 = new Tile();

        printWrapper("Creating player...", ArrowDirection.RIGHT, 0);
        FungusPlayer fp = new FungusPlayer();

        printWrapper("Player trying to grow a body...", ArrowDirection.RIGHT, 0);

        //fp.growBody(

        printWrapper("Player trying to spread spores...", ArrowDirection.RIGHT, 0);
        //fp.sporeCloud(, 1);
    }

    public FungusSpreadingSpores() {
        super(3, "Fungus Spreading Spores");
    }
}
