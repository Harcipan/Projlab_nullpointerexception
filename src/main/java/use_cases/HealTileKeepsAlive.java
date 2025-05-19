package use_cases;
import map.*;
import player.*;
import entities.*;


public class HealTileKeepsAlive extends UseCase {
    HealTileKeepsAlive() {
        super(21, "Heal Tile Keeps Alive");
    }

    @Override
    public void execute() {

        // Initializing Map
        Map m = new Map();

        // Initializing Tekton
        Tekton tek = new Tekton(m);

        // Adding Tekton to Map
        m.addTekton(tek);

        // Initializing Tile
        HealTile t = new HealTile(1, 1, tek,0,0);

        // Adding Tile to Tekton and setting parent
        tek.addTile(t);

        // Adding mycelium without any parent body
        Mycelium myc = new Mycelium();
        myc.setTile(t);

        // instead of full game update, we simulate the heal
        myc.update();
    }
}
