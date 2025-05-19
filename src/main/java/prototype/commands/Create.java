package prototype.commands;

import entities.*;
import map.AcidTile;
import map.HealTile;
import map.Map;
import map.MonoTile;
import map.Tekton;
import map.Tile;
import player.FungusPlayer;
import player.InsectPlayer;
import prototype.Command;
import prototype.App;

public class Create extends Command {

    private final String[] validTypes = {
            "CutSpore",
            "FreezeSpore",
            "FungusBody",
            "Insect",
            "InsectPlayer",
            "FungusPlayer",
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

    private Boolean promptForBoolean(String forWhat) {
        String rawStr = currentArgs[currentArgId++];
        return parseBoolean(rawStr, forWhat);
    }

    private Integer promptForPositiveInteger(String forWhat) {
        String rawStr = currentArgs[currentArgId++];
        return parsePositiveNumber(rawStr, forWhat);
    }

    private Integer promptForInteger(String forWhat) {
        String rawStr = currentArgs[currentArgId++];
        return parseNumber(rawStr, forWhat);
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

    private TektonWithId promptForTektonWithId(String forWhat) {
        Integer targetPlateId = promptForPositiveInteger(forWhat);
        if (targetPlateId == null)
            return null;
        try{
            return new TektonWithId(app.getMap().getTektons().get(targetPlateId), targetPlateId);
        }catch(IndexOutOfBoundsException ex){
            System.out.println("A tekton with the specified ID was not found");
            return null;
        }
    }

    private TektonAndTile promptForTektonAndTile() {
        if (isMapUninitialized())
            return null;

        TektonWithId tektonWithId = promptForTektonWithId("Tekton id");
        if (tektonWithId == null)
            return null;

        Integer targetTileId = promptForPositiveInteger("Tile id");
        if (targetTileId == null)
            return null;

        try{
            Tekton tek = tektonWithId.getTekton();
            Tile tile = tek.getTiles().get(targetTileId);
            return new TektonAndTile(tek, tektonWithId.getTektonId(), tile, targetTileId);
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

    /**
     * Creates an entity of given type.
     *
     * @return true if executing the command was successful, false otherwise
     */
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
        System.out.println("Creating " + type + "...");
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
            case "splitspore": {
                if (isWrongNumberOfArgs(4, args.length, "create SplitSpore <parent tekton id> <parent tile>")) {
                    return false;
                }
                TektonAndTile res = promptForTektonAndTile();
                if (res == null)
                    break;

                res.getTile().addEntity(assignId(new SplitSpore()));
                break;
            }
            case "fungusbody": {
                if (isWrongNumberOfArgs(7, args.length, "create FungusBody <player ID> <parent tekton id> <parent tile> <health> <initial spore charge>")) {
                    return false;
                }
                
                Integer playerID = promptForPositiveInteger("Player ID");
                if(playerID == null)
                    break;

                TektonAndTile res = promptForTektonAndTile();
                if (res == null)
                    break;

                Integer health = promptForPositiveInteger("Health");
                if (health == null)
                    break;

                Integer initialSporeCharge = promptForPositiveInteger("Initial spore charge");
                if (initialSporeCharge == null)
                    break;

                new FungusBody(askForId(), health, initialSporeCharge, res.getTile(), App.getFungusPlayers().get(playerID));
                break;
            }
            case "insect": {
                if (isWrongNumberOfArgs(7, args.length, "create insect <tekton id> <tile id> <player ID> <speed> <can cut flag>")) {
                    return false;
                }

                TektonAndTile tekTile = promptForTektonAndTile();
                if(tekTile == null)
                    break;

                Integer playerID = promptForPositiveInteger("Player ID");
                if(playerID == null)
                    break;

                Integer speed = promptForPositiveInteger("Speed");
                if(speed == null)
                    break;

                Boolean canCut = promptForBoolean("Can cut flag");
                if(canCut == null)
                    break;

                Tile tile = tekTile.getTile();
                Insect insect = new Insect(askForId(), tile, App.getInsectPlayers().get(playerID), speed, canCut);
                announceIdAssign(insect);
                tile.addEntity(insect);
                System.out.println("Created an insect with ID " + insect.getId() + " on tile " + tekTile.getTileId() + " of tekton " + tekTile.getTileId());
                break;
            }
            case "insectplayer": {
                if (isWrongNumberOfArgs(2, args.length, "create insectplayer")) {
                    return false;
                }
                InsectPlayer insectPlayer = new InsectPlayer();
                App.addInsectPlayer(insectPlayer);
                System.out.println("Created an insect player with ID " + insectPlayer.getID());
                break;
            }
            case "fungusplayer": {
                if (isWrongNumberOfArgs(2, args.length, "create fungusplayer")) {
                    return false;
                }
                FungusPlayer fungusPlayer = new FungusPlayer();
                System.out.println("Created a fungus player");
                break;
            }
            case "mycelium": {
                if (isWrongNumberOfArgs(6, args.length, "create mycelium <health> <parent tekton id> <parent tile id> <player id>")) {
                    return false;
                }
                
                Integer health = promptForPositiveInteger("Health");
                if(health == null)
                    break;

                TektonAndTile tekAndTile = promptForTektonAndTile();
                if(tekAndTile == null)
                    break;

                Integer playerId = promptForPositiveInteger("Player id");
                if(playerId == null)
                    break;

                Mycelium mycelium = new Mycelium(askForId(), health, tekAndTile.getTile(), App.getFungusPlayers().get(playerId));
                announceIdAssign(mycelium);
                System.out.println("Created a mycelium with ID " + mycelium.getId() + " on tile " + tekAndTile.getTileId() + " of tekton " + tekAndTile.getTektonId());
                break;
            }
            case "tekton": {
                if (isWrongNumberOfArgs(4, args.length, "create Tekton <break chance> <max spore count>")) {
                    return false;
                }
                if (isMapUninitialized()) {
                    break;
                }

                Integer breakChance = promptForPositiveInteger("Break chance");
                if(breakChance == null)
                    break;

                Integer sporeCount = promptForPositiveInteger("Spore count");
                if(sporeCount == null)
                    break;

                new Tekton(app.getMap(), breakChance, sporeCount);
                System.out.println("Created a Tekton with the id " + (app.getMap().getTektons().size() - 1));
                break;
            }
            case "tile": {
                if (isWrongNumberOfArgs(7, args.length, "create tile <growthRate> <maxMycelium> <parentTektonId> <coordX> <coordY>")){
                    return false;
                }

                Integer growthRate = promptForPositiveInteger("Growth rate");
                if(growthRate == null)
                    break;

                Integer maxMycelium = promptForPositiveInteger("Max mycelium");
                if(maxMycelium == null)
                    break;

                TektonWithId parentTektonWithId = promptForTektonWithId("Parent tekton");
                if(parentTektonWithId == null)
                    break;

                Integer coordX = promptForInteger("X Coordinate");
                if(coordX == null)
                    break;

                Integer coordY = promptForInteger("X Coordinate");
                if(coordY == null)
                    break;

                Tekton parentTekton = parentTektonWithId.getTekton();

                Tile tile = new Tile(growthRate, maxMycelium, parentTekton, coordX, coordY);
                int id = tile.getParentTekton().addTile(tile);
                System.out.println("Created a Tile with ID " + id + " on tekton ID " + parentTektonWithId.getTektonId());

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
                        tileData.getParentTekton(), damageRate,0,0);
                int id = tileData.getParentTekton().addTile(tile);
                System.out.println("Created an AcidTile with ID " + id);

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
                        tileData.getParentTekton(),0,0);
                int id = tileData.getParentTekton().addTile(tile);
                System.out.println("Created a HealTile with ID " + id);

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
                        tileData.getParentTekton(),0,0);
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
