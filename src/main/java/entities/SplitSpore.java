package entities;

import map.Tile;
import use_cases.UseCase;

public class SplitSpore extends Spore {

    public SplitSpore() {
        super();
        UseCase.replace(this);
        UseCase.printWrapper("Initializing SplitSpore as " + UseCase.logger.get(this), UseCase.ArrowDirection.RIGHT, UseCase.Indent.KEEP);
        UseCase.printWrapper("SplitSpore: "+UseCase.logger.get(this), UseCase.ArrowDirection.LEFT);
    }

    public SplitSpore(int id, Tile currentTile, int nutrientValue, int lifetime) {
        super(id, currentTile, nutrientValue, lifetime, 0, 0);
    }

    /*
     * When an insect eats this spore, there will be another insect summoned.
     */
    @Override
    public void getEaten(Insect i) {
        UseCase.printWrapper(UseCase.logger.get(this)+".getEaten(" + UseCase.logger.get(i)+")", UseCase.ArrowDirection.RIGHT, UseCase.Indent.INDENT);
        isConsumed = true;
        i.split();
        currentTile.removeEntity(this);
        UseCase.printWrapper(UseCase.logger.get(this)+".getEaten()", UseCase.ArrowDirection.LEFT, UseCase.Indent.UNINDENT);
    }
}
