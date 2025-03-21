package entities;

public abstract class Spore {
    int nutrientValue;  // The amount of nutrients the spore contains
    int lifetime;       // The number of turns the spore will last
    int effectTime;     // The number of turns the spore will apply an effect
    int efffectValue;   // The strength of the effect the spore will apply

    protected Spore(int nutrientValue, int lifetime, int effectTime, int effectValue) {
        this.nutrientValue = nutrientValue;
        this.lifetime = lifetime;
        this.effectTime = effectTime;
        this.efffectValue = effectValue;
    }

    public void getEaten(Insect i) {
        // override this method in subclasses
    }

    public void removeEffect(Insect i){
        // implemented in subclasses
    }

    public void update() {
        lifetime--;
        effectTime--;
    }

    public int getLifetime() {
        return lifetime;
    }

    public int getEffectTime() {
        return effectTime;
    }

    public int getNutrientValue() {
        return nutrientValue;
    }

}
