package prototype.commands;

import entities.Mycelium;
import map.*;
import prototype.*;

public class SetTileType extends Command {
    public SetTileType() {
        super("set_tile_type", "Set the tile's type", "set_tile_type <tectonic plate id> <tile id> <Mono|Heal|Acid>");
    }

    @Override
    public boolean execute(String[] args) {
        if (isWrongNumberOfArgs(4, args.length))
            return false;
        if (isMapUninitialized())
            return false;
        TektonAndTile tektonTile = parseTektonAndTile(args[1], args[2]);
        if (tektonTile == null)
            return false;
        Integer tileId = parsePositiveNumber(args[2], "Tile ID");
        Tekton tek = tektonTile.getTekton();
        Tile tile = tektonTile.getTile();
        tek.getTiles().remove(tile);

        // This command is a cookie...
        switch (args[3].toLowerCase()) {
            case "mono":
                tek.getTiles().add(tileId,
                        new MonoTile(tile.getGrowthRate(), tile.getParentTekton(),tile.getX(),tile.getY()));

                break;
            case "heal":
                tek.getTiles().add(tileId,
                        new HealTile(tile.getGrowthRate(), tile.getMaxMycelium(), tile.getParentTekton(),tile.getX(),tile.getY()));
                break;
            case "acid":
                tek.getTiles().add(tileId,
                        new AcidTile(tile.getGrowthRate(), tile.getMaxMycelium(), tile.getParentTekton(), 5,tile.getX(),tile.getY())); // TODO:
                                                                                                               // Ask
                                                                                                               // for
                                                                                                               // damage
                                                                                                               // rate
                break;
            default:
                tek.getTiles().add(tile);
                System.out.println(
                        "Invalid tile type \"" + args[3] + "\". Valid tile types are \"Mono\", \"Heal\", or \"Acid\"");
        }
        return false;
    }
}
