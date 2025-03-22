package use_cases;

import map.Tile;
import entities.*;
import map.*;
import player.*;

public class FungusSpreadingSpores extends UseCase {

    FungusSpreadingSpores() {
        super(13, "Fungus Spreading Spores");
    }

    @Override
    public void execute() {
        // Initializing scene
        printWrapper("Initializing scene...", ArrowDirection.RIGHT, 0);

        // Initializing map
        Map m = new Map();

        // Initializing tekton
        Tekton tek = new Tekton(1, 1);

        // Adding tekton to map
        m.addTekton(tek);

        // Initializing tile
        Tile t = new Tile();

        // Initializing fungusPlayer
        FungusPlayer fp = new FungusPlayer();

        // Initializing a FungusBody

        FungusBody fb = null;
        if (true /* TODO: check validity via fp function */) {
            fb = new FungusBody(1, t);
            t.addEntity(fb);
        }

        // FungusPlayer spreading spores
        fp.sporeCloud(fb, 5);

    }
}