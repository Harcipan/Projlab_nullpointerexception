package prototype;

import java.util.List;
import java.util.Scanner;

import entities.GameEntity;
import map.Tekton;
import map.Tile;

public abstract class Command {
    protected String name;
    protected String usage;
    protected String description;

    protected App app;
    protected Scanner scanner;

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
        this.usage = name;
    }

    public Command(String name, String description, String usage) {
        this.name = name;
        this.description = description;
        this.usage = usage;
    }

    protected void usage() {
        System.out.println("Usage: " + usage);
    }

    protected boolean isWrongNumberOfArgs(int expected, int got) {
        if (expected != got) {
            System.out.println("Wrong number of arguments (expected " + expected + ", got " + got + " arguments)");
            usage();
            return true;
        } else {
            return false;
        }
    }

    protected boolean isWrongNumberOfArgs(int expected, int got, String usageForThis) {
        if (expected != got) {
            System.out.println("Wrong number of arguments (expected " + expected + ", got " + got + " arguments)");
            System.out.println("Usage: " + usageForThis);
            return true;
        } else {
            return false;
        }
    }

    protected boolean isMapUninitialized() {
        if (app.getMap() == null) {
            System.out.println("You must create a map first by running \"create Map\"!");
            return true;
        }
        return false;
    }

    protected void announceIdAssign(GameEntity entity) {
        System.out.println("Created a \"" + entity.getClass().getSimpleName() + "\" entity with ID " + entity.getId());
    }

    
    protected int askForId() {
        return GameEntity.getNextId();
    }

    protected GameEntity assignId(GameEntity entity) {
        entity.setId(askForId());
        announceIdAssign(entity);
        return entity;
    }

    protected Integer parseNumber(String input, String forWhat) {
        Integer ret;
        try {
            ret = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("This is not a number. The " + forWhat.toLowerCase() + " can only be a number.");
            return null;
        }
        return ret;
    }

    protected Integer parsePositiveNumber(String input, String forWhat) {
        Integer ret = parseNumber(input, forWhat);
        if (ret < 0) {
            System.out.println("The " + forWhat.toLowerCase() + " must be positive");
            return null;
        }
        return ret;
    }

    protected Boolean parseBoolean(String input, String forWhat) {
        Boolean ret;
        try {
            ret = Boolean.parseBoolean(input);
        } catch (NumberFormatException e) {
            System.out.println("This is not a boolean value. The " + forWhat.toLowerCase() + " can only be a boolean value, 'true' or 'false'.");
            return null;
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    protected <T extends GameEntity> T findEntityWithId(int id, String forWhat) {
        for (Tekton tek : app.getMap().getTektons()) {
            for (Tile tile : tek.getTiles()) {
                for (GameEntity entity : tile.getEntities()) {
                    if (entity.getId() == id) {
                        try {
                            return (T) (entity);
                        } catch (ClassCastException ex) {
                            // Valamiert itt az Intellisense nem akar bedugulni. Azt hiszi, hogy az entity
                            // nem elerheto itt.
                            System.out.println("Unexpected \"" + entity.getClass().getSimpleName()
                                    + "\" type entity with ID " + id + ", expected a " + forWhat.toLowerCase());
                        }
                    }
                }
            }
        }
        System.out.println("A " + forWhat.toLowerCase() + " with ID " + id + " does not exist");
        return null;
    }

    protected <T extends GameEntity> T parseEntityId(String input, String forWhat) {
        Integer id = parsePositiveNumber(input, forWhat + " ID");
        if (id == null)
            return null;
        return findEntityWithId(id, forWhat);
    }

    protected Tekton parseTekton(String input, String forWhat) {
        Integer targetPlateId = parsePositiveNumber(input, forWhat + " ID");
        if (targetPlateId == null)
            return null;

        Tekton tek = app.getMap().getTektons().get(targetPlateId);
        if (tek == null) {
            System.out.println("A " + forWhat + " with the specified ID was not found");
            return null;
        }
        return tek;
    }

    protected final class TektonWithId{
        private final Tekton tekton;
        private final int tektonId;

        public TektonWithId(Tekton tekton, int tektonId){
            this.tekton = tekton;
            this.tektonId = tektonId;
        }

        public Tekton getTekton(){
            return tekton;
        }

        public int getTektonId(){
            return tektonId;
        }
    };

    protected TektonWithId parseTektonWithId(String input, String forWhat) {
        Integer targetPlateId = parsePositiveNumber(input, forWhat + " ID");
        if (targetPlateId == null)
            return null;

        Tekton tek = app.getMap().getTektons().get(targetPlateId);
        if (tek == null) {
            System.out.println("A " + forWhat + " with the specified ID was not found");
            return null;
        }
        return new TektonWithId(tek, targetPlateId);
    }

    protected final class TektonAndTile {
        private final Tekton first;
        private final int tektonId;
        private final Tile second;
        private final int tileId;

        public TektonAndTile(Tekton first, int tektonId, Tile second, int tileId) {
            this.first = first;
            this.tektonId = tektonId;
            this.second = second;
            this.tileId = tileId;
        }

        public Tekton getTekton() {
            return first;
        }

        public int getTektonId(){
            return tektonId;
        }

        public Tile getTile() {
            return second;
        }

        public int getTileId(){
            return tileId;
        }
    }

    protected TektonAndTile parseTektonAndTile(String inputTekton, String inputTile) {
        TektonWithId tektonAndId = parseTektonWithId(inputTekton, "Tectonic plate");
        if (tektonAndId == null)
            return null;

        Integer targetTileId = parsePositiveNumber(inputTile, "Tile ID");
        if (targetTileId == null)
            return null;

        Tekton tekton = tektonAndId.getTekton();
        List<Tile> tektonNeighbors = tekton.getTiles();
        Tile tile = tektonNeighbors.get(targetTileId);
        if (tile == null) {
            System.out.println("A tile with the specified ID on the specified tectonic plate was not found");
            return null;
        }

        return new TektonAndTile(tekton, tektonAndId.getTektonId(), tile, targetTileId);
    }

    public void setApp(App app) {
        this.app = app;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUsage() {
        return usage;
    }

    public abstract boolean execute(String[] args);

}
