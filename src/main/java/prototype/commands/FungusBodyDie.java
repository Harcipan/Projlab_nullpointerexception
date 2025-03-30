package prototype.commands;

import prototype.Command;

public class FungusBodyDie extends Command {
    public FungusBodyDie(){
        super("fungus_body_die", "Kill a fungus body", "fungus_body_die <fungus body id>");
    }

    @Override
    public boolean execute(String[] args) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
}
