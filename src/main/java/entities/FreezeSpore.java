package entities;

import map.Tile;
import use_cases.UseCase;
import use_cases.UseCase.ArrowDirection;
import use_cases.UseCase.Indent;

/*
 * FreezeSpore is a spore that disables the movement of an insect when eaten.
 * It achieves this by setting the insect's speed to -100 when consumed.
 * It is a subclass of Spore and inherits its properties and methods.
 */
public class FreezeSpore extends Spore {
    public FreezeSpore(int id, Tile currentTile, int nutrientValue, int lifetime, int effectTime) {
        super(id, currentTile, nutrientValue, lifetime, effectTime, 0);
    }

    public FreezeSpore() {
        super();
        UseCase.replace(this);
        UseCase.printWrapper("Initializing FreezeSpore as " + UseCase.logger.get(this), ArrowDirection.RIGHT, Indent.KEEP);
       
    }

    /*
     * Set insect speed to -100, effectively disabling movement
     */
    @Override
    public void getEaten(Insect i) {
        isConsumed = true;
        i.setSpeedPercent(-100);
        i.addSpore(this);
        currentTile.removeEntity(this);
        
    }

    /*
     * Restore insect speed to 0 (applies default speed)
     */
    @Override
    public void removeEffect(Insect i) {
        i.setSpeedPercent(0);
    }


}
