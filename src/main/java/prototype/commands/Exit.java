package prototype.commands;

import prototype.Command;

public class Exit extends Command{

    public Exit(){
        super("exit", "Exits the program");
    }

	@Override
	public boolean execute(String[] args) {
        return true;
	}
    
}
