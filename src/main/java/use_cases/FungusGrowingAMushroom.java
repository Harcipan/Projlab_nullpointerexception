package use_cases;
import entities.Fungus;
import map.*;
import player.FungusPlayer;

public class FungusGrowingAMushroom extends UseCase {

    @Override
    public void execute() {
        // initializing scene
        System.out.println("Initializing scene...");

        // initializing map
        System.out.println("Creating map...");
        Map m = new Map();

        // initializing tekton
        System.out.println("Creating tekton...");
        Tekton tek = new Tekton(1, 1);

        // adding tekton to map
        m.addTekton(tek);

        // initializing tile
        System.out.println("Creating tile...");
        Tile t = new Tile(1, 1, tek);

        // initializing player
        System.out.println("Creating player...");
        FungusPlayer fp = new FungusPlayer();

        // player trying to grow a mushroom
        System.out.println("Player trying to grow a mushroom...");
        fp.growBody(t);
    }

    FungusGrowingAMushroom() {
        super(2, "Fungus Growing a Mushroom");
    }
}
