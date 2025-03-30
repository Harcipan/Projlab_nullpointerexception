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

        int rounds;
        try {
            rounds = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("This is not a number. The number of rounds can only be a number.");
            return false;
        }
        if (rounds < 0) {
            System.out.println("The number of rounds must be positive");
            return false;
        }

        for(int i = 0; i < rounds; i++){
            app.getMap().tick();
        }
    }
}
