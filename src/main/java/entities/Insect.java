package entities;

public class Insect extends GameEntity {
    int speed;  // The speed of the insect
    boolean canCut; // Whether the insect can cut through mycelium

    public Insect(int id, Tile currentTile) {
        super(id, currentTile);
    }

    public void eat(Spore target) {
        // Will implement later
    }

    public void step(Tile target) {
        // Will implement later
    }

    public void cut(Tile target) {
        // Will implement later
    }

    public void setSpeedPercent(int percent) {
        speed = speed * percent / 100;
    }

    public void setCut(boolean canCut) {
        this.canCut = canCut;
    }




}
