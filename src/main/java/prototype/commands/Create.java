package prototype.commands;

import entities.*;
import map.AcidTile;
import map.HealTile;
import map.Map;
import map.MonoTile;
import map.Tekton;
import map.Tile;
import prototype.Command;

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

    public final class TileData {
        private final int growthRate;
        private final int maxMycelium;
        private final Tekton parentTekton;

        public TileData(int growthRate, int maxMycelium, Tekton parentTekton) {
            this.growthRate = growthRate;
            this.maxMycelium = maxMycelium;
            this.parentTekton = parentTekton;
        }

        public int getGrowthRate() {
            return growthRate;
        }

        public int getMaxMycelium() {
            return maxMycelium;
        }

        public Tekton getParentTekton() {
            return parentTekton;
        }
    }

    private Integer promptForPositiveInteger(String forWhat) {
        System.out.print(forWhat + "> ");
        String rawStr = scanner.nextLine().trim();
        return parsePositiveNumber(rawStr, forWhat);
    }

    private Tekton promptForTekton(String forWhat) {
        Integer targetPlateId = promptForPositiveInteger(forWhat);
        if (targetPlateId == null)
            return null;

        Tekton tek = app.getMap().getTektons().get(targetPlateId);
        if (tek == null) {
            System.out.println("A tectonic plate with the specified ID was not found");
            return null;
        }
        return tek;
    }

    private TektonAndTile promptForTektonAndTile() {
        if (isMapUninitialized())
            return null;

        Tekton tek = promptForTekton("Tectonic plate id");
        if (tek == null)
            return null;

        Integer targetTileId = promptForPositiveInteger("Tile id");
        if (targetTileId == null)
            return null;

        Tile tile = tek.getTiles().get(targetTileId);
        if (tile == null) {
            System.out.println("A tile with the specified ID on the specified tectonic plate was not found");
            return null;
        }

        return new TektonAndTile(tek, tile);
    }

    private TileData promptForTileData() {
        if (isMapUninitialized())
            return null;

        Tekton tek = promptForTekton("Parent tectonic plate id");
        if (tek == null)
            return null;

        Integer growthRate = promptForPositiveInteger("Growth rate");
        if (growthRate == null)
            return null;

        Integer maxMycelium = promptForPositiveInteger("Max mycelium");
        if (maxMycelium == null)
            return null;

        return new TileData(growthRate, maxMycelium, tek);
    }

    @Override
    public boolean execute(String[] args) {
        if (isWrongNumberOfArgs(2, args.length)) {
            return false;
        }

        String type = args[1];
        switch (type.toLowerCase()) {
            case "map":
                app.setMap(new Map());
                System.out.println("Created the map");
                break;
            case "cutspore": {
                TektonAndTile res = promptForTektonAndTile();
                if (res == null)
                    break;

                res.getTile().addEntity(assignId(new CutSpore()));

                break;
            }
            case "freezespore": {
                TektonAndTile res = promptForTektonAndTile();
                if (res == null)
                    break;

                res.getTile().addEntity(assignId(new FreezeSpore()));
                break;
            }
            case "slowspore": {
                TektonAndTile res = promptForTektonAndTile();
                if (res == null)
                    break;

                res.getTile().addEntity(assignId(new SlowSpore()));
                break;
            }
            case "speedupspore": {
                TektonAndTile res = promptForTektonAndTile();
                if (res == null)
                    break;

                res.getTile().addEntity(assignId(new SpeedUpSpore()));
                break;
            }
            case "fungusbody": {
                TektonAndTile res = promptForTektonAndTile();
                if (res == null)
                    break;

                Integer health = promptForPositiveInteger("Health");
                if (health == null)
                    break;

                Integer initialSporeCharge = promptForPositiveInteger("Initial spore charge");
                if (initialSporeCharge == null)
                    break;

                FungusBody fb = new FungusBody(health, initialSporeCharge, res.getTile());
                res.getTile().addEntity(assignId(fb));
                break;
            }
            case "insect": {
                TektonAndTile res = promptForTektonAndTile();
                if (res == null)
                    break;

                Insect insect = new Insect(askForId(), res.getTile(), app.getInsectPlayer());
                announceIdAssign(insect);
                res.getTile().addEntity(insect);
                break;
            }
            case "mycelium": {
                TektonAndTile res = promptForTektonAndTile();
                if (res == null)
                    break;
                Mycelium mycelium = new Mycelium();
                res.getTile().addEntity(assignId(mycelium));
                break;
            }
            case "tekton": {
                if (isMapUninitialized())
                    break;

                Integer breakChance = promptForPositiveInteger("Break chance");
                if (breakChance == null)
                    break;

                Integer sporeCount = promptForPositiveInteger("Spore count");
                if (sporeCount == null)
                    break;

                Tekton tek = new Tekton(breakChance, sporeCount);
                int id = app.getMap().addTekton(tek);
                System.out.println("Created a Tekton with the id " + id);

                break;
            }
            case "tile": {
                TileData tileData = promptForTileData();
                if (tileData == null)
                    break;

                Tile tile = new Tile(tileData.getGrowthRate(), tileData.getMaxMycelium(), tileData.getParentTekton());
                int id = tileData.getParentTekton().addTile(tile);
                System.out.println("Created a Tile with ID " + id);

                break;
            }
            case "acidtile": {
                TileData tileData = promptForTileData();
                if (tileData == null)
                    break;

                Integer damageRate = promptForPositiveInteger("Damage rate");
                if (damageRate == null)
                    break;

                Tile tile = new AcidTile(tileData.getGrowthRate(), tileData.getMaxMycelium(),
                        tileData.getParentTekton(), damageRate);
                int id = tileData.getParentTekton().addTile(tile);
                System.out.println("Created an AcidTile with ID " + id);

                break;
            }
            case "healtile": {
                TileData tileData = promptForTileData();
                if (tileData == null)
                    break;

                Tile tile = new HealTile(tileData.getGrowthRate(), tileData.getMaxMycelium(),
                        tileData.getParentTekton());
                int id = tileData.getParentTekton().addTile(tile);
                System.out.println("Created a HealTile with ID " + id);

                break;
            }
            case "monotile": {
                TileData tileData = promptForTileData();
                if (tileData == null)
                    break;

                Tile tile = new MonoTile(tileData.getGrowthRate(), tileData.getMaxMycelium(),
                        tileData.getParentTekton());
                int id = tileData.getParentTekton().addTile(tile);
                System.out.println("Created a MonoTile with ID " + id);

                break;
            }
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
