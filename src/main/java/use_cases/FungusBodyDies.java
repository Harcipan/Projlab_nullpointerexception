package use_cases;

import entities.FungusBody;
import map.Map;
import map.Tekton;
import map.Tile;
import player.FungusPlayer;

public class FungusBodyDies extends UseCase {

    public FungusBodyDies() {
        super(14, "Fungus Body Dies");
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
        Tekton tek = new Tekton(m);

        // Adding tekton to map
        m.addTekton(tek);

        // Initializing tile
        UseCase.logger.put(null, "t");
        Tile t = new Tile();

        t.setParentTekton(tek);

        // Initializing fungusPlayer
        UseCase.logger.put(null, "fp");
        FungusPlayer fp = new FungusPlayer();

        // Initializing a FungusBody

        FungusBody fb = null;
        if (true /* TODO: check validity via fp function */) {
            UseCase.logger.put(null, "fb");
            fb = new FungusBody(1, 3, t);
            t.addEntity(fb);
        }

        // FungusPlayer spreading spores
        fp.sporeCloud(fb, 1);
        fp.sporeCloud(fb, 1);
        fp.sporeCloud(fb, 1);
    }
}
