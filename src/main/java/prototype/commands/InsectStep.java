package prototype.commands;

import entities.Insect;
import prototype.*;

public class InsectStep extends Command {
    public InsectStep() {
        super("insect_step", "Have the specified insect go to destination tile",
                "insect_step <insect id> <destination tectonic plate> <destination tile>");
    }

    @Override
    public boolean execute(String[] args) {
        if (isWrongNumberOfArgs(4, args.length))
            return false;
        if (isMapUninitialized())
            return false;
        Insect insect = parseEntityId(args[1], "Insect");
        if (insect == null)
            return false;
        TektonAndTile tektonTile = parseTektonAndTile(args[2], args[3]);
        if (tektonTile == null)
            return false;

        app.getInsectPlayer().addControlledInsect(insect);
        app.getInsectPlayer().moveTo(tektonTile.getTile(), insect);
        return false;
    }
}
