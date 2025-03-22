package map;

import entities.*;
import java.util.ArrayList;
import java.util.List;

import entities.FungusBody;

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

    //"break" is reserved keyword
    public void breakTekton() {
        // Will implement later
    }

    public void increaseChance(int amount) {
        breakChance += amount;
    }
}
