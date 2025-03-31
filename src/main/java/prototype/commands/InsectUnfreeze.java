package prototype.commands;
import entities.FreezeSpore;
import entities.Insect;
import entities.SpeedUpSpore;
import entities.Spore;
import prototype.*;

public class InsectUnfreeze extends Command {
    public InsectUnfreeze() {
        super("insect_unfreeze", "Unparalyze an insect", "insect_unfreeze <insect id>");
    }

    @Override
    public boolean execute(String[] args) {
        if(isWrongNumberOfArgs(2, args.length)) return false;
        if(isMapUninitialized()) return false;
        Insect insect = parseEntityId(args[1], "Insect");
        if(insect == null) return false;

        Spore s=new SpeedUpSpore(0);
        insect.getCurrentTile().addEntity(s);
        app.getInsectPlayer().addControlledInsect(insect);
        app.getInsectPlayer().eat(s, insect);
        return false;
    }
}
