package prototype.commands;
import prototype.*;

public class MyceliumGrow extends Command {
    public MyceliumGrow() {
        super("mycelium_grow", "Expand the mycelium to the specified tile", "mycelium_grow <mycelium id> <destination tile>");
    }

    @Override
    public boolean execute(String[] args) {
        throw new UnsupportedOperationException("not implemented");
    }
}
