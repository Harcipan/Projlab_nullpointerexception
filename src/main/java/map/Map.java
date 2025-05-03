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
    public Map() {
        this(100, 100); // Default size
        width = 100;
        height = 100;
    }

    public Map(int width, int height) {
        replace(this);
        UseCase.printWrapper("Initializing Map as " + UseCase.logger.get(this), UseCase.ArrowDirection.RIGHT, UseCase.Indent.KEEP);
        tektons = new ArrayList<>();
        tiles = new Tile[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                tiles[i][j] = new Tile();
            }
        }
        this.width = width;
        this.height = height;
        UseCase.printWrapper("Map: "+UseCase.logger.get(this), ArrowDirection.LEFT);
    }

    public int addTekton(Tekton tekton) {
        UseCase.printWrapper(UseCase.logger.get(this)+".addTekton(" + UseCase.logger.get(tekton)+")", ArrowDirection.RIGHT, Indent.INDENT);
        tektons.add(tekton);
        UseCase.printWrapper(UseCase.logger.get(this)+".addTekton()", ArrowDirection.LEFT, Indent.UNINDENT);
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
        // for every tekton
        
        // TODO no clue how to implement this yet
        for (Tekton tek : tektons) {
            // for every tile
            for (Tile t : tek.tiles) {
                t.update();
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
        
        /*if (x < 0 || x >= width || y < 0 || y >= height) {
            return null; // Out of bounds
        }
        return tiles[x][y];
        for (Tekton tek : tektons) {
            // for every tile
            for (Tile t : tek.tiles) {
                if(t.x==x && t.y==y) return t;
            }
        }
        return null; 
        WHAT THE F*** IS THIS DOING HERE?!?!?!?
        */

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
