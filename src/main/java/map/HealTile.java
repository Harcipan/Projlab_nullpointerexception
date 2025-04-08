package map;

import entities.*;

public class HealTile extends Tile{
    public HealTile(int growthRate, int maxMycelium, Tekton parentTekton){
        super(growthRate, maxMycelium, parentTekton);
    }

    private void healEntities() {
        for (GameEntity ge : entities) {
            ge.heal(); // Call the heal method on each entity in the tile
        }
    }

    @Override
    public void update() {
        healEntities(); // Call the healEntities method to apply healing to entities
        super.update(); // Call the parent class's update method to handle other updates
    }


}
