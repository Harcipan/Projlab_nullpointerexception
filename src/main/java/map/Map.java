package map;

import use_cases.UseCase;
import use_cases.UseCase.ArrowDirection;
import use_cases.UseCase.Indent;

import java.util.ArrayList;
import java.util.List;

import entities.GameEntity;

import static use_cases.UseCase.printWrapper;
import static use_cases.UseCase.replace;

/**
 * Map class represents the game map, which contains a list of tektons and a 2D array of tiles for quick access.
 * It provides methods to add and remove tektons, update game entities, and tick the map for game logic.
 */
public class Map {
    List<Tekton> tektons;
    Tile[][] tiles; // 2D array of tiles
    int width; // Width of the map
    int height; // Height of the map

    // WHY THE F*** DOES THIS EXIST?!?!?!?
    // compatibility with legacy code i guess
    public Map() {
        this(32, 32); // Default size
        width = 32;
        height = 32;
    }

    public Map(int width, int height) {
        replace(this);
        tektons = new ArrayList<>();
        tiles = new Tile[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                tiles[i][j] = new Tile();
                tiles[i][j].setX(i);
                tiles[i][j].setY(j);
            }
        }
        this.width = width;
        this.height = height;
    }

    public int addTekton(Tekton tekton) {
        //UseCase.printWrapper(UseCase.logger.get(this)+".addTekton(" + UseCase.logger.get(tekton)+")", ArrowDirection.RIGHT, Indent.INDENT);
        tektons.add(tekton);
        //UseCase.printWrapper(UseCase.logger.get(this)+".addTekton()", ArrowDirection.LEFT, Indent.UNINDENT);
        return tektons.size() - 1;
    }

    public List<Tekton> getTektons() {
        return tektons;
    }

    public void removeTekton(Tekton tekton) {
        tektons.remove(tekton);
    }

    public void update(GameEntity e) {
        UseCase.printWrapper(UseCase.logger.get(this)+".update(" + UseCase.logger.get(e)+")", ArrowDirection.RIGHT, Indent.INDENT);
        e.update();
        UseCase.printWrapper(UseCase.logger.get(this)+".update()", ArrowDirection.LEFT, Indent.UNINDENT);

    }

    public void tick() {
        // for every tile in the map
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
            
                tiles[i][j].update();
            }
        }
    }

    /**
     * Get the tile at the specified coordinates.
     * @param x The x-coordinate of the tile.
     * @param y The y-coordinate of the tile.
     * @return The tile at the specified coordinates, or null if out of bounds.
     */
    public Tile getTile(int x, int y) {
        // Check if the coordinates are within bounds
        if (x >= 0 && x < tiles.length && y >= 0 && y < tiles[0].length) {
            return tiles[x][y];
        }
        return null;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
