package prototype.commands;
import prototype.*;

public class InsectStep extends Command {
    public InsectStep() {
        super("insect_step", "Have the specified insect go to destination tile", "insect_step <insect id> <destination tile>");
    }

    @Override
    public boolean execute(String[] args) {
        throw new UnsupportedOperationException("not implemented");
    }
}
