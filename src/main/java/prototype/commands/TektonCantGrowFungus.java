package prototype.commands;

import map.Tekton;
import prototype.*;

public class TektonCantGrowFungus extends Command {
    public TektonCantGrowFungus() {
        super("tekton_cant_grow_fungus", "Block any fungus bodies from growing on this tectonic plate",
                "tekton_cant_grow_fungus <tectonic plate id>");
    }

    @Override
    public boolean execute(String[] args) {
        if (isWrongNumberOfArgs(2, args.length))
            return false;
        if (isMapUninitialized())
            return false;

        Tekton tek = parseTekton(args[1], "Tectonic plate");
        if (tek == null)
            return false;

        //:(
        tek.setCanGrowFungus(false);

        return false;
    }
}
