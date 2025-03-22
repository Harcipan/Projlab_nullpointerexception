package map;

import entities.*;
import java.util.ArrayList;
import java.util.List;

import entities.FungusBody;
import use_cases.UseCase;

import static use_cases.UseCase.printWrapper;

public class Tekton {
    int breakChance;
    int sporeCount;
    List<Tile> tiles;
    FungusBody fungusBody;

    public Tekton(int breakChance, int sporeCount) {
        UseCase.printWrapper("Initializing Tekton as " + System.identityHashCode(this), UseCase.ArrowDirection.RIGHT, UseCase.IndentDirection.STAY);
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
