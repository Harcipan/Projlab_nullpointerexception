package prototype.commands;

import java.util.List;

import map.MonoTile;
import map.Tekton;
import map.Tile;
import prototype.*;


public class TektonOneMycelium extends Command {
    public TektonOneMycelium() {
        super("tekton_one_mycelium", "Allow only one mycelium to grow on this tectonic plate",
                "tekton_one_mycelium <tectonic plate id>");
    }

    @Override
    public boolean execute(String[] args) {
        TektonWithId tekId=parseTektonWithId(args[1], "Tectonic plate");
        Tekton tek=tekId.getTekton();
        for (Tile tile : tek.getTiles()) {
            tile.setTileSpace(1);
        }

        return false;
    }
}
