package use_cases;
import map.*;
import player.FungusPlayer;

public class FungusGrowingAMushroom extends UseCase {

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
        Tile t = new Tile();

        // initializing player
        FungusPlayer fp = new FungusPlayer();

        // player trying to grow a mushroom
        fp.growBody(t);
    }

    FungusGrowingAMushroom() {
        super(12, "Fungus Growing a Mushroom");
    }
}
