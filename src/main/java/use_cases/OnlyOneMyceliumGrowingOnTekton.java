package use_cases;

import map.Map;
import map.Tekton;
import map.Tile;
import player.FungusPlayer;

public class OnlyOneMyceliumGrowingOnTekton extends UseCase {

    public OnlyOneMyceliumGrowingOnTekton() {
        super(17, "Only One Mycelium Growing on Tekton");
    }

    @Override
    public void execute() {
        // initializing scene
        printWrapper("Initializing scene...", ArrowDirection.RIGHT, 0);

        // initializing map
        
        UseCase.logger.put(null, "m");
        Map m = new Map();

        // initializing tekton
        
        UseCase.logger.put(null, "tek");
        Tekton tek = new Tekton(m);

        // adding tekton to map
        m.addTekton(tek);

        // initializing tile
        UseCase.logger.put(null, "t1");
        Tile t1 = new Tile();
        UseCase.logger.put(null, "t2");
        Tile t2 = new Tile();

        // adding tiles to tekton
        tek.addTile(t1);
        tek.addTile(t2);

        // setting parent tekton for tiles
        t1.setParentTekton(tek);
        t2.setParentTekton(tek);

        // initializing player
        UseCase.logger.put(null, "fp");
        FungusPlayer fp = new FungusPlayer();

        // player trying to grow a mushroom
        fp.growBody(t1);

        // player trying to grow a mushroom on the same tekton
        // this should not work
        fp.growBody(t2);
    }
}
