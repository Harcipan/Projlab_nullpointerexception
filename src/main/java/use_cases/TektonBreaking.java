package use_cases;

import entities.FungusBody;
import map.Map;
import map.Tekton;
import map.Tile;
import player.FungusPlayer;

import java.util.ArrayList;
import java.util.List;

public class TektonBreaking extends UseCase {

    public TektonBreaking() {
        super(15, "Tekton breaking");
    }

    @Override
    public void execute() {
        // Initializing scene
        printWrapper("Initializing scene...", ArrowDirection.RIGHT, 0);

        // Initializing map
        UseCase.logger.put(null, "m");
        Map m = new Map();

        // Initializing tekton
        UseCase.logger.put(null, "tek");
        Tekton tek = new Tekton(m);

        // Adding tekton to map
        m.addTekton(tek);

        // TODO Lets say a map update happens and the tekton breaks
        ArrayList<Tekton> tl = tek.breakTekton();

    }
}
