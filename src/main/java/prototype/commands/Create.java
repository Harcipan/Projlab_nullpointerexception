package prototype.commands;

import entities.CutSpore;
import entities.FreezeSpore;
import prototype.Command;

import java.util.ArrayList;
import java.util.HashMap;

public class Create extends Command {

    private final String[] creatableEntities = {
            "CutSpore",
            "FreezeSpore",
            "FungusBody",
            "Insect",
            "Mycelium",
            "SlowSpore",
            "SpeedUpSpore",
            "SlowSpore",
    };

    private final String[] creatableMapElements = {
            "AcidTile",
            "HealTile",
            "Map",
            "MonoTile",
            "Tekton",
            "Tile"
    };

    public Create() {
        super("create", "Creates a new entity or map element", "create <type>");
    }

    @Override
    public boolean execute(String[] args) {


        return false;
    }
}
