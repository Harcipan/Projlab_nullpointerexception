package prototype.commands;
import prototype.*;

public class InsectUnfreeze extends Command {
    public InsectUnfreeze() {
        super("insect_unfreeze", "Unparalyze an insect", "insect_unfreeze <insect id>");
    }

    @Override
    public boolean execute(String[] args) {
        throw new UnsupportedOperationException("not implemented");
    }
}
