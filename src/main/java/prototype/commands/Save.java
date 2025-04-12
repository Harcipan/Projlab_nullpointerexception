package prototype.commands;

import prototype.*;

public class Save extends Command {
    public Save() {
        super("save", "Write the game state to the specified file", "save <filename>");
    }

    @Override
    public boolean execute(String[] args) {
        // There isn't any way yet to do this.
        throw new UnsupportedOperationException("not implemented");
    }
}
