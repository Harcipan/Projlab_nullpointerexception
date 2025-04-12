package prototype.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import prototype.Command;

public class ListCmd extends Command {

    public ListCmd(){
        super("list", "List all commands available");
    }

	@Override
	public boolean execute(String[] args) {
        System.out.printf("%-100s%s\n", "Command", "Description");
        System.out.println("-".repeat(200));
        List<Command> list = new ArrayList<>(app.getCommands().getCommands());
        Collections.sort(list, new Comparator<Command>(){
			@Override
			public int compare(Command o1, Command o2) {
				return o1.getName().compareTo(o2.getName());
			}
            
        });
        for(Command cmd : list){
            System.out.printf("%-100s%s\n", cmd.getUsage(), cmd.getDescription());
        }
        return false;
	}
    
}
