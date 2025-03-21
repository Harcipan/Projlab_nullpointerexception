package entities;

public class FreezeSpore extends Spore {
    public FreezeSpore(int nutrientValue, int lifetime, int effectTime, int effectValue) {
        super(nutrientValue, lifetime, effectTime, effectValue);
    }

    @Override
    public void getEaten(Insect i) {
        i.setSpeedPercent(-100);
        i.addSpore(this);
    }

    @Override
    public void removeEffect(Insect i) {
        i.setSpeedPercent(0);
    }


}
