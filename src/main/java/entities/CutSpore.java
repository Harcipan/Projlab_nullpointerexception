package entities;

public class CutSpore extends Spore {
    public CutSpore(int nutrientValue, int lifetime, int effectTime, int effectValue) {
        super(nutrientValue, lifetime, effectTime, effectValue);
    }

    @Override
    public void getEaten(Insect i) {
        i.setCut(false);
        i.addSpore(this);
    }

    @Override
    public void removeEffect(Insect i) {
        i.setCut(true);
    }

}
