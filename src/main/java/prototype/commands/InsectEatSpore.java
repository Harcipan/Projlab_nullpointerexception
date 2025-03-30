package prototype.commands;
import prototype.*;

public class InsectEatSpore extends Command {
    public InsectEatSpore() {
        super("insect_eat_spore", "Have the insect eat the specified spore", "insect_eat_spore <insect id> <spore id>");
    }

    @Override
    public boolean execute(String[] args) {
        throw new UnsupportedOperationException("not implemented");
    }
}
