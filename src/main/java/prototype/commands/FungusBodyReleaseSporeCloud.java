package prototype.commands;
import entities.FungusBody;
import prototype.*;

public class FungusBodyReleaseSporeCloud extends Command {
    public FungusBodyReleaseSporeCloud() {
        super("fungus_body_release_spore_cloud", "Have the fungus body spread its spores all over the place", "fungus_body_release_spore_cloud <fungus body id>");
    }

    @Override
    public boolean execute(String[] args) {
        if(isWrongNumberOfArgs(2, args.length)) return false;
        if(isMapUninitialized()) return false;
        FungusBody fb = parseEntityId(args[1], "Fungus body");
        if(fb == null) return false;
        
        app.getFungusPlayer().sporeCloud(fb, 5);
        return false;
    }
}
