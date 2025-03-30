package prototype.commands;

import prototype.Command;

public class Destroy extends Command{
    public Destroy() {
        super("destroy", "Destroy a given object", "destroy <object id>");
    }

    @Override
    public boolean execute(String[] args) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
}
