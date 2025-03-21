package entities;

/*
 * Spore that slows down the insect that eats it
 * effectValue must be negative
 */
public class SlowSpore extends Spore {
    public SlowSpore(int nutrientValue, int lifetime, int effectTime, int effectValue) {
        super(nutrientValue, lifetime, effectTime, effectValue);
    }

    @Override
    public void getEaten(Insect i) {
        i.addSpore(this);
        i.setSpeedPercent(efffectValue);
    }

    @Override
    public void removeEffect(Insect i) {
        i.setSpeedPercent(0);
    }

}
