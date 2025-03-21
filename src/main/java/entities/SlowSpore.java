package entities;

public class SlowSpore extends Spore {
    public SlowSpore(int nutrientValue, int lifetime, int effectTime, int effectValue) {
        super(nutrientValue, lifetime, effectTime, effectValue);
    }

    @Override
    public void getEaten(Insect i) {
        // Will implement later
    }

}
