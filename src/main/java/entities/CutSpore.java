package entities;

public class CutSpore extends Spore {
    public CutSpore(int nutrientValue, int lifetime, int effectTime, int effectValue) {
        super(nutrientValue, lifetime, effectTime, effectValue);
    }

    /*
     * When an insect eats this spore, cutting ability is disabled
     */
    @Override
    public void getEaten(Insect i) {
        isConsumed = true;
        i.setCut(false);
        i.addSpore(this);
    }

    /*
     * Restore cutting ability
     */
    @Override
    public void removeEffect(Insect i) {
        i.setCut(true);
    }

}
