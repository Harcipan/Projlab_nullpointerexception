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
        /*printWrapper("Removing entity (typeof " + entity.getClass().getName()
                + ")" + System.identityHashCode(entity)+ " from tile", ArrowDirection.RIGHT, Indent.KEEP);*/
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
        return Math.abs(this.x - tile.x) <= 1 && Math.abs(this.y - tile.y) <= 1;
    }

    public void growMycelium() {
        // Will implement later
    }

    public void update() {
        // Will implement later
    }


    
}
