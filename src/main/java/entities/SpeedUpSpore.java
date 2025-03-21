package entities;

/*
 * Spore that speeds up the insect
 * effectValue must be greater than 100 (its a percentage)
 */
public class SpeedUpSpore extends Spore {
    public SpeedUpSpore(int nutrientValue, int lifetime, int effectTime, int effectValue) {
        super(nutrientValue, lifetime, effectTime, effectValue);
    }

    /*
     * apply speed increase to the insect
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
