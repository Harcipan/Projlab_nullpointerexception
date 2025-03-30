package prototype.commands;
import prototype.*;

public class Tick extends Command {
    public Tick() {
        super("tick", "Run through the specified amount of rounds", "tick <round>");
    }

    @Override
    public boolean execute(String[] args) {
        throw new UnsupportedOperationException("not implemented");
    }
}
