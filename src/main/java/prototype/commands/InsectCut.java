package prototype.commands;
import entities.*;
import prototype.*;

public class InsectCut extends Command {
    public InsectCut() {
        super("insect_cut", "Have the insect cut a mycelium on a tile", "insect_cut <insect id> <mycelium id>");
    }

    @Override
    public boolean execute(String[] args) {
        if(isWrongNumberOfArgs(3, args.length)) return false;
        if(isMapUninitialized()) return false;
        Insect insect = parseEntityId(args[1], "Insect");
        if(insect == null) return false;
        Mycelium mycelium = parseEntityId(args[2], "Mycelium");
        if(mycelium == null) return false;

        app.getInsectPlayer().addControlledInsect(insect);
        app.getInsectPlayer().cut(mycelium.getCurrentTile(), insect); //Nonsense. Why would I ask for the mycelium id then? The tile & tekton id would be good enough.
        return false;
    }
}
