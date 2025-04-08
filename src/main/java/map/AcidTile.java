package map;

import entities.GameEntity;

public class AcidTile extends Tile{
    int damageRate; // The rate at which the acid damages entities (damage per tick)

    public AcidTile(int growthRate, int maxMycelium, Tekton parentTekton, int damageRate) {
        super(growthRate, maxMycelium, parentTekton);
        this.damageRate = damageRate;
    }


    private void damageEntities() {
        for (GameEntity ge : entities){
            for (int i = 0; i < damageRate; i++) {
                ge.damage(); // Call the damage method on each entity in the tile
            }
        }
    }

    @Override
    public void update() {
        damageEntities(); // Call the damageEntities method to apply damage to entities
        super.update(); // Call the parent class's update method to handle other updates
    }

}
