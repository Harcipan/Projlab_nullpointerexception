package map;

import java.util.List;
import java.util.ArrayList;
import entities.*;
import use_cases.*;
import use_cases.UseCase.Direction;

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
        UseCase.printWrapper("Tile",Direction.RIGHT);
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
