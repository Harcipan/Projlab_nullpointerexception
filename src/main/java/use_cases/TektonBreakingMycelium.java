package use_cases;

import entities.FungusBody;
import entities.GameEntity;
import entities.Mycelium;
import map.Map;
import map.Tekton;
import map.Tile;
import player.FungusPlayer;

import java.util.ArrayList;
import java.util.List;

public class TektonBreakingMycelium extends UseCase {

    public TektonBreakingMycelium() {
        super(19, "Tekton breaking with mycelium");
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

        // Adding tile to tekton
        UseCase.logger.put(null, "t1");
        Tile t1 = new Tile();
        UseCase.logger.put(null, "t2");
        Tile t2 = new Tile();
        t1.setParentTekton(tek);
        t2.setParentTekton(tek);

        tek.addTile(t1);
        tek.addTile(t2);

        // Adding fungusPlayer
        UseCase.logger.put(null, "fp");
        FungusPlayer fp = new FungusPlayer();

        // Adding fungusBody
        fp.growBody(t1);

        // Adding Mycelium
        Mycelium myc = fp.growMycelium(t2);


        // TODO Lets say a map update happens and the tekton breaks
        List<Tekton> tl = tek.breakTekton();

        // TODO lets say based on some rules that the mycelium should be removed after the tekton breaking

        // Removing mycelia from the tektons
        ArrayList<Mycelium> removableMycelia = new ArrayList<>();
        for (Tile t : tek.getTiles()) {
            for (GameEntity e : t.getEntities()) {
                if (e instanceof Mycelium) {
                    removableMycelia.add((Mycelium) e);
                }
            }
        }
        for (Mycelium mycelium : removableMycelia) {
            mycelium.die();
        }
    }
}
