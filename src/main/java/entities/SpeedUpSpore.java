package entities;

import map.Tile;

/*
 * Spore that speeds up the insect
 * effectValue must be greater than 100 (its a percentage)
 */
public class SpeedUpSpore extends Spore {
    /*public SpeedUpSpore(int id, Tile currentTile, int nutrientValue, int lifetime, int effectTime, int effectValue) {
        super(id, currentTile, nutrientValue, lifetime, effectTime, effectValue);
    }*/

    public SpeedUpSpore() {
        super();
    }

    /*
     * apply speed increase to the insect
     */
    @Override
    public void getEaten(Insect i) {
        isConsumed = true;
        i.setSpeedPercent(effectValue);
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
