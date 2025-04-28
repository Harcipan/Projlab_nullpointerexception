package prototype.commands;

import entities.Fungus;
import entities.FungusBody;
import entities.Insect;
import map.Tile;
import prototype.Command;

public class MyceliumConsumeFrozenInsect extends Command {
    public MyceliumConsumeFrozenInsect() {
        super("consume_insect", "The mycelium consumes the paralized insect on it.", 
        "consume_insect <insect id> <fungusplayer id>");
    }

    @Override
    public boolean execute(String[] args) {
        if (isWrongNumberOfArgs(3, args.length))
            return false;
        if (isMapUninitialized())
            return false;
        Insect insect = parseEntityId(args[1], "Insect");
        
        Tile t=insect.getCurrentTile();
        for(int i=0;i<FungusBody.BODY_COST;i++) {
            t.getParentTekton().addPlayerSpore(app.getFungusPlayers().get(parsePositiveNumber(args[2],"player ID")));
        }
        app.getFungusPlayers().get(parsePositiveNumber(args[2],"player ID")).growBody(t);
        insect.die();

        return false;
    }
}
