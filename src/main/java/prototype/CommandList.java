package prototype;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import prototype.commands.*;

public class CommandList {
    Map<String, Command> commands = new HashMap<>();

    private void add(Command cmd){
        commands.put(cmd.getName(), cmd);
    }

    /*
     *       ADD YOUR COMMANDS HERE
     *  */
    public CommandList(App app) {
        add(new Create());
        add(new Destroy());
        add(new FungusBodyDie());
        add(new FungusBodyGrow());
        add(new FungusBodyReleaseSporeCloud());
        add(new InsectCantCut());
        add(new InsectCut());
        add(new InsectEatSpore());
        add(new InsectFreeze());
        add(new InsectSlowDown());
        add(new InsectSpeedUp());
        add(new InsectStep());
        add(new InsectUnfreeze());
        add(new Load());
        add(new MyceliumDie());
        add(new MyceliumGrow());
        add(new Save());
        add(new SetTileParentTekton());
        add(new SetTileType());
        add(new TektonBreaks());
        add(new TektonCantGrowFungus());
        add(new TektonMultipleMycelium());
        add(new TektonOneMycelium());
        add(new Tick());

        for(Command cmd : commands.values()){
            cmd.setApp(app);
        }
    }

    public Command get(String command){
        return commands.get(command);
    }
}
