package prototype.commands;

import prototype.*;

public class SetTileParentTekton extends Command {
    public SetTileParentTekton() {
        super("set_tile_parent_tekton", "Set the parent tectonic plate of the tile",
                "set_tile_parent_tekton <tile id> <parent tectonic plate id>");
    }

    @Override
    public boolean execute(String[] args) {
        // This command is moot
        throw new UnsupportedOperationException("not implemented");
    }
}
