package prototype.commands;
import prototype.*;

public class TektonMultipleMycelium extends Command {
    public TektonMultipleMycelium() {
        super("tekton_multiple_mycelium", "Allow multiple mycelium to grow on the specified tectonic plate", "tekton_multiple_mycelium <tectonic plate id>");
    }

    @Override
    public boolean execute(String[] args) {
        //There isn't any way yet to do this.
        throw new UnsupportedOperationException("not implemented");
    }
}
