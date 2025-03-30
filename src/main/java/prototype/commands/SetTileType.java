package prototype.commands;
import prototype.*;

public class SetTileType extends Command {
    public SetTileType() {
        super("set_tile_type", "Set the tile's type", "set_tile_type <tile id> <Mono|Heal|Acid>");
    }

    @Override
    public boolean execute(String[] args) {
        throw new UnsupportedOperationException("not implemented");
    }
}
