package prototype.commands;

import prototype.Command;

public class FungusBodyGrow extends Command{
    public FungusBodyGrow(){
        super("fungus_body_grow", "Make a fungus body grow on a specified tile", "fungus_body_grow <fungus body id> <destination>");
    }

    @Override
    public boolean execute(String[] args) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
    
}
