package prototype.commands;

import entities.FungusBody;
import map.Tekton;
import prototype.Command;

public class FungusBodyGrow extends Command {
    public FungusBodyGrow() {
        super("fungus_body_grow", "Make a fungus body grow on a specified tile",
                "fungus_body_grow <fungus body id> <destination tectonic plate id> <destination tile id>");
    }

    @Override
    public boolean execute(String[] args) {
        if (isWrongNumberOfArgs(4, args.length))
            return false;
        if (isMapUninitialized())
            return false;
        FungusBody fb = parseEntityId(args[1], "Fungus body");
        if (fb == null)
            return false;
        TektonAndTile tektonTile = parseTektonAndTile(args[2], args[3]);

        app.getFungusPlayer().growBody(tektonTile.getTile());
        return false;
    }

}
