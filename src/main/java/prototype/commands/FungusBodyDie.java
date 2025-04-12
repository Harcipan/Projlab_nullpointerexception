package prototype.commands;

import entities.FungusBody;
import prototype.Command;

public class FungusBodyDie extends Command {
    public FungusBodyDie() {
        super("fungus_body_die", "Kill a fungus body", "fungus_body_die <fungus body id>");
    }

    @Override
    public boolean execute(String[] args) {
        if (isWrongNumberOfArgs(2, args.length))
            return false;
        if (isMapUninitialized())
            return false;
        FungusBody fb = parseEntityId(args[1], "Fungus body");
        if (fb == null)
            return false;
        app.getFungusPlayer().sporeCloud(fb, 1);
        app.getFungusPlayer().sporeCloud(fb, 1);
        app.getFungusPlayer().sporeCloud(fb, 1);
        return false;
    }
}
