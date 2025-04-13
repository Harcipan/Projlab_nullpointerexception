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
        super("create", "Creates a new entity or map element", "create <type> [type specific arguments]...");
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

    int currentArgId = 0;
    String[] currentArgs;

    private Integer promptForPositiveInteger(String forWhat) {
        /*System.out.print(forWhat + "> ");
        String rawStr = scanner.nextLine().trim();
        return parsePositiveNumber(rawStr, forWhat);*/
        String rawStr = currentArgs[currentArgId++];
        return parsePositiveNumber(rawStr, forWhat);
    }

    private Tekton promptForTekton(String forWhat) {
        Integer targetPlateId = promptForPositiveInteger(forWhat);
        if (targetPlateId == null)
            return null;

        try{
            return app.getMap().getTektons().get(targetPlateId);
        }catch(IndexOutOfBoundsException ex){
            System.out.println("A tekton with the specified ID was not found");
            return null;
        }
    }

    private TektonAndTile promptForTektonAndTile() {
        if (isMapUninitialized())
            return null;

        Tekton tek = promptForTekton("Tekton id");
        if (tek == null)
            return null;

        Integer targetTileId = promptForPositiveInteger("Tile id");
        if (targetTileId == null)
            return null;

        try{
            Tile tile = tek.getTiles().get(targetTileId);
            return new TektonAndTile(tek, tile);
        }catch(IndexOutOfBoundsException ex){
            System.out.println("A tile with the specified ID on the specified tekton was not found");
            return null;
        }
    }

    private TileData promptForTileData() {
        if (isMapUninitialized())
            return null;

        Tekton tek = promptForTekton("Parent tekton id");
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
        if(args.length < 2){
            System.out.println("You must specify the type!");
            usage();
            return false;
        }

        currentArgs = args;
        currentArgId = 2;

        String type = args[1];
        switch (type.toLowerCase()) {
            case "map":
                if (isWrongNumberOfArgs(2, args.length, "create Map")) {
                    return false;
                }
                app.setMap(new Map());
                System.out.println("Created the map");
                break;
            case "cutspore": {
                if (isWrongNumberOfArgs(4, args.length, "create CutSpore <parent tekton id> <parent tile>")) {
                    return false;
                }
                TektonAndTile res = promptForTektonAndTile();
                if (res == null)
                    break;

                res.getTile().addEntity(assignId(new CutSpore()));

                break;
            }
            case "freezespore": {
                if (isWrongNumberOfArgs(4, args.length, "create FreezeSpore <parent tekton id> <parent tile>")) {
                    return false;
                }
                TektonAndTile res = promptForTektonAndTile();
                if (res == null)
                    break;

                res.getTile().addEntity(assignId(new FreezeSpore()));
                break;
            }
            case "slowspore": {
                if (isWrongNumberOfArgs(4, args.length, "create SlowSpore <parent tekton id> <parent tile>")) {
                    return false;
                }
                TektonAndTile res = promptForTektonAndTile();
                if (res == null)
                    break;

                res.getTile().addEntity(assignId(new SlowSpore(50)));
                break;
            }
            case "speedupspore": {
                if (isWrongNumberOfArgs(4, args.length, "create SpeedupSpore <parent tekton id> <parent tile>")) {
                    return false;
                }
                TektonAndTile res = promptForTektonAndTile();
                if (res == null)
                    break;

                res.getTile().addEntity(assignId(new SpeedUpSpore(200)));
                break;
            }
            case "fungusbody": {
                if (isWrongNumberOfArgs(6, args.length, "create FungusBody <parent tekton id> <parent tile> <health> <initial spore charge>")) {
                    return false;
                }
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
                if (isWrongNumberOfArgs(4, args.length, "create Insect <parent tekton id> <parent tile>")) {
                    return false;
                }
                TektonAndTile res = promptForTektonAndTile();
                if (res == null)
                    break;

                Insect insect = new Insect(askForId(), res.getTile(), app.getInsectPlayer());
                announceIdAssign(insect);
                res.getTile().addEntity(insect);
                break;
            }
            case "mycelium": {
                if (isWrongNumberOfArgs(4, args.length, "create Mycelium <parent tekton id> <parent tile>")) {
                    return false;
                }
                TektonAndTile res = promptForTektonAndTile();
                if (res == null)
                    break;
                Mycelium mycelium = new Mycelium();
                res.getTile().addEntity(assignId(mycelium));
                break;
            }
            case "tekton": {
                if (isWrongNumberOfArgs(4, args.length, "create Tekton <break chance> <max spore count>")) {
                    return false;
                }
                if (isMapUninitialized())
                    break;

                Integer breakChance = promptForPositiveInteger("Break chance");
                if (breakChance == null)
                    break;

                Integer sporeCount = promptForPositiveInteger("Max spore count");
                if (sporeCount == null)
                    break;

                Tekton tek = new Tekton(app.getMap());
                app.getMap().addTekton(tek);
                System.out.println("Created a Tekton " + tek);

                break;
            }
            case "tile": {
                if (isWrongNumberOfArgs(5, args.length, "create Tile <parent tekton id> <growth rate> <max mycelium>")) {
                    return false;
                }
                TileData tileData = promptForTileData();
                if (tileData == null)
                    break;

                Tile tile = new Tile(tileData.getGrowthRate(), tileData.getMaxMycelium(), tileData.getParentTekton());
                tileData.getParentTekton().addTile(tile);
                System.out.println("Created a Tile " + tile);

                break;
            }
            case "acidtile": {
                if (isWrongNumberOfArgs(5, args.length, "create AcidTile <parent tekton id> <growth rate> <max mycelium>")) {
                    return false;
                }
                TileData tileData = promptForTileData();
                if (tileData == null)
                    break;

                Integer damageRate = promptForPositiveInteger("Damage rate");
                if (damageRate == null)
                    break;

                Tile tile = new AcidTile(tileData.getGrowthRate(), tileData.getMaxMycelium(),
                        tileData.getParentTekton(), damageRate);
                tileData.getParentTekton().addTile(tile);
                System.out.println("Created an AcidTile " + tile);

                break;
            }
            case "healtile": {
                if (isWrongNumberOfArgs(5, args.length, "create HealTile <parent tekton id> <growth rate> <max mycelium>")) {
                    return false;
                }
                TileData tileData = promptForTileData();
                if (tileData == null)
                    break;

                Tile tile = new HealTile(tileData.getGrowthRate(), tileData.getMaxMycelium(),
                        tileData.getParentTekton());
                tileData.getParentTekton().addTile(tile);
                System.out.println("Created a HealTile " + tile);

                break;
            }
            case "monotile": {
                if (isWrongNumberOfArgs(5, args.length, "create MonoTile <parent tekton id> <growth rate> <max mycelium>")) {
                    return false;
                }
                TileData tileData = promptForTileData();
                if (tileData == null)
                    break;

                Tile tile = new MonoTile(tileData.getGrowthRate(),
                        tileData.getParentTekton());
                tileData.getParentTekton().addTile(tile);
                System.out.println("Created a MonoTile " + tile);

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
