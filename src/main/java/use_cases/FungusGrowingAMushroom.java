package use_cases;
import entities.Fungus;
import map.*;
import player.FungusPlayer;

public class FungusGrowingAMushroom extends UseCase {

    @Override

    public void execute() {
        // initializing scene
        printWrapper("Initializing scene...", Direction.RIGHT, 0);

        // initializing map
        printWrapper("Creating map...", Direction.RIGHT, 0);
        Map m = new Map();

        // initializing tekton
        printWrapper("Creating tekton...", Direction.RIGHT, 0);
        Tekton tek = new Tekton(1, 1);

        // adding tekton to map
        m.addTekton(tek);

        // initializing tile
        printWrapper("Creating tile...", Direction.RIGHT, 0);
        Tile t = new Tile(1, 1, tek);

        // initializing player
        printWrapper("Creating player...", Direction.RIGHT, 0);
        FungusPlayer fp = new FungusPlayer();

        // player trying to grow a mushroom
        printWrapper("Player trying to grow a mushroom...", Direction.RIGHT, 0);
        fp.growBody(t);
    }

    FungusGrowingAMushroom() {
        super(2, "Fungus Growing a Mushroom");
    }
}
