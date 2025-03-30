package prototype.commands;
import prototype.*;

public class InsectCut extends Command {
    public InsectCut() {
        super("insect_cut", "Have the insect cut a mycelium on a tile", "insect_cut <insect id> <mycelium id>");
    }

    @Override
    public boolean execute(String[] args) {
        throw new UnsupportedOperationException("not implemented");
    }
}
