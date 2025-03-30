package prototype.commands;
import prototype.*;

public class InsectFreeze extends Command {
    public InsectFreeze() {
        super("insect_freeze", "Paralyze the specified insect", "insect_freeze <insect id>");
    }

    @Override
    public boolean execute(String[] args) {
        throw new UnsupportedOperationException("not implemented");
    }
}
