package prototype.commands;
import prototype.*;

public class MyceliumDie extends Command {
    public MyceliumDie() {
        super("mycelium_die", "Kill the specified mycelium", "mycelium_die <mycelium id>");
    }

    @Override
    public boolean execute(String[] args) {
        throw new UnsupportedOperationException("not implemented");
    }
}
