package prototype.commands;

import map.Tekton;
import map.Tile;
import prototype.*;

public class TektonMultipleMycelium extends Command {
    public TektonMultipleMycelium() {
        super("tekton_multiple_mycelium", "Allow multiple mycelium to grow on the specified tectonic plate",
                "tekton_multiple_mycelium <tectonic plate id>");
    }

    @Override
    public boolean execute(String[] args) {
        TektonWithId tekId=parseTektonWithId(args[1], "Tectonic plate");
        Tekton tek=tekId.getTekton();
        for (Tile tile : tek.getTiles()) {
            tile.setTileSpace(10);
        }

        return false;
    }
}
