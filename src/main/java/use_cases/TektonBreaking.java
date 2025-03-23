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
        super(14, "Tekton breaking");
    }

    @Override
    public void execute() {
        // Initializing scene
        printWrapper("Initializing scene...", ArrowDirection.RIGHT, 0);

        // Initializing map
        Map m = new Map();

        // Initializing tekton
        Tekton tek = new Tekton(1, 1);

        // Adding tekton to map
        m.addTekton(tek);

        // TODO Lets say a map update happens and the tekton breaks
        ArrayList<Tekton> tl = tek.breakTekton();

    }
}
