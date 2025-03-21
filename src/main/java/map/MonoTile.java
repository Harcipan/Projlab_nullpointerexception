package map;

import entities.*;

public class MonoTile extends Tile{
    boolean isTaken; // Whether the tile is taken by a Fungus

    public MonoTile(int growthRate, int maxMycelium, Tekton parentTekton){
        super(growthRate, maxMycelium, parentTekton);
        isTaken =false;
    }

    public boolean isTaken() {
        return isTaken;
    }

    @Override
    public void addEntity(GameEntity entity) {
        super.addEntity(entity);
        isTaken = true;
        // TODO needs fixing to check if the entity is a fungus without type checking
    }
}
