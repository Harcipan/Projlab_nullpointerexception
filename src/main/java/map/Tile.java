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
        
        printWrapper("Tile: "+UseCase.logger.get(this), ArrowDirection.LEFT);
    }

    public void addEntity(GameEntity entity) {
        printWrapper(UseCase.logger.get(this)+".addEntity(" + UseCase.logger.get(entity)+")", ArrowDirection.RIGHT, Indent.INDENT);
        entities.add(entity);

        //add tile to entity
        entity.setTile(this);
        printWrapper(UseCase.logger.get(this)+".addEntity()", ArrowDirection.LEFT, Indent.UNINDENT);
    }

    public List<GameEntity> getEntities() {
        return entities;
    }

    public void removeEntity(GameEntity entity) {
        UseCase.printWrapper(UseCase.logger.get(this)+".removeEntity(" + UseCase.logger.get(entity)+")", ArrowDirection.RIGHT, Indent.KEEP);
        entities.remove(entity);
        UseCase.printWrapper(UseCase.logger.get(this)+".removeEntity()", ArrowDirection.LEFT, Indent.KEEP);
    }

    public Tekton getParentTekton() {
        return parentTekton;
    }

    public void setParentTekton(Tekton parentTekton) {
        printWrapper(UseCase.logger.get(this)+".setParentTekton(" + UseCase.logger.get(parentTekton)+")", ArrowDirection.RIGHT, Indent.KEEP);
        this.parentTekton = parentTekton;
        printWrapper(UseCase.logger.get(this)+".setParentTekton()", ArrowDirection.LEFT, Indent.KEEP);
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

    /*
     * Grow mycelium in this tile
     */
    public void growMycelium(FungusPlayer player) {
        UseCase.printWrapper(UseCase.logger.get(this)+".growMycelium("+UseCase.logger.get(player)+")", ArrowDirection.RIGHT, Indent.KEEP);
        if (myceliumSpace > 0) {
            Mycelium m = new Mycelium(GameEntity.getNextId(), 100, this, player);
            myceliumSpace--;
            addEntity(m);
            printWrapper(UseCase.logger.get(this)+".growMycelium()", ArrowDirection.LEFT, Indent.KEEP);
        } else {
            printWrapper(UseCase.logger.get(this)+".growMycelium()", ArrowDirection.LEFT, Indent.KEEP);
        }
        
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


    
}
