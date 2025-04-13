package prototype;

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

    private static int entityId = 0;

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

    protected GameEntity assignId(GameEntity entity) {
        entity.setId(entityId++);
        announceIdAssign(entity);
        return entity;
    }

    protected int askForId() {
        return entityId++;
    }

    protected Integer parsePositiveNumber(String input, String forWhat) {
        Integer ret;
        try {
            ret = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("This is not a number. The " + forWhat.toLowerCase() + " can only be a number.");
            return null;
        }
        if (ret < 0) {
            System.out.println("The " + forWhat.toLowerCase() + " must be positive");
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

    public final class TektonAndTile {
        private final Tekton first;
        private final Tile second;

        public TektonAndTile(Tekton first, Tile second) {
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

    protected TektonAndTile parseTektonAndTile(String inputTekton, String inputTile) {
        Tekton tek = parseTekton(inputTekton, "Tectonic plate");
        if (tek == null)
            return null;

        Integer targetTileId = parsePositiveNumber(inputTile, "Tile ID");
        if (targetTileId == null)
            return null;

        Tile tile = tek.getTiles().get(targetTileId);
        if (tile == null) {
            System.out.println("A tile with the specified ID on the specified tectonic plate was not found");
            return null;
        }

        return new TektonAndTile(tek, tile);
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
