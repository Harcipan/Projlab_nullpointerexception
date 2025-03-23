package use_cases;

import map.Map;
import map.Tekton;
import map.Tile;
import player.FungusPlayer;

public class CannotGrowBodyOnTekton extends UseCase {

    public CannotGrowBodyOnTekton() {
        super(15, "Cannot Grow Body on Tekton");
    }

    @Override
    public void execute() {
        // initializing scene
        printWrapper("Initializing scene...", ArrowDirection.RIGHT, 0);

        // initializing map
        Map m = new Map();

        // initializing tekton
        Tekton tek = new Tekton(1, 1);

        // adding tekton to map
        m.addTekton(tek);

        // initializing tile
        Tile t1 = new Tile();
        Tile t2 = new Tile();

        // adding tiles to tekton
        tek.addTile(t1);
        tek.addTile(t2);

        // setting parent tekton for tiles
        t1.setParentTekton(tek);
        t2.setParentTekton(tek);

        // initializing player
        FungusPlayer fp = new FungusPlayer();

        // player trying to grow a mushroom
        fp.growBody(t1);

        // player trying to grow a mushroom on the same tekton
        // this should not work
        fp.growBody(t2);
    }
}
