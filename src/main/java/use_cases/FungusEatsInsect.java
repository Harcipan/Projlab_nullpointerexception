package use_cases;

import entities.*;
import map.*;
import player.*;

public class FungusEatsInsect extends UseCase {
    FungusEatsInsect() {
        super(22, "Fungus Eats Insect");
    }

    @Override
    public void execute() {
        // Initializing Map
        Map m = new Map();

        // Initializing Tekton
        Tekton tek = new Tekton(1, 1);

        // Adding Tekton to Map
        m.addTekton(tek);

        // Initializing Tile
        HealTile t = new HealTile(1, 1, tek);

        // Adding Tile to Tekton and setting parent
        tek.addTile(t);

        // Adding fungusPlayer
        FungusPlayer fp = new FungusPlayer();

        // Adding FungusBody
        FungusBody fb = fp.growBody(t);

        // Adding InsectPlayer
        InsectPlayer ip = new InsectPlayer();

        // Adding Insect
        Insect i = new Insect(1, t, ip);

        // simulating a total slowdown for the insect
        i.setSpeedPercent(-1); // who invented this abomination?

        // Eating the Insect
        fp.consumeInsect(i);
    }
}