package entities;

import map.Tile;

public class FreezeSpore extends Spore {
    public FreezeSpore(int id, Tile currentTile, int nutrientValue, int lifetime, int effectTime, int effectValue) {
        super(id, currentTile, nutrientValue, lifetime, effectTime, effectValue);
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
