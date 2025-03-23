package map;

import entities.*;

import java.lang.reflect.Array;
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
    // TODO this is a crude simplification of the actual process
    public ArrayList<Tekton> breakTekton() {

        ArrayList<Tekton> tl = new ArrayList<>();
        printWrapper("Breaking tekton...", UseCase.ArrowDirection.RIGHT, UseCase.IndentDirection.STAY);

        // based on some algorithm we break it into two pieces
        tl.add(new Tekton(breakChance, sporeCount));
        tl.add(new Tekton(breakChance, sporeCount));
        for (Tekton tekton : tl) {
            // migrating elements into new tektons
            printWrapper("New tekton " + System.identityHashCode(tekton)
                    + " created and migrated elements based on some algorithm",
                    UseCase.ArrowDirection.RIGHT, UseCase.IndentDirection.STAY);
        }

        return tl;
    }

    public void increaseChance(int amount) {
        breakChance += amount;
    }
}
