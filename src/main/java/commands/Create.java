package commands;

import entities.CutSpore;
import entities.FreezeSpore;

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

    Create() {
        super("Create", "Creates a new entity or map element");
    }

    @Override
    public void execute() {

    }
}
