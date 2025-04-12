package map;

import java.util.ArrayList;
import java.util.List;

import entities.*;
import player.FungusPlayer;
import use_cases.UseCase;
import use_cases.UseCase.ArrowDirection;
import use_cases.UseCase.Indent;

import static use_cases.UseCase.*;


public class Tile {
    int x;
    int y; // Coordinates of the tile in the map
    int growthRate;
    int myceliumSpace;      //how many more mycelium objects can be here
    Tekton parentTekton;
    List<GameEntity> entities;
    List<Tile> bridges;     // Bridges to tiles on other tektons

    public Tile(int growthRate, int maxMycelium, Tekton parentTekton, int x, int y) {
        this.growthRate = growthRate;
        this.myceliumSpace = maxMycelium;
        this.parentTekton = parentTekton;
        entities = new ArrayList<>();

        this.x = x;
        this.y = y;
    }

    public Tile(int growthRate, int maxMycelium, Tekton parentTekton) {
        this(growthRate, maxMycelium, parentTekton, 0, 0);
    }

    public Tile() {
        UseCase.replace(this);
        printWrapper("Initializing Tile as " + UseCase.logger.get(this), ArrowDirection.RIGHT, Indent.KEEP);

        entities = new ArrayList<>();
    }

    public void addEntity(GameEntity entity) {
        entities.add(entity);

        //add tile to entity
        entity.setTile(this);
    }

    public List<GameEntity> getEntities() {
        return entities;
    }

    public void removeEntity(GameEntity entity) {
        entities.remove(entity);
        UseCase.printWrapper(UseCase.logger.get(this)+".removeEntity()", ArrowDirection.LEFT, Indent.KEEP);
    }

    public Tekton getParentTekton() {
        return parentTekton;
    }
    
	public int getGrowthRate() {
		return growthRate;
	}

	public int getMaxMycelium() {
		return myceliumSpace;
	}

    public void setParentTekton(Tekton parentTekton) {
        
        this.parentTekton = parentTekton;
    }

    /*
     * Check if the tile is a neighbor of this tile
     * A tile is a neighbor if it is adjacent (horizontally, vertically, or diagonally)
     * @param tile the tile to check
     * @return true if the tile is a neighbor of this tile, false otherwise
     */
    public boolean isNeighbor(Tile tile) {
        // check if the tile is null
        if (tile == null) {
            return false;
        }
        // check if the tile is the same as this tile
        if (this == tile) {
            return false;
        }
        // else compute the distance between the two tiles
        return Math.abs(this.x - tile.x) <= 1 && Math.abs(this.y - tile.y) <= 1;
    }

    /*
     * Get a list of all neighboring tiles
     * @return a list of all neighboring tiles
     */
    public List<Tile> getNeighbors() {
        List<Tile> neighbors = new ArrayList<>();
        Map map = parentTekton.getMap();
        // check all 8 possible neighbors
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                // skip the tile itself
                if (i == 0 && j == 0) {
                    continue;
                }
                // check if the neighbor is valid
                Tile neighbor = map.getTile(x + i, y + j);
                if (neighbor != null) {
                    neighbors.add(neighbor);
                }
            }
        }
        return neighbors;
    }

    /*
     * Grow mycelium in this tile
     * Assuming tile has been verified
     * @param player the player that owns this mycelium
     */
    public Mycelium growMycelium(FungusPlayer player) {
        UseCase.printWrapper(UseCase.logger.get(this)+".growMycelium("+UseCase.logger.get(player)+")", ArrowDirection.RIGHT, Indent.KEEP);
        Mycelium m = null;
        if (myceliumSpace > 0) {
            m = new Mycelium(GameEntity.getNextId(), 100, this, player);
            myceliumSpace--;
            printWrapper(UseCase.logger.get(this)+".growMycelium()", ArrowDirection.LEFT, Indent.KEEP);
        } else {
            printWrapper(UseCase.logger.get(this)+".growMycelium()", ArrowDirection.LEFT, Indent.KEEP);
        }
        return m;
    }

    /*
     * Grow fungus body in this tile
     * Assuming tile has been verified
     * @param player the player that owns this fungus body
     */
    public FungusBody growBody(FungusPlayer player) {
        UseCase.printWrapper(UseCase.logger.get(this)+".growBody("+UseCase.logger.get(player)+")", ArrowDirection.RIGHT, Indent.KEEP);
        FungusBody b = null;
        b = new FungusBody(GameEntity.getNextId(), 100, this, player);
        printWrapper(UseCase.logger.get(this)+".growBody()", ArrowDirection.LEFT, Indent.KEEP);
        return b;
    }

    public void update() {
        for (GameEntity ge : entities) {
            ge.update();
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /*
     * Check if the tile has a bridge to the given tile
     * @param tile the tile to check
     * @return true if bridge exists, false otherwise
     */
    public boolean hasBridge(Tile tile) {
        return bridges.contains(tile);

    }


    
    
}
