package prototype.commands;
import entities.Insect;
import entities.SlowSpore;
import entities.SpeedUpSpore;
import entities.Spore;
import prototype.*;

public class InsectSpeedUp extends Command {
    public InsectSpeedUp() {
        super("insect_speed_up", "Apply a speedup effect on the specified insect so that its speed is now the specified percentage of its normal speed", "insect_speed_up <insect id> <speed percentage>");
    }

    @Override
    public boolean execute(String[] args) {
        if(isWrongNumberOfArgs(3, args.length)) return false;
        if(isMapUninitialized()) return false;
        Insect insect = parseEntityId(args[1], "Insect");
        if(insect == null) return false;
        Integer speed = parsePositiveNumber(args[2], "Speed percentage");
        if(speed == null) return false;

        Spore s=new SpeedUpSpore(speed);
        insect.getCurrentTile().addEntity(s);
        app.getInsectPlayer().addControlledInsect(insect);
        app.getInsectPlayer().eat(s, insect);
        return false;
    }
}
