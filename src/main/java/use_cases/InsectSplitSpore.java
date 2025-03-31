package use_cases;

import map.*;
import entities.*;
import player.*;

public class InsectSplitSpore extends UseCase {

    public InsectSplitSpore() {
        super(20, "Insect Split Spore");
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
        Tekton tek = new Tekton(1, 1);

        // adding tekton to map
        m.addTekton(tek);

        // initializing tile
        UseCase.logger.put(null, "t");
        Tile t = new Tile();

        // adding tile to tekton
        tek.addTile(t);

        // initializing insectPlayer
        UseCase.logger.put(null, "ip");
        InsectPlayer ip = new InsectPlayer();

        // initializing insect
        UseCase.logger.put(null, "i");
        Insect i = new Insect(1, t, ip);

        // adding insect to insectPlayer
        ip.addControlledInsect(i);

        // adding a split spore
        UseCase.logger.put(null, "ss");
        Spore ss = new SplitSpore();

        // adding split spore to tile
        t.addEntity(ss);

        // insectPlayer trying to eat the split spore
        ip.eat(ss, i);

    }
}
