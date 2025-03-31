package prototype.commands;

import java.util.jar.JarInputStream;

import prototype.Command;

public class Quit extends Command{

    public Quit(){
        super("quit", "Exits the program");
    }

	@Override
	public boolean execute(String[] args) {
        return true;
	}
    
}