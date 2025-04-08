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
        // probably by setting a flag from the mycelium when entering the tile
    }

    @Override
    public void update() {
        super.update(); // Call the parent class's update method to handle other updates
        if (entities.isEmpty()) {
            isTaken = false; // Reset isTaken if no entities are present (definitely not a fungus if it doesn't exist)
        }
    }
}
