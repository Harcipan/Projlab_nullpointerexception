package prototype.commands;

import entities.Mycelium;
import prototype.*;

public class MyceliumDie extends Command {
    public MyceliumDie() {
        super("mycelium_die", "Kill the specified mycelium", "mycelium_die <mycelium id>");
    }

    @Override
    public boolean execute(String[] args) {
        if (isWrongNumberOfArgs(2, args.length))
            return false;
        if (isMapUninitialized())
            return false;
        Mycelium mycelium = parseEntityId(args[1], "Mycelium");
        if (mycelium == null)
            return false;

        mycelium.die();
        mycelium.getCurrentTile().removeEntity(mycelium);
        return false;
    }
}
