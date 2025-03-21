package entities;

import java.util.List;

public class Tekton {
    int breakChance;
    int sporeCount;
    List<Tile> tiles;
    FungusBody fungusBody;

    public Tekton(int breakChance, int sporeCount) {
        this.breakChance = breakChance;
        this.sporeCount = sporeCount;
        tiles = new ArrayList<>();
        fungusBody = null;
    }

    public void break() {
        // Will implement later
    }

    public void increaseChance(int amount) {
        breakChance += amount;
    }
}
