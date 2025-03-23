package entities;

import map.Tile;

/*
 * Spore that slows down the insect that eats it
 * effectValue must be less than 100 (its a percentage)
 */
public class SlowSpore extends Spore {
    /*public SlowSpore(int id, Tile currentTile, int nutrientValue, int lifetime, int effectTime, int effectValue) {
        super(id, currentTile, nutrientValue, lifetime, effectTime, effectValue);
    }*/
    public SlowSpore(){
        super();
    }

    /*
     * apply speed reduction to the insect
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
