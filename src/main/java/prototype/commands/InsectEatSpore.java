package prototype.commands;

import entities.*;
import prototype.*;

public class InsectEatSpore extends Command {
    public InsectEatSpore() {
        super("insect_eat_spore", "Have the insect eat the specified spore", "insect_eat_spore <insect id> <spore id>");
    }

    @Override
    public boolean execute(String[] args) {
        if (isWrongNumberOfArgs(3, args.length))
            return false;
        if (isMapUninitialized())
            return false;
        Insect insect = parseEntityId(args[1], "Insect");
        if (insect == null)
            return false;
        Spore spore = parseEntityId(args[2], "Spore");
        if (spore == null)
            return false;

        app.getInsectPlayers().get(0).eat(spore, insect);
        return false;
    }
}
