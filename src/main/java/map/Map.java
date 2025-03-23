package map;

import use_cases.UseCase;

import java.util.ArrayList;
import java.util.List;

import static use_cases.UseCase.printWrapper;

public class Map {
    List<Tekton> tektons;

    public Map() {
        UseCase.printWrapper("Initializing Map as " + System.identityHashCode(this), UseCase.ArrowDirection.RIGHT, UseCase.Indent.KEEP);
        tektons = new ArrayList<>();
    }

    public void addTekton(Tekton tekton) {
        tektons.add(tekton);
    }

    public List<Tekton> getTektons() {
        return tektons;
    }

    public void removeTekton(Tekton tekton) {
        tektons.remove(tekton);
    }

    public void tick() {
        // for every tekton
        for (Tekton tek : tektons) {
            // for every tile
            for (Tile t : tek.tiles) {
                //determine if its mycelium is connected
                //TODO
            }
        }
    }
}
