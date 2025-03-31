package prototype.commands;

import prototype.*;

public class TektonOneMycelium extends Command {
    public TektonOneMycelium() {
        super("tekton_one_mycelium", "Allow only one mycelium to grow on this tectonic plate",
                "tekton_one_mycelium <tectonic plate id>");
    }

    @Override
    public boolean execute(String[] args) {
        // There isn't any way yet to do this.
        throw new UnsupportedOperationException("not implemented");
    }
}
