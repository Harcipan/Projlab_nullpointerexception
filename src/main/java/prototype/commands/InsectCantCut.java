package prototype.commands;
import prototype.*;

public class InsectCantCut extends Command {
    public InsectCantCut() {
        super("insect_cant_cut", "Insects won't be able to cut the mycelium connected to the supplied tectonic plate", "insect_cant_cut <tectonic plate id>");
    }

    @Override
    public boolean execute(String[] args) {
        throw new UnsupportedOperationException("not implemented");
    }
}
