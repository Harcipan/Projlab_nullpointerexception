package entities;

/*
 * Spore that slows down the insect that eats it
 * effectValue must be less than 100 (its a percentage)
 */
public class SlowSpore extends Spore {
    public SlowSpore(int nutrientValue, int lifetime, int effectTime, int effectValue) {
        super(nutrientValue, lifetime, effectTime, effectValue);
    }

    /*
     * apply speed reduction to the insect
     */
    @Override
    public void getEaten(Insect i) {
        isConsumed = true;
        i.setSpeedPercent(effectValue);
        i.addSpore(this);
    }

    /*
     * Restore insect speed to 0 (applies default speed)
     */
    @Override
    public void removeEffect(Insect i) {
        i.setSpeedPercent(0);
    }

}
