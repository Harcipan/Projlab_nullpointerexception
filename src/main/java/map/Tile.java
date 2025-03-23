package map;

import java.util.ArrayList;
import java.util.List;

import entities.*;

import static use_cases.UseCase.*;


public class Tile {
    int growthRate;
    int maxMycelium;
    Tekton parentTekton;
    List<GameEntity> entities;

    /*public Tile(int growthRate, int maxMycelium, Tekton parentTekton) {
        this.growthRate = growthRate;
        this.maxMycelium = maxMycelium;
        this.parentTekton = parentTekton;
        entities = new ArrayList<>();
    }*/

    public Tile() {
        entities = new ArrayList<>();
        printWrapper("Initializing Tile as " + System.identityHashCode(this), ArrowDirection.RIGHT, Indent.KEEP);
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
        printWrapper("Removing entity from tile", ArrowDirection.RIGHT, Indent.KEEP);
    }

    public Tekton getParentTekton() {
        return parentTekton;
    }

    public void setParentTekton(Tekton parentTekton) {
        this.parentTekton = parentTekton;
    }

    public void growMycelium() {
        // Will implement later
    }


    
}
