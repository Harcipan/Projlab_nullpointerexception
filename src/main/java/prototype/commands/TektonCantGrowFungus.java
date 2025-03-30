package prototype.commands;
import prototype.*;

public class TektonCantGrowFungus extends Command {
    public TektonCantGrowFungus() {
        super("tekton_cant_grow_fungus", "Block any fungus bodies from growing on this tectonic plate", "tekton_cant_grow_fungus <tectonic plate id>");
    }

    @Override
    public boolean execute(String[] args) {
        throw new UnsupportedOperationException("not implemented");
    }
}
