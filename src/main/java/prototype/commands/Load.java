package prototype.commands;
import prototype.*;

public class Load extends Command {
    public Load() {
        super("load", "Load the game state from the specified file", "load <filename>");
    }

    @Override
    public boolean execute(String[] args) {
        throw new UnsupportedOperationException("not implemented");
    }
}
