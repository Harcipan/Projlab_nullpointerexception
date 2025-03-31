package prototype.commands;
import entities.FreezeSpore;
import entities.Insect;
import entities.SlowSpore;
import entities.Spore;
import prototype.*;

public class InsectFreeze extends Command {
    public InsectFreeze() {
        super("insect_freeze", "Paralyze the specified insect", "insect_freeze <insect id>");
    }

    @Override
    public boolean execute(String[] args) {
        if(isWrongNumberOfArgs(2, args.length)) return false;
        Insect insect = parseEntityId(args[1], "Insect");
        if(insect == null) return false;

        Spore s=new FreezeSpore();
        insect.getCurrentTile().addEntity(s);
        app.getInsectPlayer().addControlledInsect(insect);
        app.getInsectPlayer().eat(s, insect);
        return false;
    }
}
