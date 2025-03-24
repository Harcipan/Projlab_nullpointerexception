package use_cases;

import entities.*;
import map.*;
import player.*;

public class FungusSpreadingSpores extends UseCase {

    FungusSpreadingSpores() {
        super(12, "Fungus Spreading Spores");
    }

    @Override
    public void execute() {
        // Initializing scene
        printWrapper("Initializing scene...", ArrowDirection.RIGHT, 0);

        // Initializing map
        
        UseCase.logger.put(null, "m");
        Map m = new Map();

        // Initializing tekton
        UseCase.logger.put(null, "tek");
        Tekton tek = new Tekton(1, 1);

        // Adding tekton to map
        m.addTekton(tek);

        // Initializing tile
        UseCase.logger.put(null, "t");
        Tile t = new Tile();

        // Initializing fungusPlayer
        UseCase.logger.put(null, "fp");
        FungusPlayer fp = new FungusPlayer();

        // Initializing a FungusBody

        FungusBody fb = null;
        if (true /* TODO: check validity via fp function */) {
            
            UseCase.logger.put(null, "fb");
            fb = new FungusBody(1, 1, t);
            t.addEntity(fb);
        }

        // FungusPlayer spreading spores
        fp.sporeCloud(fb, 5);

    }
}