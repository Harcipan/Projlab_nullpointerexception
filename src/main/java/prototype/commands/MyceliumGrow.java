package prototype.commands;
import entities.Insect;
import entities.Mycelium;
import prototype.*;

public class MyceliumGrow extends Command {
    public MyceliumGrow() {
        super("mycelium_grow", "Expand the mycelium to the specified tile", "mycelium_grow <mycelium id> <destination tectonic plate> <destination tile>");
    }

    @Override
    public boolean execute(String[] args) {
        if(isWrongNumberOfArgs(4, args.length)) return false;
        if(isMapUninitialized()) return false;
        Mycelium mycelium = parseEntityId(args[1], "Mycelium");
        if(mycelium == null) return false;
        TektonAndTile tektonTile = parseTektonAndTile(args[2], args[3]);
        if(tektonTile == null) return false;

        app.getFungusPlayer().growMycelium(tektonTile.getTile());
        return false;
    }
}
