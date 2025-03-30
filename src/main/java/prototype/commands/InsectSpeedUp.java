package prototype.commands;
import prototype.*;

public class InsectSpeedUp extends Command {
    public InsectSpeedUp() {
        super("insect_speed_up", "Apply a speedup effect on the specified insect so that its speed is now the specified percentage of its normal speed", "insect_speed_up <insect id> <speed percentage>");
    }

    @Override
    public boolean execute(String[] args) {
        throw new UnsupportedOperationException("not implemented");
    }
}
