package prototype.commands;
import prototype.*;

public class InsectSlowDown extends Command {
    public InsectSlowDown() {
        super("insect_slow_down", "Apply a slowdown effect on the specified insect so that it slows down to the specified percentage of its normal speed", "insect_slow_down <insect id> <speed percentage>");
    }

    @Override
    public boolean execute(String[] args) {
        throw new UnsupportedOperationException("not implemented");
    }
}
