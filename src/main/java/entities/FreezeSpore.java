package entities;

public class FreezeSpore extends Spore {
    public FreezeSpore(int nutrientValue, int lifetime, int effectTime, int effectValue) {
        super(nutrientValue, lifetime, effectTime, effectValue);
    }

    /*
     * Set insect speed to -100, effectively disabling movement
     */
    @Override
    public void getEaten(Insect i) {
        isConsumed = true;
        i.setSpeedPercent(-100);
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
