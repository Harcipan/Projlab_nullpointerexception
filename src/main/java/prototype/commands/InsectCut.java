package prototype.commands;

import entities.*;
import player.FungusPlayer;
import player.InsectPlayer;
import prototype.*;
import prototype.App;

public class InsectCut extends Command {
    public InsectCut() {
        super("insect_cut", "Have the insect cut a mycelium on a tile", "insect_cut <insect player ID> <insect id> <fungus player ID> <mycelium id>");
    }

    @Override
    public boolean execute(String[] args) {
        if (isWrongNumberOfArgs(5, args.length))
            return false;
        if (isMapUninitialized())
            return false;
        InsectPlayer insectPlayer = App.getInsectPlayers().get(Integer.parseInt(args[1]));
        Insect insect = insectPlayer.getControlledInsects().get(Integer.parseInt(args[2])); //parseEntityId(args[1], "Insect");
        if (insect == null)
            return false;
        FungusPlayer fungusPlayer = App.getFungusPlayers().get(Integer.parseInt(args[3]));
        Mycelium mycelium = fungusPlayer.getMycelia().get(Integer.parseInt(args[4]));//parseEntityId(args[2], "Mycelium");
        if (mycelium == null)
            return false;

        insectPlayer.cut(mycelium.getCurrentTile(), insect);
        return false;
    }
}
