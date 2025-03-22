package entities;

import map.Tile;

public abstract class Spore extends GameEntity{
    int nutrientValue;  // The amount of nutrients the spore contains
    int lifetime;       // The number of turns the spore will last
    int effectTime;     // The number of turns the spore will apply an effect
    int effectValue;   // The strength of the effect the spore will apply
    boolean isConsumed; // Whether the spore has been eaten by an insect

    protected Spore(int id, Tile currentTile, int nutrientValue, int lifetime, int effectTime, int effectValue) {
        super(id, currentTile);
        this.nutrientValue = nutrientValue;
        this.lifetime = lifetime;
        this.effectTime = effectTime;
        this.effectValue = effectValue;
    }

    public void getEaten(Insect i) {
        isConsumed = true;
        // implemented in subclasses
        // take care to set flag in overridden method
    }

    public void removeEffect(Insect i){
        // implemented in subclasses
    }

    /*
     * Decrease the lifetime of the spore when on the ground
     * Decrease the effectTime of the spore when applied to an insect
     */
    public void update() {
        lifetime--;
        if(isConsumed){
            effectTime--;
        }
    }

    public int getLifetime() {
        return lifetime;
    }

    public int getEffectTime() {
        return effectTime;
    }

    public int getNutrientValue() {
        return nutrientValue;
    }

}
