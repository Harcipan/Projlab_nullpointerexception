package entities;

public class SpeedUpSpore extends Spore {
    public SpeedUpSpore(int nutrientValue, int lifetime, int effectTime, int effectValue) {
        super(nutrientValue, lifetime, effectTime, effectValue);
    }

    @Override
    public void getEaten(Insect i) {
        // Will implement later
    }

}
