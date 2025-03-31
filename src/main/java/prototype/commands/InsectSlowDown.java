package prototype.commands;

import entities.Insect;
import entities.SlowSpore;
import entities.Spore;
import prototype.*;

public class InsectSlowDown extends Command {
    public InsectSlowDown() {
        super("insect_slow_down",
                "Apply a slowdown effect on the specified insect so that it slows down to the specified percentage of its normal speed",
                "insect_slow_down <insect id> <speed percentage>");
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
        Integer speed = parsePositiveNumber(args[2], "Speed percentage");
        if (speed == null)
            return false;

        Spore s = new SlowSpore(speed);
        insect.getCurrentTile().addEntity(s);
        app.getInsectPlayer().addControlledInsect(insect);
        app.getInsectPlayer().eat(s, insect);
        return false;
    }
}
