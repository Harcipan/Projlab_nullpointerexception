package entities;

import map.Tile;
import use_cases.UseCase;
import use_cases.UseCase.ArrowDirection;
import use_cases.UseCase.Indent;

/*
 * Spore is an abstract class that represents a spore in the game.
 * It contains properties and methods that are common to all spores.
 * The spore can be eaten by insects and can apply effects to them.
 */
public abstract class Spore extends GameEntity{
    int nutrientValue;  // The amount of nutrients the spore contains
    int lifetime;       // The number of turns the spore will last
    int effectTime;     // The number of turns the spore will apply an effect
    int effectValue;   // The strength of the effect the spore will apply
    boolean isConsumed; // Whether the spore has been eaten by an insect

    /*
     * basic constructor for spore
     */
    protected Spore(int id, Tile currentTile, int nutrientValue, int lifetime, int effectTime, int effectValue) {
        super(id, currentTile);
        this.nutrientValue = nutrientValue;
        this.lifetime = lifetime;
        this.effectTime = effectTime;
        this.effectValue = effectValue;
    }

    protected Spore() {
        super();
    }

    public void getEaten(Insect i) {
        UseCase.printWrapper(UseCase.logger.get(this)+".getEaten(" + UseCase.logger.get(i)+")", ArrowDirection.RIGHT, Indent.INDENT);
        isConsumed = true;
        UseCase.printWrapper(UseCase.logger.get(this)+".getEaten()", ArrowDirection.LEFT, Indent.UNINDENT);
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
    @Override
    public void update() {
        if(isConsumed){
            effectTime--;
        }
        else{
            lifetime--;
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
