package use_cases;
import map.*;
import player.FungusPlayer;

public class FungusGrowingAMushroom extends UseCase {

    FungusGrowingAMushroom() {
        super(12, "Fungus Growing a Mushroom");
    }

    @Override
    public void execute() {
        // initializing scene
        printWrapper("Initializing scene...", ArrowDirection.RIGHT, 0);

        // initializing map
        logger.put(null, "m");
        Map m = new Map();

        // initializing tekton
        UseCase.logger.put(null, "tek");
        Tekton tek = new Tekton(1, 1, m);

        // adding tekton to map
        m.addTekton(tek);

        // initializing tile
        UseCase.logger.put(null, "t");
        Tile t = new Tile();

        //add tile to tekton
        t.setParentTekton(tek);

        // initializing player
        UseCase.logger.put(null, "fp");
        FungusPlayer fp = new FungusPlayer();

        // player trying to grow a mushroom
        fp.growBody(t);
    }
}
