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
        Map m = new Map();
        UseCase.logger.put(m, "m");

        // initializing tekton
        Tekton tek = new Tekton(m);
        UseCase.logger.put(tek, "tek");

        // adding tekton to map
        m.addTekton(tek);

        // initializing tile
        Tile t = new Tile();
        UseCase.logger.put(t, "t");

        // adding tile to tekton
        tek.addTile(t);

        // initializing insectPlayer
        InsectPlayer ip = new InsectPlayer();
        UseCase.logger.put(ip, "ip");

        // initializing insect
        Insect i = new Insect(1, t, ip);
        UseCase.logger.put(i, "i");

        // adding insect to insectPlayer
        ip.addControlledInsect(i);

        // adding a split spore
        Spore ss = new SplitSpore();
        UseCase.logger.put(ss, "ss");

        // adding split spore to tile
        t.addEntity(ss);

        // insectPlayer trying to eat the split spore
        ip.eat(ss, i);

    }
}
