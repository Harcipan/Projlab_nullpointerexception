package entities;

import java.util.List;

public class Tile {
    int growthRate;
    int maxMycelium;
    Tekton parentTekton;
    List<GameEntity> entities;

    public Tile(int growthRate, int maxMycelium, Tekton parentTekton) {
        this.growthRate = growthRate;
        this.maxMycelium = maxMycelium;
        this.parentTekton = parentTekton;
        entities = new ArrayList<>();
    }

    public void addEntity(GameEntity entity) {
        entities.add(entity);
    }

    public List<GameEntity> getEntities() {
        return entities;
    }

    public void removeEntity(GameEntity entity) {
        entities.remove(entity);
    }

    public void getParentTekton() {
        return parentTekton;
    }

    public void setParentTekton(Tekton parentTekton) {
        this.parentTekton = parentTekton;
    }

    public void growMycelium() {
        // Will implement later
    }


    
}
