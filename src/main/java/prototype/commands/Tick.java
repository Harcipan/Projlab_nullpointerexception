package prototype.commands;
import prototype.*;

public class Tick extends Command {
    public Tick() {
        super("tick", "Run through the specified amount of rounds", "tick <round>");
    }

    @Override
    public boolean execute(String[] args) {
        if(isWrongNumberOfArgs(2, args.length)) return false;
        if(isMapUninitialized()) return false;

        Integer rounds = parsePositiveNumber(args[1], "Number of rounds");
        if(rounds == null) return false;

        for(int i = 0; i < rounds; i++){
            app.getMap().tick();
        }
    }
}
