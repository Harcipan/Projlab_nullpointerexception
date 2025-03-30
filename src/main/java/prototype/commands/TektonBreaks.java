package prototype.commands;
import prototype.*;

public class TektonBreaks extends Command {
    public TektonBreaks() {
        super("tekton_breaks", "Break the tectonic plate and cut all mycelium on it", "tekton_breaks <tectonic plate id>");
    }

    @Override
    public boolean execute(String[] args) {
        throw new UnsupportedOperationException("not implemented");
    }
}
