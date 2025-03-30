package prototype.commands;
import prototype.*;

public class FungusBodyReleaseSporeCloud extends Command {
    public FungusBodyReleaseSporeCloud() {
        super("fungus_body_release_spore_cloud", "Have the fungus body spread its spores all over the place", "fungus_body_release_spore_cloud <fungus body id>");
    }

    @Override
    public boolean execute(String[] args) {
        throw new UnsupportedOperationException("not implemented");
    }
}
