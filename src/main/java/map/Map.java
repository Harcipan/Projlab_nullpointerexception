package map;

import use_cases.UseCase;
import use_cases.UseCase.ArrowDirection;
import use_cases.UseCase.Indent;

import java.util.ArrayList;
import java.util.List;

import entities.GameEntity;

import static use_cases.UseCase.printWrapper;
import static use_cases.UseCase.replace;

public class Map {
    List<Tekton> tektons;

    public Map() {
        replace(this);
        UseCase.printWrapper("Initializing Map as " + UseCase.logger.get(this), UseCase.ArrowDirection.RIGHT, UseCase.Indent.KEEP);
        tektons = new ArrayList<>();
        UseCase.printWrapper("Map: "+UseCase.logger.get(this), ArrowDirection.LEFT);
    }

    public int addTekton(Tekton tekton) {
        UseCase.printWrapper(UseCase.logger.get(this)+".addTekton(" + UseCase.logger.get(tekton)+")", ArrowDirection.RIGHT, Indent.INDENT);
        tektons.add(tekton);
        UseCase.printWrapper(UseCase.logger.get(this)+".addTekton()", ArrowDirection.LEFT, Indent.UNINDENT);
        return tektons.size() - 1;
    }

    public List<Tekton> getTektons() {
        return tektons;
    }

    public void removeTekton(Tekton tekton) {
        tektons.remove(tekton);
    }

    public void update(GameEntity e) {
        UseCase.printWrapper(UseCase.logger.get(this)+".update(" + UseCase.logger.get(e)+")", ArrowDirection.RIGHT, Indent.INDENT);
        e.update();
        UseCase.printWrapper(UseCase.logger.get(this)+".update()", ArrowDirection.LEFT, Indent.UNINDENT);

    }

    public void tick() {
        // for every tekton
        UseCase.printWrapper(UseCase.logger.get(this)+".tick()", ArrowDirection.RIGHT, Indent.INDENT);
        for (Tekton tek : tektons) {
            // for every tile
            for (Tile t : tek.tiles) {
                //determine if its mycelium is connected
                //TODO
            }
        }
        UseCase.printWrapper(UseCase.logger.get(this)+".tick()", ArrowDirection.LEFT, Indent.UNINDENT);
    }
}
