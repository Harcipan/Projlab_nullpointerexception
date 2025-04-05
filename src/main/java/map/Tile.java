package map;

import java.util.ArrayList;
import java.util.List;

import entities.*;
import use_cases.UseCase;
import use_cases.UseCase.ArrowDirection;
import use_cases.UseCase.Indent;

import static use_cases.UseCase.*;


public class Tile {
    int x;
    int y; // Coordinates of the tile in the map
    int growthRate;
    int maxMycelium;
    Tekton parentTekton;
    List<GameEntity> entities;

    public Tile(int growthRate, int maxMycelium, Tekton parentTekton, int x, int y) {
        this.growthRate = growthRate;
        this.maxMycelium = maxMycelium;
        this.parentTekton = parentTekton;
        entities = new ArrayList<>();
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
        /*printWrapper("Removing entity (typeof " + entity.getClass().getName()
                + ")" + System.identityHashCode(entity)+ " from tile", ArrowDirection.RIGHT, Indent.KEEP);*/
    }

    public Tekton getParentTekton() {
        return parentTekton;
    }
    
	public int getGrowthRate() {
		return growthRate;
	}

	public int getMaxMycelium() {
		return maxMycelium;
	}

    public void setParentTekton(Tekton parentTekton) {
        
        this.parentTekton = parentTekton;
    }
    // check if the tile is a neighbor of this tile
    // A tile is a neighbor if it is adjacent (horizontally, vertically, or diagonally)
    public boolean isNeighbor(Tile tile) {
        // check if the tile is null
        if (tile == null) {
            return false;
        }

        // check if the tile is the same as this tile
        if (this == tile) {
            return true;
        }

        // else compute the distance between the two tiles
        return Math.abs(this.x - tile.x) <= 1 && Math.abs(this.y - tile.y) <= 1;
    }

    public void growMycelium() {
        // Will implement later
    }

    public void update() {
        // Will implement later
    }


    
    
}
