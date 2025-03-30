package prototype.commands;

import entities.*;
import map.Map;
import map.Tekton;
import map.Tile;
import prototype.Command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class Create extends Command {

    private final String[] validTypes = {
            "CutSpore",
            "FreezeSpore",
            "FungusBody",
            "Insect",
            "Mycelium",
            "SlowSpore",
            "SpeedUpSpore",
            "SlowSpore",
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

    final class Result {
        private final Tekton first;
        private final Tile second;

        public Result(Tekton first, Tile second) {
            this.first = first;
            this.second = second;
        }

        public Tekton getTekton() {
            return first;
        }

        public Tile getTile() {
            return second;
        }
    }

    private Optional<Result> promptForTektonAndTile() {
        if (isMapUninitialized())
            return Optional.empty();
        System.out.println("Tectonic plate id> ");
        String targetPlateIdRaw = scanner.nextLine().trim();
        int targetPlateId;
        try {
            targetPlateId = Integer.parseInt(targetPlateIdRaw);
        } catch (NumberFormatException e) {
            System.out.println("This is not a number. Tectonic plate ids can only be numbers.");
            return Optional.empty();
        }
        if (targetPlateId < 0) {
            System.out.println("Tectonic plate ids must be positive");
            return Optional.empty();
        }

        System.out.println("Target tile id> ");
        String targetTileIdRaw = scanner.nextLine().trim();
        int targetTileId;
        try {
            targetTileId = Integer.parseInt(targetTileIdRaw);
        } catch (NumberFormatException e) {
            System.out.println("This is not a number. Tile ids can only be numbers.");
            return Optional.empty();
        }
        if (targetTileId < 0) {
            System.out.println("Tile ids must be positive");
            return Optional.empty();
        }

        Tekton tek = app.getMap().getTektons().get(targetPlateId);
        if (tek == null) {
            System.out.println("A tectonic plate with the specified ID was not found");
            return Optional.empty();
        }

        Tile tile = tek.getTiles().get(targetTileId);
        if (tile == null) {
            System.out.println("A tile with the specified ID on the specified tectonic plate was not found");
            return Optional.empty();
        }

        return Optional.of(new Result(tek, tile));
    }

    @Override
    public boolean execute(String[] args) {
        if (args.length != 2) {
            wrongNumbersOfArgs(2, args.length);
            return false;
        }

        String type = args[1];
        switch (type.toLowerCase()) {
            case "map":
                app.setMap(new Map());
                System.out.println("Created the map");
                break;
            case "cutspore":
            {
                Optional<Result> res = promptForTektonAndTile();
                if(res.isEmpty()) break;

                res.get().getTile().addEntity(assignId(new CutSpore()));

                break;
            }
            case "freezespore":
            {
                Optional<Result> res = promptForTektonAndTile();
                if(res.isEmpty()) break;

                res.get().getTile().addEntity(assignId(new FreezeSpore()));
                break;
            }
            case "slowspore":
            {
                Optional<Result> res = promptForTektonAndTile();
                if(res.isEmpty()) break;

                res.get().getTile().addEntity(assignId(new SlowSpore()));
                break;
            }
            case "speedupspore":
            {
                Optional<Result> res = promptForTektonAndTile();
                if(res.isEmpty()) break;

                res.get().getTile().addEntity(assignId(new SpeedUpSpore()));
                break;
            }
            case "fungusbody":
                break;
            case "insect":
                break;
            case "mycelium":
                break;
            case "tekton":
                break;
            case "tile":
                break;
            case "acidtile":
                break;
            case "healtile":
                break;
            case "monotile":
                break;
            default:
                System.out.println("Invalid type specified");
                System.out.println("Valid types are: ");
                for (String altType : validTypes) {
                    System.out.println("- " + altType);
                }
        }

        return false;
    }
}
